package ee.taltech.iti0200.subclasses;

import java.util.Optional;

public abstract class Shape {

    protected Category category;
    protected double size;
    private Integer shapesNumber;

    public enum Category {
        SMALL("Small"), MEDIUM("Medium"), BIG("Big");

        private final String input;

        Category(String input) {
            this.input = input;
        }

        public String getInput() {
            return this.input;
        }
    }


    public Shape(Category category, double size) {
        this.category = category;
        this.size = size;
    }

    public Category getCategory() {
        return this.category;
    }

    public double getSize() {
        return size;
    }

    public void setCategory(Category newCategory) {
        this.category = newCategory;
    }

    public void setSize(double newSize) {
        this.size = newSize;
    }

    public abstract String draw();

    public abstract double calculateArea();

    public Optional<Integer> getNumber() {
        return Optional.ofNullable(shapesNumber);
    }

    public void putNumber(int number) {
        shapesNumber = number;
    }

    public void clearNumber() {
        shapesNumber = null;
    }
}
