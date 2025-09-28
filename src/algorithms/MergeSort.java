package algorithms;

public class MergeSort {
    public MergeSort() {
    }

    public static void sort(int[] arr) {
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right) {
        if (right - left + 1 <= 16) {
            insertionSort(arr, left, right);
        } else {
            int mid = left + right >>> 1;
            mergeSort(arr, buffer, left, mid);
            mergeSort(arr, buffer, mid + 1, right);
            merge(arr, buffer, left, mid, right);
        }
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right) {
        System.arraycopy(arr, left, buffer, left, right - left + 1);
        int i = left;
        int j = mid + 1;
        int k = left;

        while(i <= mid && j <= right) {
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
            for (j = i - 1; j >= left && arr[j] > key; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = key;
        }

    }
}