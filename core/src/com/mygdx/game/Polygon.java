package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public abstract class Polygon extends RenderableMovableShape {

    private List<Vector2> vertices = new ArrayList<>();
    private boolean constructed = false;

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
        this.vertices.forEach(e -> vertices.add(new Vector2(e).add(getPos())));
        return vertices;
    }

    public Vector2 getCenter() {
        List<Vector2> vertices = getVertexPositions();
        float x = 0, y = 0;
        for(Vector2 vertex : vertices) {
            x += vertex.x;
            y += vertex.y;
        }
        return new Vector2(x / vertices.size(), y / vertices.size());
    }

    public void render() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.polygon(getVerticesAsArray());
        renderer.end();
    }
}
