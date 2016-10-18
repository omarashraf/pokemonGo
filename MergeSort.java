import java.util.LinkedList;

/**
 * Created by mohamedabdel-azeem on 10/15/16.
 */
public class MergeSort {

    public static LinkedList<Node> mergeSort(LinkedList<Node> nodes) {
        if (nodes.size() <= 1) {
            return nodes;
        }

        LinkedList<Node> firstHalf = new LinkedList<Node>();
        LinkedList<Node> secondHalf = new LinkedList<Node>();

        for (int i = 0; i < nodes.size()/2; i++) {
            firstHalf.add(nodes.get(i));
        }

        for (int i = nodes.size()/2; i < nodes.size(); i++) {
            secondHalf.add(nodes.get(i));
        }

        mergeSort(firstHalf);
        mergeSort(secondHalf);

        merge(firstHalf, secondHalf, nodes);
        return nodes;
    }

    public static void merge(LinkedList<Node> firstHalf, LinkedList<Node> secondHalf, LinkedList<Node> result) {
        int iFirst = 0;
        int iSecond = 0;

        int j = 0;

        while(iFirst < firstHalf.size() && iSecond < secondHalf.size()) {
            if (firstHalf.get(iFirst).compareTo(secondHalf.get(iSecond)) == -1) {
                result.set(j, firstHalf.get(iFirst));
                iFirst++;
            }

            else {
                result.set(j, secondHalf.get(iSecond));
                iSecond++;
            }

            j++;
        }

        if (iFirst < firstHalf.size()) {
            for (int i = iFirst; i < firstHalf.size(); i++) {
                result.set(j++, firstHalf.get(i));
            }
        }

        if (iSecond < secondHalf.size()) {
            for (int i = iSecond; i < secondHalf.size(); i++) {
                result.set(j++, secondHalf.get(i));
            }
        }
    }

//    public static void main(String[] args) {
//        LinkedList<Node> nodes = new LinkedList<Node>();
//
//        Node nodeA = new Node(null, 1, 1, ' ', null);
//        Node nodeB = new Node(null, 1, 2, ' ', null);
//        Node nodeC = new Node(null, 1, 3, ' ', null);
//        Node nodeD = new Node(null, 1, 4, ' ', null);
//        Node nodeE = new Node(null, 1, 5, ' ', null);
//        Node nodeF = new Node(null, 1, 6, ' ', null);
//
//        nodes.add(nodeA);
//        nodes.add(nodeB);
//        nodes.add(nodeC);
//        nodes.add(nodeD);
//        nodes.add(nodeE);
//        nodes.add(nodeF);
//
//        mergeSort(nodes);
//
//        for (int i = 0; i < nodes.size(); i++) {
//            System.out.println(nodes.get(i).pathCost);
//        }
//    }
}

