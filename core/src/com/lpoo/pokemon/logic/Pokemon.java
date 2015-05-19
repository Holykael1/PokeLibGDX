package com.lpoo.pokemon.logic;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import com.badlogic.gdx.graphics.Texture;
import com.lpoo.pokemon.logic.Move.AILMENTS;
import com.lpoo.pokemon.logic.Move.ELEMENTS;

public class Pokemon extends PokemonBase {

	Vector<Move.DotComponent> DOT_EFFECTS;
	AILMENTS status;

	void die() {
		dead = true;
	}

	boolean isDead() {
		return dead;
	}

	void changeStat(boolean stat) {
		active = stat;
	}

	String getName() {
		return Name;
	}

	boolean getStat() {
		return active;
	}

	public Pokemon(Texture texture, String nome, ELEMENTS element, double HP, double att,
			double def, double SPA, double SPD, double ag) {
		this.texture=texture;
		Name = nome;
		ElementType = element;
		MaxHitPoints = HP;
		HitPoints = HP;
		Attack = att;
		Defense = def;
		SpecialAttack = SPA;
		SpecialDefense = SPD;
		Agility = ag;
	}

	public double getHPLeft() {
		return HitPoints;
	}
	
	public Texture getTexture(){
		return texture;
	}

	void displayMoves() {
		int n = 1;
		for (int i = 0; i < moves.size(); i++) {
			System.out.println(n + "- " + moves.get(i).getName());
			n++;
		}
	}

	void addAttack(Move move) {
		moves.add(move);
	}

	public Vector<Move> getMoves() {
		return moves;
	}

	public double Attack(Pokemon target, Move attack) {
		double damageDone = 0;
		Random r = new Random();
		int miss = r.nextInt(100) + 1;
		if (attack.Accuracy < miss) {
			switch (attack.ElementType) {
			case HEAL:
				HitPoints += (Attack * attack.BaseDamage);
				break;
			default:
				damageDone = CalculateAttackDamage(target, attack);
				target.HitPoints -= damageDone;
				if (target.HitPoints <= 0)
					target.die();
			}
			return damageDone;
		}
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
					* modifier*6.7;
		else
			damage = (((2 * 50 + 10) / 250)
					* (SpecialAttack / target.SpecialDefense)
					* attack2.BaseDamage + 2)
					* modifier*6.75;
		return damage;
	}

	double CalculateEffectiveness(Pokemon target, Move attack) {
		switch (attack.ElementType) {
		case FIRE:
			if (target.ElementType == ElementType.ICE) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.FIRE){
				System.out.println(Name + " used " + attack.getName()+".");
				return 0.5;
			}
			else if (target.ElementType == ElementType.WATER){
				System.out.println(Name + " used " + attack.getName()+".");
				return 0.5;
			}
			else if (target.ElementType == ElementType.ROCK){
				System.out.println(Name + " used " + attack.getName()+".");
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
			} else if (target.ElementType == ElementType.WATER){
				System.out.println(Name + " used " + attack.getName()+".");
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
			} else if (target.ElementType == ElementType.ELECTRIC){
				System.out.println(Name + " used " + attack.getName()+".");
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
			} else if (target.ElementType == ElementType.ROCK){
				System.out.println(Name + " used " + attack.getName()+".");
				return 0.5;
			}
			break;
		case PHYSICAL:
			if (target.ElementType == ElementType.ROCK){
				System.out.println(Name + " used " + attack.getName()+".");			
				return 0.5;
			}
			break;
		case ICE:
			if (target.ElementType == ElementType.FLYING) {
				System.out.println(Name + " used " + attack.getName()
						+ ". It's super effective !!!!");
				return 2;
			} else if (target.ElementType == ElementType.WATER){
				System.out.println(Name + " used " + attack.getName()+".");
				return 0.5;
			}
			else if (target.ElementType == ElementType.ICE){
				System.out.println(Name + " used " + attack.getName()+".");
				return 0.5;
			}
			break;
		case FLYING:
			if (target.ElementType == ElementType.ROCK){
				System.out.println(Name + " used " + attack.getName()+".");
				return 0.5;
			}
			else if (target.ElementType == ElementType.ELECTRIC){
				System.out.println(Name + " used " + attack.getName()+".");
				return 0.5;
			}
			break;
		}
		System.out.println(Name + " used " + attack.getName()+".");
		return 1;
	}

}
