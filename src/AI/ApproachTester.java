package AI;

import game.Ship;

public class ApproachTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		
		char[][] grid = {{'s', '~', '~', '~', '~', '~'},
							{'0', '~', '~', '~', '~', '~'},
							{'~', '~', '~', '~', 's', '~'},
							{'~', '~', '~', '~', '~', '~'},
							{'~', '~', '~', '~', '~', '~'},
							{'~', '~', '~', '~', '~', '~'}};
		
		char[][] grid2 = {{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'}};
		
		DistanceShooting ds = new DistanceShooting(6, 6);
		System.out.println(ds.play(grid));
		
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
