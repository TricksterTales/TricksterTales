package com.trickstertales.level;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.layers.LayerController;
import com.trickstertales.math.Constant;
import com.trickstertales.math.Maths;
import com.trickstertales.objects.Ground;
import com.trickstertales.objects.JumpThru;
import com.trickstertales.objects.LevelObject;
import com.trickstertales.objects.Sign;
import com.trickstertales.objects.SlopeLeft;
import com.trickstertales.objects.SlopeRight;
import com.trickstertales.objects.Walkable;
import com.trickstertales.player.Player;
import com.trickstertales.view.WorldRenderer;


public class Level {
	
	public LevelObject[] tiles;
	public LevelObject[] tileObjects;
	public LinkedList<LevelObject> objects, topObjects;
	
	public final int width,height,blocksize;
	public boolean paused = false;
	
	public final int viewmaxx,viewmaxy,vieww,viewh;
	public double viewx, viewy;
	
	public double deathBorder = 4;
	protected boolean isAnimating = true;
	
	protected Player player;
	protected LayerController bg;
	protected LayerController fg;
	
	public Level(int width, int height, int blocksize) {
		this.width = width;
		this.height = height;
		this.blocksize = blocksize;
		tiles = new LevelObject[width * height];
		tileObjects = new LevelObject[width * height];
		objects = new LinkedList<LevelObject>();
		topObjects = new LinkedList<LevelObject>();
		
		viewmaxx = width * blocksize;
		viewmaxy = height * blocksize;
		vieww = WorldRenderer.VIRTUAL_WIDTH;
		viewh = WorldRenderer.VIRTUAL_HEIGHT;
		player = null;
		bg = null;
		fg = null;
	}
	
	public void setPlayer(Player player) { this.player = player; }
	public void setBackground(LayerController back) { bg = back; if(bg != null) { bg.setLevel(this); } }
	public void setForeground(LayerController fore) { fg = fore; if(fg != null) { fg.setLevel(this); } }
	
	public void loadData(String file) {
		if(file == null)
			return;
		if(file.length() == 0)
			return;
		try {
			InputStream in = (Gdx.files.internal(file)).read();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int row = 0;
			char c;
			int bsize = this.blocksize;
			int pos;
			double spx,spy,animdelay;
		    for(String line; (line = br.readLine()) != null && row < height; row++) {
		        for(int col = 0; col < width && col < line.length(); col++) {
		        	c = line.charAt(col);
		        	pos = row * width + col;
		        	if(pos < 0 || pos > tiles.length)
		        		continue;
	        		spx = col * bsize;
	        		spy = viewmaxy + 20;
	        		animdelay = Maths.randomDouble(Constant.TWEENDELAYMIN, Constant.TWEENDELAYMAX);
		        	switch(c) {
		        	case 'G':
		        		tiles[pos] = new Ground(col * bsize, row * bsize, bsize, bsize, this);
		        		tiles[pos].setAnimation(spx,spy, Constant.TWEENDURATIONPERPIXEL);
		        		tiles[pos].delayAnimation(animdelay);
		        		break;
		        	case 's':
		        		tileObjects[pos] = new Sign(col * bsize, row * bsize, bsize, bsize, this);
		        		tileObjects[pos].setAnimation(spx,spy, Constant.TWEENDURATIONPERPIXEL);
		        		tileObjects[pos].delayAnimation(animdelay);
		        		break;
		        	case '_':
		        		tiles[pos] = new JumpThru(col * bsize, row * bsize, bsize, bsize, this);
		        		tiles[pos].setAnimation(spx,spy, Constant.TWEENDURATIONPERPIXEL);
		        		tiles[pos].delayAnimation(animdelay);
		        		break;
		        	case '/':
		        		tiles[pos] = new SlopeLeft(col * bsize, row * bsize, bsize, bsize, this);
		        		tiles[pos].setAnimation(spx,spy, Constant.TWEENDURATIONPERPIXEL);
		        		tiles[pos].delayAnimation(animdelay);
		        		break;
		        	case '\\':
		        		tiles[pos] = new SlopeRight(col * bsize, row * bsize, bsize, bsize, this);
		        		tiles[pos].setAnimation(spx,spy, Constant.TWEENDURATIONPERPIXEL);
		        		tiles[pos].delayAnimation(animdelay);
		        		break;
		        	default:
		        		tiles[pos] = null;
		        	}
		        }
		    }
		} catch (Exception e) {
			//Do nothing
			e.printStackTrace();
		}
	}
	
