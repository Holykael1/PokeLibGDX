package com.lpoo.pokemon.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;

public class Missile extends Entity {

	public Missile(Vector2 pos){
		super(TextureManager.MISSILE, pos, new Vector2(0,5));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		pos.add(directions);
		
	}
	
	public boolean checkEnd(){
		return pos.y >= MainGame.HEIGHT;
	}

}
