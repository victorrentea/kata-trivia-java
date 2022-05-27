package trivia;

public enum QuestionCategory {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock"),
    ;

    private final String label;

    QuestionCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
