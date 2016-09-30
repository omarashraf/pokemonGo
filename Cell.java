import java.util.*;
import java.util.LinkedList;

public class Cell {

	public boolean end;
	public boolean start;
	public LinkedList<String> directions;
	public int x, y;


	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		directions = new LinkedList<String>();
	}

	public static void main(String[] args) {

	}
	
}