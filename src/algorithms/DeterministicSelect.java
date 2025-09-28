package algorithms;
import java.util.Arrays;

public class DeterministicSelect {
    public DeterministicSelect() {
    }

    public static int select(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k);
    }

    private static int select(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        } else {
            int pivot = medianOfMedians(arr, left, right);
            int pivotIndex = partition(arr, left, right, pivot);
            int rank = pivotIndex - left + 1;
            if (k == rank) {
                return arr[pivotIndex];
            } else {
                return k < rank ? select(arr, left, pivotIndex - 1, k) : select(arr, pivotIndex + 1, right, k - rank);
            }
        }
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n < 5) {
            Arrays.sort(arr, left, right + 1);
            return arr[left + n / 2];
        } else {
            int numMedians = (int) Math.ceil((double) n / (double) 5.0F);
            int[] medians = new int[numMedians];

            for (int i = 0; i < numMedians; ++i) {
                int subLeft = left + i * 5;
                int subRight = Math.min(subLeft + 4, right);
                Arrays.sort(arr, subLeft, subRight + 1);
                medians[i] = arr[subLeft + (subRight - subLeft) / 2];
            }

            return medianOfMedians(medians, 0, medians.length - 1);
        }
    }

    private static int partition(int[] arr, int left, int right, int pivot) {
        int pivotIndex = left;

        for (int i = left; i <= right; ++i) {
            if (arr[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }

        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; ++i) {
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
}