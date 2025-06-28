import java.io.*;
import java.util.*;

public class lab4A_OptimizedPhotographyRouteInCampus {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        Edge[] edges = new Edge[m];
        for (int i = 0; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt(), v = in.nextInt();
            long c = in.nextLong();
            edges[i] = new Edge(u, v, c);
            nodes[u].edges.add(edges[i]);
            nodes[v].edges.add(new Edge(v, u, c));
        }

        nodes[1].dist1 = 0;
        PriorityQueue<Node> pq1 = new PriorityQueue<>(Comparator.comparingLong(a -> a.dist1));
        pq1.add(nodes[1]);
        while (!pq1.isEmpty()) {
            Node cur = pq1.poll();
            for (Edge e : cur.edges) {
                Node child = nodes[e.to];
                if (!child.visited1 && cur.dist1 + e.cost < child.dist1) {
                    child.dist1 = cur.dist1 + e.cost;
                    pq1.add(child);
                }
            }
        }

        nodes[n].dist2 = 0;
        PriorityQueue<Node> pq2 = new PriorityQueue<>(Comparator.comparingLong(a -> a.dist2));
        pq2.add(nodes[n]);
        while (!pq2.isEmpty()) {
            Node cur = pq2.poll();
            for (Edge e : cur.edges) {
                Node child = nodes[e.to];
                if (cur.dist2 + e.cost < child.dist2) {
                    child.dist2 = cur.dist2 + e.cost;
                    pq2.add(child);
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            out.println(nodes[i].dist1 + nodes[i].dist2);
        }
        for (int i = 0; i < m; i++) {
            out.println(Math.min(nodes[edges[i].from].dist1 + nodes[edges[i].to].dist2 + edges[i].cost, nodes[edges[i].to].dist1 + nodes[edges[i].from].dist2 + edges[i].cost));
        }

        out.close();
    }

    private static class Node {
        int id;
        long dist1, dist2;
        boolean visited1, visited2;
        ArrayList<Edge> edges;

        private Node(int id) {
            this.id = id;
            this.dist1 = Long.MAX_VALUE;
            this.dist2 = Long.MAX_VALUE;
            this.edges = new ArrayList<>();
        }
    }

    private static class Edge {
        int from, to;
        long cost;

        private Edge(int f, int t, long c) {
            this.from = f;
            this.to = t;
            this.cost = c;
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
