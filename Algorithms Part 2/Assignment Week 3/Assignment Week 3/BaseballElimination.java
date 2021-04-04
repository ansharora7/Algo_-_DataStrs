import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BaseballElimination {
    private final ArrayList<String> teams;
    private ArrayList<String> teams_ahead;
    private final int length;
    private final int[] wins;
    private final int[] losses;
    private final int[] home_remaining;
    private final int[][] away_remaining;
    private int vertices, source, target;
    private FlowNetwork division;
    private FordFulkerson elm_network;


    public BaseballElimination(String filename) {                   // create a baseball division from given filename in format specified below
        if (filename == null) throw new IllegalArgumentException();
        In division = new In(filename);
        length = division.readInt();
        teams = new ArrayList<>();
        wins = new int[length];
        losses = new int[length];
        home_remaining = new int[length];
        away_remaining = new int[length][length];
        for (int i = 0; i < length; i++) {
            teams.add(division.readString());
            wins[i] = division.readInt();
            losses[i] = division.readInt();
            home_remaining[i] = division.readInt();

            for (int j = 0; j < length; j++) {
                away_remaining[i][j] = division.readInt();
            }
        }
    }

    public int numberOfTeams() {                        // number of teams
        return teams.size();
    }

    public Iterable<String> teams() {                                // all teams
        return teams;
    }

    public int wins(String team) {                      // number of wins for given team
        if (!teams.contains(team)) throw new IllegalArgumentException();
        return (wins[teams.indexOf(team)]);
    }

    public int losses(String team) {                    // number of losses for given team
        if (!teams.contains(team)) throw new IllegalArgumentException();
        return (losses[teams.indexOf(team)]);
    }

    public int remaining(String team) {                 // number of remaining games for given team
        if (!teams.contains(team)) throw new IllegalArgumentException();
        return (home_remaining[teams.indexOf(team)]);
    }

    public int against(String team1, String team2) {    // number of remaining games between team1 and team2
        if (!teams.contains(team1) || !teams.contains((team2))) throw new IllegalArgumentException();
        int index1 = teams.indexOf(team1);
        int index2 = teams.indexOf(team2);
        return (away_remaining[index1][index2]);

    }

    ///////////////////////////////////////////////////////////////////////////////
    // find first group of vertices
    private int nCr(int n, int r) {
        if (r == 1) return n;
        return n * nCr(n - 1, r - 1) / r;
    }

    /////////////////////////////////////////////////////////////////////////////
    // Trivial elimination
    private boolean trivial_el(String team) {
        teams_ahead = new ArrayList<>();
        int max = wins[teams.indexOf(team)] + home_remaining[teams.indexOf(team)];
        int count = 0;
        int max_win = 0;
        String max_team = "";
        for (String t : teams) {
            if (wins[teams.indexOf(t)] > max && wins[teams.indexOf(t)] > max_win) {
                max_win = wins[teams.indexOf(t)];
                max_team = t;
                count = 1;
            }
        }
        if (count == 1) {
            teams_ahead.add(max_team);
            return true;
        }
        return false;

    }

    ///////////////////////////////////////////////////////////////////////////////
    // create flow network
    private void network(int team_index) {
        vertices = 2 + nCr(length, 2) + length;
        division = new FlowNetwork(vertices);
        FlowEdge edge;
        source = vertices - 2;
        target = vertices - 1;


        for (int f = 0; f < length; f++) {
            int cap = Math.abs(wins[team_index] + home_remaining[team_index] - wins[f]); // teams -> target
            edge = new FlowEdge(f, target, cap, 0);
            division.addEdge(edge);
        }

        int v = length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                edge = new FlowEdge(source, v, away_remaining[i][j], 0); // source -> team-matching
                division.addEdge(edge);
//              System.out.println(i + length + "  1." + teams.get(j) + "   2." + teams.get(k) + "    " + away_remaining[j][k]);

                edge = new FlowEdge(v, i, Double.POSITIVE_INFINITY, 0);   // team-matching -> teams
                division.addEdge(edge);

                edge = new FlowEdge(v, j, Double.POSITIVE_INFINITY, 0);
                division.addEdge(edge);

                v++;
            }
        }
        elm_network = new FordFulkerson(division, source, target);

    }

    ////////////////////////////////////////////////////////////////////////////////
    private boolean isFullFromSource(FlowNetwork division, int i) {
        for (FlowEdge edge : division.adj(i)) {
            if (Math.abs(edge.flow() - edge.capacity()) > 0.00001) {
                return false;
            }
        }
        return true;
    }

    //////////////////////////////////////////////////////////////
    public boolean isEliminated(String team) {              // is given team eliminated?
        if (!teams.contains(team)) throw new IllegalArgumentException();
        if (trivial_el(team)) {
            return true;
        }
        teams_ahead = new ArrayList<>();
        network(teams.indexOf(team));                       // Ford-Fulkerson

        if (isFullFromSource(division, source)) return false;
        for (int i = 0; i < length; i++) {
            if (elm_network.inCut(i)) {
                teams_ahead.add(teams.get(i));
            }
        }
        return true;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public Iterable<String> certificateOfElimination(String team) {  // subset R of teams that eliminates given team; null if not eliminated
        if (!teams.contains(team)) throw new IllegalArgumentException();
        if (isEliminated(team)) return teams_ahead;
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
//        System.out.println(division.nCr(24, 2));
//        System.out.println(division.isEliminated("New_York"));
    }
}
