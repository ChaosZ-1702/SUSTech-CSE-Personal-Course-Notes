import java.util.Scanner;

public class lab9_practice6 {
    private static long maxLength = Integer.MIN_VALUE;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Node[] nodes = new Node[n + 1];
        nodes[0] = new Node(0);
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(sc.nextInt());
        }
        while (--n > 0) {
            nodes[sc.nextInt()].addNode(nodes[sc.nextInt()]);
        }   

        dfs(nodes[1]);

        System.out.println(maxLength);
    }

    private static void dfs(Node n) {
        if (n == null) return;
        dfs(n.left);
        dfs(n.right);
        long l = ((n.left == null || n.left.maxL < 0) ? 0 : n.left.maxL), r = ((n.right == null || n.right.maxL < 0) ? 0 : n.right.maxL);
        maxLength = Math.max(maxLength, n.maxL + l + r);
        n.maxL += Math.max(l, r);
    }

    private static class Node {
        int value;
        Node left, right;
        long maxL;

        private Node(int v) {
            this.value = v;
            this.maxL = v;
        }

        private void addNode(Node n) {
            if (this.left == null) this.left = n;
            else if (this.right == null) this.right = n;
        }
    }
}
