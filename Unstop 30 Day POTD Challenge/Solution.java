import java.util.*;
import java.io.*;

class Node{
    Node prev;
    Node next;
    int val;
    Node(int val){
        this.val = val;
        prev = null;
        next = null;
    }
}
public class Solution{
    static Node head,tail;
    static Map<Integer,Node> map;
    public static void main(String[] args)throws IOException{
        PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        String opr;
        int x;
        tail = head = null;
        int currPsg = 1;
        map = new HashMap<>();
        while(m-->0){
            st = new StringTokenizer(br.readLine());
            opr = st.nextToken();
            if(opr.equals("A")){
                addLast(currPsg++);
            }else if(opr.equals("P")){
                x = Integer.parseInt(st.nextToken());
                if(x<currPsg && head.val != x && map.containsKey(x))
                    moveFirst(x);
            }else{
                pw.println(removeFirst());
            }
        }
        pw.flush();

    }

    public static void addLast(int currPsg){
        if(head==null){
            tail = head = new Node(currPsg);
            map.put(currPsg,head);
            return;
        }
        tail.next = new Node(currPsg);
        map.put(currPsg,tail.next);
        tail.next.prev = tail;
        tail = tail.next;
    }

    public static int removeFirst(){
        if(head == null){
            return 0;
        }
        int val = head.val;
        if(head.next == null){
            head = null;
            tail = null;
        }else{
            head = head.next;   
            head.prev.next = null;
            head.prev = null;
        }
        map.remove(val);
        return val;
    }

    public static void moveFirst(int x){
            Node nodex = map.get(x);
            nodex.prev.next = nodex.next;
            if(nodex.next!=null){
                nodex.next.prev = nodex.prev;
            }else{
                tail = nodex.prev;
            }
            nodex.next = head;
            nodex.prev = null;
            head.prev = nodex;
            head = nodex;
    }
}