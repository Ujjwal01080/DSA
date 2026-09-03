import java.util.*;
import java.io.*;

class Node {
    Node[] next = new Node[2];
    int maxIdx = -1;

    Node() {
    }

    Node(Node src) {
        if (src != null) {
            this.next[0] = src.next[0];
            this.next[1] = src.next[1];
            this.maxIdx = src.maxIdx;
        }
    }
}

public class D24_Sonar_Anomaly_Windows {
    static final int BITS = 30;

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        Node[] trie = new Node[n + 1];
        trie[0] = new Node();
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            trie[i] = insert(trie[i - 1], Integer.parseInt(st.nextToken()), i);
        }
        int q = Integer.parseInt(br.readLine()), l, r, x;
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            l = Integer.parseInt(st.nextToken());
            r = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            pw.println(queryMaxXor(trie[r], x, l));
        }
        pw.flush();
    }

    public static Node insert(Node prevRoot, int val, int idx) {
        Node newRoot = new Node(prevRoot);
        newRoot.maxIdx = idx;
        Node curr = newRoot, old = prevRoot, oldChild, next;
        int bit;
        for (int b = BITS; b >= 0; b--) {
            bit = (val >> b) & 1;
            oldChild = (old != null) ? old.next[bit] : null;
            next = new Node(oldChild);
            next.maxIdx = idx;
            curr.next[bit] = next;
            curr = next;
            old = oldChild;
        }
        return newRoot;
    }

    public static int queryMaxXor(Node rootAtR, int x, int minIdx) {
        Node curr = rootAtR;
        int maxXor = 0, bit, opp;
        for (int b = BITS; b >= 0; b--) {
            bit = (x >> b) & 1;
            opp = 1 - bit;
            if (curr.next[opp] != null && curr.next[opp].maxIdx >= minIdx) {
                maxXor |= (1 << b);
                curr = curr.next[opp];
            } else
                curr = curr.next[bit];
        }
        return maxXor;
    }
}