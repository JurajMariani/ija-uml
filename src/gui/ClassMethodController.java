package gui;
import java.io.IOException;
import java.util.List;

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
import uml.core.Core_Method;

public class ClassMethodController
{   
    public Stage actualWindow;
    public Core_Class newClass;

    @FXML private TextField tfMethodName;
    @FXML private TextField tfMethodType;

    @FXML private TextField tfMethodParamName;
    @FXML private TextField tfParamName;
    @FXML private TextField tfParamType;
    @FXML private TextField tfParamVal;

    @FXML private TextField tfDeleteMethod;
    @FXML private TextField tfDeleteParam;
    @FXML private TextField tfDeleteMethodParam;

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
        List<Core_Method> meths = newClass.get_methods();
        for(Core_Method meth : meths)
        {
            System.out.print( meth.get_str_method()+"\n");
        }
        actualWindow.close();
    }

    @FXML
    void AddMethodButton(ActionEvent event) {
        String methodName = tfMethodName.getText();
        String methodType = tfMethodType.getText();
        int methodVisibility = -1;

        switch(choiceBox.getValue().toString())
        {
            case "Public": methodVisibility = 0; break;
            case "Protected": methodVisibility = 1; break;
            case "Private": methodVisibility = 2; break;
            default: break;
        }

        Core_Method method = newClass.add_method();

        method.rename(methodName);
        method.change_type(methodType);
        method.change_visibility(methodVisibility);

        tfMethodName.setText("");
        tfMethodType.setText("");
    }

    @FXML
    void DeleteMethodButton(ActionEvent event) {
        String methodName = tfDeleteMethod.getText();

        Core_Method method = newClass.get_method(methodName);

        newClass.remove_method(method);

        tfDeleteMethod.setText("");
    }

    @FXML
    void AddParamButton(ActionEvent event){
        String paramName = tfParamName.getText();
        String paramType = tfParamType.getText();
        String paramVal = tfParamVal.getText();
        String methodName = tfMethodParamName.getText();

        Core_Method method = newClass.get_method(methodName);

        Core_Attribute param = method.add_param();
        param.rename(paramName);
        param.change_type(paramType);
        param.change_value(paramVal);
        
        tfParamName.setText("");
        tfParamType.setText("");
        tfParamVal.setText("");
        tfMethodParamName.setText("");
    }

    @FXML
    void DeleteParamButton(ActionEvent event)
    {
        String methodName = tfDeleteMethodParam.getText();
        String paramName = tfDeleteParam.getText();

        Core_Method method = newClass.get_method(methodName);
        Core_Attribute param = method.get_param(paramName);

        method.remove_param(param);

        tfDeleteMethodParam.setText("");
        tfDeleteParam.setText("");
    }

    @FXML
    void MenuAttr(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ClassAttr.fxml"));
        Parent attrView = loader.load();

        Scene scene = new Scene(attrView);

        ClassAttrController controller = loader.getController();
        controller.initData(actualWindow, newClass);

        actualWindow.setScene(scene);
        actualWindow.show();
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
}

