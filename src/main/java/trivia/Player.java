package trivia;

public class Player implements Constants {

	private String name;
	private int position;
	private int purse;
	private boolean isInPenaltyBox;
	private boolean isOnStreak;
	private int consecutiveCorrectAnswers;
	private boolean isGettingOutOfPenaltyBox;
	private boolean hadSecondChance;

	Player(String name) {
		this.name = name;
		position = 0;
		purse = 0;
		isInPenaltyBox = false;
		isOnStreak = false;
		consecutiveCorrectAnswers = 0;
		isGettingOutOfPenaltyBox = false;
	}

	public void updatePosition(int roll) {
		position += roll;

		if (position > NUMBER_OF_BOARD_POSITIONS - 1)
			position -= NUMBER_OF_BOARD_POSITIONS;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public int getPurse() {
		return purse;
	}

	public void updatePurse() {
		if (isOnStreak) {
			this.purse += 2;
		} else {
			this.purse++;
		}
	}

	public boolean isInPenaltyBox() {
		return isInPenaltyBox;
	}

	public void setPenaltyBox(boolean isInPenaltyBox) {
		this.isInPenaltyBox = isInPenaltyBox;
	}

	public boolean isOnStreak() {
		return isOnStreak;
	}

	public void setOnStreak(boolean isOnStreak) {
		this.isOnStreak = isOnStreak;
	}

	public int getConsecutiveCorrectAnswers() {
		return consecutiveCorrectAnswers;
	}

	public void addOneConsecutiveCorrectAnswers() {
		this.consecutiveCorrectAnswers++;
	}

	public void resetNumberOfQuestionsForStreak() {
		this.consecutiveCorrectAnswers = 0;
	}

	public boolean isGettingOutOfPenaltyBox() {
		return isGettingOutOfPenaltyBox;
	}

	public void setGettingOutOfPenaltyBox(boolean isGettingOutOfPenaltyBox) {
		this.isGettingOutOfPenaltyBox = isGettingOutOfPenaltyBox;
	}

	public boolean hadSecondChance() {
        return hadSecondChance;
    }

    public void setSecondChance(boolean hadSecondChance) {
        this.hadSecondChance = hadSecondChance;
    }
}
