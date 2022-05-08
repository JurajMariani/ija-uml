package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uml.core.Core_Class;

public class AddLinkController {
    private Stage window;
    private List<Core_Class> classList;

    private String linkType;
    private String lineStyle;
    private String class1;
    private String class2;
    private String card1;
    private String card2;

    private boolean linkerr;
    private boolean lineerr;
    private boolean classerr;

    @FXML private ChoiceBox class1Box;
    @FXML private ChoiceBox class2Box;
    ObservableList<String> classNames = FXCollections.observableArrayList();

    @FXML private CheckBox assCheckBox;
    @FXML private CheckBox dirAssCheckBox;
    @FXML private CheckBox genCheckBox;
    @FXML private CheckBox aggCheckBox;

    @FXML private CheckBox dotCheckBox;
    @FXML private CheckBox dashCheckBox;
    @FXML private CheckBox solidCheckBox;

    @FXML private TextField card1TF;
    @FXML private TextField card2TF;

    @FXML 
    private void initialize()
    {
        this.classList = new ArrayList<Core_Class>();

        this.linkerr = false;
        this.lineerr = false;
        this.classerr = false;
    }

    public void initData(Stage window, List<Core_Class> list)
    {
        this.window = window;
        this.classList = list;

        for(Core_Class c : this.classList)
        {
            this.classNames.add(c.get_name());
        }
        
        this.class1Box.setItems(classNames);
        this.class2Box.setItems(classNames);
    }

    @FXML
    private void Draw(ActionEvent event)
    {
        if(this.assCheckBox.isSelected())
        {
            this.aggCheckBox.setDisable(true);
            this.dirAssCheckBox.setDisable(true);
            this.genCheckBox.setDisable(true);
            this.linkType = "ass";
        }
        else if(this.dirAssCheckBox.isSelected())
        {
            this.aggCheckBox.setDisable(true);
            this.assCheckBox.setDisable(true);
            this.genCheckBox.setDisable(true);
            this.linkType = "dir";
        }
        else if(this.aggCheckBox.isSelected())
        {
            this.dirAssCheckBox.setDisable(true);
            this.assCheckBox.setDisable(true);
            this.genCheckBox.setDisable(true);
            this.linkType = "agg";
        }
        else if(this.genCheckBox.isSelected())
        {
            this.aggCheckBox.setDisable(true);
            this.assCheckBox.setDisable(true);
            this.dirAssCheckBox.setDisable(true);
            this.linkType = "gen";
        }  
        else 
        {
            this.aggCheckBox.setDisable(false);
            this.assCheckBox.setDisable(false);
            this.dirAssCheckBox.setDisable(false);
            this.genCheckBox.setDisable(false);
            this.linkerr = true;
        }

        if(this.dotCheckBox.isSelected())
        {
            this.dashCheckBox.setDisable(true);
            this.solidCheckBox.setDisable(true);
            this.lineStyle = "dot";
        }

        else if(this.dashCheckBox.isSelected())
        {
            this.dotCheckBox.setDisable(true);
            this.solidCheckBox.setDisable(true);
            this.lineStyle = "dash";
        }
        else if(this.solidCheckBox.isSelected())
        {
            this.dashCheckBox.setDisable(true);
            this.dotCheckBox.setDisable(true);
            this.lineStyle = "sol";
        }
        else
        {
            this.dashCheckBox.setDisable(false);
            this.dotCheckBox.setDisable(false);
            this.solidCheckBox.setDisable(false);
            this.lineerr = true;
        }

        if(this.class1Box.getValue() == null)
            this.classerr = true;
        else
            this.class1 = this.class1Box.getValue().toString();

        if(this.class1Box.getValue() == null)
            this.classerr = true;
        else
            this.class2 = this.class2Box.getValue().toString();

        this.card1 = this.card1TF.getText();
        this.card2 = this.card2TF.getText();

        if(this.linkerr || this.lineerr || this.classerr)
        {
            Alert a = new Alert(AlertType.WARNING);
            a.setTitle("Unchecked box/boxes");
            a.setContentText("You have to check one option from every section");
            a.show();
            this.linkerr = false;
            this.lineerr = false;
            this.classerr = false;

        }
        else this.window.close();
    }

    public List<String> get_vals()
    {
        List<String> list = new ArrayList<String>();

        list.add(this.linkType);
        list.add(this.lineStyle);
        list.add(this.class1);
        list.add(this.class2);
        list.add(this.card1);
        list.add(this.card2);

        return list;
    }
}
