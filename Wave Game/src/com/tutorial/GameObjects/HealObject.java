package com.tutorial.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.tutorial.main.Handler;
import com.tutorial.main.ID;

public class HealObject extends GameObject {
	
	private Random rand = new Random();

	public HealObject(int x, int y, int width, int height, ID id, Handler handler) {
		super(x, y, width, height, id, handler);
		
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
	//	mapObject();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int) x, (int) y, this.width, this.height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, this.width, this.height);
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
