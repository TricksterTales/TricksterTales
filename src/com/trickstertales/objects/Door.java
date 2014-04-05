package com.trickstertales.objects;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trickstertales.drawing.Art;
import com.trickstertales.exceptions.NoCollisionException;
import com.trickstertales.exceptions.SlopeAdjustFailed;
import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;


public class Door extends LevelObject {
	
	private int destLevel = -1;
	private String destDoor = "";
	private boolean hasDest = false;

	public Door(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		data = "door";
	}
	
	public void noGoal() {
		hasDest = false;
		destLevel = -1;
		destDoor = "";
	}
	public void setGoal(int level, String door) {
		destLevel = level;
		destDoor = door;
		hasDest = true;
	}
	
	public void drawSelfAt(WorldRenderer render, double x, double y, boolean debug) {
		Art.drawImage(render.spriteBatch, Art.Blank, x, y, gamerightx - gameleftx, gametopy - gamebottomy);
		if(debug) {
			(render.spriteBatch).end();
			(render.debugRenderer).begin(ShapeType.Line);
			(render.debugRenderer).setColor(Art.COLOR_DEBUG);
			(render.debugRenderer).rect((float)x, (float)y, (float)(gamerightx - gameleftx), (float)(gametopy - gamebottomy));
			(render.debugRenderer).end();
			(render.spriteBatch).begin();
		}
	}
	
	public double checkCollision(double lx, double rx, double by, double ty, int side) throws NoCollisionException {
		throw new NoCollisionException();
	}

	public double slopeAdjust(double lx, double rx, double by, double ty, int side, double max) throws SlopeAdjustFailed {
		return 0;
	}
	
	private boolean tryToTravel() {
		//System.out.println("\'" + data + "\' is trying to travel " + getDest());
		if(level == null)
			return false;
		if(hasDest == false)
			return false;
		return level.goTo(destLevel, destDoor);
	}
	
	public String getDest() {
		if(!hasDest) {
			return "NoWhere";
		}
		if(level != null && destLevel == level.getNum()) {
			return "\'" + destDoor.toUpperCase() + "\'";
		}
		return "\'" + destLevel + "-" + destDoor.toUpperCase() + "\'";
	}
	
	public boolean clicked() {
		if(!canceled)
			return false;
		super.clicked();
		canceled();
		return tryToTravel();
	}

}
