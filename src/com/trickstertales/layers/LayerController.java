package com.trickstertales.layers;

import java.util.LinkedList;

import com.trickstertales.level.Level;
import com.trickstertales.view.WorldRenderer;

public class LayerController {
	
	private LinkedList<Layer> layers;
	protected boolean isPaused = false;
	
	public LayerController() {
		layers = new LinkedList<Layer>();
	}
	
	public void addLayer(Layer layer) {
		if(layer == null)
			return;
		layers.add(layer);
	}
	public void clear() {
		if(layers.size() == 0)
			return;
		layers.clear();
	}
	
	public void draw(WorldRenderer render, boolean debug) {
		for(Layer layer : layers) {
			if(layer == null)
				continue;
			layer.draw(render,debug);
		}
	}
	
	public void update(double dt) {
		for(Layer layer : layers) {
			if(layer == null)
				continue;
			layer.update(dt);
		}
	}
	
	public void setPosition(double x, double y, double scl) {
		if(layers.size() == 0)
			return;
		for(Layer layer : layers) {
			if(layer == null)
				return;
			layer.moveTo(x, y, scl);
		}
	}
	public void setPosition(double x, double y) {
		if(layers.size() == 0)
			return;
		for(Layer layer : layers) {
			if(layer == null)
				return;
			layer.moveTo(x, y);
		}
	}
	
	public void setLevel(Level level) {
		for(Layer layer : layers) {
			if(layer == null)
				return;
			layer.setLevel(level);
		}
	}

	public void setPaused(boolean p) {
		isPaused = p;
		if(layers.size() == 0)
			return;
		for(Layer layer : layers) {
			if(layer == null)
				return;
			layer.setPaused(p);
		}
	}

}
