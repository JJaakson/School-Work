"""A program to help me learn testing."""


def students_study(time: int, coffee_needed: bool) -> bool:
    """
    Return True if students study in given circumstances.

    (19, False) -> True
    (1, True) -> False.
    """
    if 1 <= time <= 24:
        if 5 <= time <= 17 and coffee_needed:
            return True
        elif 18 <= time <= 24:
            return True
        else:
            return False


def lottery(a: int, b: int, c: int) -> int:
    """
    Return Lottery victory result 10, 5, 1, or 0 according to input values.

    (5, 5, 5) -> 10
    (2, 2, 1) -> 0
    (2, 3, 1) -> 1
    """
    if a == 5 and a == b and a == c:
        return 10
    elif a == b and a == c:
        return 5
    elif a != b and a != c:
        return 1
    else:
        return 0


def fruit_order(small_baskets: int, big_baskets: int, ordered_amount: int) -> int:
    """
    Return number of small fruit baskets if it's possible to finish the order, otherwise return -1.

    (5, 1, 9) -> 4
    (3, 1, 10) -> -1
    """
    bigs = 0
    small_baskets_needed = 0
    smalls = 0
    if big_baskets > 0:
        for i in range(big_baskets):
            bigs += 1
            small_baskets_needed = ordered_amount - 5
            ordered_amount = ordered_amount - 5
            if small_baskets_needed < 0:
                bigs -= 1
                break
        for i in range(small_baskets):
            if small_baskets_needed < 0:
                smalls = 5 + small_baskets_needed
            elif small_baskets_needed > 0:
                smalls = small_baskets_needed
    else:
        smalls = ordered_amount
    if small_baskets >= smalls:
        return smalls
    else:
        return -1


if __name__ == '__main__':
    print(students_study(19, False))
