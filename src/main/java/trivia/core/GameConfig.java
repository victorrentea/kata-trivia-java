package trivia.core;

public final class GameConfig {
    public static final short PLAYER_MAX_COUNT = 6;
    public static final short PLAYER_MIN_COIN_WIN_COUNT = 6;
    public static final short TOTAL_QUESTIONS = 50;
    public static final short TOTAL_QUESTION_TYPE_PER_BOARD = 3;
    public static final String TEXT_FORMAT_QUESTION_BODY = "%s Question %d";
    public static final String TEXT_FORMAT_CURRENT_PLAYER_COINS = "Answer was correct!!!!\n%s now has %d Gold Coins.";
    public static final String TEXT_FORMAT_ANSWER_WAS_WRONG = "Question was incorrectly answered\n%s was sent to the penalty box\n";
    public static final String TEXT_FORMAT_ROLL = "%s is the current player\nThey have rolled a %d\n";
    public static final String TEXT_FORMAT_ROLL_END = "%s's new location is %d\nThe category is %s\n";
    public static final String TEXT_FORMAT_PENALTY_OUT = "%s is getting out of the penalty box\n";
    public static final String TEXT_FORMAT_PENALTY_IN = "%s is not getting out of the penalty box\n";

}
