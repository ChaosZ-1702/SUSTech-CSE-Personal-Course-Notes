import java.io.*;
import java.util.*;

public class lab9B_MilkCandy {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt();
        int[] s = new int[m], p = new int[m];
        for (int i = 0; i < m; i++) {
            p[i] = in.nextInt();
            s[i] = in.nextInt();
        }

        long ans = Long.MIN_VALUE;
        int ansMoney = -1;
        long[] opt = new long[n + 1];
        int[] c = new int[n + 1];
        for (int i = 0; i <= n; i++) c[i] = -1;
        // is it possible opt[i] > opt[j] when i < j?
        for (int i = 0; i < m; i++) {
            for (int j = p[i]; j <= n; j++) {
                if (opt[j - p[i]] + s[i] > opt[j]) {
                    opt[j] = opt[j - p[i]] + s[i];
                    c[j] = i;
                }
                if (opt[j] > ans) {
                    ans = opt[j];
                    ansMoney = j;
                }
            }
        }

        int cur;
        int[] cnt = new int[m];
        while (ansMoney > 0) {
            cur = c[ansMoney];
            cnt[cur]++;
            ansMoney -= p[cur];
        }
        out.println(ans);
        for (int i = 0; i < m; i++) out.println(cnt[i]);

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
