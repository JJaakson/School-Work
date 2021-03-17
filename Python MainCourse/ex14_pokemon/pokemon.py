"""Pokemon."""
import json
import requests
import re
import os.path
from os import path


class SamePokemonFightException(Exception):
    """Custom exception thrown when same pokemons are fighting."""
    pass


class PokemonFightResultsInATieException(Exception):
    """Custom exception thrown when the fight lasts longer than 100 rounds."""
    pass


class Pokemon:
    """Class for Pokemon."""
    def __init__(self, url_or_path_name: str):
        """
        Class constructor.
        :param url_or_path_name: url or json object.
        If it is url, then parse information from request to proper
        json file and save it to self.data.
 1       If it is a string representation of a json object, then parse it into json object and save to self.data
        """
        self.score = 0
        self.data = {}
        self.multiplier_matrix = {}
        if self.check_url(url_or_path_name) is True:
            self.parse_json_to_pokemon_information(url_or_path_name)
        else:
            self.data = url_or_path_name

    def check_url(self, url):
        """Check if theres a url."""
        result = re.match(r'http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\(\), ]|(?:%[0-9a-fA-F][0-9a-fA-F]))+', url)
        if result:
            return True
        return False

    def parse_json_to_pokemon_information(self, url):
        """
        :param url: url where the information is requested.
        Called from constructor and this method requests data from url to parse it into proper json object
        and then saved under self.data example done previously
        """
        response = requests.get(url).json()
        self.data["name"] = response["name"]
        for i in response["stats"]:
            self.data[i["stat"]["name"]] = int(i['base_stat'])
        self.data["types"] = []
        for i in response["types"]:
            self.data["types"].append(i["type"]["name"])
        self.data["abilities"] = []
        for i in response["abilities"]:
            self.data["abilities"].append(i["ability"]["name"])
        self.data["forms"] = []
        for i in response["forms"]:
            self.data["forms"].append(i["name"])
        self.data["moves"] = []
        for i in response["moves"]:
            self.data["moves"].append(i["move"]["name"])
        self.data["height"] = int(response["height"])
        self.data["weight"] = int(response["weight"])
        self.data["base_experience"] = int(response["base_experience"])

    def get_attack_multiplier(self, other: list):
        """
        self.pokemon is attacking, other is defending
        :param other: list of other pokemon2.data['types']
        Calculate Pokemons attack multiplier against others types and take the best result.
        get the initial multiplier from Fighting Multiplier matrix.
        For example if self.type == ['fire'] and other == ['ground']: return fighting_multipliers['fire']['ground']
        if the defendant has dual types, then multiply the multipliers together.
        if the attacker has dual-types, then the best option is
        chosen(attack can only be of 1 type, choose better[higher multiplier])
        :return: Multiplier.
        """
        multipliers = []
        for i in self.data['types']:
            for j in other:
                d_pos = self.multiplier_matrix['defence'].index(j)
                multipliers.append(self.multiplier_matrix[i][d_pos])
        multiplied_multipliers = []
        if len(other) > 1:
            for i in multipliers:
                multiplier = float(i)
                for j in multipliers:
                    multiplier *= float(j)
                multiplied_multipliers.append(multiplier)
        if len(self.data['types']) > 1:
            multiplied_multipliers.sort(reverse=False)
        return multiplied_multipliers[0]

    def get_pokemon_attack(self, turn_counter):
        """
        :param turn_counter: every third round the attack is empowered. (return self.data['special-attack'])
        otherwise basic attack is returned (self.data['attack'])
        """
        if turn_counter % 3 == 0:
            return float(self.data['special_attack'])
        else:
            return float(self.data['attack'])

    def get_pokemon_defense(self, turn_counter):
        """
        Note: whatever the result is returned, return half of it instead (for example return self.data['defense'] / 2)
        :param turn_counter: every second round the defense is empowered. (return self.data['special-defense'])
        otherwise basic defense is returned (self.data['defense'])
        """
        if turn_counter % 2 == 0:
            return float(self.data['special_defense']) / 2
        else:
            return float(self.data['defense']) / 2

    def __str__(self):
        """
        String representation of json(self.data) object.
        One way to accomplish this is to use json.dumps functionality
        :return: string version of json file with necessary information
        """
        return json.dumps(self.data)

    def __repr__(self):
        """
        Object representation.
        :return: Pokemon's name in string format and his score, for example: "garchomp-mega 892"
        """
        return f"{self.data['name']} {self.score}"


