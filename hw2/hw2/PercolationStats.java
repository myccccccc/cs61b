package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] samples;
    private int t;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        t = T;
        samples = new double[T];
        Percolation p;
        for (int i = 0; i < T; i++) {
            p = pf.make(N);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            samples[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }
    public double mean() {  // sample mean of percolation threshold
        return StdStats.mean(samples);
    }
    public double stddev() {     // sample standard deviation of percolation threshold
        return StdStats.stddev(samples);
    }
    public double confidenceLow() { // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / java.lang.Math.sqrt((double) t);
    }
    public double confidenceHigh() { // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / java.lang.Math.sqrt((double) t);
    }
}
