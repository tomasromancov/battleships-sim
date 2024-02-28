package AI;

import java.util.ArrayList;

import game.Ship;

public class ParityShooting extends Approach{
	private Ship[] ships;

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
		
		ArrayList<String> validSquares = new ArrayList<String>();
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				if(((column%spacing) -row)%spacing == 0 && grid[row][column] == '~') validSquares.add((row+1) + "/"+ (column+1));
			}
		}
		
		System.out.println(validSquares.toString());
		
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		
		//if the distance between the chosen location and the closest revealed location/edge in each direction
		//is smaller than the current spacing, exclude those locations in the future
		String[] locations = coordinates.split("/");
		int row = Integer.parseInt(locations[0]);
		int column = Integer.parseInt(locations[1]);
		int distance = 1;
		for(String direction: new String[] {"above", "right", "below", "left"}) {
			switch(direction) {
				case "above":
					while(!neighbourIsRevealed(grid, row, column, distance, "above")) {
						distance++;
					}
			}
			
				
		}
		
		return coordinates;
	}
	
	private boolean neighbourIsRevealed(char[][] grid, int row, int column, int distance, String direction) {
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
				if(grid[row - 1][column + distance] == '~') {
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
			if(grid[row - 1][column - 2] == '~') {
				return false;
			}
		}else {
			System.out.println("Error: unknown direction passed to neighbourIsRevealed in ParityShooting");
		}
		
		return true;
	}

}
