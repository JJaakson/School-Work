package ee.taltech.iti0200.recursion;

import ee.taltech.iti0200.recursion.mlp.MyLittlePony;

import java.util.List;

public class Recursion {

    public static List<String> eliminatePonies(List<MyLittlePony> ponies, List<String> ponyNames, Integer listIndex) {
        if (ponies.size() < 1) {
            return ponyNames;
        } else if (ponies.size() > 1 && ponies.get(0).getPonyType() == ponies.get(1).getPonyType()) {
            return eliminatePonies(ponies.subList(1, ponies.size()), ponyNames, listIndex);
        } else {
            ponyNames.add(ponies.get(0).getName());
            return eliminatePonies(ponies.subList(1, ponies.size()), ponyNames, listIndex);
        }
    }

    public static int getPonyNamesLengthProduct(List<MyLittlePony> ponies, int product) {
        if (ponies.size() < 1) {
            return product;
        } else {
            return getPonyNamesLengthProduct(ponies.subList(1, ponies.size()),
                    product + ponies.get(0).getName().length());
        }
    }

    public static int getPonyNamesLengthProductExceptType(List<MyLittlePony> ponies, int product,
                                                          MyLittlePony.PonyType type) {
        if (ponies.size() < 1) {
            return product;
        } else if (ponies.get(0).getPonyType() == type) {
            return getPonyNamesLengthProductExceptType(ponies.subList(1, ponies.size()), product, type);
        } else {
            return getPonyNamesLengthProductExceptType(ponies.subList(1, ponies.size()),
                    product + ponies.get(0).getName().length(), type);
        }
    }
}
