package AI;

import java.util.ArrayList;

/**
 * Random Shooting is shooting approach which shoots at random locations.
 * @author Tomáš Romancov
 *
 */
public class RandomShooting extends Approach{

	/**
	 * Random Shooting constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 */
	public RandomShooting(int rows, int columns) {
		super(rows, columns);
		
	}

	/**
	 * Randomly picks a a location on the grid to shoot at
	 * @param grid the current grid of the opposing player
	 * @return the location where the player should shoot 
	 */
	public String play(char[][] grid) {
		//read the grid to find locations that are valid to shoot at
		ArrayList<String> validSquares = getValidSquares(grid);
		
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		validSquares.remove(randomLocation);
		
		return coordinates;
	}
	
}
