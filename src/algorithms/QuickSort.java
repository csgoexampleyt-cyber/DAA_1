package algorithms;
import java.util.Random;

public class QuickSort {
    private static final Random rand = new Random();
    private static int maxDepth;

    public QuickSort() {
    }

    public static void sort(int[] arr) {
        maxDepth = 0;
        quickSort(arr, 0, arr.length - 1, 1);
    }

    public static int getMaxDepth() {
        return maxDepth;
    }

    private static void quickSort(int[] arr, int left, int right, int depth) {
        while(left < right) {
            if (depth > maxDepth) {
                maxDepth = depth;
            }

            int pivotIndex = left + rand.nextInt(right - left + 1);
            int pivot = arr[pivotIndex];
            int i = left;
            int j = right;

            while(i <= j) {
                while(arr[i] < pivot) {
                    ++i;
                }

                while(arr[j] > pivot) {
                    --j;
                }

                if (i <= j) {
                    swap(arr, i, j);
                    ++i;
                    --j;
                }
            }

            if (j - left < right - i) {
                if (left < j) {
                    quickSort(arr, left, j, depth + 1);
                }

                left = i;
            } else {
                if (i < right) {
                    quickSort(arr, i, right, depth + 1);
                }

                right = j;
            }
        }

    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}