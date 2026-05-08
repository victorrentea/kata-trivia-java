from collections import deque

QUESTIONS_PER_CATEGORY = 50


class QuestionDeck:
    # CATEGORIES = ["Pop", "Science", "Sports", "Rock", "Geography"]
    CATEGORIES = ["Pop", "Science", "Sports", "Rock"]


    def __init__(self):
        self._questions = {
            category: deque(
                f"{category} Question {i}"
                for i in range(QUESTIONS_PER_CATEGORY)
            )
            for category in self.CATEGORIES
        }

    def next_question(self, category: str) -> str:
        return self._questions[category].popleft()