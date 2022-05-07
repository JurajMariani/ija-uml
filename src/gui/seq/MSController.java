package gui.seq;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Line; 
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.collections.*;

import uml.seq.*;
import uml.core.*;


public class MSController implements Initializable
{
    public Stage aWindow;
    private Seq_SequenceDiagram sd;

    @FXML private GridPane gridM;
    private int row_index = 1;
    private int col_index = 1;
    private Seq_Class system;

    @FXML private ComboBox<String> cbox;

    @FXML private Button addMess;
    @FXML private Button removeClass;
    @FXML private Button removeMess;
    @FXML private Button changeMessage;
    @FXML private Button saveDia;

    private ColumnConstraints col_size;
    private ObservableList<String> cbox_list;

    @FXML private AnchorPane ap;

    private double xStart = 54;
    private double yStart = 65.5 + 20;
    private double xStep = 150;
    private double yStep = 40;
    private double xStepWidth = 15.0;

    private double xNextStep = xStep + xStepWidth + 13;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        col_size = new ColumnConstraints();
        col_size.setPrefWidth(80.0);
        this.gridM.getColumnConstraints().add(this.col_size);
        this.gridM.getColumnConstraints().add(this.col_size);

        Label sys_label = new Label("System");
        this.gridM.add(sys_label, 0, 0);
        this.gridM.setHalignment(sys_label, HPos.CENTER);
        this.gridM.setAlignment(Pos.TOP_LEFT);
        this.gridM.setHgap(100.0);
    }

    public void update_col_width()
    {
        this.gridM.getColumnConstraints().clear();
        for(int i = 0; i <= this.col_index; i++)
        {
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setFillWidth(true);
        }
    }

    public void update_row_height()
    {
        this.gridM.getRowConstraints().clear();
        RowConstraints row = new RowConstraints();
        row.setPrefHeight(50);
        for(int i = 1; i <= row_index; i++)
        {
            row = new RowConstraints();
            row.setPrefHeight(this.yStep);
        }
    }

    public void init(Stage cWindow, Seq_SequenceDiagram sqD)
    {
        this.cbox_list = FXCollections.observableArrayList();
        this.aWindow = cWindow;
        this.sd = sqD;
        this.system = this.sd.get_actor_by_id(1);

        this.gridM.setGridLinesVisible(false);
        //System.out.println(this.gridM.getChildren());

        for (Core_Class avail : this.sd.get_available())
        {
            this.cbox_list.add(avail.get_name());
        }

        for(int i = 1; i <= this.row_index; i++)
        {
            Rectangle rectum = new Rectangle(this.xStepWidth, this.yStep);
            this.gridM.add(rectum, 0, i);
            this.gridM.setHalignment(rectum, HPos.CENTER);
        }
        //cez get row constraints - 1 item pre kazdu row, tak isto pre stlpce, tak prepis
        
        this.update_col_width();
        this.update_row_height();

        Node gr = null;
        for (Node n : this.gridM.getChildren())
        {
            if (n instanceof Group)
                gr = n;
        }

        this.gridM.getChildren().remove(gr);

        this.cbox.setItems(this.cbox_list);
        
        this.cbox.setOnAction(e -> this.shift_layout());
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

            int prev_size = this.sd.get_messages().size();

            Scene scene = new Scene(loader.load());

            NewMessageController controller = loader.getController();
            controller.init(addMessageWindow, this.sd, this.system.get_instance());

            addMessageWindow.setScene(scene);
            addMessageWindow.showAndWait();

            if(this.sd.get_messages().size() == prev_size + 1)
            {
                this.generate_lifeline();
                this.draw_message();
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception!");
            e.printStackTrace();
        }
    }


    public void update_class_label()
    {
        
    }


    private double[] get_line_coords(Seq_Message mess)
    {

        Seq_Class s = mess.get_caller();
        Seq_Class e = mess.get_receiver();
        int sIndex = this.sd.get_actors().indexOf(s);
        int eIndex = this.sd.get_actors().indexOf(e);

        double[] ret = new double[4];

        // ret[0] - leftX
        // ret[1] - leftY
        // ret[2] - rightX
        // ret[3] - rightY
        
        ret[0] = this.xStart + ((sIndex - 1 < 0 ? 0 : sIndex - 1) * this.xNextStep) + (sIndex == 0 ? 0 : this.xStep);
        ret[1] = ((this.row_index - 2) * this.yStep) + this.yStart;
        ret[2] = this.xStart + ((eIndex - 1 < 0 ? 0 : eIndex - 1) * this.xNextStep) + (eIndex == 0 ? 0 : this.xStep);
        ret[3] = (this.row_index - 2) * this.yStep + this.yStart;

        //System.out.println(ret[0] + " " +  ret[1] + " " + ret[2] + " " + ret[3]);

        return ret;
    }


    private Polygon get_message_polygon(Seq_Message mess, double[] coors)
    {
        Seq_Class s = mess.get_caller();
        Seq_Class e = mess.get_receiver();

        int sIndex = this.sd.get_actors().indexOf(s);
        int eIndex = this.sd.get_actors().indexOf(e);

        System.out.println(coors[2]+" "+ coors[3]);

        Polygon p = new Polygon();
        if (sIndex < eIndex)
        {
            p.getPoints().addAll(new Double[]{coors[2], coors[3], coors[2] - 10, coors[3] - 6, coors[2] - 10, coors[3] + 6});
        }
        else if (sIndex == eIndex)
        {
            //message to self
        }
        else
        {
            p.getPoints().addAll(new Double[]{coors[2], coors[3], coors[2] + 10, coors[3] - 6, coors[2] + 10, coors[3] + 6});
        }

        return p;
    }


    private void draw_message()
    {
        System.out.println("------------ DRAW MESSAGE -----------");
        System.out.print(this.sd.get_messages());
        System.out.println("\n-------------------------------------");

        List<Seq_Message> messList = this.sd.get_messages();
        Seq_Message mess = messList.get(messList.size() - 1);

        Line messageLine = new Line();

        double[] coords = get_line_coords(mess);
        Polygon end = get_message_polygon(mess, coords);
        end.setStroke(Color.BLUE);

        messageLine.setStartX(coords[0]);
        messageLine.setStartY(coords[1]);
        messageLine.setEndX(coords[2]);
        messageLine.setEndY(coords[3]);

        if(mess.is_ack())
        {
            messageLine.getStrokeDashArray().addAll(6d);
        }
        messageLine.setStroke(Color.BLUE);
        List<Line> messLines = new ArrayList<Line>();
        messLines.add(messageLine);
        Text messageText = new Text(mess.get_name() + " ID:" + mess.get_instance());
        if(coords[0] > coords[2])
        {
            messageText.setX(coords[2] + 15);
            messageText.setY(coords[3] - 4);
        }
        else
        {
            messageText.setX(coords[2] - (messageText.getLayoutBounds().getWidth() + 15));
            messageText.setY(coords[3] - 4);
        }

        ap.getChildren().addAll(messLines);
        ap.getChildren().add(end);
        ap.getChildren().add(messageText);

        mess.set_line(messLines, end, messageText);
    }


    private void remake_cbox()
    {
        this.cbox = new ComboBox<String>();
        this.cbox.setItems(this.cbox_list);
        this.cbox.setPromptText("Select Actor");
        this.cbox.setOnAction(e -> this.shift_layout());
    }

    private void generate_lifeline()
    {
        Seq_Message lastMessage = this.sd.get_messages().get(this.sd.get_messages().size() - 1);

        int column = 0;

        for (Seq_Class actor : this.sd.get_actors())
        {
            if(actor.get_last_activity())
            {
                Rectangle neu = new Rectangle(this.xStepWidth, this.yStep);
                this.gridM.add(neu, column, this.row_index + 1);
                this.gridM.setHalignment(neu, HPos.CENTER);
            }
            else
            {
                Rectangle neu = new Rectangle(2.0, (this.yStep / 2));
                this.gridM.add(neu, column, this.row_index + 1);
                this.gridM.setHalignment(neu, HPos.CENTER);
            }
            column++;
        }
        this.row_index++;
    }


    private void generate_lifeline_spec(int col, Seq_Class c)
    {
        Rectangle rec;

        for(int i = 1; i < c.get_activity().size() + 1; i++)
        {
            rec = new Rectangle(2.0, (this.yStep / 2));
            this.gridM.add(rec, col, i);
            this.gridM.setHalignment(rec, HPos.CENTER);
        }
    }


    private void shift_layout()
    {
        Seq_Class newA = this.sd.add_actor(this.sd.get_actor_ref(this.cbox.getValue()), this.row_index);

        this.gridM.getChildren().remove(this.cbox);
        Label classname = new Label(":" + this.cbox.getValue() + " ID:" + newA.get_instance());
        this.gridM.add(classname, this.col_index, 0);
        this.gridM.setHalignment(classname, HPos.CENTER);

        this.generate_lifeline_spec(this.col_index, newA);
        
        System.out.println("Col index = " + this.col_index);
        if(this.col_index != 8)
        {
            this.remake_cbox();

            System.out.print(this.sd.get_actors());
            this.col_index++;
            System.out.println(this.col_index);
            this.gridM.add(this.cbox, this.col_index, 0);
            this.gridM.setHalignment(this.cbox, HPos.CENTER);
            this.update_col_width();
        }
    }


    private void remove_nodes_gp(List<Node> rm)
    {
        for(Node a : rm)
        {
            this.gridM.getChildren().remove(a);
        }
    }

    private void remove_nodes_ap(List<Node> rm)
    {
        for(Node a : rm)
        {
            this.ap.getChildren().remove(a);
        }
    }

    private void shift_layout_x(int col)
    {
        List<Node> auxNodeArray = new ArrayList<Node>();
        for (Node n : this.gridM.getChildren())
        {
            if(this.gridM.getColumnIndex(n) > col)
                auxNodeArray.add(n);
        }

        int cCol;
        int row;
        for (Node n : auxNodeArray)
        {
            cCol = this.gridM.getColumnIndex(n);
            row  = this.gridM.getRowIndex(n);
            this.gridM.getChildren().remove(n);
            this.gridM.add(n, cCol - 1, row);
        }

        System.out.println("Shifting layout");
        System.out.println("i: " + (col + 1));
        System.out.println("max: " + this.sd.get_actors().size());
        for (int i = col; i < this.sd.get_actors().size(); i++)
        {
            List<Seq_Message> m = this.sd.get_active_messages_with(this.sd.get_actors().get(i).get_instance());
            System.out.println("xStep: " + this.xNextStep);
            for (Seq_Message mess : m)
            {
                List<Node> nod = mess.get_line();
                if (nod.size() == 3)
                {
                    Line l = ((Line)nod.get(0));
                    Polygon p = ((Polygon)nod.get(1));
                    Text t = ((Text)nod.get(2));

                    t.setX(t.getX() - this.xNextStep);

                    if (l.getStartX() > l.getEndX())
                    {
                        System.out.println(l.getStartX());
                        l.setStartX(l.getStartX() - this.xNextStep);
                        System.out.println(l.getStartX());
                    }
                    else
                    {
                        l.setEndX(l.getEndX() - this.xNextStep);

                        ObservableList<Double> pts = p.getPoints();
                        System.out.println(pts);
                        ObservableList<Double> neu = FXCollections.observableArrayList();
                        for(int j = 0; j < 6; j++)
                        {
                            if(j % 2 == 1)
                                neu.add(pts.get(j));
                            else
                                neu.add(pts.get(j) - this.xNextStep);
                        }
                        p.getPoints().clear();
                        p.getPoints().addAll(neu);
                        System.out.println(neu);
                    }
                }
                else
                {
                    //message to self
                } 
            }
        }
    }

    @FXML void remove_class()
    {
        FXMLLoader loader;
        List<Node> removed = new ArrayList<Node>();
        List<Integer> id = new ArrayList<Integer>();

        try
        {
            loader = new FXMLLoader(getClass().getResource("RemoveClass.fxml"));
            Stage rmClassWindow = new Stage();

            rmClassWindow.setTitle("Remove Class");
            rmClassWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            RemoveClassController controller = loader.getController();
            controller.init(rmClassWindow, this.sd, this.gridM, this.row_index, removed, id);

            rmClassWindow.setScene(scene);
            rmClassWindow.showAndWait();
        }
        catch(Exception e)
        {
            System.out.println("Exception!");
            //e.printStackTrace();
        }

        System.out.println(id);
        for (Integer i : id)
        {
            this.handle_message_romoval(i);    
        }

        this.remove_nodes_gp(removed);

        int idx = this.gridM.getColumnIndex(removed.get(0));
        this.shift_layout_x(idx);
        this.update_col_width();
        this.col_index--;
    }

    @FXML void remove_message()
    {
        FXMLLoader loader;
        List<Node> removed = new ArrayList<Node>();
        List<Integer> id = new ArrayList<Integer>();

        try
        {
            loader = new FXMLLoader(getClass().getResource("RemoveMessage.fxml"));
            Stage rmMessageWindow = new Stage();

            rmMessageWindow.setTitle("Remove Message");
            rmMessageWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            RemoveMessageController controller = loader.getController();
            controller.init(rmMessageWindow, this.sd, this.gridM, this.row_index, removed, id);

            rmMessageWindow.setScene(scene);
            rmMessageWindow.showAndWait();
        }
        catch(Exception e)
        {
            System.out.println("Exception!");
            //e.printStackTrace();
        }

        this.handle_message_romoval(id.get(0));
    }


    private List<Node> get_message_objects(int messageId)
    {
        System.out.println("id " + messageId);
        Seq_Message m = this.sd.get_message_by_id(messageId);
        System.out.println(m);
        int row = 2 + this.sd.get_messages().indexOf(m);
        System.out.println("ROW: " + row);

        List<Node> rem = new ArrayList<Node>();
        for (Node n : this.gridM.getChildren())
        {
            if (this.gridM.getRowIndex(n) == row)
                rem.add(n);
        }

        return rem;
    }


    private void handle_message_romoval(int messId)
    {
        List<Node> rm = this.get_message_objects(messId);
        if (rm.size() > 0)
        {
            this.remove_nodes_gp(rm);

            move_up(this.sd.get_messages().indexOf(this.sd.get_message_by_id(messId)));

            this.remove_nodes_ap(this.sd.get_message_by_id(messId).get_line());
            this.row_index--;
            this.sd.remove_message(this.sd.get_message_by_id(messId));

            this.adapt_rows();
        }
    }


    private void adapt_rows()
    {
        boolean isIn = false;
        int i = 0;

        for(i = 1; i < this.row_index + 1; i++)
        {
            isIn = false;
            for (Node n : this.gridM.getChildren())
            {
                //System.out.println("idx: " + this.gridM.getRowIndex(n));
                if (this.gridM.getRowIndex(n) == i)
                    isIn = true;
            }

            //System.out.println("LOOP END");
            if (!isIn)
            {
                break;
            }
        }
        //System.out.println("i: " + i + ", ridx: " + (this.row_index + 1));
        List<Node> toBeMovedUp = new ArrayList<Node>();
        if (!isIn)
        {
            int max = this.get_max_filled_row_index();
            //Node swapNode;
            //System.out.println("i: " + i + " maxrow: " + max);
            for(i += 1; i <= max; i++)
            {
                for (Node n : this.gridM.getChildren())
                {
                    if (this.gridM.getRowIndex(n) == i)
                        toBeMovedUp.add(n);
                }
            }
        }

        int r;
        int c;
        for (Node node : toBeMovedUp)
        {
            r = this.gridM.getRowIndex(node);
            c = this.gridM.getColumnIndex(node);
            this.gridM.getChildren().remove(node);
            this.gridM.add(node, c, r - 1);
        }
        System.out.println("Adapt rows");
    }


    private int get_max_filled_row_index()
    {
        int maxRow = 0;
        for (Node n : this.gridM.getChildren())
        {
            if (this.gridM.getRowIndex(n) > maxRow)
                maxRow = this.gridM.getRowIndex(n);
        }

        return maxRow;
    }

    private int get_max_filled_col_index()
    {
        int maxCol = 0;
        for (Node n : this.gridM.getChildren())
        {
            if (this.gridM.getColumnIndex(n) > maxCol)
                maxCol = this.gridM.getColumnIndex(n);
        }

        return maxCol;
    }


    @FXML void changeMess()
    {
        FXMLLoader loader;

        try
        {
            loader = new FXMLLoader(getClass().getResource("MessageUpdate.fxml"));
            Stage updateWindow = new Stage();

            updateWindow.setTitle("Update Message");
            updateWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            MessageUpdateController controller = loader.getController();
            controller.init(updateWindow, this.sd, this.ap);

            updateWindow.setScene(scene);
            updateWindow.showAndWait();
        }
        catch(Exception e)
        {
            System.out.println("Exception!");
            //e.printStackTrace();
        }
    }


    private void move_up(int from_idx)
    {
        int lcnter = 0;
        int pcnter = 0;
        int tcnter = 0;

        for(Node a : this.ap.getChildren())
        {
            if (a instanceof Line)
            {
                //System.out.println("Line " + lcnter);
                if(lcnter > from_idx)
                {
                    ((Line)(a)).setStartY(((Line)(a)).getStartY() - this.yStep);
                    ((Line)(a)).setEndY(((Line)(a)).getEndY() - this.yStep);
                }
                lcnter++;
            }
            if(a instanceof Polygon)
            {
                //System.out.println("Poly " + pcnter);
                if(pcnter > from_idx)
                {
                    ObservableList<Double> pts = ((Polygon)(a)).getPoints();
                    ObservableList<Double> neu = FXCollections.observableArrayList();
                    for(int i = 0; i < 6; i++)
                    {
                        if(i % 2 == 0)
                            neu.add(pts.get(i));
                        else
                            neu.add(pts.get(i) - this.yStep);
                    }
                    ((Polygon)(a)).getPoints().clear();
                    ((Polygon)(a)).getPoints().addAll(neu);
                }
                pcnter++;
            }
            if(a instanceof Text)
            {
                if(tcnter > from_idx)
                {
                    // no idea why but yStep / 2
                    ((Text)(a)).setY(((Text)(a)).getY() - this.yStep / 2);
                    ((Text)(a)).setY(((Text)(a)).getY() - this.yStep / 2);
                }
                tcnter++;
            }
            //System.out.println(a);
        }
    }


    @FXML void save()
    {
        FXMLLoader loader;

        try
        {
            loader = new FXMLLoader(getClass().getResource("IOpanel.fxml"));
            Stage updateWindow = new Stage();

            updateWindow.setTitle("Load/Save Diagram");
            updateWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            IOpanelController controller = loader.getController();
            controller.init(updateWindow, this.sd, this.gridM, this.ap);

            updateWindow.setScene(scene);
            updateWindow.showAndWait();
        }
        catch(Exception e)
        {
            System.out.println("Exception!");
            //e.printStackTrace();
        }

        this.row_index = this.get_max_filled_row_index();
        this.col_index = this.get_max_filled_col_index();

        this.update_col_width();
        this.update_row_height();

        this.remake_cbox();
        this.gridM.add(this.cbox, ++this.col_index, 0);
    }

}