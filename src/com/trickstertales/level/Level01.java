package com.trickstertales.level;

import com.trickstertales.gamestate.PlayState;

public class Level01 extends Level {
	
	public Level01(PlayState ps, int num) {
		super(ps, num, 50, 15);
	}
	
	public void loadStuff() {
		loadData("levels/Level01.txt");
		loadObjects("levels/Level01_Objects.txt");
	}

}
