package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class RenderableMovableShape extends Movable implements IRenderableShape {

    protected final ShapeRenderer renderer = new ShapeRenderer();

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
