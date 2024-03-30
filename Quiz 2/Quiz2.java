import java.util.*;
import java.io.*;

public class Quiz2 {
    public static void main(String[] args) throws IOException {
        // Reading input from the file
        File file = new File(args[0]);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] firstLine = reader.readLine().split(" ");
        int M = Integer.parseInt(firstLine[0]);
        int n = Integer.parseInt(firstLine[1]);
        int[] masses = Arrays.stream(reader.readLine().split(" "))
                             .mapToInt(Integer::parseInt)
                             .toArray();

        // Initializing the L matrix
        boolean[][] L = new boolean[M + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            L[0][i] = true; // 0 kg can always be achieved
        }

        // Dynamic Programming to fill the L matrix
        for (int i = 1; i <= n; i++) {
            for (int m = 1; m <= M; m++) {
                if (masses[i - 1] > m) {
                    L[m][i] = L[m][i - 1];
                } else {
                    L[m][i] = L[m][i - 1] || L[m - masses[i - 1]][i - 1];
                }
            }
        }

        // Finding the maximum mass that can be loaded
        int maxMass = 0;
        for (int m = M; m >= 0; m--) {
            if (L[m][n]) {
                maxMass = m;
                break;
            }
        }

        // Output
        System.out.println(maxMass);
        for (int m = 0; m <= M; m++) {
            for (int i = 0; i <= n; i++) {
                System.out.print(L[m][i] ? 1 : 0);
            }
            System.out.println();
        }

        reader.close();
    }

}
