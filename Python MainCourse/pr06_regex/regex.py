"""Regex harjutsülesanne."""
import re


def read_file(path: str) -> list:
    """
    Read file and return list of lines read.

    :param path: str
    :return: list
    """
    file = open(path, "r")
    list = []
    for line in file:
        list.append(line.rstrip("\n"))
    return list


def match_specific_string(input_data: list, keyword: str) -> int:
    """
    Check if given list of strings contains keyword.

    Return all keyword occurrences (case insensitive). If an element cointains the keyword several times, count all the occurrences.

    ["Python", "python", "PYTHON", "java"], "python" -> 3

    :param input_data: list
    :param keyword: str
    :return: int
    """
    count = 0
    for i in input_data:
        match = re.findall(keyword.lower(), i.lower())
        if match:
            count += len(match)
    return count


def detect_email_addresses(input_data: list) -> list:
    """
    Check if given list of strings contains valid email addresses.

    Return all unique valid email addresses in alphabetical order presented in the list.
    ["Test", "Add me test@test.ee", "ago.luberg@taltech.ee", "What?", "aaaaaa@.com", ";_:Ö<//test@test.au??>>>;;d,"] ->
    ["ago.luberg@taltech.ee", "test@test.au", "test@test.ee"]

    :param input_data: list
    :return: list
    """
    pattern = r"[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+"
    new_list = []
    if len(input_data) > 0:
        for i in input_data:
            match = re.findall(pattern, i)
            if match:
                for j in match:
                    if j not in new_list:
                        new_list.append(j)
        new_list.sort()
        return new_list
    return new_list


if __name__ == '__main__':
    list_of_lines_emails = read_file("input_detect_email_addresses_example_1.txt")  # reading from file
    list_of_lines_email_two = read_file("input_detect_email_addresses_example_2.txt")
    print(detect_email_addresses(list_of_lines_emails))  # ['allowed@example.com', 'firstname-lastname@example.com', 'right@example.com', 'spoon@lol.co.jp', 'testtest@dome.museum', 'testtest@example.name']
    print(detect_email_addresses(list_of_lines_email_two))

    list_of_lines_keywords = read_file("input_match_specific_string_example_1.txt")
    print(match_specific_string(list_of_lines_keywords, "job"))  # 9
