import java.io.*;
import java.util.*;
public class lab3A_LRUCache {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt(), m = in.nextInt();
        LRUCache c = new LRUCache(n);
        for (int i = 0; i < m; i++) {
            String op = in.next();
            if (op.equals("put")) {
                int k = in.nextInt();
                int v = in.nextInt();
                c.put(k, v);
            } else if (op.equals("get")) {
                int k = in.nextInt();
                out.println(c.get(k));
            }
        }

        out.close();
    }

    private static class LRUCache {
        private final int MAX_SIZE;
        private final HashMap<Integer, Node> cache;
        private final DoubleLinkedList list;

        private LRUCache(int s) {
            this.MAX_SIZE = s;
            this.cache = new HashMap<>();
            this.list = new DoubleLinkedList();
        }

        private int get(int k) {
            if (cache.containsKey(k)) {
                Node node = cache.get(k);
                list.moveToFront(node);
                return node.value;
            }
            return -1;
        }

        private void put(int k, int v) {
            if (cache.containsKey(k)) {
                Node node = cache.get(k);
                node.value = v;
                list.moveToFront(node);
            } else {
                if (cache.size() >= MAX_SIZE) {
                    Node tail = list.removeEnd();
                    cache.remove(tail.key);
                }
                Node newNode = new Node(k, v);
                cache.put(k, newNode);
                list.addFront(newNode);
            }
        }
    }

    private static class DoubleLinkedList {
        private final Node head;
        private final Node tail;

        private DoubleLinkedList() {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        private void addFront(Node node) {
            head.next.prev = node;
            node.next = head.next;
            node.prev = head;
            head.next = node;
        }

        private void remove(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }

        private Node removeEnd() {
            Node lastNode = tail.prev;
            remove(lastNode);
            return lastNode;
        }

        private void moveToFront(Node node) {
            remove(node);
            addFront(node);
        }
    }

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        private Node(int k, int v) {
            this.key = k;
            this.value = v;
        }
    }

    private static class QReader {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
        private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        public void print(Object object) {
            try {
                writer.write(object.toString());
            } catch (IOException e) {
            }
        }

        public void println(Object object) {
            try {
                writer.write(object.toString());
                writer.write("\n");
            } catch (IOException e) {
            }
        }

        @Override
        public void close() {
            try {
                writer.close();
            } catch (IOException e) {
            }
        }
    }
}
