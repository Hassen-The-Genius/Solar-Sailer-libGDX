package com.max.solarsailer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.max.solarsailer.Loading.Paths.SkinPaths;
import com.max.solarsailer.Loading.Paths.TexturePaths;
import com.max.solarsailer.SolarSailerMain;
import com.max.solarsailer.Tools.Prefs;

public class MenuScreen extends ScreenAdapter {
    SolarSailerMain game;
    Stage stage;
    Skin skin;
    Table table;
    Prefs prefs;
    Texture backgroundTexture;
    Sprite background;
    Texture logo;

    public MenuScreen(SolarSailerMain game) {
        this.game = game;
        skin = game.getAssMan().get(SkinPaths.SGX_SKIN);
        prefs = new Prefs();
        backgroundTexture = game.getAssMan().get(TexturePaths.MENU_BKGND);
        background = new Sprite(backgroundTexture);
        logo = game.getAssMan().get(TexturePaths.LOGO);
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.getCamera().position.set(stage.getWidth() / 2f, stage.getHeight() / 2f, 0);
        Gdx.input.setInputProcessor(stage);
        background.setBounds(0,0, stage.getWidth(), stage.getHeight());
        table = new Table();
        table.setBounds(0,0,stage.getWidth(), stage.getHeight() / 4);
        table.center();
//        table.setFillParent(false);
        ImageTextButton continueButton = new ImageTextButton("Continue to LEVEL " + prefs.getLvl(), skin);
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                InitialLvlScreen.lvl = prefs.getLvl();
                Gdx.app.postRunnable(()-> game.setScreen(game.initialLvlScreen));
            }
        });
        table.add(continueButton).center();
        ImageTextButton selectedLVLButton = new ImageTextButton("Select LEVEL", skin);
        selectedLVLButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(()-> game.setScreen(game.lvlSelectScreen));
            }
        });
        table.add(selectedLVLButton).center();
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.getViewport().apply();
        stage.getCamera().update();
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        game.batch.draw(logo,50, stage.getHeight() / 4f, stage.getWidth() - 100, stage.getHeight() * (3f / 4f));
//        background.draw(game.batch);
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }


    @Override
    public void dispose() {
        if(stage != null) stage.dispose();
    }
}
