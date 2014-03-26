package com.trickstertales.drawing;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.math.Constant;
import com.trickstertales.view.WorldRenderer;


public class Art {
	
	public static final double scaleHD = 1.45;
	public static float PPI = 1.0f;
	
	private static int strokeWidth = 1;
	private static boolean usingHD = false;
	private static boolean loadedOnce = false;
	
	public static final Color COLOR_SKY = new Color(0.4f,0.4f,1.0f,1.0f);
	public static final Color COLOR_CLOUDFILL = new Color(1.0f,1.0f,1.0f,0.7f);
	public static final Color TIME_COLOR = Color.WHITE;
	public static final Color TIMER_BACK = Color.BLACK;
	public static final Color COLOR_PAUSEDBACK = new Color(0.1f,0.1f,0.1f,0.5f);
	public static final Color COLOR_PAUSENORMAL = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color COLOR_PAUSESELECTED = new Color(1.0f,0.5f,0.5f,1.0f);
	public static final Color COLOR_FPS = new Color(1.0f,1.0f,0.8f,1.0f);
	public static final Color COLOR_MENU = new Color(0.8f,0.8f,0.8f,1.0f);
	public static final Color COLOR_DEBUG = Color.ORANGE;
	public static final Color COLOR_BUTTONS = Color.PINK;
	public static final Color COLOR_TOUCHES = Color.WHITE;
	public static final Color COLOR_BUBBLE = new Color(0.2f,0.2f,1.0f,0.6f);
	
	public static AssetManager manager;
	
	public static TextureAtlas imageAtlas;
	public static TextureAtlas extrasAtlas;
	public static TextureRegion GROUND;
	public static TextureRegion MOUNTAINS_SCALED;
	public static TextureRegion OVERLAY;
	public static TextureRegion SLOPE_LEFT;
	public static TextureRegion SLOPE_RIGHT;
	public static TextureRegion JUMPTHRU;
	public static TextureRegion SIGN;
	public static TextureRegion Blank;

	public static TextureRegion PLAYER_IDLER;
	public static TextureRegion PLAYER_IDLEL;
	public static TextureRegion PLAYER_UR;
	public static TextureRegion PLAYER_UL;
	public static TextureRegion PLAYER_DR;
	public static TextureRegion PLAYER_DL;
	public static TextureRegion PLAYER_URR;
	public static TextureRegion PLAYER_URL;
	public static TextureRegion PLAYER_DRR;
	public static TextureRegion PLAYER_DRL;
	public static Animation PLAYER_WALKR;
	public static Animation PLAYER_WALKL;
	public static TextureRegion PLAYER_cIDLER;
	public static TextureRegion PLAYER_cIDLEL;
	public static TextureRegion PLAYER_cUR;
	public static TextureRegion PLAYER_cUL;
	public static TextureRegion PLAYER_cDR;
	public static TextureRegion PLAYER_cDL;
	public static TextureRegion PLAYER_cURR;
	public static TextureRegion PLAYER_cURL;
	public static TextureRegion PLAYER_cDRR;
	public static TextureRegion PLAYER_cDRL;
	public static Animation PLAYER_cWALKR;
	public static Animation PLAYER_cWALKL;
	private static TextureRegion[] walkframesR;
	private static TextureRegion[] walkframesL;
	private static TextureRegion[] walkframescR;
	private static TextureRegion[] walkframescL;
	
	public static TextureRegion[] HUD_BUTTONS;
	
	public static Font FONT;
	
	public static void drawTransparent() {
		Gdx.graphics.getGL10().glEnable(GL10.GL_BLEND);
	}

	public static void loadArt(boolean isHD) {
		if(isHD == usingHD && loadedOnce == true)
			return;
		usingHD = isHD;
		//System.out.println((usingHD)?"Now HD":"NotHD");
		loadArt();
	}
	public static boolean isHD() { return usingHD; }
	
	public static void loadManager() {
		if(manager != null)
			return;
		manager = new AssetManager();
		Texture.setAssetManager(manager);
		manager.load("images/Images.pack", TextureAtlas.class);
		manager.load("images/Images_HD.pack", TextureAtlas.class);
		manager.load("images/Extras.pack", TextureAtlas.class);
		manager.load("images/Extras_HD.pack", TextureAtlas.class);
		manager.update();
		manager.finishLoading();
	}
	public static void clearManager() {
		if(manager == null)
			return;
		manager.dispose();
		Texture.setAssetManager(null);
		manager = null;
	}
	public static void reloadArt() {
		if(manager == null) {
			loadManager();
			return;
		}
		manager.update();
		manager.finishLoading();
		loadArt();
	}
	
