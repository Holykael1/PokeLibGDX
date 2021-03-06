package com.lpoo.pokemon.logic;

public class Move {
	// ENUMS


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
	

	//Constructors
	/**
	 * Move constructor 2
	 * @param nome Move Name
	 * @param element elemental damage
	 * @param damage amount of damage
	 * @param pp nr of times we can use that move
	 * @param acc accuracy
	 * @param hitfly can it hit flying pokemons
	 * @param ailment post atack element
	 * @param inflictchance chance to inflict post attack effects
	 * @param quantity quantity
	 * @param dmgperturn damage per trn
	 * @param dotdur damage per turn
	 */
	public Move(String nome, ELEMENTS element, int damage, int pp, int acc,
			boolean hitfly, AILMENTS ailment,int inflictchance,int quantity, int dmgperturn, int dotdur) {
		Name = nome;
		ElementType = element;
		BaseDamage = damage;
		PP = pp;
		Accuracy = acc;
		statusInflicted = new StatusEffect(ailment,inflictchance);
		dotComponent = new DotComponent(dmgperturn,dotdur);
		
		hitflyingenemy = hitfly;

	}
	
	/**
	 *  Move constructor 2
	 * @param nome Move Name
	 * @param element elemental damage
	 * @param damage amount of damage
	 * @param pp nr of times we can use that move
	 * @param acc accuracy
	 */
	public Move(String nome,ELEMENTS element,int damage, int pp, int acc) {
		Name=nome;
		ElementType=element;
		BaseDamage=damage;
		PP=pp;
		Accuracy=acc;
	}
	
	/**
	 * Empty constructor
	 */
	public Move(){	};
	//Getters
	
	/**
	 * get Name of the move
	 * @return
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * get the element of the move
	 * @return
	 */
	public ELEMENTS getElement(){
		return ElementType;
	}
}
