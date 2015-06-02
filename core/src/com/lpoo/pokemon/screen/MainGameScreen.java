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
import com.lpoo.pokemon.logic.Pokemon;
import com.lpoo.pokemon.logic.Trainer;
import com.lpoo.pokemon.utilities.ProgressBar;
import com.lpoo.pokemon.utilities.ProgressBar.ProgressBarStyle;

public class MainGameScreen extends Screen {
	enum GameState {
		PLAYER1T, PLAYER2T, AFTERTURNS;
		 @Override
		  public String toString() {
		    switch(this) {
		    case PLAYER1T:
		    	return "player 1 turn";
		    case PLAYER2T:
		    	return "player 2 turn";
		    case AFTERTURNS:
		    	return "after turns";
		      default: throw new IllegalArgumentException();
		    }
	}
	}

	BitmapFont tittleFont;
	OrthoCamera cam;
	Trainer trainer1, trainer2;
	Pokemon poke1, poke2, poke3, poke4;
	Move move1, move2, move3, move4, move5, move6, move7, move8;
	Elements test;
	ProgressBar bar, bar2;
	Sprite flareon, blastoise, pikachu, lapras, pok1, pok2, ff, bf, pf, lf;
	Stage stage;
	GameState gamestate;
	String p1, p2;
	TextButton buttonChange2;
	TextButton buttonChange;

	TextButton m1, m2, m3, m4, a1, a2, a3, a4;
	Table tableUI, tableUI2;

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
		updateSpritesTrainer1();
		updateSpritesTrainer2();
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

		bar2.setValue(bar2.getMaxValue());
		bar2.setAnimateInterpolation(Interpolation.linear);
		bar2.setAnimateDuration(0.75f);
		bar2.setSize(250, 25);
		bar2.setPosition((float) (pok2.getX() - pok2.getWidth() * 1.5),
				pok2.getY() + pok2.getHeight() / 2);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(bar);
		stage.addActor(bar2);
		stage.addActor(tableUI);
		stage.addActor(tableUI2);
	}

	public void updateSpritesTrainer1() {
		
		if (trainer1.getActivePokemon().getName() == "Flareon") {
			pok1 = test.pok1;
			pok1.setPosition(50, 50);

		}
		if (trainer1.getActivePokemon().getName() == "Blastoise") {
			pok1 = blastoise;
			pok1.setPosition(50, 50);
		}
		if (trainer1.getActivePokemon().getName() == "Pikachu") {
			pok1 = test.pok3;
			pok1.setPosition(50, 50);
		}
		if (trainer1.getActivePokemon().getName() == "Lapras") {
			pok1 = test.pok4;
			pok1.setPosition(50, 50);
		}
		m1.setText(trainer1.getActivePokemon().getMoves().get(0).getName());
		m2.setText(trainer1.getActivePokemon().getMoves().get(1).getName());
		m1.setColor(colorButtons(trainer1.getActivePokemon().getMoves().get(0)));
		m2.setColor(colorButtons(trainer1.getActivePokemon().getMoves().get(1)));

	}

	void updateSpritesTrainer2() {
		if (trainer2.getActivePokemon().getName() == "Flareon") {
			pok2 = flareon;
			pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
					- pok2.getHeight());
		}
		if (trainer2.getActivePokemon().getName() == "Blastoise") {
			pok2 = test.pok2;
			pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
					- pok2.getHeight());
		}
		if (trainer2.getActivePokemon().getName() == "Pikachu") {
			pok2 = pikachu;
			pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
					- pok2.getHeight());
		}
		if (trainer2.getActivePokemon().getName() == "Lapras") {
			pok2 = lapras;
			pok2.setPosition(MainGame.WIDTH - pok2.getWidth(), MainGame.HEIGHT
					- pok2.getHeight());
		}
		m3.setText(trainer2.getActivePokemon().getMoves().get(0).getName());
		m4.setText(trainer2.getActivePokemon().getMoves().get(1).getName());
		m3.setColor(colorButtons(trainer2.getActivePokemon().getMoves().get(0)));
		m4.setColor(colorButtons(trainer2.getActivePokemon().getMoves().get(1)));
	}

	Color colorButtons(Move m) {
		switch (m.getElement()) {
		case ELECTRIC:
			return Color.YELLOW;
		case FIRE:
			return Color.RED;
		case FLYING:
			return Color.GRAY;
		case HEAL:
			return Color.CLEAR;
		case ICE:
			return Color.CYAN;
		case PHYSICAL:
			return Color.WHITE;
		case ROCK:
			return Color.ORANGE;
		case WATER:
			return Color.BLUE;
		default:
			break;

		}
		return Color.BLACK;
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
					trainer1.getActivePokemon().Attack(
							trainer2.getActivePokemon(),
							trainer1.getActivePokemon().findMove(p1));
					updateHP();
					trainer2.getActivePokemon().Attack(
							trainer1.getActivePokemon(),
							trainer2.getActivePokemon().findMove(p2));
				} else {
					System.out.println(p2);
					trainer2.getActivePokemon().Attack(
							trainer1.getActivePokemon(),
							trainer2.getActivePokemon().findMove(p2));
				
					updateHP();
					trainer1.getActivePokemon().Attack(
							trainer2.getActivePokemon(),
							trainer1.getActivePokemon().findMove(p1));
					updateHP();
				}
			}
			if(p1=="change" && p2!="change"){
				trainer2.getActivePokemon().Attack(
						trainer1.getActivePokemon(),
						trainer2.getActivePokemon().findMove(p2));
				updateHP();
			}
			if(p2=="change" && p1!="change"){
				trainer1.getActivePokemon().Attack(
						trainer2.getActivePokemon(),
						trainer1.getActivePokemon().findMove(p1));
				updateHP();
				
			}
			updatePostTurn();
			gamestate=GameState.PLAYER1T;
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
	void updatePostTurn(){
		trainer1.getActivePokemon().updatePokemonAfterTurn();
		trainer2.getActivePokemon().updatePokemonAfterTurn();
		updateHP();
	}
	void updateHP(){
		bar2.setValue((float) trainer2.getActivePokemon()
				.getHPLeft());
		bar.setValue((float) trainer1.getActivePokemon()
				.getHPLeft());		
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
