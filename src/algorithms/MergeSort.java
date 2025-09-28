package algorithms;

public class MergeSort {
    private static Metrics metrics;

    public MergeSort() {
    }

    public static void sort(int[] arr, Metrics m) {
        metrics = m;
        metrics.startTimer();
        metrics.incrementAllocations(arr.length); // for buffer array
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1);
        metrics.stopTimer();
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right) {
        metrics.enterRecursion();

        if (right - left + 1 <= 16) {
            insertionSort(arr, left, right);
        } else {
            int mid = left + right >>> 1;
            mergeSort(arr, buffer, left, mid);
            mergeSort(arr, buffer, mid + 1, right);
            merge(arr, buffer, left, mid, right);
        }

        metrics.exitRecursion();
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right) {
        metrics.incrementAllocations(right - left + 1); // for arraycopy
        System.arraycopy(arr, left, buffer, left, right - left + 1);
        int i = left;
        int j = mid + 1;
        int k = left;

        while(i <= mid && j <= right) {
            metrics.incrementComparisons(); // buffer[i] <= buffer[j] comparison
            if (buffer[i] <= buffer[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
            }
        }

        while(i <= mid) {
            arr[k++] = buffer[i++];
        }
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for(int i = left + 1; i <= right; ++i) {
            int key = arr[i];
            int j;

            for (j = i - 1; j >= left; j--) {
                metrics.incrementComparisons(); // arr[j] > key comparison
                if (arr[j] <= key) break;

                arr[j + 1] = arr[j];
            }
            arr[j + 1] = key;
        }
    }
}