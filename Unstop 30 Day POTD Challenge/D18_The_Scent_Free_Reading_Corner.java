import java.util.*;
import java.io.*;

public class D18_The_Scent_Free_Reading_Corner {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int data[] = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            data[i] = Integer.parseInt(st.nextToken());
        pw.print(solve(data, n, k));
        pw.flush();
    }

    public static int solve(int[] data, int n, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int i = 0, ans = 0;
        int currj, curri;
        for (int j = 0; j < n; j++) {
            currj = data[j];
            map.put(currj, map.getOrDefault(currj, 0) + 1);

            while (map.size() > k && i <= j) {
                curri = data[i];
                map.put(curri, map.get(curri) - 1);
                if (map.get(curri) == 0) {
                    map.remove(curri);
                }
                i++;
            }

            ans = Math.max(ans, j - i + 1);

        }
        return ans;
    }
}