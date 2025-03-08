package fr.cejuba.pathfinding;

import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class DemoPanel extends Pane {

    // Screen Settings
    static final int maxCol = 15;
    static final int maxRow = 10;
    static final int nodeSize = 70;
    public static final int screenWidth = maxCol * nodeSize;
    public static final int screenHeight = maxRow * nodeSize;

    // Node
    Node[][] nodes = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    // Others
    boolean goalReached = false;
    int step = 0;

    public DemoPanel() {
        setPrefSize(screenWidth, screenHeight);
        setOnKeyPressed(new KeyHandler(this));
        setFocusTraversable(true);

        // Place Nodes
        int col = 0;
        int row = 0;
        while (col < maxCol && row < maxRow) {
            nodes[col][row] = new Node(col, row);
            nodes[col][row].setPrefSize(nodeSize, nodeSize);
            nodes[col][row].setLayoutX(col * nodeSize);
            nodes[col][row].setLayoutY(row * nodeSize);
            nodes[col][row].setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1;");
            getChildren().add(nodes[col][row]);

            System.out.println("Added Node at: (" + col + ", " + row + ")");

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
        // Set Start and Goal Node
        setStartNode(3, 6);
        setGoalNode(11, 3);

        // Place Solid Nodes
        setSolidNode(10, 2);
        setSolidNode(10, 3);
        setSolidNode(10, 4);
        setSolidNode(10, 5);
        setSolidNode(10, 6);
        setSolidNode(10, 7);
        setSolidNode(6, 2);
        setSolidNode(7, 2);
        setSolidNode(8, 2);
        setSolidNode(9, 2);
        setSolidNode(11, 7);
        setSolidNode(12, 7);
        setSolidNode(6, 1);

        // Set Cost on Nodes
        setCostOnNodes();
    }

    private void setStartNode(int col, int row) {
        nodes[col][row].setAsStart();
        startNode = nodes[col][row];
        currentNode = startNode;
    }

    private void setGoalNode(int col, int row) {
        nodes[col][row].setAsGoal();
        goalNode = nodes[col][row];
    }

    private void setSolidNode(int col, int row) {
        nodes[col][row].setAsSolid();
    }

    private void setCostOnNodes() {
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {
            getCost(nodes[col][row]);
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    private void getCost(Node node) {
        // Get G Cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // Get H Cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // Get F Cost
        node.fCost = node.gCost + node.hCost;

        // Display the cost on Node
        if (node != startNode && node != goalNode) {
            node.setText("F:" + node.fCost + "\nG:" + node.gCost);
        }
    }

    public void search() {
        if (!goalReached) {
            int col = currentNode.col;
            int row = currentNode.row;

            // Add Current Node to Checked List
            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            if (row - 1 >= 0) {
                openNode(nodes[col][row - 1]); // Top
            }
            if (col - 1 >= 0) {
                openNode(nodes[col - 1][row]); // Left
            }
            if (col + 1 < maxCol) {
                openNode(nodes[col + 1][row]); // Right
            }
            if (row + 1 < maxRow) {
                openNode(nodes[col][row + 1]); // Bottom
            }
            // Find the best Node
            int bestNodeIndex = 0;
            int bestNodeFCost = Integer.MAX_VALUE;

            for (int i = 0; i < openList.size(); i++) {
                // Check if this node's F cost is better than the best node's F cost
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                // If equal, check G cost
                else if (openList.get(i).fCost == bestNodeFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            // After the loop, we have our best node
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePatch();
            }
        }
    }

    public void autoSearch(){
        while (!goalReached && step < 300) {
            search();
        }
        step++;
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void trackThePatch(){

        // Backtrack and draw the best path
        Node current = goalNode;

        while(current != startNode){
            current = current.parent;
            if(current != startNode){
                current.setAsPath();
            }
        }
    }
}