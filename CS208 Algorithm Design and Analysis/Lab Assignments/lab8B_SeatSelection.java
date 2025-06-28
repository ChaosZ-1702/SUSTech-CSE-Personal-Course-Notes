import java.io.*;
import java.util.*;

public class lab8B_SeatSelection {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        Point[] ps = new Point[n];
        for (int i = 0; i < n; i++) ps[i] = new Point(in.nextDouble(), in.nextDouble());
        //System.out.println(System.currentTimeMillis());
        Arrays.sort(ps, Comparator.comparingDouble(p -> p.x));
        double minDist = solve2(ps, 0, n - 1);
        out.print(String.format("%.4f", Math.sqrt(minDist)));
        //System.out.println(System.currentTimeMillis());
        out.close();
    }

    private static double solve2(Point[] ps, int l, int r) {
        if (r - l + 1 <= 3) {
            double minDist2 = Double.MAX_VALUE;
            for (int i = l; i <= r; i++) {
                for (int j = i + 1; j <= r; j++) {
                    minDist2 = Math.min(ps[i].getDist2From(ps[j]), minDist2);
                }
            }
            Arrays.sort(ps, l, r + 1, Comparator.comparingDouble(p -> p.y));
            return minDist2;
        }
        else {
            int m = (l + r) / 2;
            double x = ps[m].x;
            double d2 = Math.min(solve2(ps, l, m), solve2(ps, m + 1, r)), d = Math.sqrt(d2);

            int left = l, right = m + 1, index = 0;
            Point[] temp = new Point[r - l + 1];
            while (left <= m && right <= r) {
                if (ps[left].y <= ps[right].y) temp[index++] = ps[left++];
                else temp[index++] = ps[right++];
            }
            while (left <= m) temp[index++] = ps[left++];
            while (right <= r) temp[index++] = ps[right++];
            System.arraycopy(temp, 0, ps, l, index);

            Point[] pss = new Point[r - l + 1];
            index = 0;
            for (int i = l; i <= r; i++) {
                if (Math.abs(ps[i].x - x) < d) pss[index++] = ps[i];
            }

            for (int i = 0; i < index; i++) {
                for (int j = i + 1; j < index && pss[j].y - pss[i].y < d; j++) {
                    d2 = Math.min(d2, pss[i].getDist2From(pss[j]));
                }
            }

            return d2;
        }
    }


    private static class Point {
        double x, y;

        private Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        private double getDist2From(Point p) {
            return (p == null) ? Double.MAX_VALUE : (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y);
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
