package fr.cejuba.stardew.AI;

public class Node {

    private Node parent;
    private final int col;
    private final int row;
    private int gCost; // Distance from start node
    private int hCost; // Distance from goal node
    private int fCost; // gCost + hCost
    private boolean solid;
    private boolean open;
    private boolean checked;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }

    // Getters and setters

    public int getgCost() {
        return gCost;
    }
    public void setgCost(int gCost) {
        this.gCost = gCost;
    }
    public int gethCost() {
        return hCost;
    }
    public void sethCost(int hCost) {
        this.hCost = hCost;
    }
    public int getfCost() {
        return fCost;
    }
    public void setfCost(int fCost) {
        this.fCost = fCost;
    }
    public boolean isSolid() {
        return solid;
    }
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }

}
