import java.util.*;
import java.io.*;

class Edge {
    int v;
    int w;

    Edge(int v, int w) {
        this.v = v;
        this.w = w;
    }
}

@SuppressWarnings("unchecked")
public class D21_The_Coral_Relay {

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<Edge> gph[] = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++)
            gph[i] = new ArrayList<>();
        int u, v, w;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            gph[u].add(new Edge(v, w));
            gph[v].add(new Edge(u, w));
        }
        pw.print(solve(gph, n));
        pw.flush();
    }

    public static long solve(List<Edge> gph[], int n) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.w - b.w);
        boolean[] vis = new boolean[n + 1];
        pq.offer(new Edge(1, 0));
        long ans = 0;
        Edge curr, neigh;
        int size, chk = 0;
        while (!pq.isEmpty()) {
            curr = pq.poll();
            if (!vis[curr.v]) {
                vis[curr.v] = true;
                chk++;
                ans += curr.w;
                size = gph[curr.v].size();
                for (int i = 0; i < size; i++) {
                    neigh = gph[curr.v].get(i);
                    if (!vis[neigh.v]) {
                        pq.offer(neigh);
                    }
                }
            }
        }
        return (chk == n) ? ans : -1;
    }

}