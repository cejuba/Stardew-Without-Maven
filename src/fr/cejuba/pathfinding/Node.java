package fr.cejuba.pathfinding;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Node extends Button {

    public Node parent;
    int col;
    int row;

    int gCost; // Distance from start node
    int hCost; // Distance from goal node
    int fCost; // gCost + hCost
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;

        setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1;");
        setPrefSize(70, 70);
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setStyle("-fx-background-color: orange; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1;");
            }
        });
    }

    public void setAsStart(){
        setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1;");
        setText("Start");
        start = true;
    }

    public void setAsGoal(){
        setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1;");
        setText("Goal");
        goal = true;
    }

    public void setAsSolid(){
        setStyle("-fx-background-color: black; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1;");
        solid = true;
    }

    public void setAsOpen(){
        open = true;
    }

    public void setAsChecked(){
        if(!start && !goal){
            setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1;");
        }
        checked = true;
    }

    public void setAsPath(){
        if(!start && !goal){
            setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1;");
        }
    }
}
