package AI;

import game.Ship;

/**
 * Strategy holds a reference to three playing approaches and decided when should each be used
 * @author Tomáš Romancov
 *
 */
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
	public final static int PROBABILITYSHOOTING = 2;
	public final static int DISTANCESHOOTING = 3;
	public final static int PARITYSHOOTING = 4;
	public final static int CLOCKWISETARGETING = 1;
	public final static int PROBABILITYTARGETING = 2;
	
	private String placementString;
	private String shootingString;
	private String targetingString;
	
	/**
	 * Strategy constructor
	 * @param columns - number of columns in the playing grid
	 * @param rows - number of rows in the playing grid
	 * @param placement - numeric value representing a placement strategy
	 * @param shooting - numeric value representing a shooting strategy
	 * @param targeting - numeric value representing a targeting strategy
	 */
	public Strategy(int rows, int columns, int placement, int shooting, int targeting, Ship[] ships) {
		this.opponentsShips = ships;
		switch(placement) {
			case 1:
				this.placementApproach =  new RandomPlacement(rows, columns, false);
				this.placementString = "random placement";
				break;
			case 2:
				this.placementApproach =  new EdgePlacement(rows, columns, false);
				this.placementString = "edge placement";
				break;
			case 3:
				this.placementApproach =  new RandomPlacement(rows, columns, true);
				this.placementString = "random placement with spacing";
				break;
			case 4:
				this.placementApproach =  new EdgePlacement(rows, columns, true);
				this.placementString = "edge placement with spacing";
				break;
			case 5:
				this.placementApproach =  new ClusterPlacement(rows, columns);
				this.placementString = "cluster placement";
				break;
			default:
				System.out.println("Error: Invalid placement approach.");
		}
		
		switch(shooting) {
			case 1:
				this.shootingApproach =  new RandomShooting(rows, columns);
				this.shootingString = "random shooting";
				break;
			case 2:
				this.shootingApproach =  new ProbabilityShooting(rows, columns, opponentsShips);
				this.shootingString = "probability shooting";
				break;
			case 3:
				this.shootingApproach =  new DistanceShooting(rows, columns);
				this.shootingString = "distance shooting";
				break;
			case 4:
				this.shootingApproach =  new ParityShooting(rows, columns, opponentsShips);
				this.shootingString = "parity shooting";
				break;
			default:
				System.out.println("Error: Invalid shooting approach.");
		}
		
		switch(targeting) {
			case 1:
				this.targetingApproach =  new ClockwiseTargeting(rows, columns, this);
				this.targetingString = "clockwise targeting";
				break;
			case 2:
				this.targetingApproach =  new ProbabilityTargeting(rows, columns, ships);
				this.targetingString = "probability targeting";
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
	
	public String place(Ship ship, char[][] grid) throws Exception {
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

	public String getPlacementString() {
		return placementString;
	}

	public void setPlacementString(String placementString) {
		this.placementString = placementString;
	}

	public String getShootingString() {
		return shootingString;
	}

	public void setShootingString(String shootingString) {
		this.shootingString = shootingString;
	}

	public String getTargetingString() {
		return targetingString;
	}

	public void setTargetingString(String targetingString) {
		this.targetingString = targetingString;
	}
	
}
