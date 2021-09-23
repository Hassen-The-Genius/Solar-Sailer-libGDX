package com.max.solarsailer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import com.max.solarsailer.SolarSailerMain;
import com.max.solarsailer.Tools.Ship;
import com.max.solarsailer.Tools.Star;
import com.max.solarsailer.Tools.StarShipRenderer;

public class InitialLvlScreen extends ScreenAdapter {
    //created 9/21/21
    SolarSailerMain game;
    OrthographicCamera cam;
    ExtendViewport viewport;
    float minVPWidth = 800f;
    float minVPHeight = 400f;
    StarShipRenderer starShipRenderer;
    Ship ship;
    Vector2 gForce = new Vector2();


    public InitialLvlScreen(SolarSailerMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        cam = new OrthographicCamera();
        viewport = new ExtendViewport(minVPWidth, minVPHeight, cam);
        cam.position.set(minVPWidth/2, minVPHeight/2, 0);
        starShipRenderer = new StarShipRenderer(game);
        starShipRenderer.setCam(cam);
        starShipRenderer.setViewport(viewport);
        starShipRenderer.setDistantStars(createStars());
        ship = new Ship();

        ship.setPosition(-50, minVPHeight/2f);
        ship.setVelocity(1,0);
        starShipRenderer.setShip(ship);
        ship.setCrashed(false);
        ship.setGravity(false);
    }

    @Override
    public void render(float delta) {
        starShipRenderer.render();
        moveShip();
        setStarsAngle();
        setStarsAppliedGForce();
        setTotalAppliedGForce();
        setShipAcceleration();
        setShipVelocity(ship.isGravity());
        checkUserInput();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        cam.position.set(minVPWidth/2, minVPHeight/2, 0);
    }



    @Override
    public void dispose() {
    }

    public Array<Star> createStars(){
        int numOfStars = MathUtils.random(2,12);
        Array<Star> stars = new Array<>();
        for(int i = 0; i < numOfStars; i++){
            Star star = new Star();
            star.setRadius(MathUtils.random(2, 20));
            star.setGravity(star.getRadius() * star.getColor().a * .001f);
            star.setPosition(new Vector2(MathUtils.random(20, minVPWidth - 20), MathUtils.random(20, minVPHeight - 20)));
            stars.add(star);
        }
        return stars;
    }

    void moveShip(){
        ship.setPosition(ship.getPosition().x + ship.getVelocity().x, ship.getPosition().y + ship.getVelocity().y);
    }

    void setStarsAngle(){
        for(Star star : starShipRenderer.getDistantStars()){
           float angle = MathUtils.atan2(star.getPosition().y - ship.getPosition().y,
                   star.getPosition().x - ship.getPosition().x) * MathUtils.radiansToDegrees;
           angle = (((angle % 360) + 360) % 360);
           star.setAngle(angle);
        }
    }

    void setStarsAppliedGForce(){
        for(Star star : starShipRenderer.getDistantStars()){
            star.setAppliedGforce(MathUtils.cosDeg(star.getAngle()) * star.getGravity(),
                    MathUtils.sinDeg(star.getAngle()) * star.getGravity());
        }
    }

    void setTotalAppliedGForce(){
        gForce.x = 0;
        gForce.y = 0;

        for(Star star : starShipRenderer.getDistantStars()){
            gForce.x += star.getAppliedGforce().x;
            gForce.y += star.getAppliedGforce().y;
        }
    }

    void setShipAcceleration(){
        ship.setAcceleration(gForce.x, gForce.y);
    }

    void setShipVelocity(boolean shipGravity){
        if(shipGravity){
        ship.setVelocity(ship.getVelocity().x + ship.getAcceleration().x, ship.getVelocity().y + ship.getAcceleration().y);
        }
    }

    void checkUserInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(!ship.isGravity()){
                ship.setGravity(true);
            }else{
                ship.setGravity(false);
            }
        }
    }


}
