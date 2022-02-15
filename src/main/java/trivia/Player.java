package trivia;

import static trivia.GameBetter.NUMBER_OF_CELLS;

public class Player {
    private final String name;
    private int position = 0;
    private int coins = 0;
    private boolean inPenaltyBox = false;

    public void addCoin() {
        coins++;
    }

    public int getCoins() {
        return coins;
    }

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