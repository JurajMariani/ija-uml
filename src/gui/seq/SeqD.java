package gui.seq;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.net.URL;
import gui.seq.*;
import uml.core.*;
import uml.seq.*;


public class SeqD extends Application
{
    Stage window;
    Seq_SequenceDiagram sd;

    public static void run(String[] args)
    {
        launch(args);
    }

    public Core_ClassDiagram set_cd()
    {
        Core_ClassDiagram cd = new Core_ClassDiagram("MyUML");
        Core_Class calss = cd.add_class();
        calss.rename("Class 1");
         
        Core_Attribute attr = calss.add_attribute();
        attr.rename("x");
        attr.change_value("5");
        attr.change_type("int");
         
        attr = calss.add_attribute();
        attr.change_visibility(2);
        attr.change_type("String");
        attr.rename("ff");

        attr = calss.add_attribute();
        attr.change_visibility(1);
        attr.change_type("List<>");
        attr.rename("list");

        Core_Method meth = calss.add_method();
        meth.change_visibility(2);
        meth.rename("git_gud");
        attr = meth.add_param();
        attr.rename("arr");
        attr.change_type("int[]");

        calss = cd.add_class();
        calss.rename("Class 2");
        attr = calss.add_attribute();
        attr.change_type("array");
        attr.rename("ukk");
        meth = calss.add_method();
        meth.rename("x");
        meth.change_type("int");

        return cd;
    }

    public void start(Stage primaryStage) throws IOException
    {
        Core_ClassDiagram cd = this.set_cd();
        this.sd = new Seq_SequenceDiagram(cd.get_name() + " - Sequence Diagram 1", cd);

        FXMLLoader loader;
        
        try {
            loader = new FXMLLoader(getClass().getResource("MS.fxml"));

            System.out.println(loader);
            Parent root = loader.load();
            this.window = primaryStage;
            this.window.setTitle(sd.get_name());
            
            Scene scene = new Scene(root);

            MSController controller = loader.getController();
            controller.init(window, this.sd);

            window.setScene(scene);
            window.show();
        }
        catch(Exception e){
            //e.printStackTrace();
            System.out.println("AAAAAAAAAAAAAAA");
        }

        
    }
}