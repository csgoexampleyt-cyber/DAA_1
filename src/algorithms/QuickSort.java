package algorithms;
import java.util.Random;

public class QuickSort {
    private static final Random rand = new Random();

    public QuickSort() {
    }

    public static void sort(int[] arr, Metrics metrics) {
        metrics.startTimer();
        quickSort(arr, 0, arr.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void quickSort(int[] arr, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        while(left < right) {
            int pivotIndex = left + rand.nextInt(right - left + 1);
            int pivot = arr[pivotIndex];
            int i = left;
            int j = right;

            while(i <= j) {
                while(i <= right) {
                    metrics.incrementComparisons();
                    if (arr[i] >= pivot) break;
                    ++i;
                }

                while(j >= left) {
                    metrics.incrementComparisons();
                    if (arr[j] <= pivot) break;
                    --j;
                }

                if (i <= j) {
                    swap(arr, i, j);
                    ++i;
                    --j;
                }
            }

            metrics.incrementComparisons();
            if (j - left < right - i) {
                if (left < j) {
                    quickSort(arr, left, j, metrics);
                }
                left = i;
            } else {
                if (i < right) {
                    quickSort(arr, i, right, metrics);
                }
                right = j;
            }
        }

        metrics.exitRecursion();
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void sort(int[] arr) {
        Metrics metrics = new Metrics();
        sort(arr, metrics);
    }
}