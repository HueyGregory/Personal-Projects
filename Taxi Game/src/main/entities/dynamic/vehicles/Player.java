package main.entities.dynamic.vehicles;

import main.Screen;
import main.entities.dynamic.VelocityVector;
import main.entities.sprites.Sprite;
import main.entities.sprites.SpriteEntity;
import main.input.Keyboard;

public class Player extends Vehicles {
	
	private static final int TRANSPARANTCOLOR = 0x00ffdc90;
	private double x, y;
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
		pixels = new int[sprite.getWidth()][sprite.getHeight()];
		this.keyboard = keyboard;
		this.screen = screen;
	}

	@Override
	public void tick() {
	//	velocityVector.setR(0);
		if (keyboard.getUp()) {
			if (velocityVector.getR() >= 5) velocityVector.setR(5);
			else velocityVector.setR(velocityVector.getR() + 0.1);
		}
		if (keyboard.getDown()) {
			if (velocityVector.getR() <= -5) velocityVector.setR(-5);
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
		
	//	int[][] pixels = setXandY(sprite.getPixels());
		if (withinBounds(screen)) {
		//	System.out.println("[Player - render()] entered render(): " + increment++);
		//	int xx = 0;
		//	for (double i = this.x - screen.getXPixel(); (i < this.sprite.getWidth() + this.x - screen.getXPixel()); i++) {
				for (int i = 0; i < this.sprite.getWidth(); i++) {
		//		int yy = 0;
			//	for (double j = this.y - screen.getYPixel(); (j < this.sprite.getHeight() + this.y - screen.getYPixel()); j++) {
					for (int j = 0; j < this.sprite.getHeight(); j++) {
					try { 
				//		int spriteColor = pixels[xx][yy++];;
						int spriteColor = sprite.getPixels()[i][j];
						if (!(spriteColor == -1)) {	// -1 means to ignore that pixel in the sprite
							pixels[i][j] = spriteColor;	// pixel-accuracy
						}
						else {	
							pixels[i][j] = screen.getPixels()[i+screen.getWidthPixel()/2][j+screen.getHeightPixel()/2];
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
						break;
					}
				}
			//	xx++;
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
		
		
		// makes the player's sprite tilted
		int[][] newPixels = new int[sprite.getWidth()][sprite.getHeight()];
		for(int i = 0; i < spritePixels.length; i++) {
			for (int j = 0; j < spritePixels[i].length; j++) {
				System.out.printf("[Player - setXandY()] i == %d, j == %d; velocityVector.getXV() == %f, velocityVector.getYV() == %f; i/velocityVector.getXV() == %f, j/velocityVector.getYV() == %f\n", i, j, velocityVector.getXV(), velocityVector.getYV(), i/velocityVector.getXV(), j/velocityVector.getYV());
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
