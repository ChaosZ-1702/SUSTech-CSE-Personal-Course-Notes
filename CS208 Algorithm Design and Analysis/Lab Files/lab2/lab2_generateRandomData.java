import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class lab2_generateRandomData {
    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream("lab2_generator.in");
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            generateGenericTestData(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void generateGenericTestData(int n) {
        System.out.println(n);
        ArrayList<String> m = new ArrayList<>();
        ArrayList<String> f = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("m").append(i);
            m.add(sb.toString());
            System.out.print(sb + " ");
        }
        System.out.println();
        for (int i = 1; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("f").append(i);
            f.add(sb.toString());
            System.out.print(sb + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            //Collections.shuffle(f);
            for (int j = n - 1; j >= 0; j--) {  //int j = n - 1; j >= 0; j--
                System.out.print(f.get(j) + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < n; i++) {
            //Collections.shuffle(m);
            for (int j = n - 1; j >= 0; j--) {  //int j = 0; j < n; j++
                System.out.print(m.get(j) + " ");
            }
            System.out.println();
        }
    }
}
