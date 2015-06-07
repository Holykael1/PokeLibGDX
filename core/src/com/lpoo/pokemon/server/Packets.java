package com.lpoo.pokemon.server;

public class Packets {

	public static class Packets01Attack { int ind;}
	
	public static class Packets02VectorStrings{
	private String Attack1; private String Attack2;
	
	public String getAttack1() {
		return Attack1;
	}
	public void setAttack1(String Attack1) {
		this.Attack1 = Attack1;
	}
	public String getAttack2() {
		return Attack2;
	}
	public void setAttack2(String Attack2) {
		this.Attack2 = Attack2;
	}}
}
