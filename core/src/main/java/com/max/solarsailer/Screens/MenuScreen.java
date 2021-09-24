package com.max.solarsailer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.max.solarsailer.Loading.Paths.SkinPaths;
import com.max.solarsailer.SolarSailerMain;

public class MenuScreen extends ScreenAdapter {
    SolarSailerMain game;
    Stage stage;
    Skin skin;
    Table table;

    public MenuScreen(SolarSailerMain game) {
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
        ImageTextButton continueButton = new ImageTextButton("Continue to LEVEL " + InitialLvlScreen.lvl, skin);
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(()-> game.setScreen(game.initialLvlScreen));
            }
        });
        table.add(continueButton).center();
        ImageTextButton selectedLVLButton = new ImageTextButton("Select LEVEL", skin);
        selectedLVLButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Todo: set up lvls screen
            }
        });
        //table.add(selectedLVLButton).center();
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GREEN);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
