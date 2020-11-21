import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // perform independent trials on an n-by-n grid

    private double sites_open[];
    private double fraction, stddev, mean;
    private int initial_trials;
    private int no_trials;
    private int ans = 0;

    public PercolationStats(int n, int trials) {
        Percolation matrix;
        // throws exception
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        initial_trials = trials;
        no_trials = trials;
        sites_open = new double[no_trials];


        while (no_trials != 0) {
            ans += 1;
            matrix = new Percolation(n);
            while (!matrix.percolates()) {

                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                matrix.open(row, col);


            }
            no_trials = no_trials - 1;
            fraction = (double) (matrix.numberOfOpenSites()) / (n * n);
            sites_open[no_trials] = fraction;
        }
    }


    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(sites_open);
        return (mean);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(sites_open);
        return (stddev);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean - (1.96 * stddev) / Math.sqrt(initial_trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean + ((1.96 * stddev) / Math.sqrt(initial_trials)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats obj1 = new PercolationStats(n, T);
        System.out.println("mean= " + String.valueOf(obj1.mean()));
        System.out.println("stddev= " + String.valueOf(obj1.stddev()));
        System.out.println("95% Confidence interval= [" + String
                .valueOf(obj1.confidenceLo()) + "," + String.valueOf(obj1.confidenceHi()) + "]");

    }

}
