import java.io.*;
import java.util.*;

public class lab11B_XiaofengInternationalCommunicationNetwork {
    private static ArrayList<Edge>[] adj;
    private static int[] level;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt(), s = in.nextInt(), t = in.nextInt(), N = n * 2, S = s + n;
        adj = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) adj[i] = new ArrayList<>();
        for (int i = 1; i <= n; i++) addEdge(i, i + n, (i == s || i == t ? Long.MAX_VALUE : 1));
        for (int i = 0; i < m; i++) {
            int u = in.nextInt(), v = in.nextInt();
            addEdge(u + n, v, Long.MAX_VALUE);
            addEdge(v + n, u, Long.MAX_VALUE);
        }


        long maxFlow = 0;
        level = new int[N + 1];
        while (bfs(S, t)) {
            int[] cur = new int[N + 1];
            long flow = dfs(S, t, Long.MAX_VALUE, cur);
            while (flow > 0) {
                maxFlow += flow;
                flow = dfs(S, t, Long.MAX_VALUE, cur);
            }
//            for (int i = 0; i < level.length; i++) {
//                System.out.print(level[i] + " ");
//            }
//            System.out.println();
//            for (int i = 0; i < cur.length; i++) {
//                System.out.print(cur[i] + " ");
//            }
//            System.out.println();
        }

        out.println(maxFlow);
        out.close();
    }

    private static long dfs(int u, int t, long flow, int[] cur) {
        if (u == t) return flow;
        while (cur[u] < adj[u].size()) {
            Edge e = adj[u].get(cur[u]);
            if (level[e.to] == level[u] + 1 && e.capacity > e.flow) {
                long f = dfs(e.to, t, Math.min(flow, e.capacity - e.flow), cur);
                if (f > 0) {
                    e.flow += f;
                    adj[e.to].get(e.fromIndex).flow -= f;
                    return f;
                }
            }
            cur[u]++;
        }
        return 0;
    }

    private static boolean bfs(int s, int t) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] isVisited = new boolean[adj.length];
        q.add(s);
        level[s] = 0;
        isVisited[s] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edge e : adj[u]) {
                if (!isVisited[e.to] && e.capacity > e.flow) {
                    isVisited[e.to] = true;
                    level[e.to] = level[u] + 1;
                    q.add(e.to);
                    if (e.to == t) return true;
                }
            }
        }
        return isVisited[t];
    }

    private static void addEdge(int u, int v, long c) {
        adj[u].add(new Edge(adj[v].size(), v, c));
        adj[v].add(new Edge(adj[u].size() - 1, u, 0));
    }

    private static class Edge {
        int fromIndex, to;
        long capacity, flow;

        public Edge(int f, int t, long c) {
            this.fromIndex = f;
            this.to = t;
            this.capacity = c;
            this.flow = 0;
        }
    }

    private static class QReader {
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");

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

        public String nextLine() {
            tokenizer = new StringTokenizer("");
            return innerNextLine();
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    private static class QWriter implements Closeable {
        private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        public void print(Object object) {
            try {
                writer.write(object.toString());
            } catch (IOException e) {
                return;
            }
        }

        public void println(Object object) {
            try {
                writer.write(object.toString());
                writer.write("\n");
            } catch (IOException e) {
                return;
            }
        }

        @Override
        public void close() {
            try {
                writer.close();
            } catch (IOException e) {
                return;
            }
        }
    }
}
