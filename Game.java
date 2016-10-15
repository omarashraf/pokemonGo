import java.util.LinkedList;

public class Game {

  GottaCatchEmAllProblem pokemonGo;

  public Game() {

  }

  public void search(Maze maze, Strategy strategy, boolean visualize) {
    QingFun qingFun = getQingFun(strategy);

    GeneralSearchAlgorithm.generalSearch(pokemonGo, qingFun);
  }

  public void initialize() {
    // Maze
    Maze maze = new Maze();
    maze.genMaze();

    // Orientation
    char initialOrientation = ' ';
    int randomOrientationIndex = (int) (Math.random() * ((3 - 0) + 1) + 0);
    switch (randomOrientationIndex) {
      case 0: initialOrientation = 'N'; break;
      case 1: initialOrientation = 'E'; break;
      case 2: initialOrientation = 'S'; break;
      case 3: initialOrientation = 'W'; break;
    }

    //Operators
    Operator forward = new Operator('F');
    Operator right = new Operator('R');
    Operator left = new Operator('L');

    LinkedList<Operator> operators = new LinkedList<Operator>();
//    operators.add(forward);
//    operators.add(right);
//    operators.add(left);
    operators.add(right);
    operators.add(left);
    operators.add(forward);

    // Hatch steps
    int hatchSteps = (int) (Math.random() * ((5 - 1) + 1) + 1);

    // Pokemons count
    int pokeCount = maze.pokemonNumbers;

    GottaCatchEmAllState initalState = new GottaCatchEmAllState(maze.startX, maze.startY, initialOrientation, hatchSteps, pokeCount, maze.pokemonCells);

    pokemonGo = new GottaCatchEmAllProblem(initalState, operators, null, maze);
  }

  public QingFun getQingFun(Strategy strategy) {
    QingFun qingFun;
    switch(strategy) {
      case BF: qingFun = QingFun.ENQUEUE_AT_END; break;
      case DF: qingFun = QingFun.ENQUEUE_AT_FRONT_DF; break;
      case ID: qingFun = QingFun.ENQUEUE_AT_FRONT_ID; break;
      case UC: qingFun = QingFun.ORDERED_INSERT; break;
      case GR1: qingFun = QingFun.HEURISTIC_ONE; break;
      default: qingFun = QingFun.ENQUEUE_AT_END;
    }

    return qingFun;
  }

  public static void main(String[] args) {
    Game pokemon = new Game();
    pokemon.initialize();
    pokemon.search(pokemon.pokemonGo.maze, Strategy.UC, false);
  }
}
