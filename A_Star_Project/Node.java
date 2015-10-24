public class Node {

    private int index;
    private char[] boardState;
    public Node prev;

    public Node(int index, char[] boardState, Node prev) {
	this.index = index;
	this.boardState = boardState;
	this.prev = prev;
    }

    public int getIndex() {
	return index;
    }

    public char[] getBoard() {
	return boardState;
    }

    public String toString() {
	return Integer.toString(index);
    }

}