package gui;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uml.core.Core_Attribute;
import uml.core.Core_Class;

public class ClassAttrController
{
    public Stage actualWindow;
    public Core_Class newClass;
    @FXML private TextField tf1;
    @FXML private TextField tf2;
    @FXML private TextField tf3;
    @FXML private TextField tf4;
    @FXML private ChoiceBox choiceBox;

    ObservableList<String> visibilityChoice = FXCollections.observableArrayList("Public", "Protected", "Private");

    @FXML
    private void initialize()
    {
        choiceBox.setValue("Public");
        choiceBox.setItems(visibilityChoice);
    }

    void initData(Stage window, Core_Class newClass)
    {
        this.actualWindow = window;
        this.newClass = newClass;
    }

    @FXML
    void ApplyButton(ActionEvent event) {
        
        actualWindow.close();
    }

    @FXML
    void DeleteAttrButton(ActionEvent event) {
        String attrName = tf4.getText();

        Core_Attribute attr = newClass.get_attribute(attrName);

        newClass.remove_attribute(attr);
        tf4.setText("");
    }

    @FXML
    void AddAttrButton(ActionEvent event) {
        String attrName = tf1.getText();
        String attrType = tf2.getText();
        String attrVal = tf3.getText();
        int attrVisibility = -1;

        switch(choiceBox.getValue().toString())
        {
            case "Public": attrVisibility = 0; break;
            case "Protected": attrVisibility = 1; break;
            case "Private": attrVisibility = 2; break;
            default: break;
        }
        
        Core_Attribute attr = newClass.add_attribute();
        attr.rename(attrName);
        attr.change_type(attrType);
        attr.change_value(attrVal);
        attr.change_visibility(attrVisibility);

        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
    }

    @FXML
    void MenuClass(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ClassName.fxml"));
        Parent classView = loader.load();

        Scene scene = new Scene(classView);

        ClassNameController controller = loader.getController();
        controller.initData(actualWindow, newClass);

        actualWindow.setScene(scene);
        actualWindow.show();
    }

    @FXML
    void MenuMethods(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ClassMethod.fxml"));
        Parent methodView = loader.load();

        Scene scene = new Scene(methodView);

        ClassMethodController controller = loader.getController();
        controller.initData(actualWindow, newClass);

        actualWindow.setScene(scene);
        actualWindow.show();
    }
}
