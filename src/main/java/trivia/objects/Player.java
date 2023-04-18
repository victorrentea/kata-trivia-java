package trivia.objects;


public class Player {
    public final String name;
    public int position = 0;
    public boolean hasPenalty = false;

    private int coins = 0;

    public Player(String name) {
        this.name = name;
    }


    public int getCoins() {
        return this.coins;
    }

    public void addCoins() {
        this.coins ++;
    }
}
