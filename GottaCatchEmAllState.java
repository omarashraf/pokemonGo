import java.util.LinkedList;

public class GottaCatchEmAllState extends State {

  public int x;
  public int y;
  public char orientation;
  public int hatchSteps;
  public int pokeCount;
  public LinkedList<Cell> pokemonLocs = new LinkedList<Cell>();

  public GottaCatchEmAllState(int x, int y, char orientation, int hatchSteps, int pokeCount, LinkedList<Cell> pokemonLocs) {
    this.x = x;
    this.y = y;
    this.orientation = orientation;
    this.hatchSteps = hatchSteps > 0 ? hatchSteps: 0;
    this.pokeCount = pokeCount;
    this.pokemonLocs = new LinkedList<Cell>();

    for (Cell cell : pokemonLocs) {
      this.pokemonLocs.add(cell);
    }
  }
}
