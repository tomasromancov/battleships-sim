package AI;

import java.util.ArrayList;

import game.Ship;

public class PropabilityShooting extends Approach{
	int[][] heatMap;
	Ship[] ships;

	public PropabilityShooting(int columns, int rows, Ship[] ships) {
		super(columns, rows);
		this.ships = ships;
	}
	
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
