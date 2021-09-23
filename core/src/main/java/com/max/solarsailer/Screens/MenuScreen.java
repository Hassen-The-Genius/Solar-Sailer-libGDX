package com.max.solarsailer.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.max.solarsailer.SolarSailerMain;

public class MenuScreen extends ScreenAdapter {
    SolarSailerMain game;
    public MenuScreen(SolarSailerMain game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GREEN);
    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void dispose() {

    }
}
