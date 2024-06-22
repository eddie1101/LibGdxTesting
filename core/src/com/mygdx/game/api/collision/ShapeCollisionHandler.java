package com.mygdx.game.api.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.api.shape.RenderableMovableShape;

import java.util.List;

public abstract class ShapeCollisionHandler {

    RenderableMovableShape[] shapes;

    public ShapeCollisionHandler(RenderableMovableShape... shapesIn) {
        shapes = shapesIn;
    }

    public abstract void handle();

}
