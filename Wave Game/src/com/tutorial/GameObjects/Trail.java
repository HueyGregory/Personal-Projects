package com.tutorial.GameObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.tutorial.main.Handler;
import com.tutorial.main.ID;

public class Trail extends GameObject {

	private float alpha = 1;	// takes its alpha value of one and then slowly fade away
	private Color color;
	private double life;
	
	public Trail(float x, float y, int width, int height, ID id, Color color, double life, Handler handler) {
		super(x, y, width, height, id, handler);
		this.color = color;
		this.life = life;
	}

	@Override
	public void tick() {
		if (alpha > life) {
			alpha -= life - 0.001f;
		} else handler.removeObject(this, this.getIndex());
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		
		g.setColor(color);
		g.fillRect((int) x, (int) y, this.width, this.height);
		
		g2d.setComposite(makeTransparent(1));	// need this or else will make things transparent that should not be transparent
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public void mapObject() {
		// TODO Auto-generated method stub
		
	}

}
