"""Simple version of rock paper and scissors."""
from random import choice


def normalize_user_name(name: str) -> str:
    """
    Simple function gets player name as input and capitalizes it.

    :param name: name of the player
    :return: A name that is capitalized.
    """
    return name.capitalize()


def reverse_user_name(name: str) -> str:
    """
    Function that takes in name as a parameter and reverses its letters. The name should also be capitalized.

    :param name: name of the player
    :return: A name that is reversed.
    """
    reversed_name = name[::-1]
    return normalize_user_name(reversed_name)


def check_user_choice(choice: str) -> str:
    """
    Function that checks user's choice.

    The choice can be uppercase or lowercase string, but the choice must be
    either rock, paper or scissors. If it is, then return a choice that is lowercase.
    Otherwise return 'Sorry, you entered unknown command.'
    :param choice: user choice
    :return: choice or an error message
    """
    choice_lowercase = choice.lower()
    unknown_command = "Sorry, you entered unknown command."
    if choice_lowercase == "rock"or choice_lowercase == "paper" or choice_lowercase == "scissors":
        return choice_lowercase
    return unknown_command


def output(name: str, user_choice: str, computer_choice: str):
    """Function that returns the output of the game."""
    a = user_choice.lower()
    b = computer_choice.lower()
    if b == "rock" or b == "paper" or b == "scissors":
        if a == "rock" or a == "paper" or a == "scissors":
            return game_right(name, user_choice, computer_choice)
        return "There is a problem determining the winner."
    return "There is a problem determining the winner."


def game_right(name: str, user_choice: str, computer_choice: str):
    """Function that calculates who won, using the correct user_name."""
    a = user_choice.lower()
    b = computer_choice.lower()
    check_name = normalize_user_name(name)
    computer_wins = (check_name + " had " + a + " and computer had " + b + ", hence computer wins.")
    user_wins = (check_name + " had " + a + " and computer had " + b + ", hence " + check_name + " wins.")
    if user_choice == check_user_choice(user_choice):
        if a == b:
            return check_name + " had " + a + " and computer had " + b + ", hence it is a draw."
        elif a == "rock":
            if b == "paper":
                return computer_wins
            return user_wins
        elif a == "paper":
            if b == "scissors":
                return computer_wins
            return user_wins
        elif a == "scissors":
            if b == "rock":
                return computer_wins
            return user_wins


def determine_winner(name: str, user_choice: str, computer_choice: str, reverse_name: bool = False) -> str:
    """
    Determine the winner returns a string that has information about who won.

    You should use the functions that you wrote before. You should use check_user_choice function
    to validate the user choice and use normalize_user_name for representing a correct name. If the
    function parameter reverse is true, then you should also reverse the player name.
    NB! Use the previous functions that you have written!

    :param name:player name
    :param user_choice:
    :param computer_choice:
    :param reverse_name:
    :return:
    """
    reversed_name = reverse_user_name(name)
    if reverse_name is True:
        return output(reversed_name, user_choice, computer_choice)
    return output(name, user_choice, computer_choice)


def play_game() -> None:
    """
    Enable you to play the game you just created.

    :return:
    """
    user_name = input("What is your name? ")
    play_more = True
    while play_more:
        computer_choice = choice(['rock', 'paper', 'scissors'])
        user_choice = check_user_choice(input("What is your choice? "))
        print(determine_winner(user_name, user_choice, computer_choice))
        play_more = True if input("Do you want to play more ? [Y/N] ").lower() == 'y' else False


if __name__ == "__main__":
    print(normalize_user_name('ago'))  # Ago
    print(normalize_user_name('AGO'))  # Ago
    print(normalize_user_name('MaRtInA'))  # Martina

    print(reverse_user_name('MaRtInA'))  # Anitram
    print(reverse_user_name('AGO'))  # Oga

    print(check_user_choice('rock'))  # rock
    print(check_user_choice('ROCK'))  # rock
    print(check_user_choice('midagi on viltu'))  # Sorry, you entered unknown command.

    # 50%2 on uba tehtud, tubli töö!

    print(determine_winner('ago', 'rock', 'paper'))  # Ago had rock and computer had paper, hence computer wins.
    print(determine_winner('ago', 'rock', 'paper', True))  # Oga had rock and computer had paper, hence computer wins.
    print(determine_winner('loORa', 'SCISSORS', 'paper'))  # Loora had scissors and computer had paper, hence Loora wins.
    print(determine_winner('Shakira', 'waka waka', 'fire'))  # There is a problem determining the winner.
    print(determine_winner('Shakira', 'rock',
                           'sciSSOrs'))  # Shakira had rock and computer had scissors, hence Shakira wins.

    play_game()        # Kommenteeri see rida välja kui kõik funktsioonid on valmis
