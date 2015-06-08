package com.lpoo.pokemon;

import java.util.Vector; 

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lpoo.pokemon.logic.Move;
import com.lpoo.pokemon.logic.Pokemon;
import com.lpoo.pokemon.logic.Move.ELEMENTS;
import com.lpoo.pokemon.logic.Move.AILMENTS;
public class Elements {
	
	Vector<Pokemon> allPoke= new Vector<Pokemon>();
	Pokemon poke1 = new Pokemon(TextureManager.FLAREON,"Flareon", ELEMENTS.FIRE, 197, 167, 156,194, 150, 156); // moltres
	Pokemon poke2 = new Pokemon(TextureManager.BLASTOISE,"Blastoise", ELEMENTS.WATER, 151, 110, 121,112, 127, 104); // squirtle
	Pokemon poke3 = new Pokemon(TextureManager.PIKACHU,"Pikachu", ELEMENTS.ELECTRIC, 142, 117, 90,112, 101, 156);
	Pokemon poke4 = new Pokemon(TextureManager.LAPRAS,"Lapras", ELEMENTS.ICE, 197, 150, 167,161, 194, 150);
	public Sprite pok1 = new Sprite(poke1.getTexture());
	public Sprite pok2 = new Sprite(poke2.getTexture());
	public Sprite pok3 = new Sprite(poke3.getTexture());
	public Sprite pok4 = new Sprite(poke4.getTexture());
	Move move1 = new Move("Ember", ELEMENTS.FIRE, 40, 25, 100,true,AILMENTS.BURN,10,0,0,0);
	Move move2 = new Move("FlameThrower", ELEMENTS.FIRE, 90, 15, 100,true,AILMENTS.BURN,10,0,0,0);
	Move move3 = new Move("Water Gun", ELEMENTS.WATER, 40, 25,100,true,AILMENTS.NEUTRAL,0,0,0,0);
	Move move4 = new Move("Water Pulse", ELEMENTS.WATER, 60, 20, 100,true,AILMENTS.CONFUSION,20,0,0,0);
	Move move5 = new Move("Thunder Shock", ELEMENTS.ELECTRIC, 40, 30, 100,true,AILMENTS.PARALYZE,10,0,20,2);
	Move move6 = new Move("Spark", ELEMENTS.ELECTRIC, 65, 20, 100,true,AILMENTS.PARALYZE,30,0,0,0);
	Move move7 = new Move("Ice Beam", ELEMENTS.ICE, 90, 10, 100,true,AILMENTS.FREEZE,10,0,0,0);
	Move move8 = new Move("Blizzard", ELEMENTS.ICE, 110, 10, 70,true,AILMENTS.FREEZE,10,0,0,0);

	
	public Elements() {
		poke1.addAttack(move1);
		poke1.addAttack(move2);
		poke2.addAttack(move3);
		poke2.addAttack(move4);
		poke3.addAttack(move5);
		poke3.addAttack(move6);
		poke4.addAttack(move7);
		poke4.addAttack(move8);
		fillAllPoke();
	}
	
	private void fillAllPoke(){
		allPoke.add(poke1);
		allPoke.add(poke2);
		allPoke.add(poke3);
		allPoke.add(poke4);
	}
	
	public Pokemon findPokemon(String nome){
		for(int i =0;i<allPoke.size();i++)
			if(allPoke.get(i).getName()==nome)
				return allPoke.get(i);
		return null;
	}
	
	public void printPoke(){
		for(int i =0;i<allPoke.size();i++){
			if(allPoke.get(i).getName()==null)
				System.out.println("null");
			else
				System.out.println(allPoke.get(i).getName());
		}
	}
}
