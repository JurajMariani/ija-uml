package gui.seq;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.*;

import uml.seq.*;
import uml.core.*;

/**
 * CLASS: REMOVE CLASS CONTROLLER
 * 
 * <p> Class RemoveClassController handles Selection window for ckass removal
 *
 * @author Juraj Mariani
 */
public class RemoveClassController
{
    public Stage aWindow;
    private Seq_SequenceDiagram sd;
    private GridPane gp;

    @FXML private ComboBox<String> classList;
    @FXML private Button confirm;

    /**
     * Initializator
     * @param cWin Window Stage
     * @param sd Reference to Seq. Diagram
     * @param g Reference to GUI GridPane
     * @param maxRow Reference to GUI AnchorPane
     * @param nlist List of GUI Nodes to be removed (OUTGOING)
     * @param id List of Message IDs to be removed (OUTGOING)
     */
    public void init(Stage cWin, Seq_SequenceDiagram sd, GridPane g, int maxRow, List<Node> nlist, List<Integer> id)
    {
        this.aWindow = cWin;
        this.sd = sd;
        this.gp = g;

        this.fillCbox();

        this.confirm.setOnAction(e -> this.confirmSelection(nlist, id));
    }

    /**
     * Fill the Class selecting ComboBox with existing Actors
     */
    private void fillCbox()
    {
        ObservableList<String> itemList = FXCollections.observableArrayList();
        for (Seq_Class cls : this.sd.get_actors())
        {
            if (cls.get_instance() != 1)
                itemList.add(cls.get_name() + " ID:" + cls.get_instance());
        }

        this.classList.getItems().addAll(itemList);
    }

    /**
     * Button onClick()
     * @param nlist List (OUTGOING) of GUI Nodes to be removed
     * @param id List (OUTGOING) of Message IDs to be removed
     */
    private void confirmSelection(List<Node> nlist, List<Integer> id)
    {
        if (this.classList.getValue() != null)
        {
            String[] str_id = this.classList.getValue().toString().split(":",0);
            int cInst = Integer.parseInt(str_id[str_id.length - 1]);

            System.out.println("User selected smthng");
            List<Seq_Message> mList = this.sd.get_active_messages_with(cInst);

            if(mList == null)
                return;

            System.out.println(mList);
            for (Seq_Message m : mList)
                id.add(m.get_instance());

            
            int column = this.sd.get_actors().indexOf(this.sd.get_actor_by_id(cInst));
            
            for (Node n : this.gp.getChildren())
            {
                if (this.gp.getColumnIndex(n) == column)
                    nlist.add(n);
            }

            this.sd.remove_actor(this.sd.get_actor_by_id(cInst));

            System.out.println(id);
            System.out.println(nlist);

            this.aWindow.close();
        }

        return;
    }
}