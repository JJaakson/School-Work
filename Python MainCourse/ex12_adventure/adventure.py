"""A world class adventure game."""
import math


class Adventurer():
    """The person class."""

    def __init__(self, name, class_type, power, experience=0):
        """Initialize adventureres attributes."""
        self.power = power
        self.name = name
        self.experience = experience
        c_types = ["Fighter", "Druid", "Wizard", "Paladin"]
        if class_type not in c_types:
            self.class_type = "Fighter"
        else:
            self.class_type = class_type

    def __repr__(self):
        """Return Adventurers attributes."""
        return f"{self.name}, the {self.class_type}, Power: {self.power}, Experience: {self.experience}."

    def add_experience(self, exp):
        """Add experience to the adventurers experience."""
        self.experience += exp

    def add_power(self, power):
        """Add power to the Adventurers power."""
        self.power += power


class Monster:
    """Monster class."""

    def __init__(self, name, mon_type, power=0):
        """Initialize Monster."""
        if mon_type == "Zombie":
            self.name = "Undead " + name
        else:
            self.name = name
        self.mon_type = mon_type
        self.power = power

    def __repr__(self):
        """Return monster attributes."""
        return f"{self.name} of type {self.mon_type}, Power: {self.power}."


class World:
    """World class."""

    def __init__(self, PM):
        """Initialize worlds atrributes."""
        self.python_master = PM
        self.adventurerlist = []
        self.monsterlist = []
        self.necromancer = False
        self.graveyard = []
        self.activeadventurers = []
        self.activemonsters = []

    def get_python_master(self):
        """Return PM."""
        return self.python_master

    def add_adventurer(self, adventurer):
        """Add adventurer to the list."""
        if isinstance(adventurer, Adventurer) and adventurer not in self.adventurerlist:
            self.adventurerlist.append(adventurer)

    def add_monster(self, monster):
        """Create a monster and add it to the list."""
        if isinstance(monster, Monster) and monster not in self.monsterlist:
            self.monsterlist.append(monster)

    def get_adventurerlist(self):
        """Get adventurerlist."""
        return self.adventurerlist

    def get_monsterlist(self):
        """Get monsterlist."""
        return self.monsterlist

    def change_necromancer(self, bool):
        """Change necromancer status."""
        if bool is True:
            self.necromancer = True
        else:
            self.necromancer = False

    def revive_graveyard(self):
        """Revive dead people."""
        if self.necromancer is True:
            undead = self.graveyard.copy()
            for i in undead:
                if isinstance(i, Monster):
                    i.mon_type = "Zombie"
                    self.monsterlist.append(i)
                    self.graveyard.remove(i)
                elif isinstance(i, Adventurer):
                    new = Monster(f"Undead {i.name}", f"Zombie {i.class_type}", i.power)
                    self.monsterlist.append(new)
                    self.graveyard.remove(i)

    def get_graveyard(self):
        """Return graveyard."""
        return self.graveyard

    @staticmethod
    def helper(list_item, attribute, reverse=False):
        """Help the main func by sorting."""
        if attribute == "power":
            return sorted(list_item, key=lambda i: i.power, reverse=reverse)
        elif attribute == "exp":
            return sorted(list_item, key=lambda i: i.experience, reverse=reverse)

    def class_type_list(self, class_type):
        """Return desired class type list."""
        result = []
        for i in self.adventurerlist:
            if i.class_type == class_type:
                result.append(i)
        return result

    def add_remove_adv(self, class_type, attribute, pos):
        """Helper func."""
        class_list = self.helper(self.class_type_list(class_type), attribute)
        self.activeadventurers.append(class_list[pos])
        self.adventurerlist.remove(class_list[pos])

    def add_strongest(self, class_type):
        """Make an adventurer active."""
        self.add_remove_adv(class_type, "power", -1)

    def add_weakest(self, class_type):
        """Make an adventurer active."""
        self.add_remove_adv(class_type, "power", 0)

    def add_most_experience(self, class_type):
        """Make an adventurer active."""
        self.add_remove_adv(class_type, "exp", -1)

    def add_least_experience(self, class_type):
        """Make an adventurer active."""
        self.add_remove_adv(class_type, "exp", 0)

    def add_remove_adv_more(self, attribute, attribute_str):
        """Help by adding more adventurers."""
        adventurers = self.adventurerlist.copy()
        if attribute_str == "name":
            for i in adventurers:
                if i.name == attribute and i not in self.activeadventurers:
                    self.activeadventurers.append(i)
                    self.adventurerlist.remove(i)
        elif attribute_str == "class":
            for i in adventurers:
                if i.class_type == attribute and i not in self.activeadventurers:
                    self.activeadventurers.append(i)
                    self.adventurerlist.remove(i)

    def add_by_name(self, name):
        """Make an adventurer active."""
        self.add_remove_adv_more(name, "name")

    def add_all_of_class_type(self, class_type):
        """Make adventurer(s) active."""
        self.add_remove_adv_more(class_type, "class")

    def add_all(self):
        """Make adventurer(s) active."""
        adventurers = self.adventurerlist.copy()
        for i in adventurers:
            if i not in self.activeadventurers:
                self.activeadventurers.append(i)
                self.adventurerlist.remove(i)

    def get_active_adventurers(self):
        """Return active adventurers."""
        return self.helper(self.activeadventurers, "exp", True)

    def add_monster_by_name(self, name):
        """Make monster active."""
        monsters = self.monsterlist.copy()
        for i in monsters:
            if i.name == name:
                self.activemonsters.append(i)
                self.monsterlist.remove(i)

    def add_remove_monst(self, pos):
        """Helper, make monster active."""
        monster_list = self.helper(self.monsterlist, "power")
        self.activemonsters.append(monster_list[pos])
        self.monsterlist.remove(monster_list[pos])

    def add_strongest_monster(self):
        """Make monster active."""
        self.add_remove_monst(-1)

    def add_weakest_monster(self):
        """Make monster active."""
        self.add_remove_monst(0)

    def add_all_of_type(self, mon_type):
        """Make monster(s) active."""
        monsters = self.monsterlist.copy()
        for i in monsters:
            if i.mon_type == mon_type and i not in self.activemonsters:
                self.activemonsters.append(i)
                self.monsterlist.remove(i)

    def add_all_monsters(self):
        """Make all monster(s) active."""
        monsters = self.monsterlist.copy()
        for i in monsters:
            if i not in self.activemonsters:
                self.activemonsters.append(i)
                self.monsterlist.remove(i)

    def get_active_monsters(self):
        """Return active monsters."""
        return self.helper(self.activemonsters, "power", True)

    def remove_character(self, name):
        """Remove character with the name."""
        if any(x.name == name for x in self.adventurerlist):
            for i in self.adventurerlist:
                if i.name == name:
                    self.adventurerlist.remove(i)
        elif any(x.name == name for x in self.monsterlist):
            for i in self.monsterlist:
                if i.name == name:
                    self.monsterlist.remove(i)
        elif any(x.name == name for x in self.graveyard):
            for i in self.graveyard:
                if i.name == name:
                    self.graveyard.remove(i)

    def combined_power(self):
        """Return combined powers of adventurers and monsters."""
        adv_power = 0
        for i in self.activeadventurers:
            adv_power += i.power
        mon_power = 0
        for i in self.activemonsters:
            mon_power += i.power
        return adv_power, mon_power

    def check_druids(self):
        """Check if there are any druids in the adventure group, if there are, then make Animal and Ent.

        type monsters unactive.
        """
        druid = False
        for i in self.activeadventurers:
            if i.class_type == "Druid":
                druid = True
                break
        monster = self.activemonsters.copy()
        if druid is True:
            for i in monster:
                if i.mon_type == "Animal" or i.mon_type == "Ent":
                    self.activemonsters.remove(i)
                    self.monsterlist.append(i)

    def check_paladins(self):
        """Check if there are any paladins fighting specific monsters, if are double paladins power."""
        monsters = ["Zombie", "Zombie Fighter", "Zombie Druid", "Zombie Paladin", "Zombie Wizard"]
        double = False
        for i in self.activemonsters:
            if i.mon_type in monsters:
                double = True
                break
        if double:
            for i in self.activeadventurers:
                if i.class_type == "Paladin":
                    i.power = i.power * 2

    def strip_from_power(self):
        """If paladins got power, remove it."""
        monsters = ["Zombie", "Zombie Fighter", "Zombie Druid", "Zombie Paladin", "Zombie Wizard"]
        double = False
        for i in self.activemonsters:
            if i.mon_type in monsters:
                double = True
                break
        if double:
            for i in self.activeadventurers:
                if i.class_type == "Paladin":
                    i.power = i.power // 2

    @staticmethod
    def determine_winner(adv_power, mon_power):
        """Determine winner of the battle."""
        if adv_power > mon_power:
            return "Adventurers"
        elif adv_power < mon_power:
            return "Monsters"
        return "Draw"

    def return_adv(self, dead=False):
        """Return winners to their lists, and losers to graveyard."""
        adventures = self.activeadventurers.copy()
        for i in adventures:
            if dead is True:
                self.graveyard.append(i)
            else:
                self.adventurerlist.append(i)
            self.activeadventurers.remove(i)

    def return_monst(self, dead=False):
        """Return winners to their lists, and losers to graveyard."""
        monsters = self.activemonsters.copy()
        for i in monsters:
            if dead is True:
                self.graveyard.append(i)
            else:
                self.monsterlist.append(i)
            self.activemonsters.remove(i)

    def increase_exp(self, mon_power, multiplier=1.0):
        """Increase experience."""
        add_exp = int(math.floor(int(mon_power / len(self.activeadventurers)) * multiplier))
        for i in self.activeadventurers:
            i.experience += add_exp

    def go_adventure(self, deadly=False):
        """Start the journey."""
        self.check_druids()
        self.check_paladins()
        adv_power, mon_power = self.combined_power()
        result = self.determine_winner(adv_power, mon_power)
        self.strip_from_power()
        if deadly is False:
            if result == "Adventurers":
                self.increase_exp(mon_power)
            elif result == "Draw":
                self.increase_exp(mon_power, 0.5)
            self.return_adv(False)
            self.return_monst(False)
        elif deadly is True:
            if result == "Adventurers":
                self.increase_exp(mon_power, 2.0)
                self.return_adv(False)
                self.return_monst(True)
            elif result == "Monsters":
                self.return_adv(True)
                self.return_monst(False)
            elif result == "Draw":
                self.increase_exp(mon_power)
                self.return_adv(False)
                self.return_monst(False)


