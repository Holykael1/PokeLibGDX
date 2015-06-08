package com.lpoo.test;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Test;

import com.lpoo.pokemon.TextureManager;
import com.lpoo.pokemon.logic.Move;
import com.lpoo.pokemon.logic.Pokemon;
import com.lpoo.pokemon.logic.Move.AILMENTS;
import com.lpoo.pokemon.logic.Move.ELEMENTS;
import com.lpoo.pokemon.logic.Trainer;
import com.sun.xml.internal.ws.message.FaultMessage;

public class tests {

	Vector<Pokemon> allPoke = new Vector<Pokemon>();
	Pokemon poke1 = new Pokemon("Moltres", ELEMENTS.FIRE, 197, 167, 156, 194,
			150, 156); // moltres
	Pokemon poke2 = new Pokemon("Squirtle", ELEMENTS.WATER, 151, 110, 121, 112,
			127, 104); // squirtle
	Pokemon poke3 = new Pokemon("Pikachu", ELEMENTS.ELECTRIC, 142, 117, 90,
			112, 101, 156);
	Pokemon poke4 = new Pokemon("Articuno", ELEMENTS.ICE, 197, 150, 167, 161,
			194, 150);
	Pokemon poke5 = new Pokemon("Teste", ELEMENTS.ICE, 197, 150, 167, 161, 194,
			150);

	Move move1 = new Move("Ember", ELEMENTS.FIRE, 40, 25, 100, true,
			AILMENTS.BURN, 10, 0, 0, 0);
	Move move2 = new Move("FlameThrower", ELEMENTS.FIRE, 90, 15, 100, true,
			AILMENTS.BURN, 10, 0, 0, 0);
	Move move3 = new Move("Water Gun", ELEMENTS.WATER, 40, 25, 100, true,
			AILMENTS.NEUTRAL, 0, 0, 0, 0);
	Move move4 = new Move("Water Pulse", ELEMENTS.WATER, 60, 20, 100, true,
			AILMENTS.CONFUSION, 20, 0, 0, 0);
	Move move5 = new Move("Thunder Shock", ELEMENTS.ELECTRIC, 40, 30, 100,
			true, AILMENTS.PARALYZE, 10, 0, 20, 2);
	Move move6 = new Move("Spark", ELEMENTS.ELECTRIC, 65, 20, 100, true,
			AILMENTS.PARALYZE, 30, 0, 0, 0);
	Move move7 = new Move("Ice Beam", ELEMENTS.ICE, 90, 10, 100, true,
			AILMENTS.FREEZE, 10, 0, 0, 0);
	Move move8 = new Move("Blizzard", ELEMENTS.ICE, 110, 10, 70, true,
			AILMENTS.FREEZE, 10, 0, 0, 0);
	Move move9 = new Move("Test Move", ELEMENTS.ICE, 90, 10, 100, true,
			AILMENTS.FREEZE, 10, 0, 0, 0);
	Move move10 = new Move();

	@Test
	public void pokePlacement() {
		String t1Name;
		boolean sameHP = false;
		// Name tests
		assertEquals("Moltres", poke1.getName());
		if (poke1.getMaxHp() == 197.0)
			sameHP = true;
		assertEquals(true, sameHP);
		assertEquals("Ember", move1.getName());

		// attack tests
		poke1.addAttack(move1);
		poke1.addAttack(move2);
		poke2.addAttack(move3);
		poke2.addAttack(move4);
		poke3.addAttack(move5);
		poke3.addAttack(move6);
		poke4.addAttack(move7);
		poke4.addAttack(move8);
		poke5.addAttack(move9);
		assertEquals(2, poke1.getMoves().size());
		
		boolean sameMove=false,nullMove=false;
		Move teste1 = poke1.findMove("Ember");
		if(teste1.getName()==move1.getName())
			sameMove=true;
		
		Move teste2 = poke1.findMove("NOPE");
		if(teste2==null)
			nullMove=true;
		assertEquals(true, nullMove	);

		Trainer t1 = new Trainer("Manecas");
		t1Name = t1.getName();
		assertEquals(t1Name, "Manecas");

		// trainer tests
		Trainer t2 = new Trainer("Jaquino");
		t2.setName("Rui");
		assertEquals(t2.getName(), "Rui");

		t1.addPokemon(poke1);
		t1.addPokemon(poke2);
		t2.addPokemon(poke3);
		t2.addPokemon(poke4);

		// Pokemon placements
		Vector<Pokemon> pokes = new Vector<Pokemon>();
		pokes.add(poke1);
		pokes.add(poke4);
		Trainer t3 = new Trainer("Miguel", pokes);
		t3.addPokemon(poke5);
		Vector<Pokemon> team3 = t3.getTeam();
		assertEquals(false, t3.TrainerLost());
		team3.get(0).die();
		team3.get(1).die();
		assertEquals(true, t3.TrainerLost());

		// change pokemon test
		boolean diffName = false;
		Pokemon p1 = t2.getActivePokemon();
		t2.changePokemon();
		Pokemon p2 = t2.getActivePokemon();
		if (p1.getName() != p2.getName())
			diffName = true;
		assertEquals(true, diffName);
		p1 = t2.getActivePokemon();
		t2.changePokemon();
		p2 = t2.getActivePokemon();
		if (p1.getName() != p2.getName())
			diffName = true;
		assertEquals(true, diffName);

		// Stat test
		assertEquals(2, t2.getTeam().size());

		assertEquals(true, t1.getActivePokemon().getStat());
		assertEquals(false, t1.getTeam().get(1).getStat());

		assertEquals(false, poke5.isDead());
		poke5.die();
		assertEquals(true, poke5.isDead());
	}

