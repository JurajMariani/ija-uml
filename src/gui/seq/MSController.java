package gui.seq;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.*;

import uml.seq.*;
import uml.core.*;


public class MSController implements Initializable
{
    public Stage aWindow;
    private Seq_SequenceDiagram sd;

    @FXML private GridPane gridM;
    private int row_index = 2;
    private int col_index = 1;

    @FXML private ComboBox<String> cbox;

    @FXML private Button addMess;

    private ColumnConstraints col_size;
    private ObservableList<String> cbox_list;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        col_size = new ColumnConstraints();
        col_size.setPercentWidth(50);
        this.gridM.getColumnConstraints().addAll(this.col_size);

        Label sys_label = new Label("System");
        this.gridM.setHalignment(sys_label, HPos.CENTER);
        this.gridM.add(sys_label, 0, 0);
        return;
    }

    public void update_col_width()
    {
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(100/(this.col_index + 1));
        this.gridM.getColumnConstraints().remove(this.col_size);
        this.gridM.getColumnConstraints().addAll(col1);
        System.out.println("updated col width to");
        System.out.println(100/(this.col_index+1));
        this.col_size = col1;
    }

    public void init(Stage cWindow, Seq_SequenceDiagram sqD)
    {
        this.cbox_list = FXCollections.observableArrayList();
        this.aWindow = cWindow;
        this.sd = sqD;

        for (Core_Class avail : this.sd.get_available())
        {
            this.cbox_list.add(avail.get_name());
        }

        this.cbox.setItems(this.cbox_list);
        
        this.cbox.setOnAction(e -> this.shift_layout());

        //this.addMess.setOnAction(e -> this.new_method_popup());
    }


    @FXML void new_method_popup(ActionEvent event)
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("Message.fxml"));
            Stage addMessageWindow = new Stage();

            addMessageWindow.setTitle("New Message");
            addMessageWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            NewMessageController controller = loader.getController();
            controller.init(addMessageWindow, this.sd);
            addMessageWindow.showAndWait();
        }
        catch(Exception e)
        {
        }
    }


    private void shift_layout()
    {
        System.out.println(this.col_index);

        this.gridM.getChildren().remove(this.cbox);
        Label classname = new Label(this.cbox.getValue());
        this.gridM.add(classname, this.col_index, 0);
        this.gridM.setHalignment(classname, HPos.CENTER);
        this.sd.add_actor(this.sd.get_actor_ref(this.cbox.getValue()));
        
        this.col_index++;
        System.out.println(this.col_index);
        this.gridM.add(this.cbox, this.col_index, 0);
        this.gridM.setHalignment(this.cbox, HPos.CENTER);
        this.update_col_width();
    }


}