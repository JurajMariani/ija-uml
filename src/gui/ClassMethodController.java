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
import javafx.scene.control.Label;
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

    @FXML private Label wrongMeth;
    @FXML private Label wrongParam;

    ObservableList<String> visibilityChoice = FXCollections.observableArrayList("Public", "Protected", "Private");

    @FXML
    private void initialize()
    {
        this.choiceBox.setValue("Public");
        this.choiceBox.setItems(this.visibilityChoice);
    }

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
    void AddMethodButton(ActionEvent event) {
        String methodName = this.tfMethodName.getText();
        String methodType = this.tfMethodType.getText();
        int methodVisibility = -1;

        switch(this.choiceBox.getValue().toString())
        {
            case "Public": methodVisibility = 0; break;
            case "Protected": methodVisibility = 1; break;
            case "Private": methodVisibility = 2; break;
            default: break;
        }

        Core_Method method = this.newClass.add_method();

        method.rename(methodName);
        method.change_type(methodType);
        method.change_visibility(methodVisibility);

        this.choiceBox.setValue("Public");
        this.tfMethodName.setText("");
        this.tfMethodType.setText("");
    }

    @FXML
    void DeleteMethodButton(ActionEvent event) {
        String methodName = this.tfDeleteMethod.getText();

        Core_Method method = this.newClass.get_method(methodName);

        String msg;
        if(method == null)
        {
            msg = "Cannot find given method";
            this.wrongMeth.setText(msg);
        }
        else
        {
            msg = "";
            this.newClass.remove_method(method);
            this.wrongMeth.setText(msg);
        }
        
        this.tfDeleteMethod.setText("");
    }

    @FXML
    void AddParamButton(ActionEvent event){
        String paramName = this.tfParamName.getText();
        String paramType = this.tfParamType.getText();
        String paramVal = this.tfParamVal.getText();
        String methodName = this.tfMethodParamName.getText();

        Core_Method method = this.newClass.get_method(methodName);

        Core_Attribute param = method.add_param();
        param.rename(paramName);
        param.change_type(paramType);
        param.change_value(paramVal);
        
        this.tfParamName.setText("");
        this.tfParamType.setText("");
        this.tfParamVal.setText("");
        this.tfMethodParamName.setText("");
    }

    @FXML
    void DeleteParamButton(ActionEvent event)
    {
        String methodName = this.tfDeleteMethodParam.getText();
        String paramName = this.tfDeleteParam.getText();

        Core_Method method = this.newClass.get_method(methodName);

        String msg;

        if(method == null)
        {
            msg = "Cannot find given method";
            this.wrongParam.setText(msg);
        }
        else 
        {
            Core_Attribute param = method.get_param(paramName);

            if(param == null)
            {
                msg = "Cannot find given parameter";
                this.wrongParam.setText(msg);
            }
            else
            {
                msg = "";
                this.wrongParam.setText(msg);
                method.remove_param(param);
            }
            
        }

        this.tfDeleteMethodParam.setText("");
        this.tfDeleteParam.setText("");
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
    void MenuClass(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ClassName.fxml"));
        Parent classView = loader.load();

        Scene scene = new Scene(classView);

        ClassNameController controller = loader.getController();
        controller.initData(this.actualWindow, this.newClass);

        this.actualWindow.setScene(scene);
        this.actualWindow.show();
    }
}

