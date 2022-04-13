import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import uml.core.Core_ClassDiagram;
import java.net.URL;
import gui.*;


public class App extends Application
{
    Stage window;

    public static void main(String[] args)
    {
        System.out.println(args.toString());
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader loader;
        try {
            //System.out.println("scena nie je nullAAAAAAAAAAAA");
            Core_ClassDiagram classDiagram = new Core_ClassDiagram();
            //System.out.println("scena nie je nullAAAAAAAAAAAA55");
            loader = new FXMLLoader(getClass().getResource("gui/MainScene.fxml"));
            Parent root = loader.load();

            //System.out.println("scena nie je nullAAAAAAAAAAAA4444444444444");
            //System.out.print(loader);

            window = primaryStage;
            //System.out.println("scena nie je nullAAAAAAAAAAAAgggggg");
            window.setTitle("MyUML");
            //System.out.println("scena nie je nullffffffffffffffffffffAAAAAAAAAAAA");
            
            Scene scene = new Scene(root);
            //System.out.println("scena nie je nullAAAAAAAAAffffffffffhhhhhhhhhhhhhhhhhhhhhhtyAAA");

            //if (scene == null)
                //System.out.println("scena je null");
            //System.out.println("scena nie je null");

            MainSceneController controller = loader.getController();
            controller.initData(window, classDiagram);

            window.setScene(scene);
            window.show();
        }
        catch(IOException e){}

        
    }
}
