import java.io.*;
import java.util.*;

public class D1_Emergency_Supply_Convoy {

    static class Village {
        int deadline;
        long crates;

        Village(int deadline, long crates) {
            this.deadline = deadline;
            this.crates = crates;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());

        Village[] villages = new Village[n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int d = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            villages[i] = new Village(d, c);
        }

        // Sort by deadline
        Arrays.sort(villages, (a, b) -> Integer.compare(a.deadline, b.deadline));

        // Min-heap based on crates
        PriorityQueue<Long> minHeap = new PriorityQueue<>();

        long total = 0;

        for (Village v : villages) {

            minHeap.add(v.crates);
            total += v.crates;

            // We can complete at most v.deadline jobs
            if (minHeap.size() > v.deadline) {
                total -= minHeap.poll();
            }
        }

        System.out.println(total);
    }
}