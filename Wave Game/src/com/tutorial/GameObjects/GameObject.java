package com.tutorial.GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.tutorial.main.Handler;
import com.tutorial.main.ID;

public abstract class GameObject {
	
	protected float x, y;
	protected ID id;
	protected float volX, volY;
	protected int width, height;
	protected Handler handler;
	protected int index;
	
	public GameObject (float x, float y, int width, int height, ID id, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.handler = handler;
		this.index = 0;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	public abstract void mapObject();
	
	public float getX() {
		return x;
	}
	public void setX (int x) {
		this.x = x;
	}
	
	public float getY () {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public ID getId () {
		return id;
	}
	public void setId (ID id) {
		this.id = id;
	}
	
	public float getVolX () {
		return volX;
	}
	public void setVolX (int volX) {
		this.volX = volX;
	}
	
	public float getVolY () {
		return volY;
	}
	public void setVolY (int volY) {
		this.volY = volY;
	}
	
	public int getIndex () {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
