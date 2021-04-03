import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private final String s;
    private final Integer[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string is invalid.");
        }

        this.s = s;
        index = new Integer[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            index[i] = i;
        }

        Arrays.sort(index, suffixOrder());
    }

    private Comparator<Integer> suffixOrder() {
        return new SuffixOrder();
    }

    private class SuffixOrder implements Comparator<Integer> {
        public int compare(Integer i1, Integer i2) {
            int first = i1;
            int second = i2;
            for (int i = 0; i < s.length(); ++i) {
                char a = s.charAt(first);
                char b = s.charAt(second);
                if (a < b) {
                    return -1;
                }
                else if (a > b) {
                    return 1;
                }
                ++first;
                if (first == s.length()) {
                    first = 0;
                }
                ++second;
                if (second == s.length()) {
                    second = 0;
                }
            }

            return 0;
        }
    }

    // length of s
    public int length() {
        return s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= s.length()) {
            throw new IllegalArgumentException("Input index is invalid.");
        }

        return index[i];
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); ++i) {
            StdOut.print(csa.index(i) + " ");
        }
        StdOut.println();
    }
}

// import edu.princeton.cs.algs4.Quick3string;
//
// import java.util.ArrayList;
//
// public class CircularSuffixArray {
//     private String[] circular;
//     private ArrayList<String> place;
//     private final int length;
//     private StringBuilder sb_s;
//
//     // circular suffix array of s
//     public CircularSuffixArray(String s) {
//         if (s == null) throw new IllegalArgumentException();
//         ArrayList<String> pre_circular = new ArrayList<>();
//         length = s.length();
//         place = new ArrayList<>();
//         sb_s = new StringBuilder(s);
//         String suffix;
//         int i = 0;
//         while (i != length) {
//             // System.out.println((suffix.equals(s)) + " " + i);
//             // System.out.println(sb_s.substring(length - i - 1));
//             suffix = sb_s.substring(length - i - 1, length) + sb_s
//                     .substring(0, length - i - 1);
//             // System.out.println(suffix + " SSSKKSKS");
//             pre_circular.add(suffix);
//             place.add(0, suffix);
//             i += 1;
//         }
//         circular = new String[pre_circular.size()];
//         circular = pre_circular.toArray(circular);
//         Quick3string.sort(circular);
//
//         // for (int j = 0; j < length; j++) {
//         //     System.out.println(circular[j]);
//         // }
//         // System.out.println("-----------------");
//         //
//         // for (String k : place) System.out.println(k);
//     }
//
//     // length of s
//     public int length() {
//         return length;
//     }
//
//     // returns index of ith sorted suffix
//     public int index(int i) {
//         if (i < 0 || i > length - 1) throw new IllegalArgumentException();
//         return (place.indexOf(circular[i]));
//     }
//
//     // unit testing (required)
//     public static void main(String[] args) {
//         String str = args[0];
//         // String str = "************";
//         CircularSuffixArray csa = new CircularSuffixArray(str);
//         System.out.println(csa.length);
//         System.out.println(csa.index(0));
//     }
//
// }
