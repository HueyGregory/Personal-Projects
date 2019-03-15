package main.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.algorithms.DiGraph;
import main.algorithms.Direction;
import main.entities.Building;
import main.render.sprites.SpriteSheet;

public class Level {

	public static Level level1 = new Level(new SpriteSheet("Resources\\Levels\\Level1.png"));
	
	private int[] sheetPixels;	// each "pixel" in sheetPixels represents a tile
	private SpriteSheet sheet;
	private DiGraph diGraph;
	private Random random;
	
	public Level (SpriteSheet sheet) {
		this.sheet = sheet;
		this.random = new Random();
		this.sheetPixels = sheet.getPixels();
	//	new DiGraph(this.sheet);
	}
	
	public PixelLevelDefinition[][] initializeMap(PixelLevelDefinition[][] map) {		// each pixel on the level spriteTile sheet represents a tile
		map = new PixelLevelDefinition[this.sheet.getWidth()][this.sheet.getHeight()];
		for (int x = 0; x < this.sheet.getWidth(); x++) {
			for (int y = 0; y < this.sheet.getHeight(); y++) {
				int i = sheetPixels[x+y*sheet.getWidth()];
				if (i == PixelLevelDefinition.BuildingFront.getPixelColor()) {
					map[x][y] = Building.getRandomBuildingPixel();
				} else if (i == PixelLevelDefinition.Street.getPixelColor()) {
					map = setRoad(map, x, y);
				} else {
					map[x][y] = PixelLevelDefinition.InsideVoidTile;
				}
			}
		}
		this.setDiGraph(new DiGraph(this.sheet));
		return map;
	}
	
	private PixelLevelDefinition[][] setRoad(PixelLevelDefinition[][] map, int x, int y) {
		// all possible permutations for roads
		// use a trie
		List<Direction> directions = new ArrayList<Direction>(4);
		if (sheetPixels[x+(y-1)*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) directions.add(Direction.up);
		if (sheetPixels[x+(y+1)*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) directions.add(Direction.down);
		if (sheetPixels[(x-1)+y*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) directions.add(Direction.left);
		if (sheetPixels[(x+1)+y*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) directions.add(Direction.right);
		
		map[x][y] = TrieNode.find(directions);
		return map;
	}

	public DiGraph getDiGraph() {
		return diGraph;
	}

	public void setDiGraph(DiGraph diGraph) {
		this.diGraph = diGraph;
	}

	
	
}
