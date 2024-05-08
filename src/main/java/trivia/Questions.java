package trivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Questions {

    public static final int NUMBER_OF_CATEGORIES = 4;

    private final Map<Category, LinkedList<String>> questionsMap =
            new HashMap<>(NUMBER_OF_CATEGORIES);




    public Questions(){
        for (Category category : Category.values()) {
            questionsMap.put(category, new LinkedList<>());
            for (int i = 0; i < 50; i++) {
                questionsMap.get(category).add(createQuestion(i, category.name()));
            }
        }

    }

    public String getQuestion(Category category){
        return questionsMap.get(category).removeFirst();
    }

    public String createQuestion(int index, String category){
        return category + " Question " + index;
    }

}
