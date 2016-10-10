import java.util.*;

abstract public class Node {

	public Node parentNode;
	public int depth;
	public int pathCost;
	public char operator;
	// make sure Object data type is corerct
	public State state;

	public Node (Node parentNode, int depth, int pathCost, char operator, State state) {
		this.parentNode = parentNode;
		this.depth = depth;
		this pathCost = pathCost;
		this.operator = operator;
		this.state = state;
	}
}