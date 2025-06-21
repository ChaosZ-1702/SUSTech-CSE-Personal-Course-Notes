import java.util.*;

public class lab8_p2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(), n, k;
        long[] piles;
        PriorityQueue<Long> pq1 = new PriorityQueue<>();
        PriorityQueue<Long> pq2 = new PriorityQueue<>(Collections.reverseOrder());
        while (t-- > 0) {
            n = sc.nextInt();
            k = sc.nextInt();
            piles = new long[n];
            for (int i = 0; i < n; i++) {
                piles[i] = sc.nextLong();
                pq1.add(piles[i]);
                pq2.add(piles[i]);
            }

            long ans1 = 0, ans2 = 0;
            while ((pq1.size() - 1) % (k - 1) != 0) pq1.add(ans1);
            while (pq1.size() > 1) {
                long cur1 = pq1.poll();
                for (int i = 1; i < k; i++) {
                    cur1 += pq1.poll();
                }
                ans1 += cur1;
                pq1.add(cur1);
            }
            for (int i = 1; i < n; i++) {
                long cur2 = pq2.poll() + pq2.poll();
                ans2 += cur2;
                pq2.add(cur2);
            }

            System.out.println(ans2 + " " + ans1);
            pq1.poll();
            pq2.poll();
        }
    }
}
