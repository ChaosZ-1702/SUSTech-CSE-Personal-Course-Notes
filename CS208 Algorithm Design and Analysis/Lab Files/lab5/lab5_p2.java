import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class lab5_p2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<interval> intervals = new ArrayList<>();
        while (sc.hasNext()) {
            intervals.add(new interval(sc.next(), sc.nextInt(), sc.nextInt()));
        }
        intervals.sort(Comparator.comparingInt(a -> a.end));
        int lastEnd = intervals.get(0).end;
        System.out.print(intervals.get(0).name + " ");
        for (int i = 1; i < intervals.size(); i++) {
            interval current = intervals.get(i);
            if (current.start >= lastEnd) {
                lastEnd = current.end;
                System.out.print(current.name + " ");
            }
        }
    }

    private static class interval {
        String name;
        int start;
        int end;

        public interval(String name, int start, int end) {
            this.name = name;
            this.start = start;
            this.end = end;
        }
    }
}
