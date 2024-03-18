package AI;

import java.util.ArrayList;

import game.Ship;

/**
 * Random Placement is a placement approach that randomly places ships on the playing grid.
 * @author Tomáš Romancov
 *
 */
public class RandomPlacement extends PlacementApproach {
	private boolean spacing; //whether or not ships should have a 1 tile space between each other

	/**
	 * Random Placement constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 * @param spacing whether or not ships can touch or should have at least a 1 tile space between them
	 */
	public RandomPlacement(int rows, int columns, boolean spacing) {
		super(rows, columns);
		this.spacing = spacing;
		
	}
	
	/**
	 * Finds a random location on the playing grid where a ship can be placed
	 * @param ship the ship currently being placed
	 * @param grid the current playing grid of the player placing the ship
	 * @return the location where the player should shoot 
	 */
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
