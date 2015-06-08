package com.lpoo.pokemon.logic;

import java.util.Random; 
import java.util.Vector;

import com.lpoo.pokemon.logic.Move.AILMENTS;
import com.lpoo.pokemon.logic.Move.ELEMENTS;
import com.lpoo.pokemon.logic.PokemonBase;
import com.badlogic.gdx.graphics.Texture;

public class Pokemon extends PokemonBase {
	// Fields
	Vector<Move> moves;
	Vector<Move.DotComponent> DOT_EFFECTS;
	
	public AILMENTS STATUS_EFFECT;
	boolean dead;
	private boolean active;
	int sleepcounter;

	// Constructors
	/**
	 * Contructor n1 with pokemons textures
	 * @param texture Pokemon texture
	 * @param nome Pokemon name
	 * @param element Pokemon element
	 * @param HP Pokemon hitpoints stat
	 * @param att Pokemon attack stat
	 * @param def Pokemon defense stat
	 * @param SPA pokemon special attack stat
	 * @param SPD pokemon special defense stat
	 * @param ag pokemon agility stat
	 */
	public Pokemon(Texture texture, String nome, ELEMENTS element, double HP, 
			double att, double def, double SPA, double SPD, double ag) {
		this.texture = texture;
		Name = nome;
		moves = new Vector<Move>();
		ElementType = element;
		MaxHitPoints = HP;
		HitPoints = HP;
		Attack = att;
		Defense = def;
		SpecialAttack = SPA;
		SpecialDefense = SPD;
		Speed = ag;
		DOT_EFFECTS = new Vector<Move.DotComponent>();
	
		STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
		setActive(false);
	}
	
	/**
	 * Constructor n2
	 * @param nome Pokemon name
	 * @param element Pokemon element
	 * @param HP Pokemon hitpoints stat
	 * @param att Pokemon attack stat
	 * @param def Pokemon defense stat
	 * @param SPA pokemon special attack stat
	 * @param SPD pokemon special defense stat
	 * @param ag pokemon agility stat
	 */
	public Pokemon(String nome, ELEMENTS element, double HP, 
			double att, double def, double SPA, double SPD, double ag) {
		Name = nome;
		moves = new Vector<Move>();
		ElementType = element;
		MaxHitPoints = HP;
		HitPoints = HP;
		Attack = att;
		Defense = def;
		SpecialAttack = SPA;
		SpecialDefense = SPD;
		Speed = ag;
		DOT_EFFECTS = new Vector<Move.DotComponent>();
	
		STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
		setActive(false);
	}
	
	/**
	 * Constructor n3 with predefined moves 
	 * @param texture
	 * @param nome Pokemon name
	 * @param element Pokemon element
	 * @param HP Pokemon hitpoints stat
	 * @param att Pokemon attack stat
	 * @param def Pokemon defense stat
	 * @param SPA pokemon special attack stat
	 * @param SPD pokemon special defense stat
	 * @param ag pokemon agility stat
	 * @param mv vector of moves of that pokemon
	 */
	public Pokemon(Texture texture, String nome, ELEMENTS element, double HP, 
			double att, double def, double SPA, double SPD, double ag,
			Vector<Move> mv) {
		this.texture = texture;
		Name = nome;
		moves = mv;
		ElementType = element;
		MaxHitPoints = HP;
		HitPoints = HP;
		Attack = att;
		Defense = def;
		SpecialAttack = SPA;
		SpecialDefense = SPD;
		Speed = ag;
		DOT_EFFECTS = new Vector<Move.DotComponent>();
		STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
		setActive(false); 
	}
 
	// Getters
	/**
	 * get pokemon name
	 * @return pokemon name
	 */ 
	public String getName() {
		return Name;
	}

	/**
	 * get pokemon texture
	 * @return pokemon texture
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * get active Boolean
	 * @return active boolean
	 */
	public boolean getStat() {
		return isActive();
	}

	/**
	 * gets the pokemon's moves
	 * @return pokemon's moves vector
	 */
	public Vector<Move> getMoves() {
		return moves;
	}

	/**
	 * gets the remaining HP of a pokemon
	 * @return HP left
	 */
	public double getHPLeft() {
		return HitPoints;
	}

	/**
	 * the state of a pokemon
	 * @return true if he's dead, false otherwise
	 */
	public boolean isDead() {
		return dead;
	}

	// Modifiers
	/**
	 * change active state
	 * @param stat state
	 */
	void changeStat(boolean stat) { 
		setActive(stat);
	}

	/**
	 * adds an attack to a pokemon
	 * @param move move given to a pokemon
	 */
	public void addAttack(Move move) {
		moves.add(move);
	}

	/**
	 * kills a pokemon ( sets dead to true)
	 */
	public void die() { 
		dead = true;
	}

