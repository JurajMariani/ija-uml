package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uml.core.Core_ClassDiagram;



public class App extends Application
{
    Stage window;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader;
        try {
            Core_ClassDiagram classDiagram = new Core_ClassDiagram();
            loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            window = primaryStage;
            window.setTitle("MyUML");
            
            Scene scene = new Scene(loader.load());

            MainSceneController controller = loader.getController();
            controller.initData(window, classDiagram);

            window.setScene(scene);
            window.show();
        }
        catch(IOException e){}

        
    }
}