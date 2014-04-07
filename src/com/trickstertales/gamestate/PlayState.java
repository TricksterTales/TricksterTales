package com.trickstertales.gamestate;



import com.trickstertales.backup.Data;
import com.trickstertales.drawing.Art;
import com.trickstertales.handlers.TouchHandler;
import com.trickstertales.layers.CloudLayer;
import com.trickstertales.layers.Layer;
import com.trickstertales.layers.LayerController;
import com.trickstertales.level.Level;
import com.trickstertales.level.Level01;
import com.trickstertales.level.Level02;
import com.trickstertales.math.Constant;
import com.trickstertales.math.Keys;
import com.trickstertales.math.Maths;
import com.trickstertales.math.Point2D;
import com.trickstertales.objects.Door;
import com.trickstertales.player.Player;
import com.trickstertales.screens.GameScreen;
import com.trickstertales.view.WorldRenderer;


public class PlayState extends GameState {
	
	public static final int KEY_LEFT = Keys.KEY_LEFT;
	public static final int KEY_RIGHT = Keys.KEY_RIGHT;
	public static final int KEY_UP = Keys.KEY_UP;
	public static final int KEY_JUMP = Keys.KEY_JUMP;
	public static final int KEY_FALL = Keys.KEY_DOWN;
	public static final int KEY_PAUSE = Keys.KEY_PAUSE;
	public static final int KEY_SELECT = Keys.KEY_SELECT;
	
	public static final int LEVEL_FIRST = 1;
	
	private int curLevelNum = -1;
	public Level curLevel;
	public Player player;
	
	private boolean playerCanJump = true;
	private boolean canHitSelect = true;
	
	public boolean fancyLoading = true;
	
