"""Encode and decode text using Rail-fence Cipher."""


def generate_field(key, msg):
    """Generate a "playground" to use zig-zag method."""
    rows = []
    for j in range(key):
        rows.append([])
        for i in range(len(msg)):
            rows[j].append(".")
    return rows


def encode(message: str, key: int) -> str:
    """
    Encode text using Rail-fence Cipher.

    Replace all spaces with '_'.

    :param message: Text to be encoded.
    :param key: Encryption key.
    :return: Decoded string.
    """
    msg = message.replace(" ", "_")
    if key > 1:
        rows = generate_field(key, msg)
        row_dir = False  # Rows counting down
        row, col = 0, 0
        for i in range(len(msg)):
            if (row == 0) or (row == key - 1):  # if row count is the most upward or downward in the "playground"
                row_dir = not row_dir  # change the direction of rows
            rows[row][col] = msg[i]  # replaces the '.' in "zigzag method
            col += 1
            if row_dir:
                row += 1
            else:
                row -= 1
        encoded_msg = []
        for i in range(key):
            for j in range(len(msg)):
                if rows[i][j] != ".":
                    encoded_msg.append(rows[i][j])  # appends the list, if it is not '.'
        return "" . join(encoded_msg)  # joins the symbols to create encoded message and returns it
    else:
        return msg


def decoded_msg(msg, key, rows):
    """Add up the symbols that are not "."."""
    txt = ""
    row_dir = False
    row, col = 0, 0
    for j in range(key):
        for i in range(len(msg)):
            if rows[j][i] != ".":
                if (row == 0) or (row == key - 1):
                    row_dir = not row_dir
                txt += rows[row][col]
                col += 1
                if row_dir:
                    row += 1
                else:
                    row -= 1
    return txt


def decode(message: str, key: int) -> str:
    """
    Decode text knowing it was encoded using Rail-fence Cipher.

    '_' have to be replaced with spaces.

    :param message: Text to be decoded.
    :param key: Decryption key.
    :return: Decoded string.
    """
    msg = message.replace("_", " ")
    if key > 1:
        rows = generate_field(key, msg)
        rows_dir = False
        row, col = 0, 0
        for i in range(len(msg)):
            if (row == 0) or (row == key - 1):
                rows_dir = not rows_dir
            rows[row][col] = "*"  # adds '*' on the "playground" where the coded symbol should be
            col += 1
            if rows_dir:
                row += 1
            else:
                row -= 1
        index = 0
        for j in range(key):
            for i in range(len(msg)):
                if rows[j][i] == "*":
                    rows[j][i] = msg[index]  # replaces '*' with the right symbol
                    index += 1
        return decoded_msg(msg, key, rows)
    else:
        return msg


if __name__ == '__main__':
    print(encode("Mind on vaja krüpteerida", 3))  # => M_v_prido_aaküteiannjred
    print(encode("Mind on", 3))  # => M_idonn
    print(encode("hello", 1))  # => hello
    print(encode("kaks pead", 0))  # => kaks_pead

    print(decode("kaks_pead", 1))  # => kaks pead
    print(decode("M_idonn", 3))  # => Mind on
    print(decode("M_v_prido_aaküteiannjred", 3))  # => Mind on vaja krüpteerida
