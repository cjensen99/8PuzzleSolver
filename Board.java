package a04;

import edu.princeton.cs.algs4.Queue;

public class Board {
	private final int[][] puzzle;
	private int n;
	private int rowZero;
	private int columnZero;
	private int misplaced;
	private int moves;
	
	public Board(int[][] blocks) {
		this.n = blocks.length;		
		puzzle = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				puzzle[i][j] = blocks[i][j];
				if(puzzle[i][j] == 0) {
					rowZero = i;
					columnZero = j;
				}
			}
		}	
	}
	
	public int size() {
		return n;
	}
	
	public int hamming() {
		misplaced = 0;
		int count;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == n - 1 && j == n -1) {
					continue;
				}
				count = j + (i * n) + 1;
				if (puzzle[i][j] != count) {
					misplaced++;
				}	
			}
		}
		return misplaced;
	}
	
	public int manhattan() {
		moves = 0;
		int desiredRow;
		int desiredColumn;
		int count;
		int thisSquare;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				thisSquare = puzzle[i][j];
				if (thisSquare == 0 ) {
					continue;
				}
				count = j + (i * n) + 1;
				
				if (thisSquare != count) {
					desiredRow = (thisSquare - 1) / n;
					desiredColumn = (thisSquare - 1) % n;
					moves += absoluteValue(desiredRow - i);
					moves += absoluteValue(desiredColumn - j);
				}	
			}
		}
		return moves;
	}
	
	public boolean isGoal() {
		int count;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if ((i == n - 1) && (j == n - 1)) {
					continue;
				}
				count = j + (i * n) + 1;
				if (puzzle[i][j] != count) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isSolvable() {
		int inversions = 0;
		int zeroLocation = 0;
		
		int[] array = new int[n * n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				array[j + (i * n)] = puzzle[i][j];
				if (puzzle[i][j] == 0) {
					zeroLocation = j + (i * n);
				}
			}
		}
		
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0) continue;
			for (int j = i; j < array.length; j++) {
				if (array[j] < array[i] && array[j] != 0) {
					inversions++;
				}
			}
		}
		
		boolean isEvenBoard = (n % 2) == 0;
		if (isEvenBoard) inversions += zeroLocation / n;
		boolean isEvenInversions = (inversions % 2) == 0;
		
		return isEvenBoard != isEvenInversions;
	}
	
	public boolean equals(Object y) {
		Board board = (Board) y;
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		if (board.size() != this.size()) return false;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.puzzle[i][j] != board.puzzle[i][j]) {
					return false;
				}
			}
		}
		return true;		
	}
	
	public Iterable<Board> neighbors() {
		Queue<Board> queue = new Queue<Board>();
		
		if(rowZero > 0) queue.enqueue(new Board(returnNeighbor(puzzle, - 1, 0)));
		if(rowZero < n - 1) queue.enqueue(new Board(returnNeighbor(puzzle, 1, 0)));
		if(columnZero > 0) queue.enqueue(new Board(returnNeighbor(puzzle, 0, - 1)));
		if(columnZero < n - 1) queue.enqueue(new Board(returnNeighbor(puzzle, 0, 1)));
		return queue;
	}
	
	private int[][] returnNeighbor(int[][] board, int row, int column) {
		int[][] tempBoard = copyArray(board);
		tempBoard[rowZero][columnZero] = puzzle[rowZero + row][columnZero + column];
		tempBoard[rowZero + row][columnZero + column] = 0;
		return tempBoard;
	}
	
	private int absoluteValue(int number) {
		if (number < 0) {
			return number * -1;
		}
		return number;
	}
	
	private int[][] copyArray(int[][] array) {
		int[][] copy = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				copy[i][j] = array[i][j];
			}
		}
		return copy;
	}
	
	public String toString() {
		String table = n + "\n";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				table += String.format("%2d ", puzzle[i][j]);
			}
			table += "\n";
		}
		return table;
	}
	
	public static void main(String[] args) {
		int[][] table = new int[4][4];
		table[0][0] = 1;
		table[0][1] = 2;
		table[0][2] = 3;
		table[0][3] = 4;
		table[1][0] = 6;
		table[1][1] = 5;
		table[1][2] = 7;
		table[1][3] = 8;
		table[2][0] = 9;
		table[2][1] = 10;
		table[2][2] = 11;
		table[2][3] = 12;
		table[3][0] = 13;
		table[3][1] = 14;
		table[3][2] = 15;
		table[3][3] = 0;
		
//		int[][] table = new int[1][1];
//		table[0][0] = 0;
		
		Board board = new Board(table);
		
//		System.out.println(board);
//		System.out.println();
//		System.out.println();
//		System.out.println();
		
//		System.out.println(board.neighbors());
		
		System.out.println(board.isSolvable());
	}
}
