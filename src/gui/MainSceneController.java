package gui;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.core.Core_Attribute;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;
import uml.core.Core_Method;

public class MainSceneController {

    public Stage actualWindow;
    public Core_ClassDiagram classDiagram;
    public Core_Class newClass;

    @FXML private TextArea taMain;

    void initData(Stage window, Core_ClassDiagram classDiagram)
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
            
            List<Core_Class> list = classDiagram.get_classes();
            for(Core_Class Cclass : list)
            {
                List<Core_Attribute> attrs = Cclass.get_attributes();
                //List<Core_Method> meths = Cclass.get_methods();

                for(Core_Attribute attr : attrs)
                {
                    System.out.print(attr.get_str_attribute()+"\n");
                }

                /*for(Core_Method meth : meths)
                {
                    taMain.appendText(meth.get_str_method()+"\n");
                }*/
            }
            
        }
        catch(IOException e){}

       
    }

}
