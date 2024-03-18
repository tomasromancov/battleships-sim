package game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import AI.*;

public class Main {
	
	static final int WIDTH = 13;
	static final int HEIGHT = 6;
	static final int waitInterval = 0;

	static char[][] grid1 = new char[HEIGHT][WIDTH];
	static char[][] grid2 = new char[HEIGHT][WIDTH];
	
	static char[][] grid1hidden = new char[HEIGHT][WIDTH];
	static char[][] grid2hidden = new char[HEIGHT][WIDTH];
	
	static Ship [] ships1;
	static Ship [] ships2;
	static int[] shipLengths = {2, 3, 4};
	
	static char[][][] grids = {grid1, grid2, grid1hidden, grid2hidden};
	
	static boolean gameLoop = true;
	static boolean setup = true;
	static boolean gameEnd = false;
	static int rounds = 1;
	
	static Scanner scanner = new Scanner(System.in);
	
	static String input;
	static String[] inputs;
	static int row;
	static int column;
	static char orientation;
	static int gameMode = 0;
	static String lastEventPlayer1 = "Miss";
	static String lastEventPlayer2 = "Miss";
	static String lastLocationPlayer1 = "0";
	static String lastLocationPlayer2 = "0";
	static int turnCounter = 0;
	
	static int player1wins = 0;
	static int player2wins = 0;
	static int ties = 0;
	static boolean player1won = false;
	static boolean player2won = false;
	static Strategy s1;
	static Strategy s2;
	
	static File resultsFile;
	static FileWriter resultWriter;
	
