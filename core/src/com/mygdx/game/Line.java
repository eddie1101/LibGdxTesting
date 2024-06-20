package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Line implements IRenderableShape {

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

    public Vector2 getP1() {
        return p1;
    }

    public Vector2 getP2() {
        return p2;
    }

    public void setP1(Vector2 p1) {
        this.p1 = p1;
    }

    public void setP2(Vector2 p2) {
        this.p2 = p2;
    }

    @Override
    public void render() {
        renderer.begin();
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
