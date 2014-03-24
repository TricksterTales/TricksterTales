package com.trickstertales.layers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.trickstertales.drawing.Art;
import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;

public class Layer {
	
	protected TextureRegion content;
	protected double width,height,scale;
	protected double xpos = 0, ypos = 0;
	public double adjustx = 0, adjusty = 0;
	protected Level level;
	public double xscale = 1,yscale = 1;
	
	public boolean loopx = true, loopy = true;
	public boolean absolutex = false,absolutey = true;
	
	protected boolean isPaused = false;
	public void setPaused(boolean p) { isPaused = p; }
	
	public Layer(TextureRegion img) {
		setImage(img);
		scale = 1;
		level = null;
	}
	public Layer(TextureRegion img, double scl) {
		setImage(img);
		scale = scl;
		level = null;
	}
	
	public void setLevel(Level lvl) {
		level = lvl;
	}
	
	public void setImage(TextureRegion img) {
		if(img == null) {
			content = null;
			width = 0;
			height = 0;
			return;
		}
		double scl = 1.0;
		if(Art.isHD())
			scl = 0.5;
		content = img;
		width = img.getRegionWidth() * scl;
		height = img.getRegionHeight() * scl;
	}
	
	public void draw(WorldRenderer render, boolean debug) {
		if(level == null) {
			return;
		}
		double s = 1.0;
		if(content != null) {
			double drawnx = xpos + adjustx * (1 + Math.abs(scale));
			double drawny = ypos + adjusty * (1 + Math.abs(scale));
			if(loopx) {
				for(double x = drawnx % width - width; x < level.viewmaxx; x += width*xscale * s) {
					if(x + width * xscale * s < level.viewx)
						continue;
					if(x > level.viewx + level.vieww)
						break;
					if(loopy) {
						for(double y = drawny % height - height; y < level.viewmaxy; y += height*yscale * s) {
							if(y + height * yscale * s < level.viewy)
								continue;
							if(y > level.viewy + level.viewh)
								break;
							Art.drawImage(render.spriteBatch, content,x,y,(width*xscale) * s,(height*yscale) * s);
						}
					} else {
						Art.drawImage(render.spriteBatch, content, x, Math.floor(drawny),(width*xscale) * s,
								(height*yscale) * s);
					}
				}
			} else {
				if(loopy) {
					for(double y = drawny % height - height; y < level.viewmaxy; y += height*yscale * s) {
						if(y + height * yscale * s < level.viewy)
							continue;
						if(y > level.viewy + level.viewh)
							break;
						Art.drawImage(render.spriteBatch, content,Math.floor(drawnx),y,(width*xscale) * s,
								(height*yscale) * s);
					}
				} else {
					Art.drawImage(render.spriteBatch, content, Math.floor(drawnx), Math.floor(drawny), (width*xscale) * s,(height*yscale) * s);
				}
			}
		}
	}
	public void setScale(double scl) {
		if(scale != 0) { xpos /= scale; ypos /= scale; }
		scale = -scl; xpos *= scale; ypos *= scale;
	}
	
	public void moveTo(double x, double y) {
		if(absolutex == false)
			xpos = x * scale;
		if(absolutey == false)
			ypos = y * scale;
	}
	public void moveTo(double x, double y, double scl) {
		if(absolutex == false)
			xpos = x * scl;
		if(absolutey == false)
			ypos = y * scl;
	}
	
	public void setPosition(double x, double y) {
		xpos = x; ypos = y;
	}
	
	public void setBottomLeft(double x, double y) {
		xpos = x; ypos = y + height;
	}

	public void shift(double xs, double ys) {
		if(absolutex == false)
			xpos += xs * scale;
		if(absolutey == false)
			ypos += ys * scale;
	}
	public void shift(double xs, double ys, double scl) {
		if(absolutex == false)
			xpos += xs * scl;
		if(absolutey == false)
			ypos += ys * scl;
	}
	
	public void update(double dt) {
		
	}
	
	public double getWidth() { return width; }
	public double getHeight() { return height; }

}
