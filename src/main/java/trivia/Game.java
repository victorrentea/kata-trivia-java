package trivia;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGame {
   private final List<Player> players = new ArrayList<>();
   private final Questions questions= new Questions();

   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;


   public void add(String playerName) {
      players.add(new Player(playerName));
      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
   }

   public void roll(int roll) {
      System.out.println(currentPlayer().name() + " is the current player");
      System.out.println("They have rolled a " + roll);


      // TODO nested ifs.
      if (currentPlayer().isInPenaltyBox()) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(currentPlayer().name() + " is getting out of the penalty box");
            currentPlayer().move(roll);

            System.out.println(currentPlayer().name()+ "'s new location is " + currentPlayer().place());
            System.out.println("The category is " + questions.currentCategory(currentPlayer().place()).getLabel());
            askQuestion();
         } else {
            System.out.println(currentPlayer().name() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false; // TODO Do we get an answer from the biz about penalty box ?
         }
      } else {
         currentPlayer().move(roll);
         System.out.println(currentPlayer().name() + "'s new location is " + currentPlayer().place());
         System.out.println("The category is " + questions.currentCategory(currentPlayer().place()).getLabel());
         askQuestion();
      }
   }

   private void askQuestion() {
      System.out.println(questions.extractNextQuestion(currentPlayer().place()));
   }

   private Player currentPlayer() {
      return players.get(currentPlayer);
   }

   public boolean wasCorrectlyAnswered() {
      boolean result = aaa();

      moveToNextPlayer();
      return result;
   }

   // TODO name this
   private boolean aaa() {
      // TODO 3 ifs
      if (currentPlayer().isInPenaltyBox()) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            // TODO posisble bug: shouldn't you exit the penalty box here ? // email the biz: in case he's about to exit from pen box and correct answer
            currentPlayer().addCoin();
            System.out.println(currentPlayer().name() + " now has " + currentPlayer().coins() + " Gold Coins.");
            return currentPlayer().didPlayerWin();
         } else {
            return true;
         }
      } else {
         System.out.println("Answer was corrent!!!!");
         currentPlayer().addCoin();
         System.out.println(currentPlayer().name() + " now has "+ currentPlayer().coins() + " Gold Coins.");
         return currentPlayer().didPlayerWin();
      }
   }

   private void moveToNextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(currentPlayer().name() + " was sent to the penalty box");
      currentPlayer().moveToPenaltyBox();

      moveToNextPlayer();
      return true;
   }


}


/**
 * extract method (replace duplicate code)
 * Inline
 * renamed
 * baby steps: don't break compilation at any moment while refactoring
 * Move Method ---> Player.move(roll) -- with invariants
 * switch [expression]: 1) one lines/case, 2) default 3) no extra code in that method
 * Alt-J
 * Any field you create let it be private final at the start. NOT creating gettters and setters. If you have to create them, create them individually
 * records Java 16+ - immutable structs
 *
 * Introduced parameter
 * Encapsulated the logic of questions + extracted class
 *
 */