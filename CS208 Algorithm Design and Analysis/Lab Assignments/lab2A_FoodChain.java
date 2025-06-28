import java.io.*;
import java.util.*;

public class lab2A_FoodChain {
    private static final long MOD = 1000000007;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt(), m = in.nextInt();
        ArrayList<Integer>[] g = new ArrayList[n + 1];
        ArrayList<Integer>[] rg = new ArrayList[n + 1];
        int[] inDeg = new int[n + 1];
        int[] outDeg = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
            rg[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt(), v = in.nextInt();
            g[u].add(v);
            rg[v].add(u);
            inDeg[v]++;
            outDeg[u]++;
        }

        Queue<Integer> q = new LinkedList<>();
        Queue<Integer> rq = new LinkedList<>();
        long[] p = new long[n + 1];
        long[] rp = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            if (inDeg[i] == 0) {
                q.add(i);
                p[i] = 1;
            }
            if (outDeg[i] == 0) {
                rq.add(i);
                rp[i] = 1;
            }
        }

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : g[u]) {
                p[v] = (p[v] + p[u]) % MOD;
                inDeg[v]--;
                if (inDeg[v] == 0) {
                    q.add(v);
                }
            }
        }
        while (!rq.isEmpty()) {
            int u = rq.poll();
            for (int v : rg[u]) {
                rp[v] = (rp[v] + rp[u]) % MOD;
                outDeg[v]--;
                if (outDeg[v] == 0) {
                    rq.add(v);
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            out.print(((p[i] * rp[i]) % MOD));
            out.print(i < n ? " " : "");
        }

        out.close();
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
