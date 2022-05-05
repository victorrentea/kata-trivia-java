package trivia;

import java.util.*;

class Deck {
    Deque<String> popQuestions = new LinkedList<>();
    Deque<String> scienceQuestions = new LinkedList<>();
    Deque<String> sportsQuestions = new LinkedList<>();
    Deque<String> rockQuestions = new LinkedList<>();

    public Deck() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public String getQuestion(Category category) {
        switch (category) {
            case POP:
                return this.getPopQuestion();
            case SCIENCE:
                return this.getScienceQuestion();
            case SPORTS:
                return this.getSportsQuestion();
            case ROCK:
                return this.getRockQuestion();
            default:
                throw new IllegalArgumentException("No questions for your category :)");
        }
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
    private List<Player> players = new ArrayList<>();

    public void add(Player player) {
        players.add(player);
    }

    public boolean isJailed(Player player) {
        return players.contains(player);
    }

    public void toJail(Player player) {
        players.add(player);
    }
}

enum Category {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");
    String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Place {
    Category category;
    int no;

    public Place(Category category, int no) {
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
    List<Place> places;
    Map<Player, Place> playersPositions;

    public Board() {
        places = new ArrayList<>();
        playersPositions = new HashMap<>();
        for (int i = 0; i < BOARD_POSITION_NO; i++) {
            places.add(i, new Place(getCategory(i), i));
        }
    }

    public void movePlayerBy(Player player, int roll) {
        final Place currentPlace = playersPositions.get(player);
        final int futurePosition = currentPlace.getNo() + roll;
//        places[currentPlayer] = places[currentPlayer] + roll;
//        if (futurePosition >= BOARD_POSITION_NO)
//            places[currentPlayer] = places[currentPlayer] - BOARD_POSITION_NO;
        playersPositions.put(player, places.get(futurePosition % BOARD_POSITION_NO));
    }

    public Place getPlayerPosition(Player player) {
        return playersPositions.get(player);
    }

    public void addPlayer(Player player) {
        playersPositions.put(player, new Place(getCategory(0), 0));
    }

    private Category getCategory(int i) {
        if (i == 0) return Category.POP;
        if (i == 4) return Category.POP;
        if (i == 8) return Category.POP;
        if (i == 1) return Category.SCIENCE;
        if (i == 5) return Category.SCIENCE;
        if (i == 9) return Category.SCIENCE;
        if (i == 2) return Category.SPORTS;
        if (i == 6) return Category.SPORTS;
        if (i == 10) return Category.SPORTS;
        return Category.ROCK;
    }
}

// REFACTOR ME
public class GameBetter implements IGame {
    private static final int WINNING_COIN_NO = 6;
    private static final int PLAYER_NO = 6;

    Deque<Player> players = new LinkedList<>();
    int[] places = new int[PLAYER_NO];
    //    int[] purses = new int[PLAYER_NO];
//    boolean[] inPenaltyBox = new boolean[PLAYER_NO];

    Deck deck;
    Jail jail;
    Board board;
    Player currentPlayer;
    boolean isGettingOutOfPenaltyBox;

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

        if (jail.isJailed(currentPlayer)) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
                moveCurrentPlayer(roll);
            } else {
                System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            moveCurrentPlayer(roll);
        }

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

    public boolean wasCorrectlyAnswered() {
        if (jail.isJailed(currentPlayer)) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                currentPlayer.awardCoin();
                System.out.println(currentPlayer.getName()
                        + " now has "
                        + currentPlayer.getPurse().getCoins()
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
            currentPlayer.awardCoin();
            System.out.println(currentPlayer.getName()
                    + " now has "
                    + currentPlayer.getPurse().getCoins()
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            nextPlayer();

            return winner;
        }
    }

    private void nextPlayer() {
        final Player nextPlayer = players.removeFirst();
        players.addLast(currentPlayer);
        currentPlayer = nextPlayer;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        jail.toJail(currentPlayer);

        nextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return currentPlayer.getPurse().getCoins() != WINNING_COIN_NO;
    }
}
