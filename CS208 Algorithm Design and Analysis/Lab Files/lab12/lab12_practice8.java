public class lab12_practice8 {
    private static long[] fib = new long[10005];
    private static int[][] lcs = new int[10005][10005];
    public static void main(String[] args) {
        System.out.println("dp_fib = " + fibonacci(10));
        System.out.println("rec_fib = " + fibonacci_recursive(10));
        String s1 = "acabbab", s2 = "cbcaaca";
        System.out.println("dp_lcs = " + lcs(s1, s2, 7, 7));
        System.out.println("rec_lcs = " + lcs_recursive(s1, s2, 7, 7));
    }

    private static long fibonacci(int n) {
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i < n; i++) fib[i] = fib[i - 1] + fib[i - 2];
        return fib[n] = fib[n - 1] + fib[n - 2];
    }

    private static long fibonacci_recursive(int n) {
        if (n <= 1) return n;
        else return fibonacci_recursive(n - 1) + fibonacci_recursive(n - 2);
    }

    private static int lcs(String s1, String s2, int m, int n) {
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) lcs[i][j] = 1 + lcs[i - 1][j - 1];
                else lcs[i][j] = Math.max(lcs[i][j - 1], lcs[i - 1][j]);
                //System.out.println("lcs[" + i + "][" + j + "] = " + lcs[i][j]);
            }
        }
        return lcs[m][n];
    }

    private static int lcs_recursive(String s1, String s2, int m, int n) {
        if (m == 0 || n == 0) return 0;
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) return 1 + lcs_recursive(s1, s2, m - 1, n - 1);
        else return Math.max(lcs_recursive(s1, s2, m, n - 1), lcs_recursive(s1, s2, m - 1, n));
    }
}
