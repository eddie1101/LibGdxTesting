package com.mygdx.game.api.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IRenderableShape {

    Color getColor();
    void setColor(Color color);

    void setShapeType(ShapeRenderer.ShapeType type);
    void render();
    ShapeRenderer getRenderer();
    void dispose();

}
