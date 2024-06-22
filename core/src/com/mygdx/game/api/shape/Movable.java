package com.mygdx.game.api.shape;

import com.badlogic.gdx.math.Vector2;

public abstract class Movable {

    private final float DRAG_PERCENT = 0.065f;
    private Vector2 position, velocity, dragAmt;

    public Movable() {
        position = new Vector2();
        velocity = new Vector2();
        dragAmt = new Vector2();
    }

    public float x() {
        return position.x;
    }

    public float y() {
        return position.y;
    }

    public float dx() {
        return velocity.x;
    }

    public float dy() {
        return velocity.y;
    }

    public Vector2 getPos() {
        return position;
    }

    public Vector2 getVel() {
        return velocity;
    }

    public void setPos(float x, float y) {
        this.setPos(new Vector2(x, y));
    }

    public void setPos(Vector2 pos) {
        this.position.set(pos);
    }

    public void setVel(float x, float y) {
        this.setVel(new Vector2(x, y));
    }

    public void setVel(Vector2 vel) {
        this.velocity.set(vel);
    }

    public void accelerate(Vector2 dv) {
        this.velocity.add(dv);
        this.dragAmt = new Vector2(this.velocity.x * DRAG_PERCENT, this.velocity.y * DRAG_PERCENT);
    }

    public void update() {
        position.add(velocity);
        this.velocity.sub(dragAmt);
    }

}
