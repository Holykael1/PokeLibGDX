package com.lpoo.pokemon.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.TextureManager;
import com.lpoo.pokemon.screen.GameOverScreen;
import com.lpoo.pokemon.screen.ScreenManager;
import com.sun.xml.internal.stream.events.EndElementEvent;

public class EntityManager {

	private final Array<Entity> entities = new Array<Entity>();
	private final Player player;
	
	public EntityManager(int amount){
		player=new Player( new Vector2(230,15), new Vector2(0,0),this );
		for(int i=0;i<amount;i++){
			float x = MathUtils.random(0, MainGame.WIDTH-TextureManager.ENEMY.getWidth());
			float y =MathUtils.random(MainGame.HEIGHT, MainGame.HEIGHT*3);
			float speed = MathUtils.random(2,4);
			addEntity(new Enemy(new Vector2(x,y),new Vector2(0,-speed)));  
		}
	}
	
	public void update(){
		for(Entity e : entities)
			e.update();
		for(Missile m : getMissile())
			if(m.checkEnd())
				entities.removeValue(m, false);
		player.update();
		checkColisions();
	}
	
	public void render(SpriteBatch sb){
		for(Entity e :entities)
			e.render(sb);
		player.render(sb);
	}
	
	private void checkColisions(){
		for(Enemy e: getEnemies()){
			for(Missile m: getMissile()){
				if(e.getBounds().overlaps(m.getBounds())){
					entities.removeValue(e, false);
					entities.removeValue(m, false);
					if(GameOver()){
						ScreenManager.setScreen(new GameOverScreen(true));
					}
				}
			}
			if(e.getBounds().overlaps(player.getBounds())){
				ScreenManager.setScreen(new GameOverScreen(false));
			}
		}
	}
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	private Array<Enemy> getEnemies(){
		Array<Enemy> ret= new Array<Enemy>();
		for(Entity e: entities)
			if(e instanceof Enemy)
				ret.add((Enemy)e);
		return ret;
	}
	
	private Array<Missile> getMissile(){
		Array<Missile> ret= new Array<Missile>();
		for(Entity e: entities)
			if(e instanceof Missile)
				ret.add((Missile)e);
		return ret;
	}
	
	public boolean GameOver(){
		return getEnemies().size<=0;
	}
}
