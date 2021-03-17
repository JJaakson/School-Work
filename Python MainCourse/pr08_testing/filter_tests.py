"""Tests for pr04."""
import filter
import random


def test_remove_vowels_one_vowel():
    """Test one vowel."""
    vowels = "aeiouAEIOU"
    for i in vowels:
        assert filter.remove_vowels(i) == ""


def test_remove_vowels_empty_string():
    """Test empty string."""
    assert filter.remove_vowels("") == ""


def test_remove_vowels_removes_wrong_ascii_letters():
    """Test ascii letters."""
    assert filter.remove_vowels("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ") == "bcdfghjklmnpqrstvwxyz" \
                                                                                           "BCDFGHJKLMNPQRSTVWXYZ"


def test_remove_vowels_when_non_vowels_longer():
    """Test with 0 vowels."""
    assert filter.remove_vowels("drhll, pnck") == "drhll, pnck"


def test_remove_vowels_mixed_cases_no_vowels():
    """Test with 0 vowels, mixed cases."""
    assert filter.remove_vowels("DRhlL") == "DRhlL"


def test_remove_vowels_only_vowels_longer():
    """Test only vowels, longer."""
    assert filter.remove_vowels("AEIOUaeiouAAEEIIOOUUaaeeiioouu") == ""


def test_longest_filtered_word_empty():
    """Test if input is empty."""
    assert filter.longest_filtered_word([]) is None


def test_longest_filtered_word_vowels_in_strings():
    """Test if only vowels in string."""
    assert filter.longest_filtered_word(["aei"]) == ""


def test_longest_filtered_word_no_vowels_in_strings():
    """Test if only non-vowels in string."""
    assert filter.longest_filtered_word(["blck"]) == "blck"


def test_longest_filtered_word_empty_string():
    """Test if input is empty string."""
    assert filter.longest_filtered_word([""]) == ""


def test_longest_filtered_word_same_length_leftmost():
    """Test if it takes the first element, if there are multiple words with the same length."""
    assert filter.longest_filtered_word(["black", "locked"]) == "blck"


def test_sort_list_empty_list():
    """Test if list is empty."""
    assert filter.sort_list([]) == []


def test_sort_list_list_len1():
    """Test if list length is 1."""
    assert filter.sort_list(["black"]) == ["blck"]


def test_sort_list_should_not_change_input_list():
    """Test so that the function to see if it changes the input list."""
    simple = ["bb", "cc", "dd"]
    correct = simple.copy()
    assert filter.sort_list(correct) == correct


def test_sort_list_correct_order_with_same_length():
    """Test if the order is correct when, there are multiple words with the same length."""
    assert filter.sort_list(["banaa", "todo"]) == ["bn", "td"]


def test_random():
    """Test with random inputs."""
    alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    list = []
    for i in alphabet:
        list.append(i)
    result = filter.sort_list(random.choices(list, k=20))
    input_list = result[:]
    assert filter.sort_list(input_list) == result
