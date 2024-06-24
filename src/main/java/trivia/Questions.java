package trivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Questions {

    public static final int NUMBER_OF_CATEGORIES = 4;

    Map<String, LinkedList<String>> questionsMap =
            new HashMap<>(NUMBER_OF_CATEGORIES);




    public Questions(){
        for (Category category : Category.values()) {
            questionsMap.put(category.name(), new LinkedList<>());
            for (int i = 0; i < 50; i++) {
                questionsMap.get(category.name()).add(createQuestion(i, category.name()));
            }
        }

    }

    public String getQuestion(String category){
        return questionsMap.get(category).removeFirst();
    }

    public String createQuestion(int index, String category){
        return category + " Question " + index;
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public String createPopQuestion(int index) {
        return "Pop Question " + index;
    }

    public String createSportsQuestion(int index){
        return "Sports Question " + index;
    }

    public String createScienceQuestion(int index){
        return "Science Question " + index;
    }


}
