/* *****************************************************************************
 *  Name: sunzg
 *  Date: 2018-11-28
 *  Description: solver class
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;

public class Solver {

    private MinPQ<Board> openSet = new MinPQ<>();
    private MinPQ<Board> closeSet = new MinPQ<>();
    private int moveNumer = 0;
    private ArrayList<Board> solutionList = new ArrayList<>();

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        openSet.insert(initial);
        while (!openSet.isEmpty()) {
            Board b = openSet.min();
            if (b.isGoal()) {
                break;
            }
            for (Board board : b.neighbors()) {
                // calc the f(board): the distance to the initial + the distance to the goal
                int f = moveNumer + board.hamming();

                // check whether in the open set
                boolean inOpen = false;
                Iterator<Board> iter = openSet.iterator();
                while (iter.hasNext()) {
                    if (iter.next().toString().equals(board.toString())) {
                        inOpen = true;
                        break;
                    }
                }
                // if this neighbor in open set
                if (inOpen) {
                    
                }
            }
        }

    }

    public boolean isSolvable() {
        // is the initial board solvable?
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return moveNumer;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        return solutionList;
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
