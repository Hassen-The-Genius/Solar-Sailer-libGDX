package com.max.solarsailer.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

//created 9/22/21
public class Star {

    float mag = 0f;
    float radius = 0f;
    float gravity = 0f;
    Vector2 position = new Vector2();
    float angle;
    Vector2 appliedGforce = new Vector2();

    Color color = new Color(MathUtils.random(.2f, 1f), MathUtils.random(.2f, 1f), MathUtils.random(.2f,1f), MathUtils.random(.2f, 1f));

    public void init(){



    }

    public float getMag() {
        return mag;
    }

    public void setMag(float mag) {
        this.mag = mag;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Vector2 getAppliedGforce() {
        return appliedGforce;
    }

    public void setAppliedGforce(float x, float y) {
        appliedGforce.x = x;
        appliedGforce.y = y;
    }
}
