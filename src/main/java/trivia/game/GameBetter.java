package trivia.game;


import trivia.models.Player;
import trivia.models.Questions;
import trivia.types.QuestionCategory;

import java.util.ArrayList;
import java.util.List;

import static trivia.util.CustomLogger.log;

// REFACTORED
public class GameBetter implements IGame {
    List<Player> players = new ArrayList<>();
    Questions questions;
    QuestionCategory category;

    int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox = true;

    public GameBetter() {
        this.questions = new Questions();
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));
        log(playerName + " was added");
        log("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        if (!isPlayable()) {
            log("Not enough players to start the game!");
            return;
        }
        play(roll);
    }


    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = players.get(currentPlayerIndex);
        boolean gameFinished;
        if (currentPlayer.isInPenaltyBox()) {
            return checkIfIsGettingOut(currentPlayer);
        } else {
            log("Answer was correct!!!!");
            addCoinsToPurse(currentPlayer);
            gameFinished = !isGameFinished(currentPlayer);
            nextPlayer();
            return gameFinished;
        }
    }

    public boolean wrongAnswer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.setInPenaltyBox(true);
        log("Question was incorrectly answered");
        log(currentPlayer.getName() + " was sent to the penalty box");
        nextPlayer();
        return true;
    }


    /**
     * This method checks if Players has 6 gold coins
     */
    private boolean isGameFinished(Player currentPlayer) {
        return currentPlayer.getCoins() == 6;
    }

    /**
     * This method moves to the next Player
     */
    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) {
            currentPlayerIndex = 0;
        }
    }

    private void askQuestion(QuestionCategory category) {
        log("The category is " + category.getLabel());
        log(questions.showNextQuestion(category));
    }

    private boolean isPlayable() {
        return players.size() >= 2;
    }

    private void addCoinsToPurse(Player currentPlayer) {
        int coins = currentPlayer.getCoins() + 1;
        currentPlayer.setCoins(coins);
        log(currentPlayer.getName() + " now has " + currentPlayer.getCoins() + " Gold Coins.");
    }

    private boolean checkIfIsGettingOut(Player player) {
        if (isGettingOutOfPenaltyBox) {
            log("Answer was correct!!!!");
            addCoinsToPurse(player);
            boolean gameFinished = !isGameFinished(player);
            nextPlayer();
            return gameFinished;
        } else {
            nextPlayer();
            return true;
        }
    }

    private boolean isRolledNumberEven(int roll) {
        return roll % 2 == 0;
    }

    private void play(int roll) {

        boolean moveFromJail = !isRolledNumberEven(roll);
        Player currentPlayer = players.get(currentPlayerIndex);
        log(currentPlayer.getName() + " is the current player");
        log("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            jailAction(currentPlayer, roll, moveFromJail);
            return;
        }
        currentPlayer.move(roll);
        category = questions.getCurrentCategory(currentPlayer.getPlace());
        log(currentPlayer.getName() + "'s new location is " + currentPlayer.getPlace());
        askQuestion(category);

    }

    private void jailAction(Player player, int roll, boolean moveFromJail) {

        if (!moveFromJail) {
            log(player.getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
            return;
        }

        isGettingOutOfPenaltyBox = true;
        log(player.getName() + " is getting out of the penalty box");
        player.move(roll);
        category = questions.getCurrentCategory(player.getPlace());
        log(player.getName() + "'s new location is " + player.getPlace());
        askQuestion(category);
    }
}
