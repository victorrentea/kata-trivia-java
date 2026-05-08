import random
from trivia.game_old import GameOld
# Yahir Hernandez

# DON'T TOUCH THIS FILE. DON'T REFACTOR THIS FILE.
# ONLY RUN IT TO MANUALLY PLAY THE GAME YOURSELF TO UNDERSTAND THE PROBLEM


def read_yes_no():
    yn = input().strip().upper()
    if yn not in ("Y", "N"):
        print("y or n please", flush=True)
        return read_yes_no()
    return yn == "Y"


def read_roll():
    roll_str = input(">> Throw a die and input roll, or [ENTER] to generate a random roll: ").strip()
    if roll_str == "":
        roll = random.randint(1, 6)
        print(f">> Random roll: {roll}")
        return roll
    if not roll_str.isdigit():
        print(f"Not a number: '{roll_str}'")
        return read_roll()
    roll = int(roll_str)
    if roll < 1 or roll > 6:
        print("Invalid roll")
        return read_roll()
    return roll


def main():
    print("*** Welcome to Trivia Game ***\n")
    player_count = int(input("Enter number of players: 1-4\n"))
    if player_count < 1 or player_count > 4:
        raise ValueError("No player 1..4")

    print(f"Reading names for {player_count} players:")

    game = GameOld()

    for i in range(1, player_count + 1):
        player_name = input(f"Player {i} name: ")
        game.add(player_name)

    print("\n\n--Starting game--")

    not_a_winner = False
    while True:
        roll = read_roll()
        game.roll(roll)

        correct = input(">> Was the answer correct? [y/n] ")
        correct = correct.strip().upper() == "Y"

        if correct:
            not_a_winner = game.handle_correct_answer()
        else:
            not_a_winner = game.wrong_answer()

        if not not_a_winner:
            break

    print(">> Game won!")


if __name__ == "__main__":
    main()