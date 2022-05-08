package gui.seq;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Line; 
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.*;

import uml.seq.*;
import uml.core.*;

/**
 * CLASS: MESSAGE UPDATE CONTROLLER
 * 
 * <p> Class MessageUpdateController handles the Message Update popup window
 *
 * @author Juraj Mariani
 */
public class MessageUpdateController
{
    public Stage aWindow;
    private Seq_SequenceDiagram sd;
    private AnchorPane ap;

    @FXML private ComboBox<String> messList;
    @FXML private ComboBox<String> nMessList;
    @FXML private CheckBox ack;
    @FXML private CheckBox chDir;
    @FXML private TextField params;
    @FXML private Button apply;

    ObservableList<String> olist = FXCollections.observableArrayList();

    /**
     * Initializator
     * @param win Window Stage
     * @param sd Reference to Seq. Diagram
     * @param ap Reference to GUI AnchorPane
     */
    public void init(Stage win, Seq_SequenceDiagram sd, AnchorPane ap)
    {
        this.aWindow = win;
        this.sd = sd;
        this.ap = ap;


        this.fillOlist();
        this.messList.setOnAction(e -> this.activate());
    }

    /**
     * Fills the "Select Message" ComboBox with Messages present in the diagram
     */
    private void fillOlist()
    {
        ObservableList<String> olist = FXCollections.observableArrayList();

        for (Seq_Message msg : this.sd.get_messages())
            olist.add(msg.get_name() + " ID:" + msg.get_instance());

        this.messList.getItems().addAll(olist);
        this.nMessList.setDisable(true);
        this.params.setDisable(true);
    }

    /**
     * Fill the ComboBox with options of new Messages to replace the current Message
     */
    @FXML void reload_cbox()
    {
        this.nMessList.setDisable(true);

        this.nMessList.getItems().clear();

        String[] str_id = this.messList.getValue().toString().split(":",0);
        int mInst = Integer.parseInt(str_id[str_id.length - 1]);

        ObservableList<String> olist = FXCollections.observableArrayList();

        for (Core_Method met : ((!this.chDir.isSelected())? this.sd.get_message_by_id(mInst).get_receiver().get_reference_class().get_methods() : this.sd.get_message_by_id(mInst).get_caller().get_reference_class().get_methods()))
            olist.add(met.get_str_method());

        this.nMessList.getItems().addAll(olist);

        this.nMessList.setDisable(false);
    }

    /**
     * When a Message has beem selected ban the Change of Selection
     */
    private void activate()
    {
        this.messList.setDisable(true);
        this.nMessList.setDisable(false);
        this.params.setDisable(false);
    }

    /**
     * Apply button onClick() - update the Text/Line/Polygon
     */
    @FXML void updateSelected()
    {
        if (this.nMessList.getValue() == null && this.params.getText().equals(""))
            this.aWindow.close();
        
        String[] str_id = this.messList.getValue().toString().split(":",0);
        int mInst = Integer.parseInt(str_id[str_id.length - 1]);

        Seq_Message message = this.sd.get_message_by_id(mInst);
        String name = this.messList.getValue().toString();

        List<Node> line = message.get_line();

        if (message.is_constructor())
        {
            name = this.nMessList.getValue().toString();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR: Cannot change constructor");
            alert.setHeaderText("Message " + this.messList.getValue().toString() + " is a constructor message.");
            alert.setContentText("Constructor messages cannot be changed.");
            alert.showAndWait();
            this.aWindow.close();
            return;
        }

        /** Change value of Text */
        if (this.nMessList.getValue() != null)
        {
            name = this.nMessList.getValue().toString();
            name = name.substring(1, name.indexOf('('));
            name = name + "(" + this.params.getText() + ")";
            message.rename(name);
            name = name + " ID:" + message.get_instance();
            message.set_ref(message.get_receiver().get_reference_class().get_method(nMessList.getValue().toString()));
        }

        /** Make line dotted */
        /** Acknowledgement overrides new method selection */
        if (this.ack.isSelected())
        {
            name = "ok(" + this.params.getText() + ")";
            message.rename(name);
            name = name + " ID:" + message.get_instance();
            message.set_ref(null);
        }

        /** Change line text == Message name */
        /** Change line from full to dashed and vice versa */
        for (Node node : line)
        {
            if (node instanceof Text)
            {
                ((Text)node).setText(name);
            }
            if (node instanceof Line)
            {
                if (this.ack.isSelected())
                    ((Line)node).getStrokeDashArray().addAll(6d);
                else
                    ((Line)node).getStrokeDashArray().clear();
            }
        }

        /** Change line direction */
        if (this.chDir.isSelected())
        {
            for (Node node : line)
            {
                if (node instanceof Line)
                {
                    /** Only one line */
                    if(line.size() == 3)
                    {
                        double coords;
                        coords = ((Line)node).getStartX();
                        ((Line)node).setStartX(((Line)node).getEndX());
                        ((Line)node).setEndX(coords);
                    }
                    else
                    {
                        /** Three lines -> message to self */
                    }
                }
            }
            
            Node node = line.get(line.size() - 2);

            ((Polygon)node).getPoints().remove(0);
            ((Polygon)node).getPoints().remove(1);
            ((Polygon)node).getPoints().remove(2);
            
            /** 6 double values (x,y),(x,y),(x,y) - only X coordinate gets changed*/
            /** that would be indexes 0,2 and 4 */

            if (line.size() == 3)
            {
                Line l = ((Line)line.get(0));
                ((Polygon)node).getPoints().add(0,l.getEndX());
        
                if (l.getStartX() < l.getEndX())
                {
                    ((Polygon)node).getPoints().add(2, l.getEndX() - 10);
                    ((Polygon)node).getPoints().add(4, l.getEndX() - 10);
                }
                else if (l.getStartX() == l.getEndX())
                {
                    /** Message to self */
                }
                else
                {
                    ((Polygon)node).getPoints().add(2, l.getEndX() + 10);
                    ((Polygon)node).getPoints().add(4, l.getEndX() + 10);
                }
            }
        }
        
        this.aWindow.close();
    }
}