package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

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
        renderer.begin();
        renderer.circle(x(), y(), radius);
        renderer.end();
    }

}
