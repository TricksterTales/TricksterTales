package com.trickstertales.player;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.level.Level;
import com.trickstertales.math.Constant;
import com.trickstertales.objects.LevelObject;
import com.trickstertales.objects.Walkable;
import com.trickstertales.view.WorldRenderer;


public class Player extends Walkable {
	
	private boolean walkL = false, walkR = false;
	private boolean canWalkL = defcan, canWalkR = defcan;
	private boolean isDucking = false, tryToStand = false;
	private double standingHeight, duckingHeight;
	private double crouchSpeedRatio = Constant.PLAYER_CROUCHRATIO;
	private double crouchTime = 0, timeToFall = Constant.PLAYER_FALLTIME;
	private boolean canMove = true;
	private LevelObject signOBJ = null;
	private boolean can_attack = false, can_select = false;
	private double awayTime = 0, awayTimeDur = Constant.PLAYER_AWAYTIME;
	
	public Player(double x, double y, double width, double standingHeight, double duckingHeight, Level level) {
		super(x,y,width,standingHeight,level);
		this.standingHeight = standingHeight;
		this.duckingHeight = duckingHeight;
		this.isDucking = false;
		crouchTime = 0;
		canMove = true;
	}
	public void resetPlayer( double xpos, double ypos, double w, double h ) {
		super.reset(xpos,ypos,w,h);
		isDucking = false;
		crouchTime = 0;
		canMove = true;
		awayTime = 0;
	}
	
	public void setWalkL(boolean b) { walkL = b; }
	public void setWalkR(boolean b) { walkR = b; }
	public boolean getWalkL() { return walkL; }
	public boolean getWalkR() { return walkR; }
	public void setCanWalkL(boolean b) { canWalkL = b; }
	public void setCanWalkR(boolean b) { canWalkR = b; }
	public boolean getCanWalkL() { return canWalkL; }
	public boolean getCanWalkR() { return canWalkR; }
	public boolean canAttack() { return can_attack; }
	public boolean canSelect() { return can_select; }
	
	public void setDucking(boolean ducking) {
		if(canMove == false) {
			if(isDucking == true)
				tryToStand = true;
			return;
		}
		if(ducking == false) {
			tryToStand = false;
		}
		if(ducking == true && isDucking == false) {
			gametopy = gamebottomy + duckingHeight;
			isDucking = true;
		}
		if(ducking == false && isDucking == true) {
			tryToStand = true;
		}
		if(ducking == true)
			tryToStand = false;
		if(ducking == false)
			crouchTime = 0;
	}
	public boolean getDucking() { return isDucking; }
	
	public void update(double dt) {
		tooFarAway(Constant.PLAYER_AWAYDIST, Constant.PLAYER_TOOFAR, dt);
		can_select = touchingSomething();
		int i = 0;
		if(walkL == true) { i--; }
		if(walkR == true) { i++; }
		if(canMove == false) {
			i = 0;
			jump = false;
			if(isDucking == true)
				tryToStand = true;
		}
		
		switch(i) {
		case -1:
			facingRight = false;
			xspeed = -Constant.PLAYER_WALKSPEED;
			break;
		case 1:
			facingRight = true;
			xspeed = Constant.PLAYER_WALKSPEED;
			break;
		default:
			xspeed = 0;
			break;
		}
		if(tryToStand == true) {
			if(isDucking == false) {
				tryToStand = false;
			} else {
				gametopy = gamebottomy + standingHeight;
				if(level != null) {
					try {
						double ypos = level.checkUp(this);
						if(ypos < gametopy) {
							gametopy = gamebottomy + duckingHeight;
						} else {
							isDucking = false;
							tryToStand = false;
						}
					} catch (NoCollisionException e) {
						isDucking = false;
						tryToStand = false;
					}
				}
			}
		}
		if(isDucking == true) {
			canJump = false;
			gametopy = gamebottomy + duckingHeight;
		}
		if(isDucking) {
			xspeed *= crouchSpeedRatio;
			if(onGround) {
				crouchTime += dt;
				if(crouchTime >= timeToFall) {
					if(canFallDown()) {
						crouchTime = 0;
						fallDown();
					} else
						crouchTime = timeToFall;
				}
			} else
				crouchTime = 0;
		}
		super.update(dt);
	}
	
