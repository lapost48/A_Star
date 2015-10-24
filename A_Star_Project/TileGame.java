import java.util.*;

public class TileGame {

    private static PriorityQueue<Path> frontier = new PriorityQueue<Path>();
    private static ArrayList<Path> closed = new ArrayList<Path>();

    public static void main(String[] args) {

	frontier.add(new Path(args[0]));
	Path bestPath = frontier.poll();

	int stepCount = 0;
      	while(!bestPath.isEnd()) {
	    stepCount++;
       	    System.out.println(bestPath + "Size: " + frontier.size());
	    closed.add(bestPath);
	    Path[] children = bestPath.getChildren();
	    for(Path child: children) {
		boolean flag = false;
		for(Path close : closed)
		    flag |= close.equals(child);
		for(Path front : frontier)
		    flag |= front.equals(child);
		if(!flag)
		    frontier.add(child);
	    }
	    bestPath = frontier.poll();
	}
	
	System.out.println("Steps: " + stepCount + "\nPath: " + bestPath + "\nWeight: " + bestPath.getWeight() + "\nLength: " + bestPath.getLength() + "\nBoards:\n" + bestPath.printBoards());

    }

}