"""Converter."""


def dec_to_binary(dec: int) -> str:
    """
    Convert decimal number into binary.

    :param dec: decimal number to convert
    :return: number in binary
    """
    binary = ""
    if dec > 0:
        while dec > 0:
            binary = str(dec % 2) + binary
            dec = dec // 2
        return binary
    elif dec == 0:
        binary += "0"
        return binary


def binary_to_dec(binary: str) -> int:
    """
    Convert binary number into decimal.

    :param binary: binary number to convert
    :return: number in decimal
    """
    dec = 0
    for i in binary:
        dec = dec * 2 + int(i)
    return dec

sona = "1234"
print(int(sona[1:3]))

if __name__ == "__main__":
    print(dec_to_binary(145))  # -> 10010001
    print(dec_to_binary(245))  # -> 11110101
    print(dec_to_binary(255))  # -> 11111111
    print(dec_to_binary(0))

    print(binary_to_dec("1111"))  # -> 15
    print(binary_to_dec("10101"))  # -> 21
    print(binary_to_dec("10010"))  # -> 18
