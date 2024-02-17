package AI;

import game.Ship;

public class Strategy {

	private PlacementApproach placementApproach;
	private Approach shootingApproach;
	private TargetingApproach targetingApproach;
	
	//public int values that represent each strategy
	public final static int RANDOMPLACEMENT = 1;
	public final static int RANDOMSHOOTING = 1;
	public final static int CLOCKWISETARGETING = 1;
	
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
				System.out.println("Error: Invalid placement approach.");
		}
		
		switch(shooting) {
			case 1:
				this.shootingApproach =  new RandomShooting(columns, rows);
				break;
			default:
				System.out.println("Error: Invalid shooting approach.");
		}
		
		switch(targeting) {
			case 1:
				this.targetingApproach =  new ClockwiseTargeting(columns, rows);
				break;
			default:
				System.out.println("Error: Invalid targeting approach.");
		}
	}
	
	public String play(char[][] grid, String lastEvent, String lastLocation) {
		switch(lastEvent) {
			case "Miss":
				if(this.targetingApproach.isActive()) {
					System.out.println("Missed but still searching for ship");
					return(this.targetingApproach.play(grid, lastLocation));
				}
				else {
					System.out.println("Missed and not searching");
					return(this.shootingApproach.play(grid));
				}
			case "Hit":
				System.out.println("Hit returning targeting value");
				this.targetingApproach.setActive(true);
				return(this.targetingApproach.play(grid, lastLocation));
			case "Sunk":
				System.out.println("Stopping search");
				this.targetingApproach.setActive(false);
				this.targetingApproach.reset();
				return(this.shootingApproach.play(grid));
			default:
				System.out.println("Error: Unknown move passed to strategy.");
				
		}
		return "";
	}
	
	public String place(Ship ship, char[][] grid) {
		return(this.placementApproach.play(ship, grid));
	}
}
