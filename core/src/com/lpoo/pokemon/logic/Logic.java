//package com.lpoo.pokemon.logic;
//
//import java.util.Random;
//import java.util.Scanner;
//import java.util.Vector;
//
//import pokemon.logic.Move;
//import pokemon.logic.Pokemon;
//import pokemon.logic.Trainer;
//import pokemon.logic.Move.ELEMENTS;
//import pokemon.logic.Move.AILMENTS;
//
//public class Logic {
//
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		int command = 0;
//		// char change;
//
//		Pokemon poke1 = new Pokemon("Moltres", ELEMENTS.FIRE, 197, 167, 156,
//				194, 150, 156); // moltres
//		Pokemon poke2 = new Pokemon("Squirtle", ELEMENTS.WATER, 151, 110, 121,
//				112, 127, 104); // squirtle
//		Pokemon poke3 = new Pokemon("Pikachu", ELEMENTS.ELECTRIC, 142, 117, 90,
//				112, 101, 156);
//		Pokemon poke4 = new Pokemon("Articuno", ELEMENTS.ICE, 197, 150, 167,
//				161, 194, 150);
//
//		Move move1 = new Move("Ember", ELEMENTS.FIRE, 40, 25, 1);
//		Move move2 = new Move("FlameThrower", ELEMENTS.FIRE, 90, 15, 1);
//		Move move3 = new Move("Water Gun", ELEMENTS.WATER, 40, 25, 1);
//		Move move4 = new Move("Water Pulse", ELEMENTS.WATER, 60, 20, 1);
//		Move move5 = new Move("Thunder Shock", ELEMENTS.ELECTRIC, 40, 30, 1);
//		Move move6 = new Move("Spark", ELEMENTS.ELECTRIC, 65, 20, 1);
//		Move move7 = new Move("Ice Beam", ELEMENTS.ICE, 90, 10, 1);
//		Move move8 = new Move("Blizzard", ELEMENTS.ICE, 90, 10, 1);
//
//		poke1.addAttack(move1);
//		poke1.addAttack(move2);
//		poke2.addAttack(move3);
//		poke2.addAttack(move4);
//		poke3.addAttack(move5);
//		poke3.addAttack(move6);
//		poke4.addAttack(move7);
//		poke4.addAttack(move8);
//
//		Trainer t1 = new Trainer("Manecas");
//		Trainer t2 = new Trainer("Jaquino");
//
//		t1.addPokemon(poke1);
//		t1.addPokemon(poke2);
//		t2.addPokemon(poke3);
//		t2.addPokemon(poke4);
//
//		// System.out.println(poke1.getStat());
//		// System.out.println(poke2.getStat());
//		// System.out.println(poke3.getStat());
//		// System.out.println(poke1.getHPLeft());
//
//		// t2.getActivePokemon().Attack(t1.getActivePokemon(),
//		// t1.getActivePokemon().getMoves().get(0));
//		//
//		// System.out.println(poke1.getHPLeft());
//
//		// while(poke1.getHPLeft()<=0||poke2.getHPLeft()<=0)
//
//		while (!t1.TrainerLost() || !t2.TrainerLost()) {
//			System.out.println(t1.getActivePokemon().getName() + "     "
//					+ t1.getActivePokemon().getHPLeft());
//			System.out.println(t2.getActivePokemon().getName() + "     "
//					+ t2.getActivePokemon().getHPLeft());
//			System.out.println();
//
//			System.out.println("Chose your attack " + t1.getName() + "!!!!");
//			t1.getActivePokemon().displayMoves();
//			command = sc.nextInt();
//			while (command != 1 && command != 2 && command != 3 && command != 4) {
//				command = sc.nextInt();
//			}
//			t1.getActivePokemon().Attack(t2.getActivePokemon(),
//					t1.getActivePokemon().getMoves().get(command - 1));
//			System.out.println("----------------\n");
//			System.out.println("Chose your attack " + t2.getName() + "!!!!");
//			t2.getActivePokemon().displayMoves();
//			command = sc.nextInt();
//			while (command != 1 && command != 2 && command != 3 && command != 4) {
//				command = sc.nextInt();
//			}
//			t2.getActivePokemon().Attack(t1.getActivePokemon(),
//					t2.getActivePokemon().getMoves().get(command - 1));
//			System.out.println("\n" + t1.getName()
//					+ " do you want to switch pokemons? (y/n)");
//			char change = sc.next().charAt(0);
//			if (change != 'y' && change != 'n') {
//				change = sc.next().charAt(0);
//			}
//			if (change == 'y')
//				t1.changePokemon();
//			System.out.println("What about you " + t2.getName() + "? (y/n)");
//			change = sc.next().charAt(0);
//			if (change != 'y' && change != 'n') {
//				change = sc.next().charAt(0);
//			}
//			if (change == 'y')
//				t2.changePokemon();
//			System.out
//					.println("\n________________________________________________________________\n");
//			if (t1.getActivePokemon().isDead()) {
//				System.out.println(t1.getActivePokemon() + " has fainted.");
//				t1.changePokemon();
//			} else if (t2.getActivePokemon().isDead()) {
//				System.out.println(t2.getActivePokemon() + " has fainted."); 
//				t2.changePokemon();
//			} else if (t1.TrainerLost()) {
//				System.out.println(t1.getActivePokemon() + " has fainted. "
//						+ t1.getName() + " is out of usable pokemons. "
//						+ t2.getName() + " wins the match!");
//				return;
//			} else if (t2.TrainerLost()) {
//				System.out.println(t2.getActivePokemon() + " has fainted. "
//						+ t2.getName() + " is out of usable pokemons. "
//						+ t1.getName() + " wins the match!");
//				return;
//			}
//		}
//	}
//}
