package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uml.core.Core_Attribute;
import uml.core.Core_Class;
import uml.core.Core_Method;

public class CreateClassController {
    public Stage actualWindow;
    Core_Class newClass;
    
    @FXML private TextField classNameTF;

    @FXML private TextField attrNameTF;
    @FXML private TextField attrTypeTF;
    @FXML private TextField attrValueTF;

    @FXML private TextField methodNameTF;
    @FXML private TextField methodTypeTF;
    @FXML private TextField methodParamNameTF;
    @FXML private TextField paramNameTF;
    @FXML private TextField paramTypeTF;
    @FXML private TextField paramValueTF;

    @FXML private ChoiceBox attrChoiceBox;
    @FXML private ChoiceBox methodChoiceBox;
    ObservableList<String> visibilityChoice = FXCollections.observableArrayList("Public", "Protected", "Private");

    @FXML private Label wrongMethod;

    @FXML
    private void initialize()
    {
        this.attrChoiceBox.setValue("Public");
        this.attrChoiceBox.setItems(this.visibilityChoice);

        this.methodChoiceBox.setValue("Public");
        this.methodChoiceBox.setItems(this.visibilityChoice);
    }

    void initData(Stage window, Core_Class newClass)
    {
        this.actualWindow = window;
        this.newClass = newClass;
    }

    @FXML
    void CreateButton(ActionEvent event) {
        if(this.newClass.get_name() == "")
            this.newClass.rename("CLASS");
        this.actualWindow.close();
    }

    @FXML
    void ChangeClassName(ActionEvent event){
        this.newClass.rename(this.classNameTF.getText());
        this.classNameTF.setText("");
    }

    @FXML
    void AddAttrButton(ActionEvent event) {
        String attrName = this.attrNameTF.getText();
        String attrType = this.attrTypeTF.getText();
        String attrVal = this.attrValueTF.getText();
        int attrVisibility = -1;

        switch(this.attrChoiceBox.getValue().toString())
        {
            case "Public": attrVisibility = 0; break;
            case "Protected": attrVisibility = 1; break;
            case "Private": attrVisibility = 2; break;
            default: break;
        }
        
        Core_Attribute attr = this.newClass.add_attribute();
        attr.rename(attrName);
        attr.change_type(attrType);
        attr.change_value(attrVal);
        attr.change_visibility(attrVisibility);

        this.attrChoiceBox.setValue("Public");
        this.attrNameTF.setText("");
        this.attrTypeTF.setText("");
        this.attrValueTF.setText("");
    }

    @FXML
    void AddMethodButton(ActionEvent event) {
        String methodName = this.methodNameTF.getText();
        String methodType = this.methodTypeTF.getText();
        int methodVisibility = -1;

        switch(this.methodChoiceBox.getValue().toString())
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

        this.methodChoiceBox.setValue("Public");
        this.methodNameTF.setText("");
        this.methodTypeTF.setText("");
    }

    @FXML
    void AddParamButton(ActionEvent event){
        String paramName = this.paramNameTF.getText();
        String paramType = this.paramTypeTF.getText();
        String paramVal = this.paramValueTF.getText();
        String methodName = this.methodParamNameTF.getText();

        Core_Method method = this.newClass.get_method(methodName);

        if(method == null)
        {
            String msg;
            msg = "Cannot find given method";
            this.wrongMethod.setText(msg);
        }
        else
        {
            Core_Attribute param = method.add_param();
            param.rename(paramName);
            param.change_type(paramType);
            param.change_value(paramVal);
        }
        
        this.paramNameTF.setText("");
        this.paramTypeTF.setText("");
        this.paramValueTF.setText("");
        this.methodParamNameTF.setText("");
    }
}
