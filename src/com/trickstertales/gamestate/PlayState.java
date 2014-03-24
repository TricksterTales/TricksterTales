package com.trickstertales.gamestate;



import com.trickstertales.drawing.Art;
import com.trickstertales.handlers.TouchHandler;
import com.trickstertales.layers.CloudLayer;
import com.trickstertales.layers.Layer;
import com.trickstertales.layers.LayerController;
import com.trickstertales.level.Level;
import com.trickstertales.level.Level01;
import com.trickstertales.math.Constant;
import com.trickstertales.math.Keys;
import com.trickstertales.math.Maths;
import com.trickstertales.player.Player;
import com.trickstertales.screens.GameScreen;
import com.trickstertales.view.WorldRenderer;


public class PlayState extends GameState {
	
	public static final int KEY_LEFT = Keys.KEY_LEFT;
	public static final int KEY_RIGHT = Keys.KEY_RIGHT;
	public static final int KEY_UP = Keys.KEY_UP;
	public static final int KEY_JUMP = Keys.KEY_JUMP;
	public static final int KEY_FALL = Keys.KEY_DOWN;
	public static final int KEY_RESTART = Keys.KEY_RESTART;
	public static final int KEY_PAUSE = Keys.KEY_PAUSE;
	public static final int KEY_SELECT = Keys.KEY_SELECT;
	
	public Level curLevel;
	public Player player;
	private boolean playerCanJump = true;
	
	public boolean fancyLoading = true;
	
	public LayerController bgs;
	public Layer bg1;
	
	public LayerController fgs;
	public CloudLayer fg1;
	
	public PlayState(GameScreen proj) {
		super(proj, true);
		
		curLevel = new Level01();
		player = new Player(Constant.PLAYER_STARTX, Constant.PLAYER_STARTY,
				Constant.PLAYER_WIDTH, Constant.PLAYER_HEIGHT, Constant.PLAYER_HEIGHTDUCK, curLevel);
		player.setAbsoluteAnimation(Constant.PLAYER_SPAWNX, Constant.PLAYER_SPAWNY, Constant.PLAYERTWEEN);
		player.delayAnimation(Constant.PLAYERTWEENDELAY);
		curLevel.setPlayer(player);
		
		bgs = new LayerController();
		bg1 = new Layer(Art.MOUNTAINS_SCALED, 0.85);
		
		fgs = new LayerController();
		fg1 = new CloudLayer(10);
		
		bg1.loopy = false;
		bg1.absolutey = true;
		bg1.adjustx = Maths.randomDouble(-400, 400);
		fg1.fillLevelWithClouds(curLevel);
		
		bgs.addLayer(bg1);
		curLevel.setBackground(bgs);

		fgs.addLayer(fg1);
		curLevel.setForeground(fgs);
		
		fancyLoading = true;
	}
	
	public void setPaused(boolean p) {
		super.setPaused(p);
		if(curLevel != null)
			curLevel.setPaused(p);
		if(player != null)
			player.setPaused(p);
	}
	
	public Player getPlayer() { return player; }
	public void update(double dt) {
		if(isPaused)
			return;
		if(fancyLoading && curLevel.doneAnimating() == true && player.doneAnimating() == true) {
			fancyLoading = false;
		}
		if(fancyLoading) {
			curLevel.animate(dt);
			player.animate(dt);
		} else {
			player.update(dt);
			curLevel.update(dt);
			if(player.pastScreen()) {
				player.resetPlayer(Constant.PLAYER_STARTX, Constant.PLAYER_STARTY,
						Constant.PLAYER_WIDTH, Constant.PLAYER_HEIGHT);
			}
		}
	}
	
	public void draw(WorldRenderer render, boolean debug) {
		render.drawToHUD(false);
		curLevel.draw(render,debug);
	}
	
	public void keyPressed(int keycode) {
		if(isPaused == true)
			return;
		int key = keycode;
		int ind = TouchHandler.buttonKey(keycode);
		boolean keygood = (ind == -1)?true:TouchHandler.active[ind];
		switch(key) {
		case KEY_LEFT:
			player.setWalkL(true);
			break;
		case KEY_RIGHT:
			player.setWalkR(true);
			break;
		case KEY_SELECT:
			if(keygood)
				player.hitUP();
			break;
		case KEY_JUMP:
			if(playerCanJump == false) {
				player.setJump(false);
				return;
			}
			player.setJump(true);
			playerCanJump = false;
			break;
		case KEY_FALL:
			player.setDucking(true);
			break;
		case KEY_RESTART:
			gsm.restartState();
			break;
		case KEY_PAUSE:
			gsm.pause();
			break;
		default:
			//Nothing
			return;
		}
	}
	
	public void keyReleased(int keycode) {
		int key = keycode;
		switch(key) {
		case KEY_LEFT:
			player.setWalkL(false);
			break;
		case KEY_RIGHT:
			player.setWalkR(false);
			break;
		case KEY_JUMP:
			playerCanJump = true;
			player.setJump(false);
			break;
		case KEY_FALL:
			player.setDucking(false);
		default:
			//Nothing
			return;
		}
	}

}
