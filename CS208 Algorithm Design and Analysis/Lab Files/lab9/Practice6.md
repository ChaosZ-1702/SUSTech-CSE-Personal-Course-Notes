## Practice 6 Max Path in Tree Use Divide-and-Conquer

#### Algorithm (Java Code)

```java
import java.util.Scanner;

public class lab9 {
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

```

#### Explanation

For every subtree with $u$ as its root, there are two situations:

1. the path through $u$

   a) the longest path is in $u$'s left/right subtree
   b) the longest path "hang" on $u$

2. The path do not through $u$

Use `maxLength` to store the length of the longest path in the tree.

In every node, use `maxL` to store the length of the longest one-sided path through $u$, with the value of the node as its initial value.

`dfs` calculates the length of the longest path in the left and right subtree recursively, updates `maxLength` if the path through $u$ is longer than the current value, and updates `maxL` of current node(choose the longer side, or just itself if both side is negative value) for upper-level nodes' calculation.

#### Complexity Analysis

Line 9 - 12: $O(n)$.

Line 13 - 15: $O(n)$.

Line 17: Traverse each node, where each node is visited only once. $O(n)$.

Time Complexity: $O(n)$.