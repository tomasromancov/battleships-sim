package AI;

import game.Ship;

public class Strategy {

	private Approach placementApproach;
	private Approach shootingApproach;
	private Approach targetingApproach;
	
	/**
	 * 
	 * @param columns - number of columns in the playing grid
	 * @param rows - number of rows in the playing grid
	 * @param placement - numeric value representing a placement strategy
	 * @param shooting - numeric value representing a shooting strategy
	 * @param targeting - numeric value representing a targeting strategy
	 */
	public Strategy(int columns, int rows, int placement, int shooting, int targeting) {
		switch(placement) {
			case 1:
				this.placementApproach =  new RandomPlacement(columns, rows);
				break;
			default:
				System.out.println("invalid placement approach");
		}
		
		switch(shooting) {
			case 1:
				this.shootingApproach =  new RandomShooting(columns, rows);
				break;
			default:
				System.out.println("invalid shooting approach");
		}
	}
	
	public int[] play(int[] grid, String lastMove) {
		
		return new int[2];
	}
}
