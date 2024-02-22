package AI;

import java.util.ArrayList;

public class Approach {
	protected int columns;
	protected int rows;
	protected int squares;
	protected int targetColumn;
	protected int targetRow;
	
	public Approach(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		
		squares =  columns * rows;
	}
	
	public String play(char[][] grid) {
		//make a decision on which location should the approach play
		//return the location chosen by the algorithm
		return "";
	}
	
	public ArrayList<String> getValidSquares(char[][] grid){
		ArrayList<String> validSquares = new ArrayList<String>();
		for(int i = 0; i < squares; i++) {
			//numeric locations to 2D coordinates;
			int row = i / columns + 1;  // Integer division to get the row
	        int column = i % columns + 1;  // modulo operation to get the column
	        if(grid[row - 1][ column -1] == '~') {
	        	validSquares.add(row + "/"+ column);
	        }
	        	
		}
		return validSquares;
	}
	
	/**
	 * Generates a list of valid locations where a ship can be placed given its orientation and length.
	 * @param grid
	 * @param orientation
	 * @param shipLength
	 * @param spacing If true, squares that are next to squares with ship segments are not valid.
	 * @return A list of squares where a ship can be placed.
	 */
	protected ArrayList<String> getValidPlacementSquares(char[][] grid, char orientation, int shipLength, boolean spacing){
		ArrayList<String> validSquares = new ArrayList<String>();
		for(int i = 0; i < squares; i++) {
			//numeric locations to 2D coordinates;
			int row = i / columns + 1;  // Integer division to get the row
	        int column = i % columns + 1;  // modulo operation to get the column
	        if(grid[row - 1][ column -1] == '~') {
	        	boolean isValid = true;
	        	if(spacing) if(neighbour(grid, row, column, 's')) isValid = false;
	        	//check if placing the ship might cause it to cross an existing ship or overflow from the grid
	        	for(int x = 1; x < shipLength; x++) {
	        		try {
	        			if(orientation == 'h') {
	        				if(grid[row - 1][column + x -1] != '~') {
	        					isValid = false;
	        					break;
		        			}else if(spacing) if(neighbour(grid, row, column + x, 's')) isValid = false;
	        			}else if(orientation == 'v') {
	        				if(grid[row + x - 1][column -1] != '~') {
	        					isValid = false;
	        					break;
		        			}else if(spacing) if(neighbour(grid, row + x, column, 's')) isValid = false;
	        			}
	        		}catch(ArrayIndexOutOfBoundsException e) {
	        			isValid = false;
    					break;
	        		}catch(Exception e) {
	        			System.out.println("Error occured while getting valid placement squares: " + e);
	        		}
	        	}
	        	if(isValid) validSquares.add(row + "/"+ column);
	        	
	        }
	        	
		}
		return validSquares;
	}
	
	protected boolean neighbour(char[][] grid, int row, int column, char neighbour) {
		//check if there is a neighbour above
		if(row - 1 > 0) {
			if(grid[row - 2][column - 1] == neighbour) { //if location above holds the desired neighbour
				return true;
			}
		}
		//check if there is a neighbour to the right
		if(column + 1 <= columns) {
			if(grid[row - 1][column] == neighbour) { //if location to the right holds the desired neighbour
				return true;
			}
		}
		//check if there is a neighbour below
		if(row + 1 <= rows) {
			if(grid[row][column - 1] == neighbour) { //if location below holds the desired neighbour
				return true;
			}
		}
		//check if there is a neighbour to the left
		if(column - 1 > 0) {
			if(grid[row - 1][column - 2] == neighbour) { //if location to the left holds the desired neighbour
				return true;
			}
		}
		return false;
	}
	
	protected boolean gridIsEmpty(char[][] grid) {
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				if(grid[row][column] != '~') return false;
			}
		}
		return true;
	}
	
	protected static void printMap(int[][] map) {
		String grid = "";
		int rowNum = 1;
		
		for(int i = 1; i <= map[0].length; i++) {
			grid += " " + i ;
		}
		grid += "\n";
		
		for(int[] row : map) {
			grid += rowNum;
			for(int field : row) {
				grid += " " + field;
			}
			grid += "\n";
			rowNum++;
		}
		System.out.println(grid);
		
	}
	
	protected int[][] generateEmptyMap() {
		int[][] map = new int[rows][columns];
		for(int row = 0; row < map.length; row++) {
			for(int column = 0; column < map[row].length; column++) {
				map[row][column] = 0;
			}
		}
		return map;
			
	}
}
