/* *****************************************************************************
 *  Name: sunzg
 *  Date: 2018-11-05
 *  Description: 渗透类
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private WeightedQuickUnionUF wquf;
    private int[] openFlag;
    // private int openNumber = 0;
    private int[] firstRowOpen;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("为空！");
        }
        size = n;
        int Size = n * n;
        wquf = new WeightedQuickUnionUF(Size);
        openFlag = new int[Size];
        firstRowOpen = new int[size];
        for (int i = 0; i < size; i++) {
            firstRowOpen[i] = 0;
            for (int j = 0; j < size; j++) {
                openFlag[i * size + j] = 0;
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        // 该位置屏蔽，需要改状态为打开
        int pos = (row - 1) * size + (col - 1);
        openFlag[pos] = 1;
        // openNumber += 1;
        // 观察周围四邻，是否为打开，如是，需要连接四邻
        // 1.上方
        int top_row = row - 1;
        if (top_row > 0) {
            if (isOpen(top_row, col)) {
                int top_pos = (top_row - 1) * size + (col - 1);
                wquf.union(top_pos, pos);
            }
        }
        // 2.下方
        int bottom_row = row + 1;
        if (bottom_row <= size) {
            if (isOpen(bottom_row, col)) {
                int bottom_pos = (bottom_row - 1) * size + (col - 1);
                wquf.union(bottom_pos, pos);
            }
        }
        // 3.左侧
        int left_col = col - 1;
        if (left_col > 0) {
            if (isOpen(row, left_col)) {
                int left_pos = (row - 1) * size + (left_col - 1);
                wquf.union(left_pos, pos);
            }
        }
        // 4.右侧
        int right_col = col + 1;
        if (right_col <= size) {
            if (isOpen(row, right_col)) {
                int right_pos = (row - 1) * size + (right_col - 1);
                wquf.union(right_pos, pos);
            }
        }
        // 5.首行点要和其他同行点连接
        if (row == 1) {
            // 遍历第一行
            for (int j = 1; j <= size; j++) {
                // 如果是打开状态，不为当前点
                if (j != col && isOpen(row, j)) {
                    // 连接两点
                    int first_row_pos = (row - 1) * size + (j - 1);
                    wquf.union(first_row_pos, pos);
                }
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int pos = (row - 1) * size + (col - 1);
        return openFlag[pos] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        // 检查当前点是否可渗透，当前点和首行点是否为union
        // 遍历第一行
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
        return wquf.count();
    }

    // does the system percolate?
    public boolean percolates() {
        // 遍历尾行所有点，如有和首行连接的，可认为是可渗透
        for (int col = 1; col <= size; col++) {
            if (isOpen(size, col)) {
                // 找到某个尾行打开点
                int last_row_pos = (size - 1) * size + col - 1;
                for (int fcol = 1; fcol <= size; fcol++) {
                    if (isOpen(1, fcol)) {
                        // 找到了某个打开的首行
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
        // 以1为启示下标判断
        if ((row <= 0 || row > size) || (col <= 0 || col > size)) {
            throw new IllegalArgumentException("越界!");
        }
    }

    // optional
    public static void main(String[] args) {

    }
}
