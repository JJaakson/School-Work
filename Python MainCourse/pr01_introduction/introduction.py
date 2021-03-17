"""My first program."""

print("Hello, my name is Python! Please type your name to continue our conversation.")
name = input("")
programmed_before = input("Have you programmed before? ")

if programmed_before == "Yes":
    print("Congratulations, " + name + "! It will be a little bit easier for you.")
elif programmed_before == "No":
    print("Don`t worry, " + name + "! You will learn everything you need.")
else:
    print("Your input is incorrect!")
