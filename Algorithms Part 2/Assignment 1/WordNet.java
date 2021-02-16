import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {
    // constructor takes the name of the two input files
    // private String synsets, hypernyms;
    private int vertices = 0;
    private String hypernyms;
    private Digraph graph;
    private SAP sap;
    private HashMap<String, Integer> data = new HashMap<>();
    private HashMap<Integer, String> id2noun = new HashMap<>();

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        // this.synsets = synsets;
        // this.hypernyms = hypernyms;
        read_synsets(synsets);
        read_hypernyms(hypernyms);
    }

    private void read_synsets(String synsets) {
        In for_synsets = new In(synsets);
        String line;
        while ((line = for_synsets.readLine()) != null) {
            vertices += 1;
            String[] check = line.split(",");
            String[] nouns = check[1].split(" ");
            id2noun.put(Integer.parseInt(check[0]), check[1]);
            for (int i = 0; i < nouns.length; i++) {
                data.put(nouns[i], Integer.parseInt(check[0]));
            }
        }
    }

    private void read_hypernyms(String hypernyms) {
        In for_hypernyms = new In(hypernyms);
        graph = new Digraph(vertices);
        String line;
        while ((line = for_hypernyms.readLine()) != null) {
            String[] check = line.split(",");
            int start = Integer.parseInt(check[0]);
            for (int i = 1; i < check.length; i++) {
                graph.addEdge(start, Integer.parseInt(check[i]));
            }

        }
        sap = new SAP(graph);
    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return data.keySet();
    }


    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return (data.containsKey(word));
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        // read_hypernyms(hypernyms);
        int a = data.get(nounA);
        int b = data.get(nounB);
        return (sap.length(a, b));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        // read_hypernyms(hypernyms);
        int a = data.get(nounA);
        int b = data.get(nounB);
        int ans = sap.ancestor(a, b);
        return (id2noun.get(ans));
        // return (sap.ancestor(data.get(nounA), data.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
