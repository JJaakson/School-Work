"""Make pizzeria work."""
from math import pi


class Chef:
    """Chef class."""

    def __init__(self, name: str, experience_level: int):
        """Initialize chefs attributes."""
        self.name = name
        self.experience_level = experience_level
        self.money = 0

    def __repr__(self):
        """Return chef's info."""
        return f"Pizza chef {self.name.capitalize()} with {self.experience_level} XP"


class Pizza:
    """Pizza class."""

    def __init__(self, name: str, diameter: int, toppings: list):
        """Initialize pizza's attributes."""
        self.name = name
        self.diameter = diameter
        self.toppings = toppings

    def calculate_complexity(self) -> int:
        """Calculate pizzas complexity."""
        result = 0
        for i in self.toppings:
            result += len(i) // 3
        return result

    def calculate_price(self) -> int:
        """Calculate pizza's price."""
        area = pi * ((self.diameter / 2) ** 2)
        price = area / 40 + len(self.toppings) // 2
        return int(price * 100 // 1)

    def __repr__(self):
        """Return pizza's info."""
        return f"{self.name.capitalize()} pizza with a price of {self.calculate_price() / 100}"


class Pizzeria:
    """Pizzeria class."""

    def __init__(self, name: str, is_fancy: bool, budget: int):
        """Initialize pizzeria's attributes."""
        self.name = name
        self.is_fancy = is_fancy
        self.budget = budget
        self.pizzas = []
        self.chefs = []
        self.baked_pizzas = []
        self.charity = 0

    def add_chef(self, chef: Chef) -> Chef or None:
        """Add chef to the chef list."""
        if chef.name not in self.chefs:
            if self.is_fancy and chef.experience_level >= 25:
                self.chefs.append(chef)
                return chef
            elif not self.is_fancy:
                self.chefs.append(chef)
                return chef

    def remove_chef(self, chef: Chef):
        """Remove the chef from the che list."""
        if chef in self.chefs:
            self.chefs.remove(chef)

    def add_pizza_to_menu(self, pizza: Pizza):
        """Add pizza to the menu."""
        if pizza.calculate_price() < self.budget and pizza not in self.pizzas and len(self.chefs) > 0:
            self.pizzas.append(pizza)
            self.budget -= pizza.calculate_price()

    def remove_pizza_from_menu(self, pizza: Pizza):
        """Remove pizza from the menu."""
        if pizza in self.pizzas:
            self.pizzas.remove(pizza)

    def bake_pizza(self, pizza: Pizza) -> Pizza or None:
        """Make a pizza."""
        if pizza in self.pizzas:
            helper = []  # Helps find the best pizza chef to bake the wanted pizza
            for c in self.chefs:
                if c.experience_level >= pizza.calculate_complexity():
                    helper.append((c.experience_level - pizza.calculate_complexity(), c))
                    # Creates a tuple for each chef, with  the diff between the chefs experience and the pizzas \
                    # complexity and adds the chef object as the second element in the tuple \
                    # example: chefxp = 16, pizzacomplex = 14 --> helper = [(16-14, chefobject), ...]
                else:
                    return None
            helper.sort(key=lambda c: c[0])  # Sorts the tuples inside the list, based on the exp and complex diff
            best_chef = helper[0][1]  # Chooses the best chef to bake the pizza
            self.baked_pizzas.append(pizza)  # "Bakes" the pizza, by adding it to the baked pizza list
            best_chef.experience_level += len(pizza.name) // 2  # Increases the chefs experience
            if pizza.calculate_price() * 4 + len(pizza.name) % 2 == 1:
                self.charity += 1  # If pizzas profit is with odd ending, it adds 1 cent to charity
            best_chef.money += (pizza.calculate_price() * 4 + len(pizza.name)) // 2  # Increases the chefs money
            self.budget += (pizza.calculate_price() * 4 + len(pizza.name)) // 2  # Increases the budget
            return pizza

    def get_pizza_menu(self) -> list:
        """Return pizza menu."""
        return sorted(self.pizzas, key=lambda p: p.calculate_price(), reverse=True)

    def get_baked_pizzas(self) -> dict:
        """Return baked pizzas."""
        result = {}
        for i in self.baked_pizzas:
            if i not in result:
                result[i] = self.baked_pizzas.count(i)
        return result

    def get_chefs(self) -> list:
        """Return chef list."""
        return sorted(self.chefs, key=lambda c: c.experience_level)

    def __repr__(self):
        """Return pizzeria's info."""
        return f"{self.name.capitalize()} with {len(self.chefs)} pizza chef(s)."


if __name__ == '__main__':
    pass
