import java.io.*;
import java.util.*;

public class lab7B_OverlappingRectangles {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt();
        Rectangle[] rs = new Rectangle[n];
        for (int i = 0; i < n; i++) rs[i] = new Rectangle(in.nextLong(), in.nextLong(), in.nextLong());

        ArrayList<long[]> ans = solve(rs, n);
        for (long[] pair : ans) out.println(pair[0] + " " + pair[1]);

        out.close();
    }

    private static ArrayList<long[]> solve(Rectangle[] rs, int n) {
        if (n == 0) return new ArrayList<>();
        if (n == 1) return rs[0].set;
        int p = n / 2;
        Rectangle[] left = new Rectangle[p], right = new Rectangle[n - p];
        System.arraycopy(rs, 0, left, 0, p);
        if (n - p > 0) System.arraycopy(rs, p, right, 0, n - p);
        ArrayList<long[]> LSet = solve(left, p), RSet = solve(right, n - p);
        return merge(LSet, LSet.size(), RSet, RSet.size());
    }

    private static ArrayList<long[]> merge(ArrayList<long[]> left, int ll, ArrayList<long[]> right, int lr) {
        int i = 0, j = 0;
        long leftPreHeight = 0, rightPreHeight = 0;
        ArrayList<long[]> MSet = new ArrayList<>();

        while (i < ll && j < lr) {
            if (left.get(i)[0] < right.get(j)[0]) {  // left is min
                if (left.get(i)[1] > rightPreHeight) MSet.add(new long[]{left.get(i)[0], left.get(i)[1]});
                else {
                    if (leftPreHeight > rightPreHeight) MSet.add(new long[]{left.get(i)[0], rightPreHeight});
                }
                leftPreHeight = left.get(i++)[1];
            }
            else if (right.get(j)[0] < left.get(i)[0]) {  // right is min
                if (right.get(j)[1] > leftPreHeight) MSet.add(new long[]{right.get(j)[0], right.get(j)[1]});
                else {
                    if (rightPreHeight > leftPreHeight) MSet.add(new long[]{right.get(j)[0], leftPreHeight});
                }
                rightPreHeight = right.get(j++)[1];
            }
            else {  // same
                if (Math.max(leftPreHeight, rightPreHeight) != Math.max(left.get(i)[1], right.get(j)[1]))
                    MSet.add(new long[]{left.get(i)[0], Math.max(left.get(i)[1], right.get(j)[1])});
                leftPreHeight = left.get(i++)[1];
                rightPreHeight = right.get(j++)[1];
            }
        }

        long preHeight = Math.max(leftPreHeight, rightPreHeight);
        while (i < ll) {
            if (left.get(i)[1] != preHeight) {
                MSet.add(new long[]{left.get(i)[0], left.get(i)[1]});
                preHeight = left.get(i)[1];
            }
            i++;
        }
        while (j < lr) {
            if (right.get(j)[1] != preHeight) {
                MSet.add(new long[]{right.get(j)[0], right.get(j)[1]});
                preHeight = right.get(j)[1];
            }
            j++;
        }

        return MSet;
    }

    private static class Rectangle {
        long left, right, height;
        ArrayList<long[]> set = new ArrayList<>();
        private Rectangle(long l, long r, long h) {
            this.left = l;
            this.right = r;
            this.height = h;
            set.add(new long[]{l, h});  // Set[0].x, Set[0].y
            set.add(new long[]{r, 0});  // Set[1].x, Set[1].y
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
