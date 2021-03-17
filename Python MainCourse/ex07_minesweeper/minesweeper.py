"""Minesweeper has to swipe the mines."""
import copy


def create_minefield(height: int, width: int) -> list:
    """
    Create and return minefield.

    Minefield must be height high and width wide. Each position must contain single dot (`.`).
    :param height: int
    :param width: int
    :return: list
    """
    table = [["." for _ in range(width)]for i in range(height)]
    return table


def add_mines(minefield: list, mines: list) -> list:
    """
    Add mines to a minefield and return minefield.

    This function cannot modify the original minefield list.
    Minefield must be length long and width wide. Each non-mine position must contain single dot.
    If a position is empty ("."), then a small mine is added ("x").
    If a position contains small mine ("x"), a large mine is added ("X").
    Mines are in a list.
    Mine is a list. Each mine has 4 integer parameters in the format [N, S, E, W].
        - N is the distance between area of mines and top of the minefield.
        - S ... area of mines and bottom of the minefield.
        - E ... area of mines and right of the minefield.
        - W ... area of mines and left of the minefield.
    :param minefield: list
    :param mines: list
    :return: list
    """
    if minefield:
        mf_copy = copy.deepcopy(minefield)
        for mine_p in range(len(mines)):
            for row in range(mines[mine_p][0], len(mf_copy) - mines[mine_p][1]):
                #  loops the rows that are between N and S
                for element in range(mines[mine_p][3], len(mf_copy[0]) - mines[mine_p][2]):
                    #  loops the rows that are between W and E
                    if mf_copy[row][element] == ".":
                        mf_copy[row][element] = "x"
                    else:
                        mf_copy[row][element] = "X"
        return mf_copy
    else:
        return []


def get_minefield_string(minefield: list) -> str:
    """
    Return minefield's string representation.

    .....
    .....
    x....
    Xx...

    :param minefield:
    :return:
    """
    if minefield:
        separator = ""
        new_line = "\n"
        list_string = [separator.join(i) for i in minefield]
        minefield_string = new_line.join(list_string)
        return minefield_string
    else:
        "Zero"


def calculate_mine_count(minefield: list) -> list:
    """
    For each cell in minefield, calculate how many mines are nearby.

    This function cannot modify the original list.
    So, the result should be a new list (or copy of original).

    ....
    ..x.
    X.X.
    x..X

    =>

    0111
    13x2
    X4X3
    x32X

    :param minefield:
    :return:
    """
    mf = copy.deepcopy(minefield)
    for row in range(len(minefield)):
        for col in range(len(minefield[row])):
            if mf[row][col] == ".":
                mf[row][col] = mine_count(minefield, row, col)
    return mf


def mine_count(minefield, row, col):
    """Subfunc, counts the nearby mines."""
    count = 0
    for rowi in range(row - 1, row + 2):
        for coli in range(col - 1, col + 2):
            if 0 <= rowi < len(minefield) and 0 <= coli < len(minefield[row]) and minefield[rowi][coli] != ".":
                #  checks for mines around the row, col position
                count += 1
    return str(count)


def walk(minefield, moves, lives) -> list:
    """
    Make moves on the minefield.

    This function cannot modify the original minefield list.
    Starting position is marked by #.
    There is always exactly one # on the field.
    The position you start is an empty cell (".").

    Moves is a list of move "orders":
    N - up,
    S - down,
    E - right,
    W - left.

    Example: "NWWES"

    If the position you have to move contains "x" (small mine),
    then the mine is cleared (position becomes "."),
    but you cannot move there.
    In case of clearing a small mine, ff the position where the minesweeper is, has 5 or more mines nearby
    (see the previous function), minesweeper also loses a life.
    If it has 0 lives left, then clearing is not done and moving stops.

    Example:
    #x
    ..
    moves: ESS

    =>

    1st step ("E"):
    #.
    ..

    2nd step ("S"):
    ..
    #.

    3rd step ("S"):
    ..
    #.

    Example #2
    XXX
    x.x
    .#X
    moves: NWES, lives = 1

    1) "N"
    XXX
    x#x
    ..X

    2) "W". the small mine is cleared, but with the cost of one life :'(
    XXX
    .#x
    ..X
    lives = 0

    3) "E"
    XXX
    .#x
    ..X
    As clearing the mine on the right, he would lose a life (because minesweeper has 5 or more mines nearby).
    But as he has no lives left, he stops there. No more moves will be carried out.

    If the position you have to move contains "X" (huge mine),
    then you move there and lose a life.

    #X
    ..
    moves: ESS

    1) (lives = lives - 1)
    .#
    ..
    2)
    ..
    .#
    3)
    ..
    .#

    If you have to move into a position with a huge mine "X"
    but you don't have any more lives, then you finish your moves.

    lives: 2

    #XXXX
    .....
    moves: EEES

    1) lives = 1
    .#XXX
    .....
    2) lives = 0
    ..#XX
    .....
    3) stop, because you would die
    final result:
    ..#XX
    .....

    :param minefield:
    :param moves:
    :param lives:
    :return:
    """
    rowh, colh = where_is_startingpoint(minefield)  # Hashtag location
    mf_copy = copy.deepcopy(minefield)
    mf_copy[rowh][colh] = "."  # Replaces #
    for move in moves:
        new_rowh, new_colh = next_position(rowh, colh, move)
        if new_rowh in range(len(mf_copy)) and new_colh in range(len(mf_copy[0])):  # checks if the move is in the mf
            if mf_copy[new_rowh][new_colh] == "x":
                if int(mine_count(mf_copy, rowh, colh)) > 4:  # checks the how many mines are around
                    if lives == 0:
                        break
                    else:
                        lives -= 1
                        mf_copy[new_rowh][new_colh] = "."
                else:
                    mf_copy[new_rowh][new_colh] = "."
            elif mf_copy[new_rowh][new_colh] == "X":
                if lives > 0:
                    lives -= 1
                    mf_copy[new_rowh][new_colh] = "."
                    rowh, colh = new_rowh, new_colh
                else:
                    break
            else:
                rowh, colh = new_rowh, new_colh
    mf_copy[rowh][colh] = "#"
    return mf_copy


