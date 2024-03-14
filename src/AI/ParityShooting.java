package AI;

import java.util.ArrayList;

import game.Ship;

public class ParityShooting extends Approach{
	private Ship[] ships;
	private ArrayList<String> excludedLocations = new ArrayList<String>();
	private String lastPlay = "firstPlay";

	public ParityShooting(int columns, int rows, Ship[] ships) {
		super(columns, rows);
		this.ships = ships;
	}
	
	public String play(char[][] grid){
		//generate list of locations that confirm to parity, so spacing between shots is equal to the currently smallest ship
		int spacing = 999999999; 
		for(Ship ship: ships) {
			if(!ship.isSunk()) {
				if(ship.getLength() < spacing) spacing = ship.getLength();
			}
		}
		
		if(!lastPlay.equals("firstPlay")) {
			//check for locations around the last chosen location and if no ship can fit in any exclude those locations in the future
			String[] locations = lastPlay.split("/");
			int lastRow = Integer.parseInt(locations[0]);
			int lastColumn = Integer.parseInt(locations[1]);
			int distance = 1;
			ArrayList<String> horizontalSquares = getValidPlacementSquares(grid, 'h', spacing, false);
			ArrayList<String> verticalSquares = getValidPlacementSquares(grid, 'v', spacing, false);
			for(String direction: new String[] {"above", "right", "below", "left"}) {
				System.out.println("looping, direction: " + direction );
				switch(direction) {
					case "above":
						distance = 1;
						while(!neighbourIsRevealed(grid, lastRow, lastColumn, distance, "above")) {
							distance++;
						}
						for(int i = 1; i < distance; i++) {
							boolean overlay = false;
							//check if the smallest horizontally placed ship can overlay the square being checked
							for(String location: horizontalSquares) {
								String[] starterLocations = location.split("/");
								int row = Integer.parseInt(starterLocations[0]);
								int column = Integer.parseInt(starterLocations[1]);
								
								for(int x = 0; x < spacing; x++) {
									if(lastRow - i == row && lastColumn == column + x) {
										overlay = true;
										break;
									}
								}
								
							}
							//check if the smallest vertically placed ship can overlay the square being checked
							if(!overlay) {
								for(String location: verticalSquares) {
									String[] starterLocations = location.split("/");
									int row = Integer.parseInt(starterLocations[0]);
									int column = Integer.parseInt(starterLocations[1]);
									for(int x = 0; x < spacing; x++) {
										if(lastRow - i == row + x && lastColumn == column) {
											overlay = true;
											break;
										}
									}
									
								}
							}
							//if no ship segments can be hidden in the location refrain from searching it in the future
							if(!overlay) {
								excludedLocations.add(((lastRow - i) + "/" + lastColumn));
							}
						}
						break;
					case "right":
						distance = 1;
						while(!neighbourIsRevealed(grid, lastRow, lastColumn, distance, "right")) {
							distance++;
						}
						for(int i = 1; i < distance; i++) {
							boolean overlay = false;
							//check if the smallest horizontally placed ship can overlay the square being checked
							for(String location: horizontalSquares) {
								String[] starterLocations = location.split("/");
								int row = Integer.parseInt(starterLocations[0]);
								int column = Integer.parseInt(starterLocations[1]);
								
								for(int x = 0; x < spacing; x++) {
									if(lastRow == row && lastColumn + i == column + x) {
										overlay = true;
										break;
									}
								}
								
							}
							//check if the smallest vertically placed ship can overlay the square being checked
							if(!overlay) {
								for(String location: verticalSquares) {
									String[] starterLocations = location.split("/");
									int row = Integer.parseInt(starterLocations[0]);
									int column = Integer.parseInt(starterLocations[1]);
									for(int x = 0; x < spacing; x++) {
										if(lastRow == row + x && lastColumn + i == column) {
											overlay = true;
											break;
										}
									}
									
								}
							}
							//if no ship segments can be hidden in the location refrain from searching it in the future
							if(!overlay) {
								excludedLocations.add((lastRow + "/" + (lastColumn + i)));
							}
						}
						break;
					case "below":
						distance = 1;
						while(!neighbourIsRevealed(grid, lastRow, lastColumn, distance, "below")) {
							distance++;
						}
						for(int i = 1; i < distance; i++) {
							boolean overlay = false;
							//check if the smallest horizontally placed ship can overlay the square being checked
							for(String location: horizontalSquares) {
								String[] starterLocations = location.split("/");
								int row = Integer.parseInt(starterLocations[0]);
								int column = Integer.parseInt(starterLocations[1]);
								for(int x = 0; x < spacing; x++) {
									if(lastRow + i == row && lastColumn == column + x) {
										overlay = true;
										break;
									}
								}
								
							}
							//check if the smallest vertically placed ship can overlay the square being checked
							if(!overlay) {
								for(String location: verticalSquares) {
									String[] starterLocations = location.split("/");
									int row = Integer.parseInt(starterLocations[0]);
									int column = Integer.parseInt(starterLocations[1]);
									for(int x = 0; x < spacing; x++) {
										if(lastRow + i == row + x && lastColumn == column) {
											overlay = true;
											break;
										}
									}
									
								}
							}
							//if no ship segments can be hidden in the location refrain from searching it in the future
							if(!overlay) {
								excludedLocations.add(((lastRow + i) + "/" + lastColumn));
							}
						}
						break;
					case "left":
						distance = 1;
						while(!neighbourIsRevealed(grid, lastRow, lastColumn, distance, "left")) {
							distance++;
						}
						for(int i = 1; i < distance; i++) {
							boolean overlay = false;
							//check if the smallest horizontally placed ship can overlay the square being checked
							for(String location: horizontalSquares) {
								String[] starterLocations = location.split("/");
								int row = Integer.parseInt(starterLocations[0]);
								int column = Integer.parseInt(starterLocations[1]);
								
								for(int x = 0; x < spacing; x++) {
									if(lastRow == row && lastColumn - i == column + x) {
										overlay = true;
										break;
									}
								}
								
							}
							//check if the smallest vertically placed ship can overlay the square being checked
							if(!overlay) {
								for(String location: verticalSquares) {
									String[] starterLocations = location.split("/");
									int row = Integer.parseInt(starterLocations[0]);
									int column = Integer.parseInt(starterLocations[1]);
									for(int x = 0; x < spacing; x++) {
										if(lastRow == row + x && lastColumn - i == column) {
											overlay = true;
											break;
										}
									}
									
								}
							}
							//if no ship segments can be hidden in the location refrain from searching it in the future
							if(!overlay) {
								excludedLocations.add((lastRow + "/" + (lastColumn - i)));
							}
						}
						break;
					default:
						System.out.println("Error: unknown direction passed to neighbourIsRevealed in ParityShooting");
						break;
				}
				
					
			}
		}
		
		ArrayList<String> validSquares = new ArrayList<String>();
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				if(!excludedLocations.contains((row+1) + "/"+ (column+1))) {
					if(((column%spacing) -row)%spacing == 0 && grid[row][column] == '~') validSquares.add((row+1) + "/"+ (column+1));
				}
			}
		}
		
		System.out.println(validSquares.toString());
		
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		excludedLocations.add(coordinates);
		lastPlay = coordinates;
		System.out.println(coordinates);
		
		
		
		System.out.println(excludedLocations.toString());
		return coordinates;
	}
	
	private boolean neighbourIsRevealed(char[][] grid, int row, int column, int distance, String direction) {
		System.out.println(row + ", " + column + ", " + distance + ", " + direction);
		//check if there is a neighbour above
		if(direction.equals("above")) {
			if(row - distance > 0) {
				if(grid[row - (1 + distance)][column - 1] == '~') {
					return false;
				}
				
			}
		}
		//check if there is a neighbour to the right
		else if(direction.equals("right")) {
			if(column + distance <= columns) {
				if(grid[row - 1][column + (distance -1)] == '~') {
					return false;
				}
			}
		}
		//check if there is a neighbour below
		else if(direction.equals("below")) {
			if(row + distance <= rows) {
				if(grid[row][column - 1] == '~') {
					return false;
				}
			}
		}
		//check if there is a neighbour to the left
		else if(direction.equals("left")) {
			if(column - distance > 0) {
				if(grid[row - 1][column - 2] == '~') {
					return false;
				}
			}
		}else {
			System.out.println("Error: unknown direction passed to neighbourIsRevealed in ParityShooting");
		}
		
		return true;
	}

}
