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

    private class BoardUnit {
        private Board Board = null;
        private Board Predecessor = null;
        private int moves = 0;

        public BoardUnit(Board board, int move, Board predecessor) {
            Board = board;
            Predecessor = predecessor;
            moves = move;
        }

        public void setPredecessor(Board board) {
            Predecessor = board;
        }

        public Board getPredecessor() {
            return Predecessor;
        }

        public void setMoves(int move) {
            moves = move;
        }

        public int getMoves() {
            return moves;
        }

        public Board getBoard() {
            return Board;
        }
    }

    private MinPQ<BoardUnit> openSet = new MinPQ<>();
    private MinPQ<BoardUnit> closeSet = new MinPQ<>();
    private int moveNumer = 0;
    private ArrayList<Board> solutionList = new ArrayList<>();
    private boolean resolvable = false;

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        openSet.insert(new BoardUnit(initial, 0, null));
        while (!openSet.isEmpty()) {
            BoardUnit b = openSet.min();
            if (b.getBoard().isGoal()) {
                resolvable = true;
                break;
            }
            for (Board board : b.getBoard().neighbors()) {
                // calc the f(board): the distance to the initial + the distance to the goal
                int f = moveNumer + board.manhattan();

                // check whether in the open set
                BoardUnit bu;
                boolean inOpen = false;
                int fInOpen = 0;
                Iterator<BoardUnit> iter = openSet.iterator();
                while (iter.hasNext()) {
                    bu = iter.next();
                    if (bu.getBoard().toString().equals(board.toString())) {
                        inOpen = true;
                        fInOpen = bu.getBoard().manhattan() + bu.getMoves();
                        break;
                    }
                }
                // if this neighbor in open set && new f(board) < the f(board) in the openSet;
                if (inOpen && f < fInOpen && bu != null) {
                    bu.setMoves(moveNumer);
                }

                //check whether in the closed set
                boolean inClosed = false;
                Iterator<BoardUnit> iter2 = closeSet.iterator();
                while (iter2.hasNext()) {
                    if (iter2.next().getBoard().toString().equals(board.toString())) {
                        inClosed = true;
                    }
                }
                // if this neighbor in closed set
                if (inClosed) {
                    continue;
                }

                // if not in open set and not in closed set
                if (!inOpen && !inClosed) {
                    BoardUnit newNode = new BoardUnit(board, moveNumer, b.getBoard());
                    openSet.insert(newNode);
                }
            }
            // insert cur node in closed set
            closeSet.insert(new BoardUnit(b.getBoard(), moveNumer, b.getPredecessor()));
        }

    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return resolvable;
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
