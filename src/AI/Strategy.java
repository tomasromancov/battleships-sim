package AI;

import game.Ship;

public class Strategy {

	private PlacementApproach placementApproach;
	private Approach shootingApproach;
	private TargetingApproach targetingApproach;
	
	private Ship[] opponentsShips;
	
	//public int values that represent each strategy
	public final static int RANDOMPLACEMENT = 1;
	public final static int EDGEPLACEMENT = 2;
	public final static int RANDOMPLACEMENTWITHSPACING = 3;
	public final static int EDGEPLACEMENTWITHSPACING = 4;
	public final static int CLUSTERPLACEMENT = 5;
	public final static int RANDOMSHOOTING = 1;
	public final static int PROPABILITYSHOOTING = 2;
	public final static int DISTANCESHOOTING = 3;
	public final static int CLOCKWISETARGETING = 1;
	
	/**
	 * 
	 * @param columns - number of columns in the playing grid
	 * @param rows - number of rows in the playing grid
	 * @param placement - numeric value representing a placement strategy
	 * @param shooting - numeric value representing a shooting strategy
	 * @param targeting - numeric value representing a targeting strategy
	 */
	public Strategy(int columns, int rows, int placement, int shooting, int targeting, Ship[] ships) {
		this.opponentsShips = ships;
		switch(placement) {
			case 1:
				this.placementApproach =  new RandomPlacement(columns, rows, false);
				break;
			case 2:
				this.placementApproach =  new EdgePlacement(columns, rows, false);
				break;
			case 3:
				this.placementApproach =  new RandomPlacement(columns, rows, true);
				break;
			case 4:
				this.placementApproach =  new EdgePlacement(columns, rows, true);
				break;
			case 5:
				this.placementApproach =  new ClusterPlacement(columns, rows);
				break;
			default:
				System.out.println("Error: Invalid placement approach.");
		}
		
		switch(shooting) {
			case 1:
				this.shootingApproach =  new RandomShooting(columns, rows);
				break;
			case 2:
				this.shootingApproach =  new PropabilityShooting(columns, rows, opponentsShips);
				break;
			case 3:
				this.shootingApproach =  new DistanceShooting(columns, rows);
				break;
			default:
				System.out.println("Error: Invalid shooting approach.");
		}
		
		switch(targeting) {
			case 1:
				this.targetingApproach =  new ClockwiseTargeting(columns, rows, this);
				break;
			default:
				System.out.println("Error: Invalid targeting approach.");
		}
	}
	
	public String play(char[][] grid, String lastEvent, String lastLocation) throws Exception {
		switch(lastEvent) {
			case "Miss":
				if(targetingApproach.isActive()) {
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
				if(unsunkShips(grid).equals("none")) {
					System.out.println("Stopping search");
					this.targetingApproach.setActive(false);
					this.targetingApproach.reset();
					return(this.shootingApproach.play(grid));
				}else {
					System.out.println("Ship sunk but still searching for different ship");
					this.targetingApproach.reset();
					this.targetingApproach.setActive(true);
					return(this.targetingApproach.play(grid, unsunkShips(grid)));
				}
			default:
				System.out.println("Error: Unknown move passed to strategy.");
				
		}
		return "";
	}
	
	public String place(Ship ship, char[][] grid) {
		return(this.placementApproach.play(ship, grid));
	}
	
	public void reset() {
		this.targetingApproach.setActive(false);
		this.targetingApproach.reset();
	}
	
	public String unsunkShips(char[][] grid) {
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				if(grid[row][column] == '*') return (row + 1) + "/" + (column + 1);
			}
		}
		return "none";
	}
}
