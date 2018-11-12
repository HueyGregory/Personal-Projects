package com.tutorial.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.tutorial.main.Handler;
import com.tutorial.main.ID;
import com.tutorial.main.TheGame;

public class Arrow extends GameObject {
	
	private Color color;
	private float startingX, startingY;
	private Random rand;
	
	
	public Arrow (float x, float y, Handler handler, int direction, Color color) {
		super(x, y, 1, 1, ID.Arrow, handler);
		startingX = x;
		startingY = y;
		setVol(direction);
		this.color = color;
		this.rand = new Random();
	}

	private void setVol(int direction) {
		switch (direction) {
		case 0:	// up
			this.volX = 0;
			this.volY = -5;
			break;
		case 1: // down
			this.volX = 0;
			this.volY = 5;
			break;
		case 2: // left
			this.volX = -5;
			this.volY = 0;
			break;
		case 3: // right
			this.volX = 5;
			this.volY = 0;
			break;
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x += volX;
		y += volY;
		this.width += volX;
		this.height += volY;
				
		if ((x < 0 || x > TheGame.WIDTH) || (y < 0 || y > TheGame.HEIGHT)) {
			handler.removeObject(this, this.getIndex());
			return;
		}
	//	mapObject();
		for (int i = 0; i < handler.getSizeOfObjectList(); i++) {
			GameObject tempObject = handler.getObject(i);
			if (tempObject == null) {
				continue;
			}
			if (tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.SmartEnemy) {	// tempObject is now an enemy
				if (this.getBounds().intersects(tempObject.getBounds())) {
					int currX = Math.round(tempObject.getX());
					int currY = Math.round(tempObject.getY());
					handler.removeObject(tempObject, tempObject.getIndex());
					tempObject = null;
					handler.removeObject(this, this.getIndex());
					if (rand.nextInt(HUD.getLevel() % 100 + 1) == 1) {
						handler.addObject(new HealObject(currX, currY, 16, 16, ID.Heal, handler));
					}
					return;
				}
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		if(this.width < 0) {
			g.fillRect((int) startingX + this.width, (int) startingY, Math.abs(this.width), Math.abs(this.height));
			g.drawRect((int) startingX + this.width, (int) startingY, Math.abs(this.width), Math.abs(this.height));
		}
		else if (this.height < 0) {
			g.fillRect((int) startingX, (int) startingY + this.height, Math.abs(this.width), Math.abs(this.height));
			g.drawRect((int) startingX, (int) startingY + this.height, Math.abs(this.width), Math.abs(this.height));
		}
		else {
			g.fillRect((int) startingX, (int) startingY, Math.abs(this.width), Math.abs(this.height));
			g.drawRect((int) startingX, (int) startingY, Math.abs(this.width), Math.abs(this.height));
		}
		
	}

	@Override
	public Rectangle getBounds() {
		if(this.width < 0) {
			return new Rectangle((int) startingX + this.width, (int) startingY, Math.abs(this.width), Math.abs(this.height));
		}
		else if (this.height < 0) {
			return new Rectangle((int) startingX, (int) startingY + this.height, Math.abs(this.width), Math.abs(this.height));
		}
		else {
			return new Rectangle((int) startingX, (int) startingY, Math.abs(this.width), Math.abs(this.height));
		}
	}

	public void mapObject() {
		Rectangle rect = this.getBounds();
		for (long i = Math.round(rect.getX()); i < rect.getWidth() + rect.getX(); i++) {
			for (long j = Math.round(rect.getY()); j < rect.getHeight() + rect.getY(); j++) {
				GameObject collisionObject;
				try {
					collisionObject = collisionDetection((int) i, (int) j);
				} catch (Exception e) {
					collisionObject = null;
				}
				
				if (collisionObject == null) {
					Handler.map[(int) i][(int) j] = this.getIndex();
				}
				else {
					switch (collisionObject.getId()) {
					case BasicEnemy:
					case SmartEnemy:
						// collision code
						int currX = Math.round(collisionObject.getX());
						int currY = Math.round(collisionObject.getY());
						handler.removeObject(collisionObject, collisionObject.getIndex());
						handler.removeObject(this, this.getIndex());
						if (rand.nextInt(HUD.getLevel() % 100 + 1) == 1) {
							handler.addObject(new HealObject(currX, currY, 16, 16, ID.Heal, handler));
						}
						break;
					default:
						break;
					}
				}
			}
		}
		
	}
	
	public GameObject collisionDetection (int i, int j) {
		if (Handler.map[i][j] != -1) {
			return Handler.getObject(Handler.map[i][j]);
		}
		else {
			return null;
		}
		
	}
}
