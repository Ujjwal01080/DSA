import java.io.*;
import java.util.*;

public class D5_Tree_Path_Guardian {
    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();

        // Forward Star representation for graph
        int[] head = new int[n + 1];
        Arrays.fill(head, -1);
        int[] to = new int[2 * n];
        int[] next = new int[2 * n];
        int edgeCount = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();

            to[edgeCount] = v;
            next[edgeCount] = head[u];
            head[u] = edgeCount++;

            to[edgeCount] = u;
            next[edgeCount] = head[v];
            head[v] = edgeCount++;
        }

        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = fs.nextInt();
        }

        int[] ans = new int[n + 1];
        int[] stk = new int[n + 1];
        int top = 0;

        // Explicit DFS state stacks
        int[] nodeStack = new int[n + 1];
        int[] parentStack = new int[n + 1];
        int[] edgePtrStack = new int[n + 1];
        int[] insertPosStack = new int[n + 1];
        int[] prevTopStack = new int[n + 1];
        int[] prevValStack = new int[n + 1];

        int ptr = 0;

        // Initialize root (node 1)
        nodeStack[0] = 1;
        parentStack[0] = 0;
        edgePtrStack[0] = head[1];
        ans[1] = -1;

        insertPosStack[0] = 0;
        prevTopStack[0] = 0;
        prevValStack[0] = stk[0];
        stk[0] = 1;
        top = 1;

        // Iterative DFS
        while (ptr >= 0) {
            int u = nodeStack[ptr];
            int p = parentStack[ptr];
            int e = edgePtrStack[ptr];

            if (e != -1) {
                edgePtrStack[ptr] = next[e]; // advance edge pointer for next iteration
                int v = to[e];
                if (v != p) {
                    ptr++;
                    nodeStack[ptr] = v;
                    parentStack[ptr] = u;
                    edgePtrStack[ptr] = head[v];

                    // 1. Binary search for guardian: deepest ancestor with a[ancestor] > a[v]
                    int low = 0, high = top - 1;
                    int best = -1;
                    while (low <= high) {
                        int mid = (low + high) >>> 1;
                        if (a[stk[mid]] > a[v]) {
                            best = stk[mid];
                            low = mid + 1;
                        } else {
                            high = mid - 1;
                        }
                    }
                    ans[v] = best;

                    // 2. Binary search insertion position for strictly decreasing stack
                    low = 0;
                    high = top - 1;
                    int insertPos = top;
                    while (low <= high) {
                        int mid = (low + high) >>> 1;
                        if (a[stk[mid]] <= a[v]) {
                            insertPos = mid;
                            high = mid - 1;
                        } else {
                            low = mid + 1;
                        }
                    }

                    // Save state for O(1) rollback
                    insertPosStack[ptr] = insertPos;
                    prevTopStack[ptr] = top;
                    prevValStack[ptr] = stk[insertPos];

                    stk[insertPos] = v;
                    top = insertPos + 1;
                }
            } else {
                // Backtrack: Restore monotonic stack state
                int insertPos = insertPosStack[ptr];
                stk[insertPos] = prevValStack[ptr];
                top = prevTopStack[ptr];
                ptr--;
            }
        }

        // Print final answers
        pw.print(ans[1]);
        for (int i = 2; i <= n; i++) {
            pw.print(" " + ans[i]);
        }
        pw.println();
        pw.flush();
    }

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, buflen = 0;

        private boolean hasNextByte() {
            if (ptr < buflen)
                return true;
            ptr = 0;
            try {
                buflen = in.read(buffer);
            } catch (IOException e) {
                return false;
            }
            return buflen > 0;
        }

        private int readByte() {
            return hasNextByte() ? buffer[ptr++] : -1;
        }

        public int nextInt() {
            int c = readByte();
            while (c <= ' ') {
                if (c == -1)
                    return -1;
                c = readByte();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = readByte();
            }
            int res = 0;
            while (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
                c = readByte();
            }
            return res * sgn;
        }
    }
}