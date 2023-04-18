package trivia;

import trivia.objects.Category;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

// REFACTOR ME
public class GameBetter implements IGame {
   private final String QUESTION_BASE_FORMAT = "%s Question %d";

   ArrayList<String> players = new ArrayList<>();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   LinkedList<String> popQuestions = new LinkedList<>();
   LinkedList<String> scienceQuestions = new LinkedList<>();
   LinkedList<String> sportsQuestions = new LinkedList<>();
   LinkedList<String> rockQuestions = new LinkedList<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast(String.format(QUESTION_BASE_FORMAT, Category.POP, i));
         scienceQuestions.addLast(String.format(QUESTION_BASE_FORMAT, Category.SCIENCE, i));
         sportsQuestions.addLast(String.format(QUESTION_BASE_FORMAT, Category.SPORTS, i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return String.format(QUESTION_BASE_FORMAT, Category.ROCK, index);
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      players.add(playerName);
      places[howManyPlayers()] = 0;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;

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

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places[currentPlayer]);
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void askQuestion() {

      if (Objects.equals(currentCategory(), Category.POP.toString()))
         System.out.println(popQuestions.removeFirst());
      if (Objects.equals(currentCategory(), Category.SCIENCE.toString()))
         System.out.println(scienceQuestions.removeFirst());
      if (Objects.equals(currentCategory(), Category.SPORTS.toString()))
         System.out.println(sportsQuestions.removeFirst());
      if (Objects.equals(currentCategory(), Category.ROCK.toString()))
         System.out.println(rockQuestions.removeFirst());
   }


   private String currentCategory() {
      if (places[currentPlayer] == 0) return Category.POP.toString();
      if (places[currentPlayer] == 4) return Category.POP.toString();
      if (places[currentPlayer] == 8) return Category.POP.toString();

      if (places[currentPlayer] == 1) return Category.SCIENCE.toString();
      if (places[currentPlayer] == 5) return Category.SCIENCE.toString();
      if (places[currentPlayer] == 9) return Category.SCIENCE.toString();

      if (places[currentPlayer] == 2) return Category.SPORTS.toString();
      if (places[currentPlayer] == 6) return Category.SPORTS.toString();
      if (places[currentPlayer] == 10) return Category.SPORTS.toString();
      return Category.ROCK.toString();
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


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
