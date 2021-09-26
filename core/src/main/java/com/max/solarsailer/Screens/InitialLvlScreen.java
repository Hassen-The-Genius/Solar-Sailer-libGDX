package com.max.solarsailer.Screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.max.solarsailer.Loading.Paths.AudioPaths;
import com.max.solarsailer.Loading.Paths.TexturePaths;
import com.max.solarsailer.SolarSailerMain;
import com.max.solarsailer.Tools.FloatingIsland;
import com.max.solarsailer.Tools.Hud;
import com.max.solarsailer.Tools.Ship;
import com.max.solarsailer.Tools.Star;
import com.max.solarsailer.Tools.StarShipRenderer;

public class InitialLvlScreen extends ScreenAdapter {
    //created 9/21/21
    SolarSailerMain game;
    OrthographicCamera cam;
    ExtendViewport viewport;
    float minVPWidth = 3000f;
    float minVPHeight = 1500f;
    StarShipRenderer starShipRenderer;
    Ship ship;
    FloatingIsland floatingIsland;
    public static int lvl = 1;
    Hud hud;
    InputEvent hudInputEvent = new InputEvent();
    Texture backgroundTexture;
    Sprite background;
    Array<String> backgroundStrings = new Array<>();
    String currentBackgroundString;
    String nextBackGroundString;
    OrthographicCamera backGroundCam;
    Viewport backgroundViewport;
    Array<Texture> asteroids = new Array<>();

    Music dreamcatcher;




    public InitialLvlScreen(SolarSailerMain game) {
        this.game = game;
        //commented out backgroundstuff
        /*backgroundStrings.add(TexturePaths.CALDWELL_CROPPED, TexturePaths.CARINA_NEBULA_CROPPED,
                TexturePaths.NGC_1232_CROPPED, TexturePaths.NGC_2264_CROPPED);
        backgroundStrings.add(TexturePaths.NGC_3582_CROPPED,TexturePaths.ORION_NEBULA_CROPPED,
                TexturePaths.RCW_38_CROPPED, TexturePaths.TERZAN5_CROPPED);*/

        //code under added to do background
        backgroundTexture = game.getAssMan().get(TexturePaths.MENU_BKGND);
        background = new Sprite(backgroundTexture);
        asteroids.add(game.getAssMan().get(TexturePaths.A));
        asteroids.add(game.getAssMan().get(TexturePaths.B));
        asteroids.add(game.getAssMan().get(TexturePaths.C));
        asteroids.add(game.getAssMan().get(TexturePaths.D));
        asteroids.add(game.getAssMan().get(TexturePaths.E));
        asteroids.add(game.getAssMan().get(TexturePaths.F));
        asteroids.add(game.getAssMan().get(TexturePaths.G));

        dreamcatcher = game.getAssMan().get(AudioPaths.DREAM_CATCHER);


        dreamcatcher.setLooping(true);
        dreamcatcher.setVolume(.6f);
        dreamcatcher.play();
    }

    @Override
    public void show() {
        cam = new OrthographicCamera();
        viewport = new ExtendViewport(minVPWidth, minVPHeight, cam);
        cam.position.set(minVPWidth/2, minVPHeight/2, 0);
        //setting up background ... removed because i hated the backgrounds
        //setUpBackGround();
        backGroundCam = new OrthographicCamera();
        backgroundViewport = new ScreenViewport(backGroundCam);
        backGroundCam.position.set(backgroundViewport.getWorldWidth()/2f, backgroundViewport.getWorldHeight()/2f, 0);
        background.setBounds(0,0, backgroundViewport.getWorldWidth(), backgroundViewport.getWorldHeight());

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
            float x = MathUtils.random(45, minVPWidth - 45); //45 just cause i wanted to, should be 64, b4 it was 25
            float y = MathUtils.random(45, minVPHeight - 45);
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

        for (int i = 0; i < starShipRenderer.getDistantStars().size; i++){
            Star star = starShipRenderer.getDistantStars().get(i);
            star.setOtherStars(starShipRenderer.getDistantStars());
        }

        hud = new Hud(game);
        hud.init(ship);
        startMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        //rendering background
        backgroundViewport.apply();
        backGroundCam.update();
        game.batch.setProjectionMatrix(backGroundCam.combined);
        game.batch.begin();
        background.draw(game.batch);
        game.batch.end();

        starShipRenderer.render();
        floatingIsland.render();
        moveShip();
        setStarsAngle();
        setStarsAppliedGForce();
        setTotalAppliedGForce();
        setShipAcceleration();
        setShipVelocity(ship.isGravity());

        //star stuff
        moveStar();
        //this method to combine everything
        setOthersAngleandsetAppliedGForceToMainStarandsetTotalStarsActingGForce();

        setStarAcceleration();
        setStarVelocity();

        floatingIsland.checkReached(starShipRenderer.getKeyFrame());
        checkShipCrashed(ship.isGravity());
        checkUserInput();
        hud.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        cam.position.set(minVPWidth/2, minVPHeight/2, 0);
        hud.resize(width, height);
        backgroundViewport.update(width, height);
        backGroundCam.position.set(backgroundViewport.getWorldWidth()/2f, backgroundViewport.getWorldHeight()/2f, 0);
        //next is important, because unlike other viewports, this one grows, like extended in a way, so background must be resized
        background.setSize(width, height);
    }