	public void update(double dt) {
		if(paused)
			return;
		
		if(bg != null) {
			bg.setPosition(viewx, viewy);
			bg.update(dt);
		}
		for(LevelObject obj : tiles) {
			if(obj != null)
				obj.update(dt);
		}
		for(LevelObject obj : tileObjects) {
			if(obj != null)
				obj.update(dt);
		}
		int maxi = objects.size();
		LevelObject obj;
		for(int i = 0; i < maxi; i++) {
			obj = objects.get(i);
			if(obj==null) {
				objects.remove(i);
				--i; --maxi;
			} else {
				obj.update(dt);
				if(obj.shouldBeRemoved()) {
					objects.remove(i);
					--i; --maxi;
				}
			}
		}
		if(player != null) {
			player.update(dt);
			adjustView(dt, player.gameleftx,player.gamerightx,player.gamebottomy,player.gametopy);
		}
		if(fg != null) {
			if(fg != bg) {
				fg.setPosition(viewx, viewy);
				fg.update(dt);
			}
		}
		maxi = topObjects.size();
		for(int i = 0; i < maxi; i++) {
			obj = topObjects.get(i);
			if(obj==null) {
				topObjects.remove(i);
				--i; --maxi;
			} else {
				obj.update(dt);
				if(obj.shouldBeRemoved()) {
					topObjects.remove(i);
					--i; --maxi;
				}
			}
		}
	}
	
	public boolean animate(double dt) {
		boolean allDone = true;
		
		if(bg != null) {
			bg.setPosition(viewx, viewy);
			bg.update(dt);
		}

		for(LevelObject obj : tiles) {
			if(obj != null) {
				obj.animate(dt);
				if(!obj.doneAnimating())
					allDone = false;
			}
		}
		for(LevelObject obj : tileObjects) {
			if(obj != null) {
				obj.animate(dt);
				if(!obj.doneAnimating())
					allDone = false;
			}
		}
		for(LevelObject obj : objects) {
			if(obj != null) {
				obj.animate(dt);
				if(!obj.doneAnimating())
					allDone = false;
			}
		}
		
		if(player != null)
			player.animate(dt);
		
		if(fg != null) {
			if(fg != bg) {
				fg.setPosition(viewx, viewy);
				fg.update(dt);
			}
		}
		
		for(LevelObject obj : topObjects) {
			if(obj != null) {
				obj.animate(dt);
				if(!obj.doneAnimating())
					allDone = false;
			}
		}
		
		isAnimating = !allDone;
		return allDone;
	}
	
	public void draw(WorldRenderer render, boolean debug) {
		if(bg != null)
			bg.draw(render,debug);
		for(LevelObject obj : tiles) {
			if(obj != null)
				obj.draw(render,debug);
		}
		for(LevelObject obj : tileObjects) {
			if(obj != null)
				obj.draw(render,debug);
		}
		for(LevelObject obj : objects) {
			if(obj != null)
				obj.draw(render,debug);
		}
		if(player != null)
			player.draw(render,debug);
		if(fg != null)
			fg.draw(render,debug);
		for(LevelObject obj : topObjects) {
			if(obj != null)
				obj.draw(render,debug);
		}
	}
	
	public void setPaused(boolean p) {
		paused = p;
		
	}
	public boolean doneAnimating() { return !isAnimating; }
	
	public double leftBound() { return 0; }
	
	public void setTileObject(LevelObject obj, int x, int y) {
		if(x < 0 || x >= width)
			return;
		if(y < 0 || y >= height)
			return;
		obj.setLevel(this);
		tileObjects[x + y * width] = obj;
	}
	public LevelObject getTileObjectAt(int x, int y) {
		if(x < 0 || x >= width)
			return null;
		if(y < 0 || y >= height)
			return null;
		return tileObjects[x + y * width];
	}
	public LevelObject getTileObject(double x, double y) {
		return getTileObjectAt((int)Math.floor(x / blocksize), (int)Math.floor(y / blocksize));
	}
	
	public void addObject(LevelObject obj) {
		if(obj == null)
			return;
		obj.setLevel(this);
		objects.add(obj);
	}
	public void addTop(LevelObject obj) {
		if(obj == null)
			return;
		obj.setLevel(this);
		topObjects.add(obj);
	}
	
