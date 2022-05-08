package gui;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.core.Core_Attribute;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;
import uml.core.Core_Link;
import uml.core.Core_Method;
import uml.io.load_classDia;
import uml.io.save_classDia;
import uml.misc.myLines;
import uml.seq.Seq_SequenceDiagram;
import gui.seq.MSController;

/**
 * /**
 * CLASS: Main scene controller
 * 
 * <p> Class MainSceneController handles main scene
 *
 * @author Denis Horil
 */

public class MainSceneController
{
    public Stage actualWindow;
    public Core_ClassDiagram classDiagram;

    public Core_Class selectedClass;

    public List<Core_Link> links;
    private double mouseCoordX, mouseCoordY;

    private boolean close;
    private File file;

    @FXML private Button editButton;
    @FXML private Button selectButton;
    @FXML private Button deleteButton;
    @FXML private Button linkButton;
    @FXML private Button createSeqDiaButton;
    @FXML private Pane pane;
    
    /**
     * Initialization of window and new class attributes
     * @param window Actual window
     * @param c Class to be edited
     */
    public void initData(Stage window, Core_ClassDiagram classDiagram)
    {
        this.actualWindow = window;
        this.classDiagram = classDiagram;
        this.selectedClass = new Core_Class();
        this.links = new ArrayList<Core_Link>();
        this.paneListener();
        this.close = false;
        this.file = null;
    }

    /**
     * Handling mouse actions on pane
     */
    private void paneListener()
    {
        this.pane.setOnMouseClicked(MouseEvent ->{
            if(this.selectedClass != null)
                this.selectedClass.get_container().removeBoxGlow();
            this.editButton.setDisable(true);
            this.selectButton.setDisable(true);
            this.deleteButton.setDisable(true);     
            this.linkButton.setDisable(true);
            this.createSeqDiaButton.setDisable(true);
            MouseEvent.consume();
        });
    }

    /**
     * Make class draggable
     * @param c Class to be made draggable
     */
    public void makeDraggable(Core_Class c)
    {   
        Node obj = c.get_container().get_vbox();
        double[] coords = { 0.0, 0.0 };

        obj.setOnMouseClicked(mouseEvent -> {
            if(this.selectedClass != null)
                this.selectedClass.get_container().removeBoxGlow();
            
            this.selectedClass = c;
            this.selectedClass.get_container().selectBoxGlow();

            this.editButton.setDisable(false);
            this.selectButton.setDisable(false);
            this.deleteButton.setDisable(false);     
            this.linkButton.setDisable(false);
            this.createSeqDiaButton.setDisable(false);
            mouseEvent.consume();
        });

        obj.setOnMousePressed(mouseEvent -> {
            this.mouseCoordX = mouseEvent.getX();
            this.mouseCoordY = mouseEvent.getY();
            mouseEvent.consume();
        });

        obj.setOnMouseDragged(mouseEvent -> {
            obj.setLayoutX(mouseEvent.getSceneX() - this.mouseCoordX);
            obj.setLayoutY(mouseEvent.getSceneY() - this.mouseCoordY);

            Array.set(coords, 0, (mouseEvent.getSceneX() - mouseCoordX));
            Array.set(coords, 1, (mouseEvent.getSceneY() - mouseCoordY)); 
            
            mouseEvent.consume();
        });

        obj.setOnMouseReleased(mouseEvent -> {
            c.move(coords[0], coords[1]);
            mouseEvent.consume();
        });
    }

