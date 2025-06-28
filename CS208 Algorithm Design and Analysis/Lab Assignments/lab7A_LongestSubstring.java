import java.io.*;
import java.util.*;

public class lab7A_LongestSubstring {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        String s = in.next();
        int k = in.nextInt();

        out.println(solve(s, k));

        out.close();
    }

    private static int solve(String s, int k) {
        HashMap<Character, Integer> appTimes = new HashMap<>();
        for (char c : s.toCharArray()) appTimes.put(c, appTimes.getOrDefault(c, 0) + 1);

        int cur = 0;
        boolean valid = true;
        ArrayList<String> subs = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (appTimes.get(s.charAt(i)) < k) {
                valid = false;
                subs.add(s.substring(cur, i));
                cur = i + 1;
            }
        }
        if (cur < s.length()) subs.add(s.substring(cur));

        if (valid) return s.length();

        int max = Integer.MIN_VALUE;
        for (String sub : subs) max = Math.max(max, solve(sub, k));

        return max;
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