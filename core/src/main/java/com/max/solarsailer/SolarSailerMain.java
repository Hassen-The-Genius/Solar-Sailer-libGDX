package com.max.solarsailer;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class SolarSailerMain extends Game {
	public SpriteBatch batch;
	private AssetManager assMan;


	@Override
	public void create() {
		batch = new SpriteBatch();
		assMan = new AssetManager();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	@Override
	public void dispose() {
		batch.dispose();

	}

	public AssetManager getAssMan() {
		return assMan;
	}

	public void setAssMan(AssetManager assMan) {
		this.assMan = assMan;
	}
}