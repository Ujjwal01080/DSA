import java.util.*;
import java.io.*;
//In standard approach resolve i after j then update answer 
//This approach fails for count all valid subarrays,shortest valid subarray like max-min<=d
public class D15_Festival_Stall_Fairness {
    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int[] data = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            data[i] = Integer.parseInt(st.nextToken());
        pw.print(solve(n, d, data));
        pw.flush();
    }

    public static int solve(int n, int d, int[] data) {
        Deque<Integer> dqMax = new ArrayDeque<>();
        Deque<Integer> dqMin = new ArrayDeque<>();
        int i = 0, j = 0;
        int ans = 0;
        while (j < n) {
            // Remove until minIdx of max and min since if max removed the differece will be solved
            if (!dqMax.isEmpty() && !dqMin.isEmpty() && data[dqMax.getFirst()] - data[dqMin.getFirst()] > d) {
                int minIdx = Math.min(dqMax.getFirst(), dqMin.getFirst());
                while (!dqMax.isEmpty() && dqMax.getFirst() <= minIdx)
                    dqMax.removeFirst();
                while (!dqMin.isEmpty() && dqMin.getFirst() <= minIdx)
                    dqMin.removeFirst();
                i = minIdx + 1;
            }

            while (!dqMax.isEmpty() && data[dqMax.getLast()] < data[j])// whether <= no since equal element difference is zero
                dqMax.removeLast();
            dqMax.addLast(j);
            while (!dqMin.isEmpty() && data[dqMin.getLast()] > data[j])
                dqMin.removeLast();
            dqMin.addLast(j);
            j++;
            if (data[dqMax.getFirst()] - data[dqMin.getFirst()] <= d)
                ans = Math.max(ans, j - i);// check adding the last j have made difference greater

        }
        return ans;

    }
}