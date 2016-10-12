import java.util.LinkedList;

public class GottaCatchEmAllProblem extends Problem {
  public int hatchSteps;
  public int pokeCount;
  public Maze maze;

  public GottaCatchEmAllProblem(GottaCatchEmAllState initialState, LinkedList<Operator> operators, LinkedList<State> stateSpace, Maze maze) {
    this.initialState = initialState;
    this.operators = operators;
    this.stateSpace = stateSpace;

    this.hatchSteps = (int) (Math.random() * ((20 - 5) + 1) + 5);
    this.maze = maze;
    this.pokeCount = this.maze.pokemonNumbers;
  }

  public boolean goalTest(State state) {
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) state;

    if ((currentState.x == maze.endCell.x) && (currentState.y == maze.endCell.y) && (hatchSteps == 0) &&
    (pokeCount == 0)) {
      return true;
    }

    return false;
  }

  public int costFunc(Node node) {
    //GottaCatchEmAllNode currentNode = (GottaCatchEmAllNode) node;

    int result = 0;

    while (node.parentNode != null) {
      result += node.pathCost;

      node = node.parentNode;
    }

    return result;
  }

  public LinkedList<Node> applyOperators(Node node) {
    //GottaCatchEmAllNode currentNode = (GottaCatchEmAllNode) node;

    // Test
    int x = ((GottaCatchEmAllState) node.state).x;
    int y = ((GottaCatchEmAllState) node.state).y;
    char orientation = ((GottaCatchEmAllState) node.state).orientation;
    System.out.println("TEST: " + x + ", " + y + " " + orientation);

    // Test
    System.out.println("HATCH_STEPS: " + hatchSteps);

    // Test
    System.out.println("POKEMONS: " + pokeCount);

    LinkedList<Node> children = new LinkedList<Node>();
    Node generatedNode = null;

    for (Operator operator : operators) {
      if (facingWall(node)) {
        switch (operator.operatorChar) {
          case 'F':
          case 'R': generatedNode = rotateRight(node); break;
          case 'L': generatedNode = rotateLeft(node); break;
        }
      }
      else {
        switch (operator.operatorChar) {
          case 'F': generatedNode = moveForward(node); break;
          case 'R': generatedNode = rotateRight(node); break;
          case 'L': generatedNode = rotateLeft(node); break;
        }
      }
      children.add(generatedNode);
    }
    return children;
  }

  public boolean facingWall(Node node) {
//    int m = maze.height;
//    int n = maze.width;
    int x = ((GottaCatchEmAllState) node.state).x;
    int y = ((GottaCatchEmAllState) node.state).y;
    char orientation = ((GottaCatchEmAllState) node.state).orientation;
//
//    return ((y == 0 && isBetween(x, 0, n-1) && orientation == 'N') ||
//            (x == 0 && isBetween(y, 0, m-1) && orientation == 'W') ||
//            (x == n-1 && isBetween(y, 0, m-1) && orientation == 'E') ||
//            (y == m-1 && isBetween(x, 0, n-1) && orientation == 'S'));

    char direction = ' ';
    switch (orientation) {
      case 'N': direction = 'U'; break;
      case 'E': direction = 'R'; break;
      case 'S': direction = 'D'; break;
      case 'W': direction = 'L'; break;
    }


    return !(this.maze.grid[x][y]).directions.contains("" + direction);
  }

  public boolean isBetween(int point, int p, int q) {
    return (point >= p && point <= q);
  }

  public Node moveForward(Node node) {
    if (hatchSteps > 0) {
      hatchSteps--;
    }

    checkPokemon(node);

    char orientation = ((GottaCatchEmAllState) node.state).orientation;
    Node newNode = new Node(node, node.depth+1, node.pathCost+1, 'F', (GottaCatchEmAllState) node.state);
    switch (orientation) {
      case 'N': ((GottaCatchEmAllState) newNode.state).y--; break;
      case 'E': ((GottaCatchEmAllState) newNode.state).x++; break;
      case 'S': ((GottaCatchEmAllState) newNode.state).y++; break;
      case 'W': ((GottaCatchEmAllState) newNode.state).x--; break;
    }
    return newNode;
  }

  public Node rotateRight(Node node) {
    char orientation = ((GottaCatchEmAllState) node.state).orientation;
    Node newNode = new Node(node, node.depth+1, node.pathCost+1, 'R', (GottaCatchEmAllState) node.state);
    switch (orientation) {
      case 'N': ((GottaCatchEmAllState) newNode.state).orientation = 'E'; break;
      case 'E': ((GottaCatchEmAllState) newNode.state).orientation = 'S'; break;
      case 'S': ((GottaCatchEmAllState) newNode.state).orientation = 'W'; break;
      case 'W': ((GottaCatchEmAllState) newNode.state).orientation = 'N'; break;
    }
    return newNode;
  }

  public Node rotateLeft(Node node) {
    char orientation = ((GottaCatchEmAllState) node.state).orientation;
    Node newNode = new Node(node, node.depth+1, node.pathCost+1, 'L', (GottaCatchEmAllState) node.state);
    switch (orientation) {
      case 'N': ((GottaCatchEmAllState) newNode.state).orientation = 'W'; break;
      case 'E': ((GottaCatchEmAllState) newNode.state).orientation = 'N'; break;
      case 'S': ((GottaCatchEmAllState) newNode.state).orientation = 'E'; break;
      case 'W': ((GottaCatchEmAllState) newNode.state).orientation = 'S'; break;
    }
    return newNode;
  }

  public void checkPokemon(Node node) {
    int x = ((GottaCatchEmAllState) node.state).x;
    int y = ((GottaCatchEmAllState) node.state).y;

    if (maze.pokemonFlag[x][y]) {
      pokeCount--;
      maze.pokemonFlag[x][y] = false;
    }
  }

  public static void main(String[] args) {

  }
}
