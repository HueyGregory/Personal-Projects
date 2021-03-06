package taxigame.render.sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public static SpriteSheet ground = new SpriteSheet("Resources\\Textiles\\Ground.png");	
	public static SpriteSheet vehicles = new SpriteSheet("Resources\\Textiles\\Vehicles.png");
	public static SpriteSheet buildings = new SpriteSheet("Resources\\Textiles\\Buildings.png");
	
	private int[] pixels;
	private int width, height;
	
	public SpriteSheet (String path) {
		File file = new File(
				Objects.requireNonNull(getClass().getClassLoader().getResource(path)).getFile()
		);
		loadImage(file);
	}

	private void loadImage(File file) {
		try {
			BufferedImage image = ImageIO.read(file);
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
