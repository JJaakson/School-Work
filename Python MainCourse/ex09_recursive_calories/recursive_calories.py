"""Let's count calories."""


def x_sum_loop(nums, x) -> int:
    """
    Given a list of integers and a number called x. Iteratively return sum of every x'th number in the list.

    In this task "indexing" starts from 1, so if x = 2 and nums = [2, 3, 4, -9], the output should be -6 (3 + -9).

    X can also be negative, in that case indexing starts from the end of the list, see examples below.

    If x is 0, the sum should be 0 as well.

    print(x_sum_loop([], 3))  # 0
    print(x_sum_loop([2, 5, 6, 0, 15, 5], 3))  # 11
    print(x_sum_loop([0, 5, 6, -5, -9, 3], 1))  # 0
    print(x_sum_loop([43, 90, 115, 500], -2))  # 158
    print(x_sum_loop([1, 2], -9))  # 0
    print(x_sum_loop([2, 3, 6], 5))  # 0
    print(x_sum_loop([6, 5, 3, 2, 9, 8, 6, 5, 4], 3))  # 15

    :param nums: list of integer
    :param x: number indicating every which num to add to sum
    :return: sum of every x'th number in the list
    """
    sum = 0
    if x >= 1:
        for i in range(x - 1, len(nums), x):
            sum += nums[i]
    elif x <= -1:
        for i in range(-x, len(nums) + 1, -x):
            sum += nums[-i]
    return sum


def x_sum_recursion(nums, x) -> int:
    """
    Given a list of integers and a number called x. Recursively return sum of every x'th number in the list.

    In this task "indexing" starts from 1, so if x = 2 and nums = [2, 3, 4, -9], the output should be -6 (3 + -9).

    X can also be negative, in that case indexing starts from the end of the list, see examples below.

    If x = 0, the sum should be 0 as well.

    print(x_sum_recursion([], 3))  # 0
    print(x_sum_recursion([2, 5, 6, 0, 15, 5], 3))  # 11
    print(x_sum_recursion([0, 5, 6, -5, -9, 3], 1))  # 0
    print(x_sum_recursion([43, 90, 115, 500], -2))  # 158
    print(x_sum_recursion([1, 2], -9))  # 0
    print(x_sum_recursion([2, 3, 6], 5))  # 0
    print(x_sum_recursion([6, 5, 3, 2, 9, 8, 6, 5, 4], 3))  # 15

    :param nums: list of integer
    :param x: number indicating every which num to add to sum
    :return: sum of every x'th number in the list
    """
    if len(nums) < x or len(nums) < -x or x == 0:
        return 0
    elif x > 0:  # When the "list index" is positive
        return nums[x - 1] + x_sum_recursion(nums[x:], x)
    elif x < 0:  # When the "list index" is negative
        return nums[x] + x_sum_recursion(nums[:x], x)


def lets_count_calories(salad: float, chocolate_pieces: int, fridge_visits: int) -> int:
    """
    Every time Kadri goes to fridge, she wants to eat something. In case she has salad in her fridge, she eats exactly.

    100g of it, no matter what. If she has chocolate in the fridge and she had just eaten salad, she takes one piece of
    chocolate. In case she came to fridge and didn't have any salad to eat, she takes two pieces of chocolate (if she
    has at least two pieces, if she doesn't, she takes just one). She keeps on going to the fridge for a little snack
    until she either runs out of fridge visits or snacks.

    Eating 100g of salad gives her 120 calories, eating a piece of chocolate gives her 34 calories.

    Your job is to count recursively how many calories she eats at total during her fridge visits.

    Salad will always be given one decimal place after comma, for an example 5.7, but never like 3.87.

    print(lets_count_calories(0.1, 3, 2))  # 120 + 3*34 = 222
    print(lets_count_calories(0.4, 3, 2))  # 2*120 + 2*34 = 308
    print(lets_count_calories(0, 4, 2))  # 4 * 34 = 136
    print(lets_count_calories(3.4, 6, 0))  # 0
    print(lets_count_calories(1.2, 5, 10))  # 1200 + 5*34 = 1370
    print(lets_count_calories(0.3, 8, 6))  # 360 + 3*34 + 2*34 + 2*34 + 34 = 632

    :param salad: salad in the fridge, given in kilograms (1.2kg == 1200g).
    :param chocolate_pieces: pieces of chocolate in the fridge.
    :return: calories eaten while visiting fridge.
    """
    if fridge_visits == 0 or chocolate_pieces == 0 and salad == 0.0:
        return 0
    elif chocolate_pieces >= 2 and salad == 0.0:
        return 34 * 2 + lets_count_calories(salad, chocolate_pieces - 2, fridge_visits - 1)
    elif chocolate_pieces == 1 and salad == 0.0:
        return 34 + lets_count_calories(salad, chocolate_pieces - 1, fridge_visits - 1)
    elif salad > 0.0 and chocolate_pieces == 0:
        return 120 + lets_count_calories(round(salad - 0.1, 1), chocolate_pieces, fridge_visits - 1)
    else:
        return 154 + lets_count_calories(round(salad - 0.1, 1), chocolate_pieces - 1, fridge_visits - 1)


