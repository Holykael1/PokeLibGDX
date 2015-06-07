package com.lpoo.pokemon.server;

import java.io.IOException; 

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.lpoo.pokemon.server.Packets.Packets02VectorStrings;
import com.lpoo.pokemon.server.Packets.Packets01Attack;
import com.lpoo.pokemon.logic.Trainer;

public class MPServer {
	Server server;
	serverInfo info;

	// String IPConnection = "localhost";
	int TCPPort = 54555;
	int UDPPort = 64777;
	serverNetworkListener snl;

	public MPServer(Trainer t1, Trainer t2) {
		server = new Server();
		snl = new serverNetworkListener(t1,t2); 

		registerPackets();
		
		server.addListener(snl);

		try {
			server.bind(TCPPort);
		} catch (IOException e) {
			e.printStackTrace();
		}

		server.start();
		info = snl.getInfo();
	}

	private void registerPackets() {
		Kryo kryo = server.getKryo();
		kryo.register(Packets01Attack.class);
		kryo.register(Packets02VectorStrings.class); 
		kryo.register(MPServer.class, new JavaSerializer());
	}

	public serverInfo getInfo(){
		return info;
	}

}
