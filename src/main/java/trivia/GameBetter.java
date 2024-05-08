package trivia;

import java.util.ArrayList;
import java.util.List;

// REFACTORED
public class GameBetter implements IGame {
    private static final int TOTAL_NUMBER_OF_POSITIONS = 12;
    private static final int NUMBER_OF_CATEGORIES = 4;

    private final List<Player> players = new ArrayList<>();
    private final Questions questions = new Questions();
    private boolean isGettingOutOfPenaltyBox;

    private int currentPlayerIndex;
    // @Transient (JPA) / transient (keyword Java) marcheaza un camp NE-MAPAT IN BAZA
    // temporary field code smell. il mai intalnesti ca campuri @Transient/transient
//    private Player currentPlayer; // = players.get(currentPlayerIndex); = redudanta

    public Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public boolean isPlayable() {
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
        System.out.println(currentPlayer().getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer().isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(currentPlayer().getName() + " is getting out of the " +
                                   "penalty box");
            } else {
                System.out.println(currentPlayer().getName() + " is not " +
                                   "getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
                return;
            }

        }

        currentPlayer().rollPosition(roll, TOTAL_NUMBER_OF_POSITIONS);
        showStatusOfCurrentPlayer();
        askQuestion();
    }

    private void showStatusOfCurrentPlayer() {
        System.out.println(currentPlayer().getName()
                           + "'s new location is "
                           + currentPlayer().getPosition());
        System.out.println("The category is " + currentCategory());
    }

    private void askQuestion() {
        System.out.println(questions.getQuestion(currentCategory()));
    }


    private Category currentCategory() {
        return Category.values()[currentPlayer().getPosition() % NUMBER_OF_CATEGORIES];
    }

    public boolean wasCorrectlyAnswered() {
        if (currentPlayer().isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                currentPlayer().addCoin();
                System.out.println(currentPlayer().getName()
                                   + " now has "
                                   + currentPlayer().getCoins()
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
            currentPlayer().addCoin();
            System.out.println(currentPlayer().getName()
                               + " now has "
                               + currentPlayer().getCoins()
                               + " Gold Coins.");

            boolean winner = didPlayerWin();

            nextPlayer();

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer().getName() + " was sent to the penalty box");
        currentPlayer().setInPenaltyBox(true);

        nextPlayer();
        return true;
    }

    public void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
    }


    private boolean didPlayerWin() {
        return !(currentPlayer().getCoins() == 6);
    }
}
