package com.tutorial.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.tutorial.main.Handler;
import com.tutorial.main.ID;

public class HUD extends GameObject {	// Heads Up Display
	
	private int score = 0;
	private static int level = 1;
	
	public HUD(int x, int y, int width, int height, Handler handler) {
		super(x, y, width, height, ID.HUD, handler);
	}
	
	public void tick() {
			score++;
	}
	
	public void render (Graphics g) {
		g.drawString("Score: " + score, (int) x, 2 * ((int) y) + height);
		g.drawString("Level: " + level, (int) x, 3 * ((int) y) + height);
	}
	
	public void setScore (int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	
	public void setLevel (int level) {
		this.level = level;
	}
	public static int getLevel() {
		return level;
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
