package gui.seq;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.collections.*;

import uml.seq.*;
import uml.core.*;

public class NewMessageController
{
    public Stage aWindow;
    private Seq_SequenceDiagram sd;

    @FXML private ChoiceBox send_chb;
    @FXML private Choicebox rec_chb;
    @FXML private ComboBox mess_cbx;
    @FXML private CheckBox ack_chb;

    private ObservableList<String> olist = FXCollections.observableArrayList();
    private ObservableList<Integer> olistM = FXCollections.observableArrayList();

    public void init(Stage win, Seq_SequenceDiagram sd)
    {
        this.aWindow = win;
        this.sd = sd;

        this.fill_sender_receiver();
        this.fill_messagelist();
    }

    private void fill_sender_receiver()
    {
        olist.clear();

        for (Seq_Class avail : this.sd.get_actors())
        {
            this.olist.add(avail.get_name());
        }
    }

    private void fill_messagelist()
    {

    }
}