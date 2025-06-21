import java.io.*;
import java.util.*;

public class lab3B_TaskSchedule {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt();
        task[] tasks = new task[n];
        slot[] slots = new slot[m];
        for (int i = 0; i < n; i++) { tasks[i] = new task(in.nextInt(), in.nextInt()); }
        for (int i = 0; i < m; i++) { slots[i] = new slot(in.nextInt(), in.nextInt()); }

        Arrays.sort(slots, (s1, s2) -> {
            if (s1.left != s2.left) return Integer.compare(s1.left, s2.left);
            else return Integer.compare(s1.right, s2.right);
        });

        int[] prefix = new int[m];
        prefix[0] = slots[0].right;
        for (int i = 1; i < m; i++) {
            prefix[i] = Math.max(slots[i].right, prefix[i - 1]);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int l = 0, r = slots.length - 1, target = -1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (slots[mid].left <= tasks[i].start) {  // find the slot whose left closest to start time
                    target = mid;
                    l = mid + 1;
                }
                else r = mid - 1;
            }
            if (target != -1 && prefix[target] >= tasks[i].end) ans++;
        }
        out.println(ans);

        out.close();
    }

    private static class slot {
        int left, right;

        private slot(int l, int r) {
            this.left = l;
            this.right = r;
        }
    }

    private static class task {
        int start, end;

        private task(int s, int e) {
            this.start = s;
            this.end = e;
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
