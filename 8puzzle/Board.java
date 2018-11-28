/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
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
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
    }

    public boolean isGoal() {
        // is this board the goal board?
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
    }

    public boolean equals(Object y) {
        // does this board equal y?
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
    }

    public static void main(String[] args) {

    }
}
