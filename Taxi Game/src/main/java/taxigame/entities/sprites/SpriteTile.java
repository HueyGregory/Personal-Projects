package taxigame.entities.sprites;

import taxigame.render.Screen;

public class SpriteTile extends Sprite {
	
	public static SpriteTile roadWithSidewalk = new SpriteTile(0, 0, Screen.TILESIZE, Screen.TILESIZE, SpriteSheet.ground);
	public static SpriteTile roadLane = new SpriteTile(0, 1, Screen.TILESIZE, Screen.TILESIZE, SpriteSheet.ground);
	//public static SpriteTile roadWithSidewalkUp = new SpriteTile(1, 0, Screen.TILESIZE, Screen.TILESIZE, SpriteSheet.ground);
	//public static SpriteTile roadWithSidewalk = new SpriteTile(0, 0, Screen.TILESIZE, Screen.TILESIZE, SpriteSheet.ground);
	//public static SpriteTile roadWithSidewalk = new SpriteTile(0, 0, Screen.TILESIZE, Screen.TILESIZE, SpriteSheet.ground);

	public SpriteTile(int x, int y, int width, int height, SpriteSheet sheet) {
		super(x, y, width, height, sheet);
		loadSprite(x, y, width, height, sheet);
	}
	
	public SpriteTile(int width, int height, int color) {
		// constructor for a void tile to avoid null pointer exceptions
		super(width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0 ; y < height; y++) {
				pixels[x][y] = color;
			}
		}
	}
	
	private void loadSprite(int startingX, int startingY, int width, int height, SpriteSheet sheet) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x][y] = sheet.getPixels()[(x+startingX) + (y+startingY)*sheet.getWidth()];	// pixel-accuracy
			}
		}
	}
		
}
