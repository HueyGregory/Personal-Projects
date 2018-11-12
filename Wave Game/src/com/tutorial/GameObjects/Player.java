package com.tutorial.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.tutorial.main.Handler;
import com.tutorial.main.ID;
import com.tutorial.main.TheGame;

public class Player extends GameObject {
	
	private Color color;
	public static int playerNumber = 1;
	private int playerNum;
	private float health;
	private int greenValue;
	public int direction = 2;
	
	public Player(float x, float y, int width, int height, Color color, ID id, Handler handler) {
		super(x, y, width, height, id, handler);
		this.color = color;
		this.health = 100f;
		this.greenValue = 255;
		this.playerNum = playerNumber++;
	}

	@Override
	public void tick() {
		x += volX;
		y += volY;
		
		health = TheGame.clamp(health, 0f, 100f);
		greenValue = Math.round(health * 2);
		greenValue = Math.round(TheGame.clamp(greenValue, 0f, 255f));
		
		x = TheGame.clamp(x, 0, TheGame.WIDTH - (this.width + 7));
		y = TheGame.clamp(y, 0, TheGame.HEIGHT - (this.height + 30));
		collision();
		handler.addObject(new Trail(x, y, this.width,this.height, ID.Trail, color, 0.1, handler));

	}
	
	private void collision() {
		for (int i = 0; i < handler.getSizeOfObjectList(); i++) {
			GameObject tempObject = handler.getObject(i);
			if (tempObject == null) {
				continue;
			}
			if (tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.SmartEnemy) {	// tempObject is now BasicEnemy
				if (this.getBounds().intersects(tempObject.getBounds())) {
					// collision code
					this.health -= 2;
					if (this.health <= 0) {
						Player.playerNumber--;
						handler.getPlayers().remove(this);
						handler.removeObject(this, this.getIndex());
						return;
					}
				}
			}
			if (tempObject.getId() == ID.Heal) {	// tempObject is now Goal
				if (this.getBounds().intersects(tempObject.getBounds())) {
					// collision code
					this.health += 5;
					handler.removeObject(tempObject, tempObject.getIndex());
					i--;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {		
		g.setColor(color);
		g.fillRect((int) x, (int) y, this.width, this.height);
		g.setColor(Color.white);
		g.drawRect(15, this.playerNum*50, 200, height);
		
		// health bar
		g.setColor(Color.gray);
		g.fillRect(15, this.playerNum*50, 200, height);

		g.setColor(new Color(75, greenValue, 0));
		g.fillRect(15, this.playerNum*50, (int) (health*2), height);
		showDirection(g);
		
	}

	private void showDirection(Graphics g) {
		g.setColor(Color.white);
		switch (this.direction) {
		case 2:	// left
			g.drawRect((int) x - 5,(int) (y + (this.height/2) - 1), 10, 3);
			g.fillRect((int) x - 5,(int) (y + (this.height/2) - 1), 10, 3);
			break;
		case 3:	// right
			g.drawRect((int) x + this.width - 4,(int) (y + (this.height/2) - 1), 10, 3);
			g.fillRect((int) x + this.width - 4,(int) (y + (this.height/2) - 1), 10, 3);
			break;
		case 0:	// up
			g.drawRect((int) x + (this.width/2) - 1,(int) y - 5, 3, 10);
			g.fillRect((int) x + (this.width/2) - 1,(int) y - 5, 3, 10);
			break;
		case 1:	// down
			g.drawRect((int) x + (this.width/2) - 1,(int) y + this.height - 4, 3, 10);
			g.fillRect((int) x + (this.width/2) - 1,(int) y + this.height - 4, 3, 10);
			break;
		}
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle ((int) x, (int) y, this.width, this.height);
	}
	
//	public int getPlayerNum() {
//		return playerNum;
//	}
	public void shoot() {
		handler.addObject(new Arrow(x + (this.width/2), y + (this.height/2), handler, this.direction, color));
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void mapObject() {
		// TODO Auto-generated method stub
		Rectangle rect = this.getBounds();
		for (long i = Math.round(rect.getX()); i < rect.getWidth() + rect.getHeight(); i++) {
			for (long j = Math.round(rect.getY()); j < rect.getHeight() + rect.getY(); j++) {
				GameObject collisionObject = collisionDetection((int) i, (int) j);
				if (collisionObject == null) {
					Handler.map[(int) i][(int) j] = this.getIndex();
				}
				else {
					switch (collisionObject.getId()) {
					case BasicEnemy:
					case SmartEnemy:
						// collision code
						this.health -= 2;
						if (this.health <= 0) {
							handler.removeObject(this, this.getIndex());
							return;
						}
						break;
					case Heal:
						this.health += 5;
						handler.removeObject(collisionObject, collisionObject.getIndex());
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
