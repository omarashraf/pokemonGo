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
    if ((state.x == maze.endCell.x) && (state.y == maze.endCell.y) && (hatchSteps == 0) && 
    (maze.pokemonNumbers == 0)) { // check if all pokemons were caught
      return true;
    }

    return false;
  }

  public int costFunc(Node node) {
    int result = 0;

    while (node.parentNode != null) {
      result += node.pathCost;

      node = node.parentNode;
    }

    return result;
  }

  public LinkedList<Node> applyOperators(Node node) {
    LinkedList<Node> children = new LinkedList<Node>();
    Node generatedNode;
    for (Char operator : operators.operatorChar) {
      if (facingEdge(node)) {
        switch (operator) {
          case 'R': generatedNode = rotateRight(node); break;
          case 'L': generatedNode = rotateLeft(node); break;
        }
      }
      else {
        switch (operator) {
          case 'F': generatedNode = moveForward(node); break;
          case 'R': generatedNode = rotateRight(node); break;
          case 'L': generatedNode = rotateLeft(node); break;
        }
      }
      children.addFirst(generatedNode);
    }
    return children;
  }

  public boolean facingEdge(Node node) {
    int m = maze.height;
    int n = maze.width;
    int x = node.state.x;
    int y = node.state.y;
    char orientation = node.state.orientation;

    return ((x == 0 && isBetween(y, 0, n-1) && orientation == 'N') ||
            (y == 0 && isBetween(x, 0, m-1) && orientation == 'W') ||
            (y == n-1 && isBetween(x, 0, m-1) && orientation == 'E') ||
            (x == m-1 && isBetween(y, 0, n-1) && orientation == 'S'));
  }

  public boolean isBetween(int point, int p, int q) {
    return (point >= p && point <= q);
  }

  public Node moveForward(Node node) {
    char orientation = node.state.orientation;
    Node newNode = node;
    switch (orientation) {
      case 'N': newNode.state.y--; break;
      case 'E': newNode.state.x++; break;
      case 'S': newNode.state.y++; break;
      case 'W': newNode.state.x--; break;
    }
    return newNode;
  }

  public Node rotateRight(Node node) {
    char orientation = node.state.orientation;
    Node newNode = node;
    switch (orientation) {
      case 'N': newNode.state.orientation = 'E'; break;
      case 'E': newNode.state.orientation = 'S'; break;
      case 'S': newNode.state.orientation = 'W'; break;
      case 'W': newNode.state.orientation = 'N'; break;
    }
    return newNode;
  }

  public Node rotateLeft(Node node) {
    char orientation = node.state.orientation;
    Node newNode = node;
    switch (orientation) {
      case 'N': newNode.state.orientation = 'W'; break;
      case 'E': newNode.state.orientation = 'N'; break;
      case 'S': newNode.state.orientation = 'E'; break;
      case 'W': newNode.state.orientation = 'S'; break;
    }
    return newNode;
  }

  public static void main(String[] args) {

  }
}
