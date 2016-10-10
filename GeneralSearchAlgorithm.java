import java.util.LinkedList;

public class GeneralSearchAlgorithm {

	public static Node generalSearch(Problem problem, QingFun qingFun) {  // QingFun is an Enum for the queueing strategies
		// Create a node for the initial state
		State initialState = problem.initalState;
		Node node = new Node(null, 0, 0, '', initialState);

		// Initialize queue, and add the node
		LinkedList<Node> nodes = new LinkedList<Node>();
		nodes.add(node);

		// Loop over the nodes in the queue
		for (int i = 0; i < nodes.size(); i++) {
			// Retrieve the node at the front
			Node n = nodes.removeFirst();

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

	public static void expand(LinkedList<Node> nodes, Node node, Problem problem, QingFun qingFun) {
		// Apply the operators on the node to get the children
		LinkedList<Node> children = problem.applyOperators(node);

		// Add the children to the queue according to the QingFun
		problem.enqueue(nodes, children, qingFun);
	}
}
