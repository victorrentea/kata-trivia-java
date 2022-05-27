package trivia;

import java.util.ArrayList;
import java.util.List;

import static trivia.QuestionCategory.*;

class Player {
    private final String name;
    private int place;
    private int coins;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public int getPlace() {
        return place;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}

// TODO refactor me
public class Game implements IGame {
    private static final int MAX_PLAYERS = 6;
    private static final int BOARD_SIZE = 12;

    private final List<String> players = new ArrayList<>();
    private final int[] places = new int[MAX_PLAYERS];
    private final int[] coins = new int[MAX_PLAYERS];
    private final boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];

    private final List<String> popQuestions = new ArrayList<>();
    private final List<String> scienceQuestions = new ArrayList<>();
    private final List<String> sportsQuestions = new ArrayList<>();
    private final List<String> rockQuestions = new ArrayList<>();

    private int currentPlayer;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add("Science Question " + i);
            sportsQuestions.add("Sports Question " + i);
            rockQuestions.add("Rock Question " + i);
        }
    }

    public void add(String playerName) {

        players.add(playerName);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                movePlayer(roll);


                System.out.println(players.get(currentPlayer)
                                   + "'s new location is "
                                   + places[currentPlayer]);
                System.out.println("The category is " + currentCategory().getLabel());
                System.out.println(getQuestion());
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            movePlayer(roll);

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory().getLabel());
            System.out.println(getQuestion());
        }

    }

    private void movePlayer(int roll) {
        places[currentPlayer] += roll;
        if (places[currentPlayer] >= BOARD_SIZE) {
            places[currentPlayer] -= BOARD_SIZE;
        }
    }

    private String getQuestion() {
        return switch (currentCategory()) {
            case POP -> popQuestions.remove(0);
            case SCIENCE -> scienceQuestions.remove(0);
            case SPORTS -> sportsQuestions.remove(0);
            case ROCK -> rockQuestions.remove(0);
        };
    }

    private QuestionCategory currentCategory() {
        int questionIndex = places[currentPlayer] % values().length;
        return switch (questionIndex) {
            case 0 -> POP;
            case 1 -> SCIENCE;
            case 2 -> SPORTS;
            default -> ROCK;
        };
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                coins[currentPlayer]++;
                System.out.println(players.get(currentPlayer)
                                   + " now has "
                                   + coins[currentPlayer]
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
            coins[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + coins[currentPlayer]
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
        return !(coins[currentPlayer] == 6);
    }


}
