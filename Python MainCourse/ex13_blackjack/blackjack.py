"""Blackjack."""
import importlib
import os
import pkgutil

from deck import Deck, Card
from game_view import GameView, FancyView, Move
from strategy import Strategy, HumanStrategy


class Hand:
    """Hand."""

    def __init__(self, cards: list = None):
        """Init."""
        if cards:
            self.cards = cards
        else:
            self.cards = []
        self.is_double_down = False
        self.is_surrendered = False
        self.split_card = ""
        self.soft_hand = False

    def add_card(self, card: Card) -> None:
        """Add card to hand."""
        self.cards.append(card)

    def double_down(self, card: Card) -> None:
        """Double down."""
        self.add_card(card)
        self.is_double_down = True

    def split(self):
        """Split hand."""
        if self.can_split is True:
            self.cards.remove(self.split_card)
            return Hand([self.split_card])
        raise ValueError("Invalid hand to split!")

    @property
    def can_split(self) -> bool:
        """Check if hand can be split."""
        score = self.score
        if len(self.cards) == 2 and self.cards[0].blackjack_value == self.cards[1].blackjack_value and score:
            self.split_card = self.cards[0]
            return True
        return False

    @property
    def is_blackjack(self) -> bool:
        """Check if is blackjack."""
        if len(self.cards) == 2 and self.score == 21:
            return True
        return False

    @property
    def is_soft_hand(self):
        """Check if is soft hand."""
        if self.soft_hand is True:
            return True
        return False

    @property
    def score(self) -> int:
        """Get score of hand."""
        worth_ten = ['10', 'JACK', 'QUEEN', 'KING']
        hand_score = 0
        for i in self.cards:
            if i.value == "ACE":
                continue
            elif i.value in worth_ten:
                hand_score += 10
                i.blackjack_value = 10
            else:
                hand_score += int(i.value)
                i.blackjack_value = int(i.value)
        for i in self.cards:
            if i.value == "ACE":
                if hand_score + 11 > 21:
                    hand_score += 1
                    i.blackjack_value = 1
                else:
                    hand_score += 11
                    i.blackjack_value = 11
                    self.soft_hand = True
        return hand_score


class Player:
    """Player."""

    def __init__(self, name: str, strategy: Strategy, coins: int = 100):
        """Init."""
        self.name = name
        self.strategy = strategy
        self.strategy.player = self
        self.coins = coins
        self.bet = 0
        self.hands = []

    def join_table(self):
        """Join table."""
        self.hands = []
        self.hands.append(Hand())

    def play_move(self, hand: Hand) -> Move:
        """Play move."""
        return self.strategy.play_move(hand)

    def split_hand(self, hand: Hand) -> None:
        """Split hand."""
        self.hands.append(hand)


