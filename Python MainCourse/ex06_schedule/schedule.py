"""Create schedule from the given file."""
import re


def no_times_table():
    """Create table if no schedule."""
    empty_file = []
    kriipsud = "-" * 18
    empty_file.append(f"{kriipsud}")
    empty_file.append(f"|  time | items  |")
    empty_file.append(f"{kriipsud}")
    empty_file.append(f"| No items found |")
    empty_file.append(f"{kriipsud}")
    empty_table = ""
    for i in empty_file:
        empty_table += i + "\n"
    return empty_table


def summon_table(a, kriipsude_a, final_dict, b=5):
    """Summon table."""
    time = a - 3
    items = b - 4
    kriipsud = "-" * (a + b + kriipsude_a)
    table = []
    tuhik = " "
    table.append(f"{kriipsud}")
    table.append(f"|{tuhik * time}time | items{tuhik * items}|")
    table.append(f"{kriipsud}")
    for i, j in final_dict.items():
        items = b + 1 - len(j[0])
        table.append(f"|{tuhik * 1}{i} | {j[0]}{tuhik * items}|")
    table.append(f"{kriipsud}")
    string_table = ""
    for i in table:
        string_table += i + "\n"
    return string_table


def which_table(final_dict, a, b):
    """Function that check, how big the table is and which table its should return."""
    if b < 6:
        return summon_table(a, 7, final_dict)
    elif b >= 6:
        return summon_table(a, 7, final_dict, b)


def create_table(correct_schedule):
    """Create table."""
    act_str = ""  # variable, that helps me to make multiple strinfs inside the list to one string
    for i, j in correct_schedule.items():
        if len(j) > 1:
            for x in j:
                act_str += ", " + x
            correct_schedule[i] = [act_str.lstrip(", ")]  # makes multiple strings from a list to one string
            act_str = ""
    a, b, list2 = get_table_sizes(correct_schedule)  # a is the first column length, b is the second column length
    final_dict = {}
    if 8 in list2:
        for i in correct_schedule.keys():
            if len(i) == 7:
                n = " " + i
                final_dict[n] = correct_schedule[i]
            else:
                final_dict[i] = correct_schedule[i]
    else:
        final_dict = correct_schedule
    if final_dict:
        a, b, list = get_table_sizes(final_dict)
        return which_table(final_dict, a, b)
    else:
        return no_times_table()


def get_table_sizes(correct_schedule: dict):
    """Get the maximum sizes for table, returns the length for time and activity column.

    To get the table column lenghts I sort the dict by its longest value, so that i can measure it.
    """
    if correct_schedule:
        x = sorted(correct_schedule.items(), key=lambda p: len(p[1][0]), reverse=True)
        list = []
        for i in correct_schedule.keys():
            list.append(len(i))
        a = len(x[0][0])
        b = len(x[0][1][0])
        return a, b, list


def normalize(activity):
    """Add missing 0's to the minutes and remove extra 0's from hours, one activity at a time."""
    if len(activity) > 0:
        activity.replace(".", ":")
        a = re.split(" + ", activity.strip())  # split the time from the activity
        b = re.split(':|,|-|[a-zA-Z]', a[0])  # split the hour from the minutes
        if len(b[0]) == 1:
            b[0] = "0" + b[0]
        if len(b[1]) == 1:
            b[1] = "0" + b[1]
        a[0] = b[0] + ":" + b[1]  # add up the time and the according activity
        return a


def get_formatted_time(times_sorted):
    """Format 24 hour time to the 12 hour time."""
    new_dict = {}
    if times_sorted:
        for i in range(len(times_sorted)):
            a = re.split(':', times_sorted[i][0])  # split the hours and minutes
            if a[0] == "00" or a[0] == "0":
                a[0] = "12"
                a[1] += " AM"
            elif a[0][0] == "0":
                a[0] = a[0][1]
                a[1] += " AM"
            elif a[0] == "10" or a[0] == "11":
                a[1] += " AM"
            elif 12 < int(a[0]) <= 23:
                a[0] = str(int(a[0]) - 12)
                a[1] += " PM"
            elif a[0] == "12":
                a[1] += " PM"
            new_dict[a[0] + ":" + a[1]] = times_sorted[i][1]  # new dict that has the correct key and the right activity
        return new_dict


def filter_schedule(list):
    """Filter the file or string for schedule times."""
    dict_of_time_and_activity = {}
    pattern = r"(\s{1})([0-1][0-9]|2[0-3]|[0-9])[\D|\W]([0-5]?[0-9]) +([a-zA-Z]+)"
    if len(list) > 0:
        for i in list:
            for match in re.finditer(pattern, i):
                time = match.group(2) + ":" + match.group(3)
                activity = match.group(4)
                if time in dict_of_time_and_activity.keys() and activity.lower() not in dict_of_time_and_activity[time]:
                    dict_of_time_and_activity[time].append(activity.lower())
                else:
                    dict_of_time_and_activity.setdefault(time, [activity.lower()])
        return dict_of_time_and_activity
    else:
        return False


def sort_the_schedule(dict_of_time_and_activity):
    """Sort the list of time and activiyty."""
    dict_of_schedule = {}
    if dict_of_time_and_activity:
        for i, j in dict_of_time_and_activity.items():
            a_list = normalize(i)  # normalize is a function that is being called out
            lamp = list(dict.fromkeys(j))
            for n in lamp:
                if a_list[0] in dict_of_schedule.keys() and n not in dict_of_schedule[a_list[0]]:
                    dict_of_schedule.setdefault(a_list[0]).append(n)
                else:
                    dict_of_schedule[a_list[0]] = lamp
        replacement = []
        for i, j in dict_of_schedule.items():
            for n in j:
                if n not in replacement:
                    replacement.append(n)
            dict_of_schedule[i] = replacement
            replacement = []
        correct_schedule = sorted(dict_of_schedule.items())  # sorts it by the time in rising order
        return correct_schedule


def is_file_empty(input_filename):
    """Check if file is empty."""
    with open(input_filename) as file:
        if file.read(1):
            return True
        else:
            return False


def create_schedule_file(input_filename: str, output_filename: str) -> None:
    """Create schedule file from the given input file."""
    if is_file_empty(input_filename):
        with open(input_filename) as file:  # reads the file
            list = file.readlines()
            for i in range(len(list)):
                if i != 0:
                    list[i] = " " + list[i]
            filtered_schedule = filter_schedule(list)
            if filtered_schedule:
                correct_schedule = get_formatted_time(sort_the_schedule(filtered_schedule))
                if correct_schedule:
                    with open(output_filename, "w") as f:
                        f.write(create_table(correct_schedule))
            else:
                with open(output_filename, "w") as f:
                    f.write(no_times_table())
    else:
        with open(output_filename, "w") as f:
            f.write(no_times_table())


def create_schedule_string(input_string: str) -> str:
    """Create schedule string from the given input string."""
    if len(input_string) > 0:
        simple_list = re.split("", input_string, 1)
        filtered_schedule = filter_schedule(simple_list)
        if filtered_schedule:
            correct_schedule = get_formatted_time(sort_the_schedule(filtered_schedule))
            return create_table(correct_schedule)
        else:
            return no_times_table()
    else:
        return no_times_table()


if __name__ == '__main__':
    print(create_schedule_string("a 1.2 abc 1.2 a 1.2 abc"))
    print(create_schedule_string("wat 1:00 sss"))
    print(create_schedule_string("wat 1:00 sssssssssssssss"))
    print(create_schedule_string("wat 12:00 sssssssssssssss"))

    create_schedule_file("schedule_input.txt", "schedule_output.txt")
