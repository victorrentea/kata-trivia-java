package trivia;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

class Deck {
    Deque<String> popQuestions = new LinkedList<>();
    Deque<String> scienceQuestions = new LinkedList<>();
    Deque<String> sportsQuestions = new LinkedList<>();
    Deque<String> rockQuestions = new LinkedList<>();

    public Deck() {
        IntStream.range(0, 50).forEach(this::generateQuestions);
    }


    public String getQuestion(Category category) {
        return category.getQuestionFunction().apply(this);
    }

    public String getPopQuestion() {
        return popQuestions.removeFirst();
    }

    public String getScienceQuestion() {
        return scienceQuestions.removeFirst();
    }

    public String getSportsQuestion() {
        return sportsQuestions.removeFirst();
    }

    public String getRockQuestion() {
        return rockQuestions.removeFirst();
    }

    private void generateQuestions(int i) {
        popQuestions.addLast("Pop Question " + i);
        scienceQuestions.addLast(("Science Question " + i));
        sportsQuestions.addLast(("Sports Question " + i));
        rockQuestions.addLast("Rock Question " + i);
    }

}

class Purse {
    private int coins;

    public void addCoin() {
        coins++;
    }

    public int getCoins() {
        return coins;
    }
}

class Player {
    String name;
    Purse purse;

    @Override
    public String toString() {
        return name;
    }

    public Player(String name) {
        this.name = name;
        this.purse = new Purse();
    }

    public void awardCoin() {
        purse.addCoin();
    }

    public Purse getPurse() {
        return purse;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class Jail {
    private final List<Player> players = new ArrayList<>();

    public void add(Player player) {
        players.add(player);
    }

    public boolean isJailed(Player player) {
        return players.contains(player);
    }

    public void toJail(Player player) {
        if (players.contains(player))
            return;
        players.add(player);
    }

    public void release(Player player) {
        players.remove(player);
    }
}

enum Category {
    POP(Deck::getPopQuestion, "Pop", Arrays.asList(0, 4, 8)),

    SCIENCE(Deck::getScienceQuestion, "Science", Arrays.asList(1, 5, 9)),

    SPORTS(Deck::getSportsQuestion, "Sports", Arrays.asList(2, 6, 10)),

    ROCK(Deck::getRockQuestion, "Rock", Arrays.asList(11, 12));

    private final Function<Deck, String> questionFunction;

    String name;
    List<Integer> positions;

    Category(Function<Deck, String> questionFunction, String name, List<Integer> positions) {
        this.questionFunction = questionFunction;
        this.name = name;
        this.positions = positions;
    }

    public String getName() {
        return name;
    }

    public static Category getCategory(int i) {
        for (Category val : values())
            if (val.positions.contains(i))
                return val;
        return ROCK;
    }

    public Function<Deck, String> getQuestionFunction() {
        return questionFunction;
    }
}

class Position {
    Category category;
    int no;

    public Position(Category category, int no) {
        this.category = category;
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public Category getCategory() {
        return category;
    }
}

class Board {
    private static final int BOARD_POSITION_NO = 12;
    List<Position> places;
    Map<Player, Position> playersPositions;

    public Board() {
        places = new ArrayList<>();
        playersPositions = new HashMap<>();
        for (int i = 0; i < BOARD_POSITION_NO; i++) {
            places.add(i, new Position(Category.getCategory(i), i));
        }
    }

    public void movePlayerBy(Player player, int roll) {
        final Position currentPlace = playersPositions.get(player);
        final int futurePosition = currentPlace.getNo() + roll;
        playersPositions.put(player, places.get(futurePosition % BOARD_POSITION_NO));
    }

    public Position getPlayerPosition(Player player) {
        return playersPositions.get(player);
    }

    public void addPlayer(Player player) {
        playersPositions.put(player, new Position(Category.getCategory(0), 0));
    }


}

// REFACTOR ME
public class GameBetter implements IGame {
    private static final int WINNING_COIN_NO = 6;

    private final Deque<Player> players = new LinkedList<>();

    private final Deck deck;
    private final Jail jail;
    private final Board board;
    private Player currentPlayer;

    public GameBetter() {
        deck = new Deck();
        jail = new Jail();
        board = new Board();
    }


    public boolean add(String playerName) {
        final Player newPlayer = new Player(playerName);
        players.add(newPlayer);
        board.addPlayer(newPlayer);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());

        return true;
    }

    public void roll(int roll) {
        if (currentPlayer == null)
            nextPlayer();

        System.out.println(currentPlayer + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (!jail.isJailed(currentPlayer)) {
            moveCurrentPlayer(roll);
            return;
        }
        if (roll % 2 == 0) {
            System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
            return;
        }

        jail.release(currentPlayer);
        System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
        moveCurrentPlayer(roll);

    }

    public boolean wasCorrectlyAnswered() {
        if (jail.isJailed(currentPlayer)) {
            nextPlayer();
            return true;
        }
        return handleCorrectAnswer();
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        jail.toJail(currentPlayer);
        nextPlayer();
        return true;
    }

    private boolean handleCorrectAnswer() {
        System.out.println("Answer was correct!!!!");
        currentPlayer.awardCoin();
        System.out.println(currentPlayer.getName()
                + " now has "
                + currentPlayer.getPurse().getCoins()
                + " Gold Coins.");
        final boolean result = didPlayerWin();
        nextPlayer();
        return result;
    }

    private void nextPlayer() {
        final Player nextPlayer = players.removeFirst();
        players.addLast(currentPlayer);
        currentPlayer = nextPlayer;
    }

    private void moveCurrentPlayer(int roll) {
        board.movePlayerBy(currentPlayer, roll);
        System.out.println(currentPlayer.getName()
                + "'s new location is "
                + board.getPlayerPosition(currentPlayer).getNo());
        System.out.println("The category is " + currentCategory().getName());
        askQuestion();
    }

    private void askQuestion() {
        System.out.println(deck.getQuestion(currentCategory()));
    }

    private Category currentCategory() {
        return board.getPlayerPosition(currentPlayer).getCategory();
    }

    private boolean didPlayerWin() {
        return currentPlayer.getPurse().getCoins() != WINNING_COIN_NO;
    }

}
