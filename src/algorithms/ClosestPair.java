package algorithms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPair {
    public ClosestPair() {
    }

    public static double closestPair(Point[] points) {
        Arrays.sort(points, Comparator.comparingDouble((p) -> p.x));
        return closestUtil(points, 0, points.length - 1);
    }

    private static double closestUtil(Point[] points, int left, int right) {
        if (right - left <= 3) {
            return bruteForce(points, left, right);
        } else {
            int mid = (left + right) / 2;
            double midX = points[mid].x;
            double dLeft = closestUtil(points, left, mid);
            double dRight = closestUtil(points, mid + 1, right);
            double d = Math.min(dLeft, dRight);
            List<Point> strip = new ArrayList<>();

            for(int i = left; i <= right; ++i) {
                if (Math.abs(points[i].x - midX) < d) {
                    strip.add(points[i]);
                }
            }

            strip.sort(Comparator.comparingDouble((p) -> p.y));
            return Math.min(d, stripClosest(strip, d));
        }
    }

    private static double bruteForce(Point[] points, int left, int right) {
        double min = Double.MAX_VALUE;

        for(int i = left; i <= right; ++i) {
            for(int j = i + 1; j <= right; ++j) {
                min = Math.min(min, dist(points[i], points[j]));
            }
        }

        return min;
    }

    private static double stripClosest(List<Point> strip, double d) {
        double min = d;

        for (int i = 0; i < strip.size(); ++i) {
            for (int j = i + 1; j < strip.size() && strip.get(j).y - strip.get(i).y < min; ++j) {
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
}