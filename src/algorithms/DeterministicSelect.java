package algorithms;
import java.util.Arrays;

public class DeterministicSelect {
    public DeterministicSelect() {
    }

    public static int select(int[] arr, int k, Metrics metrics) {
        metrics.startTimer();
        int result = select(arr, 0, arr.length - 1, k, metrics);
        metrics.stopTimer();
        return result;
    }

    private static int select(int[] arr, int left, int right, int k, Metrics metrics) {
        metrics.enterRecursion();

        int result;
        if (left == right) {
            result = arr[left];
        } else {
            int pivot = medianOfMedians(arr, left, right, metrics);
            int pivotIndex = partition(arr, left, right, pivot, metrics);
            int rank = pivotIndex - left + 1;

            metrics.incrementComparisons();
            if (k == rank) {
                result = arr[pivotIndex];
            } else {
                metrics.incrementComparisons();
                result = k < rank ? select(arr, left, pivotIndex - 1, k, metrics) : select(arr, pivotIndex + 1, right, k - rank, metrics);
            }
        }

        metrics.exitRecursion();
        return result;
    }

    private static int medianOfMedians(int[] arr, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        int n = right - left + 1;
        int result;
        if (n < 5) {
            metrics.incrementAllocations(1);
            Arrays.sort(arr, left, right + 1);
            result = arr[left + n / 2];
        } else {
            int numMedians = (n + 4) / 5;
            metrics.incrementAllocations(numMedians);
            int[] medians = new int[numMedians];

            for (int i = 0; i < numMedians; ++i) {
                int subLeft = left + i * 5;
                int subRight = Math.min(subLeft + 4, right);
                metrics.incrementAllocations(1);
                Arrays.sort(arr, subLeft, subRight + 1);
                medians[i] = arr[subLeft + (subRight - subLeft) / 2];
            }

            result = medianOfMedians(medians, 0, medians.length - 1, metrics);
        }

        metrics.exitRecursion();
        return result;
    }

    private static int partition(int[] arr, int left, int right, int pivot, Metrics metrics) {
        int pivotIndex = left;

        for (int i = left; i <= right; ++i) {
            metrics.incrementComparisons();
            if (arr[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }

        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; ++i) {
            metrics.incrementComparisons();
            if (arr[i] < pivot) {
                swap(arr, storeIndex, i);
                ++storeIndex;
            }
        }

        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int select(int[] arr, int k) {
        Metrics metrics = new Metrics();
        return select(arr, k, metrics);
    }
}