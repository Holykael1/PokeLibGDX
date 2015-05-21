package com.lpoo.pokemon.screen;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Select;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;
import com.lpoo.pokemon.teste;
import com.lpoo.pokemon.camera.OrthoCamera;
import com.lpoo.pokemon.logic.Trainer;

public class MainMenuScreen extends Screen implements TextInputListener {

	OrthoCamera cam;

	String text;

	private BitmapFont tittleFont;
	private BitmapFont selected;
	private BitmapFont nonSelected;
	

	// Vector<Boolean> selec = new Vector<Boolean>();

	GlyphLayout titleLayout = new GlyphLayout();
	GlyphLayout playLayout = new GlyphLayout();
	GlyphLayout menuSelecLayout = new GlyphLayout();
	GlyphLayout exitLayout = new GlyphLayout();
	Vector<GlyphLayout> menuLayouts = new Vector<GlyphLayout>();

	private final String title = "Poke Arena";

	private int currentItem;
	private String[] menuItems;

	@Override
	public void create() {
		cam = new OrthoCamera();
		cam.resize();

		// selec.add(true);
		// selec.add(false);

		menuItems = new String[] { "Play", "Exit" };

		tittleFont = new BitmapFont(Gdx.files.internal("PokemonTitle.fnt"));
		selected = new BitmapFont(Gdx.files.internal("PokemonSelec.fnt"));
		nonSelected = new BitmapFont(Gdx.files.internal("PokemonNonSelec.fnt"));
		text = "PokeArena";
		titleLayout.setText(tittleFont, text);
		playLayout.setText(selected, menuItems[0]);
		exitLayout.setText(nonSelected, menuItems[1]);
		menuLayouts.add(playLayout);
		menuLayouts.add(exitLayout);

		currentItem = 0;

		
	}

	@Override
	public void update() {
		cam.update();
		if (Gdx.input.isKeyJustPressed(Keys.W))
			if (currentItem > 0)
				currentItem--;
			else 
				currentItem=menuItems.length-1;
		if (Gdx.input.isKeyJustPressed(Keys.S))
			if (currentItem < menuItems.length - 1)
				currentItem++;
			else 
				//wait x miliseconds tog et input
				currentItem=0;
		 if(Gdx.input.isKeyJustPressed(Keys.ENTER))
		 select();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		
		sb.draw(TextureManager.BACKGROUND,0,0);

		float width = titleLayout.width;
		tittleFont.draw(sb, text, (MainGame.WIDTH - width) / 2, 600);

		for (int i = 0; i < menuItems.length; i++) {
			width = menuLayouts.get(i).width;
			if (currentItem == i) {
				selected.draw(sb, menuItems[i], (MainGame.WIDTH - width) / 2,
						325 - 115 * i);
			} else {
				nonSelected.draw(sb, menuItems[i],
						(MainGame.WIDTH - width) / 2, 325 - 115 * i);
			}

			// if(selec.get(i))
			// selected.draw(sb, menuItems[i], (MainGame.WIDTH-width)/2,
			// 325-115*i);
			// else
			// nonSelected.draw(sb, menuItems[i], (MainGame.WIDTH-width)/2,
			// 325-115*i);

		}

		

		sb.end();
	}

	private void select() {
		if (currentItem == 0) {
			ScreenManager.setScreen(new MainGameScreen());
		}
		if (currentItem == 1)
			Gdx.app.exit();
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
	public void canceled() {

	}

	@Override
	public void input(String text) {
		this.text = text;
	}

}
