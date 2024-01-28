package game;

import java.util.Arrays;

public class Ship {
	//change this thing to two integer values row and column
	private int row;
	private int column;
	private int [][] occupiedSquares; //List of coordinates which the ship occupies and if they were hit (1 for yes 0 for no)
	private int length; //Length of the ship
	private boolean sunk = false; //States if the ship is sunken or not (false by default)
	private char orientation = 'h'; //Orientation in which the ship is placed, can be horizontal or vertical (horizontal by default)
	
	/**
	 * Creates a new Ship instance based on the ships length
	 * @param length - the length of the ship in squares
	 */
	public Ship(int length) {
		this.length = length;
		occupiedSquares = new int[length][3];
	}

	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

	public void setLocation(int row, int column) {
		this.row = row;
		this.column = column;
		//mark all the locations that are occupied by this ship
		for(int i = 0; i < length; i++) {
			if(orientation == 'h') occupiedSquares[i] = new int[] {row, column + i, 0};
			else if(orientation == 'v') occupiedSquares[i] = new int[] {row + i, column, 0};
		}
	}

	public boolean isSunk() {
		return sunk;
	}

	public void setSunk(boolean sunken) {
		this.sunk = sunken;
	}

	public char getOrientation() {
		return orientation;
	}

	public void setOrientation(char orientation) {
		this.orientation = orientation;
	}

	public int getLength() {
		return length;
	}

	public int[][] getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setOccupiedSquares(int[][] occupiedSquares) {
		this.occupiedSquares = occupiedSquares;
	}

	@Override
	public String toString() {
		String segments = "";
		for(int[] segment: occupiedSquares) {
			segments += Arrays.toString(segment);
		}
		return "Ship [row=" + row + ", column=" + column + ", occupiedSquares=" + segments
				+ ", length=" + length + ", sunk=" + sunk + ", orientation=" + orientation + "]";
	}
	
	
	
}
