package com.max.solarsailer.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.max.solarsailer.Loading.Loader;
import com.max.solarsailer.SolarSailerMain;

public class LoadingScreen extends ScreenAdapter {
    public SolarSailerMain game;
    Loader loader;
    AssetManager assMan;
    OrthographicCamera cam;
    ExtendViewport viewport;

    public Sprite logo;
    public Sprite loadingBarBack;
    public Sprite loadingBarFront;


    public LoadingScreen(SolarSailerMain game) {
        this.game = game;
        assMan = game.getAssMan();
    }


    @Override
    public void show() {
        cam = new OrthographicCamera();
        viewport = new ExtendViewport(800, 400, cam);
        cam.position.set(800/2f, 400/2f, 0);
        init();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        update();

        viewport.apply();
        game.batch.setProjectionMatrix(cam.combined);
        cam.update();
        game.batch.begin();
        //Todo: Task to complete to the splash screen loading 9/20/21
        game.batch.end();

        if(game.getAssMan().isFinished()){
            //Todo: Initialize the screens.
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {

    }




    public void init(){
        loader = new Loader(game);
        loader.loadAtlases();
        loader.loadAudio();
        loader.loadFonts();
        loader.loadSkins();
        loader.loadTextures();
    }

    public float update(){
        if(!assMan.isFinished()) assMan.update();
        return assMan.getProgress();
    }
}
