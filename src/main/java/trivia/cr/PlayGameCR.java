package trivia.cr;

import java.util.Random;
import java.util.Scanner;

import main.java.trivia.Constants;
import main.java.trivia.IGame;

public class PlayGameCR {
	
	  private static final Scanner scanner = new Scanner(System.in);

	   public static void main(String[] args) {

	      System.out.println("*** Welcome to Trivia Game ***\n");
	      System.out.println("Enter number of players: " + Constants.MIN_NUMBER_OF_PLAYERS + "-" + Constants.MAX_NUMBER_OF_PLAYERS);
	      int playerCount = Integer.parseInt(scanner.nextLine());
	      if (playerCount < Constants.MIN_NUMBER_OF_PLAYERS || playerCount > Constants.MAX_NUMBER_OF_PLAYERS) throw new IllegalArgumentException("No player " + Constants.MIN_NUMBER_OF_PLAYERS + ".." + Constants.MAX_NUMBER_OF_PLAYERS);
	      System.out.println("Reading names for " + playerCount + " players:");

	      IGame aGame = new GameBetterCR();

	      for (int i = 1; i <= playerCount; i++) {
	         System.out.print("Player "+i+" name: ");
	         String playerName = scanner.nextLine();
	         if (!aGame.add(playerName))
	        	 i--;
	      }

	      System.out.println("\n\n--Starting game--");


	      boolean notAWinner;
	      do {
	         int roll = readRoll();
	         aGame.roll(roll);

	         System.out.print(">> Was the answer correct? [y/n] ");
	         boolean correct = readYesNo();
	         if (correct) {
	            notAWinner = aGame.wasCorrectlyAnswered();
	         } else {
	            notAWinner = aGame.wrongAnswer();
	         }

	      } while (notAWinner);
	      System.out.println(">> Game won!");
	   }

	   private static boolean readYesNo() {
	      String yn = scanner.nextLine().trim().toUpperCase();
	      if (!yn.matches("[YN]")) {
	         System.err.println("y or n please");
	         return readYesNo();
	      }
	      return yn.equalsIgnoreCase("Y");
	   }

	   private static int readRoll() {
	      System.out.print(">> Throw a die and input roll, or [ENTER] to generate a random roll: ");
	      String rollStr = scanner.nextLine().trim();
	      if (rollStr.isEmpty()) {
	         int roll = new Random().nextInt(6) + 1;
	         System.out.println(">> Random roll: " + roll);
	         return roll;
	      }
	      if (!rollStr.matches("\\d+")) {
	         System.err.println("Not a number: '" + rollStr + "'");
	         return readRoll();
	      }
	      int roll = Integer.parseInt(rollStr);
	      if (roll < 1 || roll > 6) {
	         System.err.println("Invalid roll");
	         return readRoll();
	      }
	      return roll;
	   }
}
