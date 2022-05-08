package gui.seq;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.*;

import uml.seq.*;
import uml.core.*;

/**
 * CLASS: REMOVE MESSAGE CONTROLLER
 * 
 * <p> Class RemoveMessageController defines a GUI controller for "RemoveMessage.fxml" scene
 *
 * @author Juraj Mariani
 */
public class RemoveMessageController
{
    public Stage aWindow;
    private Seq_SequenceDiagram sd;
    private GridPane gp;
    private int mRowIdx;

    private ObservableList<String> olist = FXCollections.observableArrayList();
    private List<Node> nlist;

    @FXML private ComboBox<String> messageList;
    @FXML private Button confirm;

    /**
     * Controller Constructor
     * @param cWin Window Stage
     * @param sd Reference to a Seq. Diagram
     * @param g GUI GridPane
     * @param maxRow Last filled row index
     * @param nlist List of nodes that get deleted (OUTGOING)
     * @param id List of deleted Message IDs (OUTGOING)
     */
    public void init(Stage cWin, Seq_SequenceDiagram sd, GridPane g, int maxRow, List<Node> nlist, List<Integer> id)
    {
        this.aWindow = cWin;
        this.sd = sd;
        this.gp = g;
        this.mRowIdx = maxRow;

        if (sd == null || g == null || nlist == null || id ==null)
            System.out.println("NNNNNNNUUUUUUULLLLLL");

        this.nlist = nlist;

        this.fillCbox();

        this.confirm.setOnAction(e -> this.confirmSelection(id));
    }

    /**
     * Fill a ComboBox with Messages present in the Diagram
     */
    private void fillCbox()
    {
        for (Seq_Message mess : this.sd.get_messages())
        {
            this.olist.add(mess.get_name() + " ID:" + mess.get_instance());    
        }
        this.messageList.getItems().addAll(this.olist);
    }

    /**
     * Confirm Button OnClick method - fills the OUTGOING Lists with Rectangle Object to be removed and removed Message ID
     * @param messId
     */
    private void confirmSelection(List<Integer> messId)
    {
        if(this.messageList.getValue() != null)
        {
            String[] str_id = this.messageList.getValue().toString().split(":",0);
            int mId = Integer.parseInt(str_id[str_id.length - 1]);

            messId.add(mId);

            Seq_Message rem = this.sd.get_message_by_id(mId);

            int mRow = 2 + this.sd.get_messages().indexOf(rem);
            System.out.println("ROW: " + mRow);

            List<Integer> idxs = new ArrayList<Integer>();

            System.out.println(this.gp.getChildren());

            for (Node gridObject : this.gp.getChildren())
            {
                if (!is_in_list(idxs, this.gp.getRowIndex(gridObject)))
                    idxs.add(this.gp.getRowIndex(gridObject));
            }

            for (Node gridObject : this.gp.getChildren())
            {
                System.out.println("Object row: " + this.gp.getRowIndex(gridObject));
                
                if (idxs.get(mRow) == this.gp.getRowIndex(gridObject))
                {
                    System.out.println("Toto: " + gridObject);
                    this.nlist.add(gridObject);
                }
            }

            this.aWindow.close();
        }
    }

    /**
     * @param x List of integers
     * @param a Int value
     * @return True - Int is in list, otherwise False
     */
    private boolean is_in_list(List<Integer> x, int a)
    {
        for (Integer n : x)
        {
            if (n.intValue() == a)
                return true;    
        }

        return false;
    }
}