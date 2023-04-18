package trivia.core;

import trivia.objects.Category;
import trivia.objects.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class GameBoard {
    private final Map<Integer, Category> boardCategoryMap = new HashMap<>();

    public int boardSize = 0;
    public int totalPlayers = 0;
    public final Map<Integer, Player> players = new HashMap<>();

    public GameBoard() {
        buildBoard();
    }

    public boolean addPlayer(String playerName) {
        this.players.put(this.totalPlayers, new Player(playerName));
        this.totalPlayers ++;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + this.totalPlayers);
        return true;
    }

    public Player getPlayer(int playerIndex) {
        return this.players.get(playerIndex);
    }

    public int getPlayerPosition(int playerIndex) {
        return this.players.get(playerIndex).position;
    }

    public String getPlayerName(int playerIndex) {
        return this.players.get(playerIndex).name;
    }

    public boolean isInPenalty(int playerIndex) {
        return this.players.get(playerIndex).hasPenalty;
    }

    public void setPenalty(int playerIndex, boolean value) {
        this.players.get(playerIndex).hasPenalty = value;
    }


    public void updatePlayerPosition(int playerIndex, int playerMovement) {
        Player player = this.players.get(playerIndex);

        player.position = this.resetPlayerPositionOnBoardRound(player.position + playerMovement);

    }

    public Category getBoardCategoryByIndex(int index) {
        return boardCategoryMap.get(index);
    }

    private void buildBoard() {
        Category[] categories = Category.values();

        this.boardSize = categories.length * GameConfig.TOTAL_QUESTION_TYPE_PER_BOARD;
        final AtomicInteger boardIndex = new AtomicInteger(0);
        IntStream.range(0, this.boardSize).forEachOrdered(n -> {
            for (Category category : Category.values()) {
                this.boardCategoryMap.put(boardIndex.getAndIncrement(), category);
            }
        });
    }

    private int resetPlayerPositionOnBoardRound(int position) {
        if (position > this.boardSize - 1) position -=  this.boardSize;
        return position;
    }


}
