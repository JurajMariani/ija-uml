package uml.io;

import java.io.File;

import javafx.scene.layout.Pane;

public class load_classDia {

    private Pane pane;
    private File file;
    

    public load_classDia(Pane pane, File file)
    {
        this.pane = pane;
        this.file = file;
    }

    public int load()
    {
        try{

        }
        catch(Exception e)
        {
            return 1;
        }
        return 0;
    }
}
