package trivia;



public class Player {
    public String name;
    public int position;
    public int coins;

    public boolean isInPenaltyBox;


    public Player(String name){
        this.name = name;
        this.position = 0;
        this.coins = 0;
        this.isInPenaltyBox = false;
    }

    public void rollPosition(int roll, int totalNumberOfPositions){
        this.position = this.position + roll;
        if (this.position > totalNumberOfPositions - 1)
            this.position -= totalNumberOfPositions;

    }


}
