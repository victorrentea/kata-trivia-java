from collections import deque
from trivia.player import Player
from trivia.question_deck import QuestionDeck
# Yahir Hernandez
BOARD_SIZE = 12
WINNING_COINS = 6
QUESTIONS_PER_CATEGORY = 50
MAX_PLAYERS = 6

# REFACTOR ME
class Game:
    def __init__(self):
        self.players = []

        self.pop_questions = deque()
        self.science_questions = deque()
        self.sports_questions = deque()
        self.rock_questions = deque()

        self.current_player = 0
        self.is_getting_out_of_penalty_box = False
        self.deck = QuestionDeck()

    def create_rock_question(self, index):
        return f"Rock Question {index}"

    def has_enough_players(self):
        return self.how_many_players() >= 2

    def add(self, player_name):
        player = Player(player_name)
        self.players.append(player)
        print(f"{player_name} was added")
        print(f"They are player number {len(self.players)}")
        return True

    def how_many_players(self):
        return len(self.players)

    def roll(self, roll: int):
        player = self.players[self.current_player]
        print(f"{player.name} is the current player")
        print(f"They have rolled a {roll}")

        if player.in_penalty_box:
            self._handle_penalty_box_turn(player, roll)
        else:
            self._handle_normal_turn(player, roll)

    def _handle_normal_turn(self, player, roll: int):
        player.advance(roll)
        category = self._current_category()
        print(f"{player.name}'s new location is {player.position}")
        print(f"The category is {category}")
        print(self.deck.next_question(category))

    def _handle_penalty_box_turn(self, player, roll: int):
        if roll % 2 != 0:
            self.is_getting_out_of_penalty_box = True
            print(f"{player.name} is getting out of the penalty box")
            self._handle_normal_turn(player, roll)
        else:
            print(f"{player.name} is not getting out of the penalty box")
            self.is_getting_out_of_penalty_box = False

    def _ask_question(self):
        category = self._current_category()
        print(self.deck.next_question(category))

    def _current_category(self):
        pos = self.players[self.current_player].position - 1
        if pos in (0, 4, 8):
            return "Pop"
        if pos in (1, 5, 9):
            return "Science"
        if pos in (2, 6, 10):
            return "Sports"
        # if pos in (3, 7, 11):  
        #     return "Geography"
        return "Rock"
    
    def _advance_turn(self):
        self.current_player += 1
        if self.current_player == len(self.players):
            self.current_player = 0

    def handle_correct_answer(self):
        player = self.players[self.current_player]
        if self.players[self.current_player].in_penalty_box:
            if self.is_getting_out_of_penalty_box:
                print("Answer was correct!!!!")
                player.add_coin()
                print(f"{self.players[self.current_player].name} now has {self.players[self.current_player].coins} Gold Coins.")

                winner = self._did_player_win()
                self._advance_turn()

                return winner
            else:
                self._advance_turn()
                return True
        else:
            print("Answer was correct!!!!")
            player.add_coin()
            print(f"{self.players[self.current_player].name} now has {self.players[self.current_player].coins} Gold Coins.")

            winner = self._did_player_win()
            self._advance_turn()

            return winner

    def wrong_answer(self):
        player = self.players[self.current_player]
        print("Question was incorrectly answered")
        print(f"{self.players[self.current_player].name} was sent to the penalty box")
        player.send_to_penalty_box()

        self._advance_turn()
        return True

    def _did_player_win(self):
        player = self.players[self.current_player]
        return not (player.has_won())