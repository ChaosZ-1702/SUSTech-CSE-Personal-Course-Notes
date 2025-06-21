import java.util.*;

public class lab1 {
    public static int[] memo = new int[100005];
    public static boolean[] choose = new boolean[100005];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        permutations("abc", sb, choose);
        sb.setLength(0);
        combinations("abc", 0, sb);
        //towerOfHanoi(3, 'A', 'B', 'C');
        //System.out.println(climbing(5));
        //System.out.println(tiling(5));
    }

    static void towerOfHanoi(int n, char from_rod, char to_rod, char aux_rod) {
        if (n == 1) {
            System.out.println("Move disk 1 from rod " + from_rod + " to rod " + to_rod);
            return;
        }
        towerOfHanoi(n - 1, from_rod, aux_rod, to_rod);
        System.out.println("Move disk " + n + " from rod " + from_rod + " to rod " + to_rod);
        towerOfHanoi(n - 1, aux_rod, to_rod, from_rod);
    }

    private static int climbing(int n) {
        if (n <= 0) return 0;
        if (n <= 2) return n;
        if (memo[n] != 0) return memo[n];
        return climbing(n - 1) + climbing(n - 2);
    }

    private static int tiling(int n) {
        if (n < 0) return 0;
        if (n <= 2) return n;
        if (memo[n] != 0) return memo[n];
        return tiling(n - 1) + tiling(n - 2);
    }

    public static void permutations(String s, StringBuilder sb, boolean[] choose) {
        if (sb.length() == s.length()) {
            System.out.println(sb);
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!choose[i]) {
                sb.append(s.charAt(i));
                choose[i] = true;

                permutations(s, sb, choose);

                sb.deleteCharAt(sb.length() - 1);
                choose[i] = false;
            }
        }
    }

    public static void combinations(String s, int index, StringBuilder sb) {
        if (sb.length() > 0) {
            System.out.println(sb);
        }
        for (int i = index; i < s.length(); i++) {
            sb.append(s.charAt(i));
            combinations(s, i + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

}
