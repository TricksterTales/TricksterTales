package com.trickstertales.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;



public class Constant {
	
	public static final boolean IS_PC = !(Gdx.app.getType().equals(ApplicationType.iOS) ||
			Gdx.app.getType().equals(ApplicationType.Android));
	public static final String LOCAL_DIR = Gdx.files.getLocalStoragePath();
	
	public static final double APPLET_SCALE = 1.0;
	public static final int APPLET_WIDTH = 768;
	public static final int APPLET_HEIGHT = 480;
	
	public static final double BLOCKTWEEN = 2.25;
	public static final double TWEENDURATIONPERPIXEL = .0075;
	public static final double PLAYERTWEEN = 4.0;
	public static final double MINTWEEN = 12.5;
	public static final double INRANGE = .5;
	public static final double TWEENDELAYMIN = 0;
	public static final double TWEENDELAYMAX = 3;
	public static final double PLAYERTWEENDELAY = TWEENDELAYMAX + 1 + APPLET_WIDTH * TWEENDURATIONPERPIXEL;
	
	public static final int BLOCK_SIZE = 32;
	
	public static final int LEVEL_FRAMEDELAY = 3;
	public static final int LEVEL_HEIGHT = (int)Math.ceil(APPLET_HEIGHT / BLOCK_SIZE);
	public static final int LEVEL_VIEWW = APPLET_WIDTH;
	public static final int LEVEL_VIEWH = APPLET_HEIGHT;
	public static final int LEVEL_BUFFX = (int)(LEVEL_VIEWW / 3);
	public static final int LEVEL_BUFFY = (int)(LEVEL_VIEWH / 3);
	
	public static final double VIEW_TWEEN = 2;
	public static final double VIEW_TWEENMIN = 0;
	
	public static final double PLAYER_STARTX = 4 * BLOCK_SIZE;
	public static final double PLAYER_STARTY = 6 * BLOCK_SIZE;
	public static final double PLAYER_SPAWNX = -100;
	public static final double PLAYER_SPAWNY = APPLET_HEIGHT+100;
	public static final double PLAYER_WIDTH = 24;
	public static final double PLAYER_HEIGHT = 60;
	public static final double PLAYER_HEIGHTDUCK = 30;
	public static final double PLAYER_WALKSPEED = 4 * BLOCK_SIZE;
	public static final double PLAYER_CROUCHRATIO = 0.5;
	public static final double PLAYER_GRAV = 11 * BLOCK_SIZE;
	public static final double PLAYER_JUMPAMOUNT = 7.5 * BLOCK_SIZE;
	public static final double PLAYER_MAXXSPEED = PLAYER_WALKSPEED;
	public static final double PLAYER_MAXYSPEED = 8 * BLOCK_SIZE;
	public static final double PLAYER_MAXYADJUST = 0.50001 * BLOCK_SIZE;
	public static final double PLAYER_WALKFRAMESPEED = 0.15;
	public static final double PLAYER_FALLTIME = 1.5;
	public static final double PLAYER_TOOFAR = 20 * BLOCK_SIZE;
	public static final double PLAYER_AWAYTIME = 3.0;
	public static final double PLAYER_AWAYDIST = 7 * BLOCK_SIZE;
	
	public static final double CLOUD_SCALER = 100;
	public static final double CLOUD_WIDTHMIN = 100;
	public static final double CLOUD_WIDTHMAX = 300;
	public static final double CLOUD_HEIGHTMIN = 50;
	public static final double CLOUD_HEIGHTMAX = 150;
	public static final double CLOUD_LAYERING = 0.2;
	public static final double CLOUD_MINSPEED = 20;
	
	public static final double BLINK_TIME = 0.5;
	
	public static final String SAVE_AUTO = "saves/auto.sav";

}
