import java.io.*;
import java.util.*;
public class lab2B_LegendaryGrabbingMachine {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt(), c = in.nextInt(), t = in.nextInt();
        long[] p = new long[n + 1], q = new long[m + 1], v = new long[m + 1];
        for (int i = 1; i <= n; i++) {
            p[i] = in.nextInt();
        }
        for (int i = 1; i <= m; i++) {
            q[i] = in.nextInt();
            v[i] = c;
        }

        Arrays.sort(p, 1, n + 1);
        Arrays.sort(q, 1, m + 1);

        int nestIndex = 1;
        long ans = 0;
        for (int i = 1; i <= n && t >= 0; i++) {
            while (nestIndex <= m) {
                long dist = Math.abs(p[i] - q[nestIndex]);
                if (dist <= t && v[nestIndex] > 0) {
                    v[nestIndex]--;
                    ans++;
                    nestIndex += v[nestIndex] == 0 ? 1 : 0;
                    break;
                }
                else if (p[i] < q[nestIndex] && dist > t) {
                    // if rabbit is on the left side of nest and dist > t, no need to check further
                    break;
                }
                else {  // nest is full, check next
                    nestIndex++;
                }
            }
        }

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