if __name__ == "__main__":
    print("Kord oli maailm.")
    Maailm = World("Sõber")
    print(Maailm.get_python_master())  # -> "Sõber"
    print(Maailm.get_graveyard())  # -> []
    print()
    print("Tutvustame tegelasi.")
    Kangelane = Adventurer("Sander", "Paladin", 50)
    Tüütu_Sõber = Adventurer("XxX_Eepiline_Sõdalane_XxX", "Tulevikurändaja ja ninja", 999999)
    Lahe_Sõber = Adventurer("Peep", "Druid", 25)
    Teine_Sõber = Adventurer("Toots", "Wizard", 40)

    print(Kangelane)  # -> "Sander, the Paladin, Power: 50, Experience: 0."
    # Ei, tüütu sõber, sa ei saa olla tulevikurändaja ja ninja, nüüd sa pead fighter olema.
    print(Tüütu_Sõber)  # -> "XxX_Eepiline_Sõdalane_XxX, the Fighter, Power: 999999, Experience: 0."

    print("Sa ei tohiks kohe alguses ka nii tugev olla.")
    Tüütu_Sõber.add_power(-999959)
    print(Tüütu_Sõber)  # -> XxX_Eepiline_Sõdalane_XxX, the Fighter, Power: 40, Experience: 0.
    print()
    print(Lahe_Sõber)  # -> "Peep, the Druid, Power: 25, Experience: 0."
    print(Teine_Sõber)  # -> "Toots, the Wizard, Power: 40, Experience: 0."
    print()
    Lahe_Sõber.add_power(20)
    print("Sa tundud kuidagi nõrk, ma lisasin sulle natukene tugevust.")
    print(Lahe_Sõber)  # -> "Peep, the Druid, Power: 45, Experience: 0."

    Maailm.add_adventurer(Kangelane)
    Maailm.add_adventurer(Lahe_Sõber)
    Maailm.add_adventurer(Teine_Sõber)
    print(Maailm.get_adventurerlist())  # -> Sander, Peep ja Toots

    Maailm.add_monster(Tüütu_Sõber)
    # Ei, tüütu sõber, sa ei saa olla vaenlane.
    print(Maailm.get_monsterlist())  # -> []
    Maailm.add_adventurer(Tüütu_Sõber)

    print()
    print()
    print("Oodake veidikene, ma tekitan natukene kolle.")
    Zombie = Monster("Rat", "Zombie", 10)
    GoblinSpear = Monster("Goblin Spearman", "Goblin", 50)
    GoblinArc = Monster("Goblin Archer", "Goblin", 5)
    BigOgre = Monster("Big Ogre", "Ogre", 120)
    GargantuanBadger = Monster("Massive Badger", "Animal", 1590)

    print(BigOgre)  # -> "Big Ogre of type Ogre, Power: 120."
    print(Zombie)  # -> "Undead Rat of type Zombie, Power: 10."

    Maailm.add_monster(GoblinSpear)

    print()
    print()
    print("Mängime esimese kakluse läbi!")
    Maailm.add_strongest("Paladin")
    Maailm.add_strongest_monster()
    print(Maailm.get_active_adventurers())  # -> Peep
    print(Maailm.get_active_monsters())  # -> [Goblin Spearman of type Goblin, Power: 10.]

    Maailm.go_adventure(True)
    print(Maailm.get_active_adventurers())  # -> Peep
    print(Maailm.get_active_monsters())
    Maailm.add_strongest("Paladin")
    print(Maailm.get_active_adventurers())  # -> [Peep, the Druid, Power: 45, Experience: 20.]
    print("Surnuaias peaks üks goblin olema.")
    print(Maailm.get_graveyard())  # ->[Goblin Spearman of type Goblin, Power: 10.]

    Maailm.add_monster(GargantuanBadger)
    Maailm.add_strongest_monster()

    Maailm.go_adventure(True)
    # Druid on loomade sõber, ja ajab massiivse mägra ära.
    print(Maailm.get_adventurerlist())  # -> Kõik 4 mängijat.
    print(Maailm.get_monsterlist())  # -> [Massive Badger of type Animal, Power: 1590.]

    Maailm.remove_character("Massive Badger")
    print(Maailm.get_monsterlist())  # -> []

    print(
        "Su sõber ütleb: "
        "\"Kui kõik need testid andsid sinu koodiga sama tulemuse mille ma siin ette kirjutasin, peaks kõik okei olema, proovi testerisse pushida! \" ")
