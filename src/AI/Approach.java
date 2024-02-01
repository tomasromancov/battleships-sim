package AI;

import java.util.ArrayList;

public class Approach {
	protected int columns;
	protected int rows;
	protected int squares;
	protected int targetColumn;
	protected int targetRow;
	protected ArrayList<Integer[]> validSquares;
	
	public Approach(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		
		squares =  columns * rows;
		
		//generate a list of integer arrays that represent the locations on the playing grid which have not been played yet
		validSquares = new ArrayList<Integer[]>();
		//fills valid squares with coordinates based on the size and dimensions of the grid
		for(int i = 0; i < squares; i++) {
			//numeric locations to 2D coordinates;
			int row = i / columns + 1;  // Integer division to get the row
	        int column = i % columns + 1;  // Modulo operation to get the column
			validSquares.add(new Integer[] {row, column});
		}
	}
	
	public String play(char[][] grid) {
		//make a decision on which location should the approach play
		//return the location chosen by the algorithm
		return "";
	}
}
