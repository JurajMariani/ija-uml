package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.core.Core_Attribute;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;
import uml.core.Core_Method;

public class MainSceneController implements Initializable{

    public Stage actualWindow;
    public Core_ClassDiagram classDiagram;
    public Core_Class newClass;

    @FXML private Label className;
    @FXML private Label classAttr;
    @FXML private Label classMeth;
    @FXML private VBox classBox;

    DraggableObject dragObj = new DraggableObject();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.dragObj.makeDraggable(this.classBox);
    }

    public void initData(Stage window, Core_ClassDiagram classDiagram)
    {
        this.actualWindow = window;
        this.classDiagram = classDiagram;
    }

    @FXML
    void NewButtonAction(ActionEvent event)
    {
        this.newClass = classDiagram.add_class();
        this.newClass.rename("CLASS");
        FXMLLoader loader;

        try{
            loader = new FXMLLoader(getClass().getResource("ClassName.fxml"));
            Stage newClassWindow = new Stage();
            newClassWindow.setTitle("Create new class");
            newClassWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            ClassNameController controller = loader.getController();
            controller.initData(newClassWindow, newClass);

            newClassWindow.setScene(scene);
            newClassWindow.showAndWait();
            
            List<Core_Class> classes = this.classDiagram.get_classes();
            for(Core_Class c : classes)
            {
                className.setText(c.get_name());

                List<Core_Attribute> attrs = c.get_attributes();
                List<Core_Method> meths = c.get_methods();
                StringBuilder attributes = new StringBuilder();
                StringBuilder methods = new StringBuilder();

                for(Core_Attribute attr : attrs)
                {
                    attributes.append(attr.get_str_attribute()+"\n");
                }

                for(Core_Method meth : meths)
                {
                    methods.append(meth.get_str_method()+"\n");
                }

                this.classAttr.setText(attributes.toString());
                this.classMeth.setText(methods.toString());
            }
            
        }
        catch(IOException e){}
    }
}
