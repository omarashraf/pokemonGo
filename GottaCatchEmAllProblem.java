public class GottaCatchEmAllProblem extends Problem {
  public int hatchSteps;
  public int pokeCount;
  public Maze maze;

  public GottaCatchEmAllProblem(State initalState, LinkedList<Char> operators, LinkedList<State> stateSpace) {
    this.initialState = initialState;
    this.operators = operators;
    this.stateSpace = stateSpace;

    // randmoize hatchSteps
  }

  public boolean goalTest(State state) {
    if ((state.x == maze.endCell.x) && (state.y == maze.endCell.y) && (hatchSteps == 0) && (pokeCount == 0)) { // check if all pokemons were caught
      return true;
    }

    return false;
  }

  public int costFunc(Node node) {
    int result = 0;

    while (node.parent != null) {
      result += node.pathCost;

      node = node.parent;
    }

    return result;
  }

  public LinkedList<Node> applyOperators(Node node) {
    for (Char operator : operators) {
      if (facingEdge(node)) {
        switch (operator) {
          case 'R': rotateRight(Node node); break;
          case 'L': rotateLeft(Node node); break;
        }
      }
      else {
        switch (operator) {
          case 'F': moveForward(Node node); break;
          case 'R': rotateRight(Node node); break;
          case 'L': rotateLeft(Node node); break;
        }
      }
    }
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

  public void moveForward(Node node) {
    char orientation = node.state.orientation;

    switch (orientation) {
      case 'N': node.state.y--; break;
      case 'E': node.state.x++; break;
      case 'S': node.state.y++; break;
      case 'W': node.state.x--; break;
    }
  }

  public void rotateRight(Node node) {
    char orientation = node.state.orientation;

    switch (orientation) {
      case 'N': node.state.orientation = 'E'; break;
      case 'E': node.state.orientation = 'S'; break;
      case 'S': node.state.orientation = 'W'; break;
      case 'W': node.state.orientation = 'N'; break;
    }
  }

  public void rotateLeft(Node node) {
    char orientation = node.state.orientation;

    switch (orientation) {
      case 'N': node.state.orientation = 'W'; break;
      case 'E': node.state.orientation = 'N'; break;
      case 'S': node.state.orientation = 'E'; break;
      case 'W': node.state.orientation = 'S'; break;
    }
  }
}
