package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF a; //the one with top [n * n] and bottom [n * n + 1] virtual site
    private WeightedQuickUnionUF b; //the one with only one top [n * n] virtual site
    private int numberOfOpensites;
    private int[][] openSites;
    private int n;

    private void boundaryCheck(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    private boolean boundaryCheck2(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return false;
        }
        return true;
    }

    private int xy2index(int row, int col) {
        return row * n + col;
    }

    public Percolation(int N) {  // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        n = N;
        openSites = new int[n][n];
        numberOfOpensites = 0;
        a = new WeightedQuickUnionUF(N * N + 2);
        b = new WeightedQuickUnionUF(N * N + 1);
    }

    public void open(int row, int col) {    // open the site (row, col) if it is not open already
        boundaryCheck(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int index = xy2index(row, col);
        numberOfOpensites++;
        openSites[row][col] = 1;
        if (row == 0) {
            a.union(index, n * n);
            b.union(index, n * n);
        }
        if (row == n - 1) {
            a.union(index, n * n + 1);
        }
        if (boundaryCheck2(row - 1, col)) {
            if (isOpen(row - 1, col)) {
                a.union(index, xy2index(row - 1, col));
                b.union(index, xy2index(row - 1, col));
            }
        }
        if (boundaryCheck2(row + 1, col)) {
            if (isOpen(row + 1, col)) {
                a.union(index, xy2index(row + 1, col));
                b.union(index, xy2index(row + 1, col));
            }
        }
        if (boundaryCheck2(row, col - 1)) {
            if (isOpen(row, col - 1)) {
                a.union(index, xy2index(row, col - 1));
                b.union(index, xy2index(row, col - 1));
            }
        }
        if (boundaryCheck2(row, col + 1)) {
            if (isOpen(row, col + 1)) {
                a.union(index, xy2index(row, col + 1));
                b.union(index, xy2index(row, col + 1));
            }
        }

    }

    public boolean isOpen(int row, int col) {   // is the site (row, col) open?
        boundaryCheck(row, col);
        if (openSites[row][col] == 1) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {   // is the site (row, col) full?
        boundaryCheck(row, col);
        if (isOpen(row, col)) {
            return b.connected(xy2index(row, col), n * n);
        }
        return false;
    }

    public int numberOfOpenSites() { // number of open sites
        return numberOfOpensites;
    }

    public boolean percolates() {   // does the system percolate?
        return a.connected(n * n, n * n + 1);
    }

    public static void main(String[] args) {
        // use for unit testing (not required, but keep this here for the autograder)
    }
}
