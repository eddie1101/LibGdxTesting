package com.mygdx.game.api.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.api.shape.Circle;
import com.mygdx.game.api.shape.Line;
import com.mygdx.game.api.shape.Polygon;
import com.mygdx.game.api.shape.RenderableMovableShape;

import java.util.ArrayList;
import java.util.List;

public class LineIntersection {

    Line line;
    RenderableMovableShape[] shapes;

    private boolean isIntersecting = false;
    private final List<Vector2> intersectionPoints;
    private final List<RenderableMovableShape> intersectingShapes;

    public LineIntersection(Line line, RenderableMovableShape... shapes) {
        intersectionPoints = new ArrayList<>();
        intersectingShapes = new ArrayList<>();
        this.line = line;
        this.shapes = shapes;

        for(RenderableMovableShape shape : shapes) {
            if (shape instanceof Polygon) {
                if(findIntersection(line, (Polygon) shape)) {
                    intersectingShapes.add(shape);
                }
            } else if (shape instanceof Circle) {
                if(findIntersection(line, (Circle) shape)) {
                    intersectingShapes.add(shape);
                }
            }
        }

        isIntersecting = !intersectionPoints.isEmpty();
    }

    private boolean findIntersection(Line line, Polygon poly) {
        List<Vector2> vertexPositions = poly.getVertexPositions();
        vertexPositions.add(new Vector2(vertexPositions.get(0)));

        ArrayList<Vector2> intersections = new ArrayList<>();

        for(int i = 0; i < vertexPositions.size() - 1; i++) {

            Vector2 intersection = new Vector2();

            Vector2 A = line.getP1().cpy();
            Vector2 B = line.getP2().cpy();
            Vector2 C = vertexPositions.get(i).cpy();
            Vector2 D = vertexPositions.get(i + 1).cpy();

            float lineMinX = Math.min(A.x, B.x);
            float lineMaxX = Math.max(A.x, B.x);
            float lineMinY = Math.min(A.y, B.y);
            float lineMaxY = Math.max(A.y, B.y);

            float sideMinX = Math.min(C.x, D.x);
            float sideMaxX = Math.max(C.x, D.x);
            float sideMinY = Math.min(C.y, D.y);
            float sideMaxY = Math.max(C.y, D.y);

            float slope =  (B.y - A.y) / (B.x - A.x);
            float b = A.y - (slope * A.x);

            //If the side of the polygon is vertical
            if(D.x - C.x == 0) {
                float x = C.x;
                float y = slope * x + b;
                intersection = new Vector2(x, y);

            //If the side of the polygon is horizontal
            } else if(D.y - C.y == 0) {
                float y = C.y;
                float x = (-b + y) / slope;
                intersection = new Vector2(x, y);

            //If the side of the polygon is NOT axis-aligned
            } else {
                //https://www.youtube.com/watch?v=5FkOO1Wwb8w
                Vector2 AB = B.cpy().sub(A);
                Vector2 CD = D.cpy().sub(C);

                float determinant = AB.crs(CD);
                //Lines are not parallel
                if (determinant != 0) {
                    Vector2 AC = C.cpy().sub(A);
                    float t1 = AC.crs(CD) / determinant;
//                    float t2 = -AB.crs(AC) / determinant;
                    intersection = A.cpy().add(AB.cpy().scl(t1));
                }
            }

            boolean onSide = intersection.x >= sideMinX && intersection.x <= sideMaxX && intersection.y >= sideMinY && intersection.y <= sideMaxY;
            boolean onLine = intersection.x >= lineMinX && intersection.x <= lineMaxX && intersection.y >= lineMinY && intersection.y <= lineMaxY;
            if (onSide && onLine) {
                intersections.add(intersection);
            }

        }

        return intersectionPoints.addAll(intersections);
    }

    //https://mathworld.wolfram.com/Circle-LineIntersection.html
    private boolean findIntersection(Line line, Circle circle) {
        float r = circle.getRadius();
        float x1 = line.getP1().x - circle.x();
        float x2 = line.getP2().x - circle.x();
        float y1 = line.getP1().y - circle.y();
        float y2 = line.getP2().y - circle.y();
        float dx = x2 - x1;
        float dy = y2 - y1;
        float dr = (float) Math.sqrt(dx * dx + dy * dy);
        float D = x1 * y2 - x2 * y1;

        float incidence = (r * r * dr * dr) - (D * D);

        if(incidence >= 0) {
            float sqrtIncidence = (float) Math.sqrt(incidence);

            float resultx0 = circle.x() + ((D * dy) + (sgn(dy) * dx * sqrtIncidence)) / (dr * dr);
            float resultx1 = circle.x() + ((D * dy) - (sgn(dy) * dx * sqrtIncidence)) / (dr * dr);

            float resulty0 = circle.y() + (((-D) * dx) + (Math.abs(dy) * sqrtIncidence)) / (dr * dr);
            float resulty1 = circle.y() + (((-D) * dx) - (Math.abs(dy) * sqrtIncidence)) / (dr * dr);

            Vector2 intersection0 = new Vector2(resultx0, resulty0);
            Vector2 intersection1 = new Vector2(resultx1, resulty1);

            Vector2 A = line.getP1().cpy();
            Vector2 B = line.getP2().cpy();
            float lineMinX = Math.min(A.x, B.x);
            float lineMaxX = Math.max(A.x, B.x);
            float lineMinY = Math.min(A.y, B.y);
            float lineMaxY = Math.max(A.y, B.y);

            boolean ret = false;
            if(intersection0.x >= lineMinX && intersection0.x <= lineMaxX && intersection0.y >=lineMinY && intersection0.y <= lineMaxY) {
                intersectionPoints.add(intersection0);
                ret = true;
            }
            if(intersection1.x >= lineMinX && intersection1.x <= lineMaxX && intersection1.y >=lineMinY && intersection1.y <= lineMaxY) {
                intersectionPoints.add(intersection1);
                ret = true;
            }
            return ret;
        }
        return false;
    }

    private int sgn(float x) {
        return x < 0 ? -1 : 1;
    }

    public boolean isIntersecting() {
        return isIntersecting;
    }

    public List<Vector2> getIntersectionPoints() {
        if(isIntersecting) {
            return intersectionPoints;
        } else {
            return null;
        }
    }

    public List<RenderableMovableShape> getIntersectingShapes() {
        return intersectingShapes;
    }

    public boolean isShapeIntersecting(int SID) {
        return intersectingShapes.stream().anyMatch(e -> e.SID() == SID);
    }

}