    /**
     * Creates new class of class diagram
     * @param event Event handler
     */
    @FXML
    void NewClass(ActionEvent event)
    {
        
        Core_Class newClass = new Core_Class();
        FXMLLoader loader;

        try{
            loader = new FXMLLoader(getClass().getResource("CreateClass.fxml"));
            Stage newClassWindow = new Stage();
            newClassWindow.setTitle("Create new class");
            newClassWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            CreateClassController controller = loader.getController();
            controller.initData(newClassWindow, newClass);

            newClassWindow.setScene(scene);

            newClassWindow.setOnCloseRequest(WindowEvent ->{
                MainSceneController.this.close = true;
            });

            newClassWindow.showAndWait();
            
            if(this.close)
            {
                this.close = false;
                return;
            }

            Core_Class writeClass = this.classDiagram.add_class(newClass.get_name());

            if(writeClass == null)
            {
                Alert a = new Alert(AlertType.WARNING);
                a.setTitle("Create class");
                a.setContentText("Class with this name already exist");
                a.show();
                return;
            }

            for(Core_Attribute attr : newClass.get_attributes())
            {
                Core_Attribute a = writeClass.add_attribute();
                a.rename(attr.get_name());
                a.change_type(attr.get_type());
                a.change_value(attr.get_value());
                a.change_visibility(attr.get_visibility());
            }

            for(Core_Method meth : newClass.get_methods())
            {
                Core_Method m = writeClass.add_method();
                m.change_type(meth.get_type());
                m.change_visibility(meth.get_visibility());
                m.rename(meth.get_name());

                for(Core_Attribute param : meth.get_params())
                {
                    Core_Attribute p = m.add_param();
                    p.rename(param.get_name());
                    p.change_type(param.get_type());
                    p.change_value(param.get_value());
                }
            }

            writeClass.get_container().setValues(writeClass.get_name(), writeClass.get_attributes(), writeClass.get_methods());

            this.makeDraggable(writeClass);
            this.pane.getChildren().add(writeClass.get_container().get_vbox());
        }
        catch(IOException e){}

        this.update_sequence_diagrams();
    }

    /**
     * Edit class from class diagram
     * @param event Event handler
     */
    @FXML
    void EditClass(ActionEvent event)
    {
        FXMLLoader loader;

        try{
            loader = new FXMLLoader(getClass().getResource("EditClass.fxml"));
            Stage editWindow = new Stage();
            editWindow.setTitle("Edit class");
            editWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());

            EditClassController controller = loader.getController();
            controller.initData(editWindow, this.selectedClass);

            editWindow.setScene(scene);
            editWindow.showAndWait();
            
            this.selectedClass.get_container().setValues(this.selectedClass.get_name(), this.selectedClass.get_attributes(), this.selectedClass.get_methods());

        }
        catch(IOException e){}

