package com.max.solarsailer.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.max.solarsailer.Loading.Paths.TexturePaths;
import com.max.solarsailer.Screens.InitialLvlScreen;
import com.max.solarsailer.SolarSailerMain;

public class FloatingIsland {
    SolarSailerMain game;
    OrthographicCamera cam;
    Viewport viewport;
    Vector2 position = new Vector2();
    Sprite keyFrame;
    public float keyFrameWidth = 64f;
    public float keyFrameHeight = 64f;

    public FloatingIsland(SolarSailerMain game, OrthographicCamera cam, Viewport viewport) {
        this.game = game;
        this.cam = cam;
        this.viewport = viewport;
        keyFrame = new Sprite((Texture) game.getAssMan().get(TexturePaths.GOAL));
        keyFrame.setSize(keyFrameWidth, keyFrameHeight);
    }

    public void render(){
        viewport.apply();
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        keyFrame.draw(game.batch);
        game.batch.end();
    }

    public Vector2 getPosition() {
         position.set(keyFrame.getX(), keyFrame.getY());
         return position;
    }

    public void setPosition(float x, float y) {
        keyFrame.setPosition(x - keyFrame.getWidth()/2, y - keyFrame.getHeight()/2);
    }

    public void checkReached(Sprite ufo){
        if(ufo != null) {
            if (keyFrame.getBoundingRectangle().contains(ufo.getBoundingRectangle())) {
                //Todo, set to menu screen
                Gdx.app.log(this.toString(), "goal reached .. increasing difficulty...");
                //Todo: increase lvl difficulty then set to menu for next game to be harder.
                InitialLvlScreen.lvl++;
                Gdx.app.postRunnable(() -> game.setScreen(game.menuScreen));
            }
        }
    }
}
