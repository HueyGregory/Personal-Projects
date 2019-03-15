package main.algorithms;

import java.util.HashSet;
import java.util.Set;

public class Node {
	Set<Edge> edges = new HashSet<Edge>();
	int x, y;
	
	public Node (int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	public boolean equals(Object secondObj) {
		if (secondObj instanceof Node) {
			Node secondNode = (Node) secondObj;
			if (this.x == secondNode.x && this.y == secondNode.y) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			throw new IllegalArgumentException("Parameter must be of type Node");
		}
		
	}
	public Set<Edge> getEdges() {
		return this.edges;
	}
	
}