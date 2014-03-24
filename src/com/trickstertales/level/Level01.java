package com.trickstertales.level;

import com.trickstertales.math.Constant;

public class Level01 extends Level {
	
	public Level01() {
		super(50, Constant.LEVEL_HEIGHT, Constant.BLOCK_SIZE);
		
		loadData("levels/Level01.txt");
	}

}
