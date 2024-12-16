package trivia;

import java.util.ArrayList;
import java.util.List;

import static trivia.Game.QuestionCategory.*;


class Player {
  private String name;
  private int place;
  private int purse;
  private boolean inPenaltyBox;

  public int getPlace() {
    return place;
  }

  public String getName() {
    return name;
  }

  public Player setName(String name) {
    this.name = name;
    return this;
  }

  public Player setPlace(int place) {
    this.place = place;
    return this;
  }

  public int getPurse() {
    return purse;
  }

  public Player setPurse(int purse) {
    this.purse = purse;
    return this;
  }

  public boolean isInPenaltyBox() {
    return inPenaltyBox;
  }

  public Player setInPenaltyBox(boolean inPenaltyBox) {
    this.inPenaltyBox = inPenaltyBox;
    return this;
  }
}

public class Game implements IGame {
  private List<Player> players = new ArrayList<>();
  private List<String> playerNames = new ArrayList<>();
  private int[] places = new int[6];
  private int[] purses = new int[6];
  private boolean[] inPenaltyBox = new boolean[6];

  //  enum TypeQuestion
//  enum TypeOfQuestion
//  enum QuestionType
  enum QuestionCategory {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    public final String label;

    QuestionCategory(String label) {
      this.label = label;
    }
  }

  List<String> popQuestions = new ArrayList<>();
  List<String> scienceQuestions = new ArrayList<>();
  List<String> sportsQuestions = new ArrayList<>();
  List<String> rockQuestions = new ArrayList<>();

  int currentPlayer = 0;
  boolean isGettingOutOfPenaltyBox;

  public Game() {
    for (int i = 0; i < 50; i++) {
      popQuestions.add("Pop Question " + i);
      scienceQuestions.add("Science Question " + i);
      sportsQuestions.add("Sports Question " + i);
      rockQuestions.add("Rock Question " + i);
    }
  }

  public boolean isPlayable() {
    return (howManyPlayers() >= 2);
  }

  public boolean add(String playerName) {
    playerNames.add(playerName);
    places[howManyPlayers()] = 0;
    purses[howManyPlayers()] = 0;
    inPenaltyBox[howManyPlayers()] = false;

    System.out.println(playerName + " was added");
    System.out.println("They are player number " + playerNames.size());
    return true;
  }

  public int howManyPlayers() {
    return playerNames.size();
  }

  public void roll(int roll) {
    System.out.println(playerNames.get(currentPlayer) + " is the current player");
    System.out.println("They have rolled a " + roll);

    if (inPenaltyBox[currentPlayer]) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true;

        System.out.println(playerNames.get(currentPlayer) + " is getting out of the penalty box");
        movePlayer(roll);

        System.out.println(playerNames.get(currentPlayer)
                           + "'s new location is "
                           + places[currentPlayer]);
        System.out.println("The category is " + currentCategory().label);
        askQuestion();
      } else {
        System.out.println(playerNames.get(currentPlayer) + " is not getting out of the penalty box");
        isGettingOutOfPenaltyBox = false;
      }

    } else {

      movePlayer(roll);

      System.out.println(playerNames.get(currentPlayer)
                         + "'s new location is "
                         + places[currentPlayer]);
      System.out.println("The category is " + currentCategory().label);
      askQuestion();
    }
  }


  private void movePlayer(int roll) {
//    places[currentPlayer] += roll;
//    places[currentPlayer] %= 12;
    places[currentPlayer] = (places[currentPlayer]+roll) % 12;
  }


  private void askQuestion() {
    QuestionCategory category = currentCategory();
    if (category == POP)
      System.out.println(popQuestions.remove(0));
    if (category == SCIENCE)
      System.out.println(scienceQuestions.remove(0));
    if (category == SPORTS)
      System.out.println(sportsQuestions.remove(0));
    if (category == ROCK)
      System.out.println(rockQuestions.remove(0));
  }

  private QuestionCategory currentCategory() {
//    return QuestionCategory.values()[questionIndex];// @andrei to blame
    int questionIndex = places[currentPlayer] % 4;
    switch (questionIndex) {
      case 0:
        return POP;
      case 1:
        return SCIENCE;
      case 2:
        return SPORTS;
      case 3:
        return ROCK;
      default:
        throw new IllegalArgumentException("Matematic imposibil");
    }
  }

  public boolean wasCorrectlyAnswered() {
    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        System.out.println("Answer was correct!!!!");
        purses[currentPlayer]++;
        System.out.println(playerNames.get(currentPlayer)
                           + " now has "
                           + purses[currentPlayer]
                           + " Gold Coins.");

        boolean winner = didPlayerWin();
        currentPlayer++;
        if (currentPlayer == playerNames.size()) currentPlayer = 0;

        return winner;
      } else {
        currentPlayer++;
        if (currentPlayer == playerNames.size()) currentPlayer = 0;
        return true;
      }
    } else {
      System.out.println("Answer was corrent!!!!");
      purses[currentPlayer]++;
      System.out.println(playerNames.get(currentPlayer)
                         + " now has "
                         + purses[currentPlayer]
                         + " Gold Coins.");

      boolean winner = didPlayerWin();
      currentPlayer++;
      if (currentPlayer == playerNames.size()) currentPlayer = 0;

      return winner;
    }
  }

  public boolean wrongAnswer() {
    System.out.println("Question was incorrectly answered");
    System.out.println(playerNames.get(currentPlayer) + " was sent to the penalty box");
    inPenaltyBox[currentPlayer] = true;

    currentPlayer++;
    if (currentPlayer == playerNames.size()) currentPlayer = 0;
    return true;
  }

  private boolean didPlayerWin() {
    return !(purses[currentPlayer] == 6);
  }
}
