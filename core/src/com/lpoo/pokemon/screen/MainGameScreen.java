package com.lpoo.pokemon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;
import com.lpoo.pokemon.camera.OrthoCamera;
import com.lpoo.pokemon.logic.Elements;
import com.lpoo.pokemon.logic.Move;
import com.lpoo.pokemon.logic.Move.ELEMENTS;
import com.lpoo.pokemon.logic.Move.STATS;
import com.lpoo.pokemon.logic.Pokemon;
import com.lpoo.pokemon.logic.Trainer;
import com.lpoo.pokemon.utilities.ProgressBar;
import com.lpoo.pokemon.utilities.ProgressBar.ProgressBarStyle;

public class MainGameScreen extends Screen {
	enum GameState {
		PLAYER1T, PLAYER2T, AFTERTURNS, PLAYER1ANIMATION, PLAYER2ANIMATION;
		@Override
		public String toString() {
			switch (this) {
			case PLAYER1T:
				return "player 1 turn";
			case PLAYER2T:
				return "player 2 turn";
			case AFTERTURNS:
				return "after turns";
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	BitmapFont tittleFont;
	OrthoCamera cam;
	Trainer trainer1, trainer2;
	Pokemon poke1, poke2, poke3, poke4;
	Move move1, move2, move3, move4, move5, move6, move7, move8;
	Elements test;
	ProgressBar bar, bar2, bar12, bar22;
	Sprite flareon, blastoise, pikachu, lapras, pok1, pok2, par, psn, frz, slp,
			brn;
	Stage stage;
	GameState gamestate;
	String p1, p2;
	TextButton buttonChange2;
	TextButton buttonChange;
	Boolean blinking, blinking2, first, first2;
	TextButton m1, m2, m3, m4, a1, a2, a3, a4;
	Table tableUI, tableUI2;
	TextureAtlas f;
	TextureAtlas i;
	TextureAtlas w;
	TextureAtlas e;
	Animation fire;
	Animation ice;
	Animation water;
	Animation electric;

	@Override
	public void create() { 
		cam = new OrthoCamera();
		cam.resize();
		blinking = false;
		blinking2 = false;
		test = new Elements();
		gamestate = GameState.PLAYER1T;
		frz = new Sprite(TextureManager.FREEZE);
		brn = new Sprite(TextureManager.BURN);
		psn = new Sprite(TextureManager.POISON);
		slp = new Sprite(TextureManager.SLEEP);
		par = new Sprite(TextureManager.PARALYZED);
		f = new TextureAtlas(Gdx.files.internal("fire.pack"));
		i = new TextureAtlas(Gdx.files.internal("ice.pack"));
		w = new TextureAtlas(Gdx.files.internal("water.pack"));
		e = new TextureAtlas(Gdx.files.internal("electric.pack"));
		// fire= new Animation(1/15f,f.getRegions());

		ice = new Animation(1 / 16f, i.getRegions());
		water = new Animation(1 / 12f, w.getRegions());
		electric = new Animation(1 / 12f, e.getRegions());
		fire = new Animation(1 / 11f, f.getRegions());
		poke1 = test.findPokemon("Flareon");
		poke2 = test.findPokemon("Blastoise");
		poke3 = test.findPokemon("Pikachu");
		poke4 = test.findPokemon("Lapras");
		trainer1 = new Trainer("Raul");
		trainer2 = new Trainer("Manel");

		trainer1.addPokemon(poke2);
		trainer1.addPokemon(poke1);
		trainer2.addPokemon(poke3);
		trainer2.addPokemon(poke4);

		flareon = new Sprite(poke1.getTexture());
		blastoise = new Sprite(poke2.getTexture());
		pikachu = new Sprite(poke3.getTexture());
		lapras = new Sprite(poke4.getTexture());
		test.pok1.flip(true, false);
		test.pok2.flip(true, false);
		test.pok3.flip(true, false);
		test.pok4.flip(true, false);

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
		buttonChange = new TextButton("Change Pokemon", uiSkin);
		buttonChange2 = new TextButton("Change Pokemon", uiSkin);
		m1 = new TextButton(trainer1.getActivePokemon().getMoves().get(0)
				.getName(), uiSkin);
		m2 = new TextButton(trainer1.getActivePokemon().getMoves().get(1)
				.getName(), uiSkin);
		m3 = new TextButton(trainer2.getActivePokemon().getMoves().get(0)
				.getName(), uiSkin);
		m4 = new TextButton(trainer2.getActivePokemon().getMoves().get(1)
				.getName(), uiSkin);

		tableUI.setPosition(tableUI.getWidth() - 100, tableUI.getHeight() + 100);
		tableUI.add(pl1).padBottom(5).row();
		tableUI.add(buttonChange).size(150, 60).padBottom(10).row();
		tableUI.add(m1).size(150, 60).padBottom(10).row();
		tableUI.add(m2).size(150, 60).padBottom(10).row();
		tableUI.setFillParent(true);
		tableUI.bottom().right();
		tableUI2.setPosition(tableUI.getWidth() + 100, tableUI.getHeight() - 40);
		tableUI2.add(pl2).padBottom(5).row();
		tableUI2.add(buttonChange2).size(150, 60).padBottom(10).row();
		tableUI2.add(m3).size(150, 60).padBottom(10).row();
		tableUI2.add(m4).size(150, 60).padBottom(10).row();
		tableUI2.setFillParent(true);
		tableUI2.top().left();

		buttonChange.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				tableUI.setVisible(false);
				trainer1.changePokemon();
				updateHP();
				updateSpritesTrainer1();
				p1 = "change";
				gamestate = GameState.PLAYER2T;
			};
		});
		buttonChange2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				tableUI2.setVisible(false);
				trainer2.changePokemon();
			
