package algorithms;
import java.util.Arrays;
import java.util.Random;

public class MergeSortTest {
    public MergeSortTest() {
    }

    public static void main(String[] args) {
        testRandomArrays();
        testAdversarialArrays();
        System.out.println("All tests passed!");
    }

    private static void testRandomArrays() {
        Random rand = new Random();

        for(int t = 0; t < 100; ++t) {
            int n = rand.nextInt(100) + 1;
            int[] arr = randomArray(n);
            int[] copy = Arrays.copyOf(arr, n);
            MergeSort.sort(arr);
            Arrays.sort(copy);
            if (!Arrays.equals(arr, copy)) {
                throw new AssertionError("Random test failed: " + Arrays.toString(arr));
            }
        }

    }

    private static void testAdversarialArrays() {
        int[][] cases = new int[][]{new int[0], {1}, {1, 2}, {2, 1}, {5, 5, 5, 5}, {9, 7, 5, 3, 1}, {1, 3, 2, 4, 6, 5}};

        for(int[] arr : cases) {
            int[] copy = Arrays.copyOf(arr, arr.length);
            MergeSort.sort(arr);
            Arrays.sort(copy);
            if (!Arrays.equals(arr, copy)) {
                throw new AssertionError("Adversarial test failed");
            }
        }

    }

    private static int[] randomArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];

        for(int i = 0; i < n; ++i) {
            arr[i] = rand.nextInt(1000);
        }

        return arr;
    }
}