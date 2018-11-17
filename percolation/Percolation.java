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
        wquf = new WeightedQuickUnionUF(Size + 2);   // add the top site and bottom site
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
            System.out.println("already open");
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
        // 5. the first row connect to others same row, they all connect the last - 1 site
        if (row == 1) {
            // iterate first row
//            System.out.println("first row open");
            wquf.union(size * size, pos);
        }
        // 6. the last row could be connect to bottom site if this site connect to top site
        if (row == size) {
            wquf.union(size * size + 1, pos);
//            System.out.println("last row open");
//            if (wquf.connected(size * size, pos)) {
//                System.out.println("found percolate");
//                wquf.union(size * size + 1, pos);
//            }
//            if (isFull(row, col)) {
//                System.out.println("last row is full");
//            }
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
        return wquf.connected(pos, size * size);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        // iterate all site connect to first row, if so, full;
        return wquf.connected(size * size + 1, size * size);
//        for (int i = 0; i < size; i++) {
//            int pos = (size - 1) * size + i;
//            if (wquf.connected(pos, size * size)) {
//                return true;
//            }
//        }
//        return false;
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
