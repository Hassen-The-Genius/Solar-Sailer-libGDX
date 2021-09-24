package com.max.solarsailer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.max.solarsailer.Loading.Loader;
import com.max.solarsailer.SolarSailerMain;

public class LoadingScreen extends ScreenAdapter {
    public SolarSailerMain game;
    Loader loader;
    AssetManager assMan;
    OrthographicCamera cam;
    ExtendViewport viewport;
    float minVPWidth = 800;
    float minVPHeight = 400;

    public Sprite logo;
    public Sprite loadingBarBack;
    public Sprite loadingBarFront;
    public Texture zebraDying;

    float progress = 0f;

    public LoadingScreen(SolarSailerMain game) {
        this.game = game;
        assMan = game.getAssMan();
    }


    @Override
    public void show() {
        loader = new Loader(game);
        init();

        cam = new OrthographicCamera();
        viewport = new ExtendViewport(minVPWidth, minVPHeight, cam);
        cam.position.set(minVPWidth/2f, minVPHeight/2f, 0);

        logo = new Sprite(new Texture(Gdx.files.internal("loading/splah.png")));
        loadingBarBack = new Sprite(new Texture(Gdx.files.internal("loading/zebra.png")));
        loadingBarFront =  new Sprite(new Texture(Gdx.files.internal("loading/flyingbullet.png")));
        zebraDying = new Texture(Gdx.files.internal("loading/zebradying.png"));

        logo.setBounds(0,0,minVPWidth, minVPHeight);
        loadingBarFront.setBounds(minVPWidth - loadingBarFront.getWidth(), 0, 120f, 120f);
        loadingBarBack.setBounds(minVPWidth - 120, 0, 120f, 120f);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f,0f,0f,1f);

        viewport.apply();
        game.batch.setProjectionMatrix(cam.combined);
        cam.update();
        game.batch.begin();

        logo.draw(game.batch);
        loadingBarBack.draw(game.batch);
        loadingBarFront.draw(game.batch);
        game.batch.end();



        progress = update();
        if (progress > .75f && loadingBarBack.getTexture() != zebraDying){loadingBarBack.setTexture(zebraDying);}
        loadingBarFront.setPosition((minVPWidth * progress) - loadingBarFront.getWidth(), 0);

        if(game.getAssMan().isFinished()){
            game.menuScreen = new MenuScreen(game);
            game.initialLvlScreen = new InitialLvlScreen(game);
            //Todo: finish up setting additional lvls
            Gdx.app.postRunnable(() -> game.setScreen(game.menuScreen));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        logo.getTexture().dispose();
        loadingBarBack.getTexture().dispose();
        loadingBarFront.getTexture().dispose();
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
