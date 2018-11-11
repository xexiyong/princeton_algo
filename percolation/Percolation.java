/* *****************************************************************************
 *  Name: sunzg
 *  Date: 2018-11-05
 *  Description: percolate
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private final WeightedQuickUnionUF wquf;
    private boolean[] openFlag;
    private int openNumber = 0;
    // private int[] firstRowOpen;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("");
        }
        size = n;
        int Size = n * n;
        wquf = new WeightedQuickUnionUF(Size);
        openFlag = new boolean[Size];
        // firstRowOpen = new int[size];
        for (int i = 0; i < size; i++) {
            // firstRowOpen[i] = 0;
            for (int j = 0; j < size; j++) {
                openFlag[i * size + j] = false;
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        // open it
        int pos = (row - 1) * size + (col - 1);
        openFlag[pos] = true;
        openNumber += 1;
        // check the four site, and connect it
        // 1. top
        int top_row = row - 1;
        if (top_row > 0) {
            if (isOpen(top_row, col)) {
                int top_pos = (top_row - 1) * size + (col - 1);
                wquf.union(top_pos, pos);
            }
        }
        // 2.bottom
        int bottom_row = row + 1;
        if (bottom_row <= size) {
            if (isOpen(bottom_row, col)) {
                int bottom_pos = (bottom_row - 1) * size + (col - 1);
                wquf.union(bottom_pos, pos);
            }
        }
        // 3.left
        int left_col = col - 1;
        if (left_col > 0) {
            if (isOpen(row, left_col)) {
                int left_pos = (row - 1) * size + (left_col - 1);
                wquf.union(left_pos, pos);
            }
        }
        // 4.right
        int right_col = col + 1;
        if (right_col <= size) {
            if (isOpen(row, right_col)) {
                int right_pos = (row - 1) * size + (right_col - 1);
                wquf.union(right_pos, pos);
            }
        }
        // 5. the first row connect to others same row
        if (row == 1) {
            // iterate first row
            for (int j = 1; j <= size; j++) {
                // if open and not the current site
                if (j != col && isOpen(row, j)) {
                    // connect it
                    int first_row_pos = (row - 1) * size + (j - 1);
                    wquf.union(first_row_pos, pos);
                }
            }
        }
    }

    // is site (row, col) open
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int pos = (row - 1) * size + (col - 1);
        return openFlag[pos];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        // check current site connect to first row
        // iterate first row
        int pos = (row - 1) * size + (col - 1);
        for (int j = 1; j <= size; j++) {
            if (isOpen(1, j)) {
                int first_row_pos = j - 1;
                if (wquf.connected(pos, first_row_pos))
                    return true;
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        // iterate all site connect to first row, if so, full;
        for (int col = 1; col <= size; col++) {
            if (isOpen(size, col)) {
                // find some opened site
                int last_row_pos = (size - 1) * size + col - 1;
                for (int fcol = 1; fcol <= size; fcol++) {
                    if (isOpen(1, fcol)) {
                        // find some site in the first row
                        int first_row_pos = fcol - 1;
                        if (wquf.connected(first_row_pos, last_row_pos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // invalidate
    private void validate(int row, int col) {
        // 1-start up index
        if ((row <= 0 || row > size) || (col <= 0 || col > size)) {
            throw new IllegalArgumentException("corrupt");
        }
    }

    // optional
    public static void main(String[] args) {

    }
}
