package uml.misc;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class myLines {
    private double sCoordX, sCoordY;
    private double eCoordX, eCoordY;
    private String style;

    public myLines(double s_x, double s_y, double e_x, double e_y,  String style)
    {
        this.sCoordX = s_x;
        this.sCoordY = s_y;
        this.eCoordX = e_x;
        this.eCoordY = e_y;
        this.style = style;
    }

    public Node[] ass()
    {
        Line mainLine = new Line();

        /*System.out.print("s_x:"+ this.sCoordX + "\n");
        System.out.print("s_y" + this.sCoordY + "\n");
        System.out.print("e_x:"+this.eCoordX + "\n");
        System.out.print("e_y" + this.eCoordY + "\n\n");*/

        mainLine.setStartX(this.sCoordX);
        mainLine.setStartY(this.sCoordY);
        mainLine.setEndX(this.eCoordX);
        mainLine.setEndY(this.eCoordY);

        switch(this.style)
        {
            case "dot": mainLine.setStyle("-fx-stroke-dash-array: 2;"); break;
            case "dash": mainLine.setStyle("-fx-stroke-dash-array: 10;"); break;
            case "sol": break;
        }

        Node[] arr = new Node[2];
        arr[0] = mainLine;
        return arr;
    }

    public Node[] dir()
    {
        Line mainLine = new Line();
        Line sideline1 = new Line();
        Line sideline2 = new Line();

        switch(this.style)
        {
            case "dot": mainLine.setStyle("-fx-stroke-dash-array: 2;"); break;
            case "dash": mainLine.setStyle("-fx-stroke-dash-array: 10;"); break;
            case "sol": break;
        }

        mainLine.setStartX(this.sCoordX);
        mainLine.setStartY(this.sCoordY);
        mainLine.setEndX(this.eCoordX);
        mainLine.setEndY(this.eCoordY);

        double width = mainLine.getStrokeWidth();
        double length = 20;

        sideline1.setEndX(this.eCoordX);
        sideline1.setEndY(this.eCoordY);
        sideline2.setEndX(this.eCoordX);
        sideline2.setEndY(this.eCoordY);

        if (this.eCoordX == this.sCoordX && this.eCoordY == this.sCoordY) {
            sideline1.setStartX(this.eCoordX);
            sideline1.setStartY(this.eCoordY);
            sideline2.setStartX(this.eCoordX);
            sideline2.setStartY(this.eCoordY);
        } 
        else {
            double factor = length / Math.hypot(this.sCoordX - this.eCoordX, this.sCoordY - this.eCoordY);
            double factorO = width / Math.hypot(this.sCoordX - this.eCoordX, this.sCoordY - this.eCoordY);

            double dx = (this.sCoordX - this.eCoordX) * factor;
            double dy = (this.sCoordY - this.eCoordY) * factor;

            double ox = (this.sCoordX - this.eCoordX) * factorO;
            double oy = (this.sCoordY - this.eCoordY) * factorO;

            sideline1.setStartX(this.eCoordX + dx - oy);
            sideline1.setStartY(this.eCoordY + dy + ox);
            sideline2.setStartX(this.eCoordX + dx + oy);
            sideline2.setStartY(this.eCoordY + dy - ox);
        }

        Node[] arr = new Node[3];
        arr[0] = mainLine;
        arr[1] = sideline1;
        arr[2] = sideline2;

        return arr;
    }

    public Node[] gen()
    {
        Line mainLine = new Line();
        Polygon triangel = new Polygon(-33.0, -17.0, -18.0, -30.0, -33.0, -43.0);

        switch(this.style)
        {
            case "dot": mainLine.setStyle("-fx-stroke-dash-array: 2;"); break;
            case "dash": mainLine.setStyle("-fx-stroke-dash-array: 10;"); break;
            case "sol": break;
        }

        mainLine.setStartX(this.sCoordX);
        mainLine.setStartY(this.sCoordY);
        mainLine.setEndX(this.eCoordX);
        mainLine.setEndY(this.eCoordY); 

        triangel.setFill(Color.WHITE);
        triangel.setStroke(Color.BLACK);
        triangel.setLayoutX(this.eCoordX);
        triangel.setLayoutY(this.eCoordY);

        Node[] arr = new Node[2];
        arr[0] = mainLine;
        arr[1] = triangel;

        return arr;
    }

    public Node[] agg()
    {
        Line mainLine = new Line();
        Rectangle rect = new Rectangle(15,15);

        switch(this.style)
        {
            case "dot": mainLine.setStyle("-fx-stroke-dash-array: 2;"); break;
            case "dash": mainLine.setStyle("-fx-stroke-dash-array: 10;"); break;
            case "sol": break;
        }

        mainLine.setStartX(this.sCoordX);
        mainLine.setStartY(this.sCoordY);
        mainLine.setEndX(this.eCoordX);
        mainLine.setEndY(this.eCoordY);

        rect.setRotate(45.0);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        rect.setLayoutX(this.sCoordX);
        rect.setLayoutY(this.sCoordY);

        Node[] arr = new Node[2];
        arr[0] = mainLine;
        arr[1] = rect;

        return arr;
    }


}