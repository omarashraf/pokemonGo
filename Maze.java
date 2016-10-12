import java.util.*;
import java.util.LinkedList;
import java.lang.*;

public class Maze {

	public int width = 5;
	public int height = 5;
	public Cell[][] grid = new Cell[width][height];
	public int startX = (int) (Math.random() * width);
	public int startY = (int) (Math.random() * height);
	public Cell endCell;
	public int pokemonNumbers = (int) (Math.random() * ((width * height) / 2)) + 1;
	public boolean[][] pokemonFlag = new boolean[width][height];
	public LinkedList<Cell> pokemonCells = new LinkedList<Cell>();

	public void initalizeGrid() {
		for (int i = 0; i < width; i++) {
			for (int j = 0 ; j < height; j++) {
				grid[i][j] = new Cell(i, j);
			}
		}
	}

	public void genMaze() {
		this.initalizeGrid();
		boolean[][] gridCellsState = new boolean[width][height];
		boolean[] neighbourCells = new boolean[4];
		LinkedList<Cell> stackCells = new LinkedList<Cell>();
		// printing the starting cell indices.
		System.out.println("X: " + startX + ", Y: " + startY);
		Cell cell = new Cell(startX, startY);
		stackCells.addFirst(cell);

		do {
			gridCellsState[cell.x][cell.y] = true;
			neighbourCells = new boolean[4];
			int neighbourCellsCount = 0;
			// define: 0 -> UP, 1 -> RIGHT, 2 -> DOWN, 3 -> LEFT
			for (int i = 0; i < neighbourCells.length; i++) {
				switch (i) {
					case 0:
						if (cell.y - 1 >= 0 && !gridCellsState[cell.x][cell.y - 1]) {
							//System.out.println("UP");
							neighbourCells[i] = true;
							neighbourCellsCount++;
						}
						break;
					case 1:
						if (cell.x + 1 < width && !gridCellsState[cell.x + 1][cell.y]) {
							//System.out.println("RIGHT");
							neighbourCells[i] = true;
							neighbourCellsCount++;
						}
						break;
					case 2:
						if (cell.y + 1 < height && !gridCellsState[cell.x][cell.y + 1]) {
							//System.out.println("DOWN");
							neighbourCells[i] = true;
							neighbourCellsCount++;
						}
						break;
					case 3:
						if (cell.x - 1 >= 0 && !gridCellsState[cell.x - 1][cell.y]) {
							//System.out.println("LEFT");
							neighbourCells[i] = true;
							neighbourCellsCount++;
						}
						break;
				}
			}


			if (neighbourCellsCount > 0) {
				stackCells.addFirst(cell);
				cell = new Cell(cell.x, cell.y);
				int randomNextCell;
				do {
					randomNextCell = (int) (Math.random() * 4);
				} while(!neighbourCells[randomNextCell]);

				// for debugging.
				//System.out.println(randomNextCell);

				switch (randomNextCell) {
					case 0:
						grid[cell.x][cell.y].directions.addFirst("U");
						grid[cell.x][cell.y - 1].directions.addFirst("D");
						cell.y -= 1;
						break;
					case 1:
						grid[cell.x][cell.y].directions.addFirst("R");
						grid[cell.x + 1][cell.y].directions.addFirst("L");
						cell.x += 1;
						break;
					case 2:
						grid[cell.x][cell.y].directions.addFirst("D");
						grid[cell.x][cell.y + 1].directions.addFirst("U");
						cell.y += 1;
						break;
					case 3:
						grid[cell.x][cell.y].directions.addFirst("L");
						grid[cell.x - 1][cell.y].directions.addFirst("R");
						cell.x -= 1;
						break;
				}
			}
			else {
				cell = stackCells.removeFirst();
			}
		} while(!stackCells.isEmpty());

		// generating a random end cell.
		this.genEnd();

		// generating random cells, according to the number of pokemons generated.
		this.genPokemons();

		// representing the grid, with each cell containing the directions it can move to.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print("Cell |" + x + "|" + y +"| ");
				LinkedList<String> tmp = new LinkedList<String>();
				while (!grid[x][y].directions.isEmpty()) {
					String tmpDir = grid[x][y].directions.removeFirst();
					tmp.addFirst(tmpDir);
					System.out.print(tmpDir + ", ");
				}
				grid[x][y].directions = tmp;
			}
			System.out.println("");
		}
		//this.displayMaze();
	}

	public void genEnd() {
		LinkedList<Cell> candidateEnds = new LinkedList<Cell>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (grid[i][j].directions.size() == 1 && !(i == startX && j == startY)) {
					candidateEnds.addFirst(grid[i][j]);
				}
			}
		}
		System.out.println("The no. of candidateEnds " + candidateEnds.size());

		// for printing all candidates.
		/*LinkedList<Cell> tmpCan = new LinkedList<Cell>();
		int size = candidateEnds.size();
		for (int j = 0; j < size; j++) {
			Cell tmp = candidateEnds.removeFirst();
			System.out.println(j + ". " + tmp.x + ", " + tmp.y);
			tmpCan.addFirst(tmp);
		}
		candidateEnds = tmpCan;*/

		// generating a random index to choose between all end cell candidates.
		int randEndIndex = (int) Math.random() * candidateEnds.size();
		//Cell endCell;
		for (int i = 0; i < randEndIndex; i++) {
			endCell = candidateEnds.removeFirst();
		}
		endCell = candidateEnds.removeFirst();
		System.out.println("endCell " + endCell.x + ", " + endCell.y);
	}

	public void genPokemons() {
		int pokemonNumbersTracker = pokemonNumbers;
		while (pokemonNumbersTracker > 0) {
			int pokemonX = (int) (Math.random() * width);
			int pokemonY = (int) (Math.random() * height);
			if (pokemonFlag[pokemonX][pokemonY]) {
				continue;
			}
			else {
				pokemonFlag[pokemonX][pokemonY] = true;
				pokemonNumbersTracker--;
				pokemonCells.addFirst(new Cell(pokemonX, pokemonY));
			}
		}

		// for debugging
		/*int size = pokemonCells.size();
		for (int i = 0; i < size; i++) {
			Cell tmp = pokemonCells.removeFirst();
			System.out.println("X: " + tmp.x + ", Y: " + tmp.y);
			pokemonCells.addLast(tmp);
		}*/
		//System.out.println(pokemonCells.size());
	}

	public void displayMaze() {
		System.out.print(" ");
		for (int i = 0; i < width; i++) {
			System.out.print("___ ");
		}
		System.out.println("");

		for (int y = 0; y < height; y++) {
			System.out.print("|");
			for (int x = 0; x < width; x++) {
				if (grid[x][y].directions.contains("D")) {
					System.out.print(" ");
					if (pokemonFlag[x][y]) {
						System.out.print("P");
					}
					else {
						System.out.print(" ");
					}
					System.out.print(" ");
				}
				else {
					System.out.print("_");
					if (pokemonFlag[x][y]) {
						System.out.print("P");
					}
					else {
						System.out.print("_");
					}
					System.out.print("_");
				}
				if (grid[x][y].directions.contains("R")) {
					System.out.print(" ");
				}
				else {
					System.out.print("|");
				}
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		Maze m = new Maze();
		m.genMaze();
		//m.genPokemons();
		//System.out.println(m.pokemonNumbers);
	}
}
