package gui;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;
import uml.core.Core_Link;
import uml.core.Element;
import uml.misc.myLines;

public class MainSceneController
{
    public Stage actualWindow;
    public Core_ClassDiagram classDiagram;

    public Core_Class selectedClass;
    public Core_Class newClass;

    public List<Core_Link> links;
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
        this.selectedClass = new Core_Class();
        this.links = new ArrayList<Core_Link>();
        this.paneListener();
    }

    private void paneListener()
    {
        this.pane.setOnMouseClicked(MouseEvent ->{
            if(this.selectedClass != null)
                this.selectedClass.get_container().removeBoxGlow();
            this.editButton.setDisable(true);
            this.selectButton.setDisable(true);
            this.deleteButton.setDisable(true);     
            this.linkButton.setDisable(true);
            MouseEvent.consume();
        });
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
            mouseEvent.consume();
        });

        obj.setOnMousePressed(mouseEvent -> {
            this.mouseCoordX = mouseEvent.getX();
            this.mouseCoordY = mouseEvent.getY();
            mouseEvent.consume();
        });

        obj.setOnMouseDragged(mouseEvent -> {
            obj.setLayoutX(mouseEvent.getSceneX() - this.mouseCoordX);
            obj.setLayoutY(mouseEvent.getSceneY() - this.mouseCoordY);

            Array.set(coords, 0, (mouseEvent.getSceneX() - mouseCoordX));
            Array.set(coords, 1, (mouseEvent.getSceneY() - mouseCoordY)); 
            
            mouseEvent.consume();
        });

        obj.setOnMouseReleased(mouseEvent -> {
            c.move(coords[0], coords[1]);
            mouseEvent.consume();
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
            editWindow.setTitle("Edit class");
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
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Delete class");
        a.setContentText("Do you really wanna delete class '" + this.selectedClass.get_name() + "' ?");

        Optional<ButtonType> rslt = a.showAndWait();
        if(rslt.get() == ButtonType.OK)
        {
            this.classDiagram.remove_class(this.selectedClass);
            this.pane.getChildren().remove(this.selectedClass.get_container().get_vbox());

            for(Core_Link link : this.links)
            {
                Element[] elems = link.get_objects();

                if(elems[0].get_name() == this.selectedClass.get_name() || elems[1].get_name() == this.selectedClass.get_name())
                    for(Node n : link.get_link())
                    {
                        this.pane.getChildren().remove(n);
                    }
            }

            this.selectedClass = null;

            this.editButton.setDisable(true);
            this.selectButton.setDisable(true);
            this.deleteButton.setDisable(true);
            this.linkButton.setDisable(true);
        }

        
    }
    
    @FXML
    void SelectClass(ActionEvent event)
    {
        System.out.print(this.selectedClass.get_name()+"\n");
        System.out.print(this.selectedClass.get_position()[0]+"\n");
        System.out.print(this.selectedClass.get_position()[1]+"\n");
    }
      
    @FXML
    void LinkClasses(ActionEvent event)
    {
        FXMLLoader loader;
        
        try{
            loader = new FXMLLoader(getClass().getResource("AddLink.fxml"));
            Stage addWindow = new Stage();
            addWindow.setTitle("Add link");
            addWindow.initModality(Modality.APPLICATION_MODAL);
            
            Scene scene = new Scene(loader.load());
            
            AddLinkController controller = loader.getController();
            controller.initData(addWindow, this.classDiagram.get_classes());
            
            addWindow.setScene(scene);
            addWindow.showAndWait();
            
            addWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we)
                {
                    return;
                }
            });
            
            List<String> list = controller.get_vals();

            Core_Class start_obj = this.classDiagram.get_class(list.get(2));
            Core_Class end_obj = this.classDiagram.get_class(list.get(3));

            double s_x, s_y, e_x, e_y;
            s_x = start_obj.get_position()[0];
            s_y = start_obj.get_position()[1];
            e_x = end_obj.get_position()[0];
            e_y = end_obj.get_position()[1];

            myLines handler = new myLines(s_x, s_y, e_x, e_y, list.get(1));
            Node[] toDraw = new Node[2];

            switch(list.get(0))
            {
                case "ass":
                    toDraw = handler.ass(); break;
                case "dir":
                    toDraw = handler.dir(); break;
                case "agg":
                    toDraw = handler.agg(); break;
                case "gen": 
                    toDraw = handler.gen(); break;
            }

            Core_Link link = new Core_Link(start_obj, end_obj);
            link.add_link(toDraw);
            
            if(list.get(4) != "")
                link.change_start_card(list.get(4));
            if(list.get(5) != "")
                link.change_start_card(list.get(5));

            this.links.add(link);
            
            for(Node o : toDraw)
            {
                if(o != null)
                    this.pane.getChildren().add(o);
            }
        }
        catch(IOException e){}
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
