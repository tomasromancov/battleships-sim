package strategy;

import java.util.ArrayList;

import game.Ship;

/**
 * The Probability Shooting approach is a shooting approach which assigns each location a numeric value representing
 * the number of ship segments that could be located there and shoots at the highest value locations.
 * @author Tomáš Romancov
 *
 */
public class ProbabilityShooting extends Approach{
	int[][] heatMap; //numeric representation of the probability a ship is located there
	Ship[] ships; //list of opponents ships

	/**
	 * Probability Shooting constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 * @param ships list of opponents ships
	 */
	public ProbabilityShooting(int rows, int columns, Ship[] ships) {
		super(rows, columns);
		this.ships = ships;
	}
	
	/**
	 * Finds a location where the most ship segments can fit and shoots at it
	 * @param grid the current grid of the opposing player
	 * @return the location where the player should shoot
	 */
	public String play(char[][] grid){
		heatMap = generateEmptyMap();
		//generate a heatmap for ship appearance frequency
		for(Ship ship: ships) {
			if(!ship.isSunk()) {
				ArrayList<String> horizontalLocations = getValidPlacementSquares(grid, 'h', ship.getLength(), false);
				for(String location: horizontalLocations) {
					String[] locations = location.split("/");
					int row = Integer.parseInt(locations[0]);
					int column = Integer.parseInt(locations[1]);
					for(int x = 0; x < ship.getLength(); x++) {
						heatMap[row - 1][column - 1 + x] += 1;
					}
					
				}
				ArrayList<String> vertiacalLocations = getValidPlacementSquares(grid, 'v', ship.getLength(), false);
				for(String location: vertiacalLocations) {
					String[] locations = location.split("/");
					int row = Integer.parseInt(locations[0]);
					int column = Integer.parseInt(locations[1]);
					for(int x = 0; x < ship.getLength(); x++) {
						heatMap[row - 1 + x][column - 1] += 1;
					}
				}
			}
		}
		
		//find the highest value
		int highest = 0;
		for(int row = 0; row < heatMap.length; row++) {
			for(int column = 0; column < heatMap[row].length; column++) {
				if(heatMap[row][column] > highest) highest = heatMap[row][column];
			}
		}
		//create a list of locations with the highest value
		ArrayList<String> validSquares = new ArrayList<String>();
		for(int row = 0; row < heatMap.length; row++) {
			for(int column = 0; column < heatMap[row].length; column++) {
				if(heatMap[row][column] == highest) validSquares.add((row + 1) + "/" + (column +1));
			}
		}
		
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		
		printMap(heatMap);
		return coordinates;
	}

}
