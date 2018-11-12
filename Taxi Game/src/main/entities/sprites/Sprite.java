package main.entities.sprites;

public class Sprite {

	protected int x, y, width, height;	// pixel-precision
	protected int[][] pixels;				// pixel-precision
	protected SpriteSheet sheet;
	
	public Sprite(int x, int y, int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		this.pixels = new int[width][height];	// in terms of pixels, meaning pixel-accuracy
	}
	
	// constructor for sprites without SpriteSheets, like void tiles
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int [width][height];
	}
	
	public int getX () {
		return this.x;
	}
	public int getY () {
		return this.y;
	}
	
	public int[][] getPixels() {
		return this.pixels;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
