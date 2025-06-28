import java.io.*;
import java.util.*;

public class lab10A_CookieOptimizationAtSUSTech {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt();
        long[] opt = new long[n + 1];
        opt[0] = 0;
        for (int i = 1; i <= n; i++) opt[i] = Long.MIN_VALUE;

        while (m-- > 0) {
            switch (in.nextInt()) {
                case 0: {  // unbounded
                    int c = in.nextInt(), v = in.nextInt();
                    for (int i = c; i <= n; i++)
                        if (opt[i - c] != Long.MIN_VALUE) opt[i] = Math.max(opt[i], opt[i - c] + v);
                    break;
                }
                case 1: {  // 01
                    int c = in.nextInt(), v = in.nextInt();
                    for (int i = n; i >= c; i--)
                        if (opt[i - c] != Long.MIN_VALUE) opt[i] = Math.max(opt[i], opt[i - c] + v);
                    break;
                }
                case 2: {  // bounded
                    int x = in.nextInt(), c = in.nextInt(), v = in.nextInt();
                    for (int i = 1; x > 0; i *= 2) {
                        if (x < i) i = x;
                        int C = c * i, V = v * i;
                        if (C > n) continue;
                        for (int j = n; j >= C; j--)
                            if (opt[j - C] != Long.MIN_VALUE) opt[j] = Math.max(opt[j], opt[j - C] + V);
                        x -= i;
                    }
                }
            }
        }

        out.println(opt[n] == Long.MIN_VALUE ? 0 : opt[n]);
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
