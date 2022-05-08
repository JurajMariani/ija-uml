package uml.io;

import java.io.File;

import javax.management.AttributeNotFoundException;
import javax.swing.event.DocumentEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import uml.core.Core_Attribute;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;
import uml.core.Core_Link;
import uml.core.Core_Method;
import uml.misc.myClassBox;

public class save_classDia {
    
    protected Core_ClassDiagram classDia;
    protected File file;

    public save_classDia(Core_ClassDiagram cd, File file)
    {
        this.classDia = cd;
        this.file = file;
    }

    public int save()
    {
        try{
            DocumentBuilderFactory d_factory =
            DocumentBuilderFactory.newInstance();
            DocumentBuilder d_builder = d_factory.newDocumentBuilder();
            Document document = d_builder.newDocument();
            
            // root element <classdiagram>
            Element root_elem = document.createElement("classdiagram");
            document.appendChild(root_elem);

            Attr attribute = document.createAttribute("name");
            attribute.setValue(this.classDia.get_name());
            root_elem.setAttributeNode(attribute);

            Element classes_list = document.createElement("sClasses");
            root_elem.appendChild(classes_list);

            for (Core_Class c : this.classDia.get_classes())
            {
                // Create a <class> element 
                Element class_item = document.createElement("class");
                classes_list.appendChild(class_item);

                // add a name attribute to the <class> element
                attribute = document.createAttribute("name");
                attribute.setValue(c.get_name());
                class_item.setAttributeNode(attribute);

                Element classAttr = document.createElement("attributes");
                class_item.appendChild(classAttr);

                for(Core_Attribute attr : c.get_attributes())
                {
                    attribute = document.createAttribute("name");
                    attribute.setValue(attr.get_name());
                    classAttr.setAttributeNode(attribute);

                    attribute = document.createAttribute("type");
                    attribute.setValue(attr.get_type());
                    classAttr.setAttributeNode(attribute);

                    attribute = document.createAttribute("value");
                    attribute.setValue(attr.get_value());
                    classAttr.setAttributeNode(attribute);

                    attribute = document.createAttribute("visibility");

                    switch(attr.get_visibility())
                    {
                        case 0 : attribute.setValue("Public"); break;
                        case 1 : attribute.setValue("Private"); break;
                        case 2: attribute.setValue("Protected"); break;
                    }

                    classAttr.setAttributeNode(attribute);
                }

                Element classMeth = document.createElement("methods");
                class_item.appendChild(classMeth);

                for(Core_Method meth : c.get_methods())
                {
                    attribute = document.createAttribute("name");
                    attribute.setValue(meth.get_name());
                    classMeth.setAttributeNode(attribute);

                    attribute = document.createAttribute("type");
                    attribute.setValue(meth.get_type());
                    classMeth.setAttributeNode(attribute);
                    
                    Element methParam = document.createElement("parameters");
                    classMeth.appendChild(methParam);

                    for(Core_Attribute param : meth.get_params())
                    {
                        attribute = document.createAttribute("name");
                        attribute.setValue(param.get_name());
                        methParam.setAttributeNode(attribute);

                        attribute = document.createAttribute("type");
                        attribute.setValue(param.get_type());
                        methParam.setAttributeNode(attribute);

                        attribute = document.createAttribute("value");
                        attribute.setValue(param.get_value());
                        methParam.setAttributeNode(attribute);

                        attribute = document.createAttribute("visibility");
                        switch(param.get_visibility())
                        {
                            case 0 : attribute.setValue("Public"); break;
                            case 1 : attribute.setValue("Private"); break;
                            case 2: attribute.setValue("Protected"); break;
                        }
                        classMeth.setAttributeNode(attribute);
                    }
                }   

                Element classContainer = document.createElement("container");
                class_item.appendChild(classContainer);

                Element position = document.createElement("position");
                classContainer.appendChild(position);

                attribute = document.createAttribute("X");
                attribute.setValue(Double.toString(c.get_position()[0]));
                position.setAttributeNode(attribute);

                attribute = document.createAttribute("Y");
                attribute.setValue(Double.toString(c.get_position()[1]));
                position.setAttributeNode(attribute);
            }
            
            Element links_list = document.createElement("sLinks");
            root_elem.appendChild(classes_list);

            for(Core_Link l : this.classDia.get_links())
            {
                Element link_item = document.createElement("link");
                links_list.appendChild(link_item);

                Element cardinality = document.createElement("cardinality");
                link_item.appendChild(cardinality);

                attribute = document.createAttribute("start");
                attribute.setValue(l.get_card()[0]);
                cardinality.setAttributeNode(attribute);

                attribute = document.createAttribute("end");
                attribute.setValue(l.get_card()[1]);
                cardinality.setAttributeNode(attribute);

                Element objects = document.createElement("objects");
                link_item.appendChild(objects);

                attribute = document.createAttribute("start");
                attribute.setValue(l.get_objects().get(0).get_name());
                objects.setAttributeNode(attribute);


                attribute = document.createAttribute("end");
                attribute.setValue(l.get_objects().get(1).get_name());
                objects.setAttributeNode(attribute);

                Element linesList = document.createElement("line");
                link_item.appendChild(linesList);

                for(Node n : l.get_link())
                {
                    if(n instanceof Line)
                    {
                        Element line = document.createElement("Line");
                        linesList.appendChild(line);

                        attribute = document.createAttribute("startX");
                        attribute.setValue(Double.toString(((Line)n).getStartX()));
                        line.setAttributeNode(attribute);
                        
                        attribute = document.createAttribute("startY");
                        attribute.setValue(Double.toString(((Line)n).getStartY()));
                        line.setAttributeNode(attribute);
                        
                        attribute = document.createAttribute("endX");
                        attribute.setValue(Double.toString(((Line)n).getEndX()));
                        line.setAttributeNode(attribute);
                        
                        attribute = document.createAttribute("endY");
                        attribute.setValue(Double.toString(((Line)n).getEndY()));
                        line.setAttributeNode(attribute);

                        attribute = document.createAttribute("style");
                        attribute.setValue(((Line)n).getStyle());
                        line.setAttributeNode(attribute);
                    }

                    if(n instanceof Polygon)
                    {
                        Element polygon = document.createElement("Polygon");
                        link_item.appendChild(polygon);

                        Element points = document.createElement("points");
                        polygon.appendChild(points);

                        for(Double point : ((Polygon)n).getPoints())
                        {
                            attribute = document.createAttribute("point");
                            attribute.setValue(Double.toString(point));
                            points.setAttributeNode(attribute);
                        }

                        /*attribute = document.createAttribute("fill");
                        attribute.setValue(Paint.toString(((Polygon)n).getFill()));
                        polygon.setAttributeNode(attribute);

                        attribute = document.createAttribute("stroke");
                        attribute.setValue(Paint.toString(((Polygon)n).getStroke()));
                        polygon.setAttributeNode(attribute);*/

                        Element layout = document.createElement("layout");
                        polygon.appendChild(layout);

                        attribute = document.createAttribute("X");
                        attribute.setValue(Double.toString(((Polygon)n).getLayoutX()));
                        layout.setAttributeNode(attribute);

                        attribute = document.createAttribute("Y");
                        attribute.setValue(Double.toString(((Polygon)n).getLayoutY()));
                        layout.setAttributeNode(attribute);
                    }

                    if(n instanceof Rectangle)
                    {
                        Element rectangle = document.createElement("Rectangle");
                        link_item.appendChild(rectangle);

                        Element dimensions = document.createElement("dimensions");
                        rectangle.appendChild(dimensions);

                        attribute = document.createAttribute("height");
                        attribute.setValue(Double.toString(((Rectangle)n).getHeight()));
                        dimensions.setAttributeNode(attribute);

                        attribute = document.createAttribute("width");
                        attribute.setValue(Double.toString(((Rectangle)n).getWidth()));
                        dimensions.setAttributeNode(attribute);

                        attribute = document.createAttribute("rotation");
                        attribute.setValue(Double.toString(((Rectangle)n).getRotate()));
                        rectangle.setAttributeNode(attribute);
                        /*
                        attribute = document.createAttribute("fill");
                        attribute.setValue(Paint.toString(((Rectangle)n).getFill()));
                        rectangle.setAttributeNode(attribute);

                        attribute = document.createAttribute("stroke");
                        attribute.setValue(Paint.toString(((Rectangle)n).getStroke()));
                        rectangle.setAttributeNode(attribute);*/

                        Element layout = document.createElement("layout");
                        rectangle.appendChild(layout);

                        attribute = document.createAttribute("X");
                        attribute.setValue(Double.toString(((Rectangle)n).getLayoutX()));
                        layout.setAttributeNode(attribute);

                        attribute = document.createAttribute("Y");
                        attribute.setValue(Double.toString(((Rectangle)n).getLayoutY()));
                        layout.setAttributeNode(attribute);
                    }
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("An error occured during the saving process.");
            return 1;
        }
        return 0;
    }
}
