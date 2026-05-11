package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedHashMap;

// REFACTOR ME
public class Game implements IGame {
   static final int MAX_QUESTIONS = 50;
   private static final int MAX_PLAYERS = 6;
   static final int BOARD_SIZE = 12;
   static final int WINNING_COINS = 6;

   List<Player> players = new ArrayList<>();

   QuestionDeck deck = new QuestionDeck();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public boolean hasEnoughPlayers() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName));

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      Player player = players.get(currentPlayer);
      System.out.println(player + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (player.inPenaltyBox) {
         handlePenaltyBoxTurn(player, roll);
      } else {
         handleNormalTurn(player, roll);
      }
   }

   private void handleNormalTurn(Player player, int roll) {
      player.advance(roll);
      System.out.println(player + "'s new location is " + player.position);
      
      String category = currentCategory(player.position);
      System.out.println("The category is " + category);
      askQuestion(category);
   }

   private void handlePenaltyBoxTurn(Player player, int roll) {
      if (roll % 2 != 0) {
         isGettingOutOfPenaltyBox = true;
         System.out.println(player + " is getting out of the penalty box");
         handleNormalTurn(player, roll);
      } else {
         System.out.println(player + " is not getting out of the penalty box");
         isGettingOutOfPenaltyBox = false;
      }
   }

   private void askQuestion(String category) {
      System.out.println(deck.nextQuestion(category));
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
      if (players.get(currentPlayer).inPenaltyBox) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            Player player = players.get(currentPlayer);
            player.addCoin();
            System.out.println(player + " now has " + player.coins + " Gold Coins.");

            boolean winner = !player.hasWon();
            nextPlayer();

            return winner;
         } else {
            nextPlayer();
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         Player player = players.get(currentPlayer);
            player.addCoin();
            System.out.println(player + " now has " + player.coins + " Gold Coins.");

         boolean winner = !player.hasWon();
         nextPlayer();

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      players.get(currentPlayer).sendToPenaltyBox();

      nextPlayer();
      return true;
   }


   private void nextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }
}


class Player {
    final String name;
    int position = 1;
    int coins = 0;
    boolean inPenaltyBox = false;

   Player(String name) {
      this.name = name;
   }

   public void advance(int roll) {
      position = position + roll;
      if (position > Game.BOARD_SIZE) position = position - Game.BOARD_SIZE;
    }

   public void addCoin() {
      coins++;
   }

   public void sendToPenaltyBox() {
      inPenaltyBox = true;
   }

   public boolean hasWon() {
      return coins == Game.WINNING_COINS;
   }

   @Override
   public String toString() {
      return name;
   }
}


class QuestionDeck {
    private final Map<String, Queue<String>> questionsByCategory = new LinkedHashMap<>();

    QuestionDeck() {
        // Inicializar las colas vacías en el mapa
        questionsByCategory.put("Pop", new LinkedList<>());
        questionsByCategory.put("Science", new LinkedList<>());
        questionsByCategory.put("Sports", new LinkedList<>());
        questionsByCategory.put("Rock", new LinkedList<>());

        // Poblar las preguntas
        for (int i = 0; i < Game.MAX_QUESTIONS; i++) {
            questionsByCategory.get("Pop").add("Pop Question " + i);
            questionsByCategory.get("Science").add("Science Question " + i);
            questionsByCategory.get("Sports").add("Sports Question " + i);
            questionsByCategory.get("Rock").add(createRockQuestion(i));
        }
    }

    private String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public String nextQuestion(String category) {
        return questionsByCategory.get(category).poll();
    }
}
