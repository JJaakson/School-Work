package ee.taltech.iti0200.subclasses;

public class Circle extends Shape {

    public Circle(Category category, double size) {
        super(category, size);
    }

    @Override
    public String draw() {
        return "Drawing circle! Category: " + super.getCategory().getInput();
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(super.getSize(), 2);
    }
}
