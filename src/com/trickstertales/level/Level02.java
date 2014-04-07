package com.trickstertales.level;

import com.trickstertales.gamestate.PlayState;

public class Level02 extends Level {
	
	public Level02(PlayState ps, int num) {
		super(ps, num, 24, 18);
	}
	
	public void loadStuff() {
		loadData("levels/Level02.txt");
		loadObjects("levels/Level02_Objects.txt");
	}

}
