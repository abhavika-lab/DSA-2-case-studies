import java.util.*;

public class SubsetSumDP {

    // 2D Dynamic Programming Solution
    static boolean subsetSum(int[] items, int target) {

        int n = items.length;
        boolean[][] dp = new boolean[n + 1][target + 1];

        // Empty subset can always form sum 0
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        // Fill DP table
        for (int i = 1; i <= n; i++) {

            for (int s = 1; s <= target; s++) {

                // Exclude current item
                dp[i][s] = dp[i - 1][s];

                // Include current item if possible
                if (items[i - 1] <= s) {
                    dp[i][s] =
                            dp[i][s] ||
                            dp[i - 1][s - items[i - 1]];
                }
            }
        }

        // Print one valid subset if exists
        if (dp[n][target]) {

            System.out.println("\nOne Valid Subset:");

            int i = n;
            int s = target;

            ArrayList<Integer> subset = new ArrayList<>();

            while (i > 0 && s > 0) {

                if (dp[i - 1][s]) {
                    i--;
                } else {
                    subset.add(items[i - 1]);
                    s -= items[i - 1];
                    i--;
                }
            }

            Collections.reverse(subset);

            int sum = 0;
            for (int num : subset) {
                System.out.print(num + " ");
                sum += num;
            }

            System.out.println("\nSubset Sum = " + sum);
        }

        return dp[n][target];
    }

    // Space Optimized DP
    static boolean subsetSum1D(int[] items, int target) {

        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int item : items) {

            for (int s = target; s >= item; s--) {
                dp[s] = dp[s] || dp[s - item];
            }
        }

        return dp[target];
    }

    public static void main(String[] args) {

        int[] deductions = {
                50000,
                30000,
                25000,
                15000,
                12000,
                8000,
                5000
        };

        int target = 100000;

        System.out.println("Income Tax Deduction Optimization");
        System.out.println("--------------------------------");

        System.out.print("Available Deductions: ");

        for (int x : deductions) {
            System.out.print(x + " ");
        }

        System.out.println("\nTarget Deduction: " + target);

        boolean result = subsetSum(deductions, target);

        if (result) {
            System.out.println("\nA valid subset exists that sums to ₹100000.");
        } else {
            System.out.println("\nNo valid subset exists.");
        }

        boolean result1D = subsetSum1D(deductions, target);

        System.out.println("\n1D DP Verification: " + result1D);
    }
}
