package AI;

public class RandomShooting extends Approach{

	public RandomShooting(int columns, int rows) {
		super(columns, rows);
		
	}

	public String play(char[][] grid) {
		int randomLocation = (int)(Math.random() * validSquares.size());
		Integer [] coordinates = validSquares.get(randomLocation);
		validSquares.remove(randomLocation);
		
		return (coordinates[0].intValue() + "/" + coordinates[1].intValue());
	}
	
}
