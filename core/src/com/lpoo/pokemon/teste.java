package com.lpoo.pokemon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;

public class teste extends ApplicationAdapter implements TextInputListener {

	String text;
	
	@Override
	public void create(){
		
	}
	
	@Override
	public void render(){
		if(Gdx.input.justTouched())
			Gdx.input.getTextInput(this, "Title", "default", "name");
	}
	
	@Override
	public void canceled() {
		// TODO Auto-generated method stub

	}

	@Override
	public void input(String arg0) {
		text=arg0;

	}

}
