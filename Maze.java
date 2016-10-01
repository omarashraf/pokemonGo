import java.util.*;
import java.util.LinkedList;
import java.lang.*;

public class Maze {

	// size to be taken from the user
	public int width = 2;
	public int height = 3;
	Cell[][] grid = new Cell[width][height];

	public void initalizeGrid() {
		for (int i = 0; i < width; i++) {
			for (int j = 0 ; j < height; j++) {
				grid[i][j] = new Cell(i, j);
			}
		}
	}

	public void GenMaze() {
		this.initalizeGrid();
		boolean[][] gridCellsState = new boolean[width][height];
		boolean[] neighbourCells = new boolean[4];
		LinkedList<Cell> stackCells = new LinkedList<Cell>();

		Cell cell = new Cell(0, 0);
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
							System.out.println("UP");
							neighbourCells[i] = true;
							neighbourCellsCount++;
						}
						break;
					case 1:
						if (cell.x + 1 < width && !gridCellsState[cell.x + 1][cell.y]) {
							System.out.println("RIGHT");
							neighbourCells[i] = true;	
							neighbourCellsCount++;
						}
						break;
					case 2:
						if (cell.y + 1 < height && !gridCellsState[cell.x][cell.y + 1]) {
							System.out.println("DOWN");
							neighbourCells[i] = true;
							neighbourCellsCount++;		
						}
						break;
					case 3:
						if (cell.x - 1 >= 0 && !gridCellsState[cell.x - 1][cell.y]) {
							System.out.println("LEFT");
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

				// for debugging
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

		// representing the grid, with each cell containing the directions it can move to
		/*for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				System.out.print("Cell" + y + "|" + x +" ");
				while (!grid[y][x].directions.isEmpty()) {
					System.out.print(grid[y][x].directions.removeFirst() + ", ");	
				}
			}	
			System.out.println("");
		}*/
	}

	public void displayMaze() {

	}

	public static void main(String[] args) {
		Maze m = new Maze();
		m.GenMaze();
	}
}