package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// REFACTOR ME
public class GameBetter implements IGame {

    public static class Player {
        private String name;
        private int position = 0;
        private int purse = 0;

        public void addCoin() {
            purse++;
        }

        public int getPurse() {
            return purse;
        }

        public void setPurse(int purse) {
            this.purse = purse;
        }

        private boolean inPenaltyBox = false;

        public boolean isInPenaltyBox() {
            return inPenaltyBox;
        }

        public int getPosition() {
            return position;
        }

        public void moveToPenaltyBox() {
            this.inPenaltyBox = true;
        }

        public Player(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public void movePlayer(int roll) {
            position += roll;
            if (position >= NUMBER_OF_CELLS) {
                position -= NUMBER_OF_CELLS;
            }
        }
    }

    public static final int NUMBER_OF_CARDS = 50;

    public static final int NUMBER_OF_CELLS = 12;

    private final List<Player> players = new ArrayList<>();
    private final List<String> popQuestions = new LinkedList<>();
    private final List<String> scienceQuestions = new LinkedList<>();
    private final List<String> sportsQuestions = new LinkedList<>();
    private final List<String> rockQuestions = new LinkedList<>();
    private int currentPlayer = 0;
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
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private Player currentPlayer() {
        return players.get(currentPlayer);
    }

    private void askQuestion() {
        if (currentCategory().equals("Pop")) {
            System.out.println(popQuestions.remove(0));
        }
        if (currentCategory().equals("Science")) {
            System.out.println(scienceQuestions.remove(0));
        }
        if (currentCategory().equals("Sports")) {
            System.out.println(sportsQuestions.remove(0));
        }
        if (currentCategory().equals("Rock")) {
            System.out.println(rockQuestions.remove(0));

        }
    }

    private String currentCategory() {
        if (currentPlayer().getPosition() == 0) return "Pop";
        if (currentPlayer().getPosition() == 4) return "Pop";
        if (currentPlayer().getPosition() == 8) return "Pop";
        if (currentPlayer().getPosition() == 1) return "Science";
        if (currentPlayer().getPosition() == 5) return "Science";
        if (currentPlayer().getPosition() == 9) return "Science";
        if (currentPlayer().getPosition() == 2) return "Sports";
        if (currentPlayer().getPosition() == 6) return "Sports";
        if (currentPlayer().getPosition() == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (currentPlayer().isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                currentPlayer().addCoin();
                purses[currentPlayer]++;
                System.out.println(currentPlayer()
                                   + " now has "
                                   + currentPlayer().getPurse()
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
            currentPlayer().addCoin();
            System.out.println(currentPlayer()
                               + " now has "
                               + currentPlayer().getPurse()
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer() + " was sent to the penalty box");
        currentPlayer().moveToPenaltyBox();

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(currentPlayer().getPurse() == 6);
    }
}
