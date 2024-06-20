package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class LineIntersection {

    Line line;
    RenderableMovableShape[] shapes;

    private boolean intersects = false;
    private Vector2 intersectionPoint;

    public LineIntersection(Line line, RenderableMovableShape[] shapes) {
        this.line = line;
        this.shapes = shapes;

        for(RenderableMovableShape shape : shapes) {
            if (shape instanceof Polygon) {
                findIntersection(line, (Polygon) shape);
            } else if (shape instanceof Circle) {
                findIntersection(line, (Circle) shape);
            }
        }
    }

    private void findIntersection(Line line, Polygon poly) {
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
                    //float t2 = -AB.crs(AC) / determinant;
                    intersection = A.cpy().add(AB.scl(t1));
                }
            }

            boolean onSide = intersection.x >= sideMinX && intersection.x <= sideMaxX && intersection.y >= sideMinY && intersection.y <= sideMaxY;
            boolean onLine = intersection.x >= lineMinX && intersection.x <= lineMaxX && intersection.y >= lineMinY && intersection.y <= lineMaxY;
            if (onSide && onLine) {
                intersections.add(intersection);
            }

        }

        intersections.forEach(this::checkAndSetClosestPoint);
    }

    //https://mathworld.wolfram.com/Circle-LineIntersection.html
    private void findIntersection(Line line, Circle circle) {
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

            if(intersection0.x >= lineMinX && intersection0.x <= lineMaxX && intersection0.y >=lineMinY && intersection0.y <= lineMaxY) {
                checkAndSetClosestPoint(intersection0);
            }
            if(intersection1.x >= lineMinX && intersection1.x <= lineMaxX && intersection1.y >=lineMinY && intersection1.y <= lineMaxY) {
                checkAndSetClosestPoint(intersection1);
            }
        }
    }

    private int sgn(float x) {
        return x < 0 ? -1 : 1;
    }

    private void checkAndSetClosestPoint(Vector2 intersection) {
        if(intersectionPoint == null) {
            intersectionPoint = intersection;
            intersects = true;
            return;
        }
        float previousDist = Math.abs(intersectionPoint.cpy().sub(this.line.getP1().cpy()).len2());
        float proposedPointDistance = Math.abs(intersection.cpy().sub(this.line.getP1().cpy()).len2());
        if(proposedPointDistance < previousDist) {
            intersectionPoint = intersection;
            intersects = true;
        }
    }

    public boolean intersects() {
        return intersects;
    }

    public Vector2 getIntersectionPoint() {
        if(intersects) {
            return intersectionPoint;
        } else {
            return null;
        }
    }

}
