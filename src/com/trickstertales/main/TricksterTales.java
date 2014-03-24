package com.trickstertales.main;


import com.badlogic.gdx.Game;
import com.trickstertales.screens.GameScreen;

public class TricksterTales extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}

}
