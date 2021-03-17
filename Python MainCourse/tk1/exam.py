"""Test 1 (K14)."""


def has23(nums):
    """
    Given an int array length 2, return True if it contains a number 2 or a number 3.

    has23([2, 5]) => True
    has23([4, 3]) => True
    has23([4, 25]) => False

    :param nums: list of integers.
    :return: True if the list contains a 2 or a 3.
    """
    a, b = 2, 3
    if a in nums or b in nums:
        return True
    return False


def caught_speeding(speed, is_birthday):
    """
    Return which category speeding ticket you would get.

    You are driving a little too fast, and a police officer stops you.
    Write code to compute the result, encoded as an int value:
    0=no ticket, 1=small ticket, 2=big ticket.
    If speed is 60 or less, the result is 0.
    If speed is between 61 and 80 inclusive, the result is 1.
    If speed is 81 or more, the result is 2.
    Unless it is your birthday -- on that day, your speed can be 5 higher in all cases.

    caught_speeding(60, False) => 0
    caught_speeding(65, False) => 1
    caught_speeding(65, True) => 0

    :param speed: Speed value.
    :param is_birthday: Whether it is your birthday (boolean).
    :return: Which category speeding ticket you would get (0, 1, 2).
    """
    if is_birthday:
        if speed <= 65:
            return 0
        elif 65 < speed <= 85:
            return 1
        elif speed > 85:
            return 2
    else:
        if speed <= 60:
            return 0
        elif 60 < speed <= 80:
            return 1
        elif speed > 80:
            return 2


def first_half(text):
    """
    Return the first half of an string.

    The length of the string is even.

    first_half('HaaHoo') => 'Haa'
    first_half('HelloThere') => 'Hello'
    first_half('abcdef') => 'abc'
    """
    half = int(len(text) // 2)
    return text[0:half]


def last_indices_elements_sum(nums):
    """
    Return sum of elements at indices of last two elements.

    Take element at the index of the last element value
    and take element at the index of the previous element value.
    Return the sum of those two elements.

    If the index for an element is out of the list, use 0 instead.

    The list contains at least 2 elements.

    last_indices_elements_sum([0, 1, 2, 0]) => 2 (0 + 2)
    last_indices_elements_sum([0, 1, 1, 7]) => 1 (just 1)
    last_indices_elements_sum([0, 1, 7, 2]) => 7 (just 7)
    last_indices_elements_sum([0, 1, 7, 8]) => 0 (indices too large, 0 + 0)

    :param nums: List of non-negative integers.
    :return: Sum of elements at indices of last two elements.
    """
    if len(nums) > 2:
        last, second_last = nums[-1], nums[-2]
        if last > len(nums):
            if second_last > len(nums):
                return 0
            return nums[second_last]
        elif second_last > len(nums):
            return nums[last]
        if last < len(nums) and second_last < len(nums):
            return nums[last] + nums[second_last]


def max_duplicate(nums):
    """
    Return the largest element which has at least one duplicate.

    If no element has duplicate element (an element with the same value), return None.

    max_duplicate([1, 2, 3]) => None
    max_duplicate([1, 2, 2]) => 2
    max_duplicate([1, 2, 2, 1, 1]) => 2

    :param nums: List of integers
    :return: Maximum element with duplicate. None if no duplicate found.
    """
    if len(nums) > 1:
        duplicates = []
        for i in nums:
            if nums.count(i) > 1:
                duplicates.append(i)
        duplicates.sort()
        if len(duplicates) > 0:
            return duplicates[-1]
        return None
    return None


if __name__ == '__main__':
    print(has23([2, 5]))
    print("tere")
    print(has23([1, 22]))
    print(caught_speeding(50, False))
    print(caught_speeding(81, False))
    print(first_half('HHoooossss'))
    print(last_indices_elements_sum([1, 110, 21, 2, 0, 5, 6, 7, 2, 12, 11, 13, 42]))
    print(max_duplicate([1, 2, 3, 2, 1, 100000020, 3, 5]))
