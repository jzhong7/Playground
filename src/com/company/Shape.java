package com.company;

import java.util.Objects;

public class Shape {
    double area;
    int id;

    public Shape(int id) {
        this.id = id;
    }

    public double calculateArea(){
        return area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || hashCode() != o.hashCode()) return false;
        Shape shape = (Shape) o;
        return Double.compare(shape.area, area) == 0 &&
                id == shape.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, id);
    }
}

class Circle extends Shape {
    double radius;

    public Circle(int id, double radius) {
        super(id);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.pow(radius, 2) * Math.PI;
    }
}

class Rectangle extends Shape {
    double length;
    double width;

    public Rectangle(int id, double length, double width) {
        super(id);
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }
}

class Square extends Shape {
    double side;

    public Square(int id, double side) {
        super(id);
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }
}