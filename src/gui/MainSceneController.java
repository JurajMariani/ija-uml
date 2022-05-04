package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;

public class MainSceneController{// implements Initializable{

    public Stage actualWindow;
    public Core_ClassDiagram classDiagram;
    public Core_Class newClass;

    @FXML private Pane pane;

    public myClassBox container;
    DraggableObject dragObj = new DraggableObject();
    
    public void initData(Stage window, Core_ClassDiagram classDiagram)
    {
        this.actualWindow = window;
        this.classDiagram = classDiagram;
    }

    /*@Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if(newClass != null)
        {
            this.dragObj.makeDraggable(this.container.createBox(this.newClass));
            this.gridPane.getChildren().remove(this.scrollPane);
            this.scrollPane.setContent(container.createBox(newClass));
            this.gridPane.getChildren().add(this.scrollPane);
        }
    }
    */
    @FXML
    void NewClass(ActionEvent event)
    {
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

            container = new myClassBox();
            VBox myBox = this.container.createBox(this.newClass);
            this.dragObj.makeDraggable(myBox);
            this.pane.getChildren().add(myBox);
        }
        catch(IOException e){}

    }

    @FXML
    void SelectClass(ActionEvent event)
    {

    }

    @FXML
    void EditClass(ActionEvent event)
    {

    }

    @FXML
    void DeleteClass(ActionEvent event)
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
}
