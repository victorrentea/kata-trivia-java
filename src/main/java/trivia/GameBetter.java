package trivia;

import trivia.core.GameBoard;
import trivia.core.GameConfig;
import trivia.objects.Category;
import trivia.objects.Player;

import java.util.*;
import java.util.stream.IntStream;

// REFACTOR ME
public class GameBetter implements IGame {
   private final GameBoard board;

   private final Map<Category, LinkedList<String>> questions = new HashMap<>();

   private int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      this.board = new GameBoard();
      buildQuestionnaire();
   }

   public boolean wasCorrectlyAnswered() {
      Player player = this.board.getPlayer(currentPlayer);

      if (player.getPenalty() && !isGettingOutOfPenaltyBox) {
         currentPlayer++;
         if (currentPlayer == this.board.totalPlayers) currentPlayer = 0;
         return true;
      }

      return this.onPlayerAnswerCorrect(player);
   }

   public boolean wrongAnswer() {
      Player player = this.board.getPlayer(currentPlayer);
      return this.onPlayerAnswerWrong(player);
   }

   public boolean add(String playerName) {
      return this.board.addPlayer(playerName);
   }

   public void roll(int roll) {
      System.out.println(this.board.getPlayer(currentPlayer).name + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (this.board.getPlayer(currentPlayer).getPenalty()) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(this.board.getPlayer(currentPlayer).name + " is getting out of the penalty box");
            this.board.updatePlayerPosition(currentPlayer, roll);

            System.out.println(this.board.getPlayer(currentPlayer).name
                               + "'s new location is "
                               + this.board.getPlayer(currentPlayer).position);
            System.out.println("The category is " + getCurrentCategory().toString());
            getCurrentQuestion();
         } else {
            System.out.println(this.board.getPlayer(currentPlayer).name + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {
         this.board.updatePlayerPosition(currentPlayer, roll);

         System.out.println(this.board.getPlayer(currentPlayer).name
                            + "'s new location is "
                            + this.board.getPlayer(currentPlayer).position);
         System.out.println("The category is " + getCurrentCategory().toString());
         getCurrentQuestion();
      }

   }

   private void buildQuestionnaire() {
      for (Category category : Category.values()) {
         LinkedList<String> questionList = new LinkedList<>();
         IntStream.range(0, GameConfig.TOTAL_QUESTIONS).forEachOrdered(index -> {
            questionList.addLast(buildQuestion(category, index));
         });
         this.questions.put(category, questionList);
      }

   }

   private String buildQuestion(Category category, int index) {
      return String.format(GameConfig.TEXT_FORMAT_QUESTION_BODY, category, index);
   }

   private void getCurrentQuestion() {
      Category category = getCurrentCategory();
      if (!this.questions.containsKey(category)) {
         return;
      }

      String questionList = this.questions.get(category).removeFirst();
      System.out.println(questionList);

   }

   private Category getCurrentCategory() {
      return this.board.getBoardCategoryByIndex(this.board.getPlayer(currentPlayer).position);
   }

   private boolean onPlayerAnswerCorrect(Player player)  {
      player.addCoins();
      int coins = player.getCoins();
      System.out.printf((GameConfig.TEXT_FORMAT_CURRENT_PLAYER_COINS) + "%n", player.name, coins);

      boolean winner = didPlayerWin(coins);

      nextPlayerSet();
      return winner;

   }

   private boolean onPlayerAnswerWrong(Player player)  {
      System.out.printf(GameConfig.TEXT_FORMAT_ANSWER_WAS_WRONG, player.name);
      player.setPenalty(true);

      nextPlayerSet();
      return true;
   }


   private boolean didPlayerWin(int coins) {
      return !(coins >= GameConfig.PLAYER_MIN_COIN_WIN_COUNT);
   }

   private void nextPlayerSet() {
      currentPlayer++;
      if (currentPlayer == this.board.totalPlayers) currentPlayer = 0;
   }

}
