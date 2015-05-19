package com.lpoo.pokemon.logic;

public class Move {
	enum STATS{
		ATTACK,DEFENSE,SPECIALATTACK,SPECIALDEFENSE,AGILITY,NULL
	}
	enum ELEMENTS
	{
		FIRE,WATER,ELECTRIC,ROCK,PHYSICAL,ICE,HEAL,FLYING
	}
	enum AILMENTS{
		NEUTRAL,CONFUSION,PARALYZE,SLEEP,POISON,BURN,FREEZE
	}
	int BaseDamage;
	boolean  hitflyingenemy;
	String Name;
	ELEMENTS ElementType;
	class StatusEffect{
		AILMENTS AILMENT;
		int inflictChance;
	}
	class StatChanging{
		STATS changedstat;
		int duration;
	}
	class DotComponent{
		int DamageOverTime;
		int duration; //lasting effects
	}
	int PP;
	int Accuracy;	
	
	public Move(String nome,ELEMENTS element,int damage, int pp, int acc) {
		Name=nome;
		ElementType=element;
		BaseDamage=damage;
		PP=pp;
		Accuracy=acc;
	}

	String getName(){
		return Name;
	}
}
