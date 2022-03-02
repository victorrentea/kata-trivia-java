package trivia.types;

/**
 * The Enum class represents questions
 * types of Question Category
 *
 * @author Igor Radovanovic
 * @version 1.0
 * @since 2022-02-24
 */
public enum QuestionCategory {

    POP("Pop"),
    ROCK("Rock"),
    SCIENCE("Science"),
    SPORTS("Sports");

    private final String label;

    QuestionCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
