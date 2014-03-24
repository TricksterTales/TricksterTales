package com.trickstertales.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.view.WorldRenderer;

public class Font {
	
	public static final int LEFT = -1, CENTER = 0, RIGHT = 1;
	public static final int BOTTOM = -1, MIDDLE = 0, LINE = 1, TOP = 2;
	
	public double charWidth, charHeight, charSpacing;
	public Color fontColor;
	public int halign = LEFT, valign = LINE;
	
	public Font(double w, double h, double sp) {
		charWidth = w;
		charHeight = h;
		charSpacing = sp;
		fontColor = Color.WHITE;
	}
	public Font() {
		this(0, 0, 0);
		setSize(15);
		fontColor = Color.RED;
	}
	
	public void setColor(Color fntClr) {
		fontColor = fntClr;
	}
	public void setSize(double size) {
		charHeight = 2 * size;
		charWidth = size;
		charSpacing = size / 4;
	}
	
	public void draw(WorldRenderer render, String text , double x, double y, int strokeWidth) {
		if(text == null || text == "")
			return;
		text = text.toUpperCase();
		(render.spriteBatch).end();
		(render.debugRenderer).begin(ShapeType.Line);
		(render.debugRenderer).setColor(fontColor);
		String[] parts = lines(text);
		double xstart = x, drawy = y;
		char ch;
		int w = Art.getStrokeWidth();
		Art.setStrokeWidth(strokeWidth);
		if(valign == TOP) y = y + (parts.length - 1) * (charHeight * 1.5 + 2 * charSpacing) + charHeight * 0.5;
		if(valign == BOTTOM) y = y - charHeight;
		if(valign == MIDDLE) {
			if(parts.length == 1) {
				y = y - charHeight * 0.25;
			} else {
				double tot = (parts.length - 1) * (charHeight * 1.5 + 2 * charSpacing) + charHeight * 1.5;
				y = y + .5 * tot;
				y = y - charHeight;
			}
		}
		for(String str : parts) {
			x = xstart;
			if(halign == CENTER) x -= getStringWidth(str) / 2;
			if(halign == RIGHT) x -= getStringWidth(str);
			drawy = y;
			for(int i = 0; i < str.length(); i++) {
				ch = str.charAt(i);
				drawCharacter(render.debugRenderer, ch, (float)x, (float)drawy);
				x += charWidth + charSpacing;
			}
			y -= charHeight * 1.5 + 2 * charSpacing;
		}
		Art.setStrokeWidth(w);
		(render.debugRenderer).end();
		(render.spriteBatch).begin();
	}
	public void draw(WorldRenderer render, String text, double x, double y) {
		draw(render, text, x, y, Art.getStrokeWidth());
	}
	
	public double getStringWidth(String text) {
		if(text == null || text == "")
			return 0;
		String[] parts = lines(text);
		double len = 0;
		for(String str : parts) {
			len = Math.max(len, str.length() * (charWidth + charSpacing) - charSpacing);
		}
		return len;
	}
	public double getStringHeight(String text) {
		if(text == null || text == "")
			return 0;
		String[] parts = lines(text);
		return parts.length * (1.5 * charHeight + 2 * charSpacing) - 2 * charSpacing;
	}
	private String[] lines(String text) {
		return text.split("\n");
	}
	
