package com.max.solarsailer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.max.solarsailer.Loading.Paths.SkinPaths;
import com.max.solarsailer.SolarSailerMain;


public class SelectLvlScreen extends ScreenAdapter {
    SolarSailerMain game;
    Stage stage;
    Skin skin;
    Table table;

    public  SelectLvlScreen(SolarSailerMain game)
    {
        this.game = game;
        skin = game.getAssMan().get(SkinPaths.SGX_SKIN);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.center();
        table.setFillParent(true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.05f,0.05f,0.15f, 1f);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
