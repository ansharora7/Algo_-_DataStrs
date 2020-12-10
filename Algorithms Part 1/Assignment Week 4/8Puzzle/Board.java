import java.util.ArrayList;
import java.util.Arrays;

public final class Board {

    private final int[] board;
    private final int n;
    private int[] sample;

    //////////////////////////////////////////////////////////////////////////////////////
    private int[] twoToOne(int[][] arr, int n) {
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.println(arr[i][j]);
//            }
//        }
        int[] ans = new int[n * n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[count] = arr[i][j];
                count += 1;
            }
        }
        return ans;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    private int[][] oneToTwo(int[] arr, int n) {
        int[][] ans = new int[n][n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = arr[count];
                count += 1;
            }

        }
        return ans;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // create a board from an n-by-n array of tiles,
    // where tiles[pos][col] = tile at (pos, col)
    public Board(int[][] tiles) {
        n = tiles[0].length;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.println(tiles[i][j]);
//            }
//        }
        sample = new int[n * n];
        board = twoToOne(tiles, n);
        for (int i = 0; i < n * n - 1; i++) {
            sample[i] = i + 1;
        }
        sample[n * n - 1] = 0;
    }

    ////////////////////////////////////////////////////////////////////////////////
    // string representation of this board
    public String toString() {
        StringBuilder board_string = new StringBuilder();
        board_string.append(n).append("\n");
        for (int i = 0; i < n * n; i++) {
            if (i % n == 0 && i >= n) {
                board_string.append("\n");
            }
            board_string.append(board[i]).append(" ");
        }
        board_string.append("\n");
        return board_string.toString();
    }

    /////////////////////////////////////////////////////////////////////////////////
    // board dimension n
    public int dimension() {
        return n;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n * n; i++) {
            if (board[i] != 0 && sample[i] != board[i]) {
                hamming += 1;
            }
        }
        return hamming;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int[][] sample_2 = oneToTwo(sample, n);
        int[][] board_2 = oneToTwo(board, n);
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (sample_2[i][j] == board_2[k][l] && board_2[k][l] != 0 && sample_2[i][j] != 0 && (i != k || j != l)) {
                            manhattan += Math.abs(i - k) + Math.abs(j - l);
                            //System.out.println(test[i][j] + " TEST");
                            //System.out.println(board[k][l] + " " + total);
                        }
                    }
                }
            }

        }
        return manhattan;
    }

    ////////////////////////////////////////////////////////////////////////////////
    private int[] copy(int[] arr) {
        int[] new_copy = new int[n * n];
        if (n * n >= 0) System.arraycopy(arr, 0, new_copy, 0, n * n);
        return new_copy;
    }

    ////////////////////////////////////////////////////////////////////////////////
    // is this board the goal board?
    public boolean isGoal() {
        return (Arrays.equals(board, sample));

    }


    ////////////////////////////////////////////////////////////////////////////////
    // does this board equal y?
//    @Override
//    public int hashCode() {
//        assert false : "hashCode not designed";
//        return 42;
//    } // any arbitrary constant will do

    @Override
    public boolean equals(Object y) {

        // null check
        if (y == null)
            return false;
        // type check and cast
        if (y instanceof Board) {
            Board check = (Board) y;
            // field comparison
            return Arrays.equals(board, check.board);
        }
        return false;

//        if (y != null) {
//            if (y.getClass() == this.getClass()) {
//                board ans = (board) y;
//                return (Arrays.deepEquals(this.board, ans.board));
//            }
//            return false;
//        }
//        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////
    private int[] exchange(int pos, int pos2, int[] arr) {
        int check = arr[pos2];
        arr[pos2] = 0;
        arr[pos] = check;

        return arr;
    }


    ///////////////////////////////////////////////////////////////////////////////
    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int pos = 0;
        int[] copy;
        for (int i = 0; i < n * n; i++) {
            if (board[i] == 0) {
                pos = i;
            }
        }

        // for (int i = 0; i < n * n; i++) System.out.print(copy[i]);
        if (pos - 1 >= 0 && pos % n != 0) {
            copy = copy(board);
            neighbors.add(new Board(oneToTwo(exchange(pos, pos - 1, copy), n)));
        }
        if (pos + 1 < n * n && (pos + 1) % n != 0) {
            copy = copy(board);
            neighbors.add(new Board(oneToTwo(exchange(pos, pos + 1, copy), n)));
        }
        if (pos - n >= 0) {
            copy = copy(board);
            neighbors.add(new Board(oneToTwo(exchange(pos, pos - n, copy), n)));
        }
        if (pos + n < n * n) {
            copy = copy(board);
            neighbors.add(new Board(oneToTwo(exchange(pos, pos + n, copy), n)));
        }

        return neighbors;

    }
    ////////////////////////////////////////////////////////////////////////////////////////

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
//        int[][] twin = copy(board);
//
//        int exch1 = StdRandom.uniform(0, n);
//        int exch2 = StdRandom.uniform(0, n);
//
//        while (twin[exch1][exch2] == 0) {
//            exch1 = StdRandom.uniform(0, n);
//            exch2 = StdRandom.uniform(0, n);
//        }
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (twin[i][j] != 0) {
//                    int check = twin[i][j];
//                    twin[i][j] = twin[exch1][exch2];
//                    twin[exch1][exch2] = check;
//                    break;
//
//                }
//            }
//            break;
//        }
////        for (int i = 0; i < n; i++) {
////            for (int j = 0; j < n; j++) {
////                System.out.println(copy[i][j]);
////            }
////        }
//        return new board(twin);

        int[] twin = copy(board);
        int pos2 = 0;
        int pos1 = 0;
        for (int i = 0; i < n * n; i++) {
            if (twin[i] != 0) {
                pos1 = i;
                break;
            }
        }
        for (int i = pos1 + 1; i < n * n; i++) {
            if (twin[i] != 0) {
                pos2 = i;
                break;
            }
        }
        int check = twin[pos1];
        twin[pos1] = twin[pos2];
        twin[pos2] = check;
        return new Board(oneToTwo(twin, n));
    }

    //////////////////////////////////////////////////////////////////////////////
    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] arr2 = {{5, 1, 2, 3}, {0, 6, 7, 4}, {9, 10, 11, 8}, {13, 14, 15, 12}};
        //int[][] arr = {{0, 4, 3}, {7, 2, 5}, {1, 8, 6}};
        //int[][] arr2 = {{1, 0}, {2, 3}};
        Board a = new Board(arr2);
        System.out.println(a.dimension() + "Dimension");
        System.out.println(a.hamming() + "Hamming");
        System.out.println(a.manhattan() + "Manhattan");
        System.out.println(a.isGoal() + "Goal");
        System.out.println(a.equals(a) + "Equal");
        Iterable<Board> n = a.neighbors();

        for (Board k : n) {
            System.out.println(k);
        }
        System.out.println(a.twin());
    }

}
