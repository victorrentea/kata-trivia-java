package main.java.trivia;

import java.util.ArrayList;

public class Players {

	private ArrayList<Player> players;
	private int currentPlayerIndex;

	public Players() {
		this.players = new ArrayList<>();
		this.currentPlayerIndex = 0;
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public String getCurrentPlayerName() {
		return getCurrentPlayer().getName();
	}

	public int howManyPlayers() {
		return players.size();
	}

	public boolean isPlayerNameValid(String name) {
		for (Player player : players) {
			if (player.getName().equals(name))
				return false;
		}
		return true;
	}

	public void addPlayer(String playerName) {
		players.add(new Player(playerName));
	}

	public void changePlayersTurn() {
		currentPlayerIndex++;

		if (currentPlayerIndex == howManyPlayers()) {
			currentPlayerIndex = 0;
		}
	}

	public void updatePlayerPosition(int roll) {
		getCurrentPlayer().updatePosition(roll);
	}

}