class GameController:
    """Game controller."""

    PLAYER_START_COINS = 200
    BUY_IN_COST = 10

    def __init__(self, view: GameView):
        """Init."""
        self.view = view
        self.players = []
        self.house = Hand()
        self.deck = None
        self.house_busted = False
        self.last_move = ""

    def _draw_card(self, top_down: bool = False) -> Card:
        """Draw card."""
        return self.deck.draw_card(top_down)

    @staticmethod
    def load_strategies() -> list:
        """
        Load strategies.

        @:return list of strategies that are in same package.
        DO NOT EDIT!
        """
        pkg_dir = os.path.dirname(__file__)
        for (module_loader, name, is_pkg) in pkgutil.iter_modules([pkg_dir]):
            importlib.import_module(name)
        return list(filter(lambda x: x.__name__ != HumanStrategy.__name__, Strategy.__subclasses__()))

    def start_game(self) -> None:
        """Start game, ask neccesary and makes players."""
        players_count = self.view.ask_players_count()
        bots_count = self.view.ask_bots_count()
        decks_count = self.view.ask_decks_count()
        self.deck = Deck(decks_count)
        for i in range(players_count):
            name = self.view.ask_name(i)
            self.players.append(Player(name, HumanStrategy(self.players, self.house, decks_count, self.view),
                                       GameController.PLAYER_START_COINS))
        strategies = self.load_strategies()
        for i in range(bots_count):
            name = f"Bot nr{i}"
            self.players.append(Player(name, strategies[0], GameController.PLAYER_START_COINS))

    def move_split(self, player, hand):
        """Do the necessary."""
        try:
            new_hand = hand.split()
            hand.add_card(self._draw_card())
            new_hand.add_card(self._draw_card())
            player.hands.append(new_hand)
            return False
        except ValueError:
            return True

    def decide_moves(self, move, player, hand):
        """Decide what happens."""
        if move == Move.SPLIT and player.coins >= GameController.BUY_IN_COST:
            player.coins -= GameController.BUY_IN_COST
            player.bet += GameController.BUY_IN_COST
            new_card_needed = self.move_split(player, hand)  # also checks, if the hand can be split or not
            self.last_move = "SPLIT"
            if new_card_needed is True:
                move = self.view.ask_move()
        if move == Move.HIT:
            self.last_move = "HIT"
            hand.add_card(self._draw_card())
        elif move == Move.STAND:
            self.last_move = "STAND"
        elif move == Move.DOUBLE_DOWN:
            player.coins -= GameController.BUY_IN_COST
            player.bet += GameController.BUY_IN_COST
            hand.add_card(self._draw_card())
            hand.is_double_down = True
        elif move == Move.SURRENDER:
            hand.is_surrendered = True

    def house_moves(self):
        """Draw cards for house."""
        if self.house.is_soft_hand is True:
            while self.house.score <= 17:
                self.house.add_card(self._draw_card(True))
        elif self.house.is_soft_hand is False:
            while self.house.score <= 18:
                self.house.add_card(self._draw_card(True))
        if self.house.score > 21:
            self.house_busted = True

    def action(self, table):
        """Action. Asks moves and does what is needed for those moves."""
        for player in table:
            for hand in player.hands:
                self.view.show_table(table, self.house, hand)
                if hand.is_surrendered is False and hand.is_double_down is False:
                    move = self.view.ask_move()
                    self.decide_moves(move, player, hand)
                if self.last_move == "SPLIT":
                    move = self.view.ask_move()
                    self.decide_moves(move, player, hand)
        self.house_moves()

    def share_coins(self, player, hand):
        """Determine how much coins each player gets."""
        if hand.is_surrendered is True:
            player.coins += GameController.BUY_IN_COST // 2
        elif hand.is_blackjack is True:
            player.coins += player.bet + player.bet * 1.5
        elif hand.score <= 21 and hand.score == self.house.score:
            player.coins += player.bet
        elif 21 >= hand.score > self.house.score:
            player.coins += player.bet * 2

    def decision(self, table):
        """Descision. Shares coins if someone won or surrendered etc."""
        for i in self.house.cards:
            i.top_down = False  # reveals house cards
        for player in table:
            for hand in player.hands:
                if self.house_busted is False:
                    self.view.show_table(table, self.house, hand)
                    self.share_coins(player, hand)
                elif hand.score <= 21:
                    player.coins += player.bet * 2
                    break

    def join_players(self):
        """Add players to table, if they have enough coins."""
        table = []
        for player in self.players:
            if player.coins >= GameController.BUY_IN_COST:
                table.append(player)
                player.join_table()
                player.coins -= GameController.BUY_IN_COST
                player.bet = GameController.BUY_IN_COST
        return table

    def draw_first_two_cards(self, table):
        """Draw the starting cards to the players and house."""
        for i in range(2):
            for player in table:
                player.hands[0].add_card(self._draw_card())
            self.house.add_card(self._draw_card(True))

    def play_round(self) -> bool:
        """Play round."""
        table = self.join_players()
        self.draw_first_two_cards(table)
        self.action(table)
        self.decision(table)
        return True


if __name__ == '__main__':
    game_controller = GameController(FancyView())
    game_controller.start_game()
    game_controller.play_round()
