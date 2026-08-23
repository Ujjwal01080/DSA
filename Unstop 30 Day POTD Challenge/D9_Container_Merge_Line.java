import java.util.*;
import java.io.*;

public class D9_Container_Merge_Line {

    public static void main(String arg[]) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        long[] stk = new long[n];
        int top = -1;
        while (n-- > 0) {
            stk[++top] = Integer.parseInt(st.nextToken());
            while (top >= 1 && stk[top] == stk[top - 1]) {
                stk[top - 1] = 2 * stk[top--];
            }
        }
        pw.println(top + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= top; i++) {
            sb.append(stk[i]).append(" ");
        }
        pw.print(sb.toString().trim());
        pw.flush();
    }

}