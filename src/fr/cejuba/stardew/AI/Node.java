package fr.cejuba.stardew.AI;

public class Node {

    Node parent;
    public int col;
    public int row;
    int gCost; // Distance from start node
    int hCost; // Distance from goal node
    int fCost; // gCost + hCost
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
