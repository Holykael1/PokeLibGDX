package com.lpoo.pokemon.logic;

public class Move {
	// ENUMS
	public enum STATS {
		ATTACK, DEFENSE, SPECIALATTACK, SPECIALDEFENSE, SPEED, NULL
	}

	public enum ELEMENTS {
		FIRE, WATER, ELECTRIC, ROCK, PHYSICAL, ICE, HEAL, FLYING
	}

	public enum AILMENTS {
		NEUTRAL, CONFUSION, PARALYZE, SLEEP, POISON, BURN, FREEZE
	}

	

	

	// NESTED CLASSES
	class StatusEffect {
		AILMENTS ailment;
		int inflictChance;
		StatusEffect(AILMENTS ail, int ichance) {
			ailment = ail;
			inflictChance = ichance;
			
		}
	}

	class StatChanging {
		STATS changedstat;
		int duration;
		int quantity;

		StatChanging(STATS ch, int d, int q) {
			changedstat = ch;
			duration = d;
			quantity = q;
		}
	}

	class DotComponent {
		int DamagePerTurn;
		int duration; // lasting effects

		DotComponent(int dot, int d) {
			DamagePerTurn = dot;
			duration = d;
		}
	}

	// Fields
	int BaseDamage;
	boolean hitflyingenemy;
	String Name;
	ELEMENTS ElementType;
	int PP;
	int Accuracy;
	StatusEffect statusInflicted;
	DotComponent dotComponent;
	StatChanging statchanged;
	
	//Constructors
	public Move(String nome, ELEMENTS element, int damage, int pp, int acc,
			boolean hitfly, AILMENTS ailment,int inflictchance, STATS stat,int dur, int quantity, int dmgperturn, int dotdur) {
		Name = nome;
		ElementType = element;
		BaseDamage = damage;
		PP = pp;
		Accuracy = acc;
		statusInflicted = new StatusEffect(ailment,inflictchance);
		dotComponent = new DotComponent(dmgperturn,dotdur);
		statchanged = new StatChanging(stat,dur,quantity);
		hitflyingenemy = hitfly;
	}
	public Move(String nome,ELEMENTS element,int damage, int pp, int acc) {
		Name=nome;
		ElementType=element;
		BaseDamage=damage;
		PP=pp;
		Accuracy=acc;
	}public Move(){};
	//Getters
	public String getName() {
		return Name;
	}
	public ELEMENTS getElement(){
		return ElementType;
	}
}
