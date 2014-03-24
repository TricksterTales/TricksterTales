package com.trickstertales.objects;

import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.level.Level;
import com.trickstertales.math.Constant;

public class Walkable extends LevelObject {
	
	protected boolean facingRight = true;
	protected double runTimer = 0;
	
	protected double leftside,rightside,topside,bottomside;
	protected boolean hasleftside,hasrightside,hastopside,hasbottomside;
	
	protected boolean jump = false, fall = false;
	protected final boolean defcan = true;
	protected boolean canJump = defcan, canFall = defcan;
	protected boolean onGround = false;
	public double xspeed = 0, maxxspeed = Constant.PLAYER_MAXXSPEED;
	public double yspeed = 0, maxyspeed = Constant.PLAYER_MAXYSPEED;
	protected double jumpSpeed = Constant.PLAYER_JUMPAMOUNT;
	
	protected boolean slopeWalk = true; 
	protected double maxYAdjust = Constant.PLAYER_MAXYADJUST;
	protected double fallDist = 2;
	
	public Walkable(double x, double y, double width, double height, Level level) {
		super(x,y,width,height,level);

		hasleftside = hasrightside = hastopside = hasbottomside = false;
	}
	public void reset(double xpos, double ypos, double w, double h) {
		gameleftx = xpos;
		gamerightx = gameleftx + w;
		gametopy = ypos;
		gamebottomy = gametopy - h;
		xspeed = 0;
		yspeed = 0;
		onGround = false;
		jump = fall = false;
		canJump = canFall = defcan;
		facingRight = true;
		hasleftside = hasrightside = hastopside = hasbottomside = false;
	}
	public void setJump(boolean b) { jump = b; }
	public void setFall(boolean b) { fall = b; }
	public boolean getJump() { return jump; }
	public boolean getFall() { return fall; }
	public void setCanJump(boolean b) { canJump = b; }
	public void setCanFall(boolean b) { canFall = b; }
	public boolean getCanJump() { return canJump; }
	public boolean getCanFall() { return canFall; }
	public void setSlopeWalking(boolean slopeWalk) { this.slopeWalk = slopeWalk; }
	public boolean canSlopeWalk() { return this.slopeWalk; }
	
	private void adjustSlopePosition(double difx, double dx, double xspeedgoal) {
		if(onGround == false) {
			runTimer = 0;
			return;
		}
		if(difx == dx)
			return;
		double lx = gameleftx, rx = gamerightx, ty = gametopy, by = gamebottomy, xspd = xspeed;
		gameleftx -= difx; gamerightx -= difx;
		gameleftx += dx; gamerightx += dx;
		xspeed = xspeedgoal;
		double toEdge = gameleftx - level.leftBound();
		if(toEdge < 0) {
			gameleftx -= toEdge; gamerightx -= toEdge;
		}
		try {
			level.adjustPosition(this);
		} catch (SlopeAdjustFailed e) {
			gameleftx = lx;
			gamerightx = rx;
			gametopy = ty;
			gamebottomy = by;
			xspeed = xspd;
			runTimer = 0;
		}
	}
	
	public void update(double dt) {
		if(paused)
			return;
		if(level == null)
			return;
		double dx,dy;
		double w = gamerightx - gameleftx, h = gametopy - gamebottomy;
		if(canJump == true && jump == true) {
			canJump = false;
			jump = false;
			yspeed = jumpSpeed;
			canFall = true;
		} else {
			canJump = false;
			jump = false;
		}
		if(canFall) {
			yspeed -= dt * Constant.PLAYER_GRAV;
		}
		dy = yspeed * dt;
		
		level.collideWithTilesVertical(this);
		
		if(dy > 0) {
			gametopy += dy;
			gamebottomy += dy;
			if(hastopside == true) {
				if(gametopy >= topside) {
					yspeed = 0;
				}
				gametopy = Math.min(gametopy, topside);
				gamebottomy = gametopy - h;
			}
			onGround = false;
			canFall = true;
			canJump = false;
		} else {
			gametopy += dy;
			gamebottomy += dy;
			if(hasbottomside == true) {
				if(gamebottomy <= bottomside) {
					yspeed = 0;
					onGround = true;
					canFall = false;
					canJump = true;
				} else {
					onGround = false;
					canFall = true;
					canJump = false;
				}
				gamebottomy = Math.max(gamebottomy, bottomside);
				gametopy = gamebottomy + h;
			} else {
				onGround = false;
				canFall = true;
				canJump = false;
			}
		}
		
		level.collideWithTilesHorizontal(this);
		
		dx = xspeed * dt;
		
		double difx = gameleftx;
		double xspeedgoal = xspeed;
		
		if(dx < 0) {
			gameleftx += dx;
			gamerightx += dx;
			if(hasleftside == true) {
				if(gameleftx <= leftside) {
					xspeed = 0;
				}
				gameleftx = Math.max(gameleftx, leftside);
				gamerightx = gameleftx + w;
			}
		} else {
			gameleftx += dx;
			gamerightx += dx;
			if(hasrightside == true) {
				if(gamerightx >= rightside) {
					xspeed = 0;
				}
				gamerightx = Math.min(gamerightx, rightside);
				gameleftx = gamerightx - w;
			}
			if(hasleftside == true) {
				if(gameleftx <= leftside) {
					xspeed = 0;
				}
				gameleftx = Math.max(gameleftx, leftside);
				gamerightx = gameleftx + w;
			}
		}
		
		difx = gameleftx - difx;
		runTimer += dt;
		adjustSlopePosition(difx,dx,xspeedgoal);
	}
	
	public void setLeftSide(double x) { leftside = x; hasleftside = true; }
	public void setRightSide(double x) { rightside = x; hasrightside = true; }
	public void setTopSide(double y) { topside = y; hastopside = true; }
	public void setBottomSide(double y) { bottomside = y; hasbottomside = true; }
	public void clearLeftSide() { hasleftside = false; }
	public void clearRightSide() { hasrightside = false; }
	public void clearTopSide() { hastopside = false; }
	public void clearBottomSide() { hasbottomside = false; }
	
	public boolean pastScreen() { return gametopy < level.deathBorder; }
	
	public double getMaxYAdjust() { return maxYAdjust; }
	public boolean isOnGround() { return onGround; }
	
	public boolean canFallDown() {
		if(onGround == false)
			return false;
		try {
			gamebottomy-=fallDist; gametopy-=fallDist;
			double by = level.checkDown(this);
			boolean ret = (by < gamebottomy);
			gamebottomy+=fallDist; gametopy+=fallDist;
			return ret;
		} catch (NoCollisionException e) {
			gamebottomy+=fallDist; gametopy+=fallDist;
			return true;
		}
	}
	public void fallDown() {
		if(onGround == false)
			return;
		gamebottomy-=fallDist; gametopy-=fallDist;
		onGround = false;
		canJump = false;
	}

}
