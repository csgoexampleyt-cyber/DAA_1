package algorithms;

import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {
    public static void main(String[] args) {
        testRandomArrays();
        testAdversarialArrays();
        testEdgeCases();
        testWithMetrics();
        System.out.println("All QuickSort tests passed!");
    }

    private static void testRandomArrays() {
        Random rand = new Random();
        for (int t = 0; t < 100; t++) {
            int n = rand.nextInt(200) + 1;
            int[] arr = generateRandomArray(n, rand);
            int[] copy = Arrays.copyOf(arr, n);

            Metrics metrics = new Metrics();
            QuickSort.sort(arr, metrics);

            Arrays.sort(copy);
            if (!Arrays.equals(arr, copy)) {
                throw new AssertionError("Random test failed: " + Arrays.toString(arr));
            }

            int maxAllowedDepth = 2 * (int)(Math.log(n) / Math.log(2)) + 10;
            if (metrics.getMaxRecursionDepth() > maxAllowedDepth) {
                throw new AssertionError("Recursion depth too high: " + metrics.getMaxRecursionDepth());
            }
        }
        System.out.println("Random arrays test passed - 100 tests");
    }

    private static void testAdversarialArrays() {
        int[][] testCases = {
                {},
                {1},
                {1, 2},
                {2, 1},
                {1, 2, 3},
                {3, 2, 1},
                {5, 5, 5, 5},
                {9, 7, 5, 3, 1},
                {1, 3, 2, 4, 6, 5},
                {1, 1, 1, 2, 2, 2}
        };

        for (int[] arr : testCases) {
            int[] copy = Arrays.copyOf(arr, arr.length);

            Metrics metrics = new Metrics();
            QuickSort.sort(arr, metrics);

            Arrays.sort(copy);
            if (!Arrays.equals(arr, copy)) {
                throw new AssertionError("Adversarial test failed for: " + Arrays.toString(copy));
            }
        }
        System.out.println("Adversarial arrays test passed");
    }

    private static void testEdgeCases() {
        int[] sorted = {1, 2, 3, 4, 5};
        Metrics metrics = new Metrics();
        QuickSort.sort(sorted, metrics);
        assert isSorted(sorted) : "Already sorted array failed";

        int[] reverse = {5, 4, 3, 2, 1};
        QuickSort.sort(reverse, metrics);
        assert isSorted(reverse) : "Reverse sorted array failed";

        int[] single = {42};
        QuickSort.sort(single, metrics);
        assert single[0] == 42 : "Single element array failed";

        int[] two = {2, 1};
        QuickSort.sort(two, metrics);
        assert two[0] == 1 && two[1] == 2 : "Two elements array failed";

        int[] equal = {7, 7, 7, 7};
        QuickSort.sort(equal, metrics);
        assert equal[0] == 7 && equal[3] == 7 : "All equal array failed";

        System.out.println("Edge cases test passed");
    }

    private static void testWithMetrics() {
        int[] arr = {5, 2, 8, 1, 9, 3};
        Metrics metrics = new Metrics();

        QuickSort.sort(arr, metrics);

        System.out.println("QuickSort Metrics:");
        System.out.println("  Comparisons: " + metrics.getComparisons());
        System.out.println("  Allocations: " + metrics.getAllocations());
        System.out.println("  Max Recursion Depth: " + metrics.getMaxRecursionDepth());
        System.out.println("  Time (ns): " + metrics.getElapsedTime());

        if (metrics.getComparisons() == 0) {
            throw new AssertionError("No comparisons recorded in metrics");
        }

        System.out.println("Metrics test passed");
    }

    private static int[] generateRandomArray(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1000);
        }
        return arr;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }
}