from collections import deque
# Yahir Hernandez

class GameOld:
    def __init__(self):
        self.players = []
        self.places = [0] * 6
        self.purses = [0] * 6
        self.in_penalty_box = [False] * 6

        self.pop_questions = deque()
        self.science_questions = deque()
        self.sports_questions = deque()
        self.rock_questions = deque()

        self.current_player = 0
        self.is_getting_out_of_penalty_box = False

        for i in range(50):
            self.pop_questions.append(f"Pop Question {i}")
            self.science_questions.append(f"Science Question {i}")
            self.sports_questions.append(f"Sports Question {i}")
            self.rock_questions.append(self.create_rock_question(i))

    def create_rock_question(self, index):
        return f"Rock Question {index}"

    def is_playable(self):
        return self.how_many_players() >= 2

    def add(self, player_name):
        self.places[self.how_many_players()] = 1
        self.purses[self.how_many_players()] = 0
        self.in_penalty_box[self.how_many_players()] = False
        self.players.append(player_name)

        print(f"{player_name} was added")
        print(f"They are player number {len(self.players)}")
        return True

    def how_many_players(self):
        return len(self.players)

    def roll(self, roll):
        print(f"{self.players[self.current_player]} is the current player")
        print(f"They have rolled a {roll}")

        if self.in_penalty_box[self.current_player]:
            if roll % 2 != 0:
                self.is_getting_out_of_penalty_box = True

                print(f"{self.players[self.current_player]} is getting out of the penalty box")
                self.places[self.current_player] += roll
                if self.places[self.current_player] > 12:
                    self.places[self.current_player] -= 12

                print(f"{self.players[self.current_player]}'s new location is {self.places[self.current_player]}")
                print(f"The category is {self._current_category()}")
                self._ask_question()
            else:
                print(f"{self.players[self.current_player]} is not getting out of the penalty box")
                self.is_getting_out_of_penalty_box = False
        else:
            self.places[self.current_player] += roll
            if self.places[self.current_player] > 12:
                self.places[self.current_player] -= 12

            print(f"{self.players[self.current_player]}'s new location is {self.places[self.current_player]}")
            print(f"The category is {self._current_category()}")
            self._ask_question()

    def _ask_question(self):
        category = self._current_category()
        if category == "Pop":
            print(self.pop_questions.popleft())
        if category == "Science":
            print(self.science_questions.popleft())
        if category == "Sports":
            print(self.sports_questions.popleft())
        if category == "Rock":
            print(self.rock_questions.popleft())

    def _current_category(self):
        pos = self.places[self.current_player] - 1
        if pos in (0, 4, 8):
            return "Pop"
        if pos in (1, 5, 9):
            return "Science"
        if pos in (2, 6, 10):
            return "Sports"
        return "Rock"

    def handle_correct_answer(self):
        if self.in_penalty_box[self.current_player]:
            if self.is_getting_out_of_penalty_box:
                print("Answer was correct!!!!")
                self.purses[self.current_player] += 1
                print(f"{self.players[self.current_player]} now has {self.purses[self.current_player]} Gold Coins.")

                winner = self._did_player_win()
                self.current_player += 1
                if self.current_player == len(self.players):
                    self.current_player = 0

                return winner
            else:
                self.current_player += 1
                if self.current_player == len(self.players):
                    self.current_player = 0
                return True
        else:
            print("Answer was correct!!!!")
            self.purses[self.current_player] += 1
            print(f"{self.players[self.current_player]} now has {self.purses[self.current_player]} Gold Coins.")

            winner = self._did_player_win()
            self.current_player += 1
            if self.current_player == len(self.players):
                self.current_player = 0

            return winner

    def wrong_answer(self):
        print("Question was incorrectly answered")
        print(f"{self.players[self.current_player]} was sent to the penalty box")
        self.in_penalty_box[self.current_player] = True

        self.current_player += 1
        if self.current_player == len(self.players):
            self.current_player = 0
        return True

    def _did_player_win(self):
        return not (self.purses[self.current_player] == 6)