	public static void loadArt() {
		if(manager == null)
			loadManager();
		if(loadedOnce == true) {
			updateArt();
			return;
		}
		imageAtlas = manager.get("images/Images"+((usingHD)?"_HD.pack":".pack"), TextureAtlas.class);
		GROUND = imageAtlas.findRegion("ground");
		OVERLAY = imageAtlas.findRegion("GuiOverlay");
		SLOPE_LEFT = imageAtlas.findRegion("Ground_SlopeLeft");
		SLOPE_RIGHT = imageAtlas.findRegion("Ground_SlopeRight");
		JUMPTHRU = imageAtlas.findRegion("Ground_JumpThru");
		SIGN = imageAtlas.findRegion("Sign");
		
		Blank = imageAtlas.findRegion("Blank");
		
		HUD_BUTTONS = new TextureRegion[8];
		HUD_BUTTONS[0] = imageAtlas.findRegion("Button_L");
		HUD_BUTTONS[1] = imageAtlas.findRegion("Button_R");
		HUD_BUTTONS[2] = imageAtlas.findRegion("Button_U");
		HUD_BUTTONS[3] = imageAtlas.findRegion("Button_D");
		HUD_BUTTONS[4] = imageAtlas.findRegion("Button_Jump");
		HUD_BUTTONS[5] = imageAtlas.findRegion("Button_Select");
		HUD_BUTTONS[6] = imageAtlas.findRegion("Button_Pause");
		HUD_BUTTONS[7] = imageAtlas.findRegion("Button_Attack");

		FONT = new Font();
		if(usingHD)
			Art.setStrokeWidth(2);
		else
			Art.setStrokeWidth(1);
		
		extrasAtlas = manager.get("images/Extras"+((usingHD)?"_HD.pack":".pack"), TextureAtlas.class);
		MOUNTAINS_SCALED = extrasAtlas.findRegion("TileableMountainsScaled");

		PLAYER_IDLER = imageAtlas.findRegion("PlayerFrames_R");
		PLAYER_UR = imageAtlas.findRegion("PlayerFrames_U");
		PLAYER_DR = imageAtlas.findRegion("PlayerFrames_D");
		PLAYER_URR = imageAtlas.findRegion("PlayerFrames_UR");
		PLAYER_DRR = imageAtlas.findRegion("PlayerFrames_DR");
		walkframesR = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			walkframesR[i] = imageAtlas.findRegion("PlayerFrames_W"+(i+1));
		}
		PLAYER_WALKR = new Animation((float)Constant.PLAYER_WALKFRAMESPEED, walkframesR);

