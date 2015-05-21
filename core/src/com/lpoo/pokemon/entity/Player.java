package com.lpoo.pokemon.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.pokemon.TextureManager;

public class Player extends Entity {
	
	private final EntityManager entityManager;
	private long lastFire;

	public Player(Vector2 pos, Vector2 direction, EntityManager entityManager ) {
		super(TextureManager.PLAYER, pos, direction);
		this.entityManager=entityManager;
	}

	@Override
	public void update() {
		pos.add(directions); 
		if( Gdx.input.isKeyPressed(Keys.A))
			setDirection(-300, 0);
		else if( Gdx.input.isKeyPressed(Keys.D))
			setDirection(300, 0);
		else
			setDirection(0, 0);
		
		if(Gdx.input.isKeyPressed(Keys.SPACE))
			if(System.currentTimeMillis()-lastFire>=250){
				entityManager.addEntity(new Missile(pos.cpy().add(25,TextureManager.PLAYER.getHeight())));
				lastFire=System.currentTimeMillis();
			}
	}

}
