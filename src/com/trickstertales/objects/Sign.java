package com.trickstertales.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.level.Level;
import com.trickstertales.math.Maths;
import com.trickstertales.view.WorldRenderer;


public class Sign extends NonCollidable {
	
	LevelObject message = null;
	String msg = "This is\nA sign...";

	public Sign(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		data = "sign";
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
	
	public boolean clicked() {
		if(!canceled)
			return false;
		super.clicked();
		message = new Message(this.level, this, .5 * (gameleftx + gamerightx), gametopy + level.blocksize, msg, 7);
		return false;
	}
	public void canceled() {
		if(canceled)
			return;
		super.canceled();
	}
	
	public void setMessage(String message) {
		if(message == null || message == "") {
			msg = "This is\nA sign";
			return;
		}
		msg = Maths.wordWrap(message, 12);
	}

}
