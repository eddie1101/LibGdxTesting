package com.mygdx.game.api.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.api.shape.Circle;
import com.mygdx.game.api.shape.Line;
import com.mygdx.game.api.shape.Polygon;
import com.mygdx.game.api.shape.RenderableMovableShape;

import java.util.*;

public class ShapeCollisionDetector {

    List<Vector2> collisionPoints;
    List<RenderableMovableShape> collidingShapes;
    Map<RenderableMovableShape, Set<RenderableMovableShape>> collidingShapesMap;
    Map<RenderableMovableShape, Set<Vector2>> collisionPointsMap;
    boolean isColliding;

    public ShapeCollisionDetector(RenderableMovableShape... shapesIn) {
        collisionPoints = new ArrayList<>();
        collidingShapes = new ArrayList<>();
        collidingShapesMap = new HashMap<>();
        collisionPointsMap = new HashMap<>();
        isColliding = false;

        List<RenderableMovableShape> shapes = Arrays.stream(shapesIn).toList();

        for(int i = 0; i < shapes.size(); i++) {
            for(int n = i + 1; n < shapes.size(); n++) {
                RenderableMovableShape shape1 = shapes.get(i);
                RenderableMovableShape shape2 = shapes.get(n);
                List<Vector2> points = new ArrayList<>();
                if (shape1 instanceof Circle && shape2 instanceof Circle) {
                    points = checkCollision((Circle) shape1, (Circle) shape2);
                } else if (shape1 instanceof Polygon) {
                    points = checkCollision((Polygon) shape1, shape2);
                } else if (shape2 instanceof Polygon) {
                    points = checkCollision((Polygon) shape2, shape1);
                }
                if(!points.isEmpty()) {
                    setShapesCollisionData(shape1, shape2, points);
                    collisionPoints.addAll(points);
                }
            }
        }

        isColliding = !collisionPoints.isEmpty();
    }

    //https://gist.github.com/jupdike/bfe5eb23d1c395d8a0a1a4ddd94882ac
    private List<Vector2> checkCollision(Circle c1, Circle c2) {
        float centerdx = c1.x() - c2.x();
        float centerdy = c1.y() - c2.y();
        float R = (float) Math.sqrt(centerdx * centerdx + centerdy * centerdy);
        if (!(Math.abs(c1.getRadius() - c2.getRadius()) <= R && R <= c1.getRadius() + c2.getRadius())) { // no intersection
            return new ArrayList<>();
        }
        // intersection(s) should exist
        float R2 = R*R;
        float R4 = R2*R2;
        float a = (c1.getRadius()*c1.getRadius() - c2.getRadius()*c2.getRadius()) / (2 * R2);
        float r2r2 = (c1.getRadius()*c1.getRadius() - c2.getRadius()*c2.getRadius());
        float c = (float) Math.sqrt(2 * (c1.getRadius()*c1.getRadius() + c2.getRadius()*c2.getRadius()) / R2 - (r2r2 * r2r2) / R4 - 1);
        float fx = (c1.x()+c2.x()) / 2 + a * (c2.x() - c1.x());
        float gx = c * (c2.y() - c1.y()) / 2;
        float ix1 = fx + gx;
        float ix2 = fx - gx;
        float fy = (c1.y()+c2.y()) / 2 + a * (c2.y() - c1.y());
        float gy = c * (c1.x() - c2.x()) / 2;
        float iy1 = fy + gy;
        float iy2 = fy - gy;

        if(gy == 0 && gx == 0) {
            return new ArrayList<>(Collections.singletonList(new Vector2(ix1, iy1)));
        } else {
            return new ArrayList<>(Arrays.asList(new Vector2(ix1, iy1), new Vector2(ix2, iy2)));
        }
    }

    private List<Vector2> checkCollision(Polygon p, RenderableMovableShape shape) {
        ArrayList<Vector2> points = new ArrayList<>();
        for(Line line : p.getSides()) {
            LineIntersection li = new LineIntersection(line, shape);
            if(li.isIntersecting()) {
                points.addAll(li.getIntersectionPoints());
            }
        }
        return points;
    }

    private void setShapesCollisionData(RenderableMovableShape s1, RenderableMovableShape s2, List<Vector2> points) {
        if(collidingShapesMap.containsKey(s1)) {
            collidingShapesMap.get(s1).add(s2);
        } else {
            collidingShapesMap.put(s1, new HashSet<>(Collections.singletonList(s2)));
        }

        if(collisionPointsMap.containsKey(s1)) {
            collisionPointsMap.get(s1).addAll(points);
        } else {
            collisionPointsMap.put(s1, new HashSet<>(points));
        }

        if(collidingShapesMap.containsKey(s2)) {
            collidingShapesMap.get(s2).add(s1);
        } else {
            collidingShapesMap.put(s2, new HashSet<>(Collections.singletonList(s1)));
        }

        if(collisionPointsMap.containsKey(s2)) {
            collisionPointsMap.get(s2).addAll(points);
        } else {
            collisionPointsMap.put(s2, new HashSet<>(points));
        }

        if(!collidingShapes.contains(s1)) {
            collidingShapes.add(s1);
        }

        if(!collidingShapes.contains(s2)) {
            collidingShapes.add(s2);
        }
    }

    public boolean isColliding() {
        return isColliding;
    }

    public List<Vector2> getCollisionPoints() {
        if(isColliding) {
            return collisionPoints;
        } else {
            return null;
        }
    }

    public boolean isShapeColliding(RenderableMovableShape shape) {
        return collidingShapes.contains(shape);
    }

    public List<RenderableMovableShape> getCollidingShapes() {
        return collidingShapes;
    }

    public List<RenderableMovableShape> getShapesCollidingWith(RenderableMovableShape shape) {
        if(collidingShapesMap.containsKey(shape)) {
            return collidingShapesMap.get(shape).stream().toList();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Vector2> getCollisionPointsOf(RenderableMovableShape shape) {
        if(isShapeColliding(shape) && collisionPointsMap.containsKey(shape)) {
            return collisionPointsMap.get(shape).stream().toList();
        } else {
            return new ArrayList<>();
        }
    }

}
