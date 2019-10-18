package taxigame.entities.sprites;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public static SpriteSheet ground = new SpriteSheet("Resources\\Textiles\\Ground.png");	
	public static SpriteSheet vehicles = new SpriteSheet("Resources\\Textiles\\Vehicles.png");
	
	private int[] pixels;
	private int width, height;
	
	public SpriteSheet (String path) {
		loadImage(path);
	}

	private void loadImage(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			//BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			pixels = new int[this.width * this.height];
			image.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getWidth () {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public int[] getPixels() {
		return this.pixels;
	}
	
}
