package gui;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;

public class MainSceneController {

    public Stage actualWindow;
    public Core_ClassDiagram classDiagram;
    public Core_Class newClass;

    @FXML private TextArea taMain;

    public void initData(Stage window, Core_ClassDiagram classDiagram)
    {
        this.actualWindow = window;
        this.classDiagram = classDiagram;
    }

    @FXML
    void NewButtonAction(ActionEvent event)
    {
        this.newClass = classDiagram.add_class();
        FXMLLoader loader;

        try{
            loader = new FXMLLoader(getClass().getResource("ClassName.fxml"));
            Stage newClassWindow = new Stage();
            newClassWindow.setTitle("Class Settings");
            newClassWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            ClassNameController controller = loader.getController();
            controller.initData(newClassWindow, newClass);

            newClassWindow.setScene(scene);
            newClassWindow.showAndWait();
            
            
        }
        catch(IOException e){}

       
    }

}
