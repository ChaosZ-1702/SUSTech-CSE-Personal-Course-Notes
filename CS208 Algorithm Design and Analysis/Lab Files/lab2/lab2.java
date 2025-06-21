import java.util.*;
import java.io.*;

public class lab2 {
    private static final QReader in = new QReader();
    private static final QWriter out = new QWriter();
    private static int n = 100000;
    public static void main(String[] args) {
        /*
            Unstable matching: There exist a boy and a girl who satisfies:
            1. They are not paired with each other in the output
            2. boy.getPersonRank(boy.getCurrentPairing) > boy.getPersonRank(girl)
            3. girl.getPersonRank(girl.getCurrentPairing) > girl.getPersonRank(boy)
         */
        long startTime = System.currentTimeMillis();
        n = in.nextInt();
        LinkedHashMap<String, Person> boys = new LinkedHashMap<>();
        LinkedHashMap<String, Person> girls = new LinkedHashMap<>();
        arrayBasedDeque unmatchedBoys = new arrayBasedDeque(n);
        for (int i = 0; i < n; i++) {
            Person boy = new Person(in.next());
            boy.id = i;
            boys.put(boy.getName(), boy);
            unmatchedBoys.pushRear(boy);
        }
        for (int i = 0; i < n; i++) {
            Person girl = new Person(in.next());
            girl.id = i;
            girls.put(girl.getName(), girl);
        }
        boys.forEach((name, boy) -> {
            for (int i = 0; i < n; i++) {
                Person girl = girls.get(in.next());
                boy.preferenceList.pushRear(girl);
                boy.ranks[girl.id] = i;
            }
        });
        girls.forEach((name, girl) -> {
            for (int i = 0; i < n; i++) {
                Person boy = boys.get(in.next());
                girl.preferenceList.pushRear(boy);
                girl.ranks[boy.id] = i;
            }
        });

        while (!unmatchedBoys.isEmpty()) {
            Person boy = unmatchedBoys.peekFront();
            unmatchedBoys.popFront();
            if (!boy.preferenceList.isEmpty()) {
                Person girl = boy.preferenceList.peekFront();
                Person girlCurrentPairing = girl.currentPairing;
                if (girlCurrentPairing == null) {
                    boy.pairWith(girl);
                    girl.pairWith(boy);
                }
                else if (girl.comparePersonRank(boy, girlCurrentPairing)) {
                    boy.pairWith(girl);
                    girl.pairWith(boy);
                    girlCurrentPairing.pairWith(null);
                    girlCurrentPairing.isRefused();
                    unmatchedBoys.pushRear(girlCurrentPairing);
                }
                else {
                    boy.isRefused();
                    unmatchedBoys.pushRear(boy);
                }
            }
        }

        boys.forEach((name, boy) -> out.println(name + " " + boy.getCurrentPairing().getName()));
        //out.println((isStable(n, boyList, girlList) ? "This matching is stable." : "This matching is unstable."));
        long endTime = System.currentTimeMillis();
        out.println(endTime - startTime);
        out.println("");
        out.close();

    }

    private static void generateAllStableMatching(int n, List<Person> boys, List<Person> girls, List<String> matchingList) {
        if (n == boys.size()) {
            if (isStable(n, boys, girls)) {
                StringBuilder matching = new StringBuilder();
                for (Person boy : boys) {
                    matching.append(boy.getName()).append("-").append(boy.getCurrentPairing().getName()).append(";");
                }
                if (!matchingList.contains(matching.toString())) {
                    matchingList.add(matching.toString());
                    out.println("A possible stable matching:");
                    for (Person boy : boys) {
                        out.println(boy.getName() + " " + boy.getCurrentPairing().getName());
                    }
                }
            }
            return;
        }
        for (Person boy : boys) {
            if (boy.getCurrentPairing() == null) {
                for (Person girl : girls) {
                    if (girl.getCurrentPairing() == null) {
                        boy.pairWith(girl);
                        girl.pairWith(boy);
                        generateAllStableMatching(n + 1, boys, girls, matchingList);
                        boy.pairWith(null);
                        girl.pairWith(null);
                    }
                }
            }
        }
    }

    private static boolean isStable(int n, List<Person> boys, List<Person> girls) {
        for (Person boy : boys) {
            for (Person girl : girls) {
                if (boy.getCurrentPairing() != girl) {
                    if (boy.comparePersonRank(girl, boy.getCurrentPairing()) && girl.comparePersonRank(boy, girl.getCurrentPairing())) {
                        //out.println("There is a rogue people between " + boy.getName() + " " + girl.getName() + " before returning false.");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected static class Person {
        int id;
        String name;
        arrayBasedDeque preferenceList;
        int[] ranks;
        Person currentPairing;

        private Person(String name) {
            this.name = name;
            this.preferenceList = new arrayBasedDeque(n);
            this.ranks = new int[n];
            this.currentPairing = null;
        }

        protected String getName() {
            return this.name;
        }

        /**
         * @param p The person who is being found
         * @return The index of the person in {@code preferenceList}, {@code -1} if the person is not in {@code preferenceList}.
         */
        private int getPersonRank(Person p) {
            if (preferenceList.isEmpty()) return -1;
            for (int i = 0; i < preferenceList.getSize(); i++) {
                if (preferenceList.data[i] == p) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * @param p1 The person who is compared
         * @param p2 The person who compares
         * @return {@code true} if the rank of p1 is smaller than p2, otherwise {@code false}.
         */
        private boolean comparePersonRank(Person p1, Person p2) {
//            if (preferenceList.isEmpty()) return false;
//            for (int i = 0; i < preferenceList.getSize(); i++) {
//                if (preferenceList.data[i] == p1 || preferenceList.data[i] == p2) {
//                    return preferenceList.data[i] == p1;
//                }
//            }
//            return false;
            return ranks[p1.id] < ranks[p2.id];
        }

        private void pairWith(Person p) {
            this.currentPairing = p;
        }

        private Person getCurrentPairing() {
            return this.currentPairing;
        }

        private void isRefused() { this.preferenceList.popFront(); }
    }

    private static class arrayBasedDeque {
        private Person[] data;
        private int front;
        private int rear;
        private int size;
        private int MAX_SIZE;

        public arrayBasedDeque(int size) {
            this.MAX_SIZE = size;
            this.data = new Person[size];
            this.front = -1;
            this.rear = -1;
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == MAX_SIZE;
        }

        public void pushFront(Person p) {
            if (isFull()) {
                return;
            }
            if (isEmpty()) {
                front = rear = 0;
            } else {
                front = (front - 1 + MAX_SIZE) % MAX_SIZE;
            }
            data[front] = p;
            size++;
        }

        public void pushRear(Person p) {
            if (isFull()) {
                return;
            }
            if (isEmpty()) {
                front = rear = 0;
            } else {
                rear = (rear + 1) % MAX_SIZE;
            }
            data[rear] = p;
            size++;
        }

        public void popFront() {
            if (isEmpty()) {
                return;
            }
            if (front == rear) {
                front = rear = -1;
            } else {
                front = (front + 1) % MAX_SIZE;
            }
            size--;
        }

        public void popRear() {
            if (isEmpty()) {
                return;
            }
            if (front == rear) {
                front = rear = -1;
            } else {
                rear = (rear - 1 + MAX_SIZE) % MAX_SIZE;
            }
            size--;
        }

        public Person peekFront() {
            if (isEmpty()) {
                return null;
            }
            return data[front];
        }

        public Person peekRear() {
            if (isEmpty()) {
                return null;
            }
            return data[rear];
        }

        public int getSize() {
            return size;
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
