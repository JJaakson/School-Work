"""KT4 (K14)."""


def two_digits_into_list(nr: int) -> list:
    """
    Return list of digits of 2-digit number.

    two_digits_into_list(11) => [1, 1]
    two_digits_into_list(71) => [7, 1]

    :param nr: 2-digit number
    :return: list of length 2
    """
    nr_list = []
    for i in str(nr):
        nr_list.append(int(i))
    return nr_list


def only_one_pair(numbers: list) -> bool:
    """
    Whether the list only has one pair.

    Function returns True, if the list only has one pair (two elements have the same value).
    In other cases:
     there are no elements with the same value
     there are more than 2 elements with the same value
     there are several different pairs
    returns False.

    only_one_pair([1, 2, 3]) => False
    only_one_pair([1]) => False
    only_one_pair([1, 2, 3, 1]) => True
    only_one_pair([1, 2, 1, 3, 1]) => False
    """
    countlist = []
    for i in numbers:
        if isinstance(i, int):
            countlist.append(numbers.count(i))
            if numbers.count(i) == 2:
                numbers.remove(i)
    if countlist.count(2) > 1 or countlist.count(2) == 0:
        return False
    return True


def min_diff(nums):
    """
    Find the smallest diff between two integer numbers in the list.

    The list will have at least 2 elements.

    min_diff([1, 2, 3]) => 1
    min_diff([1, 9, 17]) => 8
    min_diff([100, 90]) => 10
    min_diff([1, 100, 1000, 1]) => 0

    :param nums: list of ints, at least 2 elements.
    :return: min diff between 2 numbers.
    """
    if len(nums) > 1:
        diff_list = []
        for i in range(0, len(nums)):
            for j in range(0, len(nums)):
                if j != i and nums[j] >= nums[i]:
                    diff_list.append(nums[j] - nums[i])
                elif j != i and nums[j] < nums[i]:
                    diff_list.append(nums[i] - nums[j])
        diff_list.sort()
        return diff_list[0]
