package taxigame.algorithms;

import java.util.List;

public class Edge {
	final Node from;
	final Node to;
	int startX, endX, startY, endY;
	int weight;
	
	List<Coordinate> coordinates;
	
	public Edge (Node from, Node to, int weight, List<Coordinate> coordinates) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.startX = from.x;
		this.startY = from.y;
		this.endX = to.x;
		this.endY = to.y;
		this.coordinates = coordinates;
	}
	
	public boolean contains(Coordinate coordinate) {
		if (coordinates.contains(coordinate)) {
			return true;
		}
		return false;
	}
	
	public boolean contains(int x, int y) {
		for (Coordinate coordinate : coordinates) {
			if (coordinate.getX() == x && coordinate.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean equals(Object secondObj) {
		if (secondObj instanceof Edge) {
			Edge secondEdge = (Edge) secondObj;
			if (this.from == secondEdge.getFrom() && this.to == secondEdge.getTo()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			throw new IllegalArgumentException("Parameter must be of type Edge");
		}	
	}
	public Node getFrom() {
		return this.from;
	}
	public Node getTo() {
		return this.to;
	}	
	public int getCurrentWeight() {
		calculateWeight();
		return this.weight;
	}

	private void calculateWeight() {
		// TODO Auto-generated method stub
		
	}
}
