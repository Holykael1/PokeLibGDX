package com.lpoo.pokemon.logic;

import java.util.Vector;

public class Trainer {
	//Fields
	Vector<Pokemon> team;
	String Name;
	
	//Constructors
	/**
	 * trainer constructor with a vector of pokemons already atributed to
	 * @param nome Trainer name
	 * @param pk Trainer pokemons
	 */
	public Trainer(String nome, Vector<Pokemon> pk) {
		Name = nome;
		team=pk;
	}
	
	/**
	 * Trainer constructor with only his name
	 * @param nome Trainer name
	 */
	public Trainer(String nome) {
		Name = nome;
		team=new Vector<Pokemon>();
	}
	
	//Getters
	/**
	 * gets Trainer's active pokemon
	 * @return active pokemon
	 */
	public Pokemon getActivePokemon(){ 
		for(int i=0;i<team.size();i++)
			if(team.get(i).getStat())
				return team.get(i);
		
		return null;
	}
	
	/**
	 * gets trainer's benched pokemon
	 * @return benched pokemon
	 */
	public Pokemon getBenchedPokemon(){ 
		for(int i=0;i<team.size();i++)
			if(!team.get(i).getStat())
				return team.get(i);
		
		return null;
	}
	
	/**
	 * gets trainer's name
	 * @return trainer's name
	 */
	public String getName(){
		return Name;
	}
	
	/**
	 * give the trainer a new name
	 * @param name new Trainer name
	 */
	public void setName(String name){
		Name=name;
	}
	
	//Modifiers
	/**
	 * gives trainer a new pokemon
	 * @param poke new pokemon
	 */
	public void addPokemon(Pokemon poke){
		if(team.size()==2)
			return;
		
		if(team.isEmpty())
			poke.changeStat(true);
		else
			poke.changeStat(false);
		team.addElement(poke);
	}
	
	/**
	 * changes active pokemon to the benched one
	 */
	public void changePokemon(){ 
		if(team.get(0).getStat()){
			team.get(1).changeStat(true);
			team.get(0).changeStat(false);
		}
		else{
			team.get(0).changeStat(true);
			team.get(1).changeStat(false);
		}
	}
	
	/**
	 * gets trainer's pokemon team
	 * @return trainer's pokemons vector
	 */
	public Vector<Pokemon> getTeam(){
		return team;
	}
	//Checks
	/**
	 * checks if trainer lost
	 * @return true if both pokemons dead, false otherwise
	 */
	public boolean TrainerLost(){ 
		if(team.get(0).isDead()&&team.get(1).isDead()){
			return true;
		}
		return false;
	}

}
