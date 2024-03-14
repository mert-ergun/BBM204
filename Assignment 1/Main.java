import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.Arrays;

class Main {
    public static void main(String args[]) throws IOException {

        Experiments.warmup();

        sortRandom();
        sortSorted();
        sortReverse();
        searchAll();
    }

    public static void sortRandom() {
        int[] inputAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        double[] insertionSort = new double[inputAxis.length];
        double[] mergeSort = new double[inputAxis.length];
        double[] countingSort = new double[inputAxis.length];
        double[][] allSorts = new double[inputAxis.length][3];
        int i = 0;

        for (int inputs: inputAxis) {
            allSorts[i] = Experiments.SortRandomData(inputs);
            insertionSort[i] = allSorts[i][0];
            mergeSort[i] = allSorts[i][1];
            countingSort[i] = allSorts[i][2];
            i++;
        }

        double[][] yData = new double[][] {insertionSort, mergeSort, countingSort};

        String[] labels = {"Insertion Sort", "Merge Sort", "Counting Sort"};

        try {
            showAndSaveChart("Sort Algorithms on Random Data", inputAxis, yData, labels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sortSorted() {
        int[] inputAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        double[] insertionSort = new double[inputAxis.length];
        double[] mergeSort = new double[inputAxis.length];
        double[] countingSort = new double[inputAxis.length];
        double[][] allSorts = new double[inputAxis.length][3];
        int i = 0;

        for (int inputs: inputAxis) {
            allSorts[i] = Experiments.SortSortedData(inputs);
            insertionSort[i] = allSorts[i][0];
            mergeSort[i] = allSorts[i][1];
            countingSort[i] = allSorts[i][2];
            i++;
        }

        double[][] yData = new double[][] {insertionSort, mergeSort, countingSort};

        String[] labels = {"Insertion Sort", "Merge Sort", "Counting Sort"};

        try {
            showAndSaveChart("Sort Algorithms on Sorted Data", inputAxis, yData, labels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sortReverse() {
        int[] inputAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        double[] insertionSort = new double[inputAxis.length];
        double[] mergeSort = new double[inputAxis.length];
        double[] countingSort = new double[inputAxis.length];
        double[][] allSorts = new double[inputAxis.length][3];
        int i = 0;

        for (int inputs: inputAxis) {
            allSorts[i] = Experiments.SortReverseSortedData(inputs);
            insertionSort[i] = allSorts[i][0];
            mergeSort[i] = allSorts[i][1];
            countingSort[i] = allSorts[i][2];
            i++;
        }

        double[][] yData = new double[][] {insertionSort, mergeSort, countingSort};

        String[] labels = {"Insertion Sort", "Merge Sort", "Counting Sort"};

        try {
            showAndSaveChart("Sort Algorithms on Reverse Data", inputAxis, yData, labels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchAll() {
        // X axis data
        int[] inputAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        double[] linearSearchRandom = new double[inputAxis.length];

        for (int i = 0; i < inputAxis.length; i++) {
            int size = inputAxis[i];
            linearSearchRandom[i] = Experiments.SearchRandomData(size);
        }

        double[] searchTimes = new double[2];
        double[] linearSearchSorted = new double[inputAxis.length];
        double[] binarySearchSorted = new double[inputAxis.length];

        for (int i = 0; i < inputAxis.length; i++) {
            int size = inputAxis[i];
            searchTimes = Experiments.SearchSortedData(size);
            linearSearchSorted[i] = searchTimes[0];
            binarySearchSorted[i] = searchTimes[1];
        }

        double[][] yData = new double[][] {linearSearchRandom, linearSearchSorted, binarySearchSorted};
        String[] labels = {"Linear Search Random", "Linear Search Sorted", "Binary Search Sorted"};

        try {
            showAndSaveChart("Search Algorithms", inputAxis, yData, labels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis, String[] labels) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries(labels[0], doubleX, yAxis[0]);
        chart.addSeries(labels[1], doubleX, yAxis[1]);
        chart.addSeries(labels[2], doubleX, yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
