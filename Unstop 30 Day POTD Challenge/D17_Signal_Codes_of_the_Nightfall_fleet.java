import java.io.*;
import java.util.*;

public class D17_Signal_Codes_of_the_Nightfall_fleet {

    static class Node {
        Node[] next = new Node[2];
        int maxIdx = -1;

        Node() {}

        Node(Node src) {
            if (src != null) {
                this.next[0] = src.next[0];
                this.next[1] = src.next[1];
                this.maxIdx = src.maxIdx;
            }
        }
    }

    static final int BITS = 30;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        Node[] root = new Node[n + 1];
        root[0] = new Node();

        for (int i = 1; i <= n; i++) {
            int val = Integer.parseInt(st.nextToken());
            root[i] = insert(root[i - 1], val, i);
        }

        int q = Integer.parseInt(br.readLine().trim());
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            out.println(query(root[r], x, l));
        }

        out.flush();
    }

    static Node insert(Node prev, int val, int idx) {
        Node res = new Node(prev);
        res.maxIdx = idx;

        Node curr = res;
        Node old = prev;

        for (int b = BITS; b >= 0; b--) {
            int bit = (val >> b) & 1;
            Node oldChild = (old != null) ? old.next[bit] : null;

            Node nxt = new Node(oldChild);
            nxt.maxIdx = idx;

            curr.next[bit] = nxt;
            curr = nxt;
            old = oldChild;
        }

        return res;
    }

    static int query(Node root, int x, int minIdx) {
        Node curr = root;
        int ans = 0;

        for (int b = BITS; b >= 0; b--) {
            int bit = (x >> b) & 1;
            int opp = 1 - bit;

            if (curr.next[opp] != null && curr.next[opp].maxIdx >= minIdx) {
                ans |= (1 << b);
                curr = curr.next[opp];
            } else {
                curr = curr.next[bit];
            }
        }

        return ans;
    }
}