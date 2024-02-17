package AI;

import java.util.ArrayList;

public class TargetingApproach extends Approach{
	boolean active = false;
	
	protected boolean targeting = true;
	protected boolean figuringOrientation = true;
	protected ArrayList<String> searchedLocations = new ArrayList<String>();
	protected char orientation = '0'; //0 for unknown, h for horizontal, v for vertical
	protected char direction = '0'; //0 for unknown, u for up, d for down, l for left, r for right
	
	protected int lastRow = -1;
	protected int lastColumn = -1;
	protected int firstRow;
	protected int firstColumn;

	public TargetingApproach(int columns, int rows) {
		super(columns, rows);
		// TODO Auto-generated constructor stub
	}

	public String play(char[][] grid, String lastLocation) {
		return "";
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void reset() {
		targeting = true;
		figuringOrientation = true;
		searchedLocations = new ArrayList<String>();
		orientation = '0'; //0 for unknown, h for horizontal, v for vertical
		direction = '0'; //0 for unknown, u for up, d for down, l for left, r for right
		
		lastRow = -1;
		lastColumn = -1;
		firstRow = -1;
		firstColumn = -1;
	}
}
