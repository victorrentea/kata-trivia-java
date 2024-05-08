package trivia;

import java.util.ArrayList;

// REFACTORED
public class GameBetter implements IGame {
    static final int TOTAL_NUMBER_OF_POSITIONS = 12;

    static final int NUMBER_OF_CATEGORIES = 4;
    ArrayList<Player> players = new ArrayList<>();

    Questions questions = new Questions();

    Player currentPlayer;

    int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox;

    public GameBetter() {

    }


    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        if (currentPlayer == null) {
            currentPlayer = players.get(currentPlayerIndex);
        }

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }


    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(currentPlayer.name + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(currentPlayer.name + " is getting out of the " +
                        "penalty box");
            } else {
                System.out.println(currentPlayer.name + " is not " +
                        "getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
                return;
            }

        }

        currentPlayer.rollPosition(roll, TOTAL_NUMBER_OF_POSITIONS);
        showStatusOfCurrentPlayer();
        askQuestion();
    }

    private void showStatusOfCurrentPlayer() {
        System.out.println(currentPlayer.name
                + "'s new location is "
                + currentPlayer.position);
        System.out.println("The category is " + currentCategory());
    }

    private void askQuestion() {
        System.out.println(questions.getQuestion(currentCategory()));
    }


    private String currentCategory() {
        return Category.values()[currentPlayer.position % NUMBER_OF_CATEGORIES].name();
    }

    public boolean wasCorrectlyAnswered() {
        if (currentPlayer.isInPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                currentPlayer.coins++;
                System.out.println(currentPlayer.name
                        + " now has "
                        + currentPlayer.coins
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                nextPlayer();

                return winner;
            } else {
                nextPlayer();
                return true;
            }


        } else {

            System.out.println("Answer was correct!!!!");
            currentPlayer.coins++;
            System.out.println(currentPlayer.name
                    + " now has "
                    + currentPlayer.coins
                    + " Gold Coins.");

            boolean winner = didPlayerWin();

            nextPlayer();

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.name + " was sent to the penalty box");
        currentPlayer.isInPenaltyBox = true;

        nextPlayer();
        return true;
    }

    public void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
        currentPlayer = players.get(currentPlayerIndex);
    }


    private boolean didPlayerWin() {
        return !(currentPlayer.coins == 6);
    }
}
