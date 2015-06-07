package com.lpoo.headsoccer.desktop;

import com.badlogic.gdx.tools.texturepacker.*;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
public class imagepackage {
	public static void main(String[] args){
	String input = "C:\\fire";
	String output = "C:\\fire";
	String pfn = "fireanimation";
	Settings settings = new TexturePacker.Settings();
	settings.paddingX=0;
	settings.paddingY=0;
	settings.pot =true;
	TexturePacker.processIfModified(settings,input,output,pfn);
}}