	@Test
	public void attackTest() {

		boolean test = false;
		double HP, damageDone;

		Pokemon pfire = new Pokemon("Fire", ELEMENTS.FIRE, 197, 150, 167, 161,
				194, 150);
		Pokemon pwater = new Pokemon("Water", ELEMENTS.WATER, 197, 150, 167,
				161, 194, 150);
		Pokemon pelec = new Pokemon("Electric", ELEMENTS.ELECTRIC, 197, 150,
				167, 161, 194, 150);
		Pokemon prock = new Pokemon("Rock", ELEMENTS.ROCK, 197, 150, 167, 161,
				194, 150);
		Pokemon pphysical = new Pokemon("Physical", ELEMENTS.PHYSICAL, 197,
				150, 167, 161, 194, 150);
		Pokemon pice = new Pokemon("Ice", ELEMENTS.ICE, 197, 150, 167, 161,
				194, 150);
		Pokemon pflying = new Pokemon("Flying", ELEMENTS.FLYING, 197, 150, 167,
				161, 194, 150);

		Move mfire = new Move("Fire", ELEMENTS.FIRE, 40, 25, 100, true,
				AILMENTS.BURN, 100, 0, 0, 0);
		Move mwater = new Move("Water", ELEMENTS.WATER, 40, 25, 100, true,
				AILMENTS.NEUTRAL, 100, 0, 0, 0);
		Move melec = new Move("Electric", ELEMENTS.ELECTRIC, 40, 25, 100, true,
				AILMENTS.PARALYZE, 100,  0, 20, 2);
		Move mrock = new Move("Rock", ELEMENTS.ROCK, 40, 25, 100, true,
				AILMENTS.CONFUSION, 100,  0, 0, 0);
		Move mphysical = new Move("Physical", ELEMENTS.PHYSICAL, 40, 25, 100,
				true, AILMENTS.POISON, 100, 0, 0, 0);
		Move mice = new Move("Ice", ELEMENTS.ICE, 40, 25, 100, true,
				AILMENTS.FREEZE, 100, 0, 0, 0);
		Move mflying = new Move("Flying", ELEMENTS.FLYING, 40, 25, 100, true,
				AILMENTS.SLEEP, 100, 0, 0, 0);

		pfire.addAttack(mfire);
		pwater.addAttack(mwater);
		pelec.addAttack(melec);
		prock.addAttack(mrock);
		pphysical.addAttack(mphysical);
		pice.addAttack(mice);
		pflying.addAttack(mflying);

		Trainer t1 = new Trainer("Fire/Water");
		t1.addPokemon(pfire);
		t1.addPokemon(pwater);
		Trainer t2 = new Trainer("Elec/Rock");
		t2.addPokemon(pelec);
		t2.addPokemon(prock);
		Trainer t3 = new Trainer("Physical/Ice");
		t3.addPokemon(pphysical);
		t3.addPokemon(pice);
		Trainer t4 = new Trainer("Fly");
		t4.addPokemon(pflying);

		// begin attack test
		// fire->ice
		test = false;
		HP = pice.getHPLeft();
		damageDone = pfire.Attack(pice, mfire);
		if (pice.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
		pice.updatePokemonAfterTurn();
		pice.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;

		// fire->fire
		test = false;
		HP = pfire.getHPLeft();
		damageDone = pfire.Attack(pfire, mfire);
		if (pfire.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test); 
		pfire.updatePokemonAfterTurn();
		pfire.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;

		// fire->water
		test = false;
		HP = pwater.getHPLeft();
		damageDone = pfire.Attack(pwater, mfire);
		if (pwater.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
		pwater.updatePokemonAfterTurn();
		pwater.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;

		// fire->rock
		test = false;
		HP = prock.getHPLeft();
		damageDone = pfire.Attack(prock, mfire);
		if (prock.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
		prock.updatePokemonAfterTurn();
		prock.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// water->fire
		test = false;
		HP = pfire.getHPLeft();
		damageDone = pwater.Attack(pfire, mwater);
		if (pfire.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
		pfire.updatePokemonAfterTurn();
		pfire.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// water->rock
		test = false;
		HP = prock.getHPLeft();
		damageDone = pwater.Attack(prock, mwater);
		if (prock.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		prock.updatePokemonAfterTurn();
		prock.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;

		// water->water
		test = false;
		HP = pwater.getHPLeft();
		damageDone = pwater.Attack(pwater, mwater);
		if (pwater.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pwater.updatePokemonAfterTurn();
		pwater.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;

		// electricity->Flying
		test = false;
		HP = pflying.getHPLeft();
		damageDone = pelec.Attack(pflying, melec);
		if (pflying.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pflying.updatePokemonAfterTurn();
		pflying.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;

		// electricity->water
		test = false;
		HP = pwater.getHPLeft();
		damageDone = pelec.Attack(pwater, melec);
		if (pwater.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pwater.updatePokemonAfterTurn();
		pwater.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// electricity->electricity
		test = false;
		HP = pelec.getHPLeft();
		damageDone = pelec.Attack(pelec, melec);
		if (pelec.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pelec.updatePokemonAfterTurn();
		pelec.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// rock->flying
		test = false;
		HP = pflying.getHPLeft();
		damageDone = prock.Attack(pflying, mrock);
		if (pflying.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pflying.updatePokemonAfterTurn();
		pflying.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// rock->ice
		test = false;
		HP = pice.getHPLeft();
		damageDone = prock.Attack(pice, mrock);
		if (pice.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pice.updatePokemonAfterTurn();
		pice.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// rock->fire
		test = false;
		HP = pfire.getHPLeft();
		damageDone = prock.Attack(pfire, mrock);
		if (pfire.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pfire.updatePokemonAfterTurn();
		pfire.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;

		// rock->rock
		test = false;
		HP = prock.getHPLeft();
		damageDone = prock.Attack(prock, mrock);
		if (prock.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		prock.updatePokemonAfterTurn();
		prock.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// physical->rock
		test = false;
		HP = prock.getHPLeft();
		damageDone = pphysical.Attack(prock, mphysical);
		if (prock.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		prock.updatePokemonAfterTurn();
		prock.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// ice->flying
		test = false;
		HP = pflying.getHPLeft();
		damageDone = pice.Attack(pflying, mice);
		if (pflying.getHPLeft() == HP - damageDone)
			test = true;
//		pflying.updatePokemonAfterTurn();
		assertEquals(true, test);
		pflying.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// ice->water
		test = false;
		HP = pwater.getHPLeft();
		damageDone = pice.Attack(pwater, mice);
		if (pwater.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pwater.updatePokemonAfterTurn();
		pwater.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// ice->ice
		test = false;
		HP = pice.getHPLeft();
		damageDone = pice.Attack(pice, mice);
		if (pice.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		pice.updatePokemonAfterTurn();
		pice.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// flying->rock
		test = false;
		HP = prock.getHPLeft();
		damageDone = pflying.Attack(prock, mflying);
		if (prock.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
//		prock.updatePokemonAfterTurn();
		prock.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		// flying->electricity
		test = false;
		HP = pelec.getHPLeft();
		damageDone = pflying.Attack(pelec, mflying);
		if (pelec.getHPLeft() == HP - damageDone)
			test = true;
		assertEquals(true, test);
		//pelec.updatePokemonAfterTurn();
		pelec.STATUS_EFFECT=Move.AILMENTS.NEUTRAL;
		
		
		//STATUS_EFEFCT Stuff
		//Sleep
		test=false;
		pfire.STATUS_EFFECT=Move.AILMENTS.SLEEP;
		damageDone=pfire.Attack(prock, mfire);
		if(damageDone==-2)
			test=true;
		assertEquals(true, test);
		
		//Freeze
		test=false;
		pfire.STATUS_EFFECT=Move.AILMENTS.FREEZE;
		damageDone=pfire.Attack(prock, mfire);
		if(damageDone==-3)
			test=true;
		assertEquals(true, test);
		
		//Paralyzed
		test=false;
		pfire.STATUS_EFFECT=Move.AILMENTS.FREEZE;
		damageDone=pfire.Attack(prock, mfire);
		
		//Confused
		test=false;
		pfire.STATUS_EFFECT=Move.AILMENTS.CONFUSION;
		damageDone=pfire.Attack(prock, mfire);
		
		

	}
}
