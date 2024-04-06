import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        int N = amountOfEnergyDemandsArrivingPerHour.size();
        int[] SOL = new int[N + 1];
        ArrayList<ArrayList<Integer>> HOURS = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            HOURS.add(new ArrayList<>());
        }

        for (int j = 1; j <= N; j++) {
            for (int i = 0; i < j; i++) {
                int additionalPower = Math.min(amountOfEnergyDemandsArrivingPerHour.get(j - 1), batteryEfficiency(j - i));
                if (SOL[i] + additionalPower > SOL[j]) {
                    SOL[j] = SOL[i] + additionalPower;
                    HOURS.set(j, new ArrayList<>(HOURS.get(i)));
                    HOURS.get(j).add(j);
                }
            }
        }

        return new OptimalPowerGridSolution(SOL[N], HOURS.get(N));
    }


    /**
     * A basic function to calculate battery efficiency
     * @param i battery charge
     * @return battery efficiency
     */
    private int batteryEfficiency(int i) {
        return i * i;
    }
}