def cycle(cyclists: list, distance: float, time: int = 0, index: int = None) -> str:
    """
    Given cyclists and distance in kilometers, find out who crosses the finish line first. Cyclists is list of tuples.

    every tuple contains name of the cyclist, how many kilometres this cyclist carries the others and time in minutes
    showing how long it cycles first. If there are no cyclists or distance is 0 or less, return message "Everyone fails."
    else return the last cyclist to carry others and total time taken to cross the finish line, including the last cyclist's
    "over" minutes: "{cyclist1} is the last leader. Total time: {hours}h {minutes}min."
    We'll say if a cyclist has cycled its kilometres ahead of the others, it's the next cyclist's turn. If the last
    cyclist has done the leading, it's time for the first one again.

    print(cycle([("First", 0.1, 9), ("Second", 0.1, 8)], 0.3))  #  "First is the last leader. Total time: 0h 26min."
    print(cycle([], 0))  # "Everyone fails."
    print(cycle([("Fernando", 19.8, 42), ("Patricio", 12, 28), ("Daniel", 7.8, 11), ("Robert", 15.4, 49)], 50))  # "Robert is the last leader. Total time: 2h 10min."
    print(cycle([("Loner", 0.1, 1)], 60))  # "Loner is the last leader. Total time: 10h 0min."

    :param cyclists: list on tuples, containing cyclist's name, distance it cycles first and time in minutes how long it takes it.
    :param distance: distance to be cycled overall
    :param time: time in minutes indicating how long it has taken cyclists so far
    :param index: index to know which cyclist's turn it is to be first
    :return: string indicating the last cyclist to carry the others
    """
    if distance < 0 < time:
        minutes = time % 60
        hours = time // 60
        return f"{cyclists[-1][0]} is the last leader. Total time: {hours}h {minutes}min."
    elif distance > 0 and cyclists:
        new_list = cyclists.copy()
        new_list.append(cyclists[0])
        dist = distance - new_list[0][1]  # Distance after the first cyclist has done its job
        new_time = time + new_list[0][2]
        new_list.pop(0)
        return cycle(new_list, dist, new_time)
    elif not cyclists or distance <= 0:
        return "Everyone fails."


def count_strings(data: list, pos=None, result: dict = None) -> dict:
    """
    You are given a list of strings and lists, which may also contain strings and lists etc. Your job is to.

    collect these strings into a dict, where key would be the string and value the amount of occurrences of that string
    in these lists.

    print(count_strings([[], ["J", "*", "W", "f"], ["j", "g", "*"], ["j", "8", "5", "6", "*"], ["*", "*", "A", "8"]]))
    # {'J': 1, '*': 5, 'W': 1, 'f': 1, 'j': 2, 'g': 1, '8': 2, '5': 1, '6': 1, 'A': 1}
    print(count_strings([[], [], [], [], ["h", "h", "m"], [], ["m", "m", "M", "m"]]))  # {'h': 2, 'm': 4, 'M': 1}
    print(count_strings([]))  # {}
    print(count_strings([['a'], 'b', ['a', ['b']]]))  # {'a': 2, 'b': 2}

    :param data: given list of lists
    :param pos: figure out how to use it
    :param result: figure out how to use it
    :return: dict of given symbols and their count
    """
    if pos is None and result is None:
        pos = 0
        result = {}
    if pos >= len(data):
        return result
    elif isinstance(data[pos], str):
        if data[pos] not in result.keys():
            result[data[pos]] = 1
        else:
            result[data[pos]] += 1
        return count_strings(data, pos + 1, result)
    elif isinstance(data[pos], list):
        new_data = data + data[pos]
        new_data.pop(pos)
        return count_strings(new_data, pos, result)


if __name__ == '__main__':
    print(count_strings([[], ["J", "*", "W", "f"], ["j", "g", "*"], ["j", "8", "5", "6", "*"], ["*", "*", "A", "8"]]))
    # {'J': 1, '*': 5, 'W': 1, 'f': 1, 'j': 2, 'g': 1, '8': 2, '5': 1, '6': 1, 'A': 1}
    print(count_strings([[], [], [], [], ["h", "h", "m"], [], ["m", "m", "M", "m"]]))  # {'h': 2, 'm': 4, 'M': 1}
    print(count_strings([]))  # {}
    print(count_strings([['a'], 'b', ['a', ['b']]]))  # {'a': 2, 'b': 2}
