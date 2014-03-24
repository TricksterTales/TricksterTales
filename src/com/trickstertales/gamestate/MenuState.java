package com.trickstertales.gamestate;


import com.trickstertales.drawing.Art;
import com.trickstertales.drawing.Font;
import com.trickstertales.math.Constant;
import com.trickstertales.math.Keys;
import com.trickstertales.player.Player;
import com.trickstertales.screens.GameScreen;
import com.trickstertales.view.WorldRenderer;



public class MenuState extends GameState {
	
	public final static int KEY_QUIT = Keys.KEY_PAUSE;
	public final static int KEY_PLAY = Keys.KEY_SELECT;
	
	public MenuState(GameScreen proj) {
		super(proj,false);
	}
	
	public Player getPlayer() { return null; }
	public void update(double dt) {
	}
	
	public void draw(WorldRenderer render, boolean debug) {
		if(render == null)
			return;
		render.drawToHUD(true);
		(Art.FONT).setColor(Art.COLOR_MENU);
		String str = "Press SELECT to play";
		(Art.FONT).halign = Font.CENTER;
		(Art.FONT).valign = Font.MIDDLE;
		(Art.FONT).draw(render, str, (int)Math.floor(Constant.APPLET_WIDTH / 2),
				(int)Math.floor(Constant.APPLET_HEIGHT / 2));
	}
	
	public void keyPressed(int keycode) {
		int key = keycode;
		switch(key) {
		case KEY_PLAY:
			gsm.setState(GameStateManager.PLAYSTATE);
			break;
		case KEY_QUIT:
			gsm.quit();
			break;
		default:
			return;
		}
	}
	
	public void keyReleased(int keycode) {
		
	}

}
