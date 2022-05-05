package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

public class DraggableObject
{
    private double mouseCoordX, mouseCoordY;
    List <Double> coords;

    public void makeDraggable(Node obj)
    {
        this.coords = new ArrayList<Double>();
        
        obj.setOnMousePressed(mouseEvent -> {
            this.mouseCoordX = mouseEvent.getX();
            this.mouseCoordY = mouseEvent.getY();
        });

        obj.setOnMouseDragged(mouseEvent -> {
            obj.setLayoutX(mouseEvent.getSceneX() - this.mouseCoordX);
            obj.setLayoutY(mouseEvent.getSceneY() - this.mouseCoordY);
        });  
    }
}
