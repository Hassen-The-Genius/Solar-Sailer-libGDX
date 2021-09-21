package com.max.solarsailer.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.max.solarsailer.SolarSailerMain;

public class InitialLvlScreen extends ScreenAdapter {
    SolarSailerMain game;
    OrthographicCamera cam;
    ExtendViewport viewport;
    float minVPWidth = 800f;
    float minVPHeight = 400f;

    public InitialLvlScreen(SolarSailerMain game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }



    @Override
    public void dispose() {
        super.dispose();
    }
}
