package trivia;

import static trivia.GameBetter.NUMBER_OF_CELLS;

public class Player {
   private String name;
   private boolean isInPenaltyBox = false;
   private int place = 0;
   private int coins = 0;

   public Player(String name) {
      this.name = name;
   }

   public void addCoin() {
      coins++;
   }

   public boolean isInPenaltyBox() {
      return isInPenaltyBox;
   }

   public void setInPenaltyBox(boolean inPenaltyBox) {
      isInPenaltyBox = inPenaltyBox;
   }

   public int getPlace() {
      return place;
   }

   public int getCoins() {
      return coins;
   }


   public boolean didPlayerWin() {
      return !(coins == 6);
   }


   public void moveToPlace(int roll) {
      place += roll;
      if (place >= NUMBER_OF_CELLS) {
         place -= NUMBER_OF_CELLS;
      }
   }

   @Override
   public String toString() {
      return name;
   }
}
