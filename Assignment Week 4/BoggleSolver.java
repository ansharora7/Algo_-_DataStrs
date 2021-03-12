import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.util.ArrayList;
import java.util.HashMap;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private TST<Integer> dict;
    private HashMap<Integer, Character> chars;
    private Graph graph;
    private int qs;

    public BoggleSolver(String[] dictionary) {
        dict = new TST<>();
        for (int i = 0; i < dictionary.length; i++) {
            if (!dictionary[i].endsWith("Q") && dictionary[i].length() > 2)
                dict.put(dictionary[i], i);
        }
        // int i = 0;
        // for (i = 0; i < dict.size(); i++) {
        //     System.out.println(dict.contains(dictionary[i]));
        // }
        // System.out.println(i);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Making the graph
    private void board(BoggleBoard board) {
        int count = 0;
        chars = new HashMap<>();
        int rows = board.rows();
        int cols = board.cols();
        graph = new Graph(rows * cols);
        int coord, coord2;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                coord = i * cols + j;
                chars.put(coord, board.getLetter(i, j));
                if (board.getLetter(i, j) == 'Q') count += 1;
                // System.out.println(coord + " " + board.getLetter(i, j));

                if (cols == 1 && i < rows - 1) {
                    coord2 = (i + 1) * cols + j;  // Down
                    graph.addEdge(coord, coord2);

                }

                else if (rows == 1 && j < cols - 1) {
                    coord2 = i * cols + (j + 1);  // Right
                    graph.addEdge(coord, coord2);
                }

                else if (j > 0 && j < cols - 1 && i < rows - 1) {
                    coord2 = i * cols + (j + 1);  // Right
                    graph.addEdge(coord, coord2);

                    coord2 = (i + 1) * cols + j;  // Down
                    graph.addEdge(coord, coord2);

                    coord2 = (i + 1) * cols + (j + 1);  // Right-Diagonal
                    graph.addEdge(coord, coord2);

                    coord2 = (i + 1) * cols + (j - 1);  // Left-Diagonal
                    graph.addEdge(coord, coord2);
                }

                else if (j == 0 && i < rows - 1) {
                    // System.out.println(i + " " + j);
                    coord2 = i * cols + (j + 1);  // Right
                    graph.addEdge(coord, coord2);

                    coord2 = (i + 1) * cols + j;  // Down
                    graph.addEdge(coord, coord2);

                    coord2 = (i + 1) * cols + (j + 1);  // Right-Diagonal
                    graph.addEdge(coord, coord2);
                }

                else if (j == cols - 1 && i < rows - 1) {
                    // System.out.println(i + " " + j);
                    coord2 = (i + 1) * cols + j;  // Down
                    graph.addEdge(coord, coord2);

                    coord2 = (i + 1) * cols + (j - 1);  // Left-Diagonal
                    graph.addEdge(coord, coord2);
                }

                else if (i == rows - 1 && j != cols - 1) {
                    coord2 = i * cols + (j + 1);  // Right
                    graph.addEdge(coord, coord2);
                }
            }
        }
        if (count == rows * cols) qs = -1;
    }

    ///////////////////////////////////////////////////////////////////////////////
    private void DepthFirstSearchPvt(ArrayList<String> words, Graph G, int s) {
        boolean[] marked = new boolean[G.V()];
        String word = "";

        dfs(graph, s, marked, word, words);

    }

    // depth first search from v
    private void dfs(Graph G, int s, boolean[] marked, String word,
                     ArrayList<String> words) {

        if (chars.get(s) == 'Q') word += "QU";
        else word += (chars.get(s));

        // System.out.println(word);

        if (((Queue<String>) dict.keysWithPrefix(word)).isEmpty()) return;
        if (dict.contains(word) && !words.contains(word) && word.length() > 2) words.add(word);

        marked[s] = true;

        for (int w : G.adj(s)) {

            if (!marked[w]) {
                dfs(G, w, marked, word, words);
            }

        }
        marked[s] = false;

    }
    //////////////////////////////////////////////////////////////////////////

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        board(board);
        if (qs == -1) {
            Queue<String> words = (Queue<String>) (dict.keysWithPrefix("Q"));
            return words;
        }
        ArrayList<String> words = new ArrayList<>();

        for (int i = 0; i < chars.size(); i++) {
            DepthFirstSearchPvt(words, graph, i);

        }

        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word.length() <= 2) return 0;
        if (dict.contains(word)) {
            if (word.length() == 3 || word.length() == 4) return 1;
            if (word.length() == 5) return 2;
            if (word.length() == 6) return 3;
            if (word.length() == 7) return 5;
            return 11;
        }
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        // for (String s : dictionary) System.out.println(s);
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        // solver.board(board);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word + " " + solver.scoreOf(word));
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }


}
