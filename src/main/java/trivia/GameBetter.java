package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// REFACTOR ME

public class GameBetter implements IGame {


    public static final int NUMBER_OF_CARDS = 50;

    public static final int NUMBER_OF_CELLS = 12;

    private final List<Player> players = new ArrayList<>();

    private final List<String> popQuestions = new LinkedList<>();
    private final List<String> scienceQuestions = new LinkedList<>();
    private final List<String> sportsQuestions = new LinkedList<>();
    private final List<String> rockQuestions = new LinkedList<>();

    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    public GameBetter() {
        for (int i = 0; i < NUMBER_OF_CARDS; i++) {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add("Science Question " + i);
            sportsQuestions.add("Sports Question " + i);
            rockQuestions.add("Rock Question " + i);
        }
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
        System.out.println(currentPlayer() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer().isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(currentPlayer() + " is getting out of the penalty box");

                regularRoll(roll);
            } else {
                System.out.println(currentPlayer() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            regularRoll(roll);
        }
    }

    private void regularRoll(int roll) {
        currentPlayer().movePlayer(roll);
        System.out.println(
                currentPlayer() + "'s new location is " + currentPlayer()
                        .getPosition());
        System.out.println("The category is " + findCurrentCategory(currentPlayer().getPosition()));
        askQuestion();
    }

    private Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private void askQuestion() {
        String currentCategory = findCurrentCategory(currentPlayer().getPosition());
        List<String> questions = null; //TODO Don't!

        if ("Pop".equals(currentCategory)) {
            questions = popQuestions;
        }
        if ("Science".equals(currentCategory)) {
            questions = scienceQuestions;
        }
        if ("Sports".equals(currentCategory)) {
            questions = sportsQuestions;
        }
        if ("Rock".equals(currentCategory)) {
            questions = rockQuestions;
        }
        System.out.println(questions.remove(0));
    }

    private String findCurrentCategory(int position) {
        switch (position % 4) {
            case 0:
                return "Pop";
            case 1:
                return "Science";
            case 2:
                return "Sports";
            default:
                return "Rock";
        }
    }

    public boolean wasCorrectlyAnswered() {
        if (currentPlayer().isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                currentPlayer().addCoin();
                System.out.println(currentPlayer()
                                   + " now has "
                                   + currentPlayer().getCoins()
                                   + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayerIndex++;
                if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

                return winner;
            } else {
                currentPlayerIndex++;
                if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            currentPlayer().addCoin();
            System.out.println(currentPlayer()
                               + " now has "
                               + currentPlayer().getCoins()
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayerIndex++;
            if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer() + " was sent to the penalty box");
        currentPlayer().moveToPenaltyBox();

        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(currentPlayer().getCoins() == 6);
    }
}
