import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class D16_The_Whispiring_roots_of_devgarh {
    static class Node {
        int to, wt;

        Node(int to, int wt) {
            this.to = to;
            this.wt = wt;
        }
    }

    static ArrayList<Node>[] gph;
    static int[] val;
    static long[] sub;
    static long[] dp;
    static long[] ans;
    static long totalVal;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());

        val = new int[n + 1];
        gph = new ArrayList[n + 1];
        sub = new long[n + 1];
        dp = new long[n + 1];
        ans = new long[n + 1];

        for (int i = 1; i <= n; i++)
            gph[i] = new ArrayList<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            val[i] = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            gph[u].add(new Node(v, w));
            gph[v].add(new Node(u, w));
        }

        int[] par = new int[n + 1];
        int[] order = new int[n];
        int head = 0, tail = 0;

        order[tail++] = 1;
        par[1] = 0;
        boolean[] seen = new boolean[n + 1];
        seen[1] = true;

        while (head < tail) {
            int u = order[head++];
            for (Node edge : gph[u]) {
                int nxt = edge.to;
                if (!seen[nxt]) {
                    seen[nxt] = true;
                    par[nxt] = u;
                    order[tail++] = nxt;
                }
            }
        }

        for (int i = 1; i <= n; i++)
            sub[i] = val[i];

        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            for (Node edge : gph[u]) {
                int v = edge.to;
                if (v != par[u]) {
                    sub[u] += sub[v];
                    dp[u] += dp[v] + sub[v] * edge.wt;
                }
            }
        }

        ans[1] = dp[1];
        totalVal = sub[1];

        for (int i = 0; i < n; i++) {
            int u = order[i];
            for (Node edge : gph[u]) {
                int v = edge.to;
                if (v != par[u]) {
                    ans[v] = ans[u] + (totalVal - 2L * sub[v]) * edge.wt;
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            out.print(ans[i] + (i == n ? "" : " "));
        }
        out.flush();
    }
}