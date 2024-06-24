package trivia;

import java.util.ArrayList;
import java.util.List;

public class PlayerMangerImpl implements PlayerManger {
    private int currentPlayerIndex;
    private final List<Player> players = new ArrayList<>();

    @Override
    public Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    @Override
    public String getCurrentPlayerName(){
        return currentPlayer().getName();
    }

    @Override
    public boolean add(String playerName) {
        players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    @Override
    public int howManyPlayers() {
        return players.size();
    }

    @Override
    public void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
    }


    @Override
    public boolean didPlayerWin() {
        return currentPlayer().getCoins() != 6;
    }

    @Override
    public boolean isCurrentPlayerInPenaltyBox() {
        return currentPlayer().isInPenaltyBox();
    }

    @Override
    public int getCurrentPlayerLocation() {
        return currentPlayer().getPosition();
    }
}
