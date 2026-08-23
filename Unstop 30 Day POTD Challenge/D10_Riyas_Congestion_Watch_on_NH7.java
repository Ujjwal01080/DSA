import java.util.*;
import java.io.*;

public class D10_Riyas_Congestion_Watch_on_NH7 {
    static long[] tree;
    static long[] lazy;

    public static void main(String arg[]) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] cg = new int[n];
        tree = new long[4 * n];
        lazy = new long[4 * n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            cg[i] = Integer.parseInt(st.nextToken());
        buildST(cg, 0, 0, n - 1);
        int q = Integer.parseInt(br.readLine());
        int l, r, t;
        int v;
        long x;
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            t = Integer.parseInt(st.nextToken());
            l = Integer.parseInt(st.nextToken()) - 1;
            r = Integer.parseInt(st.nextToken()) - 1;
            if (t == 1) {
                v = Integer.parseInt(st.nextToken());
                updateUtil(0, 0, n - 1, l, r, v);
            } else if (t == 2) {
                pw.println(getMaxUtil(0, 0, n - 1, l, r));
            } else {
                x = Long.parseLong(st.nextToken());
                pw.println(findFirstG(0, 0, n - 1, l, r, x));
            }

        }
        pw.flush();
    }

    public static long buildST(int[] cg, int i, int st, int ed) {
        if (st == ed) {
            tree[i] = cg[st];
            return tree[i];
        }
        int mid = st + (ed - st) / 2;
        tree[i] = Math.max(buildST(cg, 2 * i + 1, st, mid), buildST(cg, 2 * i + 2, mid + 1, ed));
        return tree[i];
    }

    public static void push(int i, int st, int ed) {
        if (lazy[i] != 0) {
            tree[i] += lazy[i];
            if (st != ed) {
                lazy[2 * i + 1] += lazy[i];
                lazy[2 * i + 2] += lazy[i];
            }
            lazy[i] = 0;
        }
    }

    public static void updateUtil(int i, int si, int sj, int qi, int qj, int v) {
        push(i, si, sj);
        if (qj < si || qi > sj) {
            return;
        } else if (qi <= si && sj <= qj) {
            tree[i] += v;
            if (si != sj) {
                lazy[2 * i + 1] += v;
                lazy[2 * i + 2] += v;
            }
            return;
        }
        int mid = si + (sj - si) / 2;
        updateUtil(2 * i + 1, si, mid, qi, qj, v);
        updateUtil(2 * i + 2, mid + 1, sj, qi, qj, v);
        tree[i] = Math.max(tree[2 * i + 1], tree[2 * i + 2]);

    }

    public static long getMaxUtil(int i, int si, int sj, int qi, int qj) {
        push(i, si, sj);
        if (qj < si || qi > sj) {
            return 0;
        } else if (qi <= si && sj <= qj)
            return tree[i];
        int mid = si + (sj - si) / 2;
        return Math.max(getMaxUtil(2 * i + 1, si, mid, qi, qj), getMaxUtil(2 * i + 2, mid + 1, sj, qi, qj));
    }

    public static int findFirstG(int i, int si, int sj, int qi, int qj, long x) {
        push(i, si, sj);

        if (qj < si || qi > sj || tree[i] <= x) { // This segment no overlap or segment max value is less than x
            return -1;
        }

        if (si == sj) {
            return si + 1;// for 1indexed
        }
        int mid = si + (sj - si) / 2;

        int lfRes = findFirstG(2 * i + 1, si, mid, qi, qj, x);
        if (lfRes != -1) {
            return lfRes;
        }
        return findFirstG(2 * i + 2, mid + 1, sj, qi, qj, x);
    }

}