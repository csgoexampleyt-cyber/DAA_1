package algorithms;

import java.util.Random;

public class ClosestPairTest {
    public static void main(String[] args) {
        testRandomPoints();
        testAdversarialPoints();
        testEdgeCases();
        testWithMetrics();
        testPerformance();
        System.out.println("All ClosestPair tests passed!");
    }

    private static void testRandomPoints() {
        Random rand = new Random();
        boolean allCorrect = true;

        for (int t = 0; t < 20; t++) {
            int n = rand.nextInt(1000) + 50;
            ClosestPair.Point[] points = generateRandomPoints(n, rand);

            Metrics metrics = new Metrics();
            double result = ClosestPair.closestPair(points, metrics);
            double expected = bruteForceClosestPair(points);

            if (Math.abs(result - expected) > 1e-9) {
                System.out.println("Mismatch! Fast=" + result + " Expected=" + expected);
                allCorrect = false;
                break;
            }
        }

        if (!allCorrect) {
            throw new AssertionError("Random points test failed");
        }
        System.out.println("Random points test passed - 20 tests");
    }

    private static void testAdversarialPoints() {
        ClosestPair.Point[] line = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(1, 1),
                new ClosestPair.Point(2, 2),
                new ClosestPair.Point(3, 3)
        };

        Metrics metrics = new Metrics();
        double result = ClosestPair.closestPair(line, metrics);
        double expected = bruteForceClosestPair(line);

        if (Math.abs(result - expected) > 1e-9) {
            throw new AssertionError("Line points test failed: " + result + " vs " + expected);
        }

        ClosestPair.Point[] sameX = {
                new ClosestPair.Point(5, 1),
                new ClosestPair.Point(5, 3),
                new ClosestPair.Point(5, 2),
                new ClosestPair.Point(5, 4)
        };

        result = ClosestPair.closestPair(sameX, metrics);
        expected = bruteForceClosestPair(sameX);

        if (Math.abs(result - expected) > 1e-9) {
            throw new AssertionError("Same X points test failed: " + result + " vs " + expected);
        }

        System.out.println("Adversarial points test passed");
    }

    private static void testEdgeCases() {
        ClosestPair.Point[] two = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4)
        };
        Metrics metrics = new Metrics();
        double result = ClosestPair.closestPair(two, metrics);
        assert Math.abs(result - 5.0) < 1e-9 : "Two points failed: " + result;

        ClosestPair.Point[] three = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(1, 1),
                new ClosestPair.Point(2, 2)
        };
        result = ClosestPair.closestPair(three, metrics);
        double expected = bruteForceClosestPair(three);
        assert Math.abs(result - expected) < 1e-9 : "Three points failed: " + result;

        ClosestPair.Point[] square = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(0, 1),
                new ClosestPair.Point(1, 0),
                new ClosestPair.Point(1, 1)
        };
        result = ClosestPair.closestPair(square, metrics);
        expected = bruteForceClosestPair(square);
        assert Math.abs(result - expected) < 1e-9 : "Square points failed: " + result;

        System.out.println("Edge cases test passed");
    }

    private static void testWithMetrics() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(1, 2),
                new ClosestPair.Point(3, 4),
                new ClosestPair.Point(5, 6),
                new ClosestPair.Point(7, 8),
                new ClosestPair.Point(2, 1)
        };

        Metrics metrics = new Metrics();
        double result = ClosestPair.closestPair(points, metrics);
        double expected = bruteForceClosestPair(points);

        System.out.println("ClosestPair Metrics:");
        System.out.println("  Comparisons: " + metrics.getComparisons());
        System.out.println("  Allocations: " + metrics.getAllocations());
        System.out.println("  Max Recursion Depth: " + metrics.getMaxRecursionDepth());
        System.out.println("  Time (ns): " + metrics.getElapsedTime());
        System.out.println("  Closest distance: " + result);

        if (Math.abs(result - expected) > 1e-9) {
            throw new AssertionError("Incorrect result: " + result + " expected: " + expected);
        }
        if (metrics.getComparisons() == 0) {
            throw new AssertionError("No comparisons recorded in metrics");
        }

        System.out.println("Metrics test passed");
    }

    private static void testPerformance() {
        int n = 10000;
        Random rand = new Random();
        ClosestPair.Point[] bigPoints = generateRandomPoints(n, rand);

        long start = System.currentTimeMillis();
        Metrics metrics = new Metrics();
        double result = ClosestPair.closestPair(bigPoints, metrics);
        long end = System.currentTimeMillis();

        System.out.println("Performance test:");
        System.out.println("  Points: " + n);
        System.out.println("  Time: " + (end - start) + " ms");
        System.out.println("  Distance: " + result);
        System.out.println("  Comparisons: " + metrics.getComparisons());
        System.out.println("  Recursion Depth: " + metrics.getMaxRecursionDepth());

        if (end - start > 10000) {
            System.out.println("Warning: Performance might be too slow");
        }
    }

    private static double bruteForceClosestPair(ClosestPair.Point[] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = Math.hypot(points[i].x - points[j].x, points[i].y - points[j].y);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    private static ClosestPair.Point[] generateRandomPoints(int size, Random rand) {
        ClosestPair.Point[] points = new ClosestPair.Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }
        return points;
    }
}