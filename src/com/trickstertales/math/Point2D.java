package com.trickstertales.math;

public class Point2D<T extends Number> {
	
	protected T x,y;
	
	public Point2D(T xp, T yp) {
		x = xp;
		y = yp;
	}
	public Point2D(Point2D<T> p) {
		x = p.xpos();
		y = p.ypos();
	}
	
	public T xpos() {
		return x;
	}
	public T ypos() {
		return y;
	}

}
