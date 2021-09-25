package com.max.solarsailer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.max.solarsailer.Loading.Paths.SkinPaths;
import com.max.solarsailer.Loading.Paths.TexturePaths;
import com.max.solarsailer.SolarSailerMain;
import com.max.solarsailer.Tools.Prefs;

public class LvlSelectScreen extends ScreenAdapter {
    SolarSailerMain game;
    Prefs prefs;
    Stage stage;
    Skin skin;
    ScrollPane scrollPane;
    Table table;
    Texture backgroundTexture;
    Sprite background;

    public LvlSelectScreen(SolarSailerMain game) {
        this.game = game;
        prefs = new Prefs();
        skin = game.getAssMan().get(SkinPaths.SGX_SKIN);
        backgroundTexture = game.getAssMan().get(TexturePaths.MENU_BKGND);
        background = new Sprite(backgroundTexture);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        //background.setBounds(0,0, stage.getWidth(), stage.getHeight());
        table = new Table();
        table.setFillParent(true);
        //table.align(Align.top);
        Label label = new Label("Select Level", skin);
        table.add(label).center().expandX();
        table.row();

        Table scrollPaneTable = new Table();
        scrollPane = new ScrollPane(scrollPaneTable);
        for(int i = 1; i <= prefs.getHighestLvl(); i++){
            if( (i - 1) % 4 == 0){scrollPaneTable.row();}
            TextButton button = new TextButton(String.valueOf(i), skin);
            int finalI = i;
            button.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    InitialLvlScreen.lvl = finalI;
                    prefs.setLvl(finalI);
                    game.setScreen(game.initialLvlScreen);
                }
            });
            scrollPaneTable.add(button);
        }
        scrollPaneTable.row();
        table.add(scrollPane);
        table.row();

        TextButton menuButton = new TextButton("back to menu", skin);
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(() -> game.setScreen(game.menuScreen));
            }
        });
        table.add(menuButton);
        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GREEN);
        stage.act();
        stage.draw();
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        background.draw(game.batch);
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        if(width > background.getWidth()){
            background.setSize(width, background.getHeight());
        } if(height > background.getHeight()){
            background.setSize(background.getWidth(), height);
        }
    }


    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }


}
