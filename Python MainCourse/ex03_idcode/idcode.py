# -*- coding: utf-8 -*-
"""Check if given ID code is valid."""


def is_valid_year_number(year_number: int) -> bool:
    """
    Check if given value is correct for year number in ID code.

    :param year_number: int
    :return: boolean
    """
    return 00 <= year_number <= 99


def is_valid_month_number(month_number: int) -> bool:
    """
    Check if given value is correct for month number in ID code.

    :param month_number: int
    :return: boolean
    """
    return month_number > 00 and month_number <= 12


def is_valid_day_number(gender_number: int, year_number: int, month_number: int, day_number: int) -> bool:
    """
    Check if given value is correct for day number in ID code.

    Also, consider leap year and which month has 30 or 31 days.

    :param gender_number: int
    :param year_number: int
    :param month_number: int
    :param day_number: int
    :return: boolean
    """
    year = get_full_year(gender_number, year_number)
    if month_number == 4 or month_number == 6 or month_number == 9 or month_number == 11:
        return 0 < day_number <= 30
    elif month_number == 2:
        if is_leap_year(year):
            return 0 < day_number <= 29
        else:
            return 0 < day_number <= 28
    elif 12 >= month_number > 0:
        return 0 < day_number <= 31
    else:
        return False


def is_leap_year(year):
    """Function that checks if a year is a leap year."""
    return (year % 4) == 0 and (year % 100) != 0 or (year % 400) == 0 and (year % 100) != 0 or year == 2000


def is_valid_birth_number(birth_number: int):
    """
    Check if given value is correct for birth number in ID code.

    :param birth_number: int

    :return: boolean

    """
    return 1 <= birth_number <= 999


def is_valid_control_number(id_code: str) -> bool:
    """
    Check if given value is correct for control number in ID code.

    Use algorithm made for creating this number.

    :param id_code: string
    :return: boolean
    """
    short_id = []
    for i in id_code:
        short_id += i
    number = int(id_code[-1])
    del short_id[-1]
    first_list = [1, 2, 3, 4, 5, 6, 7, 8, 9, 1]  # First series of multipliers
    second_list = [3, 4, 5, 6, 7, 8, 9, 1, 2, 3]  # Second series of multipliers
    first = 0
    second = 0
    for i in range(10):
        first += int(short_id[i]) * first_list[i]
    if 0 <= (first % 11) < 10 and number == (first % 11):
        return True
    elif (first % 11) == 10:
        for i in range(10):
            second += int(short_id[i]) * second_list[i]
        if 0 <= (second % 11) < 10 and number == (second % 11):
            return True
        elif (second % 11) == 10 and number == 0:
            return True
        else:
            return False
    else:
        return False


def get_full_year(gender_number: int, year_number: int) -> int:
    """
    Define the 4-digit year when given person was born.

    Person gender and year numbers from ID code must help.

    Given year has only two last digits.

    :param gender_number: int
    :param year_number: int
    :return: int
    """
    if gender_number == 1 or gender_number == 2:
        if 00 <= year_number <= 99:
            year = 1800 + year_number
            return year
    elif gender_number == 3 or gender_number == 4:
        if 00 <= year_number <= 99:
            year = 1900 + year_number
            return year
    elif gender_number == 5 or gender_number == 6:
        if 00 <= year_number <= 99:
            year = 2000 + year_number
            return year


def is_valid_gender_number(gender_number):
    """Function that checks if the gender number is correct."""
    return 0 < gender_number <= 6


def get_gender(gender_number):
    """Function that returns sex with gender number from idcode."""
    if is_valid_gender_number(gender_number):
        if (gender_number % 2) == 1:
            return "male"
        else:
            return "female"
    else:
        return "Wrong Number"


def get_birth_place(birth_number: int) -> str:
    """
    Find the place where the person was born.

    Possible locations are following: Kuressaare, Tartu, Tallinn, Kohtla-Järve, Narva, Pärnu,
    Paide, Rakvere, Valga, Viljandi, Võru and undefined. Lastly if the number is incorrect the function must return
    the following 'Wrong input!'
    :param birth_number: int
    :return: str
    """
    locations = {
        range(1, 11): "Kuressaare",
        range(11, 21): "Tartu",
        range(271, 371): "Tartu",
        range(21, 221): "Tallinn",
        range(471, 491): "Tallinn",
        range(221, 271): "Kohtla-Järve",
        range(371, 421): "Narva",
        range(421, 471): "Pärnu",
        range(491, 521): "Paide",
        range(521, 571): "Rakvere",
        range(571, 601): "Valga",
        range(601, 651): "Viljandi",
        range(651, 711): "Võru",
        range(711, 1000): "undefined"
    }
    for i in locations:
        if birth_number in i:
            return locations[i]
    else:
        return "Wrong input!"


def get_data_from_id(id_code: str) -> str:
    """
    Get possible information about the person.

    Use given ID code and return a short message.
    Follow the template - This is a <gender> born on <DD.MM.YYYY> in <location>.
    :param id_code: str
    :return: str
    """
    if is_id_valid(id_code):
        gender = int(id_code[0])
        year = int(id_code[1:3])
        month = id_code[3:5]
        day = id_code[5:7]
        birth_place = int(id_code[7:10])
        return "This is a " + get_gender(gender) + " born on " + day + "." + month + "." + str(
            get_full_year(gender, year)) + " in " + get_birth_place(birth_place) + "."
    else:
        return "Given invalid ID code!"


def if_id_correct(id_code):
    """Check if id is the right lenght and has int numbers."""
    return len(id_code) == 11 and id_code.isdigit()


