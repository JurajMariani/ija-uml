package gui;

import javafx.scene.Node;

public class DraggableObject {
    private double mouseCoordX, mouseCoordY;

    public void makeDraggable(Node obj)
    {
        obj.setOnMousePressed(mouseEvent -> {
            this.mouseCoordX = mouseEvent.getX();
            this.mouseCoordY = mouseEvent.getY();
        });

        obj.setOnMouseDragged(mouseEvent ->{
            obj.setLayoutX(mouseEvent.getSceneX() - this.mouseCoordX);
            obj.setLayoutY(mouseEvent.getSceneY() - this.mouseCoordY);
        });
    }
}
