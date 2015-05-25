package com.lpoo.pokemon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;
import com.lpoo.pokemon.camera.OrthoCamera;
import com.lpoo.pokemon.logic.Elements;
import com.lpoo.pokemon.logic.Move;
import com.lpoo.pokemon.logic.Pokemon;
import com.lpoo.pokemon.logic.Trainer;
import com.lpoo.pokemon.utilities.ProgressBar;
import com.lpoo.pokemon.utilities.ProgressBar.ProgressBarStyle;

public class MainGameScreen extends Screen {
	enum GameState {
		PLAYER1T, PLAYER2T, AFTERTURNS
	}

	BitmapFont tittleFont;
	OrthoCamera cam;
	Trainer trainer1, trainer2;
	Pokemon poke1, poke2, poke3, poke4;
	Move move1, move2, move3, move4, move5, move6, move7, move8;
	Elements test;
	ProgressBar bar, bar2;
	Sprite pok1, pok2;
	Stage stage;
	GameState gamestate;
	Dialog p1, p2;
	TextButton buttonChange2;
	TextButton buttonAttack2;
	TextButton buttonChange;
	TextButton buttonAttack;
	Table tableUI,tableUI2;
	@Override
	public void create() {
		cam = new OrthoCamera();
		cam.resize();

		test = new Elements();
		gamestate = GameState.PLAYER1T;
		// test.printPoke();

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

		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(10, 10, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		TextureRegionDrawable textureBar = new TextureRegionDrawable(
				new TextureRegion(new Texture(
						Gdx.files.internal("green_knob.jpg")), 1, 10));

		// Option Menus
		
		tableUI = new Table();
		tableUI2 = new Table();
		Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
		TextureRegionDrawable texturebar = new TextureRegionDrawable(
				new TextureRegion(TextureManager.BACKGROUND, 1, 10));
		BitmapFont font = new BitmapFont();
		LabelStyle ls = new LabelStyle(font, Color.WHITE);
		Label pl1 = new Label(trainer1.getName(), ls);
		Label pl2 = new Label(trainer2.getName(), ls);
		buttonAttack = new TextButton("Attack", uiSkin);	
		buttonChange = new TextButton("Change Pokemon", uiSkin);
		buttonAttack2 = new TextButton("Attack", uiSkin);	
		buttonChange2 = new TextButton("Change Pokemon", uiSkin);
		tableUI.setPosition(tableUI.getWidth()-100,tableUI.getHeight()+ 100);
		tableUI.add(pl1).padBottom(5).row();
		tableUI.add(buttonChange).size(150,60).padBottom(10).row();
		tableUI.add(buttonAttack).size(150,60).padBottom(10).row();
	    tableUI.setFillParent(true);
	    tableUI.bottom().right();
	    tableUI2.setPosition(tableUI.getWidth()+100,tableUI.getHeight()-40);
	    tableUI2.add(pl2).padBottom(5).row();
		tableUI2.add(buttonChange2).size(150,60).padBottom(10).row();
		tableUI2.add(buttonAttack2).size(150,60).padBottom(10).row();
		tableUI2.setFillParent(true);
		tableUI2.top().left();
	   

		// Progress Bar Settings

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

		stage = new Stage();
		stage.addActor(bar);
		stage.addActor(bar2);
		stage.addActor(tableUI);
		stage.addActor(tableUI2);
	}

	@Override
	public void update() {
		cam.update();
		bar.setValue(30);

	}

	float time, duration = 1;

	@Override
	public void render() {

		stage.getBatch().setProjectionMatrix(cam.combined);
		stage.getBatch().begin();
		// background and pokemons
		stage.getBatch().draw(TextureManager.BATTLEBACK, 0, 0);
		pok1.draw(stage.getBatch());
		pok2.draw(stage.getBatch());

		// Progress Bar RENDERING

		float delta = Gdx.graphics.getDeltaTime();

		if ((time += delta) > duration) {
			bar.setValue(bar.getValue());
			bar2.setValue(bar2.getValue());

			time = 0;
		}

		stage.act(delta);
		stage.getBatch().end();
		stage.draw();

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

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub

	}

}