def is_id_valid(id_code: str) -> bool:
    """
    Check if given ID code is valid and return the result (True or False).

    Complete other functions before starting to code this one.
    You should use the functions you wrote before in this function.
    :param id_code: str
    :return: boolean
    """
    if if_id_correct(id_code):
        gender_num = int(id_code[0])
        year_num = int(id_code[1:3])
        month_num = int(id_code[3:5])
        day_num = int(id_code[5:7])
        birth_num = int(id_code[7:10])
        return is_valid_gender_number(gender_num) and is_valid_year_number(year_num) \
            and is_valid_month_number(month_num) and is_valid_day_number(gender_num, year_num, month_num, day_num) \
            and is_valid_day_number(gender_num, year_num, month_num, day_num) and is_valid_birth_number(birth_num) \
            and is_valid_control_number(id_code)
    else:
        return False


if __name__ == '__main__':
    print(get_gender(0))
    print(is_valid_day_number(3, 99, 11, 30))
    print(is_leap_year(2000))
    print(is_valid_control_number("39909074227"))
    print(is_valid_control_number("37605030299"))
    print(get_data_from_id("39909074227"))
    """print("\nGender number:")
    for i in range(9):
        print(f"{i} {is_valid_gender_number(i)}")
        # 0 -> False
        # 1...6 -> True
        # 7...8 -> False
    print("\nYear number:")
    print(is_valid_year_number(100))  # -> False
    print(is_valid_year_number(50))  # -> true
    print("\nMonth number:")
    print(is_valid_month_number(2))  # -> True
    print(is_valid_month_number(15))  # -> False
    print("\nDay number:")
    print(is_valid_day_number(4, 5, 12, 25))  # -> True
    print(is_valid_day_number(3, 10, 8, 32))  # -> False
    print(is_leap_year(1804))  # -> True
    print(is_leap_year(1800))  # -> False
    print("\nFebruary check:")
    print(
        is_valid_day_number(4, 96, 2, 30))  # -> False (February cannot contain more than 29 days in any circumstances)
    print(is_valid_day_number(4, 99, 2, 29))  # -> False (February contains 29 days only during leap year)
    print(is_valid_day_number(4, 8, 2, 29))  # -> True
    print("\nMonth contains 30 or 31 days check:")
    print(is_valid_day_number(4, 22, 4, 31))  # -> False (April contains max 30 days)
    print(is_valid_day_number(4, 18, 10, 31))  # -> True
    print(is_valid_day_number(4, 15, 9, 31))  # -> False (September contains max 30 days)
    print("\nBorn order number:")
    print(is_valid_birth_number(0))  # -> False
    print(is_valid_birth_number(1))  # -> True
    print(is_valid_birth_number(850))  # -> True
    print("\nControl number:")
    print(is_valid_control_number("49808270244"))  # -> True
    print(is_valid_control_number("60109200187"))  # -> False, it must be 6

    print("\nFull message:")
    print(get_data_from_id("49808270244"))  # -> "This is a female born on 27.08.1998 in Tallinn."
    print(get_data_from_id("60109200187"))  # -> "Given invalid ID code!"
    print(get_full_year(1, 28))  # -> 1828
    print(get_full_year(4, 85))  # -> 1985
    print(get_full_year(5, 1))  # -> 2001
    print(get_gender(2))  # -> "female"
    print(get_gender(5))  # -> "male"

    # Comment these back in if you have completed other functions.
    print("\nChecking where the person was born")

    print(get_birth_place(0))  # -> "Wrong input!"
    print(get_birth_place(1))  # -> "Kuressaare"
    print(get_birth_place(273))  # -> "Tartu"
    print(get_birth_place(220))  # -> "Tallinn"""""

    """print(get_full_year(3, 99))

    print(is_valid_gender_number(3))
    print(is_valid_year_number(99))
    print(is_valid_month_number(9))
    print(is_valid_day_number(3, 99, 9, 7))
    print(is_valid_birth_number(422))
    print(is_valid_control_number("39909074227"))

    print("\nOverall ID check::")
    print(is_id_valid("49808270244"))  # -> True
    print(is_id_valid("12345678901"))  # -> False
    print("\nTest now your own ID code:")
    personal_id = input()  # type your own id in command prompt
    print(is_id_valid(personal_id))  # -> True
    print(get_data_from_id("39909074227"))
    print"""

    """    if is_valid_birth_number(birth_number) is True:
        if birth_number >= 1 and birth_number <= 10:
            return "Kuressaare"
        elif birth_number >= 11 and birth_number <= 20 or birth_number >= 271 and birth_number <= 370:
            return "Tartu"
        elif birth_number >= 21 and birth_number <= 220 or birth_number >= 471 and birth_number <= 490:
            return "Tallinn"
        elif birth_number >= 221 and birth_number <= 270:
            return "Kohtla-Järve"
        elif birth_number >= 371 and birth_number <= 420:
            return  "Narva"
        elif birth_number >= 421 and birth_number <= 470:
            return "Pärnu"
        elif birth_number >= 491 and birth_number <= 520:
            return "Paide"
        elif birth_number >= 521 and birth_number <= 570:
            return "Rakvere"
        elif birth_number >= 571 and birth_number <= 600:
            return "Valga"
        elif birth_number >= 601 and birth_number <= 650:
            return "Viljandi"
        elif birth_number >= 651 and birth_number <= 710:
            return "Võru"
        elif birth_number >= 711 and birth_number <= 999:
            return "undefined"
    else:
        return "Wrong input!"""
