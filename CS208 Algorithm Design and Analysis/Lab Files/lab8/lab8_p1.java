import java.util.*;

public class lab8_p1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] piles = new long[n];
        PriorityQueue<Long> pq1 = new PriorityQueue<>();
        PriorityQueue<Long> pq2 = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < n; i++) {
            piles[i] = sc.nextLong();
            pq1.add(piles[i]);
            pq2.add(piles[i]);
        }

        long ans1 = 0, ans2 = 0;
        for (int i = 1; i < n; i++) {
            long cur1 = pq1.poll() + pq1.poll(), cur2 = pq2.poll() + pq2.poll();
            ans1 += cur1;
            ans2 += cur2;
            pq1.add(cur1);
            pq2.add(cur2);
        }

        System.out.println(ans2 + " " + ans1);
    }
}
