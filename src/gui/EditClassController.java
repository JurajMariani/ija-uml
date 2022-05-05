package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import uml.core.Core_Attribute;
import uml.core.Core_Class;
import uml.core.Core_Method;

public class EditClassController {
    @FXML private TextField className;

    @FXML private Label attributes;
    @FXML private TextField selectAttr;
    @FXML private TextField attrName;
    @FXML private TextField attrType;
    @FXML private TextField attrVal;
    @FXML private ChoiceBox attrCB;

    @FXML private Label methods;
    @FXML private TextField selectMeth;
    @FXML private TextField methName;
    @FXML private TextField methType;
    @FXML private ChoiceBox methCB;

    @FXML private Label params;
    @FXML private TextField selectParam;
    @FXML private TextField selectParam_Meth;
    @FXML private TextField paramName;
    @FXML private TextField paramType;
    @FXML private TextField paramVal;

    ObservableList<String> visibilityChoice = FXCollections.observableArrayList("Public", "Protected", "Private");

    Stage window;
    Core_Class actualClass;
    
    public void initData(Stage window, Core_Class c)
    {
        this.actualClass = c;
        this.window = window;
        this.className.setText(this.actualClass.get_name());

        StringBuilder attrs = new StringBuilder();
        StringBuilder meths = new StringBuilder();
        StringBuilder params = new StringBuilder();

        for (Core_Attribute attr : this.actualClass.get_attributes())
        {
            attrs.append(attr.get_str_attribute()+"\n");
        }

        for (Core_Method meth : this.actualClass.get_methods())
        {
            meths.append(meth.get_str_method()+"\n");
            for(Core_Attribute param : meth.get_params())
            {
                params.append(param.get_str_attribute(true)+"\n");
            }
        }

        this.attributes.setText(attrs.toString());
        this.methods.setText(meths.toString());
        this.params.setText(params.toString());
    }

    @FXML void initialize()
    {
        this.attrCB.setValue("Public");
        this.attrCB.setItems(this.visibilityChoice);

        this.methCB.setValue("Public");
        this.methCB.setItems(this.visibilityChoice);
    }

    @FXML void CloseButton(ActionEvent event)
    {
        this.window.close();
    }

    @FXML void ChangeName(ActionEvent event)
    {
        this.actualClass.rename(this.className.getText());
        this.className.setText("");
    }

    @FXML void AddAttr(ActionEvent event)
    {
        String attrName = this.attrName.getText();
        String attrType = this.attrType.getText();
        String attrVal = this.attrVal.getText();
        int attrVisibility = -1;

        switch(this.attrCB.getValue().toString())
        {
            case "Public": attrVisibility = 0; break;
            case "Protected": attrVisibility = 1; break;
            case "Private": attrVisibility = 2; break;
            default: break;
        }
        
        Core_Attribute attr = this.actualClass.add_attribute();
        attr.rename(attrName);
        attr.change_type(attrType);
        attr.change_value(attrVal);
        attr.change_visibility(attrVisibility);

        this.attrCB.setValue("Public");
        this.attrName.setText("");
        this.attrType.setText("");
        this.attrVal.setText("");
        this.selectAttr.setText("");
    }

    @FXML void EditAttr(ActionEvent event)
    {
        String select = this.selectAttr.getText();
        String attrName = this.attrName.getText();
        String attrType = this.attrType.getText();
        String attrVal = this.attrVal.getText();
        int attrVisibility = -1;

        switch(this.attrCB.getValue().toString())
        {
            case "Public": attrVisibility = 0; break;
            case "Protected": attrVisibility = 1; break;
            case "Private": attrVisibility = 2; break;
            default: break;
        }

        Core_Attribute attr = this.actualClass.get_attribute(select);

        attr.rename(attrName);
        attr.change_type(attrType);
        attr.change_value(attrVal);
        attr.change_visibility(attrVisibility);

        this.attrCB.setValue("Public");
        this.attrName.setText("");
        this.attrType.setText("");
        this.attrVal.setText("");
        this.selectAttr.setText("");

    }

    @FXML void DelAttr(ActionEvent event)
    {
        String select = this.selectAttr.getText();
        Core_Attribute attr = this.actualClass.get_attribute(select);
        this.actualClass.remove_attribute(attr);

        this.attrCB.setValue("Public");
        this.attrName.setText("");
        this.attrType.setText("");
        this.attrVal.setText("");
        this.selectAttr.setText("");
    }

    @FXML void GetAttrName(ActionEvent event)
    {
        String wantedAttr = this.selectAttr.getText();
        Core_Attribute attr = this.actualClass.get_attribute(wantedAttr);
        Alert a = new Alert(AlertType.NONE);

        if(attr == null)
        {
            a.setAlertType(AlertType.WARNING);
            a.setTitle("Invalid attribute name");
            a.setContentText("Trying to select unknown attribute.\nYou have to insert it first.");
            a.show();
        }
        else
        {
            this.attrName.setText(attr.get_name());
            this.attrType.setText(attr.get_type());
            this.attrVal.setText(attr.get_value());
            switch(attr.get_visibility())
            {
                case 0: this.attrCB.setValue("Public"); break;
                case 1: this.attrCB.setValue("Protected"); break;
                case 2: this.attrCB.setValue("Private"); break;
                default: break;
            }
        }
    }

