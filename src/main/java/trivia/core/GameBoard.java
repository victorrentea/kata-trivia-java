package trivia.core;

import trivia.objects.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class GameBoard {
    private final Map<Integer, Category> boardCategoryMap = new HashMap<>();
    private final int[] playerPositions = new int[GameConfig.PLAYER_MAX_COUNT];

    public int boardSize = 0;
    public int totalPlayers = 0;

    public GameBoard() {
        buildBoard();
    }

    public void addPlayer(String playerName) {

        System.out.println(playerName + " was added");
    }

    public int getPlayerCurrentPosition(int playerIndex) {
        return this.playerPositions[playerIndex];
    }

    public void updatePlayerPosition(int playerIndex, int playerMovement) {
        this.playerPositions[playerIndex] = this.playerPositions[playerIndex] + playerMovement;
        if (this.playerPositions[playerIndex] > 11) this.playerPositions[playerIndex] = this.playerPositions[playerIndex] - 12;
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
}
