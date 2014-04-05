package com.trickstertales.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;


public class Ground extends LevelObject {

	public Ground(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		data = "ground";
	}
	
	public void drawSelfAt(WorldRenderer render, double x, double y, boolean debug) {
		Art.drawImage(render.spriteBatch, Art.GROUND, x, y, gamerightx - gameleftx, gametopy - gamebottomy);
		if(debug) {
			(render.spriteBatch).end();
			(render.debugRenderer).begin(ShapeType.Line);
			(render.debugRenderer).setColor(Art.COLOR_DEBUG);
			(render.debugRenderer).rect((float)x, (float)y, (float)(gamerightx - gameleftx), (float)(gametopy - gamebottomy));
			(render.debugRenderer).end();
			(render.spriteBatch).begin();
		}
	}

}
