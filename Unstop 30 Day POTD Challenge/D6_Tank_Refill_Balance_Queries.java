import java.util.*;
import java.io.*;

public class D6_Tank_Refill_Balance_Queries {

    public static void main(String arg[]) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long[] prefix = new long[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            prefix[i] = Integer.parseInt(st.nextToken());
            if (i > 0)
                prefix[i] += prefix[i - 1];
        }

        int Q = Integer.parseInt(br.readLine());
        int l, r;
        long sum;
        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            l = Integer.parseInt(st.nextToken());
            r = Integer.parseInt(st.nextToken());
            if (l == 1) {
                sum = prefix[r - 1];
            } else {
                sum = prefix[r - 1] - prefix[l - 2];
            }
            pw.println(sum + " " + solve(sum));
        }
        pw.flush();
    }

    public static String solve(long sum) {
        if (sum > 0)
            return "SURPLUS";
        if (sum < 0)
            return "DEFICIT";
        return "BALANCED";
    }

}