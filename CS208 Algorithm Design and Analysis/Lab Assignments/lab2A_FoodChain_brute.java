import java.io.*;
import java.util.*;

public class lab2A_FoodChain_brute {
    static final long MOD = 1000000007;
    static int n, m;
    static ArrayList<Integer>[] graph;
    static int[] inDeg, outDeg;
    static long[] bruteAns;
    static ArrayList<Integer> path = new ArrayList<>();

    public static void main(String[] args) {
        QReader in = new QReader();
        n = in.nextInt();
        m = in.nextInt();
        graph = new ArrayList[n + 1];
        inDeg = new int[n + 1];
        outDeg = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt(), v = in.nextInt();
            graph[u].add(v);
            inDeg[v]++;
            outDeg[u]++;
        }

        bruteAns = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            if (inDeg[i] == 0) {
                dfs(i);
            }
        }
        for (int i = 1; i <= n; i++) {
            System.out.print(bruteAns[i] + " ");
        }
    }
    static void dfs(int cur) {
        path.add(cur);
        if (outDeg[cur] == 0) {
            for (int node : path) {
                bruteAns[node] = (bruteAns[node] + 1) % MOD;
            }
        } else {
            for (int next : graph[cur]) {
                dfs(next);
            }
        }
        path.remove(path.size() - 1);
    }

    private static class QReader {
        private BufferedReader reader;
        private StringTokenizer tokenizer = new StringTokenizer("");
        public QReader() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }
        public boolean hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
            return true;
        }
        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }
        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
