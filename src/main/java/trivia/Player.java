package trivia;

// @Value
public class Player {
   private final String name;
   private int place = 0;
   private int coins = 0;
   private boolean inPenaltyBox = false;

   public Player(String name) {
      this.name = name;
   }

   public String name() {
      return name;
   }

   public int place() {
      return place;
   }

   public void move(int roll) {
      place += roll;
      if (place >= 12) {
         place -= 12;
      }
   }

   public void addCoin() {
      coins++;
   }

   public int coins() {
      return coins;
   }

   public void moveToPenaltyBox() {
      inPenaltyBox = true;
   }

   public boolean isInPenaltyBox() {
      return inPenaltyBox;
   }

   public boolean didPlayerWin() {
      return !(coins == 6); // WHAT THE HECK! ??? TODO: Possible BUG
   }
}
