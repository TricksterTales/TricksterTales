package com.trickstertales.gamestate;


import com.trickstertales.player.Player;
import com.trickstertales.screens.GameScreen;
import com.trickstertales.view.WorldRenderer;


abstract public class GameState {
	
	protected GameStateManager gsm;
	protected GameScreen project;
	protected boolean isPaused;
	private boolean pausable = false;
	
	protected GameState(GameScreen proj, boolean pable) { gsm = null; project = proj; isPaused = false; pausable = pable; };
	
	public void setManager(GameStateManager m) { gsm = m; }
	public void setPaused(boolean p) { isPaused = p; }
	public boolean isPausable() { return pausable; }
	
	abstract public Player getPlayer();
	abstract public void update(double dt);
	abstract public void draw(WorldRenderer render, boolean debug);
	abstract public void keyPressed(int keycode);
	abstract public void keyReleased(int keycode);

}
