package com.trickstertales.handlers;

import com.badlogic.gdx.Input.Buttons;
import com.trickstertales.drawing.Art;
import com.trickstertales.gamestate.GameStateManager;
import com.trickstertales.math.Constant;
import com.trickstertales.math.Keys;
import com.trickstertales.player.Player;
import com.trickstertales.view.WorldRenderer;

public class TouchHandler {
	
	public static final int BUTTON_COUNT = 8;
	public static final int TOUCH_COUNT = 20;
	
	public static final int BUTTON_LEFT = 0, BUTTON_RIGHT = 1, BUTTON_UP = 2, BUTTON_DOWN = 3,
			BUTTON_JUMP = 4, BUTTON_SELECT = 5, BUTTON_PAUSE = 6, BUTTON_ATTACK = 7;
	
	private static Touch[] touches;
	
	private static boolean[] pkeys;
	private static boolean[] keys;
	public static boolean[] active;
	private static final double w = Constant.APPLET_WIDTH, h = Constant.APPLET_HEIGHT;
	public static final double[] bounds = {
		//Insert leftx,bottomy,rightx,topy, /n
		20,		20,		80,		80,		//Left
		100,	20,		160,	80,		//Right
		w-160,	100,	w-100,	160,	//Up
		w-160,	20,		w-100,	80,		//Down
		w-80,	20,		w-20,	80,		//Jump
		w-80,	100,	w-20,	160,	//Select
		20,		h-80,	80,		h-20,	//Pause
		w-80,	100,	w-20,	160		//Attack, same as select
	};
	
	static {
		pkeys = new boolean[BUTTON_COUNT];
		keys = new boolean[BUTTON_COUNT];
		active = new boolean[BUTTON_COUNT];
		allOn();
		
		touches = new Touch[TOUCH_COUNT];
		for(int i = 0; i < TOUCH_COUNT; i++) {
			touches[i] = new Touch(i);
		}
	}
	
	public static int buttonKey(int i) {
		switch(i) {
		case Keys.KEY_ATTACK:
			return BUTTON_ATTACK;
		case Keys.KEY_DOWN:
			return BUTTON_DOWN;
		case Keys.KEY_JUMP:
			return BUTTON_JUMP;
		case Keys.KEY_LEFT:
			return BUTTON_LEFT;
		case Keys.KEY_PAUSE:
			return BUTTON_PAUSE;
		case Keys.KEY_RIGHT:
			return BUTTON_RIGHT;
		case Keys.KEY_UP:
			return BUTTON_UP;
		default:
			return -1;
		}
	}
	
	public static void optimizeHUD(GameStateManager gsm) {
		int st = gsm.getState();
		switch(st) {
		case GameStateManager.PLAYSTATE:
			allOn();
			Player player = gsm.getPlayer();
			active[BUTTON_UP] = false;
			if(player == null) {
				active[BUTTON_ATTACK] = false;
				active[BUTTON_SELECT] = false;
				break;
			}
			if(player.canSelect()) {
				active[BUTTON_ATTACK] = false;
			} else if(player.canAttack()) {
				active[BUTTON_SELECT] = false;
			} else {
				active[BUTTON_SELECT] = false;
				active[BUTTON_ATTACK] = false;
			}
			break;
		case GameStateManager.MENUSTATE:
			allOff();
			active[BUTTON_SELECT] = true;
			break;
		case GameStateManager.PAUSESTATE:
			allOff();
			active[BUTTON_SELECT] = true;
			active[BUTTON_PAUSE] = true;
			active[BUTTON_UP] = true;
			active[BUTTON_DOWN] = true;
			break;
		default:
			return;
		}
	}
	private static void allOn() {
		for(int i = 0; i < BUTTON_COUNT; i++) {
			active[i] = true;
		}
	}
	private static void allOff() {
		for(int i = 0; i < BUTTON_COUNT; i++) {
			active[i] = false;
		}
	}
	
	public static void update() {
		for(int i = 0; i < BUTTON_COUNT; i++) {
			pkeys[i] = keys[i];
		}
		updateButtons();
	}
	
	public static void draw(WorldRenderer render, boolean debug) {
		render.drawToHUD(true);
		for(int i = 0; i < BUTTON_COUNT; i++) {
			if(active[i] == false)
				continue;
			Art.drawImageCorners(render.spriteBatch, Art.HUD_BUTTONS[i], bounds[4*i], bounds[4*i+1],
					bounds[4*i+2], bounds[4*i+3]);
		}
		debug = true;
		
		if(!debug) {
			return;
		}
		
		/*(render.spriteBatch).end();
		(render.debugRenderer).setColor(Art.COLOR_TOUCHES);
		(render.debugRenderer).begin(ShapeType.Filled);
		Touch t;
		float dx = 10 * Art.PPI, dy = 10 * Art.PPI;
		for(int n = 0; n < TOUCH_COUNT; n++) {
			t = touches[n];
			if(t.state == Touch.ENDED)
				continue;
			(render.debugRenderer).ellipse((float)t.x - dx/2, (float)t.y - dy/2, dx, dy);
		}
		(render.debugRenderer).end();
		(render.spriteBatch).begin();*/
	}
	
	public static boolean pressed(int i) {
		if(i < 0 || i >= BUTTON_COUNT)
			return false;
		return !pkeys[i] && keys[i];
	}
	public static boolean released(int i) {
		if(i < 0 || i >= BUTTON_COUNT)
			return false;
		return pkeys[i] && !keys[i];
	}
	public static boolean isPressed(int i) {
		if(i < 0 || i >= BUTTON_COUNT)
			return false;
		return keys[i];
	}

	public static void touchPressed(double touchX, double touchY, int pointer, int button) {
		touches[pointer].pressed(touchX, touchY, button);
	}
	public static void touchReleased(double touchX, double touchY, int pointer) {
		touches[pointer].released(touchX, touchY);
	}
	public static void touchMoved(double touchX, double touchY, int pointer) {
		touches[pointer].moved(touchX, touchY);
	}
	
	private static void updateButtons() {
		Touch t;
		int n;
		for(int m = 0; m < BUTTON_COUNT; m++) {
			keys[m] = false;
		}
		for(int i = 0; i < TOUCH_COUNT; i++) {
			t = touches[i];
			if(t.state == Touch.ENDED)
				continue;
			if(Constant.IS_PC && t.button != Buttons.LEFT)
				continue;
			n = buttonNumber(t.x, t.y);
			if(n != -1 && active[n] == true) {
				keys[n] = true;
			}
		}
	}
	
	public static int buttonNumber(double touchX, double touchY) {
		//Run through and find if the touch is inside the bounds
		double lx,rx,by,ty;
		for(int i = 0; i < BUTTON_COUNT; i++) {
			lx = bounds[4*i]; by = bounds[4*i + 1]; rx = bounds[4*i + 2]; ty = bounds[4*i + 3];
			if(touchX < lx || touchX > rx)
				continue;
			if(touchY < by || touchY > ty)
				continue;
			if(active[i] == false)
				continue;
			return i;
		}
		return -1;
	}

}
