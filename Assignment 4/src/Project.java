import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int[] schedule = getEarliestSchedule();
        int maxDuration = 0;
        for (int i = 0; i < tasks.size(); i++) {
            maxDuration = Math.max(maxDuration, schedule[i] + tasks.get(i).getDuration());
        }
        return maxDuration;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {

        int[] earliestStart = new int[tasks.size()];
        int[] inDegree = new int[tasks.size()];
        Map<Integer, List<Integer>> adjList = new HashMap<>();

        // Initialize the adjacency list and in-degrees
        for (Task task : tasks) {
            List<Integer> dependencies = task.getDependencies();
            for (int dependency : dependencies) {
                if (!adjList.containsKey(dependency)) {
                    adjList.put(dependency, new ArrayList<>());
                }
                adjList.get(dependency).add(task.getTaskID());
                inDegree[task.getTaskID()]++;
            }
        }

        // Queue for tasks that are ready to be processed
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
                earliestStart[i] = 0; // Tasks without dependencies can start at day 0
            }
        }

        // Process tasks
        while (!queue.isEmpty()) {
            int currentTaskID = queue.poll();
            Task currentTask = tasks.get(currentTaskID);
            int currentEndTime = earliestStart[currentTaskID] + currentTask.getDuration();

            if (adjList.containsKey(currentTaskID)) {
                List<Integer> dependentTasks = adjList.get(currentTaskID);
                for (int dependentTaskID : dependentTasks) {
                    earliestStart[dependentTaskID] = Math.max(earliestStart[dependentTaskID], currentEndTime);
                    inDegree[dependentTaskID]--;
                    if (inDegree[dependentTaskID] == 0) {
                        queue.add(dependentTaskID);
                    }
                }
            }
        }

        return earliestStart;
    
    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    /**
     * Some free code here. YAAAY! 
     */
    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s","Task ID","Description","Start","End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i]+t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
