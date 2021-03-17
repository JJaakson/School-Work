"""Test for ex08_solution."""
import solution


def test_students_study1():
    """Time is 1 and student needs coffee."""
    assert solution.students_study(1, True) is False


def test_students_study2():
    """Time is 1 and student does not need coffee."""
    assert solution.students_study(1, False) is False


def test_students_study3():
    """Time is 4 and student needs coffee."""
    assert solution.students_study(4, True) is False


def test_students_study4():
    """Time is 4 and student does not need coffee."""
    assert solution.students_study(4, False) is False


def test_students_study5():
    """Time is 5 and student needs coffee."""
    assert solution.students_study(5, True) is True


def test_students_study6():
    """Time is 5 and student does not need coffee."""
    assert solution.students_study(5, False) is False


def test_students_study7():
    """Time is 17 and student needs coffee."""
    assert solution.students_study(17, True) is True


def test_students_study8():
    """Time is 17 and student does not need coffee."""
    assert solution.students_study(17, False) is False


def test_students_study9():
    """Time is 18 and student needs coffee."""
    assert solution.students_study(18, True) is True


def test_students_study10():
    """Time is 18 and student does not need coffee."""
    assert solution.students_study(18, False) is True


def test_students_study11():
    """Time is 24 and student needs coffee."""
    assert solution.students_study(24, True) is True


def test_students_study12():
    """Time is 24 and student does not need coffee."""
    assert solution.students_study(24, False) is True


def test_lottery13():
    """Testing my chances."""
    assert solution.lottery(1, 2, 3) == 1


def test_lotter14():
    """Testing my chances."""
    assert solution.lottery(1, 1, 2) == 0


def test_lottery15():
    """Testing my chances."""
    assert solution.lottery(1, 1, 1) == 5


def test_lottery16():
    """Testing my chances."""
    assert solution.lottery(5, 5, 5) == 10


def test_lottery17():
    """Testing my chances."""
    assert solution.lottery(2, 1, 1) == 1


def test_lottery18():
    """Testing my chances."""
    assert solution.lottery(1, 2, 1) == 0


def test_lottery19():
    """Testing my chances."""
    assert solution.lottery(-5, -5, -5) == 5


def test_lottery20():
    """Testing my chances."""
    assert solution.lottery(0, 0, 0) == 5


def test_fruit_order21():
    """Testing fruit order."""
    assert solution.fruit_order(0, 0, 0) == 0


def test_fruit_order22():
    """Testing fruit order."""
    assert solution.fruit_order(1, 2, 3) == -1


def test_fruit_order23():
    """Testing fruit order."""
    assert solution.fruit_order(1, 0, 1) == 1


def test_fruit_order24():
    """Testing fruit order."""
    assert solution.fruit_order(3, 3, 7) == 2


def test_fruit_order25():
    """Testing fruit order."""
    assert solution.fruit_order(3, 3, 8) == 3


def test_fruit_order26():
    """Testing fruit order."""
    assert solution.fruit_order(10, 3, 9) == 4


def test_fruit_order27():
    """Testing fruit order."""
    assert solution.fruit_order(10, 1, 10) == 5


def test_fruit_order28():
    """Testing fruit order."""
    assert solution.fruit_order(0, 1, 5) == 0


def test_fruit_order29():
    """Testing fruit order."""
    assert solution.fruit_order(3, 1, 9) == -1


def test_fruit_order30():
    """Testing fruit order."""
    assert solution.fruit_order(10, 1, 15) == 10


def test_fruit_order31():
    """Testing fruit order."""
    assert solution.fruit_order(99, 99, 0) == 0


def test_fruit_order32():
    """Testing fruit order."""
    assert solution.fruit_order(99, 0, 0) == 0


def test_fruit_order33():
    """Testing fruit order."""
    assert solution.fruit_order(0, 99, 0) == 0


def test_fruit_order34():
    """Testing fruit order."""
    assert solution.fruit_order(50, 2, 101) == -1


def test_fruit_order35():
    """Testing fruit order."""
    assert solution.fruit_order(2, 55, 103) == -1


def test_fruit_order36():
    """Testing fruit order."""
    assert solution.fruit_order(0, 1, 4) == -1


def test_fruit_order37():
    """Testing fruit order."""
    assert solution.fruit_order(1, 0, 2) == -1


def test_fruit_order38():
    """Testing fruit order."""
    assert solution.fruit_order(66, 0, 66) == 66


def test_fruit_order39():
    """Testing fruit order."""
    assert solution.fruit_order(0, 1, 6) == -1


def test_fruit_order40():
    """Testing fruit order."""
    assert solution.fruit_order(101, 101, 606) == 101


def test_fruit_order41():
    """Testing fruit order."""
    assert solution.fruit_order(0, 15, 80) == -1


def test_fruit_order42():
    """Testing fruit order."""
    assert solution.fruit_order(7, 0, 8) == -1


def test_fruit_order43():
    """Testing fruit order."""
    assert solution.fruit_order(9, 0, 8) == 8


def test_fruit_order44():
    """Testing fruit order."""
    assert solution.fruit_order(0, 7, 25) == 0
