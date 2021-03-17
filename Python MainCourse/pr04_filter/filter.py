"""Filtering."""


def remove_vowels(string: str) -> str:
    """
    Remove vowels (a, e, i, o, u).

    :param string: Input string
    :return string without vowels.
    """
    vowels = ["a", "e", "i", "o", "u", "A", "E", "I", "O", "U"]
    a = ""
    for i in string:
        if i not in vowels:
            a += i
    return a


def sorteerib_pikkuse_jargi(a):
    """See funktsioon tagastab sõne pikkuse."""
    return len(a)


def longest_filtered_word(string_list: list) -> str:
    """
    Filter, find and return the longest string.

    :param string_list: List of strings.
    :return: Longest string without vowels.
    """
    tank = []
    for i in string_list:
        tank.append(remove_vowels(i))
    tank.sort(reverse=True, key=sorteerib_pikkuse_jargi)
    return tank[0]


def sort_list(string_list: list) -> list:
    """
    Filter vowels in strings and sort the list by the length.

    Longer strings come first.

    :param string_list: List of strings that need to be sorted.
    :return: Filtered list of strings sorted by the number of symbols in descending order.
    """
    filtered_list = []
    for i in string_list:
        filtered_list.append(remove_vowels(i))
    filtered_list.sort(reverse=True, key=sorteerib_pikkuse_jargi)
    return filtered_list


if __name__ == '__main__':
    print((remove_vowels("aaee")))
    print(remove_vowels(""))  # => ""
    print(remove_vowels("hello"))  # => "hll"
    print(remove_vowels("Home"))  # => "Hm"
    print(longest_filtered_word(["Bunny", "Tiger", "Bear", "Snake"]))  # => "Bnny"
    print(sort_list(["Bunny", "Tiger", "Bear", "Snake"]))  # => ['Bnny', 'Tgr', 'Snk', 'Br']
