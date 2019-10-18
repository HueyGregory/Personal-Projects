package taxigame.render.sprites;

import taxigame.render.Screen;

public class SpriteEntity extends Sprite {
	
	public static SpriteEntity Taxi = new SpriteEntity(0, 0, 2*Screen.TILESIZE, Screen.TILESIZE, new int[] { 0xFFFFD0B7, 0xff44ffff }, SpriteSheet.vehicles);
//	public static SpriteEntity Limousine = new SpriteEntity(0, 1, 2*Screen.TILESIZE, Screen.TILESIZE, new int[] { 0xFF4828FF }, SpriteSheet.vehicles);

	public SpriteEntity(int x, int y, int width, int height, int[] colorsToDisplay, SpriteSheet sheet) {
		super(x*width, y * height, width, height, sheet);
		loadSprite(this.x, this.y, this.width, this.height, colorsToDisplay, sheet);
	}
	
	private void loadSprite(int startingX, int startingY, int width, int height, int[] colorsToDisplay, SpriteSheet sheet) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int i = 0; i < colorsToDisplay.length; i++) {
					int sheetPixelColor = sheet.getPixels()[(x+startingX) + (y+startingY)*sheet.getWidth()];
					if (sheetPixelColor == colorsToDisplay[i]) {
						pixels[x][y] = sheetPixelColor;	// pixel-accuracy
						break;
					}
					else {
						pixels[x][y] = -1;		// -1 means to ignore that pixel when rendering
					}
				}
				
			}
		}
	}
	
}
