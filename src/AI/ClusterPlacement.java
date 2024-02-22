package AI;

import java.util.ArrayList;

import game.Ship;

public class ClusterPlacement extends PlacementApproach{

	public ClusterPlacement(int columns, int rows) {
		super(columns, rows);
		// TODO Auto-generated constructor stub
	}

	public String play(Ship ship, char[][] grid) {
		//choose random orientation
		char orientation;
		if((int)(Math.random() * 2) == 0) orientation = 'h';
		else orientation = 'v';
		
		boolean shipPlaced = false;
		ArrayList<String> validSquares;
		
		if(gridIsEmpty(grid)) {
			validSquares = getValidPlacementSquares(grid, orientation, ship.getLength(), false);
			System.out.println("First Ship was placed randomly");
		}
		else {
			int i = 0;
			do {
				validSquares = getValidPlacementSquares(grid, orientation, ship.getLength());
				if(!validSquares.isEmpty()) { // if there is a valid square use it
					shipPlaced = true;
				}else { // if there is no valid square try placing the ship in a different orientation
					System.out.println("Trying different orientation");
					if(orientation == 'h') orientation = 'v';
					else if(orientation == 'v') orientation = 'h';
				}
				i++;
				if(i > 10) break;
			}while(!shipPlaced);
		}
		
		
		//choose random location
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		
		return (coordinates + "/" + orientation);

	}
	
	private ArrayList<String> getValidPlacementSquares(char[][] grid, char orientation, int shipLength){
		ArrayList<String> validSquares = new ArrayList<String>();
		for(int i = 0; i < squares; i++) {
			//numeric locations to 2D coordinates;
			int row = i / columns + 1;  // Integer division to get the row
	        int column = i % columns + 1;  // modulo operation to get the column
	        if(grid[row - 1][ column -1] == '~') {
	        	boolean isValid = true;
	        	boolean isTouching = false;
	        	if(neighbour(grid, row, column, 's')) isTouching = true;
	        	//check if placing the ship might cause it to cross an existing ship or overflow from the grid
	        	for(int x = 1; x < shipLength; x++) {
	        		try {
	        			if(orientation == 'h') {
	        				if(grid[row - 1][column + x -1] != '~') {
	        					isValid = false;
	        					break;
		        			}
	        				if(neighbour(grid, row, column + x, 's')) isTouching = true;
	        			}else if(orientation == 'v') {
	        				if(grid[row + x - 1][column -1] != '~') {
	        					isValid = false;
	        					break;
		        			}if(neighbour(grid, row + x, column, 's')) isTouching = true;
	        			}
	        		}catch(ArrayIndexOutOfBoundsException e) {
	        			isValid = false;
    					break;
	        		}catch(Exception e) {
	        			System.out.println("Error occured while getting valid placement squares: " + e);
	        		}
	        	}
	        	if(isValid && isTouching) validSquares.add(row + "/"+ column);
	        	
	        }
	        	
		}
		return validSquares;
	}
}
