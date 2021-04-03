import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;

public class MoveToFront {
    // private static final int lgr = 8;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        ArrayList<Character> stream = new ArrayList<>();
        // StringBuilder str = new StringBuilder("CAAABCCCACCF");
        for (int i = 0; i < 256; i++) {
            stream.add((char) i);
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            // for (int i = 0; i < str.length(); i++) {
            //     char c = str.charAt(i);

            BinaryStdOut.write(stream.indexOf(c), 8);
            // System.out.println(stream.indexOf(c));
            stream.remove(stream.indexOf(c));
            stream.add(0, c);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        ArrayList<Character> stream = new ArrayList<>();

        for (int i = 0; i <= 255; i++) stream.add((char) i);

        while (!BinaryStdIn.isEmpty()) {
            int n = BinaryStdIn.readChar();
            char c = stream.get(n);
            stream.remove(stream.indexOf(c));
            stream.add(0, c);
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        // MoveToFront f = new MoveToFront();
        // f.encode();
    }

}