	public void adjustView(double dt, double lx, double rx, double by, double ty) {
		double dx,dy;
		double leftx = viewx, rightx = leftx + vieww;
		double bottomy = viewy, topy = bottomy + viewh;
		double bufferx = Constant.LEVEL_BUFFX, buffery = Constant.LEVEL_BUFFY;
		
		if(player == null)
			return;
		
		if(player.gameleftx < leftx + bufferx && leftx > 0) {
			dx = player.gameleftx - (leftx + bufferx);
			if(Math.abs(dx) < Constant.VIEW_TWEENMIN) {
				leftx += dx;
			} else {
				leftx += dt * dx * Constant.VIEW_TWEEN;
			}
			leftx = Math.max(0, leftx);
			rightx = leftx + vieww;
		}else if(player.gamerightx > rightx - bufferx && rightx < viewmaxx) {
			dx = player.gamerightx - (rightx - bufferx);
			if(Math.abs(dx) < Constant.VIEW_TWEENMIN) {
				rightx += dx;
			} else {
				rightx += dt * dx * Constant.VIEW_TWEEN;
			}
			rightx = Math.min(rightx, viewmaxx);
			leftx = rightx - vieww;
		}
		
		if(player.gametopy > topy - buffery && topy < viewmaxy) {
			dy = (topy - buffery) - player.gametopy;
			if(Math.abs(dy) < Constant.VIEW_TWEENMIN) {
				topy += dy;
			} else {
				topy += dt * dy * Constant.VIEW_TWEEN;
			}
			topy = Math.min(topy, viewmaxy);
			bottomy = topy - viewh;
		} else if(player.gamebottomy < bottomy + buffery && bottomy > 0) {
			dy = (bottomy + buffery) - player.gamebottomy;
			if(Math.abs(dy) < Constant.VIEW_TWEENMIN) {
				bottomy += dy;
			} else {
				bottomy += dt * dy * Constant.VIEW_TWEEN;
			}
			bottomy = Math.max(bottomy, 0);
			topy = bottomy + viewh;
		}
		viewx = leftx;
		viewy = bottomy;
	}
	
