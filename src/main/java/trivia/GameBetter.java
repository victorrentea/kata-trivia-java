package trivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// REFACTOR ME
public class GameBetter implements IGame {
   public static final String POP = "Pop";
   public static final String SCIENCE = "Science";
   public static final String SPORTS = "Sports";
   public static final String ROCK = "Rock";
   List<Player> players = new ArrayList();
   List<String> categories = List.of(POP, SCIENCE, SPORTS, ROCK);

   private Map<String, LinkedList<String>> questions = new HashMap<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      questions.put(POP, new LinkedList());
      questions.put(SCIENCE, new LinkedList());
      questions.put(SPORTS, new LinkedList());
      questions.put(ROCK, new LinkedList());
      for (int i = 0; i < 50; i++) {
         questions.get(POP).addLast(createQuestion(i, POP));
         questions.get(SCIENCE).addLast(createQuestion(i, SCIENCE));
         questions.get(SPORTS).addLast(createQuestion(i, SPORTS));
         questions.get(ROCK).addLast(createQuestion(i, ROCK));
      }
   }

   public String createQuestion(int index, String category) {
      return category + " Question " + index;
   }

   public boolean add(String playerName) {
      Player player = new Player();
      player.setName(playerName);
      player.setPlace(0);
      player.setPurse(0);
      player.setInPenaltyBox(false);
      players.add(player);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   private Player getCurrentPlayer() {
      return players.get(currentPlayer);
   }

   public void roll(int roll) {
    
      System.out.println(getCurrentPlayer().getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (getCurrentPlayer().isInPenaltyBox()) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(getCurrentPlayer().getName() + " is getting out of the penalty box");

            movePlayer(roll);
            askQuestion();
         } else {
            System.out.println(getCurrentPlayer().getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {
         movePlayer(roll);
         askQuestion();
      }

   }

   private void movePlayer(int roll) {
      getCurrentPlayer().move(roll);

      System.out.println(getCurrentPlayer().getName()
              + "'s new location is "
              + (getCurrentPlayer().getPlace() + 1));
      System.out.println("The category is " + currentCategory());
   }

   private void askQuestion() {
      System.out.println(questions.get(currentCategory()).removeFirst());
   }


   private String currentCategory() {
      return categories.get(getCurrentPlayer().getPlace() % 4);
   }

   public boolean wasCorrectlyAnswered() {
      if (getCurrentPlayer().isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
         nextPlayer();
         return true;
      }
      System.out.println("Answer was correct!!!!");
      incrementPurse();
      boolean winner = didPlayerWin();
      nextPlayer();

      return winner;
   }

   private void nextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) {
         currentPlayer = 0;
      }
   }

   private void incrementPurse() {
      getCurrentPlayer().incrementPurse();
      System.out.println(getCurrentPlayer().getName()
                         + " now has "
                         + getCurrentPlayer().getPurse()
                         + " Gold Coins.");
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");
      getCurrentPlayer().setInPenaltyBox(true);

      nextPlayer();
      return true;
   }


   private boolean didPlayerWin() {
      return !(getCurrentPlayer().getPurse() == 6);
   }
}
