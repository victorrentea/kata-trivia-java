package trivia.objects;


public class Player {
    public final String name;
    public int position = 0;

    private int coins = 0;
    private boolean hasPenalty = false;

    public Player(String name) {
        this.name = name;
    }


    public int getCoins() {
        return this.coins;
    }

    public void addCoins() {
        this.coins ++;
    }

    public boolean getPenalty() {
        return this.hasPenalty;
    }

    public void setPenalty(boolean value) {
        this.hasPenalty = value;
    }

}
