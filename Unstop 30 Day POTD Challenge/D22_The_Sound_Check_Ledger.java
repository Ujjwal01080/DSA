import java.util.*;
import java.io.*;

public class D22_The_Sound_Check_Ledger {

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int[] data = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            data[i] = Integer.parseInt(st.nextToken());
        pw.print(solve(data, n, l));
        pw.flush();
    }

    public static int solve(int[] data, int n, int l) {
        Deque<Integer> dqMax = new ArrayDeque<>();
        Deque<Integer> dqMin = new ArrayDeque<>();
        int i = 0, curr;
        int ans = 0;
        for (int j = 0; j < n; j++) {
            curr = data[j];
            while (!dqMax.isEmpty() && data[dqMax.getLast()] <= curr)
                dqMax.removeLast();
            dqMax.addLast(j);
            while (!dqMin.isEmpty() && data[dqMin.getLast()] >= curr)
                dqMin.removeLast();
            dqMin.addLast(j);

            while (data[dqMax.getFirst()] - data[dqMin.getFirst()] > l) {
                i++;
                if (dqMax.getFirst() < i)
                    dqMax.removeFirst();
                if (dqMin.getFirst() < i)
                    dqMin.removeFirst();
            }
            ans = Math.max(ans, j - i + 1);
        }
        return ans;
    }

}