    @Override
    public void dispose() {
        if(hud !=null)hud.dispose();
    }

    @Override
    public void hide() {
        //game.getAssMan().unload(currentBackgroundString);
        if(ship.isCrashed() && dreamcatcher.isPlaying()){
        dreamcatcher.pause();
        }
    }

    public Array<Star> createStars(){
        int numOfStars = MathUtils.random(1,lvl);
        //int numOfStars = lvl;
        Array<Star> stars = new Array<>();
        for(int i = 0; i < numOfStars; i++){
            Star star = new Star();
            star.setRadius(MathUtils.random(10, 10 * lvl));
            star.setGravity(star.getRadius()  * .0001f);
            star.setPosition(MathUtils.random(60, minVPWidth - 60), MathUtils.random(60, minVPHeight - 60));
            // 60 is for max star radius, nope changed to 25 * lvl
            star.setSprite(asteroids.random());
            stars.add(star);
        }
        return stars;
    }

    void moveShip(){
        if(!ship.isCrashed()){
        ship.setPosition(ship.getPosition().x + ship.getVelocity().x, ship.getPosition().y + ship.getVelocity().y);}
    }

    void moveStar(){
        for(Star star : starShipRenderer.getDistantStars()){
            star.setPosition(star.getPosition().x + star.getVelocity().x, star.getPosition().y + star.getVelocity().y);
        }
    }

    void setStarsAngle(){
        for(Star star : starShipRenderer.getDistantStars()){
           float angle = MathUtils.atan2(star.getPosition().y - ship.getPosition().y,
                   star.getPosition().x - ship.getPosition().x) * MathUtils.radiansToDegrees;
           angle = (((angle % 360) + 360) % 360);
           star.setAngle(angle);
        }
    }

    void setOthersAngleandsetAppliedGForceToMainStarandsetTotalStarsActingGForce(){
        for (Star star : starShipRenderer.getDistantStars()){
            float otherAngle;
            star.setgForce(0,0);
            for (Star otherStar : star.getOtherStars()){
                otherAngle = MathUtils.atan2(otherStar.getPosition().y - star.getPosition().y,
                         otherStar.getPosition().x - star.getPosition().x) *MathUtils.radiansToDegrees;
                otherAngle = (((otherAngle % 360) + 360) % 360);
                otherStar.setAngleFromMainStar(otherAngle);

                otherStar.setAppliedGForceToMainStar(MathUtils.cosDeg(otherStar.getAngleToMainStar()) * otherStar.getGravity(),
                        MathUtils.sinDeg(otherStar.getAngleToMainStar()) * otherStar.getGravity());

                star.getgForce().x += otherStar.getAppliedGForceToMainStar().x;
                star.getgForce().y += otherStar.getAppliedGForceToMainStar().y;
            }
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

    void setStarAcceleration() {
        for (Star star : starShipRenderer.getDistantStars()){
            star.setAcceleration(star.getgForce().x, star.getgForce().y);
        }
    }

    void setShipVelocity(boolean shipGravity){
        if(shipGravity){
        ship.setVelocity(ship.getVelocity().x + ship.getAcceleration().x, ship.getVelocity().y + ship.getAcceleration().y);
        }
    }

    void setStarVelocity(){
        for (Star star : starShipRenderer.getDistantStars()){
            star.setVelocity(star.getVelocity().x + star.getAcceleration().x, star.getVelocity().y + star.getAcceleration().y);
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
                if(dreamcatcher.isPlaying()) dreamcatcher.pause();
                //Gdx.app.log(this.toString(), "ship crashed. oh boy, play crash animation then send to crash screen. and then menu screen, but for now menu");
            }
        }
    }

    void checkUserInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            hudInputEvent.setType(InputEvent.Type.touchDown);
            hud.gravityOnOffButton.fire(hudInputEvent);
            hudInputEvent.setType(InputEvent.Type.touchUp);
            hud.gravityOnOffButton.fire(hudInputEvent);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
            ship.setPosition(-50, minVPHeight/2f);
            ship.setVelocity(1f,0);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            Gdx.app.postRunnable(()->game.setScreen(game.menuScreen));
        }
    }


     void setUpBackGround(){
         if(currentBackgroundString != null) {

             do {
                 nextBackGroundString = backgroundStrings.random();
             } while (currentBackgroundString.equals(nextBackGroundString));
             currentBackgroundString = nextBackGroundString;
         }else {
             currentBackgroundString = backgroundStrings.random();
         }
         game.getAssMan().load(currentBackgroundString, Texture.class);
         game.getAssMan().finishLoading();
         backgroundTexture = game.getAssMan().get(currentBackgroundString);
         background = new Sprite(backgroundTexture);
         background.setBounds(0,0, minVPWidth, minVPHeight);
     }

     public void startMusic(){
        if(!dreamcatcher.isPlaying()){
            dreamcatcher.play();
        }
     }
}
