"""Program that creates beautiful pyramids."""


def make_pyramid(base: int, char: str) -> list:
    """
    Construct a pyramid with given base.

    Pyramid should consist of given chars, all empty spaces in the pyramid list are ' '. Pyramid height depends on base length. Lowest floor consists of base-number chars.
    Every floor has 2 chars less than the floor lower to it.
    make_pyramid(3, "A") ->

    [
        [' ', 'A', ' '],
        ['A', 'A', 'A']
    ]
    make_pyramid(6, 'a') ->
    [
        [' ', ' ', 'a', 'a', ' ', ' '],
        [' ', 'a', 'a', 'a', 'a', ' '],
        ['a', 'a', 'a', 'a', 'a', 'a']
    ]

    :param base: int
    :param char: str
    :return: list
    """
    full_list = [[char if j in range(i, (base - i)) else " " for j in range(base)] for i in range(base)]
    #  teeb listi base pikkusega, mille sublistides on base korda char elemente
    pyramid_list = [i for i in full_list if char in i]
    #  teeb listi, kus sublistid ei ole nö tühjad
    return pyramid_list[::-1]


def join_pyramids(pyramid_a: list, pyramid_b: list) -> list:
    """
    Join together two pyramid lists.

    Get 2 pyramid lists as inputs. Join them together horizontally. If the the pyramid heights are not equal, add empty lines on the top until they are equal.
    join_pyramids(make_pyramid(3, "A"), make_pyramid(6, 'a')) ->
    [
        [' ', ' ', ' ', ' ', ' ', 'a', 'a', ' ', ' '],
        [' ', 'A', ' ', ' ', 'a', 'a', 'a', 'a', ' '],
        ['A', 'A', 'A', 'a', 'a', 'a', 'a', 'a', 'a']
    ]

    :param pyramid_a: list
    :param pyramid_b: list
    :return: list
    """
    a = len(pyramid_a)
    b = len(pyramid_b)
    pyramid_a = pyramid_a[::-1]  # Reveses the list
    pyramid_b = pyramid_b[::-1]
    if a < b:  # checks which list needs the "empty line"
        new_list_a = [" " for _ in range(len(pyramid_a[0]))]  # makes an empty list that is the correct length
        better_list = [pyramid_a[i] + pyramid_b[i] if i < a else new_list_a + pyramid_b[i] for i in range(b)]
        #  adds up the lists
    elif b < a:
        new_list_b = [" " for _ in range(len(pyramid_b[0]))]
        better_list = [pyramid_a[i] + pyramid_b[i] if i < b else pyramid_a[i] + new_list_b for i in range(a)]
    else:
        better_list = [pyramid_a[i] + pyramid_b[i] for i in range(b)]
    return better_list[::-1]


def to_string(pyramid: list) -> str:
    """
    Return pyramid list as a single string.

    Join pyramid list together into a string and return it.
    to_string(make_pyramid(3, 'A')) ->
    '''
     A
    AAA
    '''

    :param pyramid: list
    :return: str
    """
    string = ""
    new_line = "\n"
    string_list = [string.join(i) for i in pyramid]
    correct_string = new_line.join(string_list)
    return correct_string


if __name__ == '__main__':
    pyramid_a = make_pyramid(3, "A")
    print(pyramid_a)  # ->
    """
    [
        [' ', 'A', ' '],
        ['A', 'A', 'A']
    ]
    """

    pyramid_b = make_pyramid(6, 'a')
    print(pyramid_b)  # ->
    """
    [
        [' ', ' ', 'a', 'a', ' ', ' '],
        [' ', 'a', 'a', 'a', 'a', ' '],
        ['a', 'a', 'a', 'a', 'a', 'a']
    ]
    """

    joined = join_pyramids(pyramid_a, pyramid_b)
    print(joined)  # ->
    """
    [
        [' ', ' ', ' ', ' ', ' ', 'a', 'a', ' ', ' '],
        [' ', 'A', ' ', ' ', 'a', 'a', 'a', 'a', ' '],
        ['A', 'A', 'A', 'a', 'a', 'a', 'a', 'a', 'a']
    ]
    """

    pyramid_string = to_string(joined)
    print(pyramid_string)  # ->
    """
         aa
     A  aaaa
    AAAaaaaaas"""
