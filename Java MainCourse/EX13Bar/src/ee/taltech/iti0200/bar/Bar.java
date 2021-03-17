package ee.taltech.iti0200.bar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import ee.taltech.iti0200.bar.persons.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bar {

    private static final int NIGHTTIMESTART = 21;
    private static final int DAYTIMESTART = 8;

    private List<Drink> drinksList = new ArrayList<>();
    private LocalTime nightTime = LocalTime.of(NIGHTTIMESTART, 0);
    private LocalTime dayTime = LocalTime.of(DAYTIMESTART, 0);
    private LocalTime currentTime;
    private int drinkCounter = 0;

    public enum GetDrinkBy {
        NAME,
        INGREDIENT,
        CATEGORY,
        NON_ALCOHOLIC
    }

    static class Drinks {
        private List<Map<String, String>> drinks = new ArrayList<>();
    }

    public Bar(LocalTime time) {
        this.currentTime = time;
    }

    private Drinks getInfoFromApi(URL url, Gson gson) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        if (content.length() == 0) {
            return null;
        }
        return gson.fromJson(String.valueOf(content), Drinks.class);
    }

    private Drink completeDrink(Gson gson, Map<String, String> drink) {
        String jsonDrink = gson.toJson(drink);
        Drink newDrink = gson.fromJson(jsonDrink, Drink.class);
        JsonReader jsonReader = new JsonReader(new StringReader(jsonDrink));
        try {
            while (jsonReader.hasNext()) {
                JsonToken nextToken = jsonReader.peek();
                if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
                    jsonReader.beginObject();
                } else if (JsonToken.NAME.equals(nextToken)) {
                    String name  =  jsonReader.nextName();
                    if (name.contains("strIngredient")) {
                        JsonToken nextToken1 = jsonReader.peek();
                        if (JsonToken.STRING.equals(nextToken1)) {
                            String value = jsonReader.nextString();
                            if (value == null) {
                                break;
                            }
                            newDrink.addIngredient(value);
                        }
                    }
                } else if (JsonToken.STRING.equals(nextToken)) {
                    jsonReader.nextString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newDrink;
    }

    private String getCorrectDrinkIdFromDrinksList(Drinks drinks, Gson gson) {
        String drinkId = "";
        int counter = 0;
        if (drinkCounter >= drinks.drinks.size()) {
            drinkCounter = 0;
        }
        for (Object drink : drinks.drinks) {
            if (counter == drinkCounter) {
                String jsonDrink = gson.toJson(drink);
                JsonReader jsonReader = new JsonReader(new StringReader(jsonDrink));
                try {
                    while (jsonReader.hasNext()) {
                        JsonToken nextToken = jsonReader.peek();
                        if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
                            jsonReader.beginObject();
                        } else if (JsonToken.NAME.equals(nextToken)) {
                            String name  =  jsonReader.nextName();
                            if (name.contains("idDrink")) {
                                JsonToken nextToken1 = jsonReader.peek();
                                if (JsonToken.STRING.equals(nextToken1)) {
                                    String value = jsonReader.nextString();
                                    if (value != null) {
                                        drinkId = value;
                                        break;
                                    }
                                }
                            }
                        } else if (JsonToken.STRING.equals(nextToken)) {
                            jsonReader.nextString();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            counter++;
            if (drinkId.length() > 0) {
                drinkCounter++;
                break;
            }
        }
        return drinkId;
    }

    private Drink findDrink(Drinks drinks, Gson gson, GetDrinkBy method, String info) throws IOException {
        if (!method.equals(GetDrinkBy.NAME)) {
            String drinkId = getCorrectDrinkIdFromDrinksList(drinks, gson);
            URL url = new URL("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + drinkId);
            Drinks correctDrink = getInfoFromApi(url, gson);
            for (Map<String, String> drink : correctDrink.drinks) {
                return completeDrink(gson, drink);
            }
        } else {
            for (Map<String, String> drink : drinks.drinks) {
                if (drink.containsKey("strDrink")
                        && drink.get("strDrink").toLowerCase().equals(info.toLowerCase())) {
                    return completeDrink(gson, drink);
                }
            }
        }
        return null;
    }

    private String getCorrectUrl(GetDrinkBy method, String s) {
        String url = "";
        s = s.trim().replace(" ", "_");
        if (!checkNightTime() || method.equals(GetDrinkBy.NON_ALCOHOLIC)) {
            url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic";
        } else if (method.equals(GetDrinkBy.NAME)) {
            url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + s;
        } else if (method.equals(GetDrinkBy.INGREDIENT)) {
            url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + s;
        } else if (method.equals(GetDrinkBy.CATEGORY)) {
            url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=" + s;
        }
        return url;
    }

    public String getDrink(Person person) throws IOException {
        if (!checkNightTime() && !person.getOnlyDrinks().equals(GetDrinkBy.NON_ALCOHOLIC)) {
            return "It is daytime we dont sell alcoholic drinks right now!";
        }
        URL url = new URL(getCorrectUrl(person.getOnlyDrinks(), person.getAttribute()));
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Drinks drinks = getInfoFromApi(url, gson);
        Drink orderedDrink = findDrink(drinks, gson, person.getOnlyDrinks(), person.getAttribute());
        if (!checkNightTime() && orderedDrink == null) {
            return "It is daytime we dont sell alcoholic drinks right now!";
        } else if (orderedDrink == null) {
            return "Could not find such drink";
        }
        person.addDrink(orderedDrink);
        drinksList.add(orderedDrink);
        return "Here is your drink : " + orderedDrink.toString();
    }

    private boolean checkNightTime() {
        LocalTime now;
        if (currentTime == null) {
            now = LocalTime.now();
        } else {
            now = currentTime;
        }
        return now.isAfter(nightTime) || now.isBefore(dayTime);
    }

    public List<Drink> getDrinksList() {
        return drinksList;
    }
}
