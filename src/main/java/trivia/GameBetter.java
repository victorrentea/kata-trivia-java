package trivia;

import trivia.core.GameBoard;
import trivia.core.GameConfig;
import trivia.objects.Category;

import java.util.*;
import java.util.stream.IntStream;

// REFACTOR ME
public class GameBetter implements IGame {
   private final GameBoard board;
   ArrayList<String> players = new ArrayList<>();
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   private final Map<Category, LinkedList<String>> questions = new HashMap<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      this.board = new GameBoard();
      buildQuestionnaire();
   }

   public boolean wasCorrectlyAnswered() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                 + " now has "
                 + purses[currentPlayer]
                 + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }


   public boolean add(String playerName) {
      players.add(playerName);
      this.board.addPlayer(playerName);
      System.out.println("They are player number " + players.size());
      return true;
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            this.board.updatePlayerPosition(currentPlayer, roll);

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + this.board.getPlayerCurrentPosition(currentPlayer));
            System.out.println("The category is " + getCurrentCategory().toString());
            getCurrentQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {
         this.board.updatePlayerPosition(currentPlayer, roll);

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + this.board.getPlayerCurrentPosition(currentPlayer));
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
      return this.board.getBoardCategoryByIndex(this.board.getPlayerCurrentPosition(currentPlayer));
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
