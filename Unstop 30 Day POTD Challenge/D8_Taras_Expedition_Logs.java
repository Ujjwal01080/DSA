import java.util.Scanner;

public class D8_Taras_Expedition_Logs {
    // Function where user will write their logic
    public static long countArrangements(int n, long k, long[] s) {
        int mod = 1000000007;
        long[] ways = new long[s.length + 1];
        ways[0] = 1;
        int l = 0;
        long sum = 0;

        for (int i = 1; i < n + 1; i++) {
            sum = (sum + ways[i - 1]) % mod;
            while (s[i] - s[l] > k) {
                sum = (sum - ways[l] + mod) % mod;
                l++;
            }
            ways[i] = sum;
        }
        return ways[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long k = scanner.nextLong();

        long[] s = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            s[i] = scanner.nextLong() + s[i - 1];
        }
        scanner.close();
        // Call user logic function and print the output
        long result = countArrangements(n, k, s);
        System.out.println(result);
    }
}