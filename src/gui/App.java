package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Parent;
import uml.core.Core_ClassDiagram;


public class App extends Application
{
    private Stage window;

    public static void run(String[] args)
    {
        launch(args);
    }

    /**
     * 
     * @return Primary stage
     */
    public Stage get_primaryStage()
    {
        return this.window;
    }

    /**
     * Start of application
     */
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader loader;
        try {
            Core_ClassDiagram classDiagram = new Core_ClassDiagram();
            loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent root = loader.load();
            this.window = primaryStage;
            this.window.setTitle("MyUML");
            
            Scene scene = new Scene(root);

            MainSceneController controller = loader.getController();
            controller.initData(this.window, classDiagram);

            this.window.setScene(scene);
            this.window.show();

        }
        catch(IOException e){}
    }
    
}
