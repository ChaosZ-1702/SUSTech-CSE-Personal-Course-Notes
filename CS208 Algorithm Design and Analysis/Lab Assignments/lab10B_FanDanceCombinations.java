import java.io.*;
import java.util.*;

public class lab10B_FanDanceCombinations {
    private static final int MOD = 99824353;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt();
        int[][] bounds = new int[n + 1][2];  // 0: left, 1: right
        // In general, the i-th fan can only appear in [i-m, i+m] place at the end.
        for (int i = 1; i <= n; i++) {
            // How many fans can be in first i places at least/most?
            // if position_of_a_fan + m <= i, then it must be in [1, i] at the end.
            bounds[i][0] = Math.max(0, i - m);
            // if position_of_a_fan - m > i, then it must be in [i+1, n] at the end.
            // there are n - (i + m) such fans.
            // then the number of fans_that_may_be_in_first_i_places = n - (n - (i + m)) = i + m
            bounds[i][1] = Math.min(n, i + m);
        }

        // Let dp[i][j] denote the count of ways for the first i student to hold j fans.
        // state transition:
        // dp[i][j] = sum(dp[i-1][j-k]) why?
        // assume that when we move from [i-1] to [i], the dancer in i-th position can hold k fans.
        // k can be 0 or more, but it must be in the range of [bounds[i][0], bounds[i][1]].
        // So, we can sum all the ways of the previous dancer to hold j-k fans.
        long[][] dp = new long[n + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = 0;
                if (j >= bounds[i][0] && j <= bounds[i][1]) {
                    // if j is in the range of [bounds[i][0], bounds[i][1]], then we can hold it.
                    for (int k = 0; k <= j; k++) {
                        dp[i][j] += dp[i - 1][j - k];
                    }
                    dp[i][j] %= MOD;
                }
            }
        }

        /*for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                out.print(dp[i][j] + " ");
            }
            out.println("");
        }*/
        // dp[n][n] is the answer.
        out.println(dp[n][n]);
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
