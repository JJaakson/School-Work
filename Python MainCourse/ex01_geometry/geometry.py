"""Ask user a shape and a radius or a side length and calculate the shape area."""

import math

shape = input("Please insert geometric shape: ")

if shape == "circle":
    radius = float(input("Please insert radius in cm:"))
    print("The area is " + str(round(math.pi * radius ** 2, 2)) + " cm^2 ")
elif shape == "square":
    side_length_square = float(input("Please insert side length in cm:"))
    print("The area is " + str(round(side_length_square ** 2, 2)) + " cm^2 ")
elif shape == "triangle":
    side_length_triangle = float(input("Please insert side length in cm:"))
    print("The area is " + str(round((side_length_triangle ** 2 * math.sqrt(3)) / 4, 2)) + " cm^2 ")
else:
    print("Shape is not supported.")
