import java.io.*;
import java.util.*;

public class lab8A_FFT {
    private static int N;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        N = in.nextInt();
        Complex[] s = new Complex[1 << N];
        for (int i = 0; i < s.length; i++) s[i] = new Complex(in.nextDouble(), 0);
        rev(s);
        for (int n = 1; n < s.length; n *= 2) {  // spacing
            for (int j = 0; j < s.length; j += n * 2) {  //
                for (int k = 0; k < n; k++) {
                    Complex temp = s[j + k + n].mul(new Complex(Math.cos(Math.PI * k / n), -Math.sin(Math.PI * k / n)));
                    s[j + k + n] = s[j + k].sub(temp);
                    s[j + k] = s[j + k].add(temp);
                }
            }
        }
        for (Complex c : s) System.out.printf("%.10f\n", c.getMod());
        out.close();
    }

    private static void rev(Complex[] c) {
        for (int i = 0; i < c.length; i++) {
            int r = bitRev(i);
            if (i < r) {
                Complex temp = c[i];
                c[i] = c[r];
                c[r] = temp;
            }
        }
    }

    private static int bitRev(int n) {
        int r = 0;
        for (int i = 0; i < N; i++) {
            r = (r << 1) | (n & 1);
            n >>= 1;
        }
        return r;
    }

    private static class Complex {
        double a, b;

        private Complex(double a, double b) {
            this.a = a;
            this.b = b;
        }
        // why there is no operator overload?
        private Complex add(Complex c) {
            return new Complex(this.a + c.a, this.b + c.b);
        }

        private Complex sub(Complex c) {
            return new Complex(this.a - c.a, this.b - c.b);
        }

        private Complex mul(Complex c) {
            return new Complex(this.a * c.a - this.b * c.b, this.a * c.b + this.b * c.a);
        }

        private double getMod() {
            return Math.sqrt(Math.pow(this.a, 2) + Math.pow(this.b, 2));
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
