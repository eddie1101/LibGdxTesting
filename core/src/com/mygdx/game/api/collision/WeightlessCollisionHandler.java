package com.mygdx.game.api.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.api.shape.RenderableMovableShape;

import java.util.List;

public class WeightlessCollisionHandler extends ShapeCollisionHandler {

    public WeightlessCollisionHandler(RenderableMovableShape... shapesIn) {
        super(shapesIn);
    }

    @Override
    public void handle() {
        ShapeCollisionDetector detector = new ShapeCollisionDetector(shapes);
        for(RenderableMovableShape shape : detector.getCollidingShapes()) {
            List<Vector2> collisionPoints = detector.getCollisionPointsOf(shape);
            Vector2 average = new Vector2(0, 0);
            collisionPoints.forEach(e -> e.sub(shape.getPos()));
            collisionPoints.forEach(average::add);
            float x = average.x, y = average.y;
            x /= collisionPoints.size();
            y /= collisionPoints.size();
            average.set(-x, -y).nor();
            shape.setVel(shape.getVel().cpy().nor().add(average).scl(shape.getVel().len()));
        }
    }

}
