package com.trickstertales.handlers;

public class Touch {
	
	public static final int BEGAN = 1;
	public static final int MOVING = 2;
	public static final int ENDED = 3;
	
	public double x = 0, y = 0;
	public int state = ENDED;
	public int id = -1;
	public int button = 0;
	
	public Touch(int id) {
		this.id = id;
	}
	
	public void pressed(double x, double y, int button) {
		this.x = x;
		this.y = y;
		this.state = BEGAN;
		this.button = button;
	}
	
	public void released(double x, double y) {
		this.x = x;
		this.y = y;
		this.state = ENDED;
	}
	
	public void moved(double x, double y) {
		this.x = x;
		this.y = y;
		this.state = MOVING;
	}

}
