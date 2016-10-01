import java.util.*;
import java.util.LinkedList;

abstract public class Problem {

	public State initalState;
	public LinkedList<String> operators;
	public LinkedList<State> stateSpace;

	abstract public boolean goalTest(State state);
	abstract public int costFunc();

	abstract public ArrayList applyOperators(Node node);
}