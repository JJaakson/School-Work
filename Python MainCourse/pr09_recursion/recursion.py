"""Recursion is recursion."""


def recursive_reverse(s: str) -> str:
    """Reverse a string using recursion.

    recursive_reverse("") => ""
    recursive_reverse("abc") => "cba"

    :param s: string
    :return: reverse of s
    """
    if len(s) == 0:
        return ""
    string = ""
    return string + s[-1] + recursive_reverse(s[:-1])


def remove_nums_and_reverse(string):
    """
    Recursively remove all the numbers in the string and return reversed version of that string without numbers.

    print(remove_nums_and_reverse("poo"))  # "oop"
    print(remove_nums_and_reverse("3129047284"))  # empty string
    print(remove_nums_and_reverse("34e34f7i8l 00r532o23f 4n5oh565ty7p4"))  # "python for life"
    print(remove_nums_and_reverse("  k 4"))  # " k  "

    :param string: given string to change
    :return: reverse version of the original string, only missing numbers
    """
    if len(string) == 0 or string.isdigit():
        return ""
    only = ""
    if string[-1].isalpha() or string[-1].isspace():
        only += string[-1]
        return only[-1] + remove_nums_and_reverse(string[:-1])
    else:
        return only + remove_nums_and_reverse(string[:-1])


def task1(string):
    """
    Figure out what this code is supposed to do and rewrite it using recursion.

    :param string: given string
    :return: figure it out
    """
    if len(string) == 0:
        return True
    return string[0] == string[-1] and task1(string[1:-1])


def task2(string):
    """
    Figure out what this code is supposed to do and rewrite it using iteration.

    :param string: given string
    :return: figure it out
    """
    if len(string) < 2:
        return string
    else:
        simple_string = ""
        for i in range(len(string)):
            if string[i - 1] == string[i]:
                simple_string += "-" + string[i]
            else:
                simple_string += string[i]
        if simple_string[0] == "-":
            return simple_string[1:]
        else:
            return simple_string


if __name__ == '__main__':
    print(task2("erre"))
