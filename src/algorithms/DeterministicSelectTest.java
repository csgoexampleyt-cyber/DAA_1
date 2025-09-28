package algorithms;
import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    public DeterministicSelectTest() {
    }

    public static void main(String[] args) {
        Random rand = new Random();
        boolean allCorrect = true;

        for(int t = 0; t < 100; ++t) {
            int n = 200 + rand.nextInt(300);
            int[] arr = rand.ints((long)n, -10000, 10000).toArray();
            int[] copy = Arrays.copyOf(arr, n);
            int k = rand.nextInt(n) + 1;
            int result = DeterministicSelect.select(arr, k);
            Arrays.sort(copy);
            int expected = copy[k - 1];
            if (result != expected) {
                System.out.println("Mismatch! Got " + result + " expected " + expected);
                allCorrect = false;
                break;
            }
        }

        System.out.println("All 100 trials passed? " + allCorrect);
    }
}
