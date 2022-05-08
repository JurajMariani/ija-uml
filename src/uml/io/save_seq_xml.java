package uml.io;


import uml.core.*;
import uml.seq.*;

import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.views.DocumentView;

import java.io.File;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Line; 

/**
 * CLASS: SAVE SEQ XML
 * 
 * <p> Class save_message_xml handles saving Seq. Diagrams in an XML format, with the .sxml extenstion. Files are saved in the ./data folder
 *
 * @author Juraj Mariani
 */
public class save_seq_xml
{
    protected Seq_SequenceDiagram sd;
    protected String path;
    protected String filename;

    /**
     * Constructor
     * @param sd Reference to the source object
     * @param filename Name of the savefile
     */
    public save_seq_xml(Seq_SequenceDiagram sd, String filename)
    {
        this.sd = sd;
        this.path = "./data/";
        this.filename = filename + ".sxml";
        System.out.println("I have been given: "+this.path + this.filename + ".\n");
    }

    /**
     * @param name New file name
     */
    public void change_filename(String name)
    {
        this.filename = name + ".sxml";
    }

    /**
     * Main saving method - XML generator
     * @return Status -> 0 = success, 1 = fail
     */
    public int save()
    {
        try {
            DocumentBuilderFactory d_factory =
            DocumentBuilderFactory.newInstance();
            DocumentBuilder d_builder = d_factory.newDocumentBuilder();
            Document document = d_builder.newDocument();
            
            // root element <classdiagram>
            Element root_elem = document.createElement("sequencediagram");
            document.appendChild(root_elem);

            // set the name attribute to <classdiagram>
            Attr attribute = document.createAttribute("name");
            attribute.setValue(this.sd.get_name());
            root_elem.setAttributeNode(attribute);
            
            attribute = document.createAttribute("ref");
            attribute.setValue(this.sd.get_reference().get_name());
            root_elem.setAttributeNodeNS(attribute);

            attribute = document.createAttribute("id");
            attribute.setValue( Integer.toString(this.sd.get_instance()));
            root_elem.setAttributeNodeNS(attribute);

            Element classes_list = document.createElement("sClasses");
            root_elem.appendChild(classes_list);

            List<Seq_Class> classes = this.sd.get_actors();
            for (Seq_Class item : classes)
            {
                // Create a <class> element 
                Element class_item = document.createElement("class");
                classes_list.appendChild(class_item);

                // add a name attribute to the <class> element
                attribute = document.createAttribute("name");
                attribute.setValue(item.get_name());
                class_item.setAttributeNode(attribute);

                attribute = document.createAttribute("id");
                attribute.setValue(Integer.toString(item.get_instance()));
                class_item.setAttributeNodeNS(attribute);

                attribute = document.createAttribute("ref");
                attribute.setValue(item.get_reference_class().get_name());
                class_item.setAttributeNode(attribute);

                attribute = document.createAttribute("constructed");
                attribute.setValue(Boolean.toString(item.is_constructed()));
                class_item.setAttributeNode(attribute);

                /** Activity log */
                Element activityL = document.createElement("activityLog");
                class_item.appendChild(activityL);

                int row = 0;
                List<Boolean> activity = item.get_activity();
                for (Boolean attr : activity)
                {
                    // Create an attribute element
                    Element activityItem = document.createElement("activityItem");
                    activityL.appendChild(activityItem);

                    activityItem.setTextContent(attr.toString());
                }
            }

            Element messageList = document.createElement("messagelist");
            root_elem.appendChild(messageList);

            List<Seq_Message> messages = this.sd.get_messages();
            for (Seq_Message message : messages)
            {
                Element mess = document.createElement("message");
                messageList.appendChild(mess);
                
                attribute = document.createAttribute("name");
                attribute.setValue(message.get_name());
                mess.setAttributeNode(attribute);

                attribute = document.createAttribute("id");
                attribute.setValue(Integer.toString(message.get_instance()));
                mess.setAttributeNode(attribute);

                attribute = document.createAttribute("caller");
                attribute.setValue(Integer.toString(message.get_caller().get_instance()));
                mess.setAttributeNode(attribute);

                attribute = document.createAttribute("receiver");
                attribute.setValue(Integer.toString(message.get_receiver().get_instance()));
                mess.setAttributeNode(attribute);

                /*attribute = document.createAttribute("ref");
                attribute.setValue(message.get_ref().get_name());
                mess.setAttributeNode(attribute);*/

                attribute = document.createAttribute("ack");
                attribute.setValue(Boolean.toString(message.is_ack()));
                mess.setAttributeNode(attribute);

                attribute = document.createAttribute("constructor");
                attribute.setValue(Boolean.toString(message.is_constructor()));
                mess.setAttributeNode(attribute);

                List<Node> draw = message.get_line();
                Element vfx = document.createElement("VFX");
                mess.appendChild(vfx);

                for (javafx.scene.Node n : draw)
                {
                    if (n instanceof Line)
                    {
                        Element line = document.createElement("Line");
                        vfx.appendChild(line);

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

                        attribute = document.createAttribute("dashed");
                        attribute.setValue(Boolean.toString((((Line)n).getStrokeDashArray().size() > 0) ? true : false));
                        line.setAttributeNode(attribute);
                    }
                    if (n instanceof Polygon)
                    {
                        Element polygon = document.createElement("Polygon");
                        vfx.appendChild(polygon);

                        attribute = document.createAttribute("x0");
                        attribute.setValue(Double.toString(((Polygon)n).getPoints().get(0)));
                        polygon.setAttributeNode(attribute);

                        attribute = document.createAttribute("y0");
                        attribute.setValue(Double.toString(((Polygon)n).getPoints().get(1)));
                        polygon.setAttributeNode(attribute);

                        attribute = document.createAttribute("x1");
                        attribute.setValue(Double.toString(((Polygon)n).getPoints().get(2)));
                        polygon.setAttributeNode(attribute);

                        attribute = document.createAttribute("y1");
                        attribute.setValue(Double.toString(((Polygon)n).getPoints().get(3)));
                        polygon.setAttributeNode(attribute);

                        attribute = document.createAttribute("x2");
                        attribute.setValue(Double.toString(((Polygon)n).getPoints().get(4)));
                        polygon.setAttributeNode(attribute);

                        attribute = document.createAttribute("y2");
                        attribute.setValue(Double.toString(((Polygon)n).getPoints().get(5)));
                        polygon.setAttributeNode(attribute);
                    }
                    if (n instanceof Text)
                    {
                        Element text = document.createElement("Text");
                        vfx.appendChild(text);

                        attribute = document.createAttribute("x");
                        attribute.setValue(Double.toString(((Text)n).getX()));
                        text.setAttributeNode(attribute);

                        attribute = document.createAttribute("y");
                        attribute.setValue(Double.toString(((Text)n).getY()));
                        text.setAttributeNode(attribute);

                        attribute = document.createAttribute("text");
                        attribute.setValue(((Text)n).getText());
                        text.setAttributeNode(attribute);
                    }
                }
            }
   
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            /** Makes the created XML savefile readable by a human(indents and newlines) but creates problems when loading. Thereore it is not used.*/
            /*transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");*/
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(this.path + this.filename));
            transformer.transform(source, result);
            
         }
         catch (Exception e)
         {
            e.printStackTrace();
            System.out.println("An error occured during the saving process.");
            return 1;
         }
         return 0;
    }
}