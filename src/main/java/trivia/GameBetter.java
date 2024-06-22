package trivia;

import java.util.ArrayList;
import java.util.List;

// REFACTORED
public class GameBetter implements IGame {
    private static final int TOTAL_NUMBER_OF_POSITIONS = 12;
    private static final int NUMBER_OF_CATEGORIES = 4;

    private final PlayerManger playerManger = new PlayerMangerImpl();
    private final DiceManager diceManager = new DiceManagerImpl();
    private final Questions questions = new Questions();
    private boolean isGettingOutOfPenaltyBox;


    public boolean add(String playerName) {
       return playerManger.add(playerName);
    }


    public void roll(int roll) {

        handlePenalty(roll);
        playerManger.currentPlayer().rollPosition(roll, TOTAL_NUMBER_OF_POSITIONS);
        showStatusOfCurrentPlayer();
        askQuestion();
    }

    private void handlePenalty(int roll) {
        System.out.println(playerManger.getCurrentPlayerName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (playerManger.isCurrentPlayerInPenaltyBox()) {
            String isNotGettingOut = "not ";
            if (roll % 2 != 0) {
                isNotGettingOut = "";
                playerManger.currentPlayer().setInPenaltyBox(false);
            }
            System.out.println(playerManger.getCurrentPlayerName() + " is " + isNotGettingOut + "getting out of the " +
                    "penalty box");
            playerManger.nextPlayer();
            roll = diceManager.readRoll();
            handlePenalty(roll);
        }
    }

    private void showStatusOfCurrentPlayer() {
        System.out.println(playerManger.getCurrentPlayerName()
                           + "'s new location is "
                           + playerManger.getCurrentPlayerLocation());
        System.out.println("The category is " + currentCategory());
    }

    private void askQuestion() {
        System.out.println(questions.getQuestion(currentCategory()));
    }


    private Category currentCategory() {
        return Category.values()[playerManger.currentPlayer().getPosition() % NUMBER_OF_CATEGORIES];
    }

    public boolean wasCorrectlyAnswered() {
        if (playerManger.isCurrentPlayerInPenaltyBox()) {
            playerManger.nextPlayer();
            return true;
        }

        return isWinner();
    }

    private boolean isWinner() {
        System.out.println("Answer was correct!!!!");
        playerManger.currentPlayer().addCoin();
        playerManger.currentPlayer().getState();
        boolean winner = playerManger.didPlayerWin();
        playerManger.nextPlayer();

        return winner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerManger.getCurrentPlayerName() + " was sent to the penalty box");
        playerManger.currentPlayer().setInPenaltyBox(true);

        playerManger.nextPlayer();
        return true;
    }

}
