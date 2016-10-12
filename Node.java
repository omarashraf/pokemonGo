import java.util.*;

public class Node {

	public Node parentNode;
	public int depth;
	public int pathCost;
	public char operator;
	// make sure Object data type is corerct
	public State state;

	public Node() {

	}

	public Node (Node node, int depth, int pathCost, char operator, State state) {
		this.parentNode = node;
		this.depth = depth;
		this.pathCost = pathCost;
		this.operator = operator;
		this.state = state;
	}
}
