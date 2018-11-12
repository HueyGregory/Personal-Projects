package com.tutorial.GameObjects.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.tutorial.GameObjects.GameObject;
import com.tutorial.GameObjects.HUD;
import com.tutorial.GameObjects.Trail;
import com.tutorial.main.Handler;
import com.tutorial.main.ID;
import com.tutorial.main.TheGame;

public class BasicEnemy extends GameObject {

	private int lifeTime;
	private Random rand;
	private ArrayList<Trail> trail;
	
	public BasicEnemy (float x, float y, int width, int height, int maxSpeed, ID id, Handler handler, HUD hud) {
		super(x, y, width, height, id, handler);
		rand = new Random();
		volX = rand.nextInt(maxSpeed) + 1;
		volY = rand.nextInt(maxSpeed) + 1;
		lifeTime = rand.nextInt(hud.getLevel()) + 5;
	}

	@Override
	public void tick() {
		x += volX;
		y += volY;
		
		if(y <= 0 || y + 2.5 * this.height >= TheGame.HEIGHT) {
			volY *= -1;
		}
		else if (x <= 0 || x + this.width >= TheGame.WIDTH) {
			volX *= -1;
		}
//		mapObject();
		handler.addObject(new Trail(x, y, this.width, this.height, ID.Trail, Color.red, 0.03, this.handler));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, this.width, this.height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, this.width, this.height);
	}
	
	public void setLifetime (int life) {
		this.lifeTime = life;
	}
	public int getLifetime () {
		return this.lifeTime;
	}

	@Override
	public void mapObject() {
		Rectangle rect = this.getBounds();
		for (long i = Math.round(rect.getX()); i < rect.getWidth() + rect.getHeight(); i++) {
			for (long j = Math.round(rect.getY()); j < rect.getHeight() + rect.getY(); j++) {
				Handler.map[(int) i][(int) j] = this.getIndex();
			}
		}
	}
}
