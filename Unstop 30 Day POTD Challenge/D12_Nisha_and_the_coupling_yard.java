import java.io.*;
import java.util.*;

public class D12_Nisha_and_the_coupling_yard {
    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] data = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            data[i] = Integer.parseInt(st.nextToken());
        }
        pw.print(solve(data, n, m));
        pw.flush();

    }

    public static String solve(int[] data, int n, int m) {
        Deque<Integer> dqMax = new ArrayDeque<>(n);
        Deque<Integer> dqMin = new ArrayDeque<>(n);

        int ansSt = 0, minDiff = Integer.MAX_VALUE, curr, maxEl, minEl;
        for (int i = 0; i < m; i++) {
            curr = data[i];
            while (!dqMax.isEmpty() && data[dqMax.getLast()] < curr) {
                dqMax.removeLast();
            }
            dqMax.addLast(i);

            while (!dqMin.isEmpty() && data[dqMin.getLast()] > curr) {
                dqMin.removeLast();
            }
            dqMin.addLast(i);
        }
        maxEl = data[dqMax.getFirst()];
        minEl = data[dqMin.getFirst()];
        if (maxEl - minEl < minDiff) {
            minDiff = maxEl - minEl;
            ansSt = 0;
        }

        for (int i = m; i < n; i++) {
            while (!dqMax.isEmpty() && dqMax.getFirst() < i - m + 1) {
                dqMax.removeFirst();
            }

            while (!dqMin.isEmpty() && dqMin.getFirst() < i - m + 1) {
                dqMin.removeFirst();
            }
            curr = data[i];

            while (!dqMax.isEmpty() && data[dqMax.getLast()] < curr) {
                dqMax.removeLast();
            }
            dqMax.addLast(i);

            while (!dqMin.isEmpty() && data[dqMin.getLast()] > curr) {
                dqMin.removeLast();
            }
            dqMin.addLast(i);
            maxEl = data[dqMax.getFirst()];
            minEl = data[dqMin.getFirst()];
            if (maxEl - minEl < minDiff) {
                minDiff = maxEl - minEl;
                ansSt = i - m + 1;
            }

        }
        return minDiff + " " + (ansSt + 1);

    }
}