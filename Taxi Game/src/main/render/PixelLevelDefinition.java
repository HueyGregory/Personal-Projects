package main.render;

import java.util.HashMap;

import main.algorithms.Direction;

public enum PixelLevelDefinition {
	Street(0xFFA8262B), // used for identifying edges
	RoadUpDown(0x0000_0001, Direction.up, Direction.down), RoadLeftRight(0x0000_0010, Direction.left, Direction.right),  // straight directions
	RoadUp(0x0000_0011, Direction.up), RoadDown(0x0000_0100, Direction.down), RoadLeft(0x0000_0101, Direction.left), RoadRight(0x0000_0111, Direction.right),	// dead-ends
	RoadUpDownLeft(0x0000_1000, Direction.up, Direction.down, Direction.left), RoadUpDownRight(0x0000_1001, Direction.up, Direction.down, Direction.right), RoadUpLeftRight(0x0000_1010, Direction.right, Direction.left, Direction.up), RoadDownLeftRight(0x0000_1011, Direction.right, Direction.left, Direction.down),	// T-junctions
	RoadUpLeft(0x0000_1100, Direction.up, Direction.left), RoadUpRight(0x0000_1101, Direction.up, Direction.right), RoadDownLeft(0x0000_1110, Direction.down, Direction.left), RoadDownRight(0x0000_1111, Direction.down, Direction.right),	// turns
	RoadAllDirections(0x0001_0000, Direction.up, Direction.down, Direction.left, Direction.right), // all 4 directions
	InsideVoidTile(-1),
	BuildingFront(0xFFA9A9A9), BuildingBack(0xFFA9A9A8)
	;
	
	private final int pixelColor;
	private final Direction[] directions;
	
	PixelLevelDefinition (int pixelColor, Direction... directions) {
		this.pixelColor = pixelColor;
		this.directions = directions;
	}
	
	public int getPixelColor() {
		return pixelColor;
	}

	public Direction[] getDirections() {
		return directions;
	}
	
}