	// Attack/Damage functions
	/**
	 * attack function
	 * @param target attack target
	 * @param attack move to use on the targer
	 * @return damage done by the attack
	 */
	public double Attack(Pokemon target, Move attack) {  
		if (this.STATUS_EFFECT == AILMENTS.SLEEP) {
			System.out.println(this.Name + " is asleep");
			return -2; // it's asleep
		}
		if (this.STATUS_EFFECT == AILMENTS.FREEZE) {
			System.out.println(this.Name + " is frozen");
			return -3; // it's frozen
		}
		if (this.STATUS_EFFECT == AILMENTS.PARALYZE) {
			Random pr = new Random();
			if (pr.nextInt(4) == 3) {
				System.out.println(this.Name
						+ " is fully paralyzed and cannot attack");
				return -4; // it's fully paralyzed thus it cannot attack
			}
		}
		if (this.STATUS_EFFECT == AILMENTS.CONFUSION) {
			Random pr = new Random();
			if (pr.nextInt(2) == 1) {
				HitPoints -= (((2 * 50 + 10) / 250) * (Attack / this.Defense)
						* 50 + 2); // hurt itself in confusion
				System.out.println(this.Name + " has hurt itself in confusion");
				if (HitPoints <= 0) {
					System.out.println(this.Name + " has died");
					this.die();
				}
				return -5; // it's confused
			
			}
		}
		double damageDone = 0;
		Random r = new Random();
		int miss = r.nextInt(100) + 1;
		if (attack.Accuracy > miss) {
			switch (attack.ElementType) {
			case HEAL:
				HitPoints += (Attack * attack.BaseDamage);
				break;
			default:
				damageDone = CalculateAttackDamage(target, attack);
				target.HitPoints -= damageDone;
				System.out.println(target.Name + " receives " + damageDone
						+ " damage");
				// check for status effects
				int statusmiss = r.nextInt(100) + 1;
				if (attack.statusInflicted.inflictChance > statusmiss)
					if (attack.statusInflicted.ailment != AILMENTS.NEUTRAL) {
						switch (attack.statusInflicted.ailment) {
						case BURN:
							if (target.STATUS_EFFECT == AILMENTS.SLEEP) {
								target.sleepcounter = 0;
							}
							target.STATUS_EFFECT = AILMENTS.BURN;
							System.out
									.println(target.Name + " has been burned");
							break;
						case CONFUSION:
							if (target.STATUS_EFFECT == AILMENTS.SLEEP) {
								target.sleepcounter = 0;
							}
							target.STATUS_EFFECT = AILMENTS.CONFUSION;
							System.out.println(target.Name + " is confused");
							break;
						case FREEZE:
							if (target.STATUS_EFFECT == AILMENTS.SLEEP) {
								target.sleepcounter = 0;
							}
							target.STATUS_EFFECT = AILMENTS.FREEZE;
							System.out.println(target.Name + " is frozen");
							break;
						case PARALYZE:
							if (target.STATUS_EFFECT == AILMENTS.SLEEP) {
								target.sleepcounter = 0;
							}
							target.STATUS_EFFECT = AILMENTS.PARALYZE;
							System.out.println(target.Name
									+ " has been paralyzed");
							break;
						case POISON:
							if (target.STATUS_EFFECT == AILMENTS.SLEEP) {
								target.sleepcounter = 0;
							}
							target.STATUS_EFFECT = AILMENTS.POISON;
							System.out.println(target.Name
									+ " has been poisoned");
							break;
						case SLEEP:
							if (target.STATUS_EFFECT == AILMENTS.SLEEP) {
								target.sleepcounter = 0;
							}
							target.STATUS_EFFECT = AILMENTS.SLEEP;
							target.sleepcounter = r.nextInt(4) + 1;
							System.out.println(target.Name + " is asleep");
							break;
						default:
							break;

						}
					}

				// check for dot effects
				target.DOT_EFFECTS.add(attack.dotComponent);

				

				if (target.HitPoints <= 0) {
					target.die();
					System.out.println(target.Name + " has fainted");
				}
			}
			return damageDone;
		}
		System.out.println(this.Name + " has missed");
		return -1; // attack missed if here
	}

	// http://bulbapedia.bulbagarden.net/wiki/Damage formulas
	/**
	 * calculates the effectiveness given the elemets of the pokemon and the move
	 * @param target targeted pokemon
	 * @param attack2 atack used on the target
	 * @return damage done
	 */
	double CalculateAttackDamage(Pokemon target, Move attack2) {
		Random randomGenerator = new Random();
		double randomValue = randomGenerator.nextFloat() * (1 - 0.85) + 0.85;
		double damage, modifier, critical = 1;
		double STAB = 1; // same-type atack bonus;
		double type = CalculateEffectiveness(target, attack2); // type
																// effectiveness

		// is it a critical attack?
		int isCrit = randomGenerator.nextInt(5);
		if (isCrit == 2)
			critical = 2;

		// STAB check
		if (ElementType == attack2.ElementType)
			STAB = 1.5;

		modifier = STAB * type * critical * randomValue;
		if (attack2.ElementType == ElementType.PHYSICAL)
			damage = (((2 * 50 + 10) / 250) * (Attack / target.Defense)
					* attack2.BaseDamage + 2)
					* modifier * 5;
		else
			damage = (((2 * 50 + 10) / 250)
					* (SpecialAttack / target.SpecialDefense)
					* attack2.BaseDamage + 2)
					* modifier * 7.75;
		return damage;
	}

