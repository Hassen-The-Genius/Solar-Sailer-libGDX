package com.max.solarsailer.Tools;

import com.badlogic.gdx.math.Vector2;

public class Ship {
    //created 9/22/21
    Vector2 velocity = new Vector2();
    Vector2 acceleration = new Vector2();
    Vector2 position = new Vector2();
    float rotation = 0f;
    boolean crashed = false;
    boolean gravity = false;
    Vector2 gForce = new Vector2();

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float x, float y) {
        acceleration.x = x;
        acceleration.y = y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;

    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public boolean isGravity() {
        return gravity;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    public Vector2 getgForce() {
        return gForce;
    }

    public void setgForce(float x, float y) {
        gForce.x = x;
        gForce.y = y;
    }
}
