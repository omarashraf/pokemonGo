import java.util.*;

abstract public class Node {

	public Node parentNode;
	public int depth;
	public int pathCost;
	public char operator;
	// make sure Object data type is corerct
	public State state;
}
