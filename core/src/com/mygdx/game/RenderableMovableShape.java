package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class RenderableMovableShape extends Movable implements IRenderableShape {

    protected final ShapeRenderer renderer = new ShapeRenderer();
    protected ShapeRenderer.ShapeType shapeType = ShapeRenderer.ShapeType.Line;

    public void setShapeType(ShapeRenderer.ShapeType type) {
        this.shapeType = type;
    }

    public void setColor(Color color) {
        renderer.setColor(color);
    }

    public Color getColor() {
        return renderer.getColor();
    }

    @Override
    public abstract void render();

        @Override
    public ShapeRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

}
