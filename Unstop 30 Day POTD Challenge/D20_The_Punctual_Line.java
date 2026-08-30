import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class D20_The_Punctual_Line {
    static class Edge {
        int v;
        long f, q, d;

        Edge(int v, long f, long q, long d) {
            this.v = v;
            this.f = f;
            this.q = q;
            this.d = d;
        }
    }

    static class Node implements Comparable<Node> {
        int u;
        long t;

        Node(int u, long t) {
            this.u = u;
            this.t = t;
        }

        public int compareTo(Node o) {
            return Long.compare(this.t, o.t);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        List<Edge>[] g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) g[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            long f = Long.parseLong(st.nextToken());
            long q = Long.parseLong(st.nextToken());
            long dur = Long.parseLong(st.nextToken());

            g[u].add(new Edge(v, f, q, dur));
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[s] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(s, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.u;
            long t = cur.t;

            if (t > dist[u]) continue;
            if (u == d) break;

            for (Edge e : g[u]) {
                long dep;
                if (t <= e.f) {
                    dep = e.f;
                } else {
                    if (e.q == 0) continue;
                    long diff = t - e.f;
                    long k = (diff + e.q - 1) / e.q;
                    dep = e.f + k * e.q;
                }

                long arr = dep + e.d;
                if (arr < dist[e.v]) {
                    dist[e.v] = arr;
                    pq.add(new Node(e.v, arr));
                }
            }
        }

        pw.println(dist[d] == Long.MAX_VALUE ? -1 : dist[d]);
        pw.flush();
    }
}