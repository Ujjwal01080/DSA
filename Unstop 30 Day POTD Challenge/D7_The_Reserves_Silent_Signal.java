import java.io.*;
import java.util.*;

class Pair {
    long x, y;

    Pair(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

public class D7_The_Reserves_Silent_Signal {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        String line = br.readLine();

        int N = Integer.parseInt(line.trim());
        Pair[] points = new Pair[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            points[i] = new Pair(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()));
        }

        pw.println(solve(points));
        pw.flush();
    }

    public static long solve(Pair[] points) {
        // Sort by X-coordinate
        Arrays.sort(points, (a, b) -> {
            if (a.x != b.x)
                return Long.compare(a.x, b.x);
            return Long.compare(a.y, b.y);
        });

        Pair[] aux = new Pair[points.length];
        return closestPair(points, aux, 0, points.length - 1);
    }

    private static long closestPair(Pair[] pts, Pair[] aux, int left, int right) {
        // Base cases: 2 or 3 points
        if (right - left <= 3) {
            long minDist = Long.MAX_VALUE;
            for (int i = left; i <= right; i++) {
                for (int j = i + 1; j <= right; j++) {
                    minDist = Math.min(minDist, distSq(pts[i], pts[j]));
                }
            }
            // Keep the segment sorted by Y for merge step
            Arrays.sort(pts, left, right + 1, Comparator.comparingLong(p -> p.y));
            return minDist;
        }

        int mid = left + (right - left) / 2;
        long midX = pts[mid].x;

        long d1 = closestPair(pts, aux, left, mid);
        long d2 = closestPair(pts, aux, mid + 1, right);
        long d = Math.min(d1, d2);

        // Merge two Y-sorted halves in O(N)
        mergeByY(pts, aux, left, mid, right);

        // Collect candidates within Euclidean distance sqrt(d) from midX
        int stripSize = 0;
        for (int i = left; i <= right; i++) {
            long dx = pts[i].x - midX;
            if (dx * dx < d) {
                aux[stripSize++] = pts[i];
            }
        }

        // Compare each point in the strip with subsequent points within Y-bound
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize; j++) {
                long dy = aux[j].y - aux[i].y;
                if (dy * dy >= d)
                    break; // Exceeds current minimum
                d = Math.min(d, distSq(aux[i], aux[j]));
            }
        }

        return d;
    }

    private static void mergeByY(Pair[] pts, Pair[] aux, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (pts[i].y <= pts[j].y)
                aux[k++] = pts[i++];
            else
                aux[k++] = pts[j++];
        }
        while (i <= mid)
            aux[k++] = pts[i++];
        while (j <= right)
            aux[k++] = pts[j++];
        for (i = left; i <= right; i++)
            pts[i] = aux[i];
    }

    private static long distSq(Pair p1, Pair p2) {
        long dx = p1.x - p2.x;
        long dy = p1.y - p2.y;
        return dx * dx + dy * dy;
    }
}