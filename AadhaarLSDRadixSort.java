import java.util.Arrays;

public class AadhaarLSDRadixSort {

    // LSD Radix Sort
    static int[] radixSort(int[] arr, int d) {
        int[] current = arr.clone();
        int divisor = 1;

        for (int pass = 0; pass < d; pass++) {
            current = countingSortByDigit(current, divisor);

            System.out.println("Pass " + (pass + 1) +
                    " (divisor = " + divisor + "):");
            System.out.println(Arrays.toString(current));
            System.out.println();

            divisor *= 10;
        }

        return current;
    }

    // Stable Counting Sort by Digit
    static int[] countingSortByDigit(int[] in, int divisor) {

        int[] out = new int[in.length];
        int[] count = new int[10];

        // TODO 1: Count occurrences
        for (int num : in) {
            int digit = digitAt(num, divisor);
            count[digit]++;
        }

        // TODO 2: Convert to prefix sums
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // TODO 3: Place elements in reverse order
        for (int i = in.length - 1; i >= 0; i--) {

            int digit = digitAt(in[i], divisor);

            out[count[digit] - 1] = in[i];

            count[digit]--;
        }

        return out;
    }

    static int digitAt(int n, int divisor) {
        return (n / divisor) % 10;
    }

    public static void main(String[] args) {

        int[] arr = {
                473, 152, 681, 247,
                539, 826, 715, 304
        };

        System.out.println("Input Array:");
        System.out.println(Arrays.toString(arr));
        System.out.println();

        int[] sorted = radixSort(arr, 3);

        System.out.println("Final Sorted Array:");
        System.out.println(Arrays.toString(sorted));
    }
}
