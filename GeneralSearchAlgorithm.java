import java.util.Hashtable;
import java.util.LinkedList;

public class GeneralSearchAlgorithm {

	public static int depth;
	public static int depthSoFar;
	static int expansionNumbers = 0;
	public static void startSearch(Problem problem, QingFun qingFun) {
		if (qingFun == QingFun.ENQUEUE_AT_FRONT_ID) {
			for (depth = 0; depth < Double.POSITIVE_INFINITY; depth++) {
				depthSoFar = -1;
				problem.stateSpace = new Hashtable<String, Integer>();
				Node n = generalSearch(problem, qingFun);

				if (n != null) {
					break;
				}
			}
		}

		else {
			generalSearch(problem, qingFun);
		}
	}

	public static Node generalSearch(Problem problem, QingFun qingFun) {  // QingFun is an Enum for the queueing strategies
		// Create a node for the initial state
		State initialState = problem.getInitialState();
		Node node = new Node(null, 0, 0, ' ', initialState);

		// Initialize queue, and add the node
		LinkedList<Node> nodes = new LinkedList<Node>();
		nodes.add(node);
		problem.stateSpace.put(node.fingerprint, node.pathCost);

		// Loop over the nodes in the queue
		while(!nodes.isEmpty()) {
			// Retrieve the node at the front
			Node n = nodes.removeFirst();

			// Check if it passes the goal test, and return the node in case of success
			if (problem.goalTest(n.state)) {
				// Test
				System.out.println("NODE_OP: " + n.operator);
				System.out.println("NODE_PARENT: " + ((GottaCatchEmAllState)n.parentNode.state).x + ", " + ((GottaCatchEmAllState)n.parentNode.state).y + ", " + ((GottaCatchEmAllState)n.parentNode.state).orientation);
				System.out.println("NODE_POKES: " + ((GottaCatchEmAllState)n.parentNode.state).pokeCount);
				return n;
			}

			if (qingFun == QingFun.ENQUEUE_AT_FRONT_ID) {
				depthSoFar++;
				if (depthSoFar == depth) break;
			}

			// If not, expand the node and add its children to the queue according to QingFun
			expand(nodes, n, problem, qingFun);
		}

		return null;
	}

	public static void expand(LinkedList<Node> nodes, Node node, Problem problem, QingFun qingFun) {
		// Apply the operators on the node to get the children
		LinkedList<Node> children = problem.applyOperators(node);

		// Add the children to the queue according to the QingFun
		problem.enqueue(nodes, children, qingFun);
		expansionNumbers++;
		System.out.println("Expansion Numbers: " + expansionNumbers);
		
	}
}
