package main.entities;

import java.util.ArrayList;

import main.Screen;
import main.entities.sprites.SpriteTile;

public class Tile extends Entity {
	
	public static Tile tileRoadWithSideWalkDown = new Tile(SpriteTile.roadWithSidewalk, 0);
	public static Tile tileRoadWithSideWalkUp = new Tile(SpriteTile.roadWithSidewalk, 1);
	public static Tile tileRoadLane = new Tile(SpriteTile.roadLane, 0);
	public static Tile outsideVoidTile = new Tile(new SpriteTile(Screen.TILESIZE, Screen.TILESIZE, 0xffffffff), 0);
	public static Tile insideVoidTile = new Tile(new SpriteTile(Screen.TILESIZE, Screen.TILESIZE, 0xFFFFC0CB), 0);
	
	private ArrayList<Direction> directions = new ArrayList<Direction>(2);	// used by road; -1 for no direction/not a road
	private boolean solid;
	private int flipped;
	private int[][] pixels;
	
	public Tile (SpriteTile spriteTile, int flipped) {
		this.sprite = spriteTile;
		this.flipped = flipped;
		dealFlipped(flipped);
	}
	
	private void dealFlipped(int flipped) {
		int[][] flippedSprite = new int[sprite.getWidth()][sprite.getHeight()];
		switch(flipped) {
		case 0: 
			for (int x = 0; x < sprite.getWidth(); x++) {
				for (int y = 0; y < sprite.getHeight(); y++) {
					flippedSprite[x][y] = sprite.getPixels()[x][y];
				}
			}	
			break;	// keep the sprite the way it is
		case 1:				// flip the sprite up and down
			for (int x = 0; x < sprite.getWidth(); x++) {
				for (int y = 0; y < sprite.getHeight(); y++) {
					flippedSprite[x][y] = sprite.getPixels()[x][sprite.getHeight() - y - 1];
				}
			}
			break;
		default: 	break;	
		}
		pixels = flippedSprite;
	}

	public void render(int x, int y, Screen screen) {
		this.x = x;	// pixel-precision
		this.y = y;	// pixel-precision with yOffset included
		if (withinBounds(screen)) {
			int xx = Math.abs(x%Screen.TILESIZE);
			for (int i = this.x - screen.getXPixel(); (i < this.sprite.getWidth() + this.x - screen.getXPixel()) && (i < screen.getPixels().length) && xx < Screen.TILESIZE; i++) {
				int yy = Math.abs(y%Screen.TILESIZE);
				for (int j = this.y - screen.getYPixel(); (j < this.sprite.getHeight() + this.y - screen.getYPixel()) && (j < screen.getPixels()[i].length) && yy < Screen.TILESIZE; j++) {
					try { 
						screen.getPixels()[i][j] = pixels[xx][yy++];	// pixel-accuracy
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
						break;
					}
				}
				xx++;
			}
		}
	}
	public boolean isSolid () {
		return this.solid;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ArrayList<Direction> getDirections() {
		return directions;
	}

	public void addDirection(Direction direction) {
		this.directions.add(direction);
	}
	
	
}
