package algorithms;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class AlgorithmBenchmark {
    public static void main(String[] args) {
        try {
            CSVWriter csvWriter = new CSVWriter("algorithm_metrics.csv");

            csvWriter.addRow("Algorithm", "InputSize", "Comparisons", "Allocations",
                    "MaxRecursionDepth", "TimeNanos", "Correct", "AdditionalInfo");

            int[] sizes = {100, 500, 1000, 5000, 10000};
            Random rand = new Random();

            for (int size : sizes) {
                System.out.println("Testing with size: " + size);

                int[] arr = generateRandomArray(size, rand);
                ClosestPair.Point[] points = generateRandomPoints(size, rand);

                // Test MergeSort
                testMergeSort(csvWriter, arr, size);

                // Test QuickSort
                testQuickSort(csvWriter, arr, size);

                // Test DeterministicSelect
                testDeterministicSelect(csvWriter, arr, size, rand);

                // Test ClosestPair
                testClosestPair(csvWriter, points, size);
            }

            csvWriter.writeToFile();
            System.out.println("Metrics written to algorithm_metrics.csv");

        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    private static void testMergeSort(CSVWriter csvWriter, int[] original, int size) {
        Metrics metrics = new Metrics();
        int[] arr = original.clone();

        MergeSort.sort(arr, metrics);

        boolean isSorted = isSorted(arr);
        boolean matchesJava = verifyWithJavaSort(original, arr);
        boolean correct = isSorted && matchesJava;

        csvWriter.addRow("MergeSort", String.valueOf(size),
                String.valueOf(metrics.getComparisons()),
                String.valueOf(metrics.getAllocations()),
                String.valueOf(metrics.getMaxRecursionDepth()),
                String.valueOf(metrics.getElapsedTime()),
                correct ? "YES" : "NO",
                "Comparisons=" + metrics.getComparisons());
    }

    private static void testQuickSort(CSVWriter csvWriter, int[] original, int size) {
        Metrics metrics = new Metrics();
        int[] arr = original.clone();

        QuickSort.sort(arr, metrics);

        boolean isSorted = isSorted(arr);
        boolean matchesJava = verifyWithJavaSort(original, arr);
        boolean correct = isSorted && matchesJava;

        csvWriter.addRow("QuickSort", String.valueOf(size),
                String.valueOf(metrics.getComparisons()),
                String.valueOf(metrics.getAllocations()),
                String.valueOf(metrics.getMaxRecursionDepth()),
                String.valueOf(metrics.getElapsedTime()),
                correct ? "YES" : "NO",
                "Comparisons=" + metrics.getComparisons());
    }

    private static void testDeterministicSelect(CSVWriter csvWriter, int[] original, int size, Random rand) {
        Metrics metrics = new Metrics();
        int[] arr = original.clone();
        int k = rand.nextInt(size) + 1;

        int result = DeterministicSelect.select(arr, k, metrics);

        int[] verificationArr = original.clone();
        Arrays.sort(verificationArr);
        int expected = verificationArr[k - 1];
        boolean correct = (result == expected);

        csvWriter.addRow("DeterministicSelect", String.valueOf(size),
                String.valueOf(metrics.getComparisons()),
                String.valueOf(metrics.getAllocations()),
                String.valueOf(metrics.getMaxRecursionDepth()),
                String.valueOf(metrics.getElapsedTime()),
                correct ? "YES" : "NO",
                "k=" + k + ", result=" + result + ", expected=" + expected);
    }

    private static void testClosestPair(CSVWriter csvWriter, ClosestPair.Point[] points, int size) {
        Metrics metrics = new Metrics();

        double result = ClosestPair.closestPair(points, metrics);

        boolean correct = true;
        if (size <= 1000) {
            double bruteForceResult = bruteForceClosestPair(points);
            correct = Math.abs(result - bruteForceResult) < 1e-9;
        }

        csvWriter.addRow("ClosestPair", String.valueOf(size),
                String.valueOf(metrics.getComparisons()),
                String.valueOf(metrics.getAllocations()),
                String.valueOf(metrics.getMaxRecursionDepth()),
                String.valueOf(metrics.getElapsedTime()),
                correct ? "YES" : "NO",
                "distance=" + result);
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private static boolean verifyWithJavaSort(int[] original, int[] sorted) {
        int[] javaSorted = original.clone();
        Arrays.sort(javaSorted);
        return Arrays.equals(sorted, javaSorted);
    }

    private static double bruteForceClosestPair(ClosestPair.Point[] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = Math.hypot(points[i].x - points[j].x, points[i].y - points[j].y);
                min = Math.min(min, dist);
            }
        }
        return min;
    }

    private static int[] generateRandomArray(int size, Random rand) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10000);
        }
        return arr;
    }

    private static ClosestPair.Point[] generateRandomPoints(int size, Random rand) {
        ClosestPair.Point[] points = new ClosestPair.Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }
        return points;
    }
}