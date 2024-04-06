import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read the demand schedule from the file given as the first command-line argument
        ArrayList<Integer> demandSchedule = new ArrayList<>();
        File file1 = new File(args[0]);
        Scanner sc1 = new Scanner(file1);
        while (sc1.hasNextInt()) {
            demandSchedule.add(sc1.nextInt());
        }
        sc1.close();

        PowerGridOptimization powerGridOptimization = new PowerGridOptimization(demandSchedule);
        OptimalPowerGridSolution solution = powerGridOptimization.getOptimalPowerGridSolutionDP();

        int totalDemandGW = demandSchedule.stream().mapToInt(Integer::intValue).sum();
        int unsatisfiedGW = totalDemandGW - solution.getmaxNumberOfSatisfiedDemands();

        String hoursStr = solution.getHoursToDischargeBatteriesForMaxEfficiency().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        System.out.println("The total number of demanded gigawatts: " + totalDemandGW);
        System.out.println("Maximum number of satisfied gigawatts: " + solution.getmaxNumberOfSatisfiedDemands());
        System.out.println("Hours at which the battery bank should be discharged: " + hoursStr);
        System.out.println("The number of unsatisfied gigawatts: " + unsatisfiedGW);
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        // Read ESV information and task requirements from the second command-line argument
        ArrayList<Integer> esvInfoAndTasks = new ArrayList<>();
        File file2 = new File(args[1]);
        Scanner sc2 = new Scanner(file2);
        while (sc2.hasNextInt()) {
            esvInfoAndTasks.add(sc2.nextInt());
        }
        sc2.close();

        int maxNumberOfAvailableESVs = esvInfoAndTasks.get(0);
        int maxESVCapacity = esvInfoAndTasks.get(1);
        ArrayList<Integer> maintenanceTasks = new ArrayList<>(esvInfoAndTasks.subList(2, esvInfoAndTasks.size()));

        OptimalESVDeploymentGP esvDeployment = new OptimalESVDeploymentGP(maintenanceTasks);
        int minNumESVs = esvDeployment.getMinNumESVsToDeploy(maxNumberOfAvailableESVs, maxESVCapacity);

        System.out.println("##MISSION ECO-MAINTENANCE##");
        System.out.println("The minimum number of ESVs to deploy: " + minNumESVs);
        for (int i = 0; i < esvDeployment.getMaintenanceTasksAssignedToESVs().size(); i++) {
            System.out.println("ESV " + (i + 1) + " tasks: " + esvDeployment.getMaintenanceTasksAssignedToESVs().get(i));
        }
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
}
