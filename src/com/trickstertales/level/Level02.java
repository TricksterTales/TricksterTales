package com.trickstertales.level;

import com.trickstertales.gamestate.PlayState;
import com.trickstertales.math.Constant;

public class Level02 extends Level {
	
	public Level02(PlayState ps, int num) {
		super(ps, num, 50, Constant.LEVEL_HEIGHT, Constant.BLOCK_SIZE);
	}
	
	public void loadStuff() {
		loadData("levels/Level02.txt");
		loadObjects("levels/Level02_Objects.txt");
	}

}
