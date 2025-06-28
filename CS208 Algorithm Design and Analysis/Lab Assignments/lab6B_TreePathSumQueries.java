import java.io.*;
import java.util.*;

public class lab6B_TreePathSumQueries {
    private static int n, k;
    private static boolean found = false;
    private static ArrayList<int[]>[] adj;
    private static int[] size;
    private static HashSet<Integer> dis, seq;
    private static boolean[] isVisited;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        n = in.nextInt();
        k = in.nextInt();
        adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) adj[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            int u = in.nextInt(), v = in.nextInt(), w = in.nextInt();
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
        }

        isVisited = new boolean[n + 1];
        solve(1);
        out.println(found ? "Yes" : "No");

        out.close();
    }

    private static void solve(int node) {
        if (found || isVisited[node]) return;
        size = new int[n + 1];
        dfsCent(node, 0);
        int c = findCent(node, size[node]);
        isVisited[c] = true;

        dis = new HashSet<>();
        for (int[] pair : adj[c]) {
            if (!isVisited[pair[0]]) {
                if (pair[1] == k) {
                    found = true;
                    return;
                }
                seq = new HashSet<>();
                dfs(pair[0], c, pair[1]);
                if (seq.contains(k)) {
                    found = true;
                    return;
                }
                for (int cur : seq) {
                    if (dis.contains(k - cur)) {
                        found = true;
                        return;
                    }
                }
                dis.addAll(seq);
            }
        }

        for (int[] pair : adj[c]) if (!isVisited[pair[0]]) solve(pair[0]);
    }

    private static void dfs(int node, int parent, int curDis) {
        if (found) return;
        seq.add(curDis);
        for (int[] pair : adj[node]) {
            int cur = pair[0], dis = pair[1];
            if (dis == k) {
                found = true;
                return;
            }
            if (cur == parent || isVisited[cur]) continue;
            dfs(cur, node, curDis + dis);
        }
    }

    private static int findCent(int node, int totalSize) {
        int next = node;
        boolean[] visited = new boolean[n + 1];
        while (true) {
            int maxSize = totalSize - size[node];
            visited[node] = true;
            for (int[] pair : adj[node]) {
                int cur = pair[0];
                if (!visited[cur] && !isVisited[cur] && size[cur] > maxSize) {
                    maxSize = size[cur];
                    next = cur;
                }
            }
            if (maxSize <= totalSize / 2) return node;
            node = next;
        }
    }

    private static void dfsCent(int node, int parent) {
        size[node] = 1;
        for (int[] pair : adj[node]) {
            if (pair[0] == parent || isVisited[pair[0]]) continue;
            dfsCent(pair[0], node);
            size[node] += size[pair[0]];
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
