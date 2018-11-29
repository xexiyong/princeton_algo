/* *****************************************************************************
 *  Name: sunzg
 *  Date: 2018-11-28
 *  Description: board class
 **************************************************************************** */

public class Board {

    private int[] mBlocks;
    private int size;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        size = blocks.length;
        mBlocks = new int[size * size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                mBlocks[i * size + j] = blocks[i][j];
            }
    }

    public int dimension() {
        // board dimension n
        return size;
    }

    public int hamming() {
        // number of blocks out of place
        int distance = 0;
        for (int i = 0; i < size; i++) {
            if (mBlocks[i] != i + 1 && mBlocks[i] != 0) {
                distance += 1;
            }
        }
        return distance;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int distance = 0;
        for (int i = 0; i < size; i++) {
            if (mBlocks[i] != i + 1 && mBlocks[i] != 0) {
                int goalPos = mBlocks[i] - 1;
                int goalX = goalPos / size;
                int goalY = goalPos - (goalX - 1) * size;
                int actualX = i / size;
                int actualY = i - (actualX - 1) * size;
                distance += Math.abs(actualX - goalX) + Math.abs(actualY - goalY);
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
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == null) return false;
        Board t = (Board) y;
        return t.toString().equals(toString());
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        String content = String.valueOf(size) + '\n';
        for (int i = 0; i < size; i++) {
            content += ' ' + String.valueOf(mBlocks[i]);
            if ((i + 1) % size == 0) {
                content += '\n';
            }
        }
        return content;
    }

    public static void main(String[] args) {

    }
}
