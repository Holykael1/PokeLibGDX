package com.lpoo.pokemon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;
import com.lpoo.pokemon.camera.OrthoCamera;

public class GameOverScreen extends Screen {
	private Music vic;
	private OrthoCamera cam;
	private Texture texture;

	public GameOverScreen(boolean won) {
		vic = Gdx.audio.newMusic(Gdx.files.internal("victory.mp3"));
		vic.setVolume(0.3f);
		vic.play();

		if (won)
			texture = TextureManager.PLAYER1WIN;
		else
			texture = TextureManager.PLAYER2WIN;
	}

	@Override
	public void create() {
		cam = new OrthoCamera();
		cam.resize();
	}

	@Override
	public void update() {
		cam.update();
		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			vic.dispose();
			ScreenManager.setScreen(new MainMenuScreen());
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(texture, MainGame.WIDTH / 2 - texture.getWidth() / 2,
				MainGame.HEIGHT / 2 - texture.getHeight() / 2);
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
		cam.resize();
	}

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

}