	public void drawSelfAt(WorldRenderer render, double x, double y, boolean debug) {
		double bor = 6;
		x -= bor; y -= bor;
		if(isAnimating || level.doneAnimating() == false) {
			Art.drawImage(render.spriteBatch, (isDucking)?Art.PLAYER_cIDLER:Art.PLAYER_IDLER,
					x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
			if(debug) {
				x += bor; y += bor;
				(render.spriteBatch).end();
				(render.debugRenderer).begin(ShapeType.Line);
				(render.debugRenderer).setColor(Art.COLOR_DEBUG);
				(render.debugRenderer).rect((float)x, (float)y, (float)(gamerightx - gameleftx), (float)(gametopy - gamebottomy));
				(render.debugRenderer).end();
				(render.spriteBatch).begin();
			}
			return;
		}
		if(isDucking == false) {
			if(onGround == true) {
				if(xspeed == 0) {
					Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_IDLER:Art.PLAYER_IDLEL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
				} else {
					Art.drawImage(render.spriteBatch,(facingRight)?Art.PLAYER_WALKR.getKeyFrame((float)runTimer,
									true):Art.PLAYER_WALKL.getKeyFrame((float)runTimer, true), x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
				}
			} else {
				if(yspeed > 0) {
					if(xspeed == 0) {
						Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_UR:Art.PLAYER_UL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
					} else {
						Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_URR:Art.PLAYER_URL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
					}
				} else {
					if(xspeed == 0) {
						Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_DR:Art.PLAYER_DL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
					} else {
						Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_DRR:Art.PLAYER_DRL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
					}
				}
			}
		} else {
			if(onGround == true) {
				if(xspeed == 0) {
					Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_cIDLER:Art.PLAYER_cIDLEL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
				} else {
					Art.drawImage(render.spriteBatch,(facingRight)?Art.PLAYER_cWALKR.getKeyFrame((float)runTimer,
									true):Art.PLAYER_cWALKL.getKeyFrame((float)runTimer, true), x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
				}
			} else {
				if(yspeed > 0) {
					if(xspeed == 0) {
						Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_cUR:Art.PLAYER_cUL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
					} else {
						Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_cURR:Art.PLAYER_cURL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
					}
				} else {
					if(xspeed == 0) {
						Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_cDR:Art.PLAYER_cDL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
					} else {
						Art.drawImage(render.spriteBatch, (facingRight)?Art.PLAYER_cDRR:Art.PLAYER_cDRL, x, y, gamerightx - gameleftx + 2 * bor, standingHeight + 2 * bor);
					}
				}
			}
		}
		if(debug) {
			x += bor; y += bor;
			(render.spriteBatch).end();
			(render.debugRenderer).begin(ShapeType.Line);
			(render.debugRenderer).setColor(Art.COLOR_DEBUG);
			(render.debugRenderer).rect((float)x, (float)y, (float)(gamerightx - gameleftx), (float)(gametopy - gamebottomy));
			(render.debugRenderer).end();
			(render.spriteBatch).begin();
		}
		
	}
	
	public void hitUP() {
		if(level == null)
			return;
		if(onGround == false)
			return;
		LevelObject obj = level.getTileObject(.5*(gameleftx+gamerightx), gamebottomy + 1);
		if(obj == null)
			return;
		if(signOBJ != null && obj == signOBJ) {
			hitENTER();
			return;
		}
		signOBJ = obj;
		signOBJ.clicked();
	}
	private void hitENTER() {
		if(signOBJ == null) {
			canMove = true;
			return;
		}
		signOBJ.canceled();
		if(signOBJ.isCanceled())
			canMove = true;
		signOBJ = null;
	}
	private boolean touchingSomething() {
		if(level == null)
			return false;
		if(onGround == false)
			return false;
		LevelObject obj = level.getTileObject(.5*(gameleftx+gamerightx), gamebottomy + 1);
		return (obj != null);
	}
	private void tooFarAway(double awayDist, double dist, double delta) {
		if(signOBJ == null) {
			awayTime = 0;
			return;
		}
		if(signOBJ.isCanceled()) {
			awayTime = 0;
			signOBJ = null;
			return;
		}
		double xm = .5 * (gameleftx+gamerightx);
		double ym = .5 * (gamebottomy+gametopy);
		double xo = .5 * (signOBJ.gameleftx+signOBJ.gamerightx);
		double yo = .5 * (signOBJ.gamebottomy+signOBJ.gametopy);
		double d = Math.sqrt(Math.pow(xm-xo, 2) + Math.pow(ym-yo, 2));
		if(d >= awayDist) {
			awayTime += delta;
			if(awayTime >= awayTimeDur) {
				awayTime = 0;
				signOBJ.canceled();
				signOBJ = null;
			}
		} else {
			awayTime = 0;
		}
		if(d >= dist) {
			signOBJ.canceled();
			signOBJ = null;
			awayTime = 0;
		}
	}

}
