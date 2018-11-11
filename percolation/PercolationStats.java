/* *****************************************************************************
 *  Name: percolation
 *  Date: 2018-11-06
 *  Description: percolate
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] thresholdArray;
    // private double mean;
    // private double stddev;
    private final int Trails;
    // private final double CONFIDENCE_95 = 1.96d;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("");
        }
        Trails = trials;
        thresholdArray = new double[Trails];
        for (int i = 0; i < Trails; i++) {
            Percolation percolate = new Percolation(n);
            // double count = 0;
            while (!percolate.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolate.open(row, col);
                // count += 1;
            }

            double rate = (double) (percolate.numberOfOpenSites()) / (n * n);
            // System.out.println(percolate.numberOfOpenSites());
            thresholdArray[i] = rate;
        }
        // StdRandom
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholdArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdArray);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(Trails));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(Trails));
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trail = Integer.parseInt(args[1]);
        PercolationStats instance = new PercolationStats(n, trail);
        System.out.println(instance.mean());
        System.out.println(instance.stddev());
        System.out.println(instance.confidenceLo());
        System.out.println(instance.confidenceHi());
    }
}
