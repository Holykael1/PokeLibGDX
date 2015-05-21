package com.lpoo.headsoccer.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.lpoo.pokemon.Animator;
import com.lpoo.pokemon.MainGame;
import com.lpoo.pokemon.screen.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="PokeArena";
		config.width = 1366;
	    config.height = 762;

	    // fullscreen
	   // config.fullscreen = true;
	    
	    // vSync
	    config.vSyncEnabled = true;
	    
		new LwjglApplication(new MainGame(), config);
	}
}
