import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class MissingNumber {

    public static void main(String[] args) {

        // First command-line argument refers to the number of integers
        // Second command-line argument contains the path to the input file
        // Your program should only print a single integer to the standard output
        // For the sample input, your output should be:
        // 2

        int n = Integer.parseInt(args[0]);

        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += i;
        }

        // Input file is one line containing n-1 integers separated by a space
        File file = new File(args[1]);

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextInt()) {
                sum -= scanner.nextInt();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(sum);
    }

}