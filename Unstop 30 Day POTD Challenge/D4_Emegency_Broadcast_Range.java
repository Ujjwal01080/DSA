import java.util.*;
import java.io.*;

class Pair {
    int u;
    int p;

    Pair(int u, int p) {
        this.u = u;
        this.p = p;
    }
}

@SuppressWarnings("unchecked")
public class D4_Emegency_Broadcast_Range {

    public static void main(String arg[]) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()),
                m = Integer.parseInt(st.nextToken()),
                k = Integer.parseInt(st.nextToken());

        List<Integer>[] gph = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            gph[i] = new ArrayList<>();
        }

        int u, v;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            gph[u].add(v);
            gph[v].add(u);
        }

        Queue<Pair> qSrc = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        int t, p;
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            t = Integer.parseInt(st.nextToken());
            p = Integer.parseInt(st.nextToken());
            qSrc.offer(new Pair(t, p));
            set.add(t);
        }
        boolean[] vis = new boolean[n + 1];
        Queue<Pair> q = new LinkedList<>();
        while (!qSrc.isEmpty()) {
            q.offer(qSrc.poll());
            while (!q.isEmpty()) {
                Pair curr = q.poll();
                vis[curr.u] = true;
                if (curr.p > 0) {
                    for (int i = 0; i < gph[curr.u].size(); i++) {
                        v = gph[curr.u].get(i);
                        set.add(v);
                        if (!vis[v]) {
                            vis[v] = true;
                            q.offer(new Pair(v, curr.p - 1));
                        }

                    }
                }
            }
            Arrays.fill(vis, false);
        }

        pw.println(set.size());
        pw.flush();
    }

}