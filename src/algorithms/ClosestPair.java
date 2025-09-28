package algorithms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPair {
    public ClosestPair() {
    }

    public static double closestPair(Point[] points, Metrics metrics) {
        metrics.startTimer();
        metrics.incrementAllocations(1);

        Arrays.sort(points, Comparator.comparingDouble((p) -> p.x));
        double result = closestUtil(points, 0, points.length - 1, metrics);
        metrics.stopTimer();
        return result;
    }

    private static double closestUtil(Point[] points, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        double result;
        if (right - left <= 3) {
            result = bruteForce(points, left, right, metrics);
        } else {
            int mid = (left + right) / 2;
            double midX = points[mid].x;

            double dLeft = closestUtil(points, left, mid, metrics);
            double dRight = closestUtil(points, mid + 1, right, metrics);
            double d = Math.min(dLeft, dRight);

            metrics.incrementAllocations(1);
            List<Point> strip = new ArrayList<>();

            for(int i = left; i <= right; ++i) {
                metrics.incrementComparisons();
                if (Math.abs(points[i].x - midX) < d) {
                    strip.add(points[i]);
                }
            }

            metrics.incrementAllocations(1);
            strip.sort(Comparator.comparingDouble((p) -> p.y));
            result = Math.min(d, stripClosest(strip, d, metrics));
        }

        metrics.exitRecursion();
        return result;
    }

    private static double bruteForce(Point[] points, int left, int right, Metrics metrics) {
        double min = Double.MAX_VALUE;

        for(int i = left; i <= right; ++i) {
            for(int j = i + 1; j <= right; ++j) {
                metrics.incrementComparisons();
                min = Math.min(min, dist(points[i], points[j]));
            }
        }

        return min;
    }

    private static double stripClosest(List<Point> strip, double d, Metrics metrics) {
        double min = d;

        for (int i = 0; i < strip.size(); ++i) {
            for (int j = i + 1; j < strip.size(); ++j) {
                metrics.incrementComparisons();
                if (strip.get(j).y - strip.get(i).y >= min) break;

                metrics.incrementComparisons();
                min = Math.min(min, dist(strip.get(i), strip.get(j)));
            }
        }

        return min;
    }

    private static double dist(Point a, Point b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double closestPair(Point[] points) {
        Metrics metrics = new Metrics();
        return closestPair(points, metrics);
    }
}