	public static void main(String[] args) throws Exception {
		setupRound();
		setupResultsFile();
		
		//Game setup
		boolean gameSetup = true;
		while(gameSetup) {
			System.out.print("Select game mode:\nm - manual\nc - computer\nt - test\n");
			input = scanner.next();
			if(input.equals("m")) {
				gameMode = 0;
				gameSetup = false;
			}else if(input.equals("c")) {
				gameMode = 1;
				gameSetup = false;
			}else if(input.equals("t")) {
				gameMode = 2;
				gameSetup = false;
			}else {
				System.out.println(input + " is not a valid option, please try again.");
			}
		}
		
		//setup number of rounds
		boolean roundSetup = true;
		while(roundSetup) {
			System.out.print("How many rounds should be played: ");
			input = scanner.next();
			try{
				rounds = Integer.parseInt(input);
				roundSetup = false;
			}catch(NumberFormatException e) {
				System.out.println("Error: You must enter a number");
			}catch(Exception e){
				System.out.println("Error: " + e);
			}
		}
		
		
		//GAME LOOP
		//Play for each round
		for(int r = 0; r < rounds; r++) {
			//If test mode skip setup
			if(gameMode != 2) {
				while(setup) {
					//setup player 1
					for(int i = 0; i < ships1.length; i++) {
						System.out.println("------------------Player1's Turn------------------");
						System.out.println(getGrid(grid1));
						if(gameMode == 0) {
							do {
								System.out.print("Player1\nPlace a ship (length " + ships1[i].getLength() + ") on the grid in format row/column/orientation:");
								input = scanner.next();
							}while(!validatePlacementInput(input));
						}else{
							do {
								System.out.print("Player1\nPlace a ship (length " + ships1[i].getLength() + ") on the grid in format row/column/orientation:");
								input = s1.place(ships1[i], grid1);
							}while(!validatePlacementInput(input));
							
						}
						
						inputs = input.split("/");
						row = Integer.parseInt(inputs[0]);
						column = Integer.parseInt(inputs[1]);
						orientation = inputs[2].charAt(0);
						System.out.println("Computer placed ship at " + row + "/" + column + " orientation: " + orientation);
						
						ships1[i].setOrientation(orientation);
						ships1[i].setLocation(row, column);
						
						//Places the ship on the playing grid
						if(orientation == 'v') {
							for(int x = 0; x < ships1[i].getLength(); x++) {
								grid1[(row-1) + x][column-1] = 's';
							}
						}else if(orientation == 'h') {
							for(int x = 0; x < ships1[i].getLength(); x++) {
								grid1[(row-1)][column-1 + x] = 's';
							}
						}
						
						if(i+1 == ships1.length) {
							System.out.println("Final preview of player1's grid");
							System.out.println(getGrid(grid1));
						}
					}
					
					//setup player 2
					for(int i = 0; i < ships2.length; i++) {
						System.out.println("------------------Player2's Turn------------------");
						System.out.println(getGrid(grid2));
						if(gameMode == 0) {
							do {
								System.out.print("Player2\nPlace a ship (length " + ships2[i].getLength() + ") on the grid in format row/column/orientation:");
								input = scanner.next();
							}while(!validatePlacementInput(input));
						}else{
							do {
								System.out.print("Player2\nPlace a ship (length " + ships2[i].getLength() + ") on the grid in format row/column/orientation:");
								input = s2.place(ships2[i], grid2);
							}while(!validatePlacementInput(input));
							System.out.println("Computer placed ship at " + row + "/" + column + " oriention: " + orientation);
						}
						
						inputs = input.split("/");
						row = Integer.parseInt(inputs[0]);
						column = Integer.parseInt(inputs[1]);
						orientation = inputs[2].charAt(0);
						
						ships2[i].setOrientation(orientation);
						ships2[i].setLocation(row, column);
						
						//Places the ship on the playing grid
						if(orientation == 'v') {
							for(int x = 0; x < ships2[i].getLength(); x++) {
								grid2[(row-1) + x][column-1] = 's';
							}
						}else if(orientation == 'h') {
							for(int x = 0; x < ships2[i].getLength(); x++) {
								grid2[(row-1)][column-1 + x] = 's';
							}
						}
						
						if(i+1 == ships2.length) {
							System.out.println("Final preview of player2's grid");
							System.out.println(getGrid(grid2));
						}
					}
					
					setup = false;
				}
			}else { //quick setup
				ships1[0].setOrientation('h');
				ships1[0].setLocation(1, 1);
				ships1[1].setOrientation('v');
				ships1[1].setLocation(4, 4);
				System.out.println(ships1[0]);
				System.out.println(ships1[1]);
				
				grid1 = new char[][]
						{{'s', 's', 's', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', 's', '~', '~'},
						{'~', '~', '~', 's', '~', '~'},
						{'~', '~', '~', '~', '~', '~'}};
				
				ships2[0].setOrientation('h');
				ships2[0].setLocation(3, 1);
				ships2[1].setOrientation('v');
				ships2[1].setLocation(4, 2);
				System.out.println(ships2[0]);
				System.out.println(ships2[1]);
				
				grid2 = new char[][]
						{{'~', '~', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'},
						{'s', 's', 's', '~', '~', '~'},
						{'~', 's', '~', '~', '~', '~'},
						{'~', 's', '~', '~', '~', '~'},
						{'~', '~', '~', '~', '~', '~'}};
			}
			
			while(gameLoop) {
				
				//player1's turn
				System.out.println("------------------Player1's Turn------------------");
				System.out.println(getGrid(grid1));
				System.out.println(getGrid(grid2hidden));
				
				if(gameMode == 0 || gameMode == 2) {
					do {
						System.out.print("Player1\nChoose grid to shoot at in format row/column:");
						input = scanner.next();
					}while(!validateShootinginput(input));
				}else{
					do {
						System.out.print("Player1\nChoose grid to shoot at in format row/column:");
						input = s1.play(grid2hidden, lastEventPlayer1, lastLocationPlayer1);
					}while(!validateShootinginput(input));
				}
				
				lastLocationPlayer1 = input;
				inputs = input.split("/");
				row = Integer.parseInt(inputs[0]);
				column = Integer.parseInt(inputs[1]);
				System.out.println("Computer shot at " + row + "/" + column);
				
				if(grid2[row-1][column-1] == 's') {
					grid2[row-1][column-1] = '*';
					grid2hidden[row-1][column-1] = '*';
					System.out.println("Hit!");
					lastEventPlayer1 = "Hit";
					if(shipUpdate(ships2, new int[] {row, column}, "player1")) lastEventPlayer1 = "Sunk";
				}else if (grid2[row-1][column-1] == '~'){
					grid2[row-1][column-1] = '0';
					grid2hidden[row-1][column-1] = '0';
					System.out.println("Miss!");
					lastEventPlayer1 = "Miss";
				}else {
					System.out.println("Miss!");
					lastEventPlayer1 = "Miss";
				}
				if(gameMode == 1) {
					try {
						TimeUnit.SECONDS.sleep(waitInterval);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					

				//player2's turn
				System.out.println("------------------Player2's Turn------------------");
				System.out.println(getGrid(grid2));
				System.out.println(getGrid(grid1hidden));
				if(gameMode == 0 || gameMode == 2) {
					do {
						System.out.print("Player2\nChoose grid to shoot at in format row/column:");
						input = scanner.next();
					}while(!validateShootinginput(input));
				}else{
					do {
						System.out.print("Player2\nChoose grid to shoot at in format row/column:");
						input = s2.play(grid1hidden, lastEventPlayer2, lastLocationPlayer2);
					}while(!validateShootinginput(input));
				}
				
				lastLocationPlayer2 = input;
				inputs = input.split("/");
				row = Integer.parseInt(inputs[0]);
				column = Integer.parseInt(inputs[1]);
				System.out.println("Computer shot at " + row + "/" + column);
				
				
				if(grid1[row-1][column-1] == 's') {
					grid1[row-1][column-1] = '*';
					grid1hidden[row-1][column-1] = '*';
					System.out.println("Hit!");
					lastEventPlayer2 = "Hit";
					if(shipUpdate(ships1, new int[] {row, column}, "player2")) lastEventPlayer2 = "Sunk";
					
				}else if (grid1[row-1][column-1] == '~'){
					grid1[row-1][column-1] = '0';
					grid1hidden[row-1][column-1] = '0';
					System.out.println("Miss!");
					lastEventPlayer2 = "Miss";
				}else {
					System.out.println("Miss!");
					lastEventPlayer2 = "Miss";
				}
				if(gameMode == 1) {
					try {
						TimeUnit.SECONDS.sleep(waitInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				turnCounter++;
				//Check if anyone has won the game
				checkVictory("player1", ships2);
				checkVictory("player2", ships1);
				if(player1won || player2won) {
					if(player1won && player2won) {
						ties += 1;
						resultWriter.write("\ntie,tie");
						System.out.println("Its a tie!");
					}else if(player1won) {
						player1wins += 1;
						resultWriter.write("\n" + s1.getPlacementString() + '/' + s1.getShootingString() + '/' + s1.getTargetingString() +
								',' + s2.getPlacementString() + '/' + s2.getShootingString() + '/' + s2.getTargetingString());
						System.out.println("Player1 won!!!");
					}else if(player2won) {
						player2wins += 1;
						resultWriter.write("\n" + s2.getPlacementString() + '/' + s2.getShootingString() + '/' + s2.getTargetingString() +
								',' + s1.getPlacementString() + '/' + s1.getShootingString() + '/' + s1.getTargetingString());
						System.out.println("Player2 won!!!");
					}
					String shipsString = "";
					for(int i = 0; i < shipLengths.length; i++) {
						if(i > 0)shipsString += ";";
						shipsString += shipLengths[i];
					}
					resultWriter.write("," + turnCounter + "," + WIDTH + ',' + HEIGHT + ',' + shipsString);
					
				}
				
				
			}
			//Setup new round
			setupRound();
			
		}
		//end the game
		System.out.println("Final score\nPlayer1 : " + player1wins + "\nPlayer2 : " + player2wins + "\nTies : " + ties);
		scanner.close();
		resultWriter.close();
	}
	
	private static boolean validateShootinginput(String shootingInput) {
		String[] inputArray = shootingInput.split("/");
		if(inputArray.length != 2) {
			System.out.println("ERROR: Incorrect input format (correct format: row/column). Your input: " + shootingInput);
			return false;
		}
		try {
			int row = Integer.parseInt(inputArray[0]);
			int column = Integer.parseInt(inputArray[1]);
			if(row < 0 || row > HEIGHT) {
				System.out.println("ERROR: row " + row + " does not exist. Your input: " + shootingInput);
				return false;
			}
				
			if(column < 0 || column > WIDTH) {
				System.out.println("ERROR: column " + column + " does not exist. Your input: " + shootingInput);
				return false;
			}
		}catch(Exception e) {
			System.out.println("ERROR: Incorrect input format, row and column must be integers (correct format: row/column). Your input: " + shootingInput);
			return false;
		}
		return true;
	}

	public static boolean validatePlacementInput(String placementInput){
		String[] inputArray = placementInput.split("/");
		if(inputArray.length != 3) {
			System.out.println("ERROR: Incorrect input format (correct format: row/column/orientation). Your input: " + placementInput);
			return false;
		}
		try {
			int row = Integer.parseInt(inputArray[0]);
			int column = Integer.parseInt(inputArray[1]);
			if(row < 0 || row > HEIGHT) {
				System.out.println("ERROR: row " + row + " does not exist. Your input: " + placementInput);
				return false;
			}
				
			if(column < 0 || column > WIDTH) {
				System.out.println("ERROR: column " + column + " does not exist. Your input: " + placementInput);
				return false;
			}
		}catch(Exception e) {
			System.out.println("ERROR: Incorrect input format, row and column must be integers (correct format: row/column/orientation). Your input: " + placementInput);
			return false;
		}
		if(inputArray[2].length() != 1) {
			System.out.println("ERROR: Orientation must be 1 letter (h or v). Your input: " + placementInput);
			return false;
		}
		if(inputArray[2].charAt(0) != 'h' && inputArray[2].charAt(0) != 'v') {
			System.out.println("ERROR: Orientation must be 'h' or 'v' only. Your input: " + placementInput);
			return false;
		}
		return true;
	}
	
	/**
	 * Updates the status of a ship in a given location and reports if it has been sunk.
	 * @param ships List of ships belonging to the player that was hit.
	 * @param location 2 integers representing the row and column on the grid where a ship has been hit
	 * @return true if ship was sunk
	 */
	private static boolean shipUpdate(Ship[] ships, int[] location, String shooter) {
		//find ship in the location
		boolean shipSunk = true;
		boolean squareFound = false;
		System.out.println("Updating Ship");
		for(Ship ship: ships) {
			for(int i = 0; i < ship.getLength(); i++) {
				System.out.println("Comparing row: " + location[0] + ";" + ship.getOccupiedSquares()[i][0] + 
						"\nComparing column: " + location[1] + ";" + ship.getOccupiedSquares()[i][1]);
				if(location[0] == ship.getOccupiedSquares()[i][0] && location[1] == ship.getOccupiedSquares()[i][1]) {
					squareFound = true;
					System.out.println("Ship location found");
					//marks the occupied square as hit
					ship.getOccupiedSquares()[i][2] = 1;
					//checks if ship has been sunk
					for(int[] square: ship.getOccupiedSquares()) {
						if(square[2] == 0) {
							shipSunk = false;
							break;
						}
					}
					if(shipSunk) {
						ship.setSunk(true);
						System.out.println("A ship was sunk!");
						//mark ship as sunk on the grids
						if(shooter.equals("player1")) {
							for(int[] segment: ship.getOccupiedSquares()) {
								grid2[segment[0]-1][segment[1]-1] = '$';
								grid2hidden[segment[0]-1][segment[1]-1] = '$';
							}
						}else if(shooter.equals("player2")) {
							for(int[] segment: ship.getOccupiedSquares()) {
								grid1[segment[0]-1][segment[1]-1] = '$';
								grid1hidden[segment[0]-1][segment[1]-1] = '$';
							}
						}
						return true;
					}
					break;
				}
				if(squareFound) break;
			}
			if(squareFound) break;
			
		}
		return false;
		
	}

	/**
	 * Checks if player has won the game by seeing if any of the opponents ships remain unsunk.
	 * @param player The player who can be the winner.
	 * @param ships The opponents list of ships
	 */
	private static void checkVictory(String player, Ship[] ships) {
		boolean end = true;
		
		//checks if all ships have been sunk
		for(Ship ship: ships) {
			if(!ship.isSunk()) {
				end = false;
				break;
			}
		}
		
		//announces the winner and ends the game
		if(end) {
			gameLoop = false;
			if(player.equals("player1")) {
				player1won = true;
			}else if(player.equals("player2")) {
				player2won = true;
			}
		}
		
	}

	/**
	 * Initiates game grids by filling them with the '~' character representing an empty space.
	 */
	private static void fillGrids() {
		for(int x = 0; x < grids.length; x++) {
			for(int y = 0; y < grids[x].length; y++) {
				for(int z = 0; z < grids[x][y].length; z++) {
					grids[x][y][z] = '~';
				}
			}
		}
		
	}

	/**
	 * Returns a String representation of a grid.
	 * @param selectedGrid The grid to be turned to a String.
	 * @return Grid in a String format.
	 */
	private static String getGrid(char[][] selectedGrid) {
		String grid = "";
		int rowNum = 1;
		
		if(selectedGrid == grid1 || selectedGrid == grid1hidden) {
			grid = "Player1's grid\n ";
		}else if(selectedGrid == grid2 || selectedGrid == grid2hidden) {
			grid = "Player2's grid\n ";
		}
		
		for(int i = 1; i <= selectedGrid[0].length; i++) {
			grid += " " + i ;
		}
		grid += "\n";
		
		for(char[] row : selectedGrid) {
			grid += rowNum;
			for(char field : row) {
				grid += " " + field;
			}
			grid += "\n";
			rowNum++;
		}
		return grid;
	}
	
	private static void setupRound() {
		fillGrids();
		ships1 = new Ship[shipLengths.length];
		ships2 = new Ship[shipLengths.length];
		for(int i = 0; i < shipLengths.length; i++) {
			ships1[i] = new Ship(shipLengths[i]);
			ships2[i] = new Ship(shipLengths[i]);
		}
		gameEnd = false;
		gameLoop = true;
		setup = true;
		player1won = false;
		player2won = false;
		lastEventPlayer1 = "Miss";
		lastEventPlayer2 = "Miss";
		lastLocationPlayer1 = "0";
		lastLocationPlayer2 = "0";
		turnCounter = 0;
		
		s1 = new Strategy(HEIGHT, WIDTH, 5, Strategy.PARITYSHOOTING, 1, ships2);
		s2 = new Strategy(HEIGHT, WIDTH, 1, Strategy.PARITYSHOOTING, 1, ships1);
		s1.reset();
		s2.reset();
	}
	
	private static void setupResultsFile() {
		try {
		      File results = new File("results.csv");
		      if (results.createNewFile()) {
		    	FileWriter headWriter = new FileWriter(results.getName());
		    	headWriter.write("winner,looser,total turns,width,height,ships");
		    	headWriter.close();
		        System.out.println("File created: " + results.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		      resultsFile = results;
		      try {
		          FileWriter writer = new FileWriter(results.getName(), true);
		          resultWriter = writer;
		       } catch (IOException e) {
		          System.out.println("ERROR: an error accoured while opening results file: " + e);
		          e.printStackTrace();
		       }
		    } catch (IOException e) {
		      System.out.println("ERROR: an error accoured while opening results file: " + e);
		      e.printStackTrace();
		    }
	}

}
