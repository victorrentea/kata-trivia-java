package trivia.objects;

public enum Category {
    POP,
    SCIENCE,
    SPORTS,
    ROCK,
    GEOGRAPHY;

    @Override
    public String toString() {
        String typeName = name();
        return typeName.substring(0, 1).toUpperCase() + typeName.substring(1).toLowerCase();
    }

}
