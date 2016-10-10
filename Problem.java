import java.util.*;
import java.util.LinkedList;

abstract public class Problem {

	public State initalState;
	public LinkedList<Operator> operators;
	public LinkedList<State> stateSpace;

	abstract public boolean goalTest(State state);
	abstract public int costFunc(Node success);
	abstract public LinkedList<Node> applyOperators(Node node);

	public void enqueue(LinkedList<Node> nodes, LinkedList<Node> children, QingFun qingFun) {
		if (qingFun == QingFun.ENQUEUE_AT_END) {
			for (Node n : children) {
				nodes.addLast(n);
			}
		}

		else if (qingFun == QingFun.ENQUEUE_AT_FRONT) {
			for (Node n : children) {
				nodes.addFirst(n);
			}
		}

		else if (qingFun == QingFun.ORDERED_INSERT) {
			int i;
			for (Node n : children) {
				// Insertion sort
				for (i = 0; i < nodes.size(); i++) {
					Node tempNode = nodes.get(i);
					if (n.pathCost > tempNode.pathCost) {
						continue;
					}
					else {
						break;
					}
				}
				nodes.add(i, n);
			}
		}
	}
}
