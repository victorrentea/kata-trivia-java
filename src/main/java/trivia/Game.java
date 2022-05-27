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

    void move(int roll) {
        place += roll;
        if (place >= Game.BOARD_SIZE) {
            place -= Game.BOARD_SIZE;
        }
    }
}

// TODO refactor me
public class Game implements IGame {
    private static final int MAX_PLAYERS = 6;
    public static final int BOARD_SIZE = 12;

    private final List<Player> players = new ArrayList<>();
    private final int[] coins = new int[MAX_PLAYERS]; // TODO victorrentea 27.05.2022: mutat in player
    private final boolean[] inPenaltyBox = new boolean[MAX_PLAYERS]; // TODO victorrentea 27.05.2022: mutat in player

    // TODO victorrentea 27.05.2022: map mai jos Map<QuestionCategory, List<Question>>
    private final List<String> popQuestions = new ArrayList<>();
    private final List<String> scienceQuestions = new ArrayList<>();
    private final List<String> sportsQuestions = new ArrayList<>();
    private final List<String> rockQuestions = new ArrayList<>();
    // TODO victorrentea 27.05.2022: QuestionDecks (mapa de mai sus)
    private int currentPlayerIndex;
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
        players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    public void roll(int roll) {
        System.out.println(currentPlayer().getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayerIndex]) { // TODO victorrentea 27.05.2022: extract rollInPenalty
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(currentPlayer().getName() + " is getting out of the penalty box");
                currentPlayer().move(roll);


                System.out.println(currentPlayer().getName()
                                   + "'s new location is "
                                   + currentPlayer().getPlace());
                System.out.println("The category is " + currentCategory().getLabel());
                System.out.println(getQuestion());
            } else {
                System.out.println(currentPlayer().getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else { // TODO victorrentea 27.05.2022: regularRoll
            currentPlayer().move(roll);

            System.out.println(currentPlayer().getName()
                               + "'s new location is "
                               + currentPlayer().getPlace());
            System.out.println("The category is " + currentCategory().getLabel());
            System.out.println(getQuestion());
        }

    }

    private Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private String getQuestion() { // move in QuestionDecks
        return switch (currentCategory()) {
            case POP -> popQuestions.remove(0);
            case SCIENCE -> scienceQuestions.remove(0);
            case SPORTS -> sportsQuestions.remove(0);
            case ROCK -> rockQuestions.remove(0);
        };
    }

    private QuestionCategory currentCategory() {
        int questionIndex = currentPlayer().getPlace() % values().length;
        return switch (questionIndex) {
            case 0 -> POP;
            case 1 -> SCIENCE;
            case 2 -> SPORTS;
            default -> ROCK;
        };
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayerIndex]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                coins[currentPlayerIndex]++; // TODO victorrentea 27.05.2022: player.addCoin
                System.out.println(currentPlayer().getName()
                                   + " now has "
                                   + coins[currentPlayerIndex]
                                   + " Gold Coins.");

                boolean winner = didPlayerWin();
                // TODO victorrentea 27.05.2022: DRY violation:
                currentPlayerIndex++;
                if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

                return winner;
            } else {
                currentPlayerIndex++;
                if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
                return true;
            }


        } else {

            // GASESTI OARE BUGUL de business mai jos?. Daca da, esti tare:
            System.out.println("Answer was corrent!!!!");
            coins[currentPlayerIndex]++;
            System.out.println(currentPlayer().getName()
                               + " now has "
                               + coins[currentPlayerIndex]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            // iarasi si iarasi si iarasi
            currentPlayerIndex++;
            if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer().getName() + " was sent to the penalty box");
        inPenaltyBox[currentPlayerIndex] = true;

        // si-ncodata m ai flacai
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
        return true;
    }

    // minciuna in cod: nume misleading
    private boolean didPlayerWin() {
        return !(coins[currentPlayerIndex] == 6);
    }


}
