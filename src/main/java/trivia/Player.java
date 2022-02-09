package trivia;

import static trivia.GameBetter.NUMBER_OF_CELLS;

public class Player {
   private final String name;
   private boolean isInPenaltyBox = false;
   private int place = 0;
   private int purse = 0;

   public Player(String name) {
      this.name = name;
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

   public void setPlace(int place) {
      this.place = place;
   }

   public void roll(int roll) {
      place += roll;
      if (place >= NUMBER_OF_CELLS) {
         place -= NUMBER_OF_CELLS;
      }
   }

   public void addCoin() {
      purse++;
   }


   @Override
   public String toString() {
      return name;
   }
}
