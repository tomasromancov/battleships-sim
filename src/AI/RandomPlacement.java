package AI;

import java.util.ArrayList;

import game.Ship;

public class RandomPlacement extends PlacementApproach {
	private boolean spacing;

	public RandomPlacement(int columns, int rows, boolean spacing) {
		super(columns, rows);
		this.spacing = spacing;
		
	}
	
	//play gets a ship to place on the grid
	public String play(Ship ship, char[][] grid) {
		//choose random orientation
		char orientation;
		if((int)(Math.random() * 2) == 0) orientation = 'h';
		else orientation = 'v';
		
		ArrayList<String> validSquares = getValidPlacementSquares(grid, orientation, ship.getLength(), spacing);
		//System.out.println(validSquares.toString());
		
		//choose random location
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		
		return (coordinates + "/" + orientation);
	}
	
	public String locationToString(ArrayList<Integer[]> locations) {
		String locationString = "";
		for(Integer[] location: locations) {
			locationString += "[" + location[0] + ","+ location[1] +"]";
		}
		
		return locationString;
	}

}
