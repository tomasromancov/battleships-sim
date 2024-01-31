package AI;

import java.util.ArrayList;

import game.Ship;

public class RandomPlacement extends Approach {

	public RandomPlacement(int columns, int rows) {
		super(columns, rows);
		
	}
	
	//play gets a ship to place on the grid
	public String play(Ship ship, char[][] grid) {
		//choose random orientation
		int orientation = (int)(Math.random() * 2);
		char orientationChar = '0';
		
		//narrow down valid locations based on ship length
		ArrayList<Integer[]> narrowedLocations = (ArrayList<Integer[]>) validSquares.clone();
		//horizontal
		if(orientation == 0) {
			orientationChar = 'h';
			for(int row = 0; row < grid.length; row++) {
				for(int column = 0; column < grid[row].length; column++) {
					//if grid space is occupied block all spaces to the left so that the ships dont overlap
					if(grid[row][column] != '~') {
						for(Integer[] location: validSquares) {
							if((location[0] == (row + 1)) && ((location[1] < (column + 1)) && !((location[1] < ((column + 1) - (ship.getLength() - 1)))))) {
								narrowedLocations.remove(location);
//								System.out.println("removed: " + location[0] + "," + location[1] );
//								System.out.println(locationToString(narrowedLocations));
							}
						}
						
					}
				}
			}
			//remove locations where the ship would be out of the playing grid
			ArrayList<Integer[]> narrowedLocationsIterator = (ArrayList<Integer[]>) narrowedLocations.clone();
			for(Integer[] location: validSquares) {
				if((location[1] + (ship.getLength() - 1)) > columns) {
					narrowedLocations.remove(location);
				}
			}
			
		}
		//vertical
		if(orientation == 1) {
			orientationChar = 'v';
			for(Integer[] location: validSquares) {
				if((location[0] + (ship.getLength() - 1)) > rows) {
					narrowedLocations.remove(location);
				}
			}
		}
		
		System.out.println(locationToString(narrowedLocations));
		
		int randomLocation = (int)(Math.random() * narrowedLocations.size());
		Integer [] coordinates = narrowedLocations.get(randomLocation);
		int locationIndex = validSquares.indexOf(coordinates);
		ArrayList<Integer[]> validSquaresIterator = (ArrayList<Integer[]>) validSquares.clone();
		
		//remove locations where ship will be placed for future placing
		if(orientation == 0) { //horizontal
			for(int i = 0; i < ship.getLength(); i++) {
				for(Integer[] location: validSquaresIterator) {
					if(location[0] == coordinates[0] && location[1] == coordinates[1] + i) {
						validSquares.remove(location);
					}
				}
			}
		}else if(orientation == 1) { //vertical
			for(int i = 0; i < ship.getLength(); i++) {
				for(Integer[] location: validSquaresIterator) {
					if(location[0] == coordinates[0] + i && location[1] == coordinates[1]) {
						validSquares.remove(location);
					}
				}
			}
			
		}
		
		
		//System.out.println(locationToString(validSquares));
		
		//pick a random location and adjust the valid locations
		return (coordinates[0] + "/" +  coordinates[1] + "/" + orientationChar);
	}
	
	public String locationToString(ArrayList<Integer[]> locations) {
		String locationString = "";
		for(Integer[] location: locations) {
			locationString += "[" + location[0] + ","+ location[1] +"]";
		}
		
		return locationString;
	}

}
