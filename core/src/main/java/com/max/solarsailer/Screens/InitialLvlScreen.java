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
import com.max.solarsailer.Tools.FloatingIsland;
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
    FloatingIsland floatingIsland;
    public static int lvl = 5;



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

        floatingIsland = new FloatingIsland(game, cam, viewport);
        boolean isPositioned = false;
        do{
            float x = MathUtils.random(25, minVPWidth - 25);
            float y = MathUtils.random(25, minVPHeight - 25);
            for(Star star : starShipRenderer.getDistantStars()){
                if(Math.abs(x - star.getPosition().x) < star.getRadius() + floatingIsland.keyFrameWidth
                        || Math.abs(y - star.getPosition().y) < star.getRadius() + floatingIsland.keyFrameHeight){
                    continue;
                }else {
                    isPositioned = true;
                    floatingIsland.setPosition(x - floatingIsland.keyFrameWidth/2f, y - floatingIsland.keyFrameHeight/2f);
                }
            }
        }while(!isPositioned);


    }

    @Override
    public void render(float delta) {
        starShipRenderer.render();
        floatingIsland.render();
        moveShip();
        setStarsAngle();
        setStarsAppliedGForce();
        setTotalAppliedGForce();
        setShipAcceleration();
        setShipVelocity(ship.isGravity());
        floatingIsland.checkReached(starShipRenderer.getKeyFrame());
        checkShipCrashed(ship.isGravity());
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
        int numOfStars = MathUtils.random(1,lvl);
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
        if(!ship.isCrashed()){
        ship.setPosition(ship.getPosition().x + ship.getVelocity().x, ship.getPosition().y + ship.getVelocity().y);}
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
        ship.setgForce(0,0);

        for(Star star : starShipRenderer.getDistantStars()){
            ship.getgForce().x += star.getAppliedGforce().x;
            ship.getgForce().y += star.getAppliedGforce().y;
        }
    }

    void setShipAcceleration(){
        ship.setAcceleration(ship.getgForce().x, ship.getgForce().y);
    }

    void setShipVelocity(boolean shipGravity){
        if(shipGravity){
        ship.setVelocity(ship.getVelocity().x + ship.getAcceleration().x, ship.getVelocity().y + ship.getAcceleration().y);
        }
    }

    void checkShipCrashed(boolean gravity){
        if(gravity){
            for(Star star : starShipRenderer.getDistantStars()){
                if (Math.abs(star.getPosition().x - ship.getPosition().x) < star.getRadius() + starShipRenderer.shipWidth/2f
                && Math.abs(star.getPosition().y - ship.getPosition().y) < star.getRadius() + starShipRenderer.shipHeight/2f){
                    ship.setCrashed(true);
                }
            }
            if(ship.isCrashed()){
                //Todo: finish this crash
                Gdx.app.log(this.toString(), "ship crashed. oh boy, play crash animation then send to crash screen. and then menu screen, but for now menu");
                Gdx.app.postRunnable(()-> game.setScreen(game.menuScreen));
            }
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
            ship.setPosition(-50, minVPHeight/2f);
            ship.setVelocity(1,0);
        }
    }


}
