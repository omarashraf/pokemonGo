public class GeneralSearchAlgorithm {

	public static Node generalSearch(Problem problem, QingFun qingFun) {  // QingFun is an Enum for the queueing strategies
		// Create a node for the initial state
		State initialState = problem.state;
		Node node = new Node(null, 0, 0, null, initialState);

		// Initialize queue, and add the node
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node);

		// Loop over the nodes in the queue
		for (int i = 0; i < nodes.size(); i++) {
			// Retrieve the node at the front
			Node n = nodes.remove(0);

			// Chcek if it passes the goal test, and return the node in case of success
			if (problem.goalTest(n.state)) {
				return n;
			}

			// If not, expand the node and add its children to the queue according to QingFun
			expand(nodes, n, problem, qingFun);
		}

		// If the queue is empty, return a failure
		if (nodes.isEmpty()) {
			return null;
		}
	}

	public static void expand(ArrayList<Node> nodes, Node node, Problem problem, QingFun qingFun) {
		// Apply the operators on the node
		ArrayList<Node> resultNodes = problem.applyOperators(node);

		if (qingFun == QingFun.ENQUEUE_AT_END) {
			for (Node n : resultNodes) {
				nodes.add(nodes.size());
			}
		}

		else if (qingFun == QingFun.ENQUEUE_AT_FRONT) {
			for (Node n : resultNodes) {
				nodes.add(0);
			}
		}

		else if (qingFun == QingFun.ORDERED_INSERT) {
			int i;
			for (Node n : resultNodes) {
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
				nodes.add(n, i);
			}
		}
	}
}