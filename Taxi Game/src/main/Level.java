package main;

import main.entities.sprites.SpriteSheet;

public class Level {

	public static Level level1 = new Level(new SpriteSheet("Resources\\Levels\\Level1.png"));
	
	private int[] sheetPixels;	// each "pixel" in sheetPixels represents a tile
	private SpriteSheet sheet;
	
	public Level (SpriteSheet sheet) {
		this.sheet = sheet;
		this.sheetPixels = sheet.getPixels();
	}
	
	public int[][] initializeMap(int[][] map) {		// each pixel on the level spriteTile sheet represents a tile
		map = new int[this.sheet.getWidth()][this.sheet.getHeight()];
		for (int x = 0; x < this.sheet.getWidth(); x++) {
			for (int y = 0; y < this.sheet.getHeight(); y++) {
				switch(sheetPixels[x+y*sheet.getWidth()]) {
				case 0xFFA9A9A9: 	map[x][y] = 1; 	break;	// grey - tileRoadLane
				case 0xFFA8262B:	map[x][y] = 2;	break;	// red - tileRoadWithSideWalkUp
				case 0xFFA52C4B:	map[x][y] = 3;	break;	// red - tileRoadWithSideWalkDown
				default:			map[x][y] = -1;	break;
				}
			}
		}
		return map;
	}

	
	
}
