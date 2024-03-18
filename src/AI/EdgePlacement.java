package AI;

import java.util.ArrayList;

import game.Ship;

/**
 * Edge Placement is a placement approach which tries to place all of the players ships
 * along the edges of the playing grid
 * @author Tomáš Romancov
 *
 */
public class EdgePlacement extends PlacementApproach{
	private boolean spacing; //whether or not ships should have a 1 tile space between each other

	/**
	 * Edge Placement constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 * @param spacing whether or not ships can touch or should have at least a 1 tile space between them
	 */
	public EdgePlacement(int rows, int columns, boolean spacing) {
		super(rows, columns);
		this.spacing = spacing;
	}
	
	/**
	 * Finds a location where a ship can be placed. A ships are placed randomly around the edges.
	 * If there are no valid locations along the edges the ships are placed 1 square from the edge and so on.
	 * @param ship the ship currently being placed
	 * @param grid the current playing grid of the player placing the ship
	 */
	public String play(Ship ship, char[][] grid) {
		//choose random orientation
		char orientation;
		if((int)(Math.random() * 2) == 0) orientation = 'h';
		else orientation = 'v';
		
		boolean shipPlaced = false;
		boolean triedBothOrientations = false;
		int edgeSpaceAllowence = 0;
		ArrayList<String> validSquares;
		
		do {
			validSquares = getValidPlacementSquares(grid, orientation, ship.getLength(), spacing);
			ArrayList<String> squaresIterator = new ArrayList<String>(validSquares);
			for(String location: squaresIterator) {
				String[] locations = location.split("/");
				int row = Integer.parseInt(locations[0]);
				int column = Integer.parseInt(locations[1]);
				if(orientation == 'h') {
					if(row > (1 + edgeSpaceAllowence) && row < (rows - edgeSpaceAllowence)) {
						validSquares.remove(location);
						//System.out.println("Row: " + row + " was removed");
					}
				}
				else if(orientation == 'v') {
					if(column > (1 + edgeSpaceAllowence) && column < (columns - edgeSpaceAllowence)) {
						validSquares.remove(location);
						//System.out.println("Column: " + column + " was removed");
					}
				}
			}
			if(!validSquares.isEmpty()) { // if there is a valid edge square use it
				shipPlaced = true;
			}else if(triedBothOrientations){ // if there is no valid edge square for either orientation increase the allowed area
				edgeSpaceAllowence +=1;
				triedBothOrientations = false;
			}else { // if there is no valid edge square try placing the ship in a different orientation
				//System.out.println("Trying different orientation");
				triedBothOrientations = true;
				if(orientation == 'h') orientation = 'v';
				else if(orientation == 'v') orientation = 'h';
			}
		}while(!shipPlaced);
		
		
		//choose random location
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		
		return (coordinates + "/" + orientation);
	}

}
