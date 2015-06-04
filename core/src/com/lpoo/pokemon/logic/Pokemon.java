package com.lpoo.pokemon.logic;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import com.lpoo.pokemon.logic.Move.STATS;
import com.lpoo.pokemon.logic.Move.StatChanging;
import com.lpoo.pokemon.logic.Move.StatusEffect;
import com.lpoo.pokemon.logic.Move.DotComponent;
import com.lpoo.pokemon.logic.Move.AILMENTS;
import com.lpoo.pokemon.logic.Move.ELEMENTS;
import com.lpoo.pokemon.logic.PokemonBase;
import com.badlogic.gdx.graphics.Texture;

public class Pokemon extends PokemonBase {
	// Fields
	Vector<Move> moves;
	Vector<Move.DotComponent> DOT_EFFECTS;
	Vector<Move.StatChanging> STAT_CH_EFFECTS;
	AILMENTS STATUS_EFFECT;
	boolean dead;
	boolean active;
	int sleepcounter;

	// Constructors
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
		STAT_CH_EFFECTS = new Vector<Move.StatChanging>();
		STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
		active=false;
	}

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
		STAT_CH_EFFECTS = new Vector<Move.StatChanging>();
		STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
		active=false; 
	}
 
	// Getters
	public String getName() {
		return Name;
	}

	public Texture getTexture() {
		return texture;
	}

	public boolean getStat() {
		return active;
	}

	public Vector<Move> getMoves() {
		return moves;
	}

	public double getHPLeft() {
		return HitPoints;
	}

	public boolean isDead() {
		return dead;
	}

	// Modifiers
	void changeStat(boolean stat) { 
		active = stat;
	}

	public void addAttack(Move move) {
		moves.add(move);
	}

	void die() { 
		dead = true;
	}

	// Attack/Damage functions
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
				DOT_EFFECTS.add(attack.dotComponent);

				// check for stat changing effects
				if (attack.statchanged.changedstat != STATS.NULL) {
					STAT_CH_EFFECTS.add(attack.statchanged);
					switch (attack.statchanged.changedstat) {
					case ATTACK:
						target.Attack -= attack.statchanged.quantity;
						System.out.println(target.Name
								+ "'s attack has been reduced");
						break;
					case DEFENSE:
						target.Defense -= attack.statchanged.quantity;
						System.out.println(target.Name
								+ "'s defense has been reduced");
						break;
					case SPECIALATTACK:
						target.SpecialAttack -= attack.statchanged.quantity;
						System.out.println(target.Name
								+ "'s specialattack has been reduced");
						break;
					case SPECIALDEFENSE:
						target.SpecialDefense -= attack.statchanged.quantity;
						System.out.println(target.Name
								+ "'s specialdefense has been reduced");
						break;
					case SPEED:
						target.Speed -= attack.statchanged.quantity;
						System.out.println(target.Name
								+ "'speed has been reduced");
						break;
					default:
						break;
					}
				}

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
	void displayMoves() {
		int n = 1;
		for (int i = 0; i < moves.size(); i++) {
			System.out.println(n + "- " + moves.get(i).getName());
			n++;
		}
	}

	public void updatePokemonAfterTurn() { 
		if (this.active) {
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

			// Handle STAT_CH_EFFECTS
			for (int i = 0; i < STAT_CH_EFFECTS.size(); i++) {
				if (STAT_CH_EFFECTS.get(i).duration <= 0) {
					switch (STAT_CH_EFFECTS.get(i).changedstat) {
					case ATTACK:
						this.Attack += STAT_CH_EFFECTS.get(i).quantity;
						System.out.println(this.Name
								+ " attack stat has returned to normal");
						STAT_CH_EFFECTS.remove(i);
						break;
					case DEFENSE:
						this.Defense += STAT_CH_EFFECTS.get(i).quantity;
						System.out.println(this.Name
								+ " defense stat has returned to normal");
						STAT_CH_EFFECTS.remove(i);
						break;
					case SPECIALATTACK:
						this.SpecialAttack += STAT_CH_EFFECTS.get(i).quantity;
						System.out
								.println(this.Name
										+ " special attack stat has returned to normal");
						STAT_CH_EFFECTS.remove(i);
						break;
					case SPECIALDEFENSE:
						this.SpecialDefense += STAT_CH_EFFECTS.get(i).quantity;
						System.out
								.println(this.Name
										+ " special defense stat has returned to normal");
						STAT_CH_EFFECTS.remove(i);
						break;
					case SPEED:
						this.Speed += STAT_CH_EFFECTS.get(i).quantity;
						System.out.println(this.Name
								+ " speed stat has returned to normal");
						STAT_CH_EFFECTS.remove(i);
						break;
					default:
						break;

					}

				} else
					STAT_CH_EFFECTS.get(i).duration--;
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

	public double getMaxHp() {
		return MaxHitPoints;
	}

	public Move findMove(String command) {
		for (int i = 0; i < this.getMoves().size(); i++) {
			if (this.getMoves().get(i).getName().equals(command))
				return this.getMoves().get(i);
		}
		return null;
	}
	public double getSpeed(){
		return this.Speed;
	}
}
