import io
import random
import sys
import pytest
# Yahir Hernández
from trivia.game_old import GameOld
from trivia.game import Game


def extract_output(rand: random.Random, game) -> str:
    old_stdout = sys.stdout
    sys.stdout = buffer = io.StringIO()

    try:
        game.add("Chet")
        game.add("Pat")
        game.add("Sue") 

        not_a_winner = False
        while True:
            game.roll(rand.randint(1, 5))

            if rand.randint(0, 8) == 7:
                not_a_winner = game.wrong_answer()
            else:
                not_a_winner = game.handle_correct_answer()

            if not not_a_winner:
                break
    finally:
        sys.stdout = old_stdout

    return buffer.getvalue()


def run_seed(seed: int, print_expected: bool = False):
    expected_output = extract_output(random.Random(seed), GameOld())
    if print_expected:
        print(expected_output)
    actual_output = extract_output(random.Random(seed), Game())
    assert actual_output == expected_output, (
        f"Change detected for seed {seed}. "
        "To debug it, run test_one_seed with that seed."
    )


def test_caracterization():
    """Runs 10,000 random games to verify old and new code output match."""
    for seed in range(1, 10_000):
        run_seed(seed)


@pytest.mark.skip(reason="Enable and set a particular seed to see the output")
def test_one_seed():
    run_seed(1, print_expected=True)

# ─── Tests unitarios sobre código limpio ───────────────────────

from trivia.player import Player
from trivia.question_deck import QuestionDeck

def test_player_starts_with_zero_coins():
    player = Player("Chet")
    assert player.coins == 0

def test_player_has_not_won_at_start():
    player = Player("Chet")
    assert not player.has_won()

def test_player_wins_after_six_coins():
    player = Player("Chet")
    for _ in range(6):
        player.add_coin()
    assert player.has_won()

def test_player_advance_moves_position():
    player = Player("Pat")
    player.advance(3)
    assert player.position == 4  # empieza en 1, avanza 3

def test_player_advance_wraps_around_board():
    player = Player("Sue")
    player.position = 10
    player.advance(5)
    assert player.position == 3  # 10 + 5 = 15, 15 - 12 = 3

def test_player_sent_to_penalty_box():
    player = Player("Chet")
    player.send_to_penalty_box()
    assert player.in_penalty_box

def test_question_deck_has_all_categories():
    deck = QuestionDeck()
    assert "Pop" in deck._questions
    assert "Science" in deck._questions
    assert "Sports" in deck._questions
    assert "Rock" in deck._questions

def test_question_deck_returns_questions_in_order():
    deck = QuestionDeck()
    assert deck.next_question("Pop") == "Pop Question 0"
    assert deck.next_question("Pop") == "Pop Question 1"

def test_game_adds_players_correctly():
    game = Game()
    game.add("Chet")
    game.add("Pat")
    assert game.how_many_players() == 2

def test_game_needs_at_least_two_players():
    game = Game()
    game.add("Chet")
    assert not game.has_enough_players()
    game.add("Pat")
    assert game.has_enough_players()