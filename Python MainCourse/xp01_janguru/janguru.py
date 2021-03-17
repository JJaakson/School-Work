"""Jänguru, kuna Jängurud kokku saavad"""


def meet_me(pos1, jump_distance1, sleep1, pos2, jump_distance2, sleep2):
    """janguru_1 = [pos1, jump_distance1, sleep1]
    janguru_2 = [pos2, jump_distance2, sleep2]"""
    for i in range(1000):
        if i == sleep1:
            pos1 += jump_distance1
            sleep1 += sleep1
        if i == sleep2:
            pos2 += jump_distance2
            sleep2 += sleep2
        if pos1 == pos2:
            print(pos1)
            break

meet_me(1, 2, 1, 2, 1, 1)
meet_me(1, 2, 3, 4, 5, 5)
meet_me(10, 7, 7, 5, 8, 6)
meet_me(100, 7, 4, 300, 8, 6)
meet_me(1, 7, 1, 15, 5, 1)
meet_me(0, 1, 1, 1, 1, 1)