	public void collideWithTilesVertical(Walkable player) {
		try {
			player.setTopSide(checkUp(player));
		} catch (NoCollisionException e) {
			player.clearTopSide();
		}
		try {
			player.setBottomSide(checkDown(player));
		} catch (NoCollisionException e) {
			player.clearBottomSide();
		}
	}
	public void collideWithTilesHorizontal(Walkable player) {
		try {
			player.setLeftSide(checkLeft(player));
		} catch (NoCollisionException e) {
			player.clearLeftSide();
		}
		try {
			player.setRightSide(checkRight(player));
		} catch (NoCollisionException e) {
			player.clearRightSide();
		}
	}
	public double checkLeft(Walkable player) throws NoCollisionException {
		int bsize = this.blocksize;
		double bor = 0.01;
		int l = (int)Math.floor(player.gameleftx / bsize);
		int lx = (int)Math.floor((player.gameleftx - player.maxxspeed) / bsize);
		int t = (int)Math.floor((player.gametopy - bor) / bsize);
		int b = (int)Math.floor((player.gamebottomy + bor) / bsize);
		double leftxhit = 0;
		for(int x = l; x >= lx; x--) {
			for(int y = b; y <= t; y++) {
				try{
					LevelObject obj = getElement(x,y);
					if(obj == null) {
						continue;
					} else {
						try{
							leftxhit = Math.max(obj.checkCollision(player.gameleftx, player.gamerightx, player.gamebottomy,
									player.gametopy, LevelObject.SIDE_RIGHT), leftxhit);
						} catch (NoCollisionException e) {
							continue;
						}
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		return leftxhit;
	}
	public double checkRight(Walkable player) throws NoCollisionException {
		int bsize = this.blocksize;
		double bor = 0.01;
		int r = (int)Math.floor(player.gamerightx / bsize);
		int rx = (int)Math.floor((player.gamerightx + player.maxxspeed) / bsize);
		int t = (int)Math.floor((player.gametopy - bor) / bsize);
		int b = (int)Math.floor((player.gamebottomy + bor) / bsize);
		double rightxhit = viewmaxx;
		for(int x = r; x <= rx; x++) {
			for(int y = b; y <= t; y++) {
				try{
					LevelObject obj = getElement(x,y);
					if(obj == null) {
						continue;
					} else {
						try{
							rightxhit = Math.min(obj.checkCollision(player.gameleftx, player.gamerightx, player.gamebottomy,
									player.gametopy, LevelObject.SIDE_LEFT), rightxhit);
						} catch (NoCollisionException e) {
							continue;
						}
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		return rightxhit;
	}
	public double checkUp(Walkable player) throws NoCollisionException {
		int bsize = this.blocksize;
		double bor = 0.01;
		int l = (int)Math.floor((player.gameleftx+bor) / bsize);
		int r = (int)Math.floor((player.gamerightx-bor) / bsize);
		int t = (int)Math.floor(player.gametopy / bsize);
		int ty = (int)Math.floor((player.gametopy + player.maxyspeed) / bsize);
		boolean hitGoingUp = false; double topyhit = 0;
		for(int y = t; y <= ty; y++) {
			for(int x = l; x <= r; x++) {
				try{
					LevelObject obj = getElement(x,y);
					if(obj == null) {
						continue;
					} else {
						try{
							double yyy = obj.checkCollision(player.gameleftx, player.gamerightx, player.gamebottomy,
									player.gametopy, LevelObject.SIDE_BOTTOM);
							if(hitGoingUp == false) {
								hitGoingUp = true; topyhit = yyy;
							} else {
								topyhit = Math.min(yyy, topyhit);
							}
						} catch (NoCollisionException e) {
							continue;
						}
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		if(hitGoingUp == true) {
			return topyhit;
		}
		throw new NoCollisionException("No collisions going up");
	}
	public double checkDown(Walkable player) throws NoCollisionException {
		int bsize = this.blocksize;
		double bor = 0.01;
		int l = (int)Math.floor((player.gameleftx+bor) / bsize);
		int r = (int)Math.floor((player.gamerightx-bor) / bsize);
		int b = (int)Math.floor(player.gamebottomy / bsize);
		int by = (int)Math.floor((player.gamebottomy - player.maxyspeed) / bsize);
		boolean hitGoingDown = false; double bottomyhit = 0;
		for(int y = b; y >= by; y--) {
			for(int x = l; x <= r; x++) {
				try{
					LevelObject obj = getElement(x,y);
					if(obj == null) {
						continue;
					} else {
						try{
							double yyy = obj.checkCollision(player.gameleftx, player.gamerightx, player.gamebottomy,
									player.gametopy, LevelObject.SIDE_TOP);
							if(hitGoingDown == false) {
								hitGoingDown = true; bottomyhit = yyy;
							} else {
								bottomyhit = Math.max(yyy, bottomyhit);
							}
						} catch (NoCollisionException e) {
							continue;
						}
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		if(hitGoingDown == true) {
			return bottomyhit;
		}
		throw new NoCollisionException("No collisions going down");
	}
	
	public LevelObject getElement(int x, int y) {
		if(x < 0 || x >= width) {
			return null;
		}
		if(y < 0 || y >= height) {
			return null;
		}
		return tiles[y * width + x];
	}
	public void adjustPosition(Walkable player) throws SlopeAdjustFailed {
		int side = (player.xspeed < 0)?LevelObject.SIDE_RIGHT:LevelObject.SIDE_LEFT;
		double bsize = this.blocksize;
		double bor = 0.01;
		int l = (int)Math.floor((player.gameleftx) / bsize);
		int r = (int)Math.floor((player.gamerightx) / bsize);
		int t = (int)Math.floor((player.gametopy-bor) / bsize);
		int b = (int)Math.floor((player.gamebottomy+bor) / bsize);
		
		double leftxhit = 0;
		if(player.gameleftx <= leftxhit && player.xspeed <= 0) {
			throw new SlopeAdjustFailed("The left side of the level");
		}
		if(player.gamerightx >= viewmaxx && player.xspeed >= 0) {
			throw new SlopeAdjustFailed("The right side of the level");
		}
		
		double totalAdjust = 0; LevelObject obj;
		for(int x = l; x <= r; x++) {
			for(int y = b; y <= t; y++) {
				obj = getElement(x, y);
				if(obj == null)
					continue;
				try {
					double ad = obj.slopeAdjust(player.gameleftx,player.gamerightx,player.gamebottomy,player.gametopy, side, player.getMaxYAdjust());
					player.gametopy += ad;
					player.gamebottomy += ad;
					totalAdjust += ad;
				} catch (SlopeAdjustFailed e) {
					throw new SlopeAdjustFailed();
				}
			}
		}
		if(Math.abs(totalAdjust) > player.getMaxYAdjust())
			throw new SlopeAdjustFailed();
		
		t = Math.max(t, (int)Math.floor((player.gametopy-bor) / bsize));
		b = Math.min(b, (int)Math.floor((player.gamebottomy+bor) / bsize));
		
		int x,y;
		for(x = l; x <= r; x++) {
			y = b;
			for(y = b; y <= t; y++) {
				obj = getElement(x, y);
				if(obj == null)
					continue;
				if(obj.isCollidingWith(player.gameleftx, player.gamerightx, player.gamebottomy, player.gametopy)) {
					throw new SlopeAdjustFailed();
				}
			}
		}
	}
	
	public boolean checkHitting(Walkable player) {
		double bsize = this.blocksize;
		double bor = 0.01;
		int l = (int)Math.floor((player.gameleftx+bor) / bsize);
		int r = (int)Math.floor((player.gamerightx-bor) / bsize);
		int t = (int)Math.floor((player.gametopy+bor) / bsize);
		int b = (int)Math.floor((player.gamebottomy-bor) / bsize);
		LevelObject obj;
		for(int x = l; x <= r; x++) {
			for(int y = b; y <= t; y++) {
				obj = getElement(x, y);
				if(obj == null)
					continue;
				if(obj.isCollidingWith(player.gameleftx, player.gamerightx, player.gamebottomy, player.gametopy))
					return true;
			}
		}
		return false;
	}

}
