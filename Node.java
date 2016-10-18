import java.util.*;

public class Node implements Comparable<Node> {

	public Node parentNode;
	public int depth;
	public int pathCost;
	public char operator;
	public State state;
	public String fingerprint;
	public int heuristicCost;
	public int comparingFactor;
	public Node() {

	}

	public Node (Node node, int depth, int pathCost, char operator, State state) {
		this.parentNode = node;
		this.depth = depth;
		this.pathCost = pathCost;
		this.operator = operator;
		this.state = state;
		heuristicCost = 0;
		comparingFactor = -1;
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
		if (other.comparingFactor == -1) {
			if (this.pathCost > other.pathCost) return 1;
			else return -1;
		} 
		else if (other.comparingFactor == 0) {
			if (this.heuristicCost > other.heuristicCost) return 1;
			else return -1;
		}
		else {
			if (this.pathCost + this.heuristicCost > other.pathCost + other.heuristicCost) return 1;
			else return -1;
		}
	}
}
