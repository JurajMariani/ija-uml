package uml.misc;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uml.core.Core_Attribute;
import uml.core.Core_Method;

public class myClassBox{
    protected Label className;
    protected Label classAttr;
    protected Label classMeth; 
    protected VBox container;
    protected Separator separator1;
    protected Separator separator2;


    public myClassBox()
    {
        this.className = new Label();
        this.classAttr = new Label();
        this.classMeth = new Label();
        this.container = new VBox();
        this.separator1 = new Separator(Orientation.HORIZONTAL);
        this.separator2 = new Separator(Orientation.HORIZONTAL);

        this.boxStyle();
        this.labelName_style();
        this.labelAttr_style();
        this.labelMeth_style();
        this.container.getChildren().addAll(this.className, this.separator1, this.classAttr, this.separator2, this.classMeth);
    }

    private void boxStyle()
    {
        this.container.maxHeight(1.7976931348623157E308);
        this.container.maxWidth(1.7976931348623157E308);
        this.container.setMinHeight(Double.MIN_VALUE); 
        this.container.minWidth(140.00);
        this.container.prefWidth(140.00);
        this.container.setScaleShape(false);
        this.container.setAlignment(Pos.CENTER);
        this.container.setStyle(" -fx-border-color: black; -fx-border-radius: 5px; -fx-background-color: white;");    
        
        VBox.setMargin(this.className, new Insets(5,10,5,10));
        VBox.setVgrow(this.className, Priority.ALWAYS);

        VBox.setMargin(this.classAttr, new Insets(5,2,5,2));
        VBox.setVgrow(this.classAttr, Priority.ALWAYS);

        VBox.setMargin(this.classMeth, new Insets(5,2,5,2));
        VBox.setVgrow(this.classMeth, Priority.ALWAYS);

        VBox.setVgrow(this.separator1, Priority.ALWAYS);
        VBox.setVgrow(this.separator2, Priority.ALWAYS);

        this.separator1.setStyle("-fx-background-color: black;");
        this.separator2.setStyle("-fx-background-color: black;");
    }

    private void labelName_style()
    {
        this.className.setAlignment(Pos.TOP_CENTER);
        this.className.setContentDisplay(ContentDisplay.CENTER);
        this.className.maxWidth(1.7976931348623157E308);
        this.className.minWidth(140.00);
        this.className.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
    }

    private void labelAttr_style()
    {
        //this.classAttr.setMaxHeight(Double.MIN_VALUE);
        this.classAttr.setMinHeight(Double.MIN_VALUE);
        this.classAttr.maxWidth(1.7976931348623157E308);
        this.classAttr.minWidth(138.00);
        this.classAttr.prefWidth(138.00);
        this.classAttr.setFont(Font.font("Calibri",15));
        this.classAttr.setContentDisplay(ContentDisplay.CENTER);
    }

    private void labelMeth_style()
    {
        //this.classMeth.setMaxHeight(Double.MIN_VALUE);
        this.classMeth.setMinHeight(Double.MIN_VALUE);
        this.classMeth.maxWidth(1.7976931348623157E308);
        this.classMeth.minWidth(138.00);
        this.classMeth.prefWidth(138.00);
        this.classMeth.setFont(Font.font("Calibri",15));
        this.classMeth.setContentDisplay(ContentDisplay.CENTER);
    }

    public void selectBoxGlow()
    {
        int depth = 50;
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.LIGHTSKYBLUE);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);

        this.container.setEffect(borderGlow);
    }

    public void removeBoxGlow()
    {
        DropShadow borderGlow= new DropShadow();
        borderGlow.setWidth(0);
        borderGlow.setHeight(0);
        this.container.setEffect(borderGlow);
    }

    public void setValues(String name, List<Core_Attribute> attrs, List<Core_Method> meths)
    {    
        StringBuilder attributes = new StringBuilder();
        StringBuilder methods = new StringBuilder();
        
        for(Core_Attribute attr : attrs)
        {
            attributes.append(attr.get_str_attribute()+"\n");
        }

        for(Core_Method meth : meths)
        {
            methods.append(meth.get_str_method()+"\n");
        }

        this.className.setText(name);
        this.classAttr.setText(attributes.toString());
        this.classMeth.setText(methods.toString());

        
        this.container.getChildren().set(0, this.className);
        this.container.getChildren().set(2, this.classAttr);
        this.container.getChildren().set(4, this.classMeth);
    }

    public VBox get_vbox()
    {
        return this.container;
    }
    /*public VBox updateBox(Core_Class myClass)
    {

    }
    */
}

