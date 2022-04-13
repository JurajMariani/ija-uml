package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uml.core.Core_Class;

public class ClassNameController 
{
    public Stage actualWindow;
    Core_Class newClass;
    @FXML private TextField tf;

    void initData(Stage window, Core_Class newClass)
    {
        this.actualWindow = window;
        this.newClass = newClass;
    }

    @FXML
    void CreateButton(ActionEvent event) {
        this.actualWindow.close();
    }

    @FXML
    void ChangeClassName(ActionEvent event){
        this.newClass.rename(this.tf.getText());
        this.tf.setText("");
    }

    @FXML
    void MenuAttr(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ClassAttr.fxml"));
        Parent attrView = loader.load();

        Scene scene = new Scene(attrView);

        ClassAttrController controller = loader.getController();
        controller.initData(this.actualWindow, this.newClass);

        this.actualWindow.setScene(scene);
        this.actualWindow.show();
    }

    @FXML
    void MenuMethods(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ClassMethod.fxml"));
        Parent methodView = loader.load();

        Scene scene = new Scene(methodView);

        ClassMethodController controller = loader.getController();
        controller.initData(this.actualWindow, this.newClass);

        this.actualWindow.setScene(scene);
        this.actualWindow.show();
    }

}
