package algorithms;

import java.util.Arrays;
import java.util.Random;

public class MergeSortTest {
    public static void main(String[] args) {
        testRandomArrays();
        testAdversarialArrays();
        testEdgeCases();
        testWithMetrics();
        System.out.println("All MergeSort tests passed!");
    }

    private static void testRandomArrays() {
        Random rand = new Random();
        for (int t = 0; t < 100; t++) {
            int n = rand.nextInt(200) + 1;
            int[] arr = generateRandomArray(n, rand);
            int[] copy = Arrays.copyOf(arr, n);

            Metrics metrics = new Metrics();
            MergeSort.sort(arr, metrics);

            Arrays.sort(copy);
            if (!Arrays.equals(arr, copy)) {
                throw new AssertionError("Random test failed: " + Arrays.toString(arr));
            }

            if (metrics.getComparisons() == 0) {
                throw new AssertionError("No comparisons recorded");
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
            MergeSort.sort(arr, metrics);

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
        MergeSort.sort(sorted, metrics);
        assert isSorted(sorted) : "Already sorted array failed";

        int[] reverse = {5, 4, 3, 2, 1};
        MergeSort.sort(reverse, metrics);
        assert isSorted(reverse) : "Reverse sorted array failed";

        int[] single = {42};
        MergeSort.sort(single, metrics);
        assert single[0] == 42 : "Single element array failed";

        int[] two = {2, 1};
        MergeSort.sort(two, metrics);
        assert two[0] == 1 && two[1] == 2 : "Two elements array failed";

        System.out.println("Edge cases test passed");
    }

    private static void testWithMetrics() {
        int[] arr = {5, 2, 8, 1, 9, 3};
        Metrics metrics = new Metrics();

        MergeSort.sort(arr, metrics);

        System.out.println("MergeSort Metrics:");
        System.out.println("  Comparisons: " + metrics.getComparisons());
        System.out.println("  Allocations: " + metrics.getAllocations());
        System.out.println("  Max Recursion Depth: " + metrics.getMaxRecursionDepth());
        System.out.println("  Time (ns): " + metrics.getElapsedTime());

        if (metrics.getComparisons() == 0) {
            throw new AssertionError("No comparisons recorded in metrics");
        }
        if (metrics.getAllocations() == 0) {
            throw new AssertionError("No allocations recorded in metrics");
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