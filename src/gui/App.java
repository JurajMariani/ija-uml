package gui;

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

    public static void run(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader loader;
        try {
            Core_ClassDiagram classDiagram = new Core_ClassDiagram();
            loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent root = loader.load();
            window = primaryStage;
            window.setTitle("MyUML");
            
            Scene scene = new Scene(root);

            MainSceneController controller = loader.getController();
            controller.initData(window, classDiagram);

            window.setScene(scene);
            window.show();
        }
        catch(IOException e){}

        
    }
}
