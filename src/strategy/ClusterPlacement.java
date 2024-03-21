package strategy;

import java.util.ArrayList;

import game.Ship;

/**
 * A placement approach which puts all the ships together into one spot.
 * @author Tomáš Romancov
 *
 */
public class ClusterPlacement extends PlacementApproach{

	/**
	 * Cluster Placement constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 */
	public ClusterPlacement(int rows, int columns) {
		super(rows, columns);
	}

	/**
	 * Finds a location where a ship can be placed. First ship is placed randomly and subsequent ships are placed so that they always
	 * touch at least one other ship
	 * @param ship the ship currently being placed
	 * @param grid the current playing grid of the player placing the ship
	 * @throws Exception 
	 */
	public String play(Ship ship, char[][] grid) throws Exception {
		//choose random orientation
		char orientation;
		if((int)(Math.random() * 2) == 0) orientation = 'h';
		else orientation = 'v';
		
		boolean shipPlaced = false;
		ArrayList<String> validSquares;
		
		if(gridIsEmpty(grid)) {
			validSquares = getValidPlacementSquares(grid, orientation, ship.getLength(), false);
			//System.out.println("First Ship was placed randomly");
		}
		else {
			int i = 0;
			do {
				validSquares = getValidPlacementSquares(grid, orientation, ship.getLength());
				if(!validSquares.isEmpty()) { // if there is a valid square use it
					shipPlaced = true;
				}else { // if there is no valid square try placing the ship in a different orientation
					//System.out.println("Trying different orientation");
					if(orientation == 'h') orientation = 'v';
					else if(orientation == 'v') orientation = 'h';
				}
				i++;
				if(i > 10) break;
			}while(!shipPlaced);
		}
		
		if(validSquares.isEmpty()) {
			throw new Exception("Error: no valid location for ship to be placed.");
		}
		
		//choose random location
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		
		return (coordinates + "/" + orientation);

	}
	
	/**
	 * Generates a list of locations where a ship can be placed so that it touches at least one other ship
	 * @param grid the current playing grid of the player placing the ship
	 * @param orientation the orientation of the ship being placed
	 * @param shipLength the length of the ship being placed
	 * @return a list of locations where the given ship can be placed
	 */
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