	public PlayState(GameScreen proj) {
		super(proj, true);
		
		if(Data.hasData() && Data.keyExists("lastLevel") && Data.keyExists("lastDoor")) {
			int lvlNum = Integer.parseInt(Data.loadValue("lastLevel"));
			if(!levelExists(lvlNum)) {
				loadLevel(LEVEL_FIRST);
				levelLoad();
			} else
				if(!travelToDoor(lvlNum, Data.loadValue("lastDoor"))) {
					loadLevel(LEVEL_FIRST);
					levelLoad();
				}
		} else {
			loadLevel(LEVEL_FIRST);
			levelLoad();
		}
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
				double sx,sy;
				sx = Constant.PLAYER_STARTX;
				sy = Constant.PLAYER_STARTY;
				String file;
				boolean didntFind = true;
				if(Data.hasData() && Data.keyExists("lastLevel") && Data.keyExists("lastDoor")) {
					file = "levels/Level"+Maths.toTwoDigits(Integer.parseInt(Data.loadValue("lastLevel")))+"_Objects.txt";
					Point2D<Integer> pos = Level.doorLocation(file, Data.loadValue("lastDoor"));
					if(pos != null) {
						sx = (double)(pos.xpos() + 0.5) * curLevel.blocksize - Constant.PLAYER_WIDTH / 2;
						sy = (double)(pos.ypos() + 1) * curLevel.blocksize - Constant.PLAYER_HEIGHT / 2;
						didntFind = false;
					}
				}
				if(didntFind) {
					file = "levels/Level"+Maths.toTwoDigits(curLevelNum)+"_Objects.txt";
					Point2D<Integer> pos = Level.doorLocation(file, "spawn");
					if(pos != null) {
						sx = (double)(pos.xpos() + 0.5) * curLevel.blocksize - Constant.PLAYER_WIDTH / 2;
						sy = (double)(pos.ypos() + 1) * curLevel.blocksize - Constant.PLAYER_HEIGHT / 2;
						Data.saveValue("lastLevel", curLevelNum);
						Data.saveValue("lastDoor", "spawn");
					}
				}
				player.resetPlayer(sx,sy, Constant.PLAYER_WIDTH, Constant.PLAYER_HEIGHT);
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
			if(canHitSelect){
				if(keygood)
					player.hitUP();
				canHitSelect = false;
			}
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
		case KEY_SELECT:
			canHitSelect = true;
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
	
	public boolean travelToDoor(int lvlnum, String label) {
		if(!levelExists(lvlnum))
			return false;
		String file = "levels/Level"+Maths.toTwoDigits(lvlnum)+"_Objects.txt";
		if(!Level.doorExists(file, label))
			return false;
		gotoLevel(lvlnum, label);
		return true;
	}
	
	private void gotoLevel(int lvlnum, String label) {
		boolean switchedLevels = (curLevelNum == -1) || (curLevelNum != lvlnum);
		if(lvlnum != curLevelNum)
			loadLevel(lvlnum);
		if(switchedLevels) {
			levelLoad();
		}
		Door d = curLevel.getDoor(label);
		if(d == null)
			return;
		player.setPosition(d.getX(), d.getY());
		Data.saveValue("lastLevel", lvlnum);
		Data.saveValue("lastDoor", label);
		if(switchedLevels) {
			curLevel.centerOn(player);
			levelLoad();
		}
	}
	
	private boolean levelExists(int lvlnum) {
		switch(lvlnum) {
		case 1:
			return true;
		case 2:
			return true;
		default:
			return false;
		}
	}
	
	private void loadLevel(int lvlNum) {
		switch(lvlNum) {
		case 1:
			LayerController bgs;
			Layer bg1;
			LayerController fgs;
			CloudLayer fg1;
			
			curLevel = new Level01(this, lvlNum);
			boolean focus = false;
			if(player == null) {
				focus = initPlayer(lvlNum);
			} else {
				focus = startPlayer(lvlNum);
			}
			curLevel.setPlayer(player);
			if(focus)
				curLevel.focusOn(player);
			
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
			break;
		case 2:
			curLevel = new Level02(this, lvlNum);
			focus = false;
			if(player == null) {
				focus = initPlayer(lvlNum);
			} else {
				focus = startPlayer(lvlNum);
			}
			curLevel.setPlayer(player);
			if(focus)
				curLevel.focusOn(player);
			
			bgs = new LayerController();
			bg1 = new Layer(Art.MOUNTAINS_SCALED, 0.85);
			
			bg1.loopy = false;
			bg1.absolutey = false;
			bg1.adjustx = Maths.randomDouble(-400, 400);
			
			bgs.addLayer(bg1);
			curLevel.setBackground(bgs);
			break;
		default:
			if(curLevelNum == -1)
				loadLevel(LEVEL_FIRST);
			return;
		}
		player.setLevel(curLevel);
		curLevelNum = lvlNum;
	}
	
	private boolean initPlayer(int lvlNum) {
		if(!levelExists(lvlNum))
			lvlNum = LEVEL_FIRST;
		double sx,sy;
		sx = Constant.PLAYER_STARTX;
		sy = Constant.PLAYER_STARTY;
		String file;
		boolean didntFind = true;
		/*if(Data.hasData() && Data.keyExists("lastLevel") && Data.keyExists("lastDoor")) {
			file = "levels/Level"+Maths.toTwoDigits(Integer.parseInt(Data.loadValue("lastLevel")))+"_Objects.txt";
			Point2D<Integer> pos = Level.doorLocation(file, Data.loadValue("lastDoor"));
			if(pos != null) {
				sx = (double)(pos.xpos() + 0.5) * curLevel.blocksize - Constant.PLAYER_WIDTH / 2;
				sy = (double)(pos.ypos() + 1) * curLevel.blocksize - Constant.PLAYER_HEIGHT / 2;
				didntFind = false;
			}
		}*/
		if(didntFind) {
			file = "levels/Level"+Maths.toTwoDigits(lvlNum)+"_Objects.txt";
			Point2D<Integer> pos = Level.doorLocation(file, "spawn");
			if(pos != null) {
				sx = (double)(pos.xpos() + 0.5) * curLevel.blocksize - Constant.PLAYER_WIDTH / 2;
				sy = (double)(pos.ypos() + 1) * curLevel.blocksize - Constant.PLAYER_HEIGHT / 2;
				Data.saveValue("lastLevel", lvlNum);
				Data.saveValue("lastDoor", "spawn");
			}
		}
		player = new Player(sx, sy, Constant.PLAYER_WIDTH, Constant.PLAYER_HEIGHT, Constant.PLAYER_HEIGHTDUCK, curLevel);
		player.setAbsoluteAnimation(Constant.PLAYER_SPAWNX + curLevel.viewx,
				Constant.PLAYER_SPAWNY + curLevel.viewy, Constant.PLAYERTWEEN);
		player.delayAnimation(Constant.PLAYERTWEENDELAY);
		return true;
	}
	
	private boolean startPlayer(int lvlNum) {
		if(!levelExists(lvlNum))
			lvlNum = LEVEL_FIRST;
		double sx,sy;
		sx = Constant.PLAYER_STARTX;
		sy = Constant.PLAYER_STARTY;
		String file;
		boolean didntFind = true;
		/*if(Data.hasData() && Data.keyExists("lastLevel") && Data.keyExists("lastDoor")) {
			file = "levels/Level"+Maths.toTwoDigits(Integer.parseInt(Data.loadValue("lastLevel")))+"_Objects.txt";
			Point2D<Integer> pos = Level.doorLocation(file, Data.loadValue("lastDoor"));
			if(pos != null) {
				sx = (double)(pos.xpos() + 0.5) * curLevel.blocksize - Constant.PLAYER_WIDTH / 2;
				sy = (double)(pos.ypos() + 1) * curLevel.blocksize - Constant.PLAYER_HEIGHT / 2;
				didntFind = false;
			}
		}*/
		if(didntFind) {
			file = "levels/Level"+Maths.toTwoDigits(lvlNum)+"_Objects.txt";
			Point2D<Integer> pos = Level.doorLocation(file, "spawn");
			if(pos != null) {
				sx = (double)(pos.xpos() + 0.5) * curLevel.blocksize - Constant.PLAYER_WIDTH / 2;
				sy = (double)(pos.ypos() + 1) * curLevel.blocksize - Constant.PLAYER_HEIGHT / 2;
				Data.saveValue("lastLevel", lvlNum);
				Data.saveValue("lastDoor", "spawn");
			}
		}
		player.setPosition(sx, sy);
		return true;
	}
	
	private void levelLoad() {
		if(curLevel == null) {
			fancyLoading = false;
			return;
		}
		curLevel.loadStuff();
		player.setAbsoluteAnimation(Constant.PLAYER_SPAWNX + curLevel.viewx,
				Constant.PLAYER_SPAWNY + curLevel.viewy, Constant.PLAYERTWEEN);
		player.delayAnimation(Constant.PLAYERTWEENDELAY);
		fancyLoading = true;
	}

}
