package taxigame.algorithms;

import taxigame.entities.Building;

public class Coordinate {
	
	private int x, y;
	private Direction direction;
	private Building building;	// null if no building there
	private boolean solid;		// true - vehicle can not pass through it
	private Edge edge;
	
	public Coordinate (int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public Coordinate (int x, int y, Direction direction) {
		this.setX(x);
		this.setY(y);
		this.direction = direction;
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

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Building getBuildingAtLoc() {
		return building;
	}

	public void setBuildingAtLoc(Building buildingAtLoc) {
		this.building = buildingAtLoc;
	}

	public Edge getEdge() {
		return edge;
	}

	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
		
	
}
