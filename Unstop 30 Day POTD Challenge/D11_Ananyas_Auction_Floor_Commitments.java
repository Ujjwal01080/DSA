import java.util.*;
import java.io.*;

class Data {
    int st;
    int ed;
    int p;

    Data(int s, int e, int p) {
        st = s;
        ed = e;
        this.p = p;
    }
}

public class D11_Ananyas_Auction_Floor_Commitments {

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Data[] data = new Data[n];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            data[i] = new Data(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(data, (a, b) -> {
            return a.st - b.st;
        });
        long[][] memo = new long[n][n];// prevIdx is [-1,n-2]
        for (long[] arr : memo)
            Arrays.fill(arr, -1);
        pw.print(solve(-1, 0, data, memo));
        pw.flush();
    }

    public static long solve(int prevIdx, int currIdx, Data[] data, long[][] memo) {
        if (currIdx == data.length) {
            return 0;
        }
        if (memo[currIdx][prevIdx + 1] != -1) {// in memo[i][0] -> prevIdx is -1 not chosen
            return memo[currIdx][prevIdx + 1];
        }
        long ch1 = 0;
        long ch2;
        if (prevIdx == -1) {
            ch1 = data[currIdx].p + solve(currIdx, currIdx + 1, data, memo);
        } else {
            Data curr = data[currIdx], prev = data[prevIdx];
            if (prev.ed <= curr.st) {
                ch1 = data[currIdx].p + solve(currIdx, currIdx + 1, data, memo);
            }
        }
        ch2 = solve(prevIdx, currIdx + 1, data, memo);
        return memo[currIdx][prevIdx + 1] = Math.max(ch1, ch2);
    }
}