	double CalculateEffectiveness(Pokemon target, Move attack) {
		switch (attack.ElementType) {
		case FIRE:
			if (target.ElementType == ElementType.ICE) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.FIRE) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			} else if (target.ElementType == ElementType.WATER) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			} else if (target.ElementType == ElementType.ROCK) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			}
			break;
		case WATER:
			if (target.ElementType == ElementType.FIRE) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.ROCK) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.WATER) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			}
			break;
		case ELECTRIC:
			if (target.ElementType == ElementType.FLYING) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.WATER) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.ELECTRIC) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			}
			break;
		case ROCK:
			if (target.ElementType == ElementType.FIRE) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.ICE) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.FLYING) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.ROCK) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			}
			break;
		case PHYSICAL:
			if (target.ElementType == ElementType.ROCK) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			}
			break;
		case ICE:
			if (target.ElementType == ElementType.FLYING) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.WATER) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			} else if (target.ElementType == ElementType.ICE) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			}
			break;
		case FLYING:
			if (target.ElementType == ElementType.ROCK) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			} else if (target.ElementType == ElementType.ELECTRIC) {
				System.out.println(Name + " used " + attack.getName() + ".");
				return 0.5;
			}
			break;
		}
		System.out.println(Name + " used " + attack.getName() + ".");
		return 1;
	}

	// Auxiliary functions
	/**
	 * displays the moves of a pokemon
	 */
	void displayMoves() {
		int n = 1;
		for (int i = 0; i < moves.size(); i++) {
			System.out.println(n + "- " + moves.get(i).getName());
			n++;
		}
	}

	/**
	 * updates the pokemon status after a turn such as DOT attacks, paralyze, sleep, confusion
	 */
	public void updatePokemonAfterTurn() { 
		if (this.isActive()) {
			// Handle DOTS
			for (int i = 0; i < DOT_EFFECTS.size(); i++) {
				if (DOT_EFFECTS.get(i).duration <= 0) {
					DOT_EFFECTS.remove(i);
				} else {
					this.HitPoints -= DOT_EFFECTS.get(i).DamagePerTurn;
					System.out.println("Damage Over time Effect dealt "
							+ DOT_EFFECTS.get(i).DamagePerTurn + " to"
							+ this.Name);
					DOT_EFFECTS.get(i).duration--;
				}
			}

			// Handle status effects
			switch (this.STATUS_EFFECT) {
			case BURN:
				this.HitPoints -= MaxHitPoints / 8;
				System.out.println(this.Name + " feels the burn");
				if (HitPoints <= 0) {
					System.out.println(this.Name + " has died");
					this.die();
				}
				break;
			// See if freeze remains
			case FREEZE:
				Random stopfreeze = new Random();
				if (stopfreeze.nextInt(5) == 4) {
					this.STATUS_EFFECT = AILMENTS.NEUTRAL;
					System.out.println(this.Name + " is no longer frozen");
				}
				break;
			case POISON:
				this.HitPoints -= MaxHitPoints / 16;
				System.out.println(this.Name + " receives poison damage");
				if (HitPoints <= 0) {
					System.out.println(this.Name + " has died");
					this.die();
				}
				break;
			case SLEEP:
				if (sleepcounter <= 0) {
					this.STATUS_EFFECT = AILMENTS.NEUTRAL;
					System.out.println(this.Name + " is no longer asleep");
				} else {
					sleepcounter--;
					System.out.println(this.Name + " is still sleeping");
				}
				break;
			default:
				break;

			}
		}
	}

	/**
	 * gets the max HP of a pokemon
	 * @return max HP
	 */
	public double getMaxHp() {
		return MaxHitPoints;
	}

	/**
	 * finds a certain move
	 * @param command Move name
	 * @return move if found, null otherwise
	 */
	public Move findMove(String command) {
		for (int i = 0; i < this.getMoves().size(); i++) {
			if (this.getMoves().get(i).getName().equals(command))
				return this.getMoves().get(i);
		}
		return null;
	}
	
	/**
	 * gets the pokemon's speed
	 * @return speed
	 */
	public double getSpeed(){
		return this.Speed;
	}
	
	/**
	 * gets active status
	 * @return active status
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * sets active status
	 * @param active new active status
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
}
