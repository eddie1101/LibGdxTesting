package com.mygdx.game.api.shape;

import com.badlogic.gdx.math.Vector2;

public class Triangle extends Polygon {

    public Triangle() {
        super();
    }

    @Override
    public void addVertices() {
        addVertex(new Vector2(0, 0));
        addVertex(new Vector2(100, 0));
        addVertex(new Vector2(50, 90));
    }

}
