package com.max.solarsailer.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Set;

//created 9/22/21
public class Star {

    float mag = 0f;
    float radius = 0f;
    float gravity = 0f;
    Vector2 position = new Vector2();
    float angle;
    Vector2 appliedGforce = new Vector2();
    Color color = new Color(MathUtils.random(.2f, 1f), MathUtils.random(.2f, 1f), MathUtils.random(.2f,1f), MathUtils.random(.2f, 1f));

    //moving the stars
    Vector2 velocity = new Vector2();
    Vector2 acceleration = new Vector2();
    float angleToMainStar;
    Vector2 appliedGForceToMainStar = new Vector2();
    Vector2 gForce = new Vector2();
    Array<Star> otherStars = new Array<>();
    private HashSet<Star> starsBehind = new HashSet<>();
    private HashSet<Star> starsInfront = new HashSet<>();
    private HashSet<Star> switchedStars = new HashSet<>();

    private Sprite sprite;
    private Sprite switchSprite;



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

    public Sprite getSprite() { return sprite; }

    public void setSprite(Texture texture) { sprite = new Sprite(texture);
        sprite.setSize(getRadius() *2f, getRadius() * 2f);
    }

    public Sprite getSwitchSprite() {
        return switchSprite;
    }

    public void setSwitchSprite(Sprite switchSprite) {
        this.switchSprite = switchSprite;
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

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
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

    public Vector2 getAppliedGForceToMainStar() {
        return appliedGForceToMainStar;
    }

    public void setAppliedGForceToMainStar(float x, float y) {
        appliedGForceToMainStar.x = x;
        appliedGForceToMainStar.y = y;
    }

    public Array<Star> getOtherStars() {
        return otherStars;
    }

    public void setOtherStars(Array<Star> stars) {
        otherStars.clear();
        for (Star star : stars){
            if(star != this){
            otherStars.add(star);
            }
        }
    }

    public float getAngleToMainStar() {
        return angleToMainStar;
    }

    public void setAngleFromMainStar(float angleToMainStar) {
        this.angleToMainStar = angleToMainStar;
    }

    public Vector2 getgForce() {
        return gForce;
    }

    public void setgForce(float x, float y) {
        gForce.x = x;
        gForce.y = y;
    }

    public HashSet<Star> getStarsBehind() {
        return starsBehind;
    }


    public HashSet<Star> getStarsInfront() {
        return starsInfront;
    }



    public HashSet<Star> getSwitchedStars() {
        return switchedStars;
    }

}
