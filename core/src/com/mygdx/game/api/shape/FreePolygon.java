package com.mygdx.game.api.shape;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FreePolygon extends Polygon {

    List<Vector2> points;

    public FreePolygon() {
        points = new ArrayList<>();
    }

    @Override
    protected void addVertices() {
        points.forEach(this::addVertex);
    }

    public void setPoints(Vector2... points) {
        if(!constructed) {
            this.points.addAll(Arrays.asList(points));
        } else {
            throw new IllegalStateException("Cannot add points to a free polygon after it has been rendered.");
        }
    }

}
