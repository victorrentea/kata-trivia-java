package trivia.models;

import lombok.Data;

/**
 * The Player class represents player
 * for Trivia quiz.
 *
 * @author Igor Radovanovic
 * @version 1.0
 * @since 2022-02-24
 */
@Data
public class Player {

    private final String name;
    private int place = 0;
    private int coins = 0;
    private boolean inPenaltyBox = false;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Constructor is filling up Map with dummy values for the questions
     */
    public void move(int roll) {
        place += roll;
        if (place > 11) {
            place -= 12;
        }
    }
}
