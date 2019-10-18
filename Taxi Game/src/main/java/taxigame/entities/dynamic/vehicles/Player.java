package taxigame.entities.dynamic.vehicles;

import taxigame.VelocityVector;
import taxigame.input.Keyboard;
import taxigame.render.Screen;
import taxigame.render.sprites.Sprite;
import taxigame.render.sprites.SpriteEntity;

public class Player extends Vehicles {
	
	private static final int TRANSPARANTCOLOR = 0x00ffdc90;
	//private double x, y;
	private Keyboard keyboard;
	private Screen screen;
	private VelocityVector velocityVector;
	private int[][] pixels;
	
	private int[][] xyPixelPositions; 	// the x and y position of each pixel. This should be used because the car rotates.
	private int sizeOfSprite; 			// the bigger of the width vs the height will be used to create the size of the pixels 2D array, which will be able to hold if the player rotated.
	
	private int increment = 0;

	public Player(int x, int y, Keyboard keyboard, SpriteEntity sprite, Screen screen) {
		velocityVector = new VelocityVector(0, 0);
		this.x = x*Screen.TILESIZE;		// pixel-precision
		this.y = y*Screen.TILESIZE;		// pixel-precision
		this.sprite = sprite;
		pixels = new int[sprite.getBiggerSize()][sprite.getBiggerSize()];
		this.keyboard = keyboard;
		this.screen = screen;
	}

	@Override
	public void tick() {
	//	velocityVector.setR(0);
		if (keyboard.getUp()) {
			if (velocityVector.getR() >= 2) velocityVector.setR(2);
			else velocityVector.setR(velocityVector.getR() + 0.1);
		}
		if (keyboard.getDown()) {
			if (velocityVector.getR() <= 0.1) velocityVector.setR(0);
			else velocityVector.setR(velocityVector.getR() - 0.1);
		}
		if (keyboard.getRight()) velocityVector.setAngle(velocityVector.getAngle() + 1);
		if (keyboard.getLeft()) velocityVector.setAngle(velocityVector.getAngle() - 1);
		if (velocityVector.getR() >= 0.001) velocityVector.setR(velocityVector.getR() - 0.001);
		if (velocityVector.getR() <= -0.001) velocityVector.setR(velocityVector.getR() + 0.001);
		this.x+=velocityVector.getXV();
		this.y+=velocityVector.getYV();
		
		setScreenXandY();
	}
	
	private void setScreenXandY() {
		screen.setCoordinatesPixel(this.x - screen.getWidthPixel()/2, this.y - screen.getHeightPixel()/2);	// remain in pixel-precision
	}
	
	public void render(int x, int y, Screen screen) {
		// might need to change method so as to accomodate increased pixel array size, which will then contain the pixels for the rotation, so no 
		for (int i = 0; i < this.sprite.getWidth(); i++) {
			for (int j = 0; j < this.sprite.getHeight(); j++) {
				try { 
					int spriteColor = sprite.getPixels()[i][j];
					if (!(spriteColor == -1)) {	// -1 means to ignore that pixel in the sprite
						System.out.println(velocityVector.getR());
						int newX = (int) (Math.cos(velocityVector.getRadian())*i - Math.sin(velocityVector.getRadian())*j);
						int newY = (int) (Math.sin(velocityVector.getRadian())*i + Math.cos(velocityVector.getRadian())*j);
						screen.getPixels()[(screen.getWidthPixel()/2+newX)][(screen.getHeightPixel()/2+newY)] = spriteColor;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}

	private int[][] setXandY(int[][] spritePixels) {
		// might need to set xyPixelPositions array using pythogorean theorem.
		/*
		 * from https://stackoverflow.com/questions/695080/how-do-i-rotate-an-image 
		 * newX = cos(angle)*x - sin(angle)*y
		 * newY = sin(angle)*x + cos(angle)*y
		 */
		
		
		// makes the player's sprite tiled
		int[][] newPixels = new int[sprite.getWidth()][sprite.getHeight()];
		for(int i = 0; i < spritePixels.length; i++) {
			for (int j = 0; j < spritePixels[i].length; j++) {
				newPixels[(int) ((i/(velocityVector.getXV() + 1)))][(int) ((j/(velocityVector.getYV() + 1)))] = spritePixels[i][j];
			}
		}
		return newPixels;
		
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public double getAngle() {
		return this.velocityVector.getAngle();
	}
	public int[][] getPixels() {
		return pixels;
	}
	
}
