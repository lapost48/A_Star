import java.util.HashMap;

public class Path implements Comparable {

    static HashMap<String, Integer> heuristicValues = new HashMap<String, Integer>();

    private Node head;
    private int weight;
    private char[] board;

    public Path(String board) {
	this.board = board.toCharArray();
	head = null;
	weight = 0;
    }

    public Path(Path parent, int weight, int index, char[] board) {
	this.head = new Node(index, board, parent.head);
	this.weight = weight;
	this.board = board;
    }

    public Path[] getChildren() {
	Path[] children;
	String b = String.valueOf(board);
	int spaceIndex = b.indexOf(" ") + 1;
	int numChildren;
	switch(spaceIndex) {
	    case 1:
	    case 7:
	        numChildren = 3;
	        break;
	    case 2:
	    case 6:
	        numChildren = 4;
	        break;
	    default:
	        numChildren = 5;
	}
	children = new Path[numChildren];

	int moveIndex = spaceIndex - 3;
	for(int i = 0; i < children.length; i++) {
	    while(moveIndex < 1)
		moveIndex++;
	    if(spaceIndex == moveIndex)
		moveIndex++;

	    int moveWeight = Math.abs(moveIndex - spaceIndex) > 2 ? 2 : 1;
		
	    char[] childBoard = new char[7];
	    for(int j = 0; j < board.length; j++) {
		if(j == moveIndex - 1)
		    childBoard[j] = ' ';
		else if(j == spaceIndex - 1)
		    childBoard[j] = board[moveIndex - 1];
		else
		    childBoard[j] = board[j];
	    }
	    children[i] = new Path(this, weight + moveWeight, moveIndex, childBoard);
	    moveIndex++;
	}
	return children;
    }

    public int getWeight() {
	return weight + heuristic();
    }

    public int getLength() {
	int count = 0;
	Node next = head;
	for(int i = 0; next != null; count++) {
	    next = next.prev;
	}
	return count;
    }

    public String printBoards() {
	Node next = head;
	String ret = "";
	while(next != null) {
	    ret = String.valueOf(next.getBoard()) + "\n" + ret;
	    next = next.prev;
	}
	return ret;
    }

    public char[] getBoard() {
	return board;
    }

    public boolean isEnd() {
	return heuristic() == 0;
    }

    private int heuristic() {
	if(heuristicValues.containsKey(String.valueOf(board)))
	   return heuristicValues.get(String.valueOf(board));
	else {
	    int wcount = 0;
	    int bcount = 0;
	    for(char c: board) {
		if(c == 'b')
		    bcount += (3 - wcount);
		if(c == 'w')
		    wcount++;
	    }
	    heuristicValues.put(String.valueOf(board), new Integer(bcount));
	    return bcount;
	}
    }

    public boolean equals(Path comp) {
	return String.valueOf(this.board).equals(String.valueOf(comp.getBoard()));
    }

    public int compareTo(Object o) {
	Path comp = (Path)o;
	int myTotal = this.weight + heuristic();
	int compTotal = comp.weight + comp.heuristic();
	if(myTotal > compTotal)
	    return 1;
	else if(myTotal == compTotal)
	    return 0;
	else
	    return -1;
    }

    public String toString() {
	String str = "";
	Node next = head;
	while(next != null) {
	    str = next + " " + str;
	    next = next.prev;
	}
	str += " " + String.valueOf(board);
	return str;
    }

}