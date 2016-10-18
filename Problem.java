import java.util.*;
import java.util.LinkedList;

abstract public class Problem {

	public State initialState;
	public LinkedList<Operator> operators;
	// public LinkedList<State> stateSpace;
	public Hashtable<String, Integer> stateSpace;

	abstract public boolean goalTest(State state);
	abstract public int costFunc(Node success);
	abstract public LinkedList<Node> applyOperators(Node node);

	public State getInitialState() {
		return initialState;
	}

	public void enqueue(LinkedList<Node> nodes, LinkedList<Node> children, QingFun qingFun) {
		if (qingFun == QingFun.ENQUEUE_AT_END) {
			for (Node n : children) {
				if (stateSpace.containsKey(n.fingerprint)) {
					if (stateSpace.get(n.fingerprint).intValue() > n.pathCost) {
						stateSpace.remove(n.fingerprint);
						stateSpace.put(n.fingerprint, n.pathCost);
						nodes.addLast(n);
					}
				}

				else {
					stateSpace.put(n.fingerprint, n.pathCost);
					nodes.addLast(n);
				}
			}
		}

		else if (qingFun == QingFun.ENQUEUE_AT_FRONT_DF) {
			for (Node n : children) {
				if (stateSpace.containsKey(n.fingerprint)) {
					if (stateSpace.get(n.fingerprint).intValue() > n.pathCost) {
						stateSpace.remove(n.fingerprint);
						stateSpace.put(n.fingerprint, n.pathCost);
						nodes.addFirst(n);
					}
				}

				else {
					stateSpace.put(n.fingerprint, n.pathCost);
					nodes.addFirst(n);
				}
			}
		}

		else if (qingFun == QingFun.ENQUEUE_AT_FRONT_ID) {
			for (Node n : children) {
				if (n.depth <= GeneralSearchAlgorithm.depth) {
					if (stateSpace.containsKey(n.fingerprint)) {
						if (stateSpace.get(n.fingerprint).intValue() > n.pathCost) {
							stateSpace.remove(n.fingerprint);
							stateSpace.put(n.fingerprint, n.pathCost);
							nodes.addFirst(n);
						}
					}

					else {
						stateSpace.put(n.fingerprint, n.pathCost);
						nodes.addFirst(n);
					}
				}
			}
		}

		else if (qingFun == QingFun.ORDERED_INSERT) {
			for (Node n : children) {
				if (stateSpace.containsKey(n.fingerprint)) {
					if (stateSpace.get(n.fingerprint).intValue() > n.pathCost) {
						stateSpace.remove(n.fingerprint);
						stateSpace.put(n.fingerprint, n.pathCost);
						nodes.add(n);
					}
				}

				else {
					stateSpace.put(n.fingerprint, n.pathCost);
					nodes.add(n);
				}
			}

			MergeSort.mergeSort(nodes);

//			int i;
//			for (Node n : children) {
//				// Insertion sort
//				for (i = 0; i < nodes.size(); i++) {
//					Node tempNode = nodes.get(i);
//					if (n.pathCost >= tempNode.pathCost) {
//						continue;
//					}
//					else {
//						break;
//					}
//				}
//				nodes.add(i, n);
//			}
		}
	}
}
