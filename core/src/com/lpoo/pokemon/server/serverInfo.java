package com.lpoo.pokemon.server;

public class serverInfo {
	int index;
	private boolean nrCl;
	private boolean connected;
	
	public serverInfo() {
		nrCl=false;
		connected =false;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int i){
		index=i;
	}

	public boolean getNrCl() {
		return nrCl;
	}

	public void setNrCl(Boolean nrCl) {
		this.nrCl = nrCl;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
