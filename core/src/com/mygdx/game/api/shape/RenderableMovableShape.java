package com.mygdx.game.api.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class RenderableMovableShape extends Movable implements IRenderableShape {

    //Shape ID
    private static int SID_SERIAL = 0;

    private final int SID;
    protected final ShapeRenderer renderer = new ShapeRenderer();
    protected ShapeRenderer.ShapeType shapeType = ShapeRenderer.ShapeType.Line;

    protected RenderableMovableShape() {
        SID = SID_SERIAL++;
    }

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

    public final int SID() {
        return this.SID;
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o instanceof RenderableMovableShape) return this.SID() == ((RenderableMovableShape) o).SID();
        return false;
    }

}
