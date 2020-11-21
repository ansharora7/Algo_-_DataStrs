import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStatsTesting {

    // perform independent trials on an n-by-n grid

    private double sites_open[];
    private double fraction, stddev, mean;
    private int initial_trials;
    private int no_trials;
    Percolation matrix;

    public PercolationStatsTesting(int n, int trials) {

        // throws exception
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        initial_trials = trials;
        no_trials = trials;
        sites_open = new double[no_trials];


        while (no_trials != 0) {

            matrix = new Percolation(n);
            while (!matrix.percolates()) {

                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                matrix.open(row, col);


            }
            no_trials -= 1;
            fraction = (double) (matrix.numberOfOpenSites()) / (n * n);
            System.out.println(fraction);
            sites_open[no_trials] = fraction;
        }
    }


    // sample mean of percolation threshold
    public double mean() {
        return (StdStats.mean(sites_open));
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return (StdStats.stddev(sites_open));
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
        PercolationStats obj1 = new PercolationStats(20, 10);
        System.out.println("mean= " + (obj1.mean()));
        System.out.println("stddev= " + (obj1.stddev()));
        System.out.println(
                "95% Confidence interval= [" + (obj1.confidenceLo()) + "," + (obj1.confidenceHi())
                        + "]");

    }

}
