package strategy;

import game.Ship;

/**
 * This class is used for testing and debugging different approaches and strategies
 * @author Tomáš Romancov
 *
 */
public class ApproachTester {

	public static void main(String[] args) throws Exception {
		System.out.println("Hello");
		
		char[][] grid = {{'0', '~', '0', '~', '0', '~'},
							{'~', '0', '~', '0', '~', '0'},
							{'0', '~', '0', '~', '0', '~'},
							{'~', '0', '~', '0', '~', '0'},
							{'0', '~', '~', '~', '0', '~'},
							{'~', '0', '~', '0', '~', '0'}};
		
		char[][] grid2 = {{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'}};
		
		ParityShooting ps2 = new ParityShooting(6, 6, new Ship[] {new Ship(2), new Ship(3)});
		System.out.println(ps2.play(grid));
		
//		PropabilityTargeting pt = new PropabilityTargeting(6, 6, new Ship[] {new Ship(4), new Ship(3)});
//		System.out.println(pt.play(grid, "Miss"));
		
//		DistanceShooting ds = new DistanceShooting(6, 6);
//		System.out.println(ds.play(grid));
		
//		PropabilityShooting ps = new PropabilityShooting(6, 6, new Ship[] {new Ship(2), new Ship(3)});
//		System.out.println(ps.play(grid2));
		
//		RandomPlacement rp = new RandomPlacement(6,6);
//		rp.play(new Ship(2), grid);
//
//		RandomShooting rs = new RandomShooting(6,6);
//		for(int i = 0; i < 36; i++) {
//			String coordinates = rs.play(grid);
//			//System.out.println("Play " + i + ": " + coordinates[0] +","+ coordinates[1]);
//		}
		
		
		
	}

}