        this.update_sequence_diagrams();
    }

    /**
     * Delete class from class diagram
     * @param event Event handler
     */
    @FXML
    void DeleteClass(ActionEvent event)
    {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Delete class");
        a.setContentText("Do you really wanna delete class '" + this.selectedClass.get_name() + "' ?");

        Optional<ButtonType> rslt = a.showAndWait();
        if(rslt.get() == ButtonType.OK)
        {
            this.classDiagram.remove_class(this.selectedClass);
            this.pane.getChildren().remove(this.selectedClass.get_container().get_vbox());

            for(Core_Link link : this.links)
            {
                List<Core_Class> elems = link.get_objects();

                if(elems.get(0).get_name() == this.selectedClass.get_name() || elems.get(1).get_name() == this.selectedClass.get_name())
                    for(Node n : link.get_link())
                    {
                        this.pane.getChildren().remove(n);
                    }
            }

            this.selectedClass = null;

            this.editButton.setDisable(true);
            this.selectButton.setDisable(true);
            this.deleteButton.setDisable(true);
            this.linkButton.setDisable(true);
            this.createSeqDiaButton.setDisable(true);
        }  
    }
    
    /**
     * Select class and writes info about it
     * @param event Event handler
     */
    @FXML
    void SelectClass(ActionEvent event)
    {
        System.out.print(this.selectedClass.get_name()+"\n");
        System.out.print(this.selectedClass.get_position()[0]+"\n");
        System.out.print(this.selectedClass.get_position()[1]+"\n");
    }
      
    @FXML
    void LinkClasses(ActionEvent event)
    {
        FXMLLoader loader;

        try{
            loader = new FXMLLoader(getClass().getResource("AddLink.fxml"));
            Stage addWindow = new Stage();
            addWindow.setTitle("Add link");
            addWindow.initModality(Modality.APPLICATION_MODAL);
            
            Scene scene = new Scene(loader.load());
            
            AddLinkController controller = loader.getController();
            controller.initData(addWindow, this.classDiagram.get_classes());
            
            addWindow.setScene(scene);

            addWindow.setOnCloseRequest(WindowEvent -> {
                MainSceneController.this.close = true;
            });

            addWindow.showAndWait();
            
            if(this.close) 
            {
                this.close = false;
                return;
            }

            List<String> list = controller.get_vals();

            Core_Class start_obj = this.classDiagram.get_class(list.get(2));
            Core_Class end_obj = this.classDiagram.get_class(list.get(3));
            
            double s_x, s_y, e_x, e_y;
            s_x = start_obj.get_position()[0];
            s_y = start_obj.get_position()[1];
            e_x = end_obj.get_position()[0];
            e_y = end_obj.get_position()[1];

            myLines handler = new myLines(s_x, s_y, e_x, e_y, list.get(1));
            Node[] toDraw = new Node[2];

            switch(list.get(0))
            {
                case "ass":
                    toDraw = handler.ass(); break;
                case "dir":
                    toDraw = handler.dir(); break;
                case "agg":
                    toDraw = handler.agg(); break;
                case "gen": 
                    toDraw = handler.gen(); break;
            }

            Core_Link link = this.classDiagram.add_link(start_obj, end_obj);
            link.set_type(list.get(0));
            link.add_line(toDraw);
            
            if(list.get(4) != "")
                link.change_start_card(list.get(4));
            if(list.get(5) != "")
                link.change_start_card(list.get(5));

            this.links.add(link);
            
            for(Node o : toDraw)
            {
                if(o != null)
                    this.pane.getChildren().add(o);
            }
        }
        catch(IOException e){}
    }

    @FXML
    void LoadDiagram(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CXML Files", "*.cxml"));

        this.file = fileChooser.showOpenDialog(this.actualWindow);

        if(file == null)
        {
            Alert a = new Alert(AlertType.WARNING);
            a.setTitle("Open file");
            a.setContentText("Cannot open chosen file");
            a.show();
            return;
        }

        for(Node n : this.pane.getChildren())
        {
            this.pane.getChildren().remove(n);
        }

        load_classDia load = new load_classDia(this.pane, this.file, this.classDiagram, this);
        int retVal = load.load();

        if(retVal == 1)
        {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Load file");
            a.setContentText("Cannot load chosen file");
            a.show();
            return;
        }
    }

    @FXML
    void SaveDiagram(ActionEvent event)
    {
        boolean flag = false;
        File tmp = this.file;

        if(this.file != null)
        {
            this.file.delete();
            flag = true;
        }
        
        if(!flag)
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CXML Files", "*.cxml"));
            this.file = fileChooser.showSaveDialog(this.actualWindow);
        }
        else this.file = tmp; 

        this.saveToFile();
        
    }

    @FXML
    void SaveAs(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CXML Files", "*.cxml"));

        this.file = fileChooser.showSaveDialog(this.actualWindow);

        this.saveToFile();
    }

    @FXML
    void CreateSeqDia(ActionEvent event)
    {
        FXMLLoader loader;
        Seq_SequenceDiagram seq_dia = new Seq_SequenceDiagram(this.classDiagram.get_name() + " Sequence diagram", this.classDiagram);
        this.classDiagram.add_sd(seq_dia);

        try{
            loader = new FXMLLoader(getClass().getResource("seq/MS.fxml"));
            Stage seq_diagram = new Stage();
            seq_diagram.setTitle(this.classDiagram.get_name() + " Sequence diagram");

            Scene scene = new Scene(loader.load());

            MSController controller = loader.getController();
            controller.init(seq_diagram, seq_dia);

            seq_diagram.setScene(scene);
            seq_diagram.show();
        }
        catch(IOException e){}
    }

    private void saveToFile()
    {
        if(this.file == null)
        {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("File save");
            a.setContentText("Cannot create file to save");
            a.show();
            return;
        }
        save_classDia save = new save_classDia(this.classDiagram, this.file);
        int retVal = save.save();

        if(retVal == 1)
        {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Saving file");
            a.setContentText("Saving file was unsuccesful");
            a.show();
            return;
        }
        else System.out.print("Great Success (Save file)\n");
    }

    private void update_sequence_diagrams()
    {
        for(Seq_SequenceDiagram sequence : this.classDiagram.get_sequence_diagrams())
        {
            sequence.attribute_update();
        }
    }
}
