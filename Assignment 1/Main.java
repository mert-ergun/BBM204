import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.Arrays;

class Main {
    public static void main(String args[]) throws IOException {

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

        showAndSaveChart("Search Algorithms", inputAxis, yData);
    }

    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Linear Search on Random Data", doubleX, yAxis[0]);
        chart.addSeries("Linear Search on Sorted Data", doubleX, yAxis[1]);
        chart.addSeries("Binary Search on Sorted Data", doubleX, yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
