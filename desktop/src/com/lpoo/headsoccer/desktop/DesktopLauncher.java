package com.lpoo.headsoccer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.lpoo.pokemon.Animator;
import com.lpoo.pokemon.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="PokeArena";
		config.width = 1280;
	    config.height = 702;

	    // fullscreen
	   // config.fullscreen = true;
	    
	    // vSync
	    config.vSyncEnabled = true;
	    
		new LwjglApplication(new MainGame(), config);
	}
}
