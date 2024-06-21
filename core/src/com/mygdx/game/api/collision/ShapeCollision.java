package com.mygdx.game.api.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.api.shape.Circle;
import com.mygdx.game.api.shape.Line;
import com.mygdx.game.api.shape.Polygon;
import com.mygdx.game.api.shape.RenderableMovableShape;

public class ShapeCollision {

    Vector2 collisionPoint;
    boolean isColliding;

    public ShapeCollision(RenderableMovableShape shape1,RenderableMovableShape shape2) {
        collisionPoint = null;
        isColliding = false;

        if(shape1 instanceof Circle && shape2 instanceof Circle) {
            collide((Circle) shape1, (Circle) shape2);
        } else if(shape1 instanceof Polygon) {
            collide((Polygon) shape1, shape2);
        } else if(shape2 instanceof Polygon) {
            collide((Polygon) shape2, shape1);
        }
    }

    private void collide(Circle c1, Circle c2) {
        Vector2 c1c2 = c2.getPos().cpy().sub(c1.getPos());
        if(Math.abs(c1c2.len()) < c1.getRadius() + c2.getRadius()) {
            isColliding = true;
            collisionPoint = c2.getPos().cpy().sub(c1c2.nor().scl(c2.getRadius()));
        }
    }

    private void collide(Polygon p, RenderableMovableShape shape) {
        for(Line line : p.getSides()) {
            LineIntersection li = new LineIntersection(line, shape);
            if(li.isIntersecting()) {
                isColliding = li.isIntersecting();
                collisionPoint = li.getIntersectionPoint();
                break;
            }
        }
    }

    public boolean isColliding() {
        return isColliding;
    }

    public Vector2 getCollisionPoint() {
        if(isColliding) {
            return collisionPoint;
        } else {
            return null;
        }
    }

}