	private void drawCharacter(ShapeRenderer db, char ch, float x, float y) {
		float w = (float)charWidth, h = (float)charHeight;
		switch(ch) {
		case ' ':
			return;
		case '-':
			Art.line(db,x+w/4,y+h/2,x+3*w/4,y+h/2);
			return;
		case '.':
			Art.line(db,x+3*w/8,y,x+5*w/8,y);
			Art.line(db,x+3*w/8,y+h/8,x+5*w/8,y+h/8);
			Art.line(db,x+3*w/8,y,x+3*w/8,y+h/8);
			Art.line(db,x+5*w/8,y,x+5*w/8,y+h/8);
			return;
		case ',':
			Art.line(db,x+3*w/8,y,x+w/2,y);
			Art.line(db,x+3*w/8,y+h/8,x+5*w/8,y+h/8);
			Art.line(db,x+3*w/8,y,x+3*w/8,y+h/8);
			Art.line(db,x+5*w/8,y,x+5*w/8,y+h/8);
			Art.line(db,x+w/2,y-h/8,x+w/2,y);
			Art.line(db,x+w/2,y-h/8,x+5*w/8,y);
			return;
		case '0':
			Art.line(db,x,y,x+w,y);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y,x+w,y+h);
			return;
		case '1':
			Art.line(db,x+w/2,y,x+w/2,y+h);
			Art.line(db,x,y,x+w,y);
			Art.line(db,x+w/4,y+h,x+w/2,y+h);
			return;
		case '2':
			Art.line(db,x,y+3*h/4,x,y+h);
			Art.line(db,x+w,y+3*h/4,x+w,y+h);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y+h/4,x+w,y+3*h/4);
			Art.line(db,x,y,x,y+h/4);
			Art.line(db,x,y,x+w,y);
			return;
		case '3':
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x+w/2,y+h/2,x+w,y+h/2);
			Art.line(db,x,y,x+w,y);
			return;
		case '4':
			Art.line(db,x,y+h/2,x,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			Art.line(db,x+w,y,x+w,y+h);
			return;
		case '5':
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y+h/2,x,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			Art.line(db,x+w,y,x+w,y+h/2);
			Art.line(db,x,y,x+w,y);
			return;
		case '6':
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			Art.line(db,x+w,y,x+w,y+h/2);
			Art.line(db,x,y,x+w,y);
			return;
		case '7':
			Art.line(db,x,y+3*h/4,x,y+h);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y,x+w,y+h);
			Art.line(db,x+w/4,y+h/2,x+3*w/4,y+h/2);
			return;
		case '8':
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y,x+w,y);
			return;
		case '9':
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y+h/2,x,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y,x+w,y);
			return;
		case 'A':
			Art.line(db,x,y,x+w/2,y+h);
			Art.line(db,x+w/2,y+h,x+w,y);
			Art.line(db,x+w/4,y+h/2,x+3*w/4,y+h/2);
			return;
		case 'B':
			Art.line(db,x,y,x+w/2,y);
			Art.line(db,x+w/2,y,x+w,y+h/4);
			Art.line(db,x+w/2,y+h/2,x+w,y+h/4);
			Art.line(db,x,y+h/2,x+w/2,y+h/2);
			Art.line(db,x+w/2,y+h/2,x+w,y+3*h/4);
			Art.line(db,x+w/2,y+h,x+w,y+3*h/4);
			Art.line(db,x,y+h,x+w/2,y+h);
			Art.line(db,x,y,x,y+h);
			return;
		case 'C':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y,x+w,y);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x+w,y+7*h/8,x+w,y+h);
			Art.line(db,x+w,y,x+w,y+h/8);
			return;
		case 'D':
			Art.line(db,x,y,x+w/2,y);
			Art.line(db,x+w/2,y,x+w,y+h/2);
			Art.line(db,x+w/2,y+h,x+w,y+h/2);
			Art.line(db,x,y+h,x+w/2,y+h);
			Art.line(db,x,y,x,y+h);
			return;
		case 'E':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y,x+w,y);
			Art.line(db,x,y+h/2,x+w/2,y+h/2);
			Art.line(db,x,y+h,x+w,y+h);
			return;
		case 'F':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h/2,x+w/2,y+h/2);
			Art.line(db,x,y+h,x+w,y+h);
			return;
		case 'G':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y,x+w,y);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x+w,y+7*h/8,x+w,y+h);
			Art.line(db,x+w,y,x+w,y+h/2);
			Art.line(db,x+w/2,y+h/2,x+w,y+h/2);
			return;
		case 'H':
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			return;
		case 'I':
			Art.line(db,x,y,x+w,y);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x+w/2,y,x+w/2,y+h);
			return;
		case 'J':
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x+2*w/4,y,x+2*w/4,y+h);
			Art.line(db,x,y,x+2*w/4,y);
			Art.line(db,x,y,x,y+h/4);
			return;
		case 'K':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h/2,x+w,y);
			Art.line(db,x,y+h/2,x+w,y+h);
			return;
		case 'L':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y,x+w,y);
			return;
		case 'M':
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h,x+w/2,y+h/2);
			Art.line(db,x+w/2,y+h/2,x+w,y+h);
			return;
		case 'N':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h,x+w,y);
			Art.line(db,x+w,y,x+w,y+h);
			return;
		case 'O':
			Art.line(db,x,y,x+w,y);
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			return;
		case 'P':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x+w,y+h/2,x+w,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			return;
		case 'Q':
			Art.line(db,x,y,x+w,y);
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			Art.line(db,x+w/2,y+h/4,x+w,y);
			return;
		case 'R':
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x+w,y+h/2,x+w,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			Art.line(db,x,y+h/2,x+w,y);
			return;
		case 'S':
			Art.line(db,x+w,y+3*h/4,x+w,y+h);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y+h/2,x,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			Art.line(db,x+w,y,x+w,y+h/2);
			Art.line(db,x,y,x+w,y);
			Art.line(db,x,y,x,y+h/4);
			return;
		case 'T':
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x+w/2,y,x+w/2,y+h);
			return;
		case 'U':
			Art.line(db,x,y,x+w,y);
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			return;
		case 'V':
			Art.line(db,x,y+h,x+w/2,y);
			Art.line(db,x+w/2,y,x+w,y+h);
			return;
		case 'W':
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y,x+w/2,y+h/2);
			Art.line(db,x+w/2,y+h/2,x+w,y);
			return;
		case 'X':
			Art.line(db,x,y,x+w,y+h);
			Art.line(db,x+w,y,x,y+h);
			return;
		case 'Y':
			Art.line(db,x,y+h,x+w/2,y+h/2);
			Art.line(db,x+w/2,y+h/2,x+w,y+h);
			Art.line(db,x+w/2,y,x+w/2,y+h/2);
			return;
		case 'Z':
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y,x+w,y+h);
			Art.line(db,x,y,x+w,y);
			Art.line(db,x+w/4,y+h/2,x+3*w/4,y+h/2);
			return;
		default:
			Art.line(db,x,y,x+w,y);
			Art.line(db,x+w,y,x+w,y+h);
			Art.line(db,x,y+h,x+w,y+h);
			Art.line(db,x,y,x,y+h);
			Art.line(db,x,y,x+w,y+h);
			Art.line(db,x+w,y,x,y+h);
			Art.line(db,x+w/2,y,x+w/2,y+h);
			Art.line(db,x,y+h/2,x+w,y+h/2);
			return;
		}
	}

}
