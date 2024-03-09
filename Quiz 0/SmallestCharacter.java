import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class SmallestCharacter {
    public static void main(String[] args) {
        // First command-line argument contains the path to the input file
        // Structured as below:
        // Line 0: <query-1> <query-2> <query-3> ...
        // Line 1: <word-1> <word-2> <word-3> ...

        // Your program should print to the standard output.
        // Your output should match the given format character-by-character.
        // For example for the sample input:
        // [1]
        // If there are more than one integer to print then:
        // [1,2,3,4,5]

        File file = new File(args[0]);

        // Input file format is structured as below:
        // Line 0: query-1 query-2 query-3 ...
        // Line 1: word-1 word-2 word-3 ...

        //Let the function f(s) be the frequency of the lexicographically smallest character
        //in a non-empty string s. For example, if s = "dcce" then f(s) = 2 because the
        //lexicographically smallest character is ’c’, which has a frequency of 2.
        //You are given an array words of string words and another array queries of string
        //queries. For each query queries[i], count the number of words in words such that
        //f(queries[i]) < f(W) for each W in words.
        //Return an integer array answer, where each answer[i] is the answer to the ith query.

        try {
            Scanner scanner = new Scanner(file);
            String[] queries = scanner.nextLine().split(" ");
            String[] words = scanner.nextLine().split(" ");
            scanner.close();

            int[] answer = new int[queries.length];

            for (int i = 0; i < queries.length; i++) {
                int count = 0;
                for (String word : words) {
                    if (f(queries[i]) < f(word)) {
                        count++;
                    }
                }
                answer[i] = count;
            }

            System.out.println(Arrays.toString(answer));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
    }

    public static int f(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                return freq[i];
            }
        }
        return 0;
    }
}
