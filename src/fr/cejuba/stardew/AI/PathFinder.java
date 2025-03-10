// TODO : Patch bug PNJ can't find the path
package fr.cejuba.stardew.AI;

import fr.cejuba.stardew.main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    private GamePanel gamePanel;
    private Node[][] node;
    private ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;

    public PathFinder(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        instantiateNodes();
    }

    public void instantiateNodes(){
        node = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            node[col][row] = new Node(col, row);
            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes(){

        int col = 0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            node[col][row].setOpen(false);
            node[col][row].setChecked(false);
            node[col][row].setSolid(false);

            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
            }
        }

        // Reset other variables
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){

            // Set solid nodes
            // Check tiles
            int tileNumber = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][col][row];
            if(gamePanel.tileManager.tiles[tileNumber].isCollision()){
                node[col][row].setSolid(true);
            }
            // Check Interactive tiles
            for(int i = 0; i < gamePanel.interactiveTile[1].length; i++){
                if(gamePanel.interactiveTile[gamePanel.currentMap][i] != null && gamePanel.interactiveTile[gamePanel.currentMap][i].isDestructible()){
                    int interactiveTileCol = gamePanel.interactiveTile[gamePanel.currentMap][i].worldX/gamePanel.tileSize;
                    int interactiveTileRow = gamePanel.interactiveTile[gamePanel.currentMap][i].worldY/gamePanel.tileSize;
                    node[interactiveTileCol][interactiveTileRow].setSolid(true);
                }
            }

            // Set cost
            getCost(node[col][row]);

            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node){

        // G Cost
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());
        node.setgCost(xDistance + yDistance);

        // H Cost
        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());
        node.sethCost(xDistance + yDistance);

        // F Cost
        node.setfCost(node.getgCost() + node.gethCost());
    }

    public boolean search(){
        while(!goalReached && step < 1000){
            int col = currentNode.getCol();
            int row = currentNode.getRow();

            // Check the current node
            currentNode.setChecked(true);
            openList.remove(currentNode);

            // Open the top node
            if(row - 1 >=0){
                openNode(node[col][row - 1]);
            }
            // Open the left node
            if(col - 1 >= 0){
                openNode(node[col - 1][row]);
            }
            // Open the bottom node
            if(row + 1 < gamePanel.maxWorldRow){
                openNode(node[col][row + 1]);
            }
            // Open the right node
            if(col + 1 < gamePanel.maxWorldCol){
                openNode(node[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for(int i = 0; i < openList.size(); i++){
                // Check if F cost is better
                if(openList.get(i).getfCost() < bestNodeFCost){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).getfCost();
                }
                // If equals, check G cost
                else if(openList.get(i).getfCost() == bestNodeFCost){
                    if(openList.get(i).getgCost() < openList.get(bestNodeIndex).getgCost()){
                        bestNodeIndex = i;
                    }
                }
            }

            // If there's no node in openList, end the loop
            if(openList.isEmpty()){
                break;
            }

            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node){
        if(!node.isOpen() && !node.isChecked() && !node.isSolid() ){
            node.setOpen(true);
            node.setParent(currentNode);
            openList.add(node);
        }
    }

    public void trackThePath(){

        Node current = goalNode;

        while(current != startNode){
            pathList.addFirst(current);
            current = current.getParent();
        }
    }
}
