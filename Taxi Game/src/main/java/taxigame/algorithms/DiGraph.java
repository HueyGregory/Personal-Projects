package taxigame.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import taxigame.render.PixelLevelDefinition;
import taxigame.render.sprites.SpriteSheet;


public class DiGraph {
	// use Bellman-Ford's algorithm for dynamic programming
	Set<Node> nodes = new HashSet<Node>();
	Set<Edge> edges = new HashSet<Edge>();
	
	public DiGraph (SpriteSheet sheet) {
		createDiGraph(sheet);
	}
	
	private void createDiGraph(SpriteSheet sheet) {
		// iterate through all the pixels. If the pixel is adjacent to another 
		int[] pixels = sheet.getPixels();
		boolean[] alreadyTouchedPixel = new boolean[pixels.length];
		for (int x = 0; x < sheet.getWidth(); x++) {
			for (int y = 0; y < sheet.getHeight(); y++) {
				if (alreadyTouchedPixel[x+y*sheet.getWidth()]) {
					continue;
				}
				if (pixels[x+y*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) {
					System.out.println("\n[DiGraph - createDiGraph()] x == " + x + "; y: " + y);
					AtomicInteger distance = new AtomicInteger(0);
					findConnectedNode(x, y, pixels, sheet, alreadyTouchedPixel, distance, null);
				}
				else {
					
				}
				alreadyTouchedPixel[x+y*sheet.getWidth()] = true;
			}
		}
	}
	
	private Node findConnectedNode(int currX, int currY, int[] pixels, SpriteSheet sheet, boolean[] alreadyVisitedPixel, AtomicInteger distance, List<Coordinate> coordinates) {
	//	System.out.printf("[DiGraph - findConnectedNode()] Entered findConnectedNode(%d, %d, distance: %d);\n", currX, currY, distance.get());
		// set the pixel to having been already touched
		alreadyVisitedPixel[currX+currY*sheet.getWidth()] = true;
		// gather all the adjacent routes, that have not been touched yet, into an array - pixelSlotsToVisit
		
		List<Integer[]> pixelSlotsToVisit = new ArrayList<Integer[]>(4); 
		int numOfAdjacent = findAdjacentPixels(pixelSlotsToVisit, currX, currY, pixels, sheet, alreadyVisitedPixel);
		if (numOfAdjacent == 2) {
			// continues in one direction, so it is part of the same edge and is not a new node
			distance.getAndIncrement();
			if (coordinates == null) {
				coordinates = new ArrayList<Coordinate>();
			}
			coordinates.add(new Coordinate(currX, currY));
			if (pixelSlotsToVisit.size() == 0) {
				System.out.println("[DiGraph - findConnectedNode()] Error: currX: " + currX + "; currY: " + currY + "; Both sides have already been visited && pixelSlotsToVisit.size() == 0");
			}
			if (pixelSlotsToVisit.size() == 2) {
				Node firstNode = getNode(pixelSlotsToVisit.get(0)[0], pixelSlotsToVisit.get(0)[1]);
				Node secondNode = getNode(pixelSlotsToVisit.get(1)[0], pixelSlotsToVisit.get(1)[1]);
				if ((firstNode != null) && (secondNode != null)) {
					coordinates.add(new Coordinate(firstNode.x, firstNode.y));
					coordinates.add(new Coordinate(secondNode.x, secondNode.y));
					coordinates.add(new Coordinate(currX, currY));
					Edge edge = new Edge(firstNode, secondNode, 3, coordinates);
					firstNode.addEdge(edge);
					addEdge(edge);
					Edge oppositeEdge = new Edge(secondNode, firstNode, 3, coordinates);
					secondNode.addEdge(oppositeEdge);
					addEdge(oppositeEdge);
					Random rand = new Random();
					if (rand.nextBoolean())
						return firstNode;
					else 
						return secondNode;
				}
				for (int index = 0; index < pixelSlotsToVisit.size(); index++) {
					if (getNode(pixelSlotsToVisit.get(index)[0], pixelSlotsToVisit.get(index)[1]) != null) {
						pixelSlotsToVisit.remove(index);
					}
				}
			}
			return findConnectedNode(pixelSlotsToVisit.get(0)[0], pixelSlotsToVisit.get(0)[1], pixels, sheet, alreadyVisitedPixel, distance, coordinates);
			
		}
		else {
			// recursively pass through the pixelSlotstoVisit array, visiting all the possible edges from this node
			// set a new distance variable for each edge to calculate each edges distance
			// end of one edge and this spot is a node
			Node fromNode;
			if((fromNode = getNode(currX, currY)) == null) {
				fromNode = new Node(currX, currY);
				nodes.add(fromNode);
			}
			printThePaths(fromNode, pixelSlotsToVisit);
			for (Integer[] path : pixelSlotsToVisit) {
				AtomicInteger newDistance = new AtomicInteger(0);
				coordinates = new ArrayList<Coordinate>();
				coordinates.add(new Coordinate(fromNode.x, fromNode.y));
				Node toNode = findConnectedNode(path[0], path[1], pixels, sheet, alreadyVisitedPixel, newDistance, coordinates);
				Edge edge = new Edge(fromNode, toNode, newDistance.get(), coordinates);
				fromNode.addEdge(edge);
				addEdge(edge);
				Edge oppositeEdge = new Edge(toNode, fromNode, newDistance.get(), coordinates);
				toNode.addEdge(oppositeEdge);
				addEdge(oppositeEdge);
				
			}
		//	printNodesAndEdges();
			return fromNode;		
		}		
	}
		

	private void printThePaths(Node fromNode, List<Integer[]> pixelSlotsToVisit) {
		System.out.println("Number of paths for node: x: " + fromNode.x + "; y: " + fromNode.y + "; is: " + pixelSlotsToVisit.size());
		for (Integer[] path : pixelSlotsToVisit) {
			System.out.println("path is: x == " + path[0] + " y == " + path[1]);
		}
	}

	private void printNodesAndEdges() {
		System.out.printf("\tx\ty\t# edges\n");
		for (Node node : nodes) {
			System.out.printf("Node: %d\t%d\t%d\n", node.x, node.y, node.edges.size());
			System.out.printf("\t%d Edges:\tstartX\tstartY\tendX\tendY\tdistance\n", node.edges.size());
			for (Edge edge : node.edges) {
				System.out.printf("\t\t\t%d\t%d\t%d\t%d\t%d\n", edge.startX, edge.startY, edge.endX, edge.endY, edge.weight);
			}
			System.out.println();
		}
	}

	private int findAdjacentPixels(List<Integer[]> pixelSlotsToVisit, int currX, int currY, int[] pixels, SpriteSheet sheet, boolean[] alreadyTouchedPixel) {
		int numOfAdjacent = 0;
		try {
			if(currY < sheet.getHeight()) {
				if (pixels[currX+(currY+1)*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) {
					if (alreadyTouchedPixel[currX+(currY+1)*sheet.getWidth()] == false || getNode(currX,(currY+1)) != null) {
						pixelSlotsToVisit.add(new Integer[] { currX, currY+1  });
					}
					numOfAdjacent++;
				}
			}
			if (currY > 0) {
				if (pixels[currX+(currY-1)*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) {
					if (alreadyTouchedPixel[currX+(currY-1)*sheet.getWidth()] == false || getNode(currX,(currY-1)) != null) {
						pixelSlotsToVisit.add(new Integer[] { currX, currY-1  });		
					}
					numOfAdjacent++;
				}
			}
			if(currX < sheet.getWidth()) {
				if (pixels[(currX+1)+currY*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) {
					if (alreadyTouchedPixel[(currX+1)+currY*sheet.getWidth()] == false || getNode(currX+1,currY) != null) {
						pixelSlotsToVisit.add(new Integer[] { currX+1, currY  });
					}
					numOfAdjacent++;
				}
			}
			if (currX > 0) {
				if (pixels[(currX-1)+currY*sheet.getWidth()] == PixelLevelDefinition.Street.getPixelColor()) {
					if (alreadyTouchedPixel[(currX-1)+currY*sheet.getWidth()] == false || getNode(currX-1,currY) != null) {
						pixelSlotsToVisit.add(new Integer[] { currX-1, currY  });		
					}
					numOfAdjacent++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numOfAdjacent;
	}


	
	
	public void addNode(int x, int y) {
		nodes.add(new Node(x, y));
	}
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	
/*	public void addEdge(int startX, int endX, int startY, int endY) {
		Node fromNode = getNode(startX, startY);
		Node toNode = getNode(endX, endY);
		Edge sharedEdge = new Edge(fromNode, toNode);
		fromNode.addEdge(sharedEdge);
		toNode.addEdge(sharedEdge);
		
	}
	*/
	public Node getNode(int x, int y) {
		for (Node node: nodes) {
			if (node.x == x && node.y == y) {
				return node;
			}
		}
		return null;
	}
	
	public Edge findEdge(Coordinate coordinate) {
		for (Edge edge: edges) {
			if (edge.contains(coordinate)) {
				return edge;
			}
		}
		return null;
	}
	
	public Edge findEdge(int x, int y) {
		for (Edge edge: edges) {
			if (edge.contains(x, y)) {
				return edge;
			}
		}
		return null;
	}
	
	
	
	
}
