import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Scanner;

public class Percolation {

    // declare your variables
    private final int size, top, bottom;
    private int sites_open = 0;
    private int site_num[], cond_of_site[];
    private WeightedQuickUnionUF grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        size = n;
        cond_of_site = new int[size * size + 2];
        site_num = new int[size * size + 2];
        top = 0;
        bottom = (size * size) + 1;

        // creating site_num
        for (int i = 0; i < bottom + 1; i++) {
            site_num[i] = i;
            cond_of_site[i] = 0;


        }
        grid = new WeightedQuickUnionUF((size * size) + 2);


    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > (size) || col <= 0 || col > (size)) {
            throw new IllegalArgumentException();
        }
        if (cond_of_site[(row - 1) * size + col] == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > (size) || col <= 0 || col > (size)) {
            throw new IllegalArgumentException();
        }
        int current_site = site_num[(row - 1) * size + col];


        // curent site
        if (!(this.isOpen(row, col))) {
            cond_of_site[current_site] = 1;
            sites_open += 1;
            if (current_site <= size) {
                grid.union(top, current_site);
            }
            if ((((size * size) - size) < current_site) && (current_site <= (size * size))) {
                grid.union(bottom, current_site);

            }

        }

        // upper site if open
        if (row > 1) {
            int upper_site = site_num[(row - 2) * size + col];

            if (!grid.connected(current_site, upper_site) && this.isOpen(row - 1, col)) {

                grid.union(current_site, upper_site);

            }
        }

        // lower site if open
        if (row < size) {
            int lower_site = site_num[(row) * size + col];
            if (!grid.connected(current_site, lower_site) && this
                    .isOpen(row + 1, col)) {
                grid.union(current_site, lower_site);
            }
        }


        // left site if open
        if (col > 1) {
            int left_site = site_num[(row - 1) * size + col - 1];
            if (!grid.connected(current_site, left_site) && this.isOpen(row, col - 1)) {
                grid.union(current_site, left_site);
            }
        }

        // right site if open
        if (col < size) {
            int right_site = site_num[(row - 1) * size + col + 1];
            if (!grid.connected(current_site, right_site) && this.isOpen(row, col + 1)) {
                grid.union(current_site, right_site);
            }
        }
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > (size) || col <= 0 || col > (size)) {
            throw new IllegalArgumentException();
        }
        if (site_num[(row - 1) * size + col] <= size && this.isOpen(row, col)) {
            return true;
        }
        else {
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return sites_open;
    }

    // does the system percolate?
    public boolean percolates() {
        if (grid.connected(top, bottom)) {
            return true;
        }
        else {
            return false;
        }
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter any number: ");
        int num = scan.nextInt();
        PercolationTesting obj1 = new PercolationTesting(num);
        while (!obj1.percolates()) {
            System.out.println("Enter row ");
            int row = scan.nextInt();
            System.out.println("Enter col: ");
            int col = scan.nextInt();

            System.out.println("row=" + String.valueOf(row));
            System.out.println("col=" + String.valueOf(col));
            System.out.println("---------------------------------------------------");
            obj1.open(row, col);
        }
    }


}








