package com.max.solarsailer;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.max.solarsailer.Screens.InitialLvlScreen;
import com.max.solarsailer.Screens.LoadingScreen;


public class SolarSailerMain extends Game {
	//created 9/19/20
	public SpriteBatch batch;
	private AssetManager assMan;
	public LoadingScreen loadingScreen;
	public InitialLvlScreen initialLvlScreen;


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
		if (loadingScreen != null) loadingScreen.dispose();
		if(initialLvlScreen != null) initialLvlScreen.dispose();
	}

	public AssetManager getAssMan() {
		return assMan;
	}

}