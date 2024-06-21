package com.mygdx.game.api.shape;

import com.badlogic.gdx.math.Vector2;

public class Rectangle extends Polygon {

    private int width, height;

    public Rectangle(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    @Override
    protected void addVertices() {
        addVertex(new Vector2(0, 0));
        addVertex(new Vector2(width, 0));
        addVertex(new Vector2(width, height));
        addVertex(new Vector2(0, height));
    }

}
