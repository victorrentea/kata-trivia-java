package trivia.models;

import trivia.types.QuestionCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Questions class represents questions
 * for Trivia quiz.
 *
 * @author Igor Radovanovic
 * @version 1.0
 * @since 2022-02-24
 */
public class Questions {

    private final Map<QuestionCategory, List<String>> questions = new HashMap<>();

    /**
     * Constructor is filling up Map with dummy values for the questions
     */
    public Questions() {
        for (QuestionCategory questionCategory : QuestionCategory.values()) {
            questions.put(questionCategory, new ArrayList<>());
        }
        for (int i = 0; i < 50; i++) {
            questions.get(QuestionCategory.POP).add("Pop Question " + i);
            questions.get(QuestionCategory.SCIENCE).add("Science Question " + i);
            questions.get(QuestionCategory.SPORTS).add("Sports Question " + i);
            questions.get(QuestionCategory.ROCK).add("Rock Question " + i);

        }
    }

    /**
     * This method displays current Question and removes it from the List
     */
    public String showNextQuestion(QuestionCategory questionCategory) {
        List<String> categoryQuestions = questions.get(questionCategory);
        String question = categoryQuestions.get(0);
        categoryQuestions.remove(0);
        return question;
    }

    /**
     * This method checks for the CategoryType
     */
    public QuestionCategory getCurrentCategory(int step) {
        if (step == 0 || step == 4 || step == 8) {
            return QuestionCategory.POP;
        } else if (step == 1 || step == 5 || step == 9) {
            return QuestionCategory.SCIENCE;
        } else if (step == 2 || step == 6 || step == 10) {
            return QuestionCategory.SPORTS;
        } else if (step == 3 || step == 7 || step == 11) {
            return QuestionCategory.ROCK;
        }
        return null;
    }

}
