package ee.taltech.iti0200.datatypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class DataTypes {

    /**
     * Find all elements that appear more than once over all lists.
     * ["this", "is", "fun", "this", "is", "rad"], ["actually", "it", "is", "not", "fun"] ===>
     * ====> ["this", "is", "fun"]
     *
     * @param data List of lists containing strings
     * @return Set of unique data
     */
    public static Set<String> getUniqueDuplicates(List<List<String>> data) {
        List<String> duplicateFinderHelper = new LinkedList<>();
        Set<String> duplicates = new HashSet<>();
        for (List<String> sublist : data) {
            for (String element : sublist) {
                if (duplicateFinderHelper.contains(element) && !duplicates.contains(element)) {
                    duplicates.add(element);
                } else {
                    duplicateFinderHelper.add(element);
                }
            }
        }
        return duplicates;
    }

    /**
     * Calculate B^P mod M using BigInteger data type.
     * <p>
     * B = b!, P = p!, M = m!
     * n! = 1 * 2 * ... * (n-2) * (n-1) * n
     *
     * @return Algorithm value using BigInteger
     */
    public static BigInteger bigMod(int b, int p, int m) {
        BigInteger bFactorial = BigInteger.ONE;
        BigInteger pFactorial = BigInteger.ONE;
        BigInteger mFactorial = BigInteger.ONE;
        for (int i = 1; i <= b; i++) bFactorial = bFactorial.multiply(BigInteger.valueOf(i));
        for (int i = 1; i <= p; i++) pFactorial = pFactorial.multiply(BigInteger.valueOf(i));
        for (int i = 1; i <= m; i++) mFactorial = mFactorial.multiply(BigInteger.valueOf(i));
        return bFactorial.modPow(pFactorial, mFactorial);
    }

    /**
     * Convert different currencies to euros and return the sum it.
     * <p>
     * Map of CurrencyToEurRate always contains necessary exchange rates.
     *
     * @param data              Map containing the name of currency and a list of different values
     * @param currencyToEurRate Map containing the name of currency and its exchange rate to euro
     * @return Sum of money in euros
     */
    public static BigDecimal currencyConverter(Map<String, List<BigDecimal>> data, Map<String,
            BigDecimal> currencyToEurRate) {
        BigDecimal money = BigDecimal.ZERO;
        for (Map.Entry<String, List<BigDecimal>> entry : data.entrySet()) {
            for (BigDecimal amount : entry.getValue()) {
                money = money.add(amount.multiply(currencyToEurRate.get(entry.getKey())));
            }
        }
        return money;
    }

    public static void main(String[] args) {
        List<String> data1 = new ArrayList<>(List.of("this", "is", "fun", "this", "is", "rad"));
        List<String> data2 = new ArrayList<>(List.of("actually", "it", "is", "not", "fun"));
        List<List<String>> data = new ArrayList<>(List.of(data1, data2));
        System.out.println(getUniqueDuplicates(data)); // ["this", "is", "fun"]

        System.out.println(bigMod(3, 5, 8)); // 3!^5! mod 8! = 26496

        Map<String, BigDecimal> currencyToEurRate = new HashMap<>();
        currencyToEurRate.put("Yen", BigDecimal.valueOf(0.00828172423484));
        currencyToEurRate.put("Pounds", BigDecimal.valueOf(1.17812423423082));

        Map<String, List<BigDecimal>> exchangeData = new HashMap<>();
        exchangeData.put("Yen", List.of(BigDecimal.valueOf(241645), BigDecimal.valueOf(321)));
        exchangeData.put("Pounds", List.of(BigDecimal.valueOf(256)));

        System.out.println(currencyConverter(exchangeData, currencyToEurRate)); //2305.49549017038536
    }
}
