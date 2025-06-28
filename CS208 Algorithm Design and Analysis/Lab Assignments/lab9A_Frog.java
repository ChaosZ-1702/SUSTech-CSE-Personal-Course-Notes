import java.io.*;
import java.util.*;

public class lab9A_Frog {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), L = in.nextInt();
        int ma = in.nextInt(), xa = in.nextInt(), ca = in.nextInt();
        int mb = in.nextInt(), xb = in.nextInt(), cb = in.nextInt();
        HashSet<Integer> s = new HashSet<>();
        s.add(0);
        s.add(L);
        for (int i = 0; i < n; i++) s.add(in.nextInt());

        long ans = Long.MAX_VALUE / 2;
        long[][] opt = new long[xa][xb];
        for (int i = 0; i < xa; i++) {
            for (int j = 0; j < xb; j++) {
                opt[i][j] = Long.MAX_VALUE / 2;
            }
        }
        opt[0][0] = 0;
        for (int i = 0; i < xa; i++) {
            for (int j = 0; j < xb; j++) {
                if (s.contains(i * ma + j * mb)) {
                    if (i > 0 && s.contains((i - 1) * ma + j * mb))
                        opt[i][j] = Math.min(opt[i][j], opt[i - 1][j] + ca);
                    if (j > 0 && s.contains(i * ma + (j - 1) * mb))
                        opt[i][j] = Math.min(opt[i][j], opt[i][j - 1] + cb);
                    if (i * ma + j * mb == L)
                        ans = Math.min(ans, opt[i][j]);
                }
            }
        }

        out.println(ans == Long.MAX_VALUE / 2 ? -1 : ans);
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
