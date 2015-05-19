package com.lpoo.pokemon.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.pokemon.camera.OrthoCamera;
import com.lpoo.pokemon.entity.EntityManager;
import com.lpoo.pokemon.entity.Player;

public class GameScreen extends Screen {

	private OrthoCamera cam;
	private EntityManager entityManager;
	
	@Override
	public void create() {
		cam = new OrthoCamera();
		entityManager = new EntityManager(2);
	}

	@Override
	public void update() {
		cam.update();
		entityManager.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		entityManager.render(sb);
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
