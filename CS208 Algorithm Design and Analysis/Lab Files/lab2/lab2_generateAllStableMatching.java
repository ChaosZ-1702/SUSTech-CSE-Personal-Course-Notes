import java.util.*;

public class lab2_generateAllStableMatching {
    private static int n = 100000;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        ArrayList<Person> boyList = new ArrayList<>(n);
        ArrayList<Person> girlList = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) { boyList.add(new Person(sc.next())); }
        for (int i = 1; i <= n; i++) { girlList.add(new Person(sc.next())); }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) { boyList.get(i).preferenceList.add(sc.next()); }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) { girlList.get(i).preferenceList.add(sc.next()); }
        }

        ArrayList<String> matchingList = new ArrayList<>();
        generateAllStableMatching(0, boyList, girlList, matchingList);
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
                    for (Person boy : boys) {
                        System.out.println(boy.getName() + " " + boy.getCurrentPairing().getName());
                    }
                    System.out.println();
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
                    if (boy.comparePersonRank(girl.getName(), boy.getCurrentPairing().getName()) && girl.comparePersonRank(boy.getName(), girl.getCurrentPairing().getName())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected static class Person {
        String name;
        ArrayList<String> preferenceList;
        Person currentPairing;

        private Person(String name) {
            this.name = name;
            this.preferenceList = new ArrayList<>(n);
            this.currentPairing = null;
        }

        protected String getName() {
            return this.name;
        }

        /**
         * @param s The person who is being found
         * @return The index of the person in {@code preferenceList}, {@code -1} if the person is not in {@code preferenceList}.
         */
        private int getPersonRank(String s) {
            return preferenceList.indexOf(s);
        }

        /**
         * @param s1 The person who is compared
         * @param s2 The person who compares
         * @return {@code true} if the rank of s1 is smaller than s2, otherwise {@code false}.
         */
        private boolean comparePersonRank(String s1, String s2) {
            return preferenceList.indexOf(s1) < preferenceList.indexOf(s2);
        }

        private void pairWith(Person p) {
            this.currentPairing = p;
        }

        private Person getCurrentPairing() {
            return this.currentPairing;
        }
    }
}
