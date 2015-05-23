package com.lpoo.pokemon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;
import com.lpoo.pokemon.camera.OrthoCamera;
import com.lpoo.pokemon.logic.Elements;
import com.lpoo.pokemon.logic.Move;
import com.lpoo.pokemon.logic.Pokemon;
import com.lpoo.pokemon.logic.Trainer;
import com.lpoo.bar.ProgressBar;
import com.lpoo.bar.ProgressBar.ProgressBarStyle;

public class MainGameScreen extends Screen {
	OrthoCamera cam;
	Trainer trainer1, trainer2;
	Pokemon poke1, poke2, poke3, poke4;
	Move move1, move2, move3, move4, move5, move6, move7, move8;
	Elements test;
	ProgressBar bar, bar2;
	Sprite pok1, pok2;
	Stage stage;

	@Override
	public void create() {
		cam = new OrthoCamera();
		cam.resize();

		test = new Elements();

		// test.printPoke();
		stage = new Stage();
		poke1 = test.findPokemon("Flareon");
		poke2 = test.findPokemon("Blastoise");
		poke3 = test.findPokemon("Pikachu");
		poke4 = test.findPokemon("Lapras");

		trainer1 = new Trainer("Raul");
		trainer2 = new Trainer("Manel");

		trainer1.addPokemon(poke1);
		trainer1.addPokemon(poke2);
		trainer2.addPokemon(poke3);
		trainer2.addPokemon(poke4);
		pok1 = new Sprite(trainer1.getActivePokemon().getTexture());
		pok1.flip(true, false);
		pok1.setPosition(50, 50);
		pok2 = new Sprite(trainer2.getActivePokemon().getTexture());
		if (trainer2.getActivePokemon().getName() == "Blastoise")
			pok2.flip(true, false);
		pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
				- pok2.getHeight());
		// Progress Bar Settings
		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(10, 10, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		TextureRegionDrawable textureBar = new TextureRegionDrawable(
				new TextureRegion(new Texture(
						Gdx.files.internal("green_knob.jpg")), 1, 10));
		ProgressBarStyle barStyle = new ProgressBarStyle(skin.newDrawable(
				"white", Color.DARK_GRAY), textureBar);

		// Progress Bars

		// bar
		bar = new ProgressBar(0,
				(float) trainer1.getActivePokemon().getMaxHp(), 1, false,
				barStyle);
		bar.setValue(bar.getMaxValue());
		bar.setSize(250, 25);
		bar.setAnimateInterpolation(Interpolation.linear);
		bar.setAnimateDuration(0.75f);

		bar.setPosition((float) (pok1.getX() + pok1.getWidth() * 1.5),
				pok1.getY() + pok1.getHeight() / 2);

		// bar2
		bar2 = new ProgressBar(0, (float) trainer2.getActivePokemon()
				.getMaxHp(), 1, false, barStyle);

		bar2.setValue(30);
		bar2.setAnimateInterpolation(Interpolation.linear);
		bar2.setAnimateDuration(0.75f);
		bar2.setSize(250, 25);
		bar2.setPosition((float) (pok2.getX() - pok2.getWidth() * 1.5),
				pok2.getY() + pok2.getHeight() / 2);

		stage.addActor(bar);
		stage.addActor(bar2);
	}

	@Override
	public void update() {
		cam.update();
		bar.setValue(10);
		bar2.setValue(20);
		pok2.setPosition(300, 300);

	}

	float time, duration = 1;

	@Override
	public void render(SpriteBatch sb) {

		sb.setProjectionMatrix(cam.combined);
		sb.begin();

		// background

		sb.draw(TextureManager.BATTLEBACK, 0, 0);
		// Pokemon1

		pok1.draw(sb);

		// Pokemon2

		pok2.draw(sb);

		// Progress Bar RENDERING

		float delta = Gdx.graphics.getDeltaTime();

		if ((time += delta) > duration) {
			bar.setValue(bar.getValue());
			bar2.setValue(bar2.getValue());
			time = 0;
		}

		stage.act(delta);
		stage.draw();

		
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
		cam.resize();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
