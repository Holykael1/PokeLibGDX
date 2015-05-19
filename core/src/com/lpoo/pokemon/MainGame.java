package com.lpoo.pokemon;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.pokemon.screen.GameScreen;
import com.lpoo.pokemon.screen.MainGameScreen;
import com.lpoo.pokemon.screen.MainMenuScreen;
import com.lpoo.pokemon.screen.ScreenManager;

public class MainGame implements ApplicationListener {

	public static int WIDTH=1280,HEIGHT=720;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		batch=new SpriteBatch();
		ScreenManager.setScreen(new MainMenuScreen());
	}

	@Override
	public void dispose() {
		if(ScreenManager.getCurrentScreen()!=null)
			ScreenManager.getCurrentScreen().dispose();
		batch.dispose();
	}

	@Override
	public void pause() {
		if(ScreenManager.getCurrentScreen()!=null)
			ScreenManager.getCurrentScreen().pause();

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(ScreenManager.getCurrentScreen()!=null)
			ScreenManager.getCurrentScreen().update();
		
		if(ScreenManager.getCurrentScreen()!=null)
			ScreenManager.getCurrentScreen().render(batch);
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		if(ScreenManager.getCurrentScreen()!=null)
			ScreenManager.getCurrentScreen().resize(WIDTH, WIDTH);
	}

	@Override
	public void resume() {
		if(ScreenManager.getCurrentScreen()!=null)
			ScreenManager.getCurrentScreen().resume();

	}

}