def next_position(row, col, move):
    """Find where is the next move with coordinates."""
    if move == "N":
        return row - 1, col
    elif move == "S":
        return row + 1, col
    elif move == "E":
        return row, col + 1
    elif move == "W":
        return row, col - 1


def where_is_startingpoint(minefield):
    """Find startingpoint."""
    for rowi in range(len(minefield)):
        for coli in range(len(minefield[rowi])):
            if minefield[rowi][coli] == "#":
                return rowi, coli


if __name__ == '__main__':
    minefield_a = create_minefield(4, 3)
    print(minefield_a)  # ->
    """
    [
        ['.', '.', '.'],
        ['.', '.', '.'],
        ['.', '.', '.'],
        ['.', '.', '.']
    ]
    """

    minefield_a = add_mines(minefield_a, [[0, 3, 2, 0], [2, 1, 0, 1]])
    print(minefield_a)  # ->
    """
    [
        ['x', '.', '.'],
        ['.', '.', '.'],
        ['.', 'x', 'x'],
        ['.', '.', '.']
    ]
    """

    print(get_minefield_string(minefield_a))
    minefield_ac = calculate_mine_count(minefield_a)
    print(get_minefield_string(minefield_ac))

    minefield_b = create_minefield(8, 7)
    minefield_b = add_mines(minefield_b, [[2, 1, 3, 2], [0, 5, 3, 0]])

    print(minefield_b)  # ->
    """
    [
        ['x', 'x', 'x', 'x', '.', '.', '.'],
        ['x', 'x', 'x', 'x', '.', '.', '.'],
        ['x', 'x', 'X', 'X', '.', '.', '.'],
        ['.', '.', 'x', 'x', '.', '.', '.'],
        ['.', '.', 'x', 'x', '.', '.', '.'],
        ['.', '.', 'x', 'x', '.', '.', '.'],
        ['.', '.', 'x', 'x', '.', '.', '.'],
        ['.', '.', '.', '.', '.', '.', '.']
    ]
    """

    minefield_c = create_minefield(5, 5)
    minefield_c = add_mines(minefield_c, [[0, 0, 2, 2]])
    print(minefield_c)  # ->
    """
    [
        ['.', '.', 'x', '.', '.'],
        ['.', '.', 'x', '.', '.'],
        ['.', '.', 'x', '.', '.'],
        ['.', '.', 'x', '.', '.'],
        ['.', '.', 'x', '.', '.']
    ]
    """

    mf = [['.', '.', '.', '.'], ['.', '.', 'x', '.'], ['X', '.', 'X', '.'], ['x', '.', '.', 'X']]
    print(calculate_mine_count(mf))

    """
    [
        ['0', '1', '1', '1'],
        ['1', '3', 'x', '2'],
        ['X', '4', 'X', '3'],
        ['x', '3', '2', 'X']
    ]
    """

    mf = copy.deepcopy(minefield_c)
    mf[0][0] = '#'
    print(get_minefield_string(walk(mf, "WEESE", 2)))
    """
    .....
    .#...
    ..x..
    ..x..
    ..x..
    """

    mf = create_minefield(3, 5)
    mf = add_mines(mf, [[0, 0, 1, 2]])
    mf = add_mines(mf, [[0, 1, 1, 1]])
    print(get_minefield_string(mf))
    """
    .xXX.
    .xXX.
    ..xx.
    """
    mf[0][4] = "#"
    mf = walk(mf, "WSSWN", 2)
    print(get_minefield_string(mf))
    """
    .xX..
    .xX#.
    ..x..
    """
    # minesweeper would die if stepping into the mine, therefore he stops
    minefieldd = [[".", ".", "X", ".", "x"], [".", ".", "X", "x", "."], ["#", "x", "x", ".", "x"], ["X", ".", ".", "x", "X"], ["x", ".", ".", "x", "X"]]
    minefielddd = walk(minefieldd, "NEWS", 1)
    print(get_minefield_string(minefielddd))
