package trivia.core;

import trivia.objects.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class GameBoard {
    private final Map<Integer, Category> boardCategoryMap = new HashMap<>();

    public GameBoard() {
        buildBoard();
    }

    private void buildBoard() {
        Category[] categories = Category.values();

        int boardSize = categories.length * GameConfig.TOTAL_QUESTION_TYPE_PER_BOARD;
        final AtomicInteger boardIndex = new AtomicInteger(0);
        IntStream.range(0, boardSize).forEachOrdered(n -> {
            for (Category category : Category.values()) {
                this.boardCategoryMap.put(boardIndex.getAndIncrement(), category);
            }
        });
    }

    public Category getBoardCategoryByIndex(int index) {
        return boardCategoryMap.get(index);
    }


}
