package AI;

/**
 * Clockwise Targeting is a targeting approach where when a ship segment is found the algorithm searches around the revealed spot
 * in a clockwise manner to establish the orientation of the ship. Once the orientation is found the algorithm pursues the direction to
 * sink the ship completely.
 * @author Tomáš Romancov
 *
 */
public class ClockwiseTargeting extends TargetingApproach{
	protected Strategy strategy; //reference to the strategy object of which this approach is a part
	
	/**
	 * ClockwiseTargeting constructor
	 * @param rows the number of rows on the playing grid
	 * @param columns the number of columns on the playing grid
	 * @param strategy the strategy to which this approach belongs
	 */
	public ClockwiseTargeting(int rows, int columns, Strategy strategy) {
		super(rows, columns);
		this.strategy = strategy;
	}
	
	/**
	 * Decides which location the approach should shoot at next
	 * @param grid the current grid of the opposing player
	 * @param lastLocation the location which the player last shot at
	 * @return chosen location where the approach will make a play in the format row/column
	 */
	public String play(char[][] grid, String lastLocation) throws Exception {
		//System.out.println(toString());
		String[] locations = lastLocation.split("/");
		lastRow = Integer.parseInt(locations[0]);
		lastColumn = Integer.parseInt(locations[1]);
		
		confirmOrientation(grid, lastLocation);
		
		if(orientation == '0') return lookForOrientation(grid);
		
		switchDirection(grid);
		
		//System.out.println(toString());
		return pursueDirection(lastLocation);
		
	}
	
	/**
	 * Shoots at the next location from the last location in a specific direction
	 * @param lastLocation the last location where a shot was made
	 * @return chosen location where the approach will make a play in the format row/column
	 */
	private String pursueDirection(String lastLocation) {
		//declares row and column variables of the location where the next shot will be made
		//stores -1 values as those are invalid since no decision has been made yet on where the next shot will be
		int row = -1;
		int column = -1;
		
		//pursue direction
		switch(direction) {
			case 'u':
				//System.out.println("Trying to shoot above last location: " + lastLocation);
				row = lastRow - 1;
				column = lastColumn;
				break;
			case 'd':
				//System.out.println("Trying to shoot below last location: " + lastLocation);
				row = lastRow + 1;
				column = lastColumn;
				break;
			case 'r':
				//System.out.println("Trying to shoot right to the last location: " + lastLocation);
				row = lastRow;
				column = lastColumn + 1;
				break;
			case 'l':
				//System.out.println("Trying to shoot left to the last location: " + lastLocation);
				row = lastRow;
				column = lastColumn - 1;
				break;
		}
		
		return row + "/" + column;
	}
	
	/**
	 * Changes the current direction if it cannot be followed any longer (location in it is invalid or already revealed)
	 * @param grid the current grid of the opposing player
	 * @throws Exception
	 */
	private void switchDirection(char[][] grid) throws Exception {
		//switch direction if last shot missed or if the end of a grid has been reached
		switch(direction) {
			case 'u':
				if(grid[lastRow - 1][lastColumn - 1] == '0' || lastRow - 1 <= 0) {
					direction = 'd';
					if(firstRow == rows) {
						if(firstColumn != columns) direction = 'r';
						else direction = 'l';
//								this.reset();
//								this.setActive(false);
//								return(strategy.play(grid, lastLocation, "Miss"));
					}else if(grid[firstRow][firstColumn - 1] != '~') {
						if(firstColumn != columns) direction = 'r';
						else direction = 'l';
//								this.reset();
//								this.setActive(false);
//								return(strategy.play(grid, lastLocation, "Miss"));
					}
					//System.out.println("Row was changed from: " + lastRow + " to " + firstRow);
					//System.out.println("Column was changed from: " + lastColumn + " to " + firstColumn);
					lastRow = firstRow;
					lastColumn = firstColumn;
					//System.out.println("Switching direction from up to down.");
				}
				break;
			case 'd':
				if(grid[lastRow - 1][lastColumn - 1] == '0' || lastRow + 1 > rows) {
					direction = 'u';
					if(firstRow == 1) {
						if(firstColumn != columns) direction = 'r';
						else direction = 'l';
//								this.reset();
//								this.setActive(false);
//								return(strategy.play(grid, lastLocation, "Miss"));
					}else if(grid[firstRow - 2][firstColumn - 1] != '~') {
						if(firstColumn != columns) direction = 'r';
						else direction = 'l';
//								this.reset();
//								this.setActive(false);
//								return(strategy.play(grid, lastLocation, "Miss"));
					}
					//System.out.println("Row was changed from: " + lastRow + " to " + firstRow);
					//System.out.println("Column was changed from: " + lastColumn + " to " + firstColumn);
					lastRow = firstRow;
					lastColumn = firstColumn;
					//System.out.println("Switching direction from down to up.");
				}
				break;
			case 'r':
				if(grid[lastRow - 1][lastColumn - 1] == '0' || lastColumn + 1 > columns) {
					direction = 'l';
					//if its not possible to go left or right change direction
					if(firstColumn == 1) {
						if(firstRow != rows) direction = 'd';
						else direction = 'u';
//								this.reset();
//								this.setActive(false);
//								return(strategy.play(grid, lastLocation, "Miss"));
					}else if(grid[firstRow - 1][firstColumn - 2] != '~') {
						if(firstRow != rows) direction = 'd';
						else direction = 'u';
//								this.reset();
//								this.setActive(false);
//								return(strategy.play(grid, lastLocation, "Miss"));
					}
					//System.out.println("Row was changed from: " + lastRow + " to " + firstRow);
					//System.out.println("Column was changed from: " + lastColumn + " to " + firstColumn);
					lastRow = firstRow;
					lastColumn = firstColumn;
					//System.out.println("Switching direction from right to left.");
				}
				break;
			case 'l':
				if(grid[lastRow - 1][lastColumn - 1] == '0' || lastColumn - 1 <= 0) {
					direction = 'r';
					if(firstColumn == columns) {
						if(firstRow != rows) direction = 'd';
						else direction = 'u';
//								this.reset();
//								this.setActive(false);
//								return(strategy.play(grid, lastLocation, "Miss"));
					}else if(grid[firstRow - 1][firstColumn] != '~') {
						if(firstRow != rows) direction = 'd';
						else direction = 'u';
//								this.reset();
//								this.setActive(false);
//								return(strategy.play(grid, lastLocation, "Miss"));
					}
					//System.out.println("Row was changed from: " + lastRow + " to " + firstRow);
					//System.out.println("Column was changed from: " + lastColumn + " to " + firstColumn);
					lastRow = firstRow;
					lastColumn = firstColumn;
					//System.out.println("Switching direction from left to right.");
				}
				break;
			default:
				throw new Exception("Error: unexpected direction registered in targeting approach.");
		}
		
	}

