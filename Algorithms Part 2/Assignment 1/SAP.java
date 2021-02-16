import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        graph = new Digraph(G);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // private boolean check_iterable(Iterable<Integer> v) {
    //     for (int i : v) {
    //         return true;
    //     }
    //     return false;
    // }

    ////////////////////////////////////////////////////////////
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V()) throw new IllegalArgumentException();
        if (v == w) return 0;
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
        int count = 0;
        int min_length = graph.V();
        for (int i = 0; i < graph.V(); i++) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                count = 1;
                if (bfsv.distTo(i) + bfsw.distTo(i) < min_length) {
                    min_length = bfsv.distTo(i) + bfsw.distTo(i);
                }
            }
        }
        if (count == 1) return min_length;
        return -1;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V()) throw new IllegalArgumentException();
        if (v == w) return v;
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
        int ancestor = -1;
        int length = graph.V();
        for (int i = 0; i < graph.V(); i++) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                if (bfsv.distTo(i) + bfsw.distTo(i) < length) {
                    length = bfsv.distTo(i) + bfsw.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    // private boolean check_itr(Iterable<>
   
    ////////////////////////////////////////////////////////////////////////////////////

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        // if (!check_iterable(v) || !check_iterable(w)) throw new IllegalArgumentException();
        if (v == null || w == null) throw new IllegalArgumentException();
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
        int count = 0;
        int min_length = graph.V();
        BreadthFirstDirectedPaths bfs;
        for (int i = 0; i < graph.V(); i++) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                count = 1;
                if (bfsv.distTo(i) + bfsw.distTo(i) < min_length) {
                    min_length = bfsv.distTo(i) + bfsw.distTo(i);
                }
            }
        }
        if (count == 1) return min_length;
        return -1;
    }
    ////////////////////////////////////////////////////////////////////////////////////////


    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // if (!check_iterable(v) || !check_iterable(w)) throw new IllegalArgumentException();
        if (v == null || w == null) throw new IllegalArgumentException();
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
        int ancestor = -1;
        int min_length = graph.V();
        for (int i = 0; i < graph.V(); i++) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                if ((bfsv.distTo(i) + bfsw.distTo(i)) < min_length) {
                    min_length = bfsv.distTo(i) + bfsw.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////


    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
