package com.tutorial.GameObjects.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.tutorial.GameObjects.GameObject;
import com.tutorial.GameObjects.HUD;
import com.tutorial.GameObjects.Player;
import com.tutorial.GameObjects.Trail;
import com.tutorial.main.Handler;
import com.tutorial.main.ID;
import com.tutorial.main.TheGame;

public class SmartEnemy extends GameObject {

	private int lifeTime;
	private Random rand;
	private Player player;
	private ArrayList<Trail> trail;
	
	public SmartEnemy (int x, int y, int width, int height, int maxSpeed, ID id, Handler handler, HUD hud) {
		super(x, y, width, height, id, handler);
		rand = new Random();
		volX = rand.nextInt(maxSpeed) + 1;
		volY = rand.nextInt(maxSpeed) + 1;
		lifeTime = rand.nextInt(HUD.getLevel()) + 5;
	//	mapObject();
		assignPlayer();		
	}

	private void assignPlayer() {
		try {
			int playerNum = rand.nextInt(handler.getPlayers().size());
			this.player = handler.getPlayers().get(playerNum);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			if(handler.getPlayers().size() < 1) {
				System.exit(1);
			}
		}
	}

	@Override
	public void tick() {
		x += volX;
		y += volY;
		
	//	System.out.println("At the beginning of tick() in smartEnemy " + " Is player null? " + (player == null));
		try {
			if (player == null) {
				assignPlayer();
			}
			float diffX = x - player.getX() - 8;
			float diffY = y - player.getY() - 8;
			float distance = (float) Math.sqrt(Math.pow(x - player.getX(), 2) + Math.pow(y-player.getY(), 2));
			
			volX = (float) ((-1.0/distance) * diffX);
			volY = (float) ((-1.0/distance) * diffY);
			
			if(y <= 0 || y + 2.5 * this.height >= TheGame.HEIGHT) {
				volY *= -1;
			}
			else if (x <= 0 || x + this.width >= TheGame.WIDTH) {
				volX *= -1;
			}
			handler.addObject(new Trail(x, y, this.width, this.height, ID.Trail, Color.GREEN, 0.03, this.handler));
		} catch (NullPointerException e) {
			assignPlayer();
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int) x, (int) y, this.width, this.height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, this.width, this.height);
	}
	
	public void setLifetime (int life) {
		this.lifeTime = life;
	}
//	public int getLifetime () {
//		return this.lifeTime;
//	}

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
