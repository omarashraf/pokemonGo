import java.util.*;
import java.util.LinkedList;

abstract public class Problem {

	public State initalState;
	public LinkedList<String> operators;
	public LinkedList<State> stateSpace;

	abstract public boolean goalTest();
	abstract public int costFunc();

}