import java.util.*;
import java.io.*;

public class D23_The_Watch_Tower_Horizon {

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[] data = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            data[i] = Integer.parseInt(st.nextToken());
        }
        for (int ans : solve(data, n)) {
            pw.print(ans + " ");
        }
        pw.flush();
    }

    public static int[] solve(int[] data, int n) {
        int[] ans = new int[n];
        Deque<Integer> dq = new ArrayDeque<>();
        ans[n - 1] = 0;
        dq.push(n - 1);
        for (int i = n - 2; i >= 0; i--) {
            while (!dq.isEmpty() && data[dq.peek()] <= data[i]) {
                dq.pop();
            }
            if (dq.isEmpty()) {
                ans[i] = n - i - 1;
            } else {
                ans[i] = dq.peek() - i;
            }
            dq.push(i);
        }
        return ans;
    }
}