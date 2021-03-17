"""Deck."""
from typing import Optional, List
import requests


class Card:
    """Simple dataclass for holding card information."""

    def __init__(self, value: str, suit: str, code: str):
        """Constructor."""
        self.value = value
        self.suit = suit
        self.code = code
        self.top_down = False  # is the card facing downwards

    def __str__(self):
        """Str."""
        if self.top_down is False:
            return self.code
        return "??"

    def __repr__(self) -> str:
        """Repr."""
        return self.code

    def __eq__(self, o) -> bool:
        """Eq."""
        if self.value == o.value and self.suit == o.suit and self.code == o.code:
            return True
        return False


class Deck:
    """Deck."""

    DECK_BASE_API = "https://deckofcardsapi.com/api/deck/"

    def __init__(self, deck_count: int = 1, shuffle: bool = False):
        """Constructor."""
        self.is_shuffled = shuffle
        self.deck_count = deck_count
        self._backup_deck = self._generate_backup_pile(deck_count)
        self.remaining = 52 * deck_count
        if shuffle is True:
            self.deck = self._request(Deck.DECK_BASE_API + f"new/shuffle/?deck_count={self.deck_count}")
        else:
            self.deck = self._request(Deck.DECK_BASE_API + f"new/?deck_count={self.deck_count}")
        if self.deck:  # If the request got connection with the webpage server
            self.deck_id = self.deck['deck_id']
        else:
            self.deck_id = ""  # <---------- this does not matter much

    def shuffle(self) -> None:
        """Shuffle the deck."""
        self._request(Deck.DECK_BASE_API + f"{self.deck_id}/shuffle/")

    def draw_card(self, top_down: bool = False) -> Optional[Card]:
        """
        Draw card from the deck.

        :return: card instance.
        """
        if self.remaining <= 0:
            return None
        response = self._request(Deck.DECK_BASE_API + f"{self.deck_id}/draw/?count={self.deck_count}")
        if response:
            card = response['cards'][0]
            new_card = Card(card['value'], card['suit'], card['code'])
            self.remaining = response['remaining']
            if new_card in self._backup_deck:
                self._backup_deck.remove(new_card)
        else:
            new_card = self._backup_deck.pop(0)
            self.remaining = len(self._backup_deck)
        new_card.top_down = top_down
        return new_card

    def _request(self, url: str) -> dict:
        """Update deck, if possible return .json dict if not return empty dict."""
        try:  # Try to get a connection with the website
            response = requests.get(url)
            if response.status_code == requests.codes.ok:  # If the status_code is [200], not for example [404]
                result = response.json()
                if 'success' in result.keys() and result['success'] is True:  # If the response was "successful"
                    return result
        except requests.exceptions.ConnectionError:  # No internet connection
            return {}

    @staticmethod
    def _generate_backup_pile(deck_count) -> List[Card]:
        """Generate backup pile."""
        back_up_pile = []
        suits = ['SPADES', 'DIAMONDS', 'HEARTS', 'CLUBS']
        values = ['2', '3', '4', '5', '6', '7', '8', '9', '01', 'JACK', 'QUEEN', 'KING', 'ACE']
        for _ in range(deck_count):
            for s in suits:
                for v in values:
                    back_up_pile.append(Card(v, s, v[0] + s[0]))
        return back_up_pile
