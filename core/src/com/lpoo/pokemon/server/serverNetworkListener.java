package com.lpoo.pokemon.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.lpoo.networking.Packets.Packets01Attack;
import com.lpoo.networking.Packets.Packets02VectorStrings;
import com.lpoo.pokemon.logic.Trainer;

public class serverNetworkListener extends Listener {
	String attack1;
	String attack2;
	String attack3;
	String attack4;
	String trainer1;
	String trainer2;
	serverInfo info;


	public serverNetworkListener(Trainer t1, Trainer t2) {
		attack1= t1.getActivePokemon().getMoves().get(0).getName();
		attack2= t1.getActivePokemon().getMoves().get(1).getName();
		attack3= t2.getActivePokemon().getMoves().get(0).getName();
		attack4= t2.getActivePokemon().getMoves().get(1).getName();
		info = new serverInfo();
	}
	
	public void connected(Connection c){
		System.out.println("Someone has connected");
	}
	
	public void disconnected(Connection c){
		System.out.println("Someone has disconnected");
	}
	
	public void received(Connection c, Object o){
		if(o instanceof Packets01Attack){
			int mes = ((Packets01Attack)o).ind;
			
			if(mes==0){
				Packets02VectorStrings answer= new Packets02VectorStrings();
				answer.setAttack1(attack1);
				answer.setAttack2(attack2);
				c.sendTCP(answer);
			}
			
			System.out.println("[CLIENT] >> "+mes);
			info.setIndex(mes);
			info.setNrCl(true);
			info.setConnected(true);
			System.out.println(info.isConnected());
			System.out.println("nrCl: "+info.getNrCl());
			
//			Packets01Attack resp = new Packets01Attack();
//			resp.ind="message received!";
//			c.sendTCP(resp);
		}
	}
	public serverInfo getInfo(){
		return info;
	}
}
