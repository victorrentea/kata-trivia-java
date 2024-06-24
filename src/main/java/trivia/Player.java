package trivia;

public class Player {
  private final String name;
  private int position;
  private int coins;
  private boolean isInPenaltyBox;

  public Player(String name) {
    this.name = name;
  }

  public void rollPosition(int roll, int totalNumberOfPositions) {
    this.position += roll;
    if (this.position > totalNumberOfPositions - 1) {
      this.position -= totalNumberOfPositions;
    }
  }


  public String getName() {
    return name;
  }

  public int getPosition() {
    return position;
  }

  public int getCoins() {
    return coins;
  }

  public boolean isInPenaltyBox() {
    return isInPenaltyBox;
  }

  public void setInPenaltyBox(boolean inPenaltyBox) {
    isInPenaltyBox = inPenaltyBox;
  }

  void addCoin() {
      coins++;
  }

  public void getState() {
    System.out.println(this.getName()
            + " now has "
            + this.getCoins()
            + " Gold Coins.");
  }
}
