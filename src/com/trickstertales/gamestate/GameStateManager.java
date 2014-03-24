package com.trickstertales.gamestate;


import com.trickstertales.handlers.TouchHandler;
import com.trickstertales.math.Constant;
import com.trickstertales.math.Keys;
import com.trickstertales.player.Player;
import com.trickstertales.screens.GameScreen;
import com.trickstertales.view.WorldRenderer;


public class GameStateManager {
	
	private static final int NULL = -1;
	public static final int PLAYSTATE = 1;
	public static final int MENUSTATE = 2;
	public static final int PAUSESTATE = 3;
	
	private int activeState, pausedState;
	private GameState stateActive, statePaused;
	private GameScreen project;
	private boolean isPaused = false;
	
	public GameStateManager(GameScreen proj) {
		activeState = NULL;
		stateActive = null;
		pausedState = NULL;
		statePaused = null;
		project = proj;
	}
	
	public Player getPlayer() {
		if(stateActive == null)
			return null;
		if(isPaused == true) {
			if(statePaused != null)
				return statePaused.getPlayer();
			return null;
		} else {
			if(stateActive != null)
				return stateActive.getPlayer();
			return null;
		}
	}
	
	public int getState() { return activeState; }
	public void setState(int st) {
		switch(st) {
		case PLAYSTATE:
			activeState = st;
			stateActive = new PlayState(project);
			stateActive.setManager(this);
			break;
		case MENUSTATE:
			activeState = st;
			stateActive = new MenuState(project);
			stateActive.setManager(this);
			break;
		case PAUSESTATE:
			pause();
			break;
		case NULL:
			System.exit(0);
			return;
		default:
			setState(activeState);
		}
	}
	public void restartState() { setState(activeState); }
	
	public void update(double dt) {
		if(Constant.IS_PC == false)
			useTouches();
		if(stateActive == null)
			return;
		if(isPaused == true) {
			if(statePaused != null)
				statePaused.update(dt);
			if(stateActive != null)
				stateActive.update(dt);
		} else {
			if(stateActive != null)
				stateActive.update(dt);
		}
	}
	private void useTouches() {
		if(TouchHandler.pressed(TouchHandler.BUTTON_LEFT))
			keyPressed(Keys.KEY_LEFT);
		if(TouchHandler.released(TouchHandler.BUTTON_LEFT))
			keyReleased(Keys.KEY_LEFT);
		
		if(TouchHandler.pressed(TouchHandler.BUTTON_RIGHT))
			keyPressed(Keys.KEY_RIGHT);
		if(TouchHandler.released(TouchHandler.BUTTON_RIGHT))
			keyReleased(Keys.KEY_RIGHT);

		if(TouchHandler.pressed(TouchHandler.BUTTON_UP))
			keyPressed(Keys.KEY_UP);
		if(TouchHandler.released(TouchHandler.BUTTON_UP))
			keyReleased(Keys.KEY_UP);

		if(TouchHandler.pressed(TouchHandler.BUTTON_DOWN))
			keyPressed(Keys.KEY_DOWN);
		if(TouchHandler.released(TouchHandler.BUTTON_DOWN))
			keyReleased(Keys.KEY_DOWN);

		if(TouchHandler.pressed(TouchHandler.BUTTON_JUMP))
			keyPressed(Keys.KEY_JUMP);
		if(TouchHandler.released(TouchHandler.BUTTON_JUMP))
			keyReleased(Keys.KEY_JUMP);

		if(TouchHandler.pressed(TouchHandler.BUTTON_SELECT))
			keyPressed(Keys.KEY_SELECT);
		if(TouchHandler.released(TouchHandler.BUTTON_SELECT))
			keyReleased(Keys.KEY_SELECT);

		if(TouchHandler.pressed(TouchHandler.BUTTON_PAUSE))
			keyPressed(Keys.KEY_PAUSE);
		if(TouchHandler.released(TouchHandler.BUTTON_PAUSE))
			keyReleased(Keys.KEY_PAUSE);
	}
	
	public void draw(WorldRenderer render, boolean debug) {
		if(stateActive == null)
			return;
		if(isPaused == true) {
			if(statePaused != null)
				statePaused.draw(render,debug);
			if(stateActive != null)
				stateActive.draw(render,debug);
		} else {
			if(stateActive != null)
				stateActive.draw(render,debug);
		}
	}

	public void keyPressed(int keycode) {
		if(stateActive == null)
			return;
		if(isPaused == true) {
			if(statePaused != null)
				statePaused.keyPressed(keycode);
			if(stateActive != null)
				stateActive.keyPressed(keycode);
		} else {
			if(stateActive != null)
				stateActive.keyPressed(keycode);
		}
	}
	public void keyReleased(int keycode) {
		if(stateActive == null)
			return;
		if(isPaused == true) {
			if(statePaused != null)
				statePaused.keyReleased(keycode);
			if(stateActive != null)
				stateActive.keyReleased(keycode);
		} else {
			if(stateActive != null)
				stateActive.keyReleased(keycode);
		}
	}
	
	public void pause() {
		if(stateActive.isPausable() == false)
			return;
		if(isPaused == true)
			return;
		
		pausedState = activeState;
		activeState = PAUSESTATE;
		statePaused = stateActive;
		stateActive = new PauseState(project);
		stateActive.setManager(this);
		
		stateActive.setPaused(true);
		statePaused.setPaused(true);
		
		isPaused = true;
	}
	public void unpause() {
		if(statePaused == null)
			return;
		if(statePaused.isPausable() == false)
			return;
		if(isPaused == false)
			return;
		
		stateActive = statePaused;
		activeState = pausedState;
		
		stateActive.setPaused(false);
		
		isPaused = false;
	}
	
	public void quit() {
		return;
		//Can't do this...
	}

	public double getX() {
		if(isPaused) {
			switch(pausedState) {
			case PLAYSTATE:
				return ((PlayState)statePaused).curLevel.viewx;
			default:
				return 0;
			}
		} else {
			switch(activeState) {
			case PLAYSTATE:
				return ((PlayState)stateActive).curLevel.viewx;
			case MENUSTATE:
				return 0;
			default:
				return 0;
			}
		}
	}
	public double getY() {
		if(isPaused) {
			switch(pausedState) {
			case PLAYSTATE:
				return ((PlayState)statePaused).curLevel.viewy;
			default:
				return 0;
			}
		} else {
			switch(activeState) {
			case PLAYSTATE:
				return ((PlayState)stateActive).curLevel.viewy;
			case MENUSTATE:
				return 0;
			default:
				return 0;
			}
		}
	}

}
