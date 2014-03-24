package com.trickstertales.gamestate;



import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.backup.Data;
import com.trickstertales.drawing.Art;
import com.trickstertales.drawing.Font;
import com.trickstertales.math.Constant;
import com.trickstertales.math.Keys;
import com.trickstertales.player.Player;
import com.trickstertales.screens.GameScreen;
import com.trickstertales.view.WorldRenderer;



public class PauseState extends GameState {
	
	public static final int KEY_UP = Keys.KEY_UP;
	public static final int KEY_DOWN = Keys.KEY_DOWN;
	public static final int KEY_SELECT = Keys.KEY_SELECT;
	public static final int KEY_PAUSE = Keys.KEY_PAUSE;
	
	private final int selectUnpause = 0;
	private final int selectSave = 1;
	private final int selectLoad = 2;
	private final int selectQuit = 3;
	
	private final int minSelect = 0;
	private final int maxSelect = 3;
	
	private final String[] selectionOptions = { "UNPAUSE", "SAVE", "LOAD", "QUIT" };
	
	private double blinker = 0;
	private int selection = minSelect;
	private boolean canGoUp = true, canGoDown = true;
	
	public PauseState(GameScreen proj) {
		super(proj,false);
		isPaused = true;
	}

	public Player getPlayer() { return null; }
	public void update(double dt) {
		blinker += dt;
		while(blinker >= 2 * Constant.BLINK_TIME) {
			blinker -= 2 * Constant.BLINK_TIME;
		}
	}
	public void draw(WorldRenderer render, boolean debug) {
		boolean blink = (blinker < Constant.BLINK_TIME);
		render.drawToHUD(true);
		(render.spriteBatch).end();
		Art.drawTransparent();
		(render.debugRenderer).begin(ShapeType.Filled);
		(render.debugRenderer).setColor(Art.COLOR_PAUSEDBACK);
		(render.debugRenderer).rect(0, 0, WorldRenderer.VIRTUAL_WIDTH, WorldRenderer.VIRTUAL_HEIGHT);
		(render.debugRenderer).end();
		(render.spriteBatch).begin();
		drawStuff(render, blink);
	}
	public void drawStuff(WorldRenderer render, boolean isBlink) {
		double mx = WorldRenderer.VIRTUAL_WIDTH / 2;
		double my = WorldRenderer.VIRTUAL_HEIGHT / 2;
		double ystep = (Art.FONT).getStringHeight("\',");
		double ypos = my + 3 * ystep;
		(Art.FONT).halign = Font.CENTER;
		(Art.FONT).valign = Font.MIDDLE;
		for(int i = minSelect; i <= maxSelect; i++) {
			if(selection == i) {
				(Art.FONT).setColor(Art.COLOR_PAUSESELECTED);
			} else {
				(Art.FONT).setColor(Art.COLOR_PAUSENORMAL);
			}
			(Art.FONT).draw(render, selectionOptions[i], (int)mx, (int)ypos);
			ypos -= 2 * ystep;
		}
		render.drawToHUD(false);
	}
	
	public void keyPressed(int keycode) {
		int key = keycode;
		switch(key) {
		case KEY_PAUSE:
			gsm.unpause();
			break;
		case KEY_UP:
			if(canGoUp == false)
				return;
			selection--;
			if(selection < minSelect)
				selection = maxSelect;
			canGoUp = false;
			break;
		case KEY_DOWN:
			if(canGoDown == false)
				return;
			selection++;
			if(selection > maxSelect)
				selection = minSelect;
			canGoDown = false;
			break;
		case KEY_SELECT:
			use();
			break;
		default:
			return;
		}
	}
	public void keyReleased(int keycode) {
		int key = keycode;
		switch(key) {
		case KEY_UP:
			canGoUp = true;
			break;
		case KEY_DOWN:
			canGoDown = true;
			break;
		default:
			return;
		}
	}
	
	private void use() {
		switch(selection) {
		case selectUnpause:
			gsm.unpause();
			break;
		case selectSave:
			Data.backupData("backup.txt");
			break;
		case selectLoad:
			Data.loadData("backup.txt");
			break;
		case selectQuit:
			gsm.unpause();
			gsm.setState(GameStateManager.MENUSTATE);
			break;
		default:
			return;
		}
	}

}
