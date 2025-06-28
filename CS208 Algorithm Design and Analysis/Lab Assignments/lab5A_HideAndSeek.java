import java.io.*;
import java.util.*;

public class lab5A_HideAndSeek {
    private static ArrayList<Integer>[] adj;
    private static final int[] lg = new int[100008];  // the time that node at depth i need to jump to root
    private static int[][] father;
    private static int[] depth, subSize;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt();
        father = new int[n + 1][18];
        depth = new int[n + 1];
        subSize = new int[n + 1];
        adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            int u = in.nextInt(), v = in.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        for (int i = 2; i <= n; i++) lg[i] = lg[i >> 1] + 1;
        dfs(0, 1);

        int q = in.nextInt();
        while (q-- > 0) {
            int x = in.nextInt(), y = in.nextInt();
            if (x == y) {
                out.println(n);
                continue;
            }

            int lca = lca(x, y), dis = depth[x] + depth[y] - 2 * depth[lca];
            if (dis % 2 != 0) {
                out.println(0);
                continue;
            }

            int d = dis / 2, mid;
            if (depth[x] - depth[lca] >= d) mid = getAncestor(x, d);  // mid point at x side
            else mid = getAncestor(y, dis - d);  // mid point at y side
            int X = getNeighbour(x, mid), Y = getNeighbour(y, mid);
            long sizeX = (X == father[0][mid]) ? (n - subSize[mid]) : subSize[X];
            long sizeY = (Y == father[0][mid]) ? (n - subSize[mid]) : subSize[Y];
            out.println(n - sizeX - sizeY);
        }
        out.close();
    }

    private static int getNeighbour(int n, int m) {
        return lca(n, m) == m
                ? depth[n] - depth[m] - 1 == 0
                    ? n
                    : getAncestor(n, depth[n] - depth[m] - 1)
                : father[m][0];
    }

    private static int getAncestor(int n, int k) {
        for (int i = 0; i < 18; i++) {
            if (((k >> i) & 1) != 0) {
                n = father[n][i];
            }
        }
        return n;
    }

    private static int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        while (depth[u] > depth[v]) u = father[u][lg[depth[u] - depth[v]]];
        if (u == v) return u;  // if u is the ancestor of v...

        for (int i = 17; i >= 0; i--) {
            if (father[u][i] != father[v][i]) {
                u = father[u][i];
                v = father[v][i];
            }
        }
        return father[u][0];
    }

    private static void dfs(int fa, int cur) {
        father[cur][0] = fa;
        depth[cur] = depth[fa] + 1;
        subSize[cur] = 1;

        for (int i = 1; i < 18; i++)
            if (father[cur][i - 1] != 0)
                father[cur][i] = father[father[cur][i - 1]][i - 1];

        for (int child : adj[cur]) {
            if (child != fa) {
                dfs(cur, child);
                subSize[cur] += subSize[child];
            }
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

        public double nextDouble() {
            return Double.parseDouble(next());
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
