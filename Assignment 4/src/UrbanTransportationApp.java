import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Comparator;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;
    
    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    /**
     * Function calculate the fastest route from the user's desired starting point to 
     * the desired destination point, taking into consideration the hyperloop train
     * network. 
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();

        // Make an adjacency list of the network, each station will have a list of stations it is connected to (all stations are connected)
        // If two stations are connected by a train line, travel time is computed with the formula: distance / train speed
        // If two stations are not connected by a train line, travel time is computed with the formula: distance / average walking speed

        Map<Station, List<StationEdge>> graph = buildGraph(network);

        List<Station> path = getShortestPath(graph, network.startPoint, network.destinationPoint);

        // Get the route directions from the path
        for (int i = 0; i < path.size() - 1; i++) {
            Station start = path.get(i);
            Station end = path.get(i + 1);
            boolean trainRide = areStationsConnectedByTrain(start, end, graph, network);
            double duration = graph.get(start).stream().filter(edge -> edge.station.equals(end)).findFirst().get().time;
            routeDirections.add(new RouteDirection(start.description, end.description, duration, trainRide));
        }
        
        return routeDirections;
    }
    

    private double getDistance(Station station1, Station station2) {
        return Math.sqrt(Math.pow(station1.coordinates.x - station2.coordinates.x, 2) + Math.pow(station1.coordinates.y - station2.coordinates.y, 2));
    }


    private Map<Station, List<StationEdge>> buildGraph(HyperloopTrainNetwork network) {
        Map<Station, List<StationEdge>> graph = new HashMap<>();
        
        for (TrainLine line : network.lines) {
            List<Station> stations = line.trainLineStations;
            for (int i = 0; i < stations.size() - 1; i++) {
                Station start = stations.get(i);
                Station end = stations.get(i + 1);

                double distance = getDistance(start, end);
                double time = distance / network.averageTrainSpeed;

                graph.computeIfAbsent(start, k -> new ArrayList<>()).add(new StationEdge(end, time));
                graph.computeIfAbsent(end, k -> new ArrayList<>()).add(new StationEdge(start, time));
            }
        }


        // If two stations are not connected by a train line, travel time is computed with the formula: distance / average walking speed
        for (int i = 0; i < network.lines.size(); i++) {
            for (int j = i + 1; j < network.lines.size(); j++) {
                List<Station> stations1 = network.lines.get(i).trainLineStations;
                List<Station> stations2 = network.lines.get(j).trainLineStations;

                for (Station station1 : stations1) {
                    for (Station station2 : stations2) {
                        double distance = getDistance(station1, station2);
                        double time = distance / network.averageWalkingSpeed;
                        graph.computeIfAbsent(station1, k -> new ArrayList<>()).add(new StationEdge(station2, time));
                        graph.computeIfAbsent(station2, k -> new ArrayList<>()).add(new StationEdge(station1, time));
                    }
                }
            }
        }

        // Calculate distance from starting point to all other stations
        for (TrainLine line : network.lines) {
            Station start = network.startPoint;
            List<Station> stations = line.trainLineStations;
            for (int i = 0; i < stations.size(); i++) {
                Station end = stations.get(i);
                double distance = getDistance(start, end);
                double time = distance / network.averageWalkingSpeed;
                graph.computeIfAbsent(start, k -> new ArrayList<>()).add(new StationEdge(end, time));
                graph.computeIfAbsent(end, k -> new ArrayList<>()).add(new StationEdge(start, time));
            }
        }

        // Calculate distance from starting point to destination point
        double startingToDestinationDistance = getDistance(network.startPoint, network.destinationPoint);
        double startingToDestinationTime = startingToDestinationDistance / network.averageWalkingSpeed;
        graph.computeIfAbsent(network.startPoint, k -> new ArrayList<>()).add(new StationEdge(network.destinationPoint, startingToDestinationTime));
        graph.computeIfAbsent(network.destinationPoint, k -> new ArrayList<>()).add(new StationEdge(network.startPoint, startingToDestinationTime));
        



        // Calculate distance from destination point to all other stations
        for (TrainLine line : network.lines) {
            Station start = network.destinationPoint;
            List<Station> stations = line.trainLineStations;
            for (int i = 0; i < stations.size(); i++) {
                Station end = stations.get(i);
                double distance = getDistance(start, end);
                double time = distance / network.averageWalkingSpeed;
                graph.computeIfAbsent(start, k -> new ArrayList<>()).add(new StationEdge(end, time));
                graph.computeIfAbsent(end, k -> new ArrayList<>()).add(new StationEdge(start, time));
            }
        }
        

        return graph;
    }

    private List<Station> getShortestPath(Map<Station, List<StationEdge>> graph, Station start, Station end) {
        // Map to store the shortest distance from the start to each station
        Map<Station, Double> distances = new HashMap<>();
        // Map to track the optimal path
        Map<Station, Station> previousStations = new HashMap<>();
        // Priority queue to process nodes in the order of increasing distance
        PriorityQueue<Station> priorityQueue = new PriorityQueue<>(Comparator.comparing(distances::get));

        // Initialization: set every distance to infinity except the start node
        for (Station station : graph.keySet()) {
            distances.put(station, Double.MAX_VALUE);
            previousStations.put(station, null);
        }
        distances.put(start, 0.0);
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            Station current = priorityQueue.poll();

            // Stop if we reach the destination
            if (current.equals(end)) {
                break;
            }

            for (StationEdge edge : graph.get(current)) {
                Station neighbor = edge.station;
                double newDist = distances.get(current) + edge.time;

                // If a shorter path to the neighbor is found, update and queue the neighbor
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousStations.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            }
        }

        // Reconstruct the path from end to start using the previousStations map
        List<Station> path = new ArrayList<>();
        for (Station at = end; at != null; at = previousStations.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);  // Reverse the path to be from start to end
        return path;
    }

    private boolean areStationsConnectedByTrain(Station station1, Station station2, Map<Station, List<StationEdge>> graph, HyperloopTrainNetwork network) {
        if (graph.get(station1) != null) {
            for (StationEdge edge : graph.get(station1)) {
                if (edge.station.equals(station2) && edge.time == (getDistance(station1, station2) / network.averageTrainSpeed)) {
                    return true;
                }
            }
        }
        return false;
    }
    

    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        double totalTimeDouble = 0;
        for (RouteDirection direction : directions) {
            totalTimeDouble += direction.duration;
        }

        // Round to the nearest minute
        int totalTime = (int) Math.round(totalTimeDouble);

        System.out.println("The fastest route takes " + totalTime + " minute(s).");
        System.out.println("Directions\n----------");

        int step = 1;
        for (RouteDirection direction : directions) {
            String travelMode = direction.trainRide ? "Get on the train from" : "Walk from";
            System.out.printf("%d. %s \"%s\" to \"%s\" for %.2f minutes.\n", 
                            step++, 
                            travelMode, 
                            direction.startStationName, 
                            direction.endStationName, 
                            direction.duration);
        }    

    }

    class StationEdge {
        public Station station;
        public double time;

        public StationEdge(Station station, double time) {
            this.station = station;
            this.time = time;
        }
    }
}