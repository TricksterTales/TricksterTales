package com.trickstertales.layers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.level.Level;
import com.trickstertales.math.Constant;
import com.trickstertales.math.Maths;
import com.trickstertales.view.WorldRenderer;

public class CloudLayer extends Layer {
	
	public static final int FOREGROUND = -1;
	public static final int BACKGROUND = 1;
	
	private double[] widths,heights,xposs,yposs,scales,speeds;
	private final int maxCount;
	private int count = 0;
	private double curSpeed = -30;
	
	public CloudLayer(int maxClouds, int foreBack) {
		super(null,foreBack);
		loopx = false;
		loopy = false;
		absolutey = true;
		maxCount = maxClouds;
		count = 0;
		widths = new double[maxCount];
		heights = new double[maxCount];
		xposs = new double[maxCount];
		yposs = new double[maxCount];
		scales = new double[maxCount];
		speeds = new double[maxCount];
	}
	public CloudLayer(int maxClouds) {
		super(null, FOREGROUND);
		loopx = false;
		loopy = false;
		absolutey = true;
		maxCount = maxClouds;
		count = 0;
		widths = new double[maxCount];
		heights = new double[maxCount];
		xposs = new double[maxCount];
		yposs = new double[maxCount];
		scales = new double[maxCount];
		speeds = new double[maxCount];
	}
	
	private void addCloud(Level lvl, double width, double height) {
		if(lvl == null)
			return;
		if(count >= maxCount)
			return;
		double maxx = lvl.viewmaxx - width;
		double maxy = lvl.viewmaxy - 2 * height;
		double xpos = Maths.randomDouble(width, maxx);
		double ypos = Maths.randomDouble(height,maxy);
		widths[count] = width;
		heights[count] = height;
		xposs[count] = xpos;
		yposs[count] = ypos;
		scales[count] = Maths.randomDouble(-Constant.CLOUD_LAYERING, Constant.CLOUD_LAYERING);
		if(Math.abs(curSpeed) > Constant.CLOUD_MINSPEED) {
			speeds[count] = Maths.randomDouble(Math.signum(curSpeed), curSpeed);
		} else {
			speeds[count] = Math.signum(curSpeed) * Constant.CLOUD_MINSPEED;
		}
		count++;
	}
	
	private void addClouds(Level lvl, int amount) {
		if(lvl == null)
			return;
		if(amount <= 0)
			return;
		if(count >= maxCount)
			return;
		
		double w,h;
		
		for(int i = 0; i < amount && count < maxCount; i++) {
			w = Maths.randomDouble(Constant.CLOUD_WIDTHMIN, Constant.CLOUD_WIDTHMAX);
			h = Maths.randomDouble(Math.max(w / 4, Constant.CLOUD_HEIGHTMIN), Math.min(3 * w / 4, Constant.CLOUD_HEIGHTMAX));
			addCloud(lvl, w, h);
		}
	}
	
	public void fillLevelWithClouds(Level lvl) {
		if(lvl == null)
			return;
		if(count >= maxCount)
			return;
		addClouds(lvl, maxCount - count);
	}
	
	public void update(double dt) {
		if(count == 0)
			return;
		boolean l = (level != null);
		double adjx,adjy,spd,w,esclx;
		for(int i = 0; i < count; ++i) {
			adjx = xposs[i];
			adjy = yposs[i];
			spd = speeds[i];
			w = widths[i];
			adjx += spd * dt;
			esclx = 1 + Math.abs(scales[i] + scale);
			if(absolutex) { esclx = 1; }
			if(l) {
				if(xpos + adjx * esclx + w < 0) {
					adjx += level.viewmaxx + 2*w;
				}
				if(xpos + adjx * esclx - w > level.viewmaxx) {
					adjx -= level.viewmaxx + 2*w;
				}
			}
			
			xposs[i] = adjx;
			yposs[i] = adjy;
		}
	}
	
	public void draw(WorldRenderer render, boolean debug) {
		if(count == 0)
			return;
		if(level == null)
			return;
		double dx,dy,w,h,esclx,escly;
		(render.spriteBatch).end();
		Art.drawTransparent();
		(render.debugRenderer).begin(ShapeType.Filled);
		for(int i = 0; i < count; i++) {
			esclx = escly = 1 + Math.abs(scales[i] + scale);
			if(absolutex) { esclx = 1; }
			if(absolutey) { escly = 1; }
			dx = xpos + xposs[i] * esclx;
			dy = ypos + yposs[i] * escly;
			w = widths[i];
			h = heights[i];
			
			(render.debugRenderer).setColor(Art.COLOR_CLOUDFILL);
			(render.debugRenderer).ellipse((float)(dx - w/2), (float)(dy - h/2), (float)w, (float)h);
		}
		(render.debugRenderer).end();
		(render.spriteBatch).begin();
	}
	
	public void setScale(double scl) {
		if(scl == scale)
			return;
		if(scale != 0) {
			xpos /= scale; ypos /= scale;
		}
		scale = scl; xpos *= scale; ypos *= scale;
	}

}
