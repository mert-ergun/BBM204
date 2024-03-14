import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Experiments {
    public static double[] SortRandomData(int size) {
        // Read data from file
        List<Integer> data = FileIO.readFile("TrafficFlowDataset.csv");
        Collections.shuffle(data);

        // Convert list to array
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = data.get(i);
        }

        double insertionSortTime = 0;
        double mergeSortTime = 0;
        double countingSortTime = 0;

        double[] times = new double[3];

        // Insertion Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            InsertionSort.sort(array.clone());
            long endTime = System.currentTimeMillis();
            insertionSortTime += endTime - startTime;
        }
        insertionSortTime /= 10;
        times[0] = insertionSortTime;
        System.out.println("Insertion Sort: " + insertionSortTime + " ms for " + size + " elements");

        // Merge Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            MergeSort.sort(array.clone());
            long endTime = System.currentTimeMillis();
            mergeSortTime += endTime - startTime;
        }
        mergeSortTime /= 10;
        times[1] = mergeSortTime;
        System.out.println("Merge Sort: " + mergeSortTime + " ms for " + size + " elements");

        // Counting Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            CountingSort.sort(array.clone());
            long endTime = System.currentTimeMillis();
            countingSortTime += endTime - startTime;
        }
        countingSortTime /= 10;
        times[2] = countingSortTime;
        System.out.println("Counting Sort: " + countingSortTime + " ms for " + size + " elements");

        return times;
    }

    public static double[] SortSortedData(int size) {
        // Read data from file
        List<Integer> data = FileIO.readFile("TrafficFlowDataset.csv");
        Collections.shuffle(data);

        // Convert list to array
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = data.get(i);
        }

        Arrays.sort(array);

        double insertionSortTime = 0;
        double mergeSortTime = 0;
        double countingSortTime = 0;

        double[] times = new double[3];

        // Insertion Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            InsertionSort.sort(array.clone());
            long endTime = System.currentTimeMillis();
            insertionSortTime += endTime - startTime;
        }
        insertionSortTime /= 10;
        times[0] = insertionSortTime;
        System.out.println("Insertion Sort: " + insertionSortTime + " ms for " + size + " elements");

        // Merge Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            MergeSort.sort(array.clone());
            long endTime = System.currentTimeMillis();
            mergeSortTime += endTime - startTime;
        }
        mergeSortTime /= 10;
        times[1] = mergeSortTime;
        System.out.println("Merge Sort: " + mergeSortTime + " ms for " + size + " elements");

        // Counting Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            CountingSort.sort(array.clone());
            long endTime = System.currentTimeMillis();
            countingSortTime += endTime - startTime;
        }
        countingSortTime /= 10;
        times[2] = countingSortTime;
        System.out.println("Counting Sort: " + countingSortTime + " ms for " + size + " elements");

        return times;
    }

    public static double[] SortReverseSortedData(int size) {
        // Read data from file
        List<Integer> data = FileIO.readFile("TrafficFlowDataset.csv");
        Collections.shuffle(data);

        // Convert list to array
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = data.get(i);
        }

        Arrays.sort(array);
        int[] reverseArray = new int[size];

        for (int i = 0; i < size; i++) {
            reverseArray[i] = array[size - i - 1];
        }

        double insertionSortTime = 0;
        double mergeSortTime = 0;
        double countingSortTime = 0;

        double[] times = new double[3];

        // Insertion Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            InsertionSort.sort(reverseArray.clone());
            long endTime = System.currentTimeMillis();
            insertionSortTime += endTime - startTime;
        }
        insertionSortTime /= 10;
        times[0] = insertionSortTime;
        System.out.println("Insertion Sort: " + insertionSortTime + " ms for " + size + " elements");

        // Merge Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            MergeSort.sort(reverseArray.clone());
            long endTime = System.currentTimeMillis();
            mergeSortTime += endTime - startTime;
        }
        mergeSortTime /= 10;
        times[1] = mergeSortTime;
        System.out.println("Merge Sort: " + mergeSortTime + " ms for " + size + " elements");

        // Counting Sort
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            CountingSort.sort(reverseArray.clone());
            long endTime = System.currentTimeMillis();
            countingSortTime += endTime - startTime;
        }
        countingSortTime /= 10;
        times[2] = countingSortTime;
        System.out.println("Counting Sort: " + countingSortTime + " ms for " + size + " elements");

        return times;
    }

    public static double SearchRandomData(int size) {
        // Read data from file
        List<Integer> data = FileIO.readFile("TrafficFlowDataset.csv");
        Collections.shuffle(data);

        // Convert list to array
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = data.get(i);
        }

        double linearSearchTime = 0;
        double binarySearchTime = 0;

        // Linear Search
        for (int i = 0; i < 1000; i++) {
            // Select a random element from array
            int key = array[(int) (Math.random() * size)];
            long startTime = System.nanoTime();
            LinearSearch.search(array, key);
            long endTime = System.nanoTime();
            linearSearchTime += endTime - startTime;
        }
        linearSearchTime /= 1000;
        System.out.println("Linear Search: " + linearSearchTime + " ns for " + size + " elements");

        // Binary Search
        for (int i = 0; i < 1000; i++) {
            // Select a random element from array
            int key = array[(int) (Math.random() * size)];
            long startTime = System.nanoTime();
            BinarySearch.search(array, key);
            long endTime = System.nanoTime();
            binarySearchTime += endTime - startTime;
        }
        binarySearchTime /= 1000;
        System.out.println("Binary Search: " + binarySearchTime + " ns for " + size + " elements");

        return linearSearchTime;
    }

    public static double[] SearchSortedData(int size) {
        // Read data from file
        List<Integer> data = FileIO.readFile("TrafficFlowDataset.csv");
        Collections.shuffle(data);

        // Convert list to array
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = data.get(i);
        }

        Arrays.sort(array);

        double linearSearchTime = 0;
        double binarySearchTime = 0;

        double[] times = new double[2];

        // Linear Search
        for (int i = 0; i < 1000; i++) {
            // Select a random element from array
            int key = array[(int) (Math.random() * size)];
            long startTime = System.nanoTime();
            LinearSearch.search(array, key);
            long endTime = System.nanoTime();
            linearSearchTime += endTime - startTime;
        }
        linearSearchTime /= 1000;
        times[0] = linearSearchTime;
        System.out.println("Linear Search: " + linearSearchTime + " ns for " + size + " elements");

        // Binary Search
        for (int i = 0; i < 1000; i++) {
            // Select a random element from array
            int key = array[(int) (Math.random() * size)];
            long startTime = System.nanoTime();
            BinarySearch.search(array, key);
            long endTime = System.nanoTime();
            binarySearchTime += endTime - startTime;
        }
        binarySearchTime /= 1000;
        times[1] = binarySearchTime;
        System.out.println("Binary Search: " + binarySearchTime + " ns for " + size + " elements");

        return times;
    }

    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            // Adjust the range of random values as needed
            array[i] = ThreadLocalRandom.current().nextInt(0, size);
        }
        return array;
    }

    public static void main(String[] args) {

        for (int warmup = 0; warmup < 100000; warmup++) {
            int[] warmupArray = generateRandomArray(500); // Generate or read and shuffle
            int key = warmupArray[ThreadLocalRandom.current().nextInt(500)];
            LinearSearch.search(warmupArray, key);
            BinarySearch.search(warmupArray, key);
        }


        System.out.println("Sort Random Data");
        testSortRandom();
        System.out.println("--------------------------------------------------");
        System.out.println("Sort Sorted Data");
        testSortSorted();
        System.out.println("--------------------------------------------------");
        System.out.println("Sort Reverse Sorted Data");
        testSorteReverseSorted();
        System.out.println("--------------------------------------------------");
        System.out.println("Search Random Data");
        testSearchRandom();
        System.out.println("--------------------------------------------------");
        System.out.println("Search Sorted Data");
        testSearchSorted();

    }

    public static void testSortRandom() {
        SortRandomData(500);
        System.out.println("--------------------------------------------------");

        SortRandomData(1000);
        System.out.println("--------------------------------------------------");

        SortRandomData(2000);
        System.out.println("--------------------------------------------------");

        SortRandomData(4000);
        System.out.println("--------------------------------------------------");

        SortRandomData(8000);
        System.out.println("--------------------------------------------------");

        SortRandomData(16000);
        System.out.println("--------------------------------------------------");

        SortRandomData(32000);
        System.out.println("--------------------------------------------------");

        SortRandomData(64000);
        System.out.println("--------------------------------------------------");

        SortRandomData(128000);
        System.out.println("--------------------------------------------------");

        SortRandomData(250000);
    }

    public static void testSortSorted() {
        SortSortedData(500);
        System.out.println("--------------------------------------------------");

        SortSortedData(1000);
        System.out.println("--------------------------------------------------");

        SortSortedData(2000);
        System.out.println("--------------------------------------------------");

        SortSortedData(4000);
        System.out.println("--------------------------------------------------");

        SortSortedData(8000);
        System.out.println("--------------------------------------------------");

        SortSortedData(16000);
        System.out.println("--------------------------------------------------");

        SortSortedData(32000);
        System.out.println("--------------------------------------------------");

        SortSortedData(64000);
        System.out.println("--------------------------------------------------");

        SortSortedData(128000);
        System.out.println("--------------------------------------------------");

        SortSortedData(250000);
    }

    public static void testSorteReverseSorted() {
        SortReverseSortedData(500);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(1000);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(2000);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(4000);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(8000);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(16000);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(32000);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(64000);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(128000);
        System.out.println("--------------------------------------------------");

        SortReverseSortedData(250000);
    }

    public static void testSearchRandom() {
        SearchRandomData(500);
        System.out.println("--------------------------------------------------");

        SearchRandomData(1000);
        System.out.println("--------------------------------------------------");

        SearchRandomData(2000);
        System.out.println("--------------------------------------------------");

        SearchRandomData(4000);
        System.out.println("--------------------------------------------------");

        SearchRandomData(8000);
        System.out.println("--------------------------------------------------");

        SearchRandomData(16000);
        System.out.println("--------------------------------------------------");

        SearchRandomData(32000);
        System.out.println("--------------------------------------------------");

        SearchRandomData(64000);
        System.out.println("--------------------------------------------------");

        SearchRandomData(128000);
        System.out.println("--------------------------------------------------");

        SearchRandomData(250000);
    }

    public static void testSearchSorted() {
        SearchSortedData(500);
        System.out.println("--------------------------------------------------");

        SearchSortedData(1000);
        System.out.println("--------------------------------------------------");

        SearchSortedData(2000);
        System.out.println("--------------------------------------------------");

        SearchSortedData(4000);
        System.out.println("--------------------------------------------------");

        SearchSortedData(8000);
        System.out.println("--------------------------------------------------");

        SearchSortedData(16000);
        System.out.println("--------------------------------------------------");

        SearchSortedData(32000);
        System.out.println("--------------------------------------------------");

        SearchSortedData(64000);
        System.out.println("--------------------------------------------------");

        SearchSortedData(128000);
        System.out.println("--------------------------------------------------");

        SearchSortedData(250000);
    }
}
