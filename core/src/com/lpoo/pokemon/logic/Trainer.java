package com.lpoo.pokemon.logic;

import java.util.Vector;

public class Trainer {
	Vector<Pokemon> team=new Vector<Pokemon>();
	String Name;
	
	public Trainer(String nome) {
		Name = nome;
	}
	
	public String getName(){
		return Name;
	}
	
	public void setName(String name){
		Name=name;
	}
	
	public void addPokemon(Pokemon poke){
		if(team.size()==2)
			return;
		
		if(team.isEmpty())
			poke.changeStat(true);
		else
			poke.changeStat(false);
		team.addElement(poke);
	}
	
	public Pokemon getActivePokemon(){
		for(int i=0;i<team.size();i++)
			if(team.get(i).getStat())
				return team.get(i);
		
		return null;
	}
	
	boolean TrainerLost(){
		if(team.get(0).isDead()&&team.get(1).isDead()){
			return true;
		}
		return false;
	}

	void changePokemon(){
		if(team.get(0).getStat()){
			team.get(1).changeStat(true);
			team.get(0).changeStat(false);
		}
		else{
			team.get(0).changeStat(true);
			team.get(1).changeStat(false);
		}
	}
}
