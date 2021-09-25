package com.max.solarsailer.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.max.solarsailer.Loading.Paths.AtlasPaths;
import com.max.solarsailer.Loading.Paths.AudioPaths;
import com.max.solarsailer.Loading.Paths.TexturePaths;
import com.max.solarsailer.SolarSailerMain;

import java.util.ArrayList;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class StarShipRenderer {
    //created 9/22/21
    SolarSailerMain game;
    OrthographicCamera cam;
    Viewport viewport;
    Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
    ShapeDrawer shapeDrawer;
    Texture shapedrawerPixel;
    Array<Star> distantStars;

    Ship ship;
    Animation<TextureRegion> shipAnimation;
    public Animation<TextureRegion> explosionAnimation;
    Sprite keyFrame;
    public float shipWidth = 60f;
    public float shipHeight = 60f;
    float stateTime = 0f;
    boolean timereset;

    Sound explosion;


    public StarShipRenderer(SolarSailerMain game) {
        this.game = game;
        shapedrawerPixel = game.getAssMan().get(TexturePaths.WHITE_PIXEL);
        shapeDrawer = new ShapeDrawer(game.batch, new TextureRegion(shapedrawerPixel));
        shipAnimation = new Animation<TextureRegion>(1/3f, game.getAssMan().get(AtlasPaths.UFO_ATLAS, TextureAtlas.class).findRegions("ufoandalien"));
        shipAnimation.setPlayMode(Animation.PlayMode.LOOP);
        explosionAnimation = new Animation<TextureRegion>(1/30f, game.getAssMan().get(AtlasPaths.EXPLOSION_ATLAS, TextureAtlas.class).findRegions("1"));
        explosionAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        keyFrame = new Sprite(shipAnimation.getKeyFrame(stateTime));
        keyFrame.setSize(shipWidth,shipHeight);
        timereset = false;
        explosion = game.getAssMan().get(AudioPaths.EXPLOSION);
    }

    public void init(){

    }


    public void render(){
        viewport.apply();
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        for (Star star : distantStars){
            //shapeDrawer.setColor(star.color);
            //shapeDrawer.filledCircle(star.position.x, star.position.y, star.radius);
            star.getSprite().setPosition(star.position.x - star.getRadius(), star.position.y - star.getRadius());
            star.getSprite().draw(game.batch);
        }
        //checkOverlap();
        animateShip();
        game.batch.end();
    }


    public void setDistantStars(Array<Star> distantStars) {
        this.distantStars = distantStars;
    }

    public Array<Star> getDistantStars() {
        return distantStars;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setCam(OrthographicCamera cam) {
        this.cam = cam;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Sprite getKeyFrame() {
        return keyFrame;
    }


    void animateShip(){
        if(!ship.isCrashed()){
            stateTime += Gdx.graphics.getDeltaTime();
            keyFrame.setPosition(ship.getPosition().x - keyFrame.getWidth()/2f, ship.getPosition().y - keyFrame.getHeight()/2f);
            keyFrame.setRegion(shipAnimation.getKeyFrame(stateTime));
            if(stateTime > shipAnimation.getAnimationDuration()){stateTime = 0;}
        } else{
            if(!timereset) {
                stateTime = 0f;
                timereset = true;
                explosion.play(1f);
            }
            stateTime += Gdx.graphics.getDeltaTime();
            keyFrame.setRegion(explosionAnimation.getKeyFrame(stateTime));
            if(explosionAnimation.isAnimationFinished(stateTime)){
                Gdx.app.postRunnable(()-> game.setScreen(game.menuScreen));}

        }
        keyFrame.draw(game.batch);
    }

    void checkOverlap(){
        for (int i = 0; i < distantStars.size; i++) {
            for (int j = 0; j < distantStars.size; j++) {
                Star main = distantStars.get(i);
                Star other = distantStars.get(j);
                if(i == j){continue;}

                //redoing the code under
                if (distantStars.get(i).getSprite().getBoundingRectangle().overlaps(distantStars.get(j).getSprite().getBoundingRectangle())){
                    if (i > j){
                        if(main.getStarsBehind().contains(other)){main.getStarsBehind().remove(other);
                        }else if (i < j)
                        main.getStarsInfront().add(other);

                        if(main.getSwitchSprite() != other.getSprite()){
                            distantStars.set(i, other);
                            distantStars.set(j, main);
                            main.setSwitchSprite(other.getSprite());
                            other.setSwitchSprite(main.getSprite());
                        }
                    }
                }else if(main.getSwitchSprite() == other.getSprite()){
                    main.setSwitchSprite(null);
                    other.setSwitchSprite(null);
                }

            }
        }
    }

}
