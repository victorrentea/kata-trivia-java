BOARD_SIZE = 12
WINNING_COINS = 6
#Yahir Hernandez

class Player:
    def __init__(self, name: str):
        self.name = name
        self.position = 1
        self.coins = 0
        self.in_penalty_box = False

    def advance(self, roll: int):
        self.position += roll
        if self.position > BOARD_SIZE:
            self.position -= BOARD_SIZE

    def add_coin(self):
        self.coins += 1

    def send_to_penalty_box(self):
        self.in_penalty_box = True

    def has_won(self) -> bool:
        return self.coins == WINNING_COINS