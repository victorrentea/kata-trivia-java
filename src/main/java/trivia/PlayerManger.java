package trivia;

public interface PlayerManger {
    Player currentPlayer();

    String getCurrentPlayerName();

    boolean add(String playerName);

    int howManyPlayers();

    void nextPlayer();

    boolean didPlayerWin();

    boolean isCurrentPlayerInPenaltyBox();

    int getCurrentPlayerLocation();

    void getCurrentPlayerState();
}
