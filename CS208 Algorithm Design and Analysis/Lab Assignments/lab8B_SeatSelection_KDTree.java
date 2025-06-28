import java.io.*;
import java.util.*;

public class lab8B_SeatSelection_KDTree {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        Point[] ps = new Point[n];
        for (int i = 0; i < n; i++) ps[i] = new Point(in.nextDouble(), in.nextDouble());

        Node root = build(ps, 0);
        double minDist = Double.MAX_VALUE;
        for (Point p : ps) minDist = Math.min(solve(root, p), minDist);
        out.print(String.format("%.4f", minDist));
        out.close();

    }

    private static double solve(Node root, Point p) {
        if (root == null) return Double.MAX_VALUE;

        // binary search，create searching path
        Stack<Node> s = new Stack<>();
        Node cur = root, nearest = null;
        double minDist = Double.MAX_VALUE;
        while (cur != null) {
            s.push(cur);
            double curDist = p.getDistFrom(cur.p);
            if (cur.p != p && curDist < minDist) {
                nearest = cur;
                minDist = curDist;
            }
            cur = (cur.dim == 0) ? (p.x <= cur.p.x) ? cur.left : cur.right : (p.y <= cur.p.y) ? cur.left : cur.right;
        }


        // backtrace search
        while (!s.isEmpty()) {
            cur = s.pop();
            if (cur != null) {
                if (cur.p != p) {
                    double curDist = p.getDistFrom(cur.p);
                    nearest = (curDist < minDist) ? cur : nearest;
                    minDist = Math.min(curDist, minDist);
                }
                cur = (cur.dim == 0) ? (p.x <= cur.p.x) ? cur.right : cur.left : (p.y <= cur.p.y) ? cur.right : cur.left;
                Stack<Node> t = new Stack<>();
                while (cur != null) {
                    t.push(cur);
                    double curDist = p.getDistFrom(cur.p);
                    if (cur.p != p && curDist < minDist) {
                        nearest = cur;
                        minDist = curDist;
                    }
                    cur = (cur.dim == 0) ? (p.x <= cur.p.x) ? cur.left : cur.right : (p.y <= cur.p.y) ? cur.left : cur.right;
                }
                while (!t.isEmpty()) {
                    s.push(t.pop());
                }
            }
        }
        //System.out.println("For point (" + p.x + ", " + p.y + "), nearest is ("+ nearest.p.x + ", " + nearest.p.y + "), minDist is " + minDist);
        return minDist;
    }

    private static Node build(Point[] ps, int depth) {
        if (ps.length == 0) return null;

        int dim = depth % 2, mid = ps.length / 2;
        Arrays.sort(ps, (p1, p2) -> (dim == 0) ? Double.compare(p1.x, p2.x) : Double.compare(p1.y, p2.y));

        Node n = new Node(ps[mid], dim);
        n.left = build(Arrays.copyOfRange(ps, 0, mid), depth + 1);
        n.right = build(Arrays.copyOfRange(ps, mid + 1, ps.length), depth + 1);

        return n;
    }

    private static class Node {
        Point p;
        Node left, right;
        int dim;

        private Node(Point p, int d) {
            this.p = p;
            this.left = null;
            this.right = null;
            this.dim = d;
        }
    }

    private static class Point {
        double x, y;

        private Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        private double getDistFrom(Point p) {
            return (p == null) ? Double.MAX_VALUE : Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
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
