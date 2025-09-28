package algorithms;

public class MergeSort {
    public MergeSort() {
    }

    public static void sort(int[] arr, Metrics metrics) {
        metrics.startTimer();
        metrics.incrementAllocations(arr.length);
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        if (right - left + 1 <= 16) {
            insertionSort(arr, left, right, metrics);
        } else {
            int mid = (left + right) / 2;
            mergeSort(arr, buffer, left, mid, metrics);
            mergeSort(arr, buffer, mid + 1, right, metrics);
            merge(arr, buffer, left, mid, right, metrics);
        }

        metrics.exitRecursion();
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics metrics) {
        metrics.incrementAllocations(right - left + 1);
        System.arraycopy(arr, left, buffer, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while(i <= mid && j <= right) {
            metrics.incrementComparisons();
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

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for(int i = left + 1; i <= right; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= left) {
                metrics.incrementComparisons();
                if (arr[j] <= key) break;

                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void sort(int[] arr) {
        Metrics metrics = new Metrics();
        sort(arr, metrics);
    }
}