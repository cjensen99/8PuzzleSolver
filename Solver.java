
package a04;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * To implement the A* algorithm, you must use the MinPQ data type from algs4.jar for the priority queue.
 * 
 * @author hector_juarez, and Cannon Jensen
 *
 */
public class Solver {
	private Stack<Board> solution;
	private int moves;

	/**
	 * Constructor 
	 * find a solution to the initial board (using the A* algorithm)
	 * 
	 * throw a java.lang.IllegalArgumentException if the initial board is not solvable and 
	 * a java.lang.NullPointerException if the initial board is null.
	 * 
	 * In the while loop: if the goal has been reached, populate fields of interest and return
	 * also look back one move to prevent useless looping
	 * 
	 * @param initial
	 */
	public Solver(Board initial) {
		if (initial.isSolvable() == false) {
			throw new IllegalArgumentException("Must be Solvable");
		}
		if (initial == null) {
			throw new NullPointerException("Cannot be null");
		}

		solution = new Stack<>();
		MinPQ<NewMove> pq = new MinPQ<>();
		pq.insert(new NewMove(initial, 0, null));

		while(true) {
			NewMove move = pq.delMin();
			if(move.board.isGoal()) {
				this.moves = move.moves;
				do {
					solution.push(move.board);
					move = move.last;
				}
				while(move != null);
				return;
			}
			
			for(Board next : move.board.neighbors()) {
				if(move.last == null || !next.equals(move.last.board)) {
					pq.insert(new NewMove(next, move.moves+1, move));
				}
					
			}
		}

	}

	/**
	 * min number of moves to solve initial board
	 * 
	 * @return
	 */
	public int moves() {
		return this.moves;

	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Board> solution() {
		return solution;

	}

	/**
	 * Helper class to search/move through nodes that implements comparable.
	 * 
	 * return normal difference of priority functions
	 * if priority is the same give preference to the one with more moves
	 *
	 */
	private class NewMove implements Comparable<NewMove>{
		private Board board;
		private int moves;
		private NewMove last;


		public NewMove(Board board, int moves, NewMove previous) {
			this.board = board;
			this.moves = moves;
			this.last = previous;
		}

		@Override
		public int compareTo(NewMove o) {
			int difference = this.board.manhattan() + this.moves - o.board.manhattan() - o.moves;
			
			if (difference != 0) {
				return difference; 
			}
			if (this.moves > o.moves) {
				return -1; 
			}
			return 1;
		}
	}

	/**
	 * To test code from https://www.cs.princeton.edu/courses/archive/fall14/cos226/assignments/8puzzle.html
	 * 
	 * solve a slider puzzle (given below)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// check if puzzle is solvable; if so, solve it and output solution
		if (initial.isSolvable()) {
			Solver solver = new Solver(initial);
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
		// if not, report unsolvable
		else {
			StdOut.println("Unsolvable puzzle");
		}


	}

}
