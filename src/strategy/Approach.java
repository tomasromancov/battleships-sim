package strategy;

import java.util.ArrayList;

/**
 * Approach is the top level class holding the shared logic of all the other approach classes
 * 
 * @author Tomáš Romancov
 *
 */
public class Approach {
	protected int rows; //number of rows on the playing grid
	protected int columns; //number of columns on the playing grid
	protected int squares; //total number of squares on the playing grid
	
	/**
	 * Approach constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 */
	public Approach(int rows, int columns) {
		this.columns = columns;
		this.rows = rows;
		
		squares =  columns * rows;
	}
	
	/**
	 * Makes a decision on which location should the approach play
	 * @param grid the current grid of the opposing player
	 * @return chosen location where the approach will make a play in the format row/column
	 */
	public String play(char[][] grid) {
		return "";
	}
	
	/**
	 * Generates and returns a list of valid locations where a shot can be made 
	 * (locations that are revealed are invalid)
	 * @param grid the grid from which the valid locations are generated
	 * @return a list of valid locations where a shot can be made
	 */
	protected ArrayList<String> getValidSquares(char[][] grid){
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
	 * Generates a list of valid starting locations where a ship can be placed given its orientation and length.
	 * @param grid the grid from which the valid locations are generated
	 * @param orientation the orientation in which the ship should be placed
	 * @param shipLength length of the ships which should be placed
	 * @param spacing if true, squares that are next to squares with ship segments are not valid.
	 * @return a list of locations where a ship can be placed
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
	
	/**
	 * Checks if there is a location holding a specific neighbour next to a given location on the grid
	 * @param grid the grid where the location and its neighbours are being searched for
	 * @param row the row of the location which neighbours are being searched for
	 * @param column the column of the location which neighbours are being searched for
	 * @param neighbour the character on the grid which is being searched for
	 * @return true if the given neighbour is located around the given location and false if not
	 */
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
	
	/**
	 * Checks if a given grid has any revealed locations or if they are all hidden
	 * @param grid the grid which is being searched for revealed locations
	 * @return true if grid has no revealed locations
	 */
	protected boolean gridIsEmpty(char[][] grid) {
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				if(grid[row][column] != '~') return false;
			}
		}
		return true;
	}
	
	/**
	 * Prints a visual representation of a map which holds numeric 
	 * values representing a score given to each location on the playing grid
	 * @param map the map that is to be printed
	 */
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
	
	/**
	 * Generates an empty map to hold numeric 
	 * values representing a score given to each location on the playing grid
	 * @return an empty map (2d int array)
	 */
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