				updateHP();
				updateSpritesTrainer2();
				p2 = "change";
				gamestate = GameState.AFTERTURNS;
			};
		});
		m1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tableUI.setVisible(false);
				p1 = trainer1.getActivePokemon().getMoves().get(0).getName();
				gamestate = GameState.PLAYER2T;
			};
		});
		m2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tableUI.setVisible(false);
				p1 = trainer1.getActivePokemon().getMoves().get(1).getName();
				gamestate = GameState.PLAYER2T;
			};
		});
		m3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tableUI2.setVisible(false);
				p2 = trainer2.getActivePokemon().getMoves().get(0).getName();
				gamestate = GameState.AFTERTURNS;
			};
		});
		m4.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tableUI2.setVisible(false);
				p2 = trainer2.getActivePokemon().getMoves().get(1).getName();
				gamestate = GameState.AFTERTURNS;
			};
		});
		
		// Progress Bar Settings

		ProgressBarStyle barStyle = new ProgressBarStyle(skin.newDrawable(
				"white", Color.DARK_GRAY), textureBar);

		// Progress Bars

		// bar
		bar = new ProgressBar(0, (float) trainer1.getTeam().get(0).getMaxHp(),
				1, false, barStyle);
		bar.setValue(bar.getMaxValue());
		bar.setSize(250, 25);
		bar.setAnimateInterpolation(Interpolation.linear);
		bar.setAnimateDuration(0.75f);
		bar.setPosition((float) (50 + flareon.getWidth() * 1.5),
				50 + flareon.getHeight() / 2);
		// bar
		bar12 = new ProgressBar(0,
				(float) trainer1.getTeam().get(1).getMaxHp(), 1, false,
				barStyle);
		bar12.setValue(bar12.getMaxValue());
		bar12.setSize(250, 25);
		bar12.setAnimateInterpolation(Interpolation.linear);
		bar12.setAnimateDuration(0.75f);
		bar12.setPosition((float) (50 + flareon.getWidth() * 1.5),
				50 + flareon.getHeight() / 2);
		bar12.setVisible(false);
		// bar2
		bar2 = new ProgressBar(0, (float) trainer2.getTeam().get(0).getMaxHp(),
				1, false, barStyle);

		bar2.setValue(bar2.getMaxValue());
		bar2.setAnimateInterpolation(Interpolation.linear);
		bar2.setAnimateDuration(0.75f);
		bar2.setSize(250, 25);
		bar2.setPosition((float) (MainGame.WIDTH - 1.5*flareon.getWidth() * 1.5),
				(MainGame.HEIGHT-flareon.getHeight()) + flareon.getHeight() / 2);
		bar22 = new ProgressBar(0,
				(float) trainer2.getTeam().get(1).getMaxHp(), 1, false,
				barStyle);

		bar22.setValue(bar22.getMaxValue());
		bar22.setAnimateInterpolation(Interpolation.linear);
		bar22.setAnimateDuration(0.75f);
		bar22.setSize(250, 25);
		bar22.setPosition((float) (MainGame.WIDTH - 1.5*flareon.getWidth() * 1.5),
				(MainGame.HEIGHT-flareon.getHeight()) + flareon.getHeight() / 2);
		bar22.setVisible(false);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(bar);
		stage.addActor(bar2);
		stage.addActor(bar22);
		stage.addActor(bar12);
		stage.addActor(tableUI);
		stage.addActor(tableUI2);
		updateSpritesTrainer1();
		updateSpritesTrainer2();
		// frz.setPosition(bar.getWidth()+20, bar.getHeight()+20);
		// slp.setPosition(bar.getWidth()+20, bar.getHeight()+20);
		// psn.setPosition(bar.getWidth()+20, bar.getHeight()+20);
		// brn.setPosition(bar.getWidth()+20, bar.getHeight()+20);
		// par.setPosition(bar.getWidth()+20, bar.getHeight()+20);
	}

	public void updateSpritesTrainer1()  {

		if (trainer1.getActivePokemon().getName() == "Flareon") {
			pok1 = test.pok1;
			pok1.setPosition(50, 50);
			if(trainer1.getActivePokemon()==trainer1.getTeam().get(1)){
				bar.setVisible(false);
				bar12.setVisible(true);
			}
			if(trainer1.getActivePokemon()==trainer1.getTeam().get(0)){
				bar.setVisible(true);
				bar12.setVisible(false);
			}
		}
		if (trainer1.getActivePokemon().getName() == "Blastoise") {
			pok1 = blastoise;
			pok1.setPosition(50, 50);
			if(trainer1.getActivePokemon()==trainer1.getTeam().get(1)){
				bar.setVisible(false);
				bar12.setVisible(true);
			}
			if(trainer1.getActivePokemon()==trainer1.getTeam().get(0)){
				bar.setVisible(true);
				bar12.setVisible(false);
			}
		}
		if (trainer1.getActivePokemon().getName() == "Pikachu") {
			pok1 = test.pok3;
			pok1.setPosition(50, 50);
			if(trainer1.getActivePokemon()==trainer1.getTeam().get(1)){
				bar.setVisible(false);
				bar12.setVisible(true);
			}
			if(trainer1.getActivePokemon()==trainer1.getTeam().get(0)){
				bar.setVisible(true);
				bar12.setVisible(false);
			}
		}
		if (trainer1.getActivePokemon().getName() == "Lapras") {
			pok1 = test.pok4;
			pok1.setPosition(50, 50);
			if(trainer1.getActivePokemon()==trainer1.getTeam().get(1)){
				bar.setVisible(false);
				bar12.setVisible(true);
			}
			if(trainer1.getActivePokemon()==trainer1.getTeam().get(0)){
				bar.setVisible(true);
				bar12.setVisible(false);
			}
		}
		m1.setText(trainer1.getActivePokemon().getMoves().get(0).getName());
		m2.setText(trainer1.getActivePokemon().getMoves().get(1).getName());
		// m1.setColor(colorButtons(trainer1.getActivePokemon().getMoves().get(0)));
		// m2.setColor(colorButtons(trainer1.getActivePokemon().getMoves().get(1)));

	}

	void updateSpritesTrainer2() { 
		if (trainer2.getActivePokemon().getName() == "Flareon") {
			pok2 = flareon;
			pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
					- pok2.getHeight());
			if(trainer2.getActivePokemon()==trainer2.getTeam().get(1)){
				bar2.setVisible(false);
				bar22.setVisible(true);
			}
			if(trainer2.getActivePokemon()==trainer2.getTeam().get(0)){
				bar2.setVisible(true);
				bar22.setVisible(false);
			}
		}
		if (trainer2.getActivePokemon().getName() == "Blastoise") {
			pok2 = test.pok2;
			pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
					- pok2.getHeight());
			if(trainer2.getActivePokemon()==trainer2.getTeam().get(1)){
				bar2.setVisible(false);
				bar22.setVisible(true);
			}
			if(trainer2.getActivePokemon()==trainer2.getTeam().get(0)){
				bar2.setVisible(true);
				bar22.setVisible(false);
			}
		}
		if (trainer2.getActivePokemon().getName() == "Pikachu") {
			pok2 = pikachu;
			pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
					- pok2.getHeight());
			if(trainer2.getActivePokemon()==trainer2.getTeam().get(1)){
				bar2.setVisible(false);
				bar22.setVisible(true);
			}
			if(trainer2.getActivePokemon()==trainer2.getTeam().get(0)){
				bar2.setVisible(true);
				bar22.setVisible(false);
			}
		}
		if (trainer2.getActivePokemon().getName() == "Lapras") {
			pok2 = lapras;
			pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
					- pok2.getHeight());
			if(trainer2.getActivePokemon()==trainer2.getTeam().get(1)){
				bar2.setVisible(false);
				bar22.setVisible(true);
			}
			if(trainer2.getActivePokemon()==trainer2.getTeam().get(0)){
				bar2.setVisible(true);
				bar22.setVisible(false);
			}
		}
		m3.setText(trainer2.getActivePokemon().getMoves().get(0).getName());
		m4.setText(trainer2.getActivePokemon().getMoves().get(1).getName());
		// m3.setColor(colorButtons(trainer2.getActivePokemon().getMoves().get(0)));
		// m4.setColor(colorButtons(trainer2.getActivePokemon().getMoves().get(1)));
	}

	@Override
	public void update() {
		cam.update();

		switch (gamestate) {
		case AFTERTURNS:
			System.out.println(gamestate.toString());
			tableUI.setVisible(false);
			tableUI2.setVisible(false);
			if (p1 != "change" && p2 != "change") {
				if (trainer1.getActivePokemon().getSpeed() > trainer2
						.getActivePokemon().getSpeed()) {
					if (trainer1.getActivePokemon().Attack(
							trainer2.getActivePokemon(),
							trainer1.getActivePokemon().findMove(p1)) > 0) {
						blinking2 = true;
						blinkingcounter2 = 50;
					}
					// play this move animation
					updateHP();
					if (!trainer2.getActivePokemon().isDead()) {

						if (trainer2.getActivePokemon().Attack(
								trainer1.getActivePokemon(),
								trainer2.getActivePokemon().findMove(p2)) > 0) {
							blinking = true;
							blinkingcounter1 = 50;
						}
						updateHP();
						// updateDeath1();
					}
				} else {
					if (trainer2.getActivePokemon().Attack(
							trainer1.getActivePokemon(),
							trainer2.getActivePokemon().findMove(p2)) > 0) {
						// play this move animation
						blinking = true;
						blinkingcounter1 = 50;
					}
					updateHP();
					if (!trainer1.getActivePokemon().isDead()) {
						if (trainer1.getActivePokemon().Attack(
								trainer2.getActivePokemon(),
								trainer1.getActivePokemon().findMove(p1)) > 0) {
							// play this move animation
							blinking2 = true;
							blinkingcounter2 = 50;
						}
						updateHP();
						// updateDeath2();
					}
				}
			}
			if (p1 == "change" && p2 != "change") {
				if (trainer2.getActivePokemon().Attack(
						trainer1.getActivePokemon(),
						trainer2.getActivePokemon().findMove(p2)) > 0) {
					// play this move animation
					blinking = true;
					blinkingcounter1 = 50;
				}
				updateHP();
				// updateDeath1();
			}
			if (p2 == "change" && p1 != "change") {
				if (trainer1.getActivePokemon().Attack(
						trainer2.getActivePokemon(),
						trainer1.getActivePokemon().findMove(p1)) > 0) {
					// play this move animation
					blinking2 = true;
					blinkingcounter2 = 50;
				}
				updateHP();
				// updateDeath2();
			}
			updatePostTurn();
			gamestate = GameState.PLAYER1T;
			break;
		case PLAYER1T:

			tableUI.setVisible(true);
			tableUI2.setVisible(false);

			break;
		case PLAYER2T:
			tableUI2.setVisible(true);
			tableUI.setVisible(false);
			break;
		default:
			break;

		}

	}

	void updatePostTurn() {
		trainer1.getActivePokemon().updatePokemonAfterTurn();
		trainer2.getActivePokemon().updatePokemonAfterTurn();
		updateHP();
	}

	void updateHP() {

		bar2.setValue((float) trainer2.getTeam().get(0).getHPLeft());
		bar22.setValue((float) trainer2.getTeam().get(1).getHPLeft());
		bar.setValue((float) trainer1.getTeam().get(0).getHPLeft());
		bar12.setValue((float) trainer1.getTeam().get(1).getHPLeft());

	}

	boolean updateDeath2() {
		if (trainer2.getActivePokemon().isDead()) {
			if (trainer2.TrainerLost()) {
				ScreenManager.setScreen(new GameOverScreen(true));
			} else {
				buttonChange2.setVisible(false);
				trainer2.changePokemon();
				if (trainer2.getActivePokemon() == trainer2.getTeam().get(0)) {
					bar2.setVisible(true);
					bar22.setVisible(false);
				} else {
					bar22.setVisible(true);
					bar2.setVisible(false);
				}
				updateSpritesTrainer2();
				return true;
			}

		}
		return false;

	}

	boolean updateDeath1() {
		if (trainer1.getActivePokemon().isDead()) {
			if (trainer1.TrainerLost()) {
				ScreenManager.setScreen(new GameOverScreen(true));

			} else {
				buttonChange.setVisible(false);
				trainer1.changePokemon();
				if (trainer1.getActivePokemon() == trainer1.getTeam().get(0)) {
					bar.setVisible(true);
					bar12.setVisible(false);
				} else {
					bar12.setVisible(true);
					bar.setVisible(false);
				}
				updateSpritesTrainer1();
				return true;
			}
		}
		return false;
	}

	float time, timeblinking, blinkduration, duration = 1;
	int blinkingcounter1, blinkingcounter2 = 0;
	float elapsedTime = 0;

	@Override
	public void render() { 

		if (blinkingcounter1 == 0) {
			blinking = false;
		}
		if (blinkingcounter2 == 0) {
			blinking2 = false;
		}
		stage.getBatch().setProjectionMatrix(cam.combined);
		stage.getBatch().begin();

		if (blinking == true) {
			if (blinkingcounter1 % 10 == 0) {
				pok1.setColor(0, 0, 0, 0);
				pok1.draw(stage.getBatch());
			}
		}

		if (blinking2 == true) {

			if (blinkingcounter2 % 10 == 0) {
				pok2.setColor(0, 0, 0, 0);
				pok2.draw(stage.getBatch());
			}
		}
		float delta = Gdx.graphics.getDeltaTime();
		if ((time += delta) > duration) {

			if (!blinking)
				if (trainer1.getActivePokemon() == trainer1.getTeam().get(0))
					bar.setValue(bar.getValue());
				else
					bar12.setValue(bar12.getValue());

			if (!blinking2)
				if (trainer2.getActivePokemon() == trainer2.getTeam().get(0))
					bar2.setValue(bar2.getValue());
				else
					bar22.setValue(bar22.getValue());

			time = 0;
		}

		// background and pokemons

		stage.getBatch().draw(TextureManager.BATTLEBACK, 0, 0);
		pok1.draw(stage.getBatch());
		pok2.draw(stage.getBatch());

		pok1.setColor(1, 1, 1, 1);
		pok2.setColor(1, 1, 1, 1);

		if (blinking2 == true) {
			if (trainer1.getActivePokemon().getName() == "Flareon") {
				stage.getBatch().draw(fire.getKeyFrame(elapsedTime, false),
						pok2.getX() + pok2.getWidth() / 3,
						pok2.getY() + pok2.getHeight() / 3);
				if (fire.isAnimationFinished(elapsedTime)) {
					elapsedTime = 0;
					// updateDeath2();
				}
			}
			if (trainer1.getActivePokemon().getName() == "Blastoise") {
				stage.getBatch().draw(water.getKeyFrame(elapsedTime, true),
						pok2.getX() + pok2.getWidth() / 3,
						pok2.getY() + pok2.getHeight() / 3);
				if (water.isAnimationFinished(elapsedTime)) {
					elapsedTime = 0;
					// updateDeath2();
				}
			}
			if (trainer1.getActivePokemon().getName() == "Pikachu") {
				stage.getBatch().draw(electric.getKeyFrame(elapsedTime, false),
						pok2.getX() + pok2.getWidth() / 3,
						pok2.getY() + pok2.getHeight() / 3);
				if (electric.isAnimationFinished(elapsedTime)) {
					elapsedTime = 0;
					// updateDeath2();
				}
			}
			if (trainer1.getActivePokemon().getName() == "Lapras") {
				stage.getBatch().draw(ice.getKeyFrame(elapsedTime, false),
						pok2.getX() + pok2.getWidth() / 3,
						pok2.getY() + pok2.getHeight() / 3);
				if (ice.isAnimationFinished(elapsedTime)) {
					elapsedTime = 0;
					// updateDeath2();
				}
			}
		}

		if (blinking == true) {
			if (trainer2.getActivePokemon().getName() == "Flareon") {
				stage.getBatch().draw(fire.getKeyFrame(elapsedTime, false),
						pok1.getX() + pok1.getWidth() / 3,
						pok1.getY() + pok1.getHeight() / 3);
				if (fire.isAnimationFinished(elapsedTime)) {
					elapsedTime = 0;
					// updateDeath1();
				}
			}
			if (trainer2.getActivePokemon().getName() == "Blastoise") {
				stage.getBatch().draw(water.getKeyFrame(elapsedTime, false),
						pok1.getX() + pok1.getWidth() / 3,
						pok1.getY() + pok1.getHeight() / 3);
				if (water.isAnimationFinished(elapsedTime)) {
					elapsedTime = 0;
					// updateDeath1();
				}
			}
			if (trainer2.getActivePokemon().getName() == "Pikachu") {
				stage.getBatch().draw(electric.getKeyFrame(elapsedTime, false),
						pok1.getX() + pok1.getWidth() / 3,
						pok1.getY() + pok1.getHeight() / 3);
				if (electric.isAnimationFinished(elapsedTime)) {
					elapsedTime = 0;
					// updateDeath1();
				}
			}
			if (trainer2.getActivePokemon().getName() == "Lapras") {
				stage.getBatch().draw(ice.getKeyFrame(elapsedTime, false),
						pok1.getX() + pok1.getWidth() / 3,
						pok1.getY() + pok1.getHeight() / 3);
				if (ice.isAnimationFinished(elapsedTime)) {
					elapsedTime = 0;
					// updateDeath1();
				}
			}
		}
		switch (trainer1.getActivePokemon().STATUS_EFFECT) {
		case BURN:
			brn.setPosition(bar.getX() + 10, bar.getY() + 10);
			brn.draw(stage.getBatch());
			break;
		case FREEZE:
			frz.setPosition(bar.getX() + 10, bar.getY() + 10);
			frz.draw(stage.getBatch());
			break;
		case NEUTRAL:
			break;
		case PARALYZE:
			par.setPosition(bar.getX() + 10, bar.getY() + 10);
			par.draw(stage.getBatch());
			break;
		case POISON:
			psn.setPosition(bar.getX() + 10, bar.getY() + 10);
			psn.draw(stage.getBatch());
			break;
		case SLEEP:
			slp.setPosition(bar.getX() + 10, bar.getY() + 10);
			slp.draw(stage.getBatch());
			break;
		default:
			break;

		}
		switch (trainer2.getActivePokemon().STATUS_EFFECT) {
		case BURN:
			brn.setPosition(bar2.getX() + 10, bar2.getY());
			brn.draw(stage.getBatch());
			break;
		case FREEZE:
			frz.setPosition(bar2.getX() + 10, bar2.getY());
			frz.draw(stage.getBatch());
			break;
		case NEUTRAL:
			break;
		case PARALYZE:
			par.setPosition(bar2.getX() + 10, bar2.getY());
			par.draw(stage.getBatch());
			break;
		case POISON:
			psn.setPosition(bar2.getX() + 10, bar2.getY());
			psn.draw(stage.getBatch());
			break;
		case SLEEP:
			slp.setPosition(bar2.getX() + 10, bar2.getY());
			slp.draw(stage.getBatch());
			break;
		default:
			break;

		}
	
		elapsedTime += delta;
		stage.act(delta);
		stage.getBatch().end();
		stage.draw();
		blinkingcounter1--;
		blinkingcounter2--;
		if (blinking == false) {
			updateDeath1();
		}
		if (blinking2 == false) {
			updateDeath2();
		}
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
