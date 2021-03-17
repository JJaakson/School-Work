package ee.taltech.iti0200.kt1.chocolatefactory;

public class ChocolateFactory {
    private int chocolateBoxesMade;
    private int costSoFar;

    public enum BoxType {

        SQUARE1(4, 4),
        SQUARE2(5, 5),
        RECTANGLE1(3, 6),
        RECTANGLE2(4, 8);

        private int width;
        private int length;

        BoxType(int width, int length) {
            this.width = width;
            this.length = length;
        }
    }

    public ChocolateType[][] makeChocolateBox(ChocolateType chocolate1, ChocolateType chocolate2,
                                              Integer preferedChocolate1Count, BoxType boxType) {
        ChocolateType[][] box = new ChocolateType[boxType.length][boxType.width];
        int boxSize = 2 * (boxType.length - 2 + boxType.width);
        int choco1Count = 0;
        int choco2Count = 0;
        if (preferedChocolate1Count == 0) {
            for (int x = 0; x < boxType.length; x++) {
                for (int y = 0; y < boxType.width; y++) {
                    box[x][y] = chocolate2;
                    choco2Count++;
                }
            }
        } else if (preferedChocolate1Count == 4) {
            for (int x = 0; x < boxType.length; x++) {
                for (int y = 0; y < boxType.width; y++) {
                    if (x == 0 && y == 0 || x == 0 && y == boxType.width - 1
                            || x == boxType.length - 1 && y == 0 || x == boxType.length - 1 && y == boxType.width - 1) {
                        box[x][y] = chocolate1;
                        choco1Count++;
                    } else {
                        box[x][y] = chocolate2;
                        choco2Count++;
                    }
                }
            }
        } else if (preferedChocolate1Count == boxSize) {
            for (int x = 0; x < boxType.length; x++) {
                for (int y = 0; y < boxType.width; y++) {
                    if (x == 0 || y == 0 || y == boxType.width - 1 || x == boxType.length - 1) {
                        box[x][y] = chocolate1;
                        choco1Count++;
                    } else {
                        box[x][y] = chocolate2;
                        choco2Count++;
                    }
                }
            }
        } else {
            for (int x = 0; x < boxType.length; x++) {
                for (int y = 0; y < boxType.width; y++) {
                if (x % 2 == 0 && y % 2 == 0 || x % 2 == 1 && y % 2 == 1) {
                    box[x][y] = chocolate1;
                    choco1Count++;
                } else {
                    box[x][y] = chocolate2;
                    choco2Count++;
                }
                }
            }
        }
        costSoFar += choco1Count * chocolate1.getPricePerPiece() + choco2Count * chocolate2.getPricePerPiece();
        chocolateBoxesMade++;
        return box;
    }

    public int getChocolateBoxesMade() {
        return chocolateBoxesMade;
    }

    public int getCostSoFar() {
        return costSoFar;
    }

    public static void main(String[] args) {
        ChocolateFactory factory = new ChocolateFactory();
        ChocolateType[][] a = factory.makeChocolateBox(new ChocolateType(2, "1"),
                new ChocolateType(3, "2"), 1, BoxType.SQUARE1);
        System.out.println(factory.getCostSoFar());
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j].getStringForm());
            }
            System.out.println();
        }
    }
}
