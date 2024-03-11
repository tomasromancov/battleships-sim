package AI;

import java.util.ArrayList;
import java.util.HashMap;

import game.Ship;

public class PropabilityTargeting extends TargetingApproach{
	int[][] heatMap;
	Ship[] ships;

	public PropabilityTargeting(int columns, int rows, Ship[] ships) {
		super(columns, rows);
		this.ships = ships;
	}
	
	public String play(char[][] grid, String lastLocation) throws Exception {
		//generate a heatmap by finding valid locations of ships where at least one ship segment overlays a *
		heatMap = generateEmptyMap();
		for(Ship ship: ships) {
			if(!ship.isSunk()) {
				HashMap<String, Integer> horizontalLocations = getValidPlacementSquares(grid, 'h', ship.getLength());
				for(String location: horizontalLocations.keySet()) {
					String[] locations = location.split("/");
					int row = Integer.parseInt(locations[0]);
					int column = Integer.parseInt(locations[1]);
					for(int x = 0; x < ship.getLength(); x++) {
						if(grid[row - 1][column - 1 + x] == '~') heatMap[row - 1][column - 1 + x] += horizontalLocations.get(location)*1.5;
					}
					
				}
				HashMap<String, Integer> vertiacalLocations = getValidPlacementSquares(grid, 'v', ship.getLength());
				for(String location: vertiacalLocations.keySet()) {
					String[] locations = location.split("/");
					int row = Integer.parseInt(locations[0]);
					int column = Integer.parseInt(locations[1]);
					for(int x = 0; x < ship.getLength(); x++) {
						if(grid[row - 1 + x][column - 1] == '~') heatMap[row - 1 + x][column - 1] += vertiacalLocations.get(location)*1.5;
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
	
	private HashMap<String, Integer> getValidPlacementSquares(char[][] grid, char orientation, int shipLength){
		HashMap<String, Integer> validSquares = new HashMap<String, Integer>();
		for(int i = 0; i < squares; i++) {
			//numeric locations to 2D coordinates;
			int row = i / columns + 1;  // Integer division to get the row
	        int column = i % columns + 1;  // modulo operation to get the column
	        boolean isValid = true;
	        int overlaySquares = 0;
	        if(grid[row - 1][ column -1] == '~' || grid[row - 1][ column -1] == '*') {
	        	if(grid[row - 1][ column -1] == '*') overlaySquares++;
	        	//check if placing the ship might cause it to cross an existing ship or overflow from the grid
	        	for(int x = 1; x < shipLength; x++) {
	        		try {
	        			if(orientation == 'h') {
	        				if(grid[row - 1][column + x -1] != '~' && grid[row - 1][column + x -1] != '*') {
	        					isValid = false;
	        					break;
		        			}else if(grid[row - 1][column + x -1] == '*') {
		        				overlaySquares++;
		        			}
	        			}else if(orientation == 'v') {
	        				if(grid[row + x - 1][column -1] != '~' && grid[row + x - 1][column -1] != '*') {
	        					isValid = false;
	        					break;
		        			}else if(grid[row + x - 1][column -1] == '*') {
		        				overlaySquares++;
		        			}
	        			}
	        		}catch(ArrayIndexOutOfBoundsException e) {
	        			isValid = false;
    					break;
	        		}catch(Exception e) {
	        			System.out.println("Error occured while getting valid placement squares: " + e);
	        		}
	        	}
	        	if(isValid && overlaySquares > 0) validSquares.put(row + "/"+ column, overlaySquares);
	        	
	        }
	        	
		}
		return validSquares;
	}
}
