package com.lpoo.pokemon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;
import com.lpoo.pokemon.camera.OrthoCamera;
import com.lpoo.pokemon.entity.EntityManager;
import com.lpoo.pokemon.logic.Elements;
import com.lpoo.pokemon.logic.Move;
import com.lpoo.pokemon.logic.Pokemon;
import com.lpoo.pokemon.logic.Trainer;

public class MainGameScreen extends Screen {

	OrthoCamera cam;
	Trainer trainer1, trainer2;
	Pokemon poke1, poke2, poke3, poke4;
	Move move1,move2,move3,move4,move5,move6,move7,move8;
	Elements test;

	@Override
	public void create() {
		cam = new OrthoCamera();
		cam.resize();

		test = new Elements();

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

	}

	@Override
	public void update() {
		cam.update();
		if(Gdx.input.isKeyPressed(Keys.ENTER)){
//			System.out.println(trainer2.getActivePokemon().getHPLeft());
//			trainer1.getActivePokemon().Attack(trainer2.getActivePokemon(), trainer1.getActivePokemon().getMoves().get(0));
//			System.out.println(trainer2.getActivePokemon().getHPLeft());
			
		}
			

	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(TextureManager.BATTLEBACK, 0, 0);
		sb.draw(TextureManager.BAR,
				MainGame.WIDTH - TextureManager.BAR.getWidth() - 40, 65);
		sb.draw(TextureManager.BAR, 65,
				MainGame.HEIGHT - TextureManager.BAR.getHeight() - 40);
//		sb.draw(trainer1.getActivePokemon().getTexture(), 50, 50);
//		sb.draw(trainer2.getActivePokemon().getTexture(), MainGame.WIDTH
//				- TextureManager.BLASTOISE.getWidth() - 50, MainGame.HEIGHT
//				- TextureManager.BLASTOISE.getHeight() - 50);
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
