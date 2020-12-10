import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private ArrayList<Board> ans = new ArrayList<>();
    private int solvable;
    private MinPQ<SearchNode> puzzle;
    private MinPQ<SearchNode> twin_puzzle;

    ///////////////////////////////////////////////////////////////////////////////
    private class SearchNode implements Comparable<SearchNode> {
        Board node;
        SearchNode prev_node;
        int moves, manhattan, priority;

        SearchNode(Board node, int moves, SearchNode prev_node) {
            if (node != null) {
                this.node = node;
                this.prev_node = prev_node;
                this.moves = moves;
                this.manhattan = node.manhattan();
                this.priority = moves + manhattan;
            }
        }

        @Override
        public int compareTo(SearchNode node2) {
            return (this.priority - node2.priority);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        // For Board

        puzzle = new MinPQ<>();
        Iterable<Board> neighbours;
        SearchNode currentNode = new SearchNode(initial, 0, new SearchNode(null, 0, null));

        // For Twin Board
        twin_puzzle = new MinPQ<>();
        Iterable<Board> twin_neighbours;
        SearchNode twin_currentNode = new SearchNode(initial.twin(), 0, new SearchNode(null, 0, null));

        int count = 1;
        while (!currentNode.node.isGoal()) {
            if (twin_currentNode.node.isGoal()) {
                solvable = -1;
                break;
            }
            // For Board
            neighbours = currentNode.node.neighbors();

            for (Board bd : neighbours) {
                if (count == 1) {
//                    System.out.println("HI");
//                    System.out.println(bd);
                    puzzle.insert(new SearchNode(bd, currentNode.moves + 1, currentNode));
                } else if (!(currentNode.prev_node.node.equals(bd))) {
//                    System.out.println("HI");
//                    System.out.println(bd);
                    puzzle.insert(new SearchNode(bd, currentNode.moves + 1, currentNode));
                }
            }
            count += 1;
            currentNode = puzzle.delMin();

            // For Twin Board
            twin_neighbours = twin_currentNode.node.neighbors();

            for (Board twin_bd : twin_neighbours) {
                if (count == 2) {
                    twin_puzzle.insert(new SearchNode(twin_bd, twin_currentNode.moves + 1,
                            twin_currentNode));
                } else if (!twin_currentNode.prev_node.node.equals(twin_bd)) {
                    twin_puzzle.insert(new SearchNode(twin_bd, twin_currentNode.moves + 1,
                            twin_currentNode));
                }
            }

            twin_currentNode = twin_puzzle.delMin();


        }

        if (currentNode.node.isGoal()) {
            solvable = 1;
            while (currentNode.node != null) {
                ans.add(currentNode.node);
                currentNode = currentNode.prev_node;
            }
            Collections.reverse(ans);
        }
    }

    // is the initial Board solvable? (see below)
    public boolean isSolvable() {
        return (solvable == 1);
    }

    // min number of moves to solve initial Board; -1 if unsolvable
    public int moves() {
        if (solvable == 1) return (ans.size() - 1);
        return solvable;
    }

    // sequence of Boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable == -1) return null;
        return ans;
    }

    // test client (see below)
    public static void main(String[] args) {
        //  int[][] arr2 = {{5, 8, 7}, {1, 4, 6}, {3, 0, 2}};
        //  int[][] arr = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        int[][] arr3 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        Board bd = new Board(arr3);
        Solver s = new Solver(bd);
        //System.out.println(s.isSolvable());
        System.out.println(s.solution());
        System.out.println(s.moves());
    }
}
