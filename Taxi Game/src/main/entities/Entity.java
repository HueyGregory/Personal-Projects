package main.entities;

import main.Screen;
import main.entities.sprites.Sprite;

public class Entity {
	
	//protected int[][] pixels;
	protected int x, y;		// pixel-precision 
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
		System.out.printf("[Entity - withinBounds()] entered method; this.x == %d; screen.getXPixel() == %d; screen.getWidthPixel() == %d\n", this.x, screen.getXPixel(), screen.getWidthPixel());
		if ((this.x >= screen.getXPixel()) && (this.x <= screen.getXPixel() + screen.getWidthPixel()) && (this.y >= screen.getYPixel()) && (this.y <= screen.getYPixel() + screen.getHeightPixel())) {
			return true;
		}
		else {
			System.out.printf("[Entity - withinBounds() - else{}] this.x == %d; screen.getXPixel() == %d; screen.getWidthPixel() == %d\n", this.x, screen.getXPixel(), screen.getWidthPixel());
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
		return x;
	}
	public int getY() {
		return y;
	}
	
}
