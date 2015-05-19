package com.lpoo.pokemon.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;

public class Enemy extends Entity {

	public Enemy(Vector2 pos, Vector2 directions) {
		super(TextureManager.ENEMY, pos, directions);
	}

	@Override
	public void update() {
		pos.add(directions);
		
		if(pos.y<=-TextureManager.ENEMY.getHeight()){
			float x=MathUtils.random(0,MainGame.WIDTH-TextureManager.ENEMY.getWidth());
			pos.set(x,MainGame.HEIGHT);
		}
			
	}

}
