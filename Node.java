import java.util.*;

public class Node implements Comparable<Node> {

	public Node parentNode;
	public int depth;
	public int pathCost;
	public char operator;
	public State state;
	public String fingerprint;

	public Node() {

	}

	public Node (Node node, int depth, int pathCost, char operator, State state) {
		this.parentNode = node;
		this.depth = depth;
		this.pathCost = pathCost;
		this.operator = operator;
		this.state = state;

		setFingerprint();
	}

	public void setFingerprint() {
		GottaCatchEmAllState thisState = (GottaCatchEmAllState) state;
		int x = thisState.x;
		int y = thisState.y;
		char orientation = thisState.orientation;
		int hatchSteps = thisState.hatchSteps;
		int pokeCount = thisState.pokeCount;

		fingerprint = x + ", " + y + ", " + orientation + ", " + hatchSteps + ", " + pokeCount;
	}

	public int compareTo(Node other) { // Will be modified for heuristics
		if (this.pathCost > other.pathCost) return 1;
		else return -1;
	}
}
