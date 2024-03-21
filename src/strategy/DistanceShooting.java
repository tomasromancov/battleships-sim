package strategy;

import java.util.ArrayList;

/**
 * A shooting approach which tries to shoot the largest distance from the currently revealed locations.
 * @author Tomáš Romancov
 *
 */
public class DistanceShooting extends Approach{
	int[][] distanceMap; //number representation of the distance to the closest revealed location for each unrevealed location

	/**
	 * Distance Shooting constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 */
	public DistanceShooting(int rows, int columns) {
		super(rows, columns);
	}
	
	/**
	 * Finds the location that is the furtherest from any currently revealed location.
	 * @param grid the current grid of the opposing player
	 */
	public String play(char[][] grid){
		fillDistanceMap();
		//generate a distancemap detailing the distance for every square to the nearest revealed square
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				boolean revealedSquareFound = false; 
				int distance = 1;
				//if square is revealed give it a value of -1 to prevent shooting at revealed squares
				if(grid[row][column] != '~') {
					distanceMap[row][column] = -1;
					revealedSquareFound = true;
				}
				while(!revealedSquareFound){
					ArrayList<String> neighbours = getNeighbours(grid, row + 1, column + 1, distance);
					if(neighbours.isEmpty()){
						break;
					} 
					if(neighboursAreUnrevealed(grid, neighbours)) {
						distanceMap[row][column] += 1;
						distance += 1;
					}else revealedSquareFound = true;
				}
				
			}
		}
		
		//find the highest value
		int highest = 0;
		for(int row = 0; row < distanceMap.length; row++) {
			for(int column = 0; column < distanceMap[row].length; column++) {
				if(distanceMap[row][column] > highest) highest = distanceMap[row][column];
			}
		}
		
		if(highest == 0) { //find the square with the highest number of 0 neighbours
			
		}
		
		//create a list of locations with the highest value
		ArrayList<String> validSquares = new ArrayList<String>();
		for(int row = 0; row < distanceMap.length; row++) {
			for(int column = 0; column < distanceMap[row].length; column++) {
				if(distanceMap[row][column] == highest) validSquares.add((row + 1) + "/" + (column +1));
			}
		}
		
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		
		printDistanceMap(distanceMap);
		return coordinates;
	}
	
	/**
	 * prints a visual representation of each tiles distance to the closest revealed location
	 * @param map the map of distances to be printed 
	 */
	private static void printDistanceMap(int[][] map) {
		String grid = "";
		int rowNum = 1;
		
		for(int i = 1; i <= map[0].length; i++) {
			grid += " " + i ;
		}
		grid += "\n";
		
		for(int[] row : map) {
			grid += rowNum;
			for(int field : row) {
				grid += " " + field;
			}
			grid += "\n";
			rowNum++;
		}
		System.out.println(grid);
		
	}
	
	/**
	 * Fills an empty distance map with default values of 0
	 */
	private void fillDistanceMap() {
		distanceMap = new int[rows][columns];
		for(int row = 0; row < distanceMap.length; row++) {
			for(int column = 0; column < distanceMap[row].length; column++) {
				distanceMap[row][column] = 0;
			}
		}
			
	}

	/**
	 * Searches the given squares and returns true if none of them have been revealed
	 * @param grid the current grid of the opposing player
	 * @param neighbours the list of neighbours to be searched
	 * @return true if any given neighbour has been revealed
	 */
	private boolean neighboursAreUnrevealed(char[][] grid, ArrayList<String> neighbours) {
		for(String neighbour: neighbours) {
			String[] neighbourLocations = neighbour.split("/");
			int neighbourRow = Integer.parseInt(neighbourLocations[0]);
			int neighbourColumn = Integer.parseInt(neighbourLocations[1]);
			if(grid[neighbourRow - 1][neighbourColumn -1] != '~') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Generates a list of neighbour locations to a given location
	 * @param grid the current grid of the opposing player
	 * @param row the row of the location which neighbours are being generated
	 * @param column the column of the location which neighbours are being generated
	 * @param distance the distance away from the original location at which neighbours will be searched for
	 * @return a list of neighbours of the given location
	 */
	private ArrayList<String> getNeighbours(char[][] grid, int row, int column, int distance) {
		ArrayList<String> neighbours = new ArrayList<String>();
		//check if there is a neighbour above
		if(row - distance > 0) {
			neighbours.add((row - distance) + "/" + column);
		}
		//check if there is a neighbour to the right
		if(column + distance <= columns) {
			neighbours.add(row + "/" + (column + distance));
		}
		//check if there is a neighbour below
		if(row + distance <= rows) {
			neighbours.add((row + distance) + "/" + column);
		}
		//check if there is a neighbour to the left
		if(column - distance > 0) {
			neighbours.add(row + "/" + (column - distance));
		}
		//check if there is a neighbour to the top left (diagonally)
		
		if(row - distance > 0 && column - distance > 0) {
			neighbours.add((row - distance) + "/" + (column - distance));
		}
		//check if there is a neighbour to the top right (diagonally)
		if(row - distance > 0 && column + distance <= columns) {
			neighbours.add((row - distance) + "/" + (column + distance));
		}
		//check if there is a neighbour to the bottom right (diagonally)
		if(row + distance <= rows && column + distance <= columns) {
			neighbours.add((row + distance) + "/" + (column + distance));
		}
		//check if there is a neighbour to the bottom left (diagonally)
		if(column - distance > 0 && row + distance <= rows) {
			neighbours.add((row + distance) + "/" + (column - distance));
		}
		//add neighbours between diagonal and horizontal/vertical neighbours if distance is greater than 1;
		for(int i = 1; i < distance; i++) {
			if(row - distance > 0 && column - (distance - i) > 0) {
				neighbours.add((row - distance) + "/" + (column - (distance - i)));
			}
			if(row - distance > 0 && column + (distance - i) <= columns) {
				neighbours.add((row - distance) + "/" + (column + (distance - i)));
			}
			
			if(row + distance <= rows && column - (distance - i) > 0) {
				neighbours.add((row + distance) + "/" + (column - (distance - i)));
			}
			if(row + distance <= rows && column + (distance - i) <= columns) {
				neighbours.add((row + distance) + "/" + (column + (distance - i)));
			}
			
			if(row - (distance - i) > 0 && column + distance <= columns ) {
				neighbours.add((row - (distance - i)) + "/" + (column + distance));
			}
			if(row + (distance - i) <= rows && column + distance <= columns) {
				neighbours.add((row + (distance - i)) + "/" + (column + distance));
			}
			
			if(row - (distance - i) > 0 && column - distance > 0 ) {
				neighbours.add((row - (distance - i)) + "/" + (column - distance));
			}
			if(row + (distance - i) <= rows && column - distance > 0) {
				neighbours.add((row + (distance - i)) + "/" + (column - distance));
			}
			
		}
		
		return neighbours;
	}
}
