package taxigame.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import taxigame.algorithms.Direction;

public class TrieNode {

	private Map<Direction, TrieNode> children = new HashMap<Direction, TrieNode>();
	private TrieNode parent;
	private PixelLevelDefinition content;
	private static TrieNode root;
	
	public TrieNode(TrieNode parent, PixelLevelDefinition content, Direction direction) {
		this.parent = parent;
		this.content = content;
		if (parent != null) parent.setChild(direction, this);
	}
	
	TrieNode getChild (Direction direction) { return this.children.get(direction); }
	PixelLevelDefinition getContent () { return this.content; }
	
	void setChild (Direction direction, TrieNode child) {
		if (this.children.put(direction, child) != null) {
			throw new RuntimeException();
		}
	}
	
	public static PixelLevelDefinition find (List<Direction>directions) {
		TrieNode current = TrieNode.root;
		for (Direction direction : directions) {
			current = current.getChild(direction);
		}
		return current.getContent();
	}
	
	static {
		TrieNode.root = new TrieNode(null, PixelLevelDefinition.Street, null);
		
		// dead-ends
		TrieNode upDirection = new TrieNode(TrieNode.root, PixelLevelDefinition.RoadUp, Direction.up);
		TrieNode downDirection = new TrieNode(TrieNode.root, PixelLevelDefinition.RoadDown, Direction.down);
		TrieNode leftDirection = new TrieNode(TrieNode.root, PixelLevelDefinition.RoadLeft, Direction.left);
		new TrieNode(TrieNode.root, PixelLevelDefinition.RoadRight, Direction.right);
		
		// straight directions
		TrieNode upDownDirection = new TrieNode(upDirection, PixelLevelDefinition.RoadUpDown, Direction.down);
		new TrieNode(leftDirection, PixelLevelDefinition.RoadLeftRight, Direction.right);

		// turns
		TrieNode upLeftDirection = new TrieNode(upDirection, PixelLevelDefinition.RoadUpLeft, Direction.left);
		new TrieNode(upDirection, PixelLevelDefinition.RoadUpRight, Direction.right);
		TrieNode downLeftDirection = new TrieNode(downDirection, PixelLevelDefinition.RoadDownLeft, Direction.left);
		new TrieNode(downDirection, PixelLevelDefinition.RoadDownRight, Direction.right);
		
		// T-Junctions
		TrieNode upDownLeftDirection = new TrieNode(upDownDirection, PixelLevelDefinition.RoadUpDownLeft, Direction.left);
		new TrieNode(upDownDirection, PixelLevelDefinition.RoadUpDownRight, Direction.right);
		new TrieNode(upLeftDirection, PixelLevelDefinition.RoadUpLeftRight, Direction.right);
		new TrieNode(downLeftDirection, PixelLevelDefinition.RoadDownLeftRight, Direction.right);

		// All 4-Ways
		new TrieNode(upDownLeftDirection, PixelLevelDefinition.RoadAllDirections, Direction.right);
	}
	
}
