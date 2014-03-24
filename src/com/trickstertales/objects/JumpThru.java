package com.trickstertales.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;


public class JumpThru extends LevelObject {

	public JumpThru(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
	}
	
	public void drawSelfAt(WorldRenderer render, double x, double y, boolean debug) {
		Art.drawImage(render.spriteBatch, Art.JUMPTHRU, x, y, (gamerightx-gameleftx), (gametopy - gamebottomy));
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
		if(side < SIDE_LEFT || side > SIDE_BOTTOM)
			throw new NoCollisionException();
		boolean wholeSide = false;
		switch(side) {
		case SIDE_TOP:
			wholeSide = (lx <= gameleftx && rx >= gamerightx);
			if(wholeSide) {
				return gametopy;
			} else {
				if(rx <= gameleftx || lx >= gamerightx)
					throw new NoCollisionException();
				//overwrite this
				if(by < gametopy)
					throw new NoCollisionException();
				return gametopy;
			}
		default:
			throw new NoCollisionException();
		}
	}
	
	public double slopeAdjust(double lx, double rx, double by, double ty, int side, double max) throws SlopeAdjustFailed {
		return 0;
	}

}
