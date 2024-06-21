package com.mygdx.game.api.shape;

public class Circle extends RenderableMovableShape {

    private int radius;

    public Circle(int radius) {
        super();
        renderer.setAutoShapeType(true);
        setRadius(radius);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void render() {
        renderer.begin(shapeType);
        renderer.circle(x(), y(), radius);
        renderer.end();
    }

}
