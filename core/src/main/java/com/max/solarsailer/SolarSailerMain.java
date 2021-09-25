package com.max.solarsailer;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.max.solarsailer.Screens.InitialLvlScreen;
import com.max.solarsailer.Screens.LoadingScreen;
import com.max.solarsailer.Screens.LvlSelectScreen;
import com.max.solarsailer.Screens.MenuScreen;


public class SolarSailerMain extends Game {
	//created 9/19/20
	public SpriteBatch batch;
	private AssetManager assMan;
	public LoadingScreen loadingScreen;
	public MenuScreen menuScreen;
	public InitialLvlScreen initialLvlScreen;
	public LvlSelectScreen lvlSelectScreen;


	@Override
	public void create() {
		batch = new SpriteBatch();
		assMan = new AssetManager();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();

	}

	@Override
	public void dispose() {
		batch.dispose();
		assMan.dispose();
		if (loadingScreen != null) loadingScreen.dispose();
		if(menuScreen != null) menuScreen.dispose();
		if(initialLvlScreen != null) initialLvlScreen.dispose();
		if(lvlSelectScreen != null) lvlSelectScreen.dispose();
	}

	public AssetManager getAssMan() {
		return assMan;
	}

}