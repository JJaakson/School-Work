"""Primes identifier."""


def is_prime_number(number: int) -> bool:
    """
    Check if number (given in function parameter) is prime.

    If number is prime -> return True
    If number is not prime -> return False

    :param number: number for check.
    :return: boolean True if number is prime or False if number is not prime.
    """
    if number > 1:
        for i in range(2, number):
            if (number % i) == 0:
                return False
        else:
            return True
