package com.trickstertales.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.trickstertales.drawing.Art;
import com.trickstertales.gamestate.GameStateManager;
import com.trickstertales.handlers.TouchHandler;
import com.trickstertales.math.Constant;
import com.trickstertales.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	
	public static boolean running = true;
	
	private WorldRenderer renderer;

	public GameStateManager gsm;
	
	private int width,height;
	private int[] buttons = new int[20];
	
	private double frames = 0, frameTime = 0, frameDelay = 0.1;
	private static double fps = 0;
	public static int getFPS() { return (int)Math.floor(fps + 0.5); }
	
	public GameScreen() {
		Art.loadManager();
		renderer = new WorldRenderer();
		
		width = (int)Math.floor(Constant.APPLET_WIDTH * Constant.APPLET_SCALE);
		height = (int)Math.floor(Constant.APPLET_HEIGHT * Constant.APPLET_SCALE);
		
		gsm = new GameStateManager(this);
		gsm.setState(GameStateManager.MENUSTATE);
	}
	
	public void render(float delta) {
		frames++;
		frameTime += delta;
		if(frameTime >= frameDelay) {
			fps = frames / frameTime;
			frameTime -= frameDelay;
			frames = 0;
			while(frameTime >= frameDelay) {
				frameTime -= frameDelay;
			}
		}
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		TouchHandler.optimizeHUD(gsm);
		
		TouchHandler.update();
		if(gsm != null)
			gsm.update(delta);
		
		renderer.render(gsm);
	}
	
	public void resize(int width, int height) {
		Art.reloadArt();
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	public void show() {
		Art.reloadArt();
		Gdx.input.setInputProcessor(this);
	}
	
	public void hide() {
		Art.clearManager();
		Gdx.input.setInputProcessor(null);
	}
	
	public void pause() {
		Art.clearManager();
		if(gsm != null)
			gsm.pause();
	}
	
	public void resume() {
		Art.reloadArt();
		Gdx.input.setInputProcessor(this);
	}
	
	public void dispose() {
		Art.clearManager();
		Gdx.input.setInputProcessor(null);
	}
	
	public void destroy() {
		Art.clearManager();
		Gdx.input.setInputProcessor(null);
	}
	
	public boolean keyDown(int keycode) {
		if(gsm != null)
			gsm.keyPressed(keycode);
		return true;
	}
	
	public boolean keyUp(int keycode) {
		if(gsm != null)
			gsm.keyReleased(keycode);
		return true;
	}
	
	public boolean keyTyped(char character) {
		return false;
	}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int sx = screenX, sy = screenY;
		screenX = hudX(sx);
		screenY = hudY(sy);
		buttons[pointer] = button;
		if(!clickedScreen(screenX, screenY)) {
			TouchHandler.touchReleased(screenX, screenY, pointer);
			return false;
		}
		TouchHandler.touchPressed(screenX, screenY, pointer, button);
		screenX = gameX(sx);
		screenY = gameY(sy);
		return true;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int sx = screenX, sy = screenY;
		screenX = hudX(sx);
		screenY = hudY(sy);
		buttons[pointer] = button;
		if(!clickedScreen(screenX, screenY)) {
			TouchHandler.touchReleased(screenX, screenY, pointer);
			return false;
		}
		TouchHandler.touchReleased(screenX, screenY, pointer);
		screenX = gameX(sx);
		screenY = gameY(sy);
		return true;
	}
	
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int sx = screenX, sy = screenY;
		screenX = hudX(sx);
		screenY = hudY(sy);
		if(!clickedScreen(screenX, screenY)) {
			TouchHandler.touchReleased(screenX, screenY, pointer);
			return false;
		}
		TouchHandler.touchMoved(screenX, screenY, pointer);
		screenX = gameX(sx);
		screenY = gameY(sy);
		return true;
	}
	
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	
	public boolean scrolled(int amount) {
		return false;
	}

	private int hudX(int screenX) {
		if(width < 0) {}
		float viewX = screenX - WorldRenderer.viewport.x;
		viewX = (viewX / WorldRenderer.viewport.width) *
				renderer.camera.viewportWidth;
		return (int)Math.floor(viewX);
	}
	private int hudY(int screenY) {
		float viewY = (height - screenY) - WorldRenderer.viewport.y;
		viewY = (viewY / WorldRenderer.viewport.height) *
				renderer.camera.viewportHeight;
		return (int)Math.floor(viewY);
	}
	
	private int gameX(int screenX) {
		if(width < 0) {}
		float viewX = screenX - WorldRenderer.viewport.x;
		viewX = (viewX / WorldRenderer.viewport.width) *
				renderer.camera.viewportWidth +
				renderer.camera.position.x -
				renderer.camera.viewportWidth / 2;
		return (int)Math.floor(viewX);
	}
	private int gameY(int screenY) {
		float viewY = (height - screenY) - WorldRenderer.viewport.y;
		viewY = (viewY / WorldRenderer.viewport.height) *
				renderer.camera.viewportHeight +
				renderer.camera.position.y -
				renderer.camera.viewportHeight / 2;
		return (int)Math.floor(viewY);
	}
	private boolean clickedScreen(int hudX, int hudY) {
		if(hudX < 0 || hudX > WorldRenderer.VIRTUAL_WIDTH)
			return false;
		if(hudY < 0 || hudY > WorldRenderer.VIRTUAL_HEIGHT)
			return false;
		return true;
	}

}