	/**
	 * Confirms the predicted orientation of a ship from the last move
	 * @param grid the current grid of the opposing player
	 * @param lastLocation the last location where a shot was made
	 */
	private void confirmOrientation(char[][] grid, String lastLocation) {
		//checks if last location was a hit or not to determine the ships orientation
		if(!searchedLocations.isEmpty()) {
			if(figuringOrientation) {
				if(grid[lastRow - 1][lastColumn - 1] != '*') {
					orientation = '0'; //reset orientation to unknown if last shot did not hit else keep it as is
					direction = '0';
				}else {
					searchedLocations.add(lastLocation);
					figuringOrientation = false; //orientation was found
				}
			}
		}else{
			//record the first location
			searchedLocations.add(lastLocation);
			firstRow = lastRow;
			firstColumn = lastColumn;
			//System.out.println("Assigning first location: " + searchedLocations.get(0));
		}
	}
	
	/**
	 * Tries to find the orientation of a ship by shooting around the first revealed location
	 * @param grid the current grid of the opposing player
	 * @return location where the shot will be made
	 * @throws Exception
	 */
	private String lookForOrientation(char[][] grid) throws Exception {
		//declares row and column variables of the location where the next shot will be made
		//stores -1 values as those are invalid since no decision has been made yet on where the next shot will be
		int row = -1;
		int column = -1;
		
		//figure out the ships orientation by connecting two of the ships segments
		//alternate start is a new location from which the ship should be searched for
		String alternateStart = "-1/-1";
		//System.out.println("Unknown orientation");
		//System.out.println("Searching arounds first location: " + searchedLocations.get(0));
		
		//check if shot can be made above the first hit
		if(firstRow - 1 > 0) {
			if(grid[firstRow - 2][firstColumn - 1] == '~') { //if location above has not been revealed yet
				//System.out.println("Trying to shoot above first location: " + searchedLocations.get(0));
				row = firstRow - 1;
				column = firstColumn;
				//if shot was
				orientation = 'v';
				direction = 'u';
				return row + "/" + column;
			}else if(grid[firstRow - 2][firstColumn - 1] == '*') {
				alternateStart = (firstRow - 1) + "/" + firstColumn;
			}
		}
		//check if shot can be made right to the first hit
		if(firstColumn + 1 <= columns) {
			if(grid[firstRow - 1][firstColumn] == '~') { //if location to the right has not been revealed yet
				//System.out.println("Trying to shoot right to the first location: " + searchedLocations.get(0));
				row = firstRow;
				column = lastColumn + 1;
				orientation = 'h';
				direction = 'r';
				return row + "/" + column;
			}else if(grid[firstRow - 1][firstColumn] == '*') {
				alternateStart = firstRow + "/" + (firstColumn + 1);
			}
		}
		//check if shot can be made below the first hit
		if(firstRow + 1 <= rows) {
			if(grid[firstRow][firstColumn - 1] == '~') { //if location below has not been revealed yet
				//System.out.println("Trying to shoot below first location: " + searchedLocations.get(0));
				row = firstRow + 1;
				column = firstColumn;
				orientation = 'v';
				direction = 'd';
				return row + "/" + column;
			}else if(grid[firstRow][firstColumn - 1] == '*') {
				alternateStart = (firstRow + 1) + "/" + firstColumn;
			}
		}
		//check if shot can be made left to the first hit
		if(firstColumn - 1 > 0) {
			if(grid[firstRow - 1][firstColumn - 2] == '~') { //if location to the left has not been revealed yet
				//System.out.println("Trying to shoot left to the first location: " + searchedLocations.get(0));
				row = firstRow;
				column = firstColumn - 1;
				orientation = 'h';
				direction = 'l';
				return row + "/" + column;
			}else if(grid[firstRow - 1][firstColumn - 2] == '*') {
				alternateStart = firstRow + "/" + (firstColumn - 1);
			}
		}
		//System.out.println("No valid squares around " + searchedLocations.get(0) + " were found");
		this.reset();
		return this.play(grid, alternateStart);
					
				
	}

	/**
	 * Custom toString method to print details of the class
	 */
	@Override
	public String toString() {
		return "ClockwiseTargeting [active=" + active + ", figuringOrientation="
				+ figuringOrientation + ", searchedLocations=" + searchedLocations + ", orientation=" + orientation
				+ ", direction=" + direction + ", lastRow=" + lastRow + ", lastColumn=" + lastColumn + ", firstRow="
				+ firstRow + ", firstColumn=" + firstColumn + "]";
	}
	
	
	
	
}
