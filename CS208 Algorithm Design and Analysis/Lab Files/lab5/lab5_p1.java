import java.util.*;

public class lab5_p1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        ArrayList<Integer>[] adj = new ArrayList[n + 1];
        int[] inDeg = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            inDeg[v]++;
        }
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            if (inDeg[i] == 0) q.add(i);
        }

        ArrayList<Integer> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            int current = q.poll();
            ans.add(current);
            for (int i = 0; i < adj[current].size(); i++) {
                int next = adj[current].get(i);
                inDeg[next]--;
                if (inDeg[next] == 0) q.add(next);
            }
        }

        if (ans.size() != n) {
            System.out.println("impossible");
        } else {
            for (int node : ans) {
                System.out.print(node + " ");
            }
        }
    }
}
