import java.util.*;
import java.io.*;

class RDLedger {
    ArrayList<Integer> ledger;
    int size;

    public RDLedger() {
        ledger = new ArrayList<>();
        size = 0;
    }

    public void add(int x) {
        int st = 0, ed = size - 1;
        while (st <= ed) {
            int mid = st + (ed - st) / 2;
            int midEl = ledger.get(mid);
            if (midEl == x) {
                return;
            } else if (midEl < x) {
                st = mid + 1;
            } else {
                ed = mid - 1;
            }
        }
        ledger.add(st, x);
        size++;
    }

    public String position(int k) {
        if (k < 1 || k > size) {
            return "NONE";
        }
        return ledger.get(k - 1).toString();
    }

    public String exists(int x) {
        int st = 0, ed = size - 1;
        while (st <= ed) {
            int mid = st + (ed - st) / 2;
            int midEl = ledger.get(mid);
            if (midEl == x) {
                return "YES";
            } else if (midEl < x) {
                st = mid + 1;
            } else {
                ed = mid - 1;
            }
        }
        return "NO";
    }

    public void remove(int x) {
        int st = 0, ed = size - 1;
        while (st <= ed) {
            int mid = st + (ed - st) / 2;
            int midEl = ledger.get(mid);
            if (midEl == x) {
                ledger.remove(mid);
                size--;
                return;
            } else if (midEl < x) {
                st = mid + 1;
            } else {
                ed = mid - 1;
            }
        }
    }

    public String before(int x) {
        int st = 0, ed = size - 1;
        while (st <= ed) {
            int mid = st + (ed - st) / 2;
            if (ledger.get(mid) < x) {
                st = mid + 1;
            } else {
                ed = mid - 1;
            }
        }
        if (st == 0) {
            return "NONE";
        }
        return ledger.get(st - 1).toString();
    }

    public String after(int x) {
        int st = 0, ed = size - 1;
        while (st <= ed) {
            int mid = st + (ed - st) / 2;
            if (ledger.get(mid) <= x) {
                st = mid + 1;
            } else {
                ed = mid - 1;
            }
        }
        if (st == size) {
            return "NONE";
        }
        return ledger.get(st).toString();
    }
}

public class D2_The_Royal_Decree_Ledger {
    static RDLedger obj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            pw.flush();
            return;
        }

        int Q = Integer.parseInt(line.trim());
        StringTokenizer st;
        obj = new RDLedger();

        while (Q-- > 0) {
            String qLine = br.readLine();
            if (qLine == null)
                break;
            st = new StringTokenizer(qLine);
            if (!st.hasMoreTokens())
                continue;

            String opr = st.nextToken();
            int x = Integer.parseInt(st.nextToken());

            String res = solve(opr, x);
            if (!res.isEmpty()) {
                pw.println(res);
            }
        }
        pw.flush();
    }

    public static String solve(String opr, int x) {
        if (opr.equals("ADD")) {
            obj.add(x);
            return "";
        }
        if (opr.equals("REMOVE")) {
            obj.remove(x);
            return "";
        }
        if (opr.equals("EXISTS")) {
            return obj.exists(x);
        }
        if (opr.equals("BEFORE")) {
            return obj.before(x);
        }
        if (opr.equals("AFTER")) {
            return obj.after(x);
        }
        if (opr.equals("POSITION")) {
            return obj.position(x);
        }
        return "";
    }
}