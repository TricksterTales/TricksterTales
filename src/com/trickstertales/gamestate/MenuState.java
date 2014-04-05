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



public class MenuState extends GameState {
	
	public final static int KEY_QUIT = Keys.KEY_PAUSE;
	public final static int KEY_PLAY = Keys.KEY_SELECT;
	
	public MenuState(GameScreen proj) {
		super(proj,false);
		Data.clearData();
	}
	
	public Player getPlayer() { return null; }
	public void update(double dt) {
	}
	
	public void draw(WorldRenderer render, boolean debug) {
		if(render == null)
			return;
		render.drawToHUD(true);
		(render.spriteBatch).end();
        (render.debugRenderer).begin(ShapeType.Filled);
        (render.debugRenderer).setColor(0.2f, 0.2f, 0.2f, 1);
        (render.debugRenderer).rect(0,0, WorldRenderer.VIRTUAL_WIDTH,WorldRenderer.VIRTUAL_HEIGHT);
        (render.debugRenderer).end();
        (render.spriteBatch).begin();
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
