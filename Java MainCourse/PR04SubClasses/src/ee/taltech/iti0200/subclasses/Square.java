package ee.taltech.iti0200.subclasses;

public class Square extends Shape {

    public Square(Category category, double size) {
        super(category, size);
    }

    @Override
    public String draw() {
        return "Drawing square! Category: " + super.getCategory().getInput();
    }

    @Override
    public double calculateArea() {
        return Math.pow(super.getSize(), 2);
    }
}
