package main.entities;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import main.GameLoop;
import main.algorithms.Coordinate;
import main.algorithms.Edge;
import main.algorithms.Node;
import main.render.PixelLevelDefinition;
import main.render.Screen;
import main.render.sprites.SpriteSheet;
import main.render.sprites.SpriteTile;

public class Building extends Entity {

	public static SpriteTile[] Buildings = new SpriteTile[] {
			new SpriteTile(0, 0, Screen.TILESIZE, Screen.TILESIZE, SpriteSheet.buildings),	//BuildingFront;
			new SpriteTile(1, 0, Screen.TILESIZE, Screen.TILESIZE, SpriteSheet.buildings), 	// BuildingBack
			
	};
	
	private static final PixelLevelDefinition[] BuildingPixels = new PixelLevelDefinition[] {
			PixelLevelDefinition.BuildingFront, PixelLevelDefinition.BuildingBack 	
	};
	private static final Random rand = new Random();
	
	private Set<Edge> inEdges;
	private Node closestNode;
	private int distanceToNode;
	private Coordinate coordinate;
	
	public Building (int x, int y) {
		if (x < 0 || y < 0) throw new RuntimeException();
		coordinate = new Coordinate(x, y);
		coordinate.setBuildingAtLoc(this);
		inEdges = new HashSet<Edge>();
		addEdges(coordinate);
	}

	private void addEdges(Coordinate coordinate) {
		if (coordinate == null) throw new RuntimeException();
		if ((coordinate.getX() < GameLoop.map.length - 1) && GameLoop.map[coordinate.getX()+1][coordinate.getY()] == PixelLevelDefinition.Street) {
			this.inEdges.add(GameLoop.getCurrentLevel().getDiGraph().findEdge(coordinate.getX()+1, coordinate.getY()));
		}
		if ((coordinate.getX() > 0) && GameLoop.map[coordinate.getX()-1][coordinate.getY()] == PixelLevelDefinition.Street) {
			this.inEdges.add(GameLoop.getCurrentLevel().getDiGraph().findEdge(coordinate.getX()-1, coordinate.getY()));
		}
		if ((coordinate.getY() < GameLoop.map[coordinate.getX()].length - 1) && GameLoop.map[coordinate.getX()][coordinate.getY()+1] == PixelLevelDefinition.Street) {
			this.inEdges.add(GameLoop.getCurrentLevel().getDiGraph().findEdge(coordinate.getX(), coordinate.getY()+1));
		}
		if ((coordinate.getY() > 0) && GameLoop.map[coordinate.getX()][coordinate.getY()-1] == PixelLevelDefinition.Street) {
			this.inEdges.add(GameLoop.getCurrentLevel().getDiGraph().findEdge(coordinate.getX(), coordinate.getY()-1));
		}
		
	}
	
	public static PixelLevelDefinition getRandomBuildingPixel() {
		return BuildingPixels[rand.nextInt(BuildingPixels.length)];
	}
	public static int getBuildingPixel(int index) {
		if (index >= BuildingPixels.length || index < 0) throw new RuntimeException();
		return BuildingPixels[index].getPixelColor();
	}
	
	
	
}
