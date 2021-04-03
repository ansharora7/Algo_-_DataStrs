import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;
import java.util.HashMap;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = StdIn.readString();
        // String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int first;
        for (int i = 0; i < csa.length(); i++) {
            int n = csa.index(i);
            if (n == 0) {
                first = i;
                BinaryStdOut.write(first);
                break;
            }
        }
        for (int i = 0; i < csa.length(); i++) {
            int n = csa.index(i);
            if (n == 0) {
                // System.out.println(s.charAt(s.length() - 1));
                BinaryStdOut.write(s.charAt(s.length() - 1), 8);
            }
            else {
                // System.out.println(s.charAt(n - 1));
                BinaryStdOut.write(s.charAt(n - 1), 8);
            }
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] t = s.toCharArray();
        HashMap<Character, Queue<Integer>> table = new HashMap<Character, Queue<Integer>>();
        for (int i = 0; i < t.length; ++i) {
            if (!table.containsKey(t[i])) {
                table.put(t[i], new Queue<Integer>());
            }
            table.get(t[i]).enqueue(i);
        }

        Arrays.sort(t);
        int[] next = new int[t.length];
        for (int i = 0; i < next.length; ++i) {
            next[i] = table.get(t[i]).dequeue();
        }

        for (int i = 0; i < next.length; ++i) {
            BinaryStdOut.write(t[first], 8);
            first = next[first];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        if (args[0].equals("+")) inverseTransform();
        // BurrowsWheeler b = new BurrowsWheeler();
        // b.transform();
    }

}
