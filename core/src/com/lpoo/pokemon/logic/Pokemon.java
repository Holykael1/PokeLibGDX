package com.lpoo.pokemon.logic;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

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
	public Pokemon(Texture texture,String nome, ELEMENTS element, double HP, double att,
			double def, double SPA, double SPD, double ag) {
		this.texture=texture;
		Name = nome;
		moves = new Vector<Move>();
		ElementType = element;
		MaxHitPoints = HP;
		HitPoints = HP;
		Attack = att;
		Defense = def;
		SpecialAttack = SPA;
		SpecialDefense = SPD;
		Agility = ag;
		DOT_EFFECTS = new Vector<Move.DotComponent>();
		STAT_CH_EFFECTS = new Vector<Move.StatChanging>();
		STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
	}

	public Pokemon(Texture texture,String nome, ELEMENTS element, double HP, double att,
			double def, double SPA, double SPD, double ag, Vector<Move> mv) {
		this.texture=texture;
		Name = nome;
		moves = mv;
		ElementType = element;
		MaxHitPoints = HP;
		HitPoints = HP;
		Attack = att;
		Defense = def;
		SpecialAttack = SPA;
		SpecialDefense = SPD;
		Agility = ag;
		DOT_EFFECTS = new Vector<Move.DotComponent>();
		STAT_CH_EFFECTS = new Vector<Move.StatChanging>();
		STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
	}

	// Getters
	String getName() {
		return Name;
	}

	boolean getStat() {
		return active;
	}

	Vector<Move> getMoves() {
		return moves;
	}

	double getHPLeft() {
		return HitPoints;
	}

	boolean isDead() {
		return dead;
	}

	// Modifiers
	void changeStat(boolean stat) {
		active = stat;
	}

	void addAttack(Move move) {
		moves.add(move);
	}

	void die() {
		dead = true;
	}

	// Attack/Damage functions
//	double Attack(Pokemon target, Move attack) {
//		if (this.STATUS_EFFECT == Move.AILMENTS.SLEEP)
//			return -2; // it's asleep
//		if (this.STATUS_EFFECT == Move.AILMENTS.FREEZE)
//			return -3; // it's frozen
//		if (this.STATUS_EFFECT == Move.AILMENTS.PARALYZE) {
//			Random pr = new Random();
//			if (pr.nextInt(4) == 3)
//				return -4; // it's fully paralyzed thus it cannot attack
//		}
//		if (this.STATUS_EFFECT == Move.AILMENTS.CONFUSION) {
//			Random pr = new Random();
//			if (pr.nextInt(2) == 1) {
//				HitPoints -= (((2 * 50 + 10) / 250) * (Attack / this.Defense)
//						* 50 + 2); // hurt itself in confusion
//				return -5; // it's confused
//			}
//		}
//		double damageDone = 0;
//		Random r = new Random();
//		int miss = r.nextInt(100) + 1;
//		if (attack.Accuracy > miss) {
//			switch (attack.ElementType) {
//			case HEAL:
//				HitPoints += (Attack * attack.BaseDamage);
//				break;
//			default:
//				damageDone = CalculateAttackDamage(target, attack);
//
//				// check for status effects
//				int statusmiss = r.nextInt(100) + 1;
//				if (attack.statusInflicted.inflictChance > statusmiss)
//					if (attack.statusInflicted.ailment != Move.AILMENTS.NEUTRAL) {
//						switch (attack.statusInflicted.ailment) {
//						case BURN:
//							target.STATUS_EFFECT = Move.AILMENTS.BURN;
//							break;
//						case CONFUSION:
//							target.STATUS_EFFECT = Move.AILMENTS.CONFUSION;
//							break;
//						case FREEZE:
//							target.STATUS_EFFECT = Move.AILMENTS.FREEZE;
//							break;
//						case PARALYZE:
//							target.STATUS_EFFECT = Move.AILMENTS.PARALYZE;
//							break;
//						case POISON:
//							target.STATUS_EFFECT = Move.AILMENTS.POISON;
//							break;
//						case SLEEP:
//							target.STATUS_EFFECT = Move.AILMENTS.SLEEP;
//							break;
//						default:
//							break;
//
//						}
//					}
//
//				// check for dot effects
//				DOT_EFFECTS.add(attack.dotComponent);
//
//				// check for stat changing effects
//				if (attack.statchanged.changedstat != Move.STATS.NULL) {
//					STAT_CH_EFFECTS.add(attack.statchanged);
//					switch (attack.statchanged.changedstat) {
//					case ATTACK:
//						target.Attack -= attack.statchanged.quantity;
//						break;
//					case DEFENSE:
//						target.Defense -= attack.statchanged.quantity;
//						break;
//					case SPECIALATTACK:
//						target.SpecialAttack -= attack.statchanged.quantity;
//						break;
//					case SPECIALDEFENSE:
//						target.SpecialDefense -= attack.statchanged.quantity;
//						break;
//					case SPEED:
//						target.Speed -= attack.statchanged.quantity;
//						break;
//					default:
//						break;
//					}
//				}
//				target.HitPoints -= damageDone;
//				if (target.HitPoints <= 0)
//					target.die();
//			}
//			return damageDone;
//		}
//		return -1; // attack missed if here
//	}

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
		System.out.println(damage);
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

	void updatePokemonAfterTurn() {
//		if (this.active) {
//			// Handle DOTS
//			for (Move.DotComponent dot : DOT_EFFECTS) {
//				this.HitPoints-=dot.DamagePerTurn;
//				dot.duration--;
//				if(dot.duration<=0)
//					DOT_EFFECTS.remove(dot);
//			}
			// Handle STAT_CH_EFFECTS
//			for (Move.StatChanging stch : STAT_CH_EFFECTS) {
//				stch.duration--;
//				if(stch.duration<=0){
//					switch(stch.changedstat){
//					case ATTACK:
//						this.Attack+=stch.quantity;
//						STAT_CH_EFFECTS.remove(stch);
//						break;
//					case DEFENSE:
//						this.Defense+=stch.quantity;
//						STAT_CH_EFFECTS.remove(stch);
//						break;
//					case SPECIALATTACK:
//						this.SpecialAttack+=stch.quantity;
//						STAT_CH_EFFECTS.remove(stch);
//						break;
//					case SPECIALDEFENSE:
//						this.SpecialDefense+=stch.quantity;
//						STAT_CH_EFFECTS.remove(stch);
//						break;
//					case SPEED:
//						this.Speed+=stch.quantity;
//						STAT_CH_EFFECTS.remove(stch);
//						break;
//					default:
//						break;
//						
//					}
//				}
//			}
			// Handle
			switch (this.STATUS_EFFECT) {
			case BURN:
				this.HitPoints -= MaxHitPoints / 8;
				if (HitPoints <= 0)
					this.die();
				break;
			// See if freeze remains
			case FREEZE:
				Random stopfreeze = new Random();
				if (stopfreeze.nextInt(5) == 4) {
					this.STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
				}
				break;
			case POISON:
				this.HitPoints -= MaxHitPoints / 16;
				if (HitPoints <= 0)
					this.die();
				break;
			case SLEEP:
				if(sleepcounter<=0)
					this.STATUS_EFFECT = Move.AILMENTS.NEUTRAL;
				else sleepcounter--;
				break;
			default:
				break;

			}
		}
	}
//}
