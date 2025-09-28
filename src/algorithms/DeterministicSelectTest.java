package algorithms;

import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    public static void main(String[] args) {
        testRandomArrays();
        testAdversarialArrays();
        testEdgeCases();
        testWithMetrics();
        System.out.println("All DeterministicSelect tests passed!");
    }

    private static void testRandomArrays() {
        Random rand = new Random();
        boolean allCorrect = true;

        for (int t = 0; t < 100; t++) {
            int n = 200 + rand.nextInt(300);
            int[] arr = generateRandomArray(n, rand);
            int[] copy = Arrays.copyOf(arr, n);
            int k = rand.nextInt(n) + 1;

            Metrics metrics = new Metrics();
            int result = DeterministicSelect.select(arr, k, metrics);

            Arrays.sort(copy);
            int expected = copy[k - 1];
            if (result != expected) {
                System.out.println("Mismatch! Got " + result + " expected " + expected);
                allCorrect = false;
                break;
            }
        }

        if (!allCorrect) {
            throw new AssertionError("Random arrays test failed");
        }
        System.out.println("Random arrays test passed - 100 tests");
    }

    private static void testAdversarialArrays() {
        int[][] testCases = {
                {1},
                {1, 2},
                {2, 1},
                {3, 1, 2},
                {5, 5, 5, 5},
                {9, 7, 5, 3, 1},
                {1, 3, 2, 4, 6, 5}
        };

        int[] ks = {1, 2, 1, 2, 3, 3, 4};

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];
            int k = ks[i];
            int[] copy = Arrays.copyOf(arr, arr.length);

            Metrics metrics = new Metrics();
            int result = DeterministicSelect.select(arr, k, metrics);

            Arrays.sort(copy);
            int expected = copy[k - 1];
            if (result != expected) {
                throw new AssertionError("Adversarial test failed: got " + result + " expected " + expected);
            }
        }
        System.out.println("Adversarial arrays test passed");
    }

    private static void testEdgeCases() {
        int[] single = {42};
        Metrics metrics = new Metrics();
        int result = DeterministicSelect.select(single, 1, metrics);
        assert result == 42 : "Single element failed: " + result;

        int[] two1 = {2, 1};
        result = DeterministicSelect.select(two1, 1, metrics);
        assert result == 1 : "Two elements first failed: " + result;

        int[] two2 = {2, 1};
        result = DeterministicSelect.select(two2, 2, metrics);
        assert result == 2 : "Two elements second failed: " + result;

        int[] equal = {5, 5, 5, 5};
        result = DeterministicSelect.select(equal, 3, metrics);
        assert result == 5 : "All equal failed: " + result;

        int[] sorted = {1, 2, 3, 4, 5};
        result = DeterministicSelect.select(sorted, 3, metrics);
        assert result == 3 : "Already sorted failed: " + result;

        System.out.println("Edge cases test passed");
    }

    private static void testWithMetrics() {
        int[] arr = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        Metrics metrics = new Metrics();

        int result = DeterministicSelect.select(arr, 5, metrics);

        System.out.println("DeterministicSelect Metrics:");
        System.out.println("  Comparisons: " + metrics.getComparisons());
        System.out.println("  Allocations: " + metrics.getAllocations());
        System.out.println("  Max Recursion Depth: " + metrics.getMaxRecursionDepth());
        System.out.println("  Time (ns): " + metrics.getElapsedTime());
        System.out.println("  5th smallest element: " + result);

        if (metrics.getComparisons() == 0) {
            throw new AssertionError("No comparisons recorded in metrics");
        }
        if (metrics.getAllocations() == 0) {
            throw new AssertionError("No allocations recorded in metrics");
        }

        int[] copy = arr.clone();
        Arrays.sort(copy);
        int expected = copy[4];
        if (result != expected) {
            throw new AssertionError("Incorrect result: " + result + " expected: " + expected);
        }

        System.out.println("Metrics test passed");
    }

    private static int[] generateRandomArray(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(10000);
        }
        return arr;
    }
}