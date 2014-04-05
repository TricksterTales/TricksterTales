package com.trickstertales.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;


public class NonCollidable extends LevelObject {

	public NonCollidable(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		data = "nonCollidable";
	}
	
	public void drawSelfAt(WorldRenderer render, double x, double y, boolean debug) {
		Art.drawImage(render.spriteBatch, Art.Blank, x, y, gamerightx - gameleftx, gametopy - gamebottomy);
		if(debug) {
			(render.spriteBatch).end();
			(render.debugRenderer).begin(ShapeType.Line);
			(render.debugRenderer).setColor(Art.COLOR_DEBUG);
			(render.debugRenderer).rect((float)x, (float)y, (float)(gamerightx - gameleftx), (float)(gametopy - gamebottomy));
			(render.debugRenderer).end();
			(render.spriteBatch).begin();
		}
	}
	
	public double checkCollision(double lx, double rx, double by, double ty, int side) throws NoCollisionException {
		throw new NoCollisionException();
	}

	public double slopeAdjust(double lx, double rx, double by, double ty, int side, double max) throws SlopeAdjustFailed {
		return 0;
	}

}
