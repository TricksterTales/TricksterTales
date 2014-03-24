package com.trickstertales.objects;


import com.trickstertales.drawing.Art;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;


public class Message extends LevelObject {
	
	private String msg;
	private LevelObject parent = null;
	private double size = Art.FONT.charWidth;

	public Message(Level level, LevelObject par, double x, double y, String message, double size) {
		super(x, y, 0, 0, level);
		level.addTop(this);
		this.size = size;
		this.msg = message;
		this.parent = par;
	}
	public Message(Level level, LevelObject par, double x, double y, String message) {
		this(level,par,x,y,message,Art.FONT.charWidth);
	}
	
	public void update(double dt) {
		if(paused)
			return;
		if(parent == null)
			shouldDie = true;
		if(parent.isCanceled())
			shouldDie = true;
	}
	
	public void drawSelfAt(WorldRenderer render, double x, double y, boolean debug) {
		double s = Art.FONT.charWidth;
		(Art.FONT).setSize(size);
		Art.drawBubble(render, Art.FONT, x, y, msg);
		(Art.FONT).setSize(s);
	}
	
	public double checkCollision(double lx, double rx, double by, double ty, int side) throws NoCollisionException {
		throw new NoCollisionException();
	}

	public double slopeAdjust(double lx, double rx, double by, double ty, int side, double max) throws SlopeAdjustFailed {
		return 0;
	}

}