class World:
    """World class."""
    def __init__(self, name, offset, limit):
        """
        Class constructor.
        :param name: name of the pokemon world
        :param offset: offset for api request
        :param limit: limit for api request
        Check if f"{name}_{offset}_{limit}.txt" file exists, if it does, read pokemons in from that file, if not, then make an api
        request to f"https://pokeapi.co/api/v2/pokemon?offset={offset}&limit={limit}" to get pokemons and dump them to
        f"{name}_{offset}_{limit}.txt" file
        """
        self.poke_fights = []
        self.pokemons = []
        self.fighting_multipliers = {}
        self.multipliers_matrix("fighting_multipliers.txt")
        if path.exists(f"{name}_{offset}_{limit}.txt"):
            x = open(f"{name}_{offset}_{limit}.txt")
            for line in x:
                poke = Pokemon(line)
                poke.multiplier_matrix = self.fighting_multipliers
                self.pokemons.append(poke)
        else:
            result = requests.get(f"https://pokeapi.co/api/v2/pokemon?offset={offset}&limit={limit}").json()
            for pokemon in result['results']:
                poke = Pokemon(pokemon['url'])
                poke.multiplier_matrix = self.fighting_multipliers
                self.pokemons.append(poke)

    def multipliers_matrix(self, input_file):
        """Add the multipliers to a dict."""
        file = open(input_file, "r")
        for line in file:
            l_info = line.split()
            if l_info[2].isalpha():
                self.fighting_multipliers["defence"] = l_info
            else:
                self.fighting_multipliers[l_info[0]] = l_info[1:]
        file.close()

    def dump_pokemons_to_file_as_json(self, name):
        """
        :param name: name of the .txt file
        Write all self.pokemons separated by a newline to the given filename(if it doesnt exist, then create one)
        PS: Write the pokemon.__str__() version, not __repr__() as only name is useless :)
        """
        f = open(name, "w")
        for p in self.pokemons:
            f.write(json.dumps(p.data) + "\n")
        f.close()

    def fight(self):
        """
        A wild brawl between all pokemons where points are assigned to winners
        Note, every pokemon fights another pokemon only once
        Fight lasts until one pokemon runs out of hp.
        every pokemon hits only 1 time per turn and they take turns when they attack.
        Call choose_which_pokemon_hits_first(pokemon1, pokemon2): to determine which pokemon hits first
        Call pokemon_duel function in this method with the aforementioned pokemons.
        every exception thrown by called sub methods must be caught and dealt with.
        """
        for i in self.pokemons:
            for j in self.pokemons:
                if i != j and (i, j) not in self.poke_fights or i != j and (j, i) not in self.poke_fights:
                    pokemons = self.choose_which_pokemon_hits_first(i, j)
                    self.pokemon_duel(pokemons[0], pokemons[1])

    @staticmethod
    def pokemon_duel(pokemon1, pokemon2):
        """
        :param pokemon1: pokemon, who attacks first.
        :param pokemon2: pokemon, who attacks second.
        :return winner: pokemon, who won.

        Here 2 pokemons fight.
        To get the attack and defense of the pokemon, call pokemon1.get_pokemon_attack()
        and pokemon1.get_pokemon_defense() respectively.
        Attack is multiplied by the pokemon1.get_attack_multiplier(list(second.data['types'])) multiplier
        Total attack is
        pokemon1.get_pokemon_attack(turn_counter) * multiplier1 - second.get_pokemon_defense(turn_counter)
        [turn counter starts from 1]
        Total attack is subtracted from other pokemons hp.
        Pokemons can not heal during the fight. (when total attack is negative, no damage is dealt)
        If the fight between 2 pokemons lasts more than 100 turns, then PokemonFightResultsInATieException() is thrown.
        If one pokemon runs out of hp, fight ends and the winner gets 1 point, (self.score += 1)
        then both pokemons are healed to full hp.
        """
        t_c = 0
        m1 = pokemon1.get_attack_multiplier(pokemon2.data['types'])
        m2 = pokemon2.get_attack_multiplier(pokemon1.data['types'])
        starting_hp = [pokemon1.data['hp'], pokemon2.data['hp']]
        while pokemon1.data['hp'] > 0 and pokemon2.data['hp'] > 0:
            t_c += 1
            pokemon2.data['hp'] -= pokemon1.get_pokemon_attack(t_c) * m1 - pokemon2.get_pokemon_defense(t_c)
            if pokemon2.data['hp'] > 0:
                pokemon1.data['hp'] = pokemon2.get_pokemon_attack(t_c) * m2 - pokemon1.get_pokemon_defense(t_c)
        if pokemon1.data['hp'] < 0:
            winner = pokemon2
        else:
            winner = pokemon1
        winner.score += 1
        pokemon1.data['hp'] = starting_hp[0]
        pokemon2.data['hp'] = starting_hp[1]
        return winner

    @staticmethod
    def choose_which_pokemon_hits_first(pokemon1, pokemon2):
        """
        :param pokemon1:
        :param pokemon2:
        Pokemon who's speed is higher, goes first. if both pokemons have the same speed, then pokemon who's weight
        is lower goes first, if both pokemons have same weight, then pokemon who's height is lower goes first,
        if both pokemons have the same height, then the pokemon with more abilities goes first, if they have the same
        amount of abilities, then the pokemon with more moves goes first, if the pokemons have the same amount of
        moves, then the pokemon with higher base_experience goes first, if the pokemons have the same
        base_experience then SamePokemonFightException() is thrown
        :return pokemon1 who goes first and pokemon2 who goes second (return pokemon1, pokemon2)
        """
        attribute_list = ['speed', 'weight', 'height', 'abilities', 'moves', 'base_experience']
        for i in attribute_list:
            if i == 'speed':
                if pokemon1.data['speed'] > pokemon2.data['speed']:
                    return pokemon1, pokemon2
                elif pokemon2.data['speed'] > pokemon1.data['speed']:
                    return pokemon2, pokemon1
            elif i == 'weight':
                if pokemon1.data['weight'] < pokemon2.data['weight']:
                    return pokemon1, pokemon2
                elif pokemon2.data['weight'] < pokemon1.data['weight']:
                    return pokemon2, pokemon1
            elif i == 'height':
                if pokemon1.data['height'] < pokemon2.data['height']:
                    return pokemon1, pokemon2
                elif pokemon2.data['height'] < pokemon1.data['height']:
                    return pokemon2, pokemon1
            elif i == 'abilities':
                if len(pokemon1.data['abilties']) > len(pokemon2.data['abilties']):
                    return pokemon1, pokemon2
                elif len(pokemon2.data['abilties']) > len(pokemon1.data['abilties']):
                    return pokemon2, pokemon1
            elif i == 'moves':
                if len(pokemon1.data['moves']) > len(pokemon2.data['moves']):
                    return pokemon1, pokemon2
                elif len(pokemon2.data['moves']) > len(pokemon1.data['moves']):
                    return pokemon2, pokemon1
            elif i == 'base_experience':
                if pokemon1.data['base_experience'] > pokemon2.data['base_experience']:
                    return pokemon1, pokemon2
                elif pokemon2.data['base_experience'] > pokemon1.data['base_experience']:
                    return pokemon2, pokemon1
            else:
                raise SamePokemonFightException()

    def get_leader_board(self):
        """
        Get Pokemons by given format in a list sorted by the pokemon.score.

        In case of the same score, order pokemons by their name (ascending).

        :return: List of leader board. where winners are first
        """
        self.pokemons.sort(key=lambda x: x.data['name'])
        return self.pokemons.sort(key=lambda x: x.score)

    def get_pokemons_sorted_by_attribute(self, attribute: str):
        """
        Get Pokemons by given format in a list sorted by the pokemon.data[attribute]
        :param attribute:  pokemon data attribute to sort by
        :return: sorted List of pokemons
        """
        return self.pokemons.sort(key=lambda x: x.data[attribute])

if __name__ == '__main__':
    w = World(" ", " ", " ")
    w.dump_pokemons_to_file_as_json("pokefail.txt")
    p = Pokemon("https://pokeapi.co/api/v2/pokemon/1/")
    p.parse_json_to_pokemon_information("https://pokeapi.co/api/v2/pokemon/1/")
