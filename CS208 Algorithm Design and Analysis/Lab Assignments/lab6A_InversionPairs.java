import java.io.*;
import java.util.*;

public class lab6A_InversionPairs {
    private static long cost = 0;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt();
        int[] s = new int[n];
        for (int i = 0; i < n; i++) s[i] = in.nextInt();
        mergeSort(s, n);
        out.println(cost);

        out.close();
    }

    private static int[] mergeSort(int[] a, int n) {
        if (n > 1) {
            int p = n / 2;
            int[] left = new int[p], right = new int[n - p];
            System.arraycopy(a, 0, left, 0, p);
            if (n - p > 0) System.arraycopy(a, p, right, 0, n - p);
            left = mergeSort(left, p);
            right = mergeSort(right, n - p);
            return merge(left, p, right, n - p);
        }
        return a;
    }

    private static int[] merge(int[] left, int ll, int[] right, int lr) {
        int n = ll + lr;
        int[] a = new int[n];
        int i = 0, j = 0;
        for (int k = 0; k < n; k++) {
            if (i < ll && (j >= lr || left[i] <= right[j])) {
                a[k] = left[i];
                i++;
            }
            else {
                a[k] = right[j];
                cost += ll - i;
                j++;
            }
        }
        return a;
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