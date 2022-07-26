package trivia.cr;


import trivia.GameBetter;
import trivia.SubjectFactory;

public class GameBetterCR extends GameBetter {

	@Override
	protected void createSubjects() {
		super.createSubjects();
		this.subjectsInGame.add(new SubjectFactory().createSubject(GEOGRAPHY));
	}

	public boolean isPlayable() {
		return (players.howManyPlayers() >= MIN_NUMBER_OF_PLAYERS);
	}

	@Override
	public boolean add(String playerName) {

		if (players.isPlayerNameValid(playerName)) {
			players.addPlayer(playerName);
			System.out.println(playerName + " was added");
			System.out.println("They are player number " + players.howManyPlayers());

			return true;
		}

		System.out.println("The player " + playerName + " already exists!");
		return false;

	}
	
	@Override
	public void roll(int roll) {
		if (players.getCurrentPlayer().hadSecondChance()) {
			askQuestion();
		} else {
			super.roll(roll);
		}
	}

	@Override
	protected boolean isWinner() {
		manageStreak();
		updatePurse();

		boolean winner = didPlayerWin();
		players.changePlayersTurn();

		return winner;
	}

	private void manageStreak() {
		players.getCurrentPlayer().addOneConsecutiveCorrectAnswers();

		if (players.getCurrentPlayer().getConsecutiveCorrectAnswers() == NUMBER_OF_QUESTIONS_FOR_STREAK) {
			players.getCurrentPlayer().setOnStreak(true);
		}
	}

	@Override
	public boolean wrongAnswer() {

		if (!players.getCurrentPlayer().isInPenaltyBox()) {

			System.out.println("Question was incorrectly answered");

			players.getCurrentPlayer().resetNumberOfQuestionsForStreak();

			if (players.getCurrentPlayer().isOnStreak()) {
				updateStreak();
			} else {
				manageSecondChanceOnWrongAnswer();
			}
		} else {
			System.out.println("Player is already in penalty box!");
			players.changePlayersTurn();
		}

		return true;
	}

	private void updateStreak() {
		players.getCurrentPlayer().setOnStreak(false);
		System.out.println(players.getCurrentPlayerName() + " lost his streak");

		players.changePlayersTurn();
	}

	private void manageSecondChanceOnWrongAnswer() {
		if (players.getCurrentPlayer().hadSecondChance()) {
			players.getCurrentPlayer().setPenaltyBox(true);
			System.out.println(players.getCurrentPlayerName() + " was sent to the penalty box");
			players.getCurrentPlayer().setSecondChance(false);

			players.changePlayersTurn();
		} else {
			players.getCurrentPlayer().setSecondChance(true);
		}
	}

	private void manageSecondChanceOnCorrectAnswer() {
		System.out.println(players.getCurrentPlayerName() + " avoided penalty box");
		players.getCurrentPlayer().setSecondChance(false);
		players.changePlayersTurn();
	}

	@Override
	protected boolean didPlayerWin() {
		return !(players.getCurrentPlayer().getPurse() >= MAX_NUMBER_OF_COINS * 2);
	}

	@Override
    public boolean wasCorrectlyAnswered() {
        if (players.getCurrentPlayer().isInPenaltyBox() && !players.getCurrentPlayer().isGettingOutOfPenaltyBox()) {
            players.changePlayersTurn();
            return true;
        } else if (players.getCurrentPlayer().hadSecondChance()) {
            manageSecondChanceOnCorrectAnswer();
        }

        return isWinner();
    }
}
