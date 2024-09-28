import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {

    public static void defaultTest() {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));

        double[] c1 = {6, 5};
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {-2, -9};
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial s = p1.add(p2);

        System.out.println("s(0.1)=" + s.evaluate(0.1));
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }
    }


    public static void customTest() {
        double[] c1 = {3, 5, -2, 3};
        int[] e1 = {10, 3, 0, 1};

        double[] c2 = {2, -100, -1, 4};
        int[] e2 = {7, 4, 1, 2};

        Polynomial p1 = new Polynomial(c1, e1);
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial s = p1.add(p2);
        Polynomial m = p2.multiply(p1);

        System.out.println("s(1) = " + s.evaluate(1));
        System.out.println("m(1) = " + m.evaluate(1));
    }


    public static void fileTest() throws IOException {
        Polynomial p1 = new Polynomial(new File("p1.txt"));
        Polynomial p2 = new Polynomial(new File("p2.txt"));

        Polynomial sum = p2.add(p1);
        Polynomial mul = p2.multiply(p1);

        System.out.println("sum(2) = " + sum.evaluate(2));
        System.out.println("mul(2) = " + mul.evaluate(2));

        mul.saveToFile("output.txt");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException{
        System.out.println("Default Test:");
        defaultTest();

        System.out.println();
        System.out.println("Custom Test:");
        customTest();

        System.out.println();
        System.out.println("File Test");
        fileTest();
        
    }
}