import java.util.*;
import java.io.*;

public class Quiz3 {
    static class Station {
        int x, y;

        public Station(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public double distanceTo(Station other) {
            return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
        }
    }

    static class Edge implements Comparable<Edge> {
        int u, v;
        double weight;

        public Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }

    static class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int u) {
            if (parent[u] != u)
                parent[u] = find(parent[u]);
            return parent[u];
        }

        public boolean union(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);
            if (rootU != rootV) {
                if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU;
                } else if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV;
                } else {
                    parent[rootV] = rootU;
                    rank[rootU]++;
                }
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        // Set locale to US to use dot as decimal separator
        Locale.setDefault(Locale.US);

        // Read input file from args[0]
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        int T = Integer.parseInt(br.readLine().trim()); // number of test cases
        StringBuilder output = new StringBuilder();
        
        while (T-- > 0) {
            String[] sp = br.readLine().trim().split("\\s+");
            int S = Integer.parseInt(sp[0]); // stations with drones
            int P = Integer.parseInt(sp[1]); // total stations
            
            Station[] stations = new Station[P];
            for (int i = 0; i < P; i++) {
                sp = br.readLine().trim().split("\\s+");
                stations[i] = new Station(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]));
            }
            
            // Minimum spanning tree calculation using Kruskal's algorithm
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < P; i++) {
                for (int j = i + 1; j < P; j++) {
                    double dist = stations[i].distanceTo(stations[j]);
                    edges.add(new Edge(i, j, dist));
                }
            }
            Collections.sort(edges);

            // Drone stations are fully connected
            UnionFind uf = new UnionFind(P);
            // Connect longest distance edges with drones to minimize the maximum distance (stations that have drones are connected and their weight is 0)
            // Place drones to minimize the T, the maximum distance between stations

            double minT = 0.0;
            int connections = 0;
            for (Edge edge : edges) {
                if (uf.find(edge.u) == uf.find(edge.v)) continue;
                uf.union(edge.u, edge.v);
                connections++;

                if (connections >= P - S) {
                    minT = edge.weight;
                    break;
                }
            }
            output.append(String.format("%.2f\n", minT));
        }
        System.out.print(output);

        br.close();
    }

}
