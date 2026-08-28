import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;

public class D14_Signal_Archives_of_the_triton {
    static final int MAX_BITS = 30;
    static final int MAX_NODES = 200005 * 32;

    static int[][] nextNode = new int[MAX_NODES][2];
    static int[] latestIdx = new int[MAX_NODES];
    static int[] root = new int[200005];
    static int nodeCount = 0;

    static int insert(int prevRoot, int val, int idx) {
        int cur = ++nodeCount;
        nextNode[cur][0] = nextNode[prevRoot][0];
        nextNode[cur][1] = nextNode[prevRoot][1];
        latestIdx[cur] = idx;

        int head = cur;
        int p = prevRoot;

        for (int b = MAX_BITS - 1; b >= 0; b--) {
            int bit = (val >> b) & 1;
            int child = ++nodeCount;

            int prevChild = (p != 0) ? nextNode[p][bit] : 0;
            nextNode[child][0] = nextNode[prevChild][0];
            nextNode[child][1] = nextNode[prevChild][1];
            latestIdx[child] = idx;

            nextNode[head][bit] = child;

            head = child;
            p = prevChild;
        }
        return cur;
    }

    static int query(int rootVersion, int l, int x) {
        int cur = rootVersion;
        int ans = 0;

        for (int b = MAX_BITS - 1; b >= 0; b--) {
            int bit = (x >> b) & 1;
            int want = 1 - bit;
            int cand = nextNode[cur][want];

            // If the preferred branch exists and has an element in [l, r]
            if (cand != 0 && latestIdx[cand] >= l) {
                ans |= (1 << b);
                cur = cand;
            } else {
                cur = nextNode[cur][bit];
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int n = fs.nextInt();
        latestIdx[0] = -1;

        for (int i = 1; i <= n; i++) {
            int a = fs.nextInt();
            root[i] = insert(root[i - 1], a, i);
        }

        int q = fs.nextInt();
        for (int i = 0; i < q; i++) {
            int l = fs.nextInt();
            int r = fs.nextInt();
            int x = fs.nextInt();
            out.println(query(root[r], l, x));
        }

        out.flush();
    }

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buf = new byte[32768];
        private int head = 0, tail = 0;

        private int read() throws IOException {
            if (head >= tail) {
                head = 0;
                tail = in.read(buf, 0, buf.length);
                if (tail <= 0)
                    return -1;
            }
            return buf[head++];
        }

        public int nextInt() throws IOException {
            int c = read();
            while (c <= ' ') {
                if (c == -1)
                    return -1;
                c = read();
            }
            int res = 0;
            while (c > ' ') {
                if (c < '0' || c > '9')
                    break;
                res = res * 10 + (c - '0');
                c = read();
            }
            return res;
        }
    }
}