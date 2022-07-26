package trivia;

import java.util.ArrayList;
import java.util.List;

public class GameBetter implements IGame, Constants {

	protected Players players = new Players();
	protected List<Subject> subjectsInGame = new ArrayList<Subject>();

	public GameBetter() {
		createSubjects();
		for (int i = 0; i < NUMBER_OF_QUESTIONS_PER_SUBJECT; i++) {
			for (Subject s : subjectsInGame) {
				s.addQuestion(i);
			}
		}
	}
	
	protected void createSubjects() {
		SubjectFactory factory = new SubjectFactory();
		subjectsInGame.add(factory.createSubject(POP));
		subjectsInGame.add(factory.createSubject(SCIENCE));
		subjectsInGame.add(factory.createSubject(SPORTS));
		subjectsInGame.add(factory.createSubject(ROCK));
	}

	public boolean add(String playerName) {
		players.addPlayer(playerName);

		System.out.println(playerName + " was added");
		System.out.println("They are player number " + players.howManyPlayers());
		return true;
	}

	public void roll(int roll) {
		System.out.println(players.getCurrentPlayerName() + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (players.getCurrentPlayer().isInPenaltyBox()) {

			if (roll % 2 != 0) {

				updatePenaltyBoxStatus(true);
				changePlayerPosition(roll);
				askQuestion();

				players.getCurrentPlayer().setPenaltyBox(false);

			} else {
				updatePenaltyBoxStatus(false);
			}

		} else {
			changePlayerPosition(roll);
			askQuestion();
		}
	}

	protected void updatePenaltyBoxStatus(boolean value) {
		players.getCurrentPlayer().setGettingOutOfPenaltyBox(value);

		if (players.getCurrentPlayer().isGettingOutOfPenaltyBox()) {
			System.out.println(players.getCurrentPlayerName() + " is getting out of the penalty box");
		} else {
			System.out.println(players.getCurrentPlayerName() + " is not getting out of the penalty box");
		}
	}

	protected void changePlayerPosition(int roll) {
		players.updatePlayerPosition(roll);

		System.out.println(
				players.getCurrentPlayerName() + "'s new location is " + players.getCurrentPlayer().getPosition());
		System.out.println("The category is " + getCurrentCategory());
	}

	protected void askQuestion() {
		for (Subject s : subjectsInGame) {
			s.askQuestionAccordingToPosition(players.getCurrentPlayer().getPosition());
		}
	}

	protected String getCurrentCategory() {
		for (Subject s : subjectsInGame) {
			if (s.isPlaceFromSubject(players.getCurrentPlayer().getPosition())) {
				return s.getName();
			}
		}
		return null;
	}

	public boolean wasCorrectlyAnswered() {
		if (players.getCurrentPlayer().isInPenaltyBox()) {
			if (players.getCurrentPlayer().isGettingOutOfPenaltyBox()) {
				return isWinner();
			} else {
				players.changePlayersTurn();
				return true;
			}
		} else {
			return isWinner();
		}
	}

	protected boolean isWinner() {
		updatePurse();

		boolean winner = didPlayerWin();
		players.changePlayersTurn();

		return winner;
	}

	protected void updatePurse() {
		System.out.println("Answer was correct!!!!");
		players.getCurrentPlayer().updatePurse();
		System.out.println(
				players.getCurrentPlayerName() + " now has " + players.getCurrentPlayer().getPurse() + " Gold Coins.");
	}

	public boolean wrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(players.getCurrentPlayerName() + " was sent to the penalty box");

		players.getCurrentPlayer().setPenaltyBox(true);

		players.changePlayersTurn();

		return true;
	}

	protected boolean didPlayerWin() {
		return !(players.getCurrentPlayer().getPurse() == MAX_NUMBER_OF_COINS);
	}

}
