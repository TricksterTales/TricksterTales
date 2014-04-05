package com.trickstertales.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;


public class SlopeRight extends LevelObject {

	public SlopeRight(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		data = "slopeRight";
	}
	
	public void drawSelfAt(WorldRenderer render, double x, double y, boolean debug) {
		Art.drawImage(render.spriteBatch, Art.SLOPE_RIGHT, x, y, gamerightx - gameleftx, gametopy - gamebottomy);
		if(debug) {
			double w = gamerightx - gameleftx, h = gametopy - gamebottomy;
			(render.spriteBatch).end();
			(render.debugRenderer).begin(ShapeType.Line);
			(render.debugRenderer).setColor(Art.COLOR_DEBUG);
			(render.debugRenderer).line((float)x, (float)y, (float)(x + w), (float)y);
			(render.debugRenderer).line((float)x, (float)y, (float)(x + w), (float)(y + h));
			(render.debugRenderer).line((float)(x + w), (float)y, (float)(x + w), (float)(y + h));
			(render.debugRenderer).end();
			(render.spriteBatch).begin();
		}
	}
	
	public double checkCollision(double lx, double rx, double by, double ty, int side) throws NoCollisionException {
	    boolean wholeSide = false;
	    //gameleftx, gamerightx, gametopy, gamebottomy from the LevelObject
	    switch(side) {
	    case SIDE_RIGHT:
	        wholeSide = (ty >= gametopy && by <= gamebottomy);
	        if(wholeSide) {
	        	return gamerightx;
	        } else {
	        	if(rx+0.01 <= gamerightx)
	        		throw new NoCollisionException();
	        	return gamerightx;
	        }
	    case SIDE_LEFT:
	        wholeSide = (ty >= gametopy && by <= gamebottomy);
	        if(wholeSide) {
	            return gameleftx;
	        } else {
	            if(by >= gametopy || ty <= gamebottomy) {
	                throw new NoCollisionException("No collision due to y position");
	            } else {
	            	double yint = gamebottomy - gameleftx;
	            	if(by < gamebottomy) {
	            		return gameleftx;
	            	}
	                return by - yint;
	            }
	        }
		    case SIDE_TOP:
		        wholeSide = (lx <= gameleftx && rx >= gamerightx);
		        if(wholeSide) {
		            return gametopy;
		        } else {
		            if(lx >= gamerightx || rx <= gameleftx) {
		                throw new NoCollisionException("No collision due to x position");
		            } else {
		            	double yint = gamebottomy - gameleftx;
		            	if(rx > gamerightx) {
		            		return gametopy;
		            	}
		                return rx + yint;
		            }
		        }
	    default:
	        return super.checkCollision(lx,rx,by,ty,side);
	    }
	}
	
	public boolean isCollidingWith(double lx, double rx, double by, double ty) {
	    if(rx < gameleftx || lx > gamerightx)
	        return false;
	    if(by > gametopy || ty < gamebottomy)
	        return false;
	    
	    double yint = gamebottomy - gameleftx;
	    if(by < lx + yint) return true;
	    if(ty < lx + yint) return true;
	    if(by < rx + yint) return true;
	    if(ty < rx + yint) return true;
	    
	    return false;
	}
	
	public double slopeAdjust(double lx, double rx, double by, double ty, int side, double max)
			throws SlopeAdjustFailed {
		if(!(isCollidingWith(lx,rx,by,ty))) {
			return 0;
		} else {
			if(side == LevelObject.SIDE_RIGHT) {
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
			} else if(side == LevelObject.SIDE_LEFT) {
            	double yint = gamebottomy - gameleftx;
            	if(rx > gamerightx) {
            		double adj = gametopy - by;
            		if(Math.abs(adj) > max)
            			throw new SlopeAdjustFailed();
            		return adj + 0.00001;
            	}
                double adj = (rx + yint) - by;
        		if(Math.abs(adj) > max)
        			throw new SlopeAdjustFailed();
                return adj;
			} else {
				throw new SlopeAdjustFailed();
			}
		}
	}

}
