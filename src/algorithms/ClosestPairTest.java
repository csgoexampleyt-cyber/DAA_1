package algorithms;
import java.util.Random;

public class ClosestPairTest {
    public ClosestPairTest() {
    }

    private static double bruteForce(ClosestPair.Point[] points) {
        double min = Double.MAX_VALUE;

        for(int i = 0; i < points.length; ++i) {
            for(int j = i + 1; j < points.length; ++j) {
                min = Math.min(min, Math.hypot(points[i].x - points[j].x, points[i].y - points[j].y));
            }
        }

        return min;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        boolean allCorrect = true;

        for(int t = 0; t < 20; ++t) {
            int n = rand.nextInt(1000) + 50;
            ClosestPair.Point[] pts = new ClosestPair.Point[n];

            for(int i = 0; i < n; ++i) {
                pts[i] = new ClosestPair.Point(rand.nextDouble() * (double)1000.0F, rand.nextDouble() * (double)1000.0F);
            }

            double fast = ClosestPair.closestPair(pts);
            double slow = bruteForce(pts);
            if (Math.abs(fast - slow) > 1.0E-9) {
                System.out.println("Mismatch! Fast=" + fast + " Slow=" + slow);
                allCorrect = false;
                break;
            }
        }

        System.out.println("Small n tests passed? " + allCorrect);
        int n = 100000;
        ClosestPair.Point[] bigPts = new ClosestPair.Point[n];

        for(int i = 0; i < n; ++i) {
            bigPts[i] = new ClosestPair.Point(rand.nextDouble() * (double)100000.0F, rand.nextDouble() * (double)100000.0F);
        }

        long start = System.currentTimeMillis();
        double result = ClosestPair.closestPair(bigPts);
        long end = System.currentTimeMillis();
        System.out.println("Large n (" + n + ") test finished in " + (end - start) + " ms, result=" + result);
    }
}
