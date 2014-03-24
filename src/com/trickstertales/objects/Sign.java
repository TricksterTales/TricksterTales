package com.trickstertales.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;


public class Sign extends LevelObject {
	
	LevelObject message = null;

	public Sign(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
	}
	
	public void drawSelfAt(WorldRenderer render, double x, double y, boolean debug) {
		Art.drawImage(render.spriteBatch, Art.SIGN, x, y, gamerightx - gameleftx, gametopy - gamebottomy);
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
	
	public boolean clicked() {
		if(!canceled)
			return false;
		super.clicked();
		message = new Message(this.level, this, .5 * (gameleftx + gamerightx), gametopy + level.blocksize, "This is\nA sign...", 7);
		return false;
	}
	public void canceled() {
		if(canceled)
			return;
		super.canceled();
	}

}
