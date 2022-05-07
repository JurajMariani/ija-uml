package gui.seq;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.*;

import uml.seq.*;
import uml.core.*;
import uml.io.load_seq_xml;
import uml.io.save_seq_xml;

public class IOpanelController
{
    public Stage aWindow;
    private Seq_SequenceDiagram sd;
    private GridPane gp;
    private AnchorPane ap;

    @FXML private CheckBox storeO;
    @FXML private CheckBox loadO;
    @FXML private ComboBox<String> fileSelect;
    @FXML private TextField filename;
    @FXML private Button io;

    public void init(Stage cWin, Seq_SequenceDiagram sd, GridPane g, AnchorPane a)
    {
        this.aWindow = cWin;
        this.sd = sd;
        this.gp = g;
        this.ap = a;

        this.fileSelect.setDisable(true);
        this.filename.setDisable(true);
        this.io.setDisable(true);
    }

    @FXML void doIO()
    {

        if(this.storeO.isSelected())
        {
            /** Handle diagram save */
            System.out.println("Saving...");
            save_seq_xml save = this.sd.get_saver();

            if (!this.filename.getText().equals(""))
                save.change_filename(this.filename.getText());
            
            if (save.save() != 0)
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR: Saving unsuccessful");
                alert.setHeaderText("An unexpected error happened during saving process.");
                alert.setContentText("");
                alert.showAndWait();
            }

            this.aWindow.close();
        }
        if(this.loadO.isSelected())
        {
            /** Handle diagram load */
            if (this.fileSelect.getValue() != null)
            {
                System.out.println("Loading...");

                load_seq_xml loader = new load_seq_xml(this.fileSelect.getValue().toString(), this.sd, this.gp, this.ap);
                if (loader.load() == 1)
                    System.out.println("Error");

                this.aWindow.close();
            }
            else
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR: No file selected");
                alert.setHeaderText("Please select a file to load.");
                alert.setContentText("");
                alert.showAndWait();
            }
        }
    }

    @FXML void flipS()
    {
        if(this.storeO.isSelected())
        {
            this.loadO.setDisable(true);
            this.filename.setDisable(false);
            this.io.setDisable(false);
        }
        else
        {
            this.filename.setDisable(true);
            this.loadO.setDisable(false);
            this.io.setDisable(true);
        }
    }

    @FXML void flipL()
    {
        if(this.loadO.isSelected())
        {
            this.storeO.setDisable(true);
            this.fileSelect.setDisable(false);
            this.io.setDisable(false);

            ObservableList<String> olist = FXCollections.observableArrayList();

            File data = new File("./data/");
            for( File f : data.listFiles())
            {
                if (f.getName().substring(f.getName().length() - 5, f.getName().length()).equals(".sxml"))
                {
                    olist.add(f.getName());
                }
            }

            this.fileSelect.getItems().clear();
            this.fileSelect.getItems().addAll(olist);
        }
        else
        {
            this.fileSelect.setDisable(true);
            this.storeO.setDisable(false);
            this.io.setDisable(true);
        }
    }
}