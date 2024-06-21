package com.mygdx.game.api.shape;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

//Not technically a polygon but idc
public class Line extends Polygon {

    ShapeRenderer renderer = new ShapeRenderer();
    Vector2 p1, p2;

    public Line(float x1, float y1, float x2, float y2) {
        this(new Vector2(x1, y1), new Vector2(x2, y2));
    }

    public Line(Vector2 p1, Vector2 p2) {
        this.p1 = p1;
        this.p2 = p2;
        renderer.setAutoShapeType(true);
    }

    @Override
    protected void addVertices() {
        addVertex(p1);
        addVertex(p2);
    }

    public void setP1(Vector2 p) {
        this.p1.set(p);
        constructed = false;
    }

    public void setP2(Vector2 p) {
        this.p2.set(p);
        constructed = false;
    }

    public Vector2 getP1() {
        return p1;
    }

    public Vector2 getP2() {
        return p2;
    }

    @Override
    public void render() {
        renderer.begin(shapeType);
        renderer.line(p1, p2);
        renderer.end();
    }

    @Override
    public ShapeRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

}
