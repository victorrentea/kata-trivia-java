package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// REFACTOR ME
public class Game implements IGame {
   private static final int MAX_QUESTIONS = 50;
   private static final int MAX_PLAYERS = 6;
   private static final int BOARD_SIZE = 12;
   private static final int WINNING_COINS = 6;

   List<Player> players = new ArrayList<>();
   boolean[] currentPlayerInPenaltyBox = new boolean[MAX_PLAYERS];

   LinkedList popQuestions = new LinkedList();
   LinkedList scienceQuestions = new LinkedList();
   LinkedList sportsQuestions = new LinkedList();
   LinkedList rockQuestions = new LinkedList();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (int i = 0; i < MAX_QUESTIONS; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean hasEnoughPlayers() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      currentPlayerInPenaltyBox[howManyPlayers()] = false;
      players.add(new Player(playerName));

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (currentPlayerInPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            
            movePlayer(roll);

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + players.get(currentPlayer).position);
            String category = currentCategory(players.get(currentPlayer).position);
            System.out.println("The category is " + category);
            askQuestion(category);
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         movePlayer(roll);
         
         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + players.get(currentPlayer).position);
         String category = currentCategory(players.get(currentPlayer).position);
         System.out.println("The category is " + category);
         askQuestion(category);
      }

   }

   private void askQuestion(String category) {
      if (category == "Pop")
         System.out.println(popQuestions.removeFirst());
      if (category == "Science")
         System.out.println(scienceQuestions.removeFirst());
      if (category == "Sports")
         System.out.println(sportsQuestions.removeFirst());
      if (category == "Rock")
         System.out.println(rockQuestions.removeFirst());
   }


   private String currentCategory(int position) {
      if (position - 1 == 0) return "Pop";
      if (position - 1 == 4) return "Pop";
      if (position - 1 == 8) return "Pop";
      if (position - 1 == 1) return "Science";
      if (position - 1 == 5) return "Science";
      if (position - 1 == 9) return "Science";
      if (position - 1 == 2) return "Sports";
      if (position - 1 == 6) return "Sports";
      if (position - 1 == 10) return "Sports";
      return "Rock";
   }

   public boolean handleCorrectAnswer() {
      if (currentPlayerInPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            Player player = players.get(currentPlayer);
            player.coins++;
            System.out.println(player + " now has " + player.coins + " Gold Coins.");

            boolean winner = playerHasNotWon();
            nextPlayer();

            return winner;
         } else {
            nextPlayer();
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         Player player = players.get(currentPlayer);
            player.coins++;
            System.out.println(player + " now has " + player.coins + " Gold Coins.");

         boolean winner = playerHasNotWon();
         nextPlayer();

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      currentPlayerInPenaltyBox[currentPlayer] = true;

      nextPlayer();
      return true;
   }


   private boolean playerHasNotWon() {
      return !(players.get(currentPlayer).coins == WINNING_COINS);
   }

   private void nextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

   private void movePlayer(int roll) {
      Player player = players.get(currentPlayer);
      player.position = player.position + roll;
      if (player.position > BOARD_SIZE) player.position = player.position - BOARD_SIZE;
   }
}


class Player {
    final String name;
    int position = 1;
    int coins = 0;

    Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