		PLAYER_IDLEL = new TextureRegion(PLAYER_IDLER); PLAYER_IDLEL.flip(true, false);
		PLAYER_UL = new TextureRegion(PLAYER_UR); PLAYER_UL.flip(true, false);
		PLAYER_DL = new TextureRegion(PLAYER_DR); PLAYER_DL.flip(true, false);
		PLAYER_URL = new TextureRegion(PLAYER_URR); PLAYER_URL.flip(true, false);
		PLAYER_DRL = new TextureRegion(PLAYER_DRR); PLAYER_DRL.flip(true, false);
		walkframesL = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			walkframesL[i] = new TextureRegion(walkframesR[i]);
			walkframesL[i].flip(true, false);
		}
		PLAYER_WALKL = new Animation((float)Constant.PLAYER_WALKFRAMESPEED, walkframesL);
		
		
		PLAYER_cIDLER = imageAtlas.findRegion("PlayerFrames_cR");
		PLAYER_cUR = imageAtlas.findRegion("PlayerFrames_cU");
		PLAYER_cDR = imageAtlas.findRegion("PlayerFrames_cD");
		PLAYER_cURR = imageAtlas.findRegion("PlayerFrames_cUR");
		PLAYER_cDRR = imageAtlas.findRegion("PlayerFrames_cDR");
		walkframescR = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			walkframescR[i] = imageAtlas.findRegion("PlayerFrames_cW"+(i+1));
		}
		PLAYER_cWALKR = new Animation((float)Constant.PLAYER_WALKFRAMESPEED, walkframescR);

		PLAYER_cIDLEL = new TextureRegion(PLAYER_cIDLER); PLAYER_cIDLEL.flip(true, false);
		PLAYER_cUL = new TextureRegion(PLAYER_cUR); PLAYER_cUL.flip(true, false);
		PLAYER_cDL = new TextureRegion(PLAYER_cDR); PLAYER_cDL.flip(true, false);
		PLAYER_cURL = new TextureRegion(PLAYER_cURR); PLAYER_cURL.flip(true, false);
		PLAYER_cDRL = new TextureRegion(PLAYER_cDRR); PLAYER_cDRL.flip(true, false);
		walkframescL = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			walkframescL[i] = new TextureRegion(walkframescR[i]);
			walkframescL[i].flip(true, false);
		}
		PLAYER_cWALKL = new Animation((float)Constant.PLAYER_WALKFRAMESPEED, walkframescL);
		
		loadedOnce = true;
	}
	private static void updateArt() {
		imageAtlas = manager.get("images/Images"+((usingHD)?"_HD.pack":".pack"), TextureAtlas.class);
		GROUND.setRegion(imageAtlas.findRegion("ground"));
		OVERLAY.setRegion(imageAtlas.findRegion("GuiOverlay"));
		SLOPE_LEFT.setRegion(imageAtlas.findRegion("Ground_SlopeLeft"));
		SLOPE_RIGHT.setRegion(imageAtlas.findRegion("Ground_SlopeRight"));
		JUMPTHRU.setRegion(imageAtlas.findRegion("Ground_JumpThru"));
		SIGN.setRegion(imageAtlas.findRegion("Sign"));
		
		Blank.setRegion(imageAtlas.findRegion("Blank"));
		
		HUD_BUTTONS[0].setRegion(imageAtlas.findRegion("Button_L"));
		HUD_BUTTONS[1].setRegion(imageAtlas.findRegion("Button_R"));
		HUD_BUTTONS[2].setRegion(imageAtlas.findRegion("Button_U"));
		HUD_BUTTONS[3].setRegion(imageAtlas.findRegion("Button_D"));
		HUD_BUTTONS[4].setRegion(imageAtlas.findRegion("Button_Jump"));
		HUD_BUTTONS[5].setRegion(imageAtlas.findRegion("Button_Select"));
		HUD_BUTTONS[6].setRegion(imageAtlas.findRegion("Button_Pause"));
		HUD_BUTTONS[7].setRegion(imageAtlas.findRegion("Button_Attack"));

		if(usingHD)
			Art.setStrokeWidth(2);
		else
			Art.setStrokeWidth(1);

		extrasAtlas = manager.get("images/Extras"+((usingHD)?"_HD.pack":".pack"), TextureAtlas.class);
		MOUNTAINS_SCALED.setRegion(extrasAtlas.findRegion("TileableMountainsScaled"));

		PLAYER_IDLER.setRegion(imageAtlas.findRegion("PlayerFrames_R"));
		PLAYER_UR.setRegion(imageAtlas.findRegion("PlayerFrames_U"));
		PLAYER_DR.setRegion(imageAtlas.findRegion("PlayerFrames_D"));
		PLAYER_URR.setRegion(imageAtlas.findRegion("PlayerFrames_UR"));
		PLAYER_DRR.setRegion(imageAtlas.findRegion("PlayerFrames_DR"));
		walkframesR = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			walkframesR[i] = imageAtlas.findRegion("PlayerFrames_W"+(i+1));
		}

		PLAYER_IDLEL.setRegion(new TextureRegion(PLAYER_IDLER)); PLAYER_IDLEL.flip(true, false);
		PLAYER_UL.setRegion(new TextureRegion(PLAYER_UR)); PLAYER_UL.flip(true, false);
		PLAYER_DL.setRegion(new TextureRegion(PLAYER_DR)); PLAYER_DL.flip(true, false);
		PLAYER_URL.setRegion(new TextureRegion(PLAYER_URR)); PLAYER_URL.flip(true, false);
		PLAYER_DRL.setRegion(new TextureRegion(PLAYER_DRR)); PLAYER_DRL.flip(true, false);
		walkframesL = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			walkframesL[i] = new TextureRegion(walkframesR[i]);
			walkframesL[i].flip(true, false);
		}
		
		
		PLAYER_cIDLER.setRegion(imageAtlas.findRegion("PlayerFrames_cR"));
		PLAYER_cUR.setRegion(imageAtlas.findRegion("PlayerFrames_cU"));
		PLAYER_cDR.setRegion(imageAtlas.findRegion("PlayerFrames_cD"));
		PLAYER_cURR.setRegion(imageAtlas.findRegion("PlayerFrames_cUR"));
		PLAYER_cDRR.setRegion(imageAtlas.findRegion("PlayerFrames_cDR"));
		walkframescR = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			walkframescR[i] = imageAtlas.findRegion("PlayerFrames_cW"+(i+1));
		}

		PLAYER_cIDLEL.setRegion(new TextureRegion(PLAYER_cIDLER)); PLAYER_cIDLEL.flip(true, false);
		PLAYER_cUL.setRegion(new TextureRegion(PLAYER_cUR)); PLAYER_cUL.flip(true, false);
		PLAYER_cDL.setRegion(new TextureRegion(PLAYER_cDR)); PLAYER_cDL.flip(true, false);
		PLAYER_cURL.setRegion(new TextureRegion(PLAYER_cURR)); PLAYER_cURL.flip(true, false);
		PLAYER_cDRL.setRegion(new TextureRegion(PLAYER_cDRR)); PLAYER_cDRL.flip(true, false);
		walkframescL = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			walkframescL[i] = new TextureRegion(walkframescR[i]);
			walkframescL[i].flip(true, false);
		}
	}

	public static void drawImage(SpriteBatch sb, TextureRegion tex, double xpos, double ypos, double width, double height) {
		if(sb == null || tex == null)
			return;
		sb.draw(tex, (float)xpos, (float)ypos, (float)width, (float)height);
	}
	public static void drawImage(SpriteBatch sb, TextureRegion tex, double xpos, double ypos) {
		if(sb == null || tex == null)
			return;
		sb.draw(tex, (float)xpos, (float)ypos);
	}
	public static void drawImageCorners(SpriteBatch sb, TextureRegion tex, double x1, double y1, double x2, double y2) {
		drawImage(sb,tex,x1,y1,x2-x1,y2-y1);
	}
	
	public static void setStrokeWidth(int w) {
		strokeWidth = w;
	}
	public static int getStrokeWidth() { return strokeWidth; }
	public static void line(ShapeRenderer db, double x1, double y1, double x2, double y2) {
		if(db == null)
			return;
		if(strokeWidth <= 0)
			return;
		for(int dx = -strokeWidth + 1; dx <= strokeWidth - 1; dx++) {
			for(int dy = -strokeWidth + 1; dy <= strokeWidth - 1; dy++) {
				db.line((float)(x1 + dx*PPI), (float)(y1 + dy*PPI), (float)(x2 + dx*PPI), (float)(y2 + dy*PPI));
			}
		}
	}

	public static void drawBubble(WorldRenderer render, Font fnt, double xpos, double ypos, String str, int stroke) {
		fnt.halign = Font.CENTER;
		fnt.valign = Font.TOP;
		ShapeRenderer db = render.debugRenderer;
		double bor = 10;
		double w = fnt.getStringWidth(str), h = fnt.getStringHeight(str);
		(render.spriteBatch).end();
		db.setColor(Art.COLOR_BUBBLE);
		db.begin(ShapeType.Filled);
		db.triangle((float)(xpos-bor),(float)(ypos+2*bor),(float)(xpos+bor),(float)(ypos+2*bor),(float)xpos,(float)ypos);
		ypos += 3*bor;
		db.rect((float)(xpos - w/2 - bor), (float)(ypos - bor), (float)(w + 2*bor), (float)(h + 2*bor));
		db.end();
		(render.spriteBatch).begin();
		fnt.draw(render, str, xpos, ypos, stroke);
	}
	public static void drawBubble(WorldRenderer render, Font fnt, double xpos, double ypos, String str) {
		drawBubble(render,fnt,xpos,ypos,str,Art.getStrokeWidth());
	}

}
