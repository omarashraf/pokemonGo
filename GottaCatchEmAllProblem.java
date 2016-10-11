import java.util.LinkedList;

public class GottaCatchEmAllProblem extends Problem {
  public int hatchSteps;
  public int pokeCount;
  public Maze maze;

  public GottaCatchEmAllProblem(GottaCatchEmAllState initialState, LinkedList<Operator> operators, LinkedList<State> stateSpace) {
    this.initialState = initialState;
    this.operators = operators;
    this.stateSpace = stateSpace;

    // randmoize hatchSteps
  }

  public boolean goalTest(State state) {
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) state;

    if ((currentState.x == maze.endCell.x) && (currentState.y == maze.endCell.y) && (hatchSteps == 0) &&
    (maze.pokemonNumbers == 0)) { // check if all pokemons were caught
      return true;
    }

    return false;
  }

  public int costFunc(Node node) {
    GottaCatchEmAllNode currentNode = (GottaCatchEmAllNode) node;

    int result = 0;

    while (currentNode.parentNode != null) {
      result += currentNode.pathCost;

      currentNode = (GottaCatchEmAllNode) currentNode.parentNode;
    }

    return result;
  }

  public LinkedList<Node> applyOperators(Node node) {
    GottaCatchEmAllNode currentNode = (GottaCatchEmAllNode) node;
    LinkedList<Node> children = new LinkedList<Node>();
    GottaCatchEmAllNode generatedNode = null;
    for (Operator operator : operators) {
      if (facingEdge(currentNode)) {
        switch (operator.operatorChar) {
          case 'R': generatedNode = rotateRight(currentNode); break;
          case 'L': generatedNode = rotateLeft(currentNode); break;
        }
      }
      else {
        switch (operator.operatorChar) {
          case 'F': generatedNode = moveForward(currentNode); break;
          case 'R': generatedNode = rotateRight(currentNode); break;
          case 'L': generatedNode = rotateLeft(currentNode); break;
        }
      }
      children.addFirst(generatedNode);
    }
    return children;
  }

  public boolean facingEdge(GottaCatchEmAllNode node) {
    int m = maze.height;
    int n = maze.width;
    int x = ((GottaCatchEmAllState) node.state).x;
    int y = ((GottaCatchEmAllState) node.state).y;
    char orientation = ((GottaCatchEmAllState) node.state).orientation;

    return ((x == 0 && isBetween(y, 0, n-1) && orientation == 'N') ||
            (y == 0 && isBetween(x, 0, m-1) && orientation == 'W') ||
            (y == n-1 && isBetween(x, 0, m-1) && orientation == 'E') ||
            (x == m-1 && isBetween(y, 0, n-1) && orientation == 'S'));
  }

  public boolean isBetween(int point, int p, int q) {
    return (point >= p && point <= q);
  }

  public GottaCatchEmAllNode moveForward(GottaCatchEmAllNode node) {
    char orientation = ((GottaCatchEmAllState) node.state).orientation;
    GottaCatchEmAllNode newNode = new GottaCatchEmAllNode(node, node.depth+1, node.pathCost+1, 'F', (GottaCatchEmAllState) node.state);
    switch (orientation) {
      case 'N': ((GottaCatchEmAllState) newNode.state).y--; break;
      case 'E': ((GottaCatchEmAllState) newNode.state).x++; break;
      case 'S': ((GottaCatchEmAllState) newNode.state).y++; break;
      case 'W': ((GottaCatchEmAllState) newNode.state).x--; break;
    }
    return newNode;
  }

  public GottaCatchEmAllNode rotateRight(GottaCatchEmAllNode node) {
    char orientation = ((GottaCatchEmAllState) node.state).orientation;
    GottaCatchEmAllNode newNode = new GottaCatchEmAllNode(node, node.depth+1, node.pathCost+1, 'R', (GottaCatchEmAllState) node.state);
    switch (orientation) {
      case 'N': ((GottaCatchEmAllState) newNode.state).orientation = 'E'; break;
      case 'E': ((GottaCatchEmAllState) newNode.state).orientation = 'S'; break;
      case 'S': ((GottaCatchEmAllState) newNode.state).orientation = 'W'; break;
      case 'W': ((GottaCatchEmAllState) newNode.state).orientation = 'N'; break;
    }
    return newNode;
  }

  public GottaCatchEmAllNode rotateLeft(GottaCatchEmAllNode node) {
    char orientation = ((GottaCatchEmAllState) node.state).orientation;
    GottaCatchEmAllNode newNode = new GottaCatchEmAllNode(node, node.depth+1, node.pathCost+1, 'L', (GottaCatchEmAllState) node.state);
    switch (orientation) {
      case 'N': ((GottaCatchEmAllState) newNode.state).orientation = 'W'; break;
      case 'E': ((GottaCatchEmAllState) newNode.state).orientation = 'N'; break;
      case 'S': ((GottaCatchEmAllState) newNode.state).orientation = 'E'; break;
      case 'W': ((GottaCatchEmAllState) newNode.state).orientation = 'S'; break;
    }
    return newNode;
  }

  public static void main(String[] args) {

  }
}
