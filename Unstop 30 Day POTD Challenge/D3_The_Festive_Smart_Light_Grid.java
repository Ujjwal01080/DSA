import java.util.*;
import java.io.*;

public class D3_The_Festive_Smart_Light_Grid {

    public static void main(String arg[]) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        Set<Integer> set = new HashSet<>();
        st = new StringTokenizer(br.readLine());
        while (N-- > 0) {
            set.add(Integer.parseInt(st.nextToken()));
        }
        pw.println(solve(set, k));
        pw.flush();
    }

    public static String solve(Set<Integer> set, int k) {
        int[] ans = new int[2];
        int minD = 100001;
        for (int x : set) {
            if (x == -1 || x == k)
                continue;
            int diff = k - x;
            if (k - x > 0 && set.contains(diff + k) && diff < minD) {
                ans[0] = x;
                ans[1] = diff + k;
                minD = diff;
            }
        }
        return (minD == 100001) ? "-1" : (ans[0] + " " + ans[1]);

    }

}