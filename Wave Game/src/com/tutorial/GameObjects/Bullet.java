package com.tutorial.GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.tutorial.main.Handler;
import com.tutorial.main.ID;

public class Bullet extends GameObject {

	public Bullet (int startX, int startY, int finalX, int finalY, Handler handler) {
		super(startX, startY, 5, 10, ID.Bullet, handler);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mapObject() {
		// TODO Auto-generated method stub
		
	}
	
}
