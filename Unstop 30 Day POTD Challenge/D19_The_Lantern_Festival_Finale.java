import java.util.*;
import java.io.*;

//Same as D15
public class D19_The_Lantern_Festival_Finale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int[] data = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            data[i] = Integer.parseInt(st.nextToken());
        pw.print(solve(n, l, data));
        pw.flush();
    }

    public static long solve(int n, int l, int[] data) {
        Deque<Integer> dqMax = new ArrayDeque<>();
        Deque<Integer> dqMin = new ArrayDeque<>();
        long[] pref = new long[n + 1];
        for (int k = 0; k < n; k++) {
            pref[k + 1] = pref[k] + data[k];
        }
        int i = 0, j = 0;
        int currj;
        long ans = 0;
        while (j < n) {
            currj = data[j];
            while (!dqMax.isEmpty() && data[dqMax.getLast()] <= currj)
                dqMax.removeLast();
            dqMax.addLast(j);
            while (!dqMin.isEmpty() && data[dqMin.getLast()] >= currj)
                dqMin.removeLast();
            dqMin.addLast(j);

            while (data[dqMax.getFirst()] - data[dqMin.getFirst()] > l) {
                i++;
                if (dqMax.getFirst() < i)
                    dqMax.removeFirst();
                if (dqMin.getFirst() < i)
                    dqMin.removeFirst();
            }
            long currentSum = pref[j + 1] - pref[i];
            ans = Math.max(ans, currentSum);
            j++;
        }

        return ans;
    }

}