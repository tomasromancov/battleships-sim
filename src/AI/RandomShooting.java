package AI;

import java.util.ArrayList;

public class RandomShooting extends Approach{

	public RandomShooting(int columns, int rows) {
		super(columns, rows);
		
	}

	public String play(char[][] grid) {
		//read the grid to find locations that are valid to shoot at
		ArrayList<String> validSquares = getValidSquares(grid);
		
		int randomLocation = (int)(Math.random() * validSquares.size());
		String coordinates = validSquares.get(randomLocation);
		validSquares.remove(randomLocation);
		
		return coordinates;
	}
	
}
