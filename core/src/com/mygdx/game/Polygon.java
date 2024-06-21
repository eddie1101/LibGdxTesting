package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public abstract class Polygon extends RenderableMovableShape {

    private List<Vector2> vertices = new ArrayList<>();
    private boolean constructed = false;
    private float radians = 0;

    public Polygon() {
        super();
        renderer.setAutoShapeType(true);
    }

    private float[] getVerticesAsArray() {
        List<Vector2> vertices = getVertexPositions();
        float[] vertexArray = new float[this.vertices.size() * 2];
        for(int i = 0; i < vertices.size(); i++) {
            vertexArray[i*2] = vertices.get(i).x;
            vertexArray[i*2+1] = vertices.get(i).y;
        }
        return vertexArray;
    }

    protected abstract void addVertices();

    protected final void addVertex(Vector2 vertex) {
        this.vertices.add(vertex);
    }

    public List<Vector2> getVertexPositions() {
        if(!constructed) {
            addVertices();
            constructed = true;
        }
        ArrayList<Vector2> vertices = new ArrayList<>();
        Vector2 center = getCenter();
        this.vertices.forEach(e -> {
            float x = e.x, y = e.y;
            x -= center.x;
            y -= center.y;
            float r = (float) Math.sqrt(x * x + y * y);
            float th = (float) Math.atan2(y, x);
            th += radians;
            Vector2 vertex = new Vector2((r * (float) Math.cos(th)), (r * (float) Math.sin(th)));
            vertex.add(center);
            vertex.add(getPos());
            vertices.add(vertex);
        });
        return vertices;
    }

    public Vector2 getCenter() {
        return getCenter(false);
    }

//  if absolute is false, then the center point returned is relative to the shape local origin, or the position of it's first vertex
//  otherwise, it is relative to the screen origin.
    public Vector2 getCenter(boolean absolute) {
        if(!constructed) {
            addVertices();
            constructed = true;
        }
        float x = 0, y = 0;
        for(Vector2 vertex : vertices) {
            x += vertex.x;
            y += vertex.y;
            if(absolute) {
                x += getPos().x;
                y += getPos().y;
            }
        }
        return new Vector2(x / vertices.size(), y / vertices.size());
    }

    public float getRotation() {
        return radians;
    }

    public void setRotation(float radians) {
        this.radians = radians;
    }

    public void render() {
        renderer.begin(shapeType);
        renderer.polygon(getVerticesAsArray());
        renderer.end();
    }
}
