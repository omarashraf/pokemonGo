import java.util.*;

public class GottaCatchEmAllNode extends Node {
	int heuristicCost;
	
	
  public GottaCatchEmAllNode(GottaCatchEmAllNode parentNode, int depth, int pathCost, char operator, GottaCatchEmAllState state) {
    this.parentNode = parentNode;
    this.depth = depth;
    this.pathCost = pathCost;
    this.operator = operator;
    this.state = state;
    heuristicCost = 0;
  }
}
