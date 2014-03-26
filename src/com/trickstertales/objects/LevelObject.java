package com.trickstertales.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.level.Level;
import com.trickstertales.math.Tween;
import com.trickstertales.view.WorldRenderer;


public class LevelObject {
	
	public static final int SIDE_LEFT = 1, SIDE_RIGHT = 2, SIDE_TOP = 3, SIDE_BOTTOM = 4;
	
	protected Level level;
	protected boolean paused = false;
	public double gameleftx, gamerightx, gametopy, gamebottomy;
	public double animstartx, animstarty, animleftx, animbottomy;
	public boolean isAnimating = false;
	public double animationTimer = 0.0, animationDuration = 0.0, animationDelay = 0.0;
	protected boolean isOffScreen = false, shouldDie = false;
	protected boolean canceled = true;
	protected int _id = -1;
	
	public LevelObject(double x, double y, double width, double height, Level level) {
		animleftx = gameleftx = x;
		gamerightx = gameleftx + width;
		animbottomy = gamebottomy = y;
		gametopy = gamebottomy + height;
		this.level = level;
		isAnimating = false;
	}
	
	public void setAnimation(double animstartx, double animstarty, double durationPerPixel) {
		this.animstartx = animstartx;
		this.animstarty = animstarty;
		animleftx = animstartx;
		animbottomy = animstarty;
		isAnimating = true;
		animationTimer = 0.0;
		animationDuration = durationPerPixel * Math.sqrt(Math.pow(animstartx - gameleftx, 2) + Math.pow(animstarty - gamebottomy, 2));
	}
	public void setAbsoluteAnimation(double animstartx, double animstarty, double duration) {
		this.animstartx = animstartx;
		this.animstarty = animstarty;
		animleftx = animstartx;
		animbottomy = animstarty;
		isAnimating = true;
		animationTimer = 0.0;
		animationDuration = duration;
	}
	
	public void setId(int id) { _id = id; }
	public int getId() { return _id; }
	
	public boolean clicked() {
		if(!canceled)
			return false;
		canceled = false;
		return false;
	}
	public void canceled() {
		if(canceled)
			return;
		canceled = true;
	}
	public boolean isCanceled() { return canceled; }
	
	public void delayAnimation(double delay) {
		animationDelay = (delay < 0)?0:delay;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void update(double dt) {
		if(paused)
			return;
		//Overwrite here...
	}
	
	public void animate(double dt) {
		if(paused)
			return;
		if(isOffScreen) {
			isAnimating = false;
		}
		if(!isAnimating) {
			animleftx = gameleftx;
			animbottomy = gamebottomy;
			return;
		}
		if(animationDelay > 0) {
			animationDelay -= dt;
			animationDelay = Math.max(0, animationDelay);
			return;
		}
		animationTimer += dt;
		if(animationTimer >= animationDuration) {
			animationTimer = animationDuration;
			animleftx = gameleftx;
			animbottomy = gamebottomy;
			isAnimating = false;
			return;
		}
		animleftx = Tween.Back.easeOut(animationTimer, animstartx, gameleftx-animstartx, animationDuration);
		animbottomy = Tween.Back.easeOut(animationTimer, animstarty, gamebottomy-animstarty, animationDuration);
	}
	
	public boolean doneAnimating() { return !isAnimating; }
	
	public void draw(WorldRenderer render, boolean debug) {
		if(isOffScreen)
			return;
		if(isAnimating) {
			drawSelfAt(render, animleftx, animbottomy, debug);
		} else {
			drawSelfAt(render, gameleftx, gamebottomy, debug);
		}
	}
	public void drawSelfAt(WorldRenderer render, double lx, double by, boolean debug) {
		//Always overwrite
		Art.drawImage(render.spriteBatch, Art.Blank, lx, by, gamerightx - gameleftx, gametopy - gamebottomy);
		if(debug) {
			(render.spriteBatch).end();
			(render.debugRenderer).begin(ShapeType.Line);
			(render.debugRenderer).setColor(Art.COLOR_DEBUG);
			(render.debugRenderer).rect((float)lx, (float)by, (float)(gamerightx - gameleftx), (float)(gametopy - gamebottomy));
			(render.debugRenderer).end();
			(render.spriteBatch).begin();
		}
	}
	
	public void checkOffScreen() {
		//Should overwrite for some objects...
		isOffScreen = isOffScreen();
	}
	
	public boolean isOffScreen(double border) {
		double xmin = -border, xmax = WorldRenderer.VIRTUAL_WIDTH+border;
		double ymin = -border, ymax = WorldRenderer.VIRTUAL_HEIGHT+border;
		if(gamerightx < xmin || gameleftx > xmax)
			return true;
		if(gametopy < ymin || gamebottomy > ymax)
			return true;
		return false;
	}
	public boolean isOffScreen() {
		return isOffScreen(0);
	}
	public boolean shouldBeRemoved() { return shouldDie; }
	
	public void setPaused(boolean p) {
		this.paused = p;
	}
	
	public double checkCollision(double lx, double rx, double by, double ty, int side) throws NoCollisionException {
		if(side < SIDE_LEFT || side > SIDE_BOTTOM)
			throw new NoCollisionException();
		boolean wholeSide = false;
		switch(side) {
		case SIDE_LEFT:
			wholeSide = (by <= gamebottomy && ty >= gametopy);
			if(wholeSide) {
				return gameleftx;
			} else {
				if(ty <= gamebottomy || by >= gametopy)
					throw new NoCollisionException();
				//overwrite this
				return gameleftx;
			}
		case SIDE_RIGHT:
			wholeSide = (by <= gamebottomy && ty >= gametopy);
			if(wholeSide) {
				return gamerightx;
			} else {
				if(ty <= gamebottomy || by >= gametopy)
					throw new NoCollisionException();
				//overwrite this
				return gamerightx;
			}
		case SIDE_TOP:
			wholeSide = (lx <= gameleftx && rx >= gamerightx);
			if(wholeSide) {
				return gametopy;
			} else {
				if(rx <= gameleftx || lx >= gamerightx)
					throw new NoCollisionException();
				//overwrite this
				return gametopy;
			}
		case SIDE_BOTTOM:
			wholeSide = (lx <= gameleftx && rx >= gamerightx);
			if(wholeSide) {
				return gamebottomy;
			} else {
				if(rx <= gameleftx || lx >= gamerightx)
					throw new NoCollisionException();
				//overwrite this
				return gamebottomy;
			}
		default:
			throw new NoCollisionException();
		}
	}
	
	public boolean isCollidingWith(double lx, double rx, double by, double ty) {
		//overwrite for all non-rectangular objects
		if(rx <= gameleftx || lx >= gamerightx)
			return false;
		if(ty <= gamebottomy || by >= gametopy)
			return false;
		return true;
	}
	
	public double slopeAdjust(double lx, double rx, double by, double ty, int side, double max) throws SlopeAdjustFailed {
		//overwrite for all non-rectangular objects
		if(!isCollidingWith(lx,rx,by,ty))
			return 0;
		
		if(side != SIDE_LEFT && side != SIDE_RIGHT)
			return 0;
		
		//for slopes have a left and right side function, but for rectangles they're the same formula
		double up,down;
		up = gametopy - by;
		down = gamebottomy - ty;
		if(Math.abs(up) > max) {
			if(Math.abs(down) > max)
				throw new SlopeAdjustFailed();
			else
				return down;
		} else {
			if(Math.abs(down) > max)
				return up;
			else
				return (Math.abs(down)<Math.abs(up))?down:up;
		}
	}

}
