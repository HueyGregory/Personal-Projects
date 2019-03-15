package main.entities;

import main.render.Screen;
import main.render.sprites.Sprite;

public class Entity {
	
	//protected int[][] pixels;
	protected double x, y;		// pixel-precision 
	protected int width, height;
	protected Sprite sprite;
	protected int index;
	
	public void tick() {
	}

	public void render(int x, int y, Screen screen) {
		//if (withinBounds(screen)) {
			
		//}
	}
	
	protected boolean withinBounds(Screen screen) {
		if ((this.x >= screen.getXPixel()) && (this.x <= screen.getXPixel() + screen.getWidthPixel()) && (this.y >= screen.getYPixel()) && (this.y <= screen.getYPixel() + screen.getHeightPixel())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int i) {
		this.index = i;
	}
	
	public int getX() {
		return (int) Math.round(x);
	}
	public int getY() {
		return (int) Math.round(y);
	}
	
}
