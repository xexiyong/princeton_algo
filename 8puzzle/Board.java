/* *****************************************************************************
 *  Name: sunzg
 *  Date: 2018-11-28
 *  Description: board class
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Iterator;

public class Board {

    private int[][] mBlocks;
    private int size;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        size = blocks.length;
        mBlocks = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                mBlocks[i][j] = blocks[i][j];
            }
    }

    public int dimension() {
        // board dimension n
        return size;
    }

    public int hamming() {
        // number of blocks out of place
        int distance = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (mBlocks[i][j] != (i * size + j + 1) && mBlocks[i][j] != 0) {
                    distance += 1;
                }
            }
        return distance;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int distance = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (mBlocks[i][j] != (i * size + j + 1) && mBlocks[i][j] != 0) {
                    int goalPos = mBlocks[i][j] - 1;
                    int goalX = goalPos / size;
                    int goalY = goalPos - (goalX - 1) * size;
                    // int actualX = i / size;
                    // int actualY = i - (actualX - 1) * size;
                    distance += Math.abs(i - goalX) + Math.abs(j - goalY);
                }
            }
        return distance;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] clone_blocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                clone_blocks[i][j] = mBlocks[i][j];
            }
        }
        int i = 0;
        int j = 0;
        int m = 1;
        int n = 0;
        if (clone_blocks[i][j] == 0) {
            j += 1;
        }
        if (clone_blocks[m][n] == 0) {
            n += 1;
        }
        int tmp = clone_blocks[i][j];
        clone_blocks[i][j] = clone_blocks[m][n];
        clone_blocks[m][n] = tmp;
        Board b = new Board(clone_blocks);
        return b;
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == null) return false;
        Board t = (Board) y;
        return t.toString().equals(toString());
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Neighbors<Board> b = new Neighbors<Board>();
        return b;
    }

    public class Neighbors<Board> implements Iterable<Board> {
        private ArrayList<Board> mList = new ArrayList<Board>();

        public Neighbors(Class<Board> clz) {
            int empty = emptyPos();
            int i = empty / size;
            int j = empty - i * size;

            // top
            if (i != 0) {
                int[][] left = getCopy();
                left[i - 1][j] = left[i][j];
                left[i][j] = 0;
                Board b = clz.Board();
                mList.add(b);
            }
            // bottom
            if (i != size - 1) {
                int[][] bottom = getCopy();
                bottom[i + 1][j] = bottom[i][j];
                bottom[i][j] = 0;
                Board b = new Board(bottom);
                mList.add(b);
            }
            // left
            if (j != 0) {
                int[][] left = getCopy();
                left[i][j - 1] = left[i][j];
                left[i][j] = 0;
                Board b = new Board(left);
                mList.add(b);
            }
            // right
            if (j != size - 1) {
                int[][] left = getCopy();
                left[i][j + 1] = left[i][j];
                left[i][j] = 0;
                Board b = new Board(left);
                mList.add(b);
            }
        }

        @Override
        public Iterator<Board> iterator() {
            return mList.iterator();
        }

        public int[][] getCopy() {
            int[][] cloneBlock = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cloneBlock[i][j] = mBlocks[i][j];
                }
            }
            return cloneBlock;
        }

        public int emptyPos() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (mBlocks[i][j] == 0)
                        return i * size + j;
                }
            }
            return -1;
        }
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        String content = String.valueOf(size) + '\n';
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                content += ' ' + String.valueOf(mBlocks[i]);
            }
            content += '\n';
        }
        return content;
    }

    public static void main(String[] args) {

    }
}
