package com.max.solarsailer.Tools;

import com.badlogic.gdx.Gdx;
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
    Sprite keyFrame;
    float stateTime = 0f;


    public StarShipRenderer(SolarSailerMain game) {
        this.game = game;
        shapedrawerPixel = game.getAssMan().get(TexturePaths.WHITE_PIXEL);
        shapeDrawer = new ShapeDrawer(game.batch, new TextureRegion(shapedrawerPixel));
        shipAnimation = new Animation<TextureRegion>(1/3f, game.getAssMan().get(AtlasPaths.UFO_ATLAS, TextureAtlas.class).findRegions("ufoandalien"));
        shipAnimation.setPlayMode(Animation.PlayMode.LOOP);

        keyFrame = new Sprite(shipAnimation.getKeyFrame(stateTime));
        keyFrame.setSize(20,20);
    }

    public void init(){

    }


    public void render(){
        viewport.apply();
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        for (Star star : distantStars){
            shapeDrawer.setColor(star.color);
            shapeDrawer.filledCircle(star.position.x, star.position.y, star.radius);
        }
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

    void animateShip(){
        stateTime += Gdx.graphics.getDeltaTime();
        keyFrame.setPosition(ship.getPosition().x - keyFrame.getWidth()/2f, ship.getPosition().y - keyFrame.getHeight()/2f);
        keyFrame.setRegion(shipAnimation.getKeyFrame(stateTime));
        keyFrame.draw(game.batch);
    }

}
