package trivia;

import trivia.core.GameBoard;
import trivia.core.GameConfig;
import trivia.objects.Category;
import trivia.objects.Player;

import java.util.*;
import java.util.stream.IntStream;

// REFACTOR ME
public class GameV2 implements IGame {
   private final GameBoard board;

   private final Map<Category, LinkedList<String>> questions = new HashMap<>();

   private int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameV2() {
      this.board = new GameBoard();
      buildQuestionnaire();
   }

   public boolean wasCorrectlyAnswered() {
      Player player = this.board.getPlayer(currentPlayer);

      if (player.getPenalty() && !isGettingOutOfPenaltyBox) {
         this.nextPlayerSet();
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
      Player player = this.board.getPlayer(currentPlayer);

      System.out.printf(GameConfig.TEXT_FORMAT_ROLL, player.name, roll);

      if (player.getPenalty() && roll % 2 == 0) {
         System.out.printf(GameConfig.TEXT_FORMAT_PENALTY_IN, player.name );
         isGettingOutOfPenaltyBox = false;
         return;
      }

      this.board.updatePlayerPosition(currentPlayer, roll);
      String message = String.format(GameConfig.TEXT_FORMAT_ROLL_END,
              player.name,
              player.position,
              getCurrentCategory().toString()
      );

      if (player.getPenalty() && roll % 2 != 0) {
         isGettingOutOfPenaltyBox = true;
         message = String.format(GameConfig.TEXT_FORMAT_PENALTY_OUT + GameConfig.TEXT_FORMAT_ROLL_END,
                 player.name,
                 player.name,
                 player.position,
                 getCurrentCategory().toString()
         );
      }

      System.out.print(message);
      getNextQuestion();

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

   private void getNextQuestion() {
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
