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
		
//		//generate a list of integer arrays that represent the locations on the playing grid which have not been played yet
//		validSquares = new ArrayList<Integer[]>();
//		//fills valid squares with coordinates based on the size and dimensions of the grid
//		for(int i = 0; i < squares; i++) {
//			//numeric locations to 2D coordinates;
//			int row = i / columns + 1;  // Integer division to get the row
//	        int column = i % columns + 1;  // Modulo operation to get the column
//			validSquares.add(new Integer[] {row, column});
//		}
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
	
	public ArrayList<String> getValidPlacementSquares(char[][] grid, char orientation, int shipLength){
		ArrayList<String> validSquares = new ArrayList<String>();
		for(int i = 0; i < squares; i++) {
			//numeric locations to 2D coordinates;
			int row = i / columns + 1;  // Integer division to get the row
	        int column = i % columns + 1;  // modulo operation to get the column
	        if(grid[row - 1][ column -1] == '~') {
	        	boolean isValid = true;
	        	//check if placing the ship might cause it to cross an existing ship or overflow from the grid
	        	for(int x = 1; x < shipLength; x++) {
	        		try {
	        			if(orientation == 'h') {
	        				if(grid[row - 1][column + x -1] != '~') {
	        					isValid = false;
	        					break;
		        			}
	        			}else if(orientation == 'v') {
	        				if(grid[row + x - 1][column -1] != '~') {
	        					isValid = false;
	        					break;
		        			}
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
}