    @FXML void AddMeth(ActionEvent event)
    {
        String methName = this.methName.getText();
        String methType = this.methType.getText();
        int visibility = -1;

        switch(this.methCB.getValue().toString())
        {
            case "Public": visibility = 0; break;
            case "Protected": visibility = 1; break;
            case "Private": visibility = 2; break;
            default: break;
        }

        Core_Method meth = this.actualClass.add_method();
        meth.rename(methName);
        meth.change_type(methType);
        meth.change_visibility(visibility);

        this.methCB.setValue("Public");
        this.methName.setText("");
        this.methType.setText("");
        this.selectMeth.setText("");

    }

    @FXML void EditMeth(ActionEvent event)
    {
        String select = this.selectMeth.getText();
        String methName = this.methName.getText();
        String methType = this.methType.getText();
        int visibility = -1;

        switch(this.methCB.getValue().toString())
        {
            case "Public": visibility = 0; break;
            case "Protected": visibility = 1; break;
            case "Private": visibility = 2; break;
            default: break;
        }

        Core_Method meth = this.actualClass.get_method(select);
        meth.rename(methName);
        meth.change_type(methType);
        meth.change_visibility(visibility);

        this.methCB.setValue("Public");
        this.methName.setText("");
        this.methType.setText("");
        this.selectMeth.setText("");
    }

    @FXML void DelMeth(ActionEvent event)
    {
        String select = this.selectMeth.getText();
        Core_Method meth = this.actualClass.get_method(select);
        this.actualClass.remove_method(meth);

        this.methCB.setValue("Public");
        this.methName.setText("");
        this.methType.setText("");
        this.selectMeth.setText("");
    }

    @FXML void GetMethName(ActionEvent event)
    {
        String wantedMeth = this.selectMeth.getText();
        Core_Method meth = this.actualClass.get_method(wantedMeth);
        Alert a = new Alert(AlertType.NONE);

        if(meth == null)
        {
            a.setAlertType(AlertType.WARNING);
            a.setTitle("Invalid method name");
            a.setContentText("Trying to select unknown method.\nYou have to insert it first.");
            a.show();
        }
        else
        {
            this.methName.setText(meth.get_name());
            this.methType.setText(meth.get_type());
            switch(meth.get_visibility())
            {
                case 0: this.methCB.setValue("Public"); break;
                case 1: this.methCB.setValue("Protected"); break;
                case 2: this.methCB.setValue("Private"); break;
                default: break;
            }
        }
    }

    @FXML void AddParam(ActionEvent event)
    {
        String paramName = this.paramName.getText();
        String paramType = this.paramType.getText();
        String paramVal = this.paramVal.getText();
        String wantedMeth = this.selectParam_Meth.getText();

        Core_Method meth = this.actualClass.get_method(wantedMeth);
        Core_Attribute param = meth.add_param();

        param.rename(paramName);
        param.change_type(paramType);
        param.change_value(paramVal);

        this.paramName.setText("");
        this.paramType.setText("");
        this.paramVal.setText("");
        this.selectParam_Meth.setText("");
        this.selectParam.setText("");
    }

    @FXML void EditParam(ActionEvent event)
    {
        String paramName = this.paramName.getText();
        String paramType = this.paramType.getText();
        String paramVal = this.paramVal.getText();
        String wantedParam = this.selectParam.getText();
        String wantedMeth = this.selectParam_Meth.getText();

        Core_Method meth = this.actualClass.get_method(wantedMeth);
        Core_Attribute param = meth.get_param(wantedParam);

        param.rename(paramName);
        param.change_type(paramType);
        param.change_value(paramVal);

        this.paramName.setText("");
        this.paramType.setText("");
        this.paramVal.setText("");
        this.selectParam_Meth.setText("");
        this.selectParam.setText("");

    }

    @FXML void DelParam(ActionEvent event)
    {
        String wantedParam = this.selectParam.getText();
        String wantedMeth = this.selectParam_Meth.getText();

        Core_Method meth = this.actualClass.get_method(wantedMeth);
        Core_Attribute param = meth.get_param(wantedParam);

        meth.remove_param(param);
        this.paramName.setText("");
        this.paramType.setText("");
        this.paramVal.setText("");
        this.selectParam_Meth.setText("");
        this.selectParam.setText("");
    }

    @FXML void GetParamName(ActionEvent event)
    {
        String wantedParam = this.selectParam.getText();
        String wantedMeth = this.selectParam_Meth.getText();
        Core_Method meth = this.actualClass.get_method(wantedMeth);
        Alert a = new Alert(AlertType.NONE);

        if(meth == null)
        {
            a.setAlertType(AlertType.WARNING);
            a.setTitle("Invalid method name");
            a.setContentText("Trying to select unknown method.\nYou have to insert it first.");
            a.show();
        }
        else
        {
            Core_Attribute param = meth.get_param(wantedParam);
            if(param == null)
            {
                a.setAlertType(AlertType.WARNING);
                a.setTitle("Invalid parameter name");
                a.setContentText("Trying to select unknown parameter.\nYou have to insert it first.");
                a.show();
            }
            else
            {
                this.paramName.setText(param.get_name());
                this.paramType.setText(param.get_type());
                this.paramVal.setText(param.get_value());
            }
        }
    }

    @FXML void GetParam_MethName(ActionEvent event)
    {
        String wantedMeth = this.selectMeth.getText();
        Core_Method meth = this.actualClass.get_method(wantedMeth);
        Alert a = new Alert(AlertType.NONE);

        if(meth == null)
        {
            a.setAlertType(AlertType.WARNING);
            a.setTitle("Invalid method name");
            a.setContentText("Trying to select unknown method.\nYou have to insert it first.");
            a.show();
        }
    }
}
