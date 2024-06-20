package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IRenderableShape {

    void render();
    ShapeRenderer getRenderer();
    void dispose();

}
