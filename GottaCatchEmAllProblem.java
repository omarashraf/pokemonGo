import java.util.Hashtable;
import java.util.LinkedList;


public class GottaCatchEmAllProblem extends Problem {
  public Maze maze;

  public GottaCatchEmAllProblem(GottaCatchEmAllState initialState, LinkedList<Operator> operators, Hashtable<String, Integer> stateSpace, Maze maze) {
    this.initialState = initialState;
    this.operators = operators;
    this.stateSpace = stateSpace;
    this.maze = maze;
  }

  public boolean goalTest(State state) {
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) state;

    checkPokemon(state);

    // Test
    System.out.println("X: " + currentState.x + ", Y: " + currentState.y + ", O: " + currentState.orientation + ", H: " + currentState.hatchSteps + ", P: " + currentState.pokeCount);

    if ((currentState.x == maze.endCell.x) && (currentState.y == maze.endCell.y) && (currentState.hatchSteps == 0) &&
    (currentState.pokeCount == 0)) {
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
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) node.state;

    //Test
    // maze.displayMaze(x, y);
    System.out.println("TEST: " + currentState.x + ", " + currentState.y + " " + currentState.orientation);
    System.out.println("HATCH_STEPS: " + currentState.hatchSteps);
    System.out.println("POKEMONS: " + currentState.pokeCount);
    System.out.println();

    LinkedList<Node> children = new LinkedList<Node>();

    for (Operator operator : operators) {
      Node generatedNode = null;

      if (facingWall(node)) {
        switch (operator.operatorChar) {
          case 'R': generatedNode = rotateRight(node); break;
          case 'L': generatedNode = rotateLeft(node); break;
          default: break;
        }
      }

      else {
        switch (operator.operatorChar) {
          case 'F': generatedNode = moveForward(node); break;
          case 'R': generatedNode = rotateRight(node); break;
          case 'L': generatedNode = rotateLeft(node); break;
        }
      }

      if (generatedNode != null)
        children.add(generatedNode);
    }
    return children;
  }

  public boolean facingWall(Node node) {
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) node.state;

    int x = currentState.x;
    int y = currentState.y;
    char orientation = currentState.orientation;
    char direction = ' ';

    switch (orientation) {
      case 'N': direction = 'U'; break;
      case 'E': direction = 'R'; break;
      case 'S': direction = 'D'; break;
      case 'W': direction = 'L'; break;
    }

    return !(this.maze.grid[x][y]).directions.contains("" + direction);
  }

  public Node moveForward(Node node) {
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) node.state;

    LinkedList<Cell> pokemonLocs = new LinkedList<Cell>();
    for (Cell cell : currentState.pokemonLocs) {
      pokemonLocs.add(cell);
    }

    GottaCatchEmAllState newState = new GottaCatchEmAllState(currentState.x, currentState.y, currentState.orientation, currentState.hatchSteps-1, currentState.pokeCount, pokemonLocs);

    char orientation = currentState.orientation;

    switch (orientation) {
      case 'N': newState.y--; break;
      case 'E': newState.x++; break;
      case 'S': newState.y++; break;
      case 'W': newState.x--; break;
    }

    Node newNode = new Node(node, node.depth+1, node.pathCost+2, 'F', newState);

    return newNode;
  }

  public Node rotateRight(Node node) {
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) node.state;

    LinkedList<Cell> pokemonLocs = new LinkedList<Cell>();
    for (Cell cell : currentState.pokemonLocs) {
      pokemonLocs.add(cell);
    }

    GottaCatchEmAllState newState = new GottaCatchEmAllState(currentState.x, currentState.y, currentState.orientation, currentState.hatchSteps, currentState.pokeCount, pokemonLocs);

    char orientation = currentState.orientation;

    switch (orientation) {
      case 'N': newState.orientation = 'E'; break;
      case 'E': newState.orientation = 'S'; break;
      case 'S': newState.orientation = 'W'; break;
      case 'W': newState.orientation = 'N'; break;
    }

    Node newNode = new Node(node, node.depth+1, node.pathCost+1, 'R', newState);

    return newNode;
  }

  public Node rotateLeft(Node node) {
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) node.state;

    LinkedList<Cell> pokemonLocs = new LinkedList<Cell>();
    for (Cell cell : currentState.pokemonLocs) {
      pokemonLocs.add(cell);
    }

    GottaCatchEmAllState newState = new GottaCatchEmAllState(currentState.x, currentState.y, currentState.orientation, currentState.hatchSteps, currentState.pokeCount, pokemonLocs);

    char orientation = currentState.orientation;

    switch (orientation) {
      case 'N': newState.orientation = 'W'; break;
      case 'E': newState.orientation = 'N'; break;
      case 'S': newState.orientation = 'E'; break;
      case 'W': newState.orientation = 'S'; break;
    }

    Node newNode = new Node(node, node.depth+1, node.pathCost+1, 'L', newState);

    return newNode;
  }

  public void checkPokemon(State state) {
    GottaCatchEmAllState currentState = (GottaCatchEmAllState) state;

    int x = currentState.x;
    int y = currentState.y;

    for (int i = 0; i < currentState.pokemonLocs.size(); i++) {
      Cell cell = currentState.pokemonLocs.get(i);

      int pokeX = cell.x;
      int pokeY = cell.y;

      if (x == pokeX && y == pokeY) {
        currentState.pokemonLocs.remove(i);
        currentState.pokeCount = currentState.pokemonLocs.size();
        break;
      }
    }
  }

  public void enqueue(LinkedList<Node> nodes, LinkedList<Node> children, QingFun qingFun) {
    if (qingFun == QingFun.ENQUEUE_AT_END || qingFun == QingFun.ENQUEUE_AT_FRONT_DF
        || qingFun == QingFun.ENQUEUE_AT_FRONT_ID || qingFun == QingFun.ORDERED_INSERT) {
      super.enqueue(nodes, children, qingFun);
    }

    else if (qingFun == QingFun.HEURISTIC_ONE) {
      int [] nodesHeuristicCosts = new int [children.size()];

      for (int i = 0; i < children.size(); i++) {
        nodesHeuristicCosts[i] = Math.abs(maze.endCell.x
                                      - ((GottaCatchEmAllState)(children.get(i)).state).x)
                               + Math.abs(maze.endCell.y
                                      - ((GottaCatchEmAllState)(children.get(i)).state).y);
      }

      for (int i = 0; i < children.size(); i++) {
        int min = 1000;
        int minIndex = -1;

        for (int j = 0; j < nodesHeuristicCosts.length; j++) {
          if (nodesHeuristicCosts[i] < min) {
            min = nodesHeuristicCosts[i];
            minIndex = i;
          }
        }

        nodesHeuristicCosts[minIndex] = 1000;
        nodes.addLast(children.get(minIndex));
      }
    }
  }
}
