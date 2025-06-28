import java.io.*;
import java.util.*;

public class lab4B_OptimizedDeliveryNetworkForCampusExpressStations {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        for (int i = 0; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt(), v = in.nextInt();
            long c = in.nextLong();
            Edge e = new Edge(u, v, c);
            nodes[u].outEdges.add(e);
            nodes[v].inEdges.add(e);
        }
        
        nodes[1].dist = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.dist));
        pq.add(nodes[1]);
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            Node child;
            for (Edge e : cur.outEdges) {
                child = nodes[e.to];
                if (!child.visited && cur.dist + e.cost < child.dist) {
                    child.dist = cur.dist + e.cost;
                    pq.add(child);
                }
            }
        }

        long ans = 0;
        for (int i = 2; i <= n; i++) {
            long minCost = Long.MAX_VALUE;
            for (Edge e : nodes[i].inEdges) {
                if (nodes[e.from].dist + e.cost == nodes[i].dist && e.cost < minCost) {
                    minCost = e.cost;
                }
            }
            ans += minCost;
        }
        out.println(ans);

        out.close();
    }

    private static class Node {
        int id;
        long dist;
        boolean visited;
        ArrayList<Edge> outEdges, inEdges;

        private Node(int id) {
            this.id = id;
            this.dist = Long.MAX_VALUE;
            this.outEdges = new ArrayList<>();
            this.inEdges = new ArrayList<>();
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
