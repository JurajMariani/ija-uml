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
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.collections.*;

import uml.seq.*;
import uml.core.*;

public class NewMessageController
{
    public Stage aWindow;
    private Seq_SequenceDiagram sd;
    private Seq_Class system;

    @FXML private ComboBox<String> send_chb;
    @FXML private ComboBox<String> rec_chb;
    @FXML private ComboBox<String> mess_cbx;
    @FXML private TextArea ta;
    @FXML private CheckBox ack_chb;
    @FXML private CheckBox const_chb;
    @FXML private Button confirm;

    private int sInstance;
    private int dInstance;

    private boolean mTrigger = false;
    //future use
    private boolean canAck = true;

    private ObservableList<String> olist = FXCollections.observableArrayList();
    private ObservableList<Integer> olistM = FXCollections.observableArrayList();

    public void init(Stage win, Seq_SequenceDiagram sd, int system_id)
    {
        this.aWindow = win;
        this.sd = sd;
        this.system = this.sd.get_actor_by_id(system_id);

        this.olist.clear();

        for (Seq_Class avail : this.sd.get_actors())
        {
            this.olist.add(avail.get_name() + " ID:" + avail.get_instance());
        }

        
        this.send_chb.setItems(this.olist);
        this.rec_chb.setDisable(true);
        this.mess_cbx.setDisable(true);
        this.ack_chb.setDisable(true);

        this.send_chb.setOnAction(e -> this.fill_receiver());
        this.rec_chb.setOnAction(e -> this.fill_messagelist());
    }

    private void fill_receiver()
    {
        this.send_chb.setDisable(true);

        String[] str_id = this.send_chb.getValue().toString().split(":",0);
        this.sInstance = Integer.parseInt(str_id[str_id.length - 1]);

        this.olist = FXCollections.observableArrayList();

        if (this.sd.get_actor_by_id(this.sInstance).get_instance() == 1)
        {
            for(Seq_Class c : this.sd.get_actors())
                if (c.get_instance() != 1)
                    this.olist.add(c.get_name() + " ID:" + c.get_instance());
        }
        else
        {
            for (Seq_Class avail : this.sd.get_actors_with_bond_to(this.sInstance))
            {
                this.olist.add(avail.get_name() + " ID:" + avail.get_instance());
            }
            this.olist.add(this.system.get_name() + " ID:" + this.system.get_instance());
        }

        this.rec_chb.setDisable(false);
        this.rec_chb.setItems(this.olist);
    }

    private void fill_messagelist()
    {
        this.rec_chb.setDisable(true);

        String[] str_id = this.rec_chb.getValue().toString().split(":",0);
        this.dInstance = Integer.parseInt(str_id[str_id.length - 1]);
        
        this.olist = FXCollections.observableArrayList();
        System.out.println("toci sa, " + this.dInstance);

        for (Core_Method meth : this.sd.get_actor_by_id(this.dInstance).get_reference_class().get_methods())
        {
            System.out.println("tociiiiiii");
            this.olist.add(meth.get_str_method());
        }


        this.mess_cbx.setDisable(false);
        this.mess_cbx.setItems(this.olist);
        this.ack_chb.setDisable(false);
    }

    @FXML void swap_ack()
    {
        if (this.ack_chb.isSelected())
        {
            this.mess_cbx.setDisable(true);
            this.const_chb.setDisable(true);
        }
        else
        {
            this.mess_cbx.setDisable(false);
            this.const_chb.setDisable(false);
        }
    }

    @FXML void swap_const()
    {
        if (this.const_chb.isSelected())
        {
            this.mess_cbx.setDisable(true);
            this.ack_chb.setDisable(true);
        }
        else
        {
            this.mess_cbx.setDisable(false);
            this.ack_chb.setDisable(false);
        }
    }

    @FXML void create_message()
    {
        if((!this.const_chb.isSelected() && !this.ack_chb.isSelected() && this.mess_cbx.getValue() == null) || this.rec_chb.getValue() == null || this.send_chb.getValue() == null)
            return;
            
        String name;
        Core_Method ref = null;

        if(this.ack_chb.isSelected())
        {
            name = "ok";
        }
        else
        {
            if(this.const_chb.isSelected())
            {
                name = "init";
            }
            else
            {
                name = this.mess_cbx.getValue().toString();
            }
        }

        if (!this.ack_chb.isSelected() && !this.const_chb.isSelected())
        {
            String[] str_id = this.rec_chb.getValue().toString().split(":",0);
            int eInstance = Integer.parseInt(str_id[str_id.length - 1]);
            String method = this.mess_cbx.getValue().toString();
            System.out.println(method);
            name = method.substring(1, method.indexOf('('));
            ref = this.sd.get_actor_by_id(this.dInstance).get_reference_class().get_method(name);
            System.out.println(name);
        }
        
        name = name + "(" + ((ta.getText() == null) ? "" : ta.getText()) + ")";

        if (this.sd.get_actor_by_id(this.dInstance).is_constructed() && this.const_chb.isSelected())
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR: Class already constructed");
            alert.setHeaderText("Class " + this.rec_chb.getValue().toString() + " has already been initialised.");
            alert.setContentText("Class has already been initialised.\nCannot send more than one initialisation message.");
            alert.showAndWait();
            return;
        }

        Seq_Message mess = this.sd.add_message(this.sd.get_actor_by_id(this.sInstance), this.sd.get_actor_by_id(this.dInstance), name, ref, this.ack_chb.isSelected(), this.const_chb.isSelected());
        if(mess == null)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR: Class not constructed");
            alert.setHeaderText("Class " + this.rec_chb.getValue().toString() + " has not been initialised.");
            alert.setContentText("Class has to be initialised in order to send messages.\nPlease send an initialisation message first.");
            alert.showAndWait();
            return;
        }

        this.aWindow.close();
    }
}