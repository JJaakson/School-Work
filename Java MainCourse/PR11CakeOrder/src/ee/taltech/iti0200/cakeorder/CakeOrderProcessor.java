package ee.taltech.iti0200.cakeorder;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CakeOrderProcessor {

    class Order {
        @SerializedName("order_id")
        private int orderId = 0;
        private List<Cake> cakes = new ArrayList<>();
    }

    class Cake {
        @SerializedName("cake_id")
        private String cakeID = "";
        private String name;
        private String BBD;
        private double price;
        private double kg;
        private List<String> ingredients;

        public void setCakeID(String cakeID) {
            this.cakeID = cakeID;
        }
    }

    public enum CakeOrderProcessorType {
        MAKE_DAIRY_FREE,
        COUNT_TOTAL_SUM,
        REMOVE_BEST_BEFORE_DAY_OVER
    }

    private static final double TEN_PERCENT = 0.1;
    private CakeOrderProcessorType type;
    private int orderCount;

    public CakeOrderProcessor(CakeOrderProcessorType type) {
        this.orderCount = 0;
        this.type = type;
    }

    public String process(String jsonInput) {
        orderCount += 1;
        Gson gson = new Gson();
        Order order = gson.fromJson(jsonInput, Order.class);
        order.orderId = orderCount;
        if (this.type == CakeOrderProcessorType.MAKE_DAIRY_FREE) {
            makeDairyFree(order);
        } else if (this.type == CakeOrderProcessorType.COUNT_TOTAL_SUM) {
            return countTotalSum(order, gson);
        } else if (this.type == CakeOrderProcessorType.REMOVE_BEST_BEFORE_DAY_OVER) {
            removeOldCakes(order);
        }
        return gson.toJson(order);
    }

    private void removeOldCakes(Order order) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        List<Cake> checkedCakeList = new ArrayList<>();
        for (Cake cake : order.cakes) {
            setCakeId(cake);
            try {
                Date cakeDate = sdf.parse(cake.BBD);
                if (currentDate.compareTo(cakeDate) < 0) {
                    checkedCakeList.add(cake);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        order.cakes = checkedCakeList;
    }

    private String countTotalSum(Order order, Gson gson) {
        double totalSum = 0;
        if (!order.cakes.isEmpty()) {
            for (Cake cake : order.cakes) {
                setCakeId(cake);
                totalSum += cake.price * cake.kg;
                System.out.println(cake.toString());
            }
        }
        JsonElement jsonElement = gson.toJsonTree(order);
        jsonElement.getAsJsonObject().addProperty("total", totalSum);
        return gson.toJson(jsonElement);
    }

    public void makeDairyFree(Order order) {
            List<String> newIngredients = new ArrayList<>();
            for (Cake cake : order.cakes) {
                setCakeId(cake);
                double percentage = 0;
                for (String ingredient : cake.ingredients) {
                    if (ingredient.equals("milk")) {
                        ingredient = "plant-milk";
                        percentage += TEN_PERCENT;
                    } else if (ingredient.equals("cream-cheese")) {
                        ingredient = "plant-cream-cheese";
                        percentage += TEN_PERCENT;
                    } else if (ingredient.equals("yoghurt")) {
                        ingredient = "plant-yoghurt";
                        percentage += TEN_PERCENT;
                    }
                    newIngredients.add(ingredient);
                }
                cake.price += cake.price * percentage;
                cake.price = new BigDecimal(cake.price).setScale(2, RoundingMode.HALF_UP).doubleValue();
                cake.ingredients = newIngredients;
            }
    }

    public void setCakeId(Cake cake) {
        String[] nameWords = cake.name.split(" ");
        StringBuilder newCakeId = new StringBuilder();
        int cakeWordCount = 0;
        for (String word : nameWords) {
            newCakeId.append(word.charAt(0));
            cakeWordCount++;
        }
        cake.setCakeID(newCakeId.toString().toUpperCase() + cakeWordCount);
    }
}
