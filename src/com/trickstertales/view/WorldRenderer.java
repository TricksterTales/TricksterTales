package com.trickstertales.view;


import static com.trickstertales.drawing.Art.PPI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.trickstertales.drawing.Art;
import com.trickstertales.drawing.Font;
import com.trickstertales.gamestate.GameStateManager;
import com.trickstertales.handlers.TouchHandler;
import com.trickstertales.math.Constant;
import com.trickstertales.screens.GameScreen;


public class WorldRenderer {
	
	public static final int VIRTUAL_WIDTH = Constant.APPLET_WIDTH;
	public static final int VIRTUAL_HEIGHT = Constant.APPLET_HEIGHT;
	public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH /
			(float)VIRTUAL_HEIGHT;
	public static int SCREEN_WIDTH = VIRTUAL_WIDTH;
	public static int SCREEN_HEIGHT = VIRTUAL_HEIGHT;
	
	public static Rectangle viewport;
	public OrthographicCamera camera, hudCam;
	public ShapeRenderer debugRenderer = new ShapeRenderer();
	private boolean hudActive = false;
	
	public SpriteBatch spriteBatch;
	private boolean debug = false;
	
	public void setSize(int width, int height) {
		// calculate new viewport
		SCREEN_WIDTH = width;
		SCREEN_HEIGHT = height;
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);
        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }

        float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
        PPI = 1 / scale;
        if(scale > Art.scaleHD) {
        	Art.loadArt(true);
        } else {
        	Art.loadArt(false);
        }
	}

	public WorldRenderer() {
		this.debug = false;
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		camera.update();
		hudCam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		hudCam.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		hudCam.update();
		spriteBatch = new SpriteBatch();
	}
	public WorldRenderer(boolean debug) {
		this();
		this.debug = debug;
	}
	
	public void render(GameStateManager gsm) {
		// update camera
		hudCam.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		hudCam.update();
		hudCam.apply(Gdx.gl10);
		camera.position.set((float)gsm.getX() + VIRTUAL_WIDTH / 2, (float)gsm.getY() + VIRTUAL_HEIGHT / 2, 0);
		camera.update();
		camera.apply(Gdx.gl10);
        
        // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
        		(int) viewport.width, (int) viewport.height);
        
        // clear previous frame
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		loadHUD(hudActive);
        
        //Drawing stuff here....
        debugRenderer.begin(ShapeType.Filled);
        debugRenderer.setColor(0.69f, 0.85f, 1.0f, 1);
        if(hudActive == false) {
            debugRenderer.rect((float)gsm.getX(),(float)gsm.getY(), VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        } else {
            debugRenderer.rect(0,0, VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        }
        debugRenderer.end();
		
		spriteBatch.begin();
		
		gsm.draw(this, debug);
		if(Constant.IS_PC == false)
			TouchHandler.draw(this, debug);
		
		if(debug) {
			drawToHUD(true);
			double s = Art.FONT.charWidth;
			(Art.FONT).setSize(7);
			(Art.FONT).setColor(Art.COLOR_FPS);
			(Art.FONT).halign = Font.LEFT;
			(Art.FONT).valign = Font.BOTTOM;
			(Art.FONT).draw(this, "" + GameScreen.getFPS(), 5, VIRTUAL_HEIGHT-5, 1);
			(Art.FONT).setSize(s);
		}
		
		spriteBatch.end();
		
		loadHUD(true);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(Color.BLACK);
		if(viewport.x != 0) {
			debugRenderer.rect(-viewport.x * PPI, 0, viewport.x * PPI, VIRTUAL_HEIGHT);
			debugRenderer.rect(VIRTUAL_WIDTH, 0, viewport.x * PPI, VIRTUAL_HEIGHT);
		}
		if(viewport.y != 0) {
			debugRenderer.rect(0, -viewport.y * PPI, VIRTUAL_WIDTH, viewport.y * PPI);
			debugRenderer.rect(0, VIRTUAL_HEIGHT, VIRTUAL_WIDTH, viewport.y * PPI);
		}
		debugRenderer.end();
	}
	
	public void drawToHUD(boolean onHUD) {
		spriteBatch.end();
		hudActive = onHUD;
		spriteBatch.setProjectionMatrix((hudActive)?hudCam.combined:camera.combined);
		debugRenderer.setProjectionMatrix((hudActive)?hudCam.combined:camera.combined);
		spriteBatch.begin();
	}
	private void loadHUD(boolean onHUD) {
		hudActive = onHUD;
		spriteBatch.setProjectionMatrix((hudActive)?hudCam.combined:camera.combined);
		debugRenderer.setProjectionMatrix((hudActive)?hudCam.combined:camera.combined);
	}

}
