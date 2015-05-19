package com.lpoo.pokemon.logic;

import java.util.Vector;

import com.badlogic.gdx.graphics.Texture;
import com.lpoo.pokemon.logic.Move.ELEMENTS;

public abstract class PokemonBase {
    String Name;
    ELEMENTS ElementType;
    Vector<Move> moves=new Vector<Move>();
    Texture texture;
    
    // Stats
    double MaxHitPoints;
    double HitPoints;
    double Attack;
    double Defense;
    double SpecialAttack;
    double SpecialDefense;
    double Agility;
    
    boolean dead;
    boolean active;

}
