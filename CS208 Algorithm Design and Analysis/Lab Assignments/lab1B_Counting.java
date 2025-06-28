import java.io.*;
import java.util.*;

public class lab1B_Counting {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt(), a = in.nextInt(), b = in.nextInt();
        ArrayList<Integer>[] g = new ArrayList[n + 1];
        boolean[] isVisitedA = new boolean[n + 1];
        boolean[] isVisitedB = new boolean[n + 1];
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt(), v = in.nextInt();
            g[u].add(v);
            g[v].add(u);
        }

        q.offer(b);
        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == a) continue;
            isVisitedB[cur] = true;
            for (int neighbor : g[cur]) {
                if (neighbor == a) continue;
                if (!isVisitedB[neighbor]) q.offer(neighbor);
            }
        }
        isVisitedB[b] = false;
        long countB = 0;
        for (int i = 1; i <= n; i++) {
            if (isVisitedB[i]) countB++;
        }

        q.offer(a);
        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == b) continue;
            isVisitedA[cur] = true;
            for (int neighbor : g[cur]) {
                if (neighbor == b) continue;
                if (!isVisitedA[neighbor]) q.offer(neighbor);
            }
        }
        isVisitedA[a] = false;
        long countA = 0, together = 0;
        for (int i = 1; i <= n; i++) {
            if (isVisitedA[i]) {
                countA++;
                if (isVisitedB[i]) together++;
            }
        }

        countA -= together;
        countB -= together;
        long ans = countA * countB;
        out.println(ans);

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