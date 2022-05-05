package gui;

import java.io.IOException;
import java.lang.reflect.Array;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.core.Core_Attribute;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;

public class MainSceneController
{
    public Stage actualWindow;
    public Core_ClassDiagram classDiagram;
    public Core_Class selectedClass;
    public Core_Class newClass;
    private double mouseCoordX, mouseCoordY;

    @FXML private Button editButton;
    @FXML private Button selectButton;
    @FXML private Button deleteButton;
    @FXML private Button linkButton;
    @FXML private Pane pane;
    
    public void initData(Stage window, Core_ClassDiagram classDiagram)
    {
        this.actualWindow = window;
        this.classDiagram = classDiagram;
    }

    public void makeDraggable(Core_Class c)
    {   
        Node obj = c.get_container().get_vbox();
        double[] coords = { 0.0, 0.0 };

        obj.setOnMouseClicked(mouseEvent -> {
            if(this.selectedClass != null)
                this.selectedClass.get_container().removeBoxGlow();
            
            this.selectedClass = c;
            this.selectedClass.get_container().selectBoxGlow();

            this.editButton.setDisable(false);
            this.selectButton.setDisable(false);
            this.deleteButton.setDisable(false);
            this.linkButton.setDisable(false);
        });

        obj.setOnMousePressed(mouseEvent -> {
            mouseCoordX = mouseEvent.getX();
            mouseCoordY = mouseEvent.getY();
        });

        obj.setOnMouseDragged(mouseEvent -> {
            obj.setLayoutX(mouseEvent.getSceneX() - mouseCoordX);
            obj.setLayoutY(mouseEvent.getSceneY() - mouseCoordY);

            Array.set(coords, 0, (mouseEvent.getSceneX() - mouseCoordX));
            Array.set(coords, 1, (mouseEvent.getSceneY() - mouseCoordY));          
        });

        obj.setOnMouseReleased(mouseEvent -> {
            c.move(coords[0], coords[1]);
        });
    }

    @FXML
    void NewClass(ActionEvent event)
    {
        // TODO ked sa ukonci krizikom

        this.newClass = classDiagram.add_class("");
        FXMLLoader loader;

        try{
            loader = new FXMLLoader(getClass().getResource("CreateClass.fxml"));
            Stage newClassWindow = new Stage();
            newClassWindow.setTitle("Create new class");
            newClassWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            CreateClassController controller = loader.getController();
            controller.initData(newClassWindow, newClass);

            newClassWindow.setScene(scene);
            newClassWindow.showAndWait();
            
            this.newClass.get_container().setValues(this.newClass.get_name(), this.newClass.get_attributes(), this.newClass.get_methods());

            this.makeDraggable(this.newClass);
            this.pane.getChildren().add(this.newClass.get_container().get_vbox());
        }
        catch(IOException e){}

    }

    @FXML
    void EditClass(ActionEvent event)
    {
        FXMLLoader loader;

        try{
            loader = new FXMLLoader(getClass().getResource("EditClass.fxml"));
            Stage editWindow = new Stage();
            editWindow.setTitle("Create new class");
            editWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            EditClassController controller = loader.getController();
            controller.initData(editWindow, this.selectedClass);

            editWindow.setScene(scene);
            editWindow.showAndWait();
            
            this.selectedClass.get_container().setValues(this.selectedClass.get_name(), this.selectedClass.get_attributes(), this.selectedClass.get_methods());

        }
        catch(IOException e){}
    }

    @FXML
    void DeleteClass(ActionEvent event)
    {

    }
    
    @FXML
    void SelectClass(ActionEvent event)
    {

    }
      
    @FXML
    void LinkClasses(ActionEvent event)
    {
        
    }

    @FXML
    void LoadDiagram(ActionEvent event)
    {

    }

    @FXML
    void SaveDiagram(ActionEvent event)
    {
        
    }

    /*@FXML
    void HandlePaneAction(ActionEvent event)
    {
        /*for( Node node : this.pane.getChildren())
        {
            System.out.print(node.layoutXProperty().getName() + "\n");
        }
        return;
    }*/
}
