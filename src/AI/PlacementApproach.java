package AI;

import game.Ship;

/**
 * Top level class for placement approach logic
 * @author Tomáš Romancov
 *
 */
public class PlacementApproach extends Approach{

	/**
	 * Placement Approach constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 */
	public PlacementApproach(int rows, int columns) {
		super(rows, columns);
		
	}

	public String play(Ship ship, char[][] grid) {
		return "";
	}
}
