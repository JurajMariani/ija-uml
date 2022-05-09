package uml.io;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gui.MainSceneController;
import javafx.scene.layout.Pane;
import uml.core.Core_Attribute;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;
import uml.core.Core_Method;


/**
 * CLASS: Load class diagram
 * 
 * <p> Class load_classDia loads class diagram from file
 *
 * @author Denis Horil
 */
public class load_classDia {

    private Pane pane;
    private File file;
    private Core_ClassDiagram cd;
    private MainSceneController cntrl;
    
    /**
     * Initialization of attributes
     * @param pane Elements will be inserted to this pane
     * @param file File to loaded from
     * @param cd Class diagram in which will be stored information
     * @param cntrl Instance of controller class
     */
    public load_classDia(Pane pane, File file, Core_ClassDiagram cd, MainSceneController cntrl)
    {
        this.pane = pane;
        this.file = file;
        this.cd = cd;
        this.cntrl = cntrl;
    }

    /**
     * Loading class diagram from given file
     * @return Exit code
     */
    public int load()
    {
        try{
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();  
            Document doc = documentBuilder.parse(this.file);  
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            String diagName = root.getAttribute("name");
            this.cd.rename(diagName);

            NodeList classes = doc.getElementsByTagName("class");
            NodeList links = doc.getElementsByTagName("link");

            for(int i = 0; i < classes.getLength(); i++)
            {
                String className = classes.item(i).getAttributes().getNamedItem("name").getTextContent();
                Core_Class newClass = this.cd.add_class(className);

                NodeList children = classes.item(i).getChildNodes();
                for(int j = 0; j < children.getLength(); j++)
                {
                    if(children.item(j).getNodeName() == "attributes")
                    {
                        Core_Attribute attr = newClass.add_attribute();
                        String attrName = "";
                        String attrType = "";
                        String attrVal = "";
                        int attrVis = -1;

                        if(children.item(j).hasAttributes())
                        {
                            if(children.item(j).getAttributes().getNamedItem("name") != null)
                            {
                                attrName = children.item(j).getAttributes().getNamedItem("name").getTextContent();
                            }

                            if(children.item(j).getAttributes().getNamedItem("type") != null)
                            {
                                attrType = children.item(j).getAttributes().getNamedItem("type").getTextContent();
                            }

                            if(children.item(j).getAttributes().getNamedItem("value") != null)
                            {
                                attrVal = children.item(j).getAttributes().getNamedItem("value").getTextContent();
                            }

                            if(children.item(j).getAttributes().getNamedItem("visibility") != null)
                            {
                                switch(children.item(j).getAttributes().getNamedItem("visibility").getTextContent())
                                {
                                    case "Public": attrVis = 0; break;
                                    case "Private": attrVis = 1; break;
                                    case "Protected": attrVis = 2; break;
                                }
                            }

                            attr.rename(attrName);
                            attr.change_type(attrType);
                            attr.change_value(attrVal);
                            attr.change_visibility(attrVis);
                        }  
                    }

                    if(children.item(j).getNodeName() == "methods")
                    {
                        Core_Method meth = newClass.add_method();
                        String methName = "";
                        String methType = "";
                        int methVis = -1;

                        if(children.item(j).hasAttributes())
                        {
                            if(children.item(j).getAttributes().getNamedItem("name") != null)
                            {
                                methName = children.item(j).getAttributes().getNamedItem("name").getTextContent();
                            }

                            if(children.item(j).getAttributes().getNamedItem("type") != null)
                            {
                                methType = children.item(j).getAttributes().getNamedItem("type").getTextContent();
                            }

                            if(children.item(j).getAttributes().getNamedItem("visibility") != null)
                            {
                                switch(children.item(j).getAttributes().getNamedItem("visibility").getTextContent())
                                {
                                    case "Public": methVis = 0; break;
                                    case "Private": methVis = 1; break;
                                    case "Protected": methVis = 2; break;
                                }
                            }

                            meth.rename(methName);
                            meth.change_type(methType);
                            meth.change_visibility(methVis);

                            if(children.item(j).hasChildNodes())
                            {
                                NodeList nodes = children.item(j).getChildNodes();
                                for(int k = 0; k < children.getLength(); k++)
                                {
                                    Core_Attribute param = meth.add_param();
                                    String paramName = "";
                                    String paramType = "";
                                    String paramVal = "";

                                    if(children.item(j).hasAttributes())
                                    {

                                        if(children.item(j).getAttributes().getNamedItem("name") != null)
                                        {
                                            paramName = children.item(j).getAttributes().getNamedItem("name").getTextContent();
                                        }

                                        if(children.item(j).getAttributes().getNamedItem("type") != null)
                                        {
                                            paramType = children.item(j).getAttributes().getNamedItem("type").getTextContent();
                                        }

                                        if(children.item(j).getAttributes().getNamedItem("value") != null)
                                        {
                                            paramVal = children.item(j).getAttributes().getNamedItem("value").getTextContent();
                                        }

                                        param.rename(paramName);
                                        param.change_type(paramType);
                                        param.change_value(paramVal);
                                    }   
                                }
                            }
                        }  
                    }

                    if(children.item(j).getNodeName() == "container")
                    {
                        double x = 0.0;
                        double y = 0.0;

                        NodeList nodes = children.item(j).getChildNodes();
                        for(int k = 0; k < nodes.getLength(); k++)
                        {
                            if(nodes.item(k).hasAttributes())
                            {
                                x = Double.parseDouble(nodes.item(k).getAttributes().getNamedItem("X").getTextContent());
                                y = Double.parseDouble(nodes.item(k).getAttributes().getNamedItem("Y").getTextContent());
                            }

                        }
                        newClass.move(x,y);
                    }
                }

                newClass.get_container().setValues(newClass.get_name(), newClass.get_attributes(), newClass.get_methods());
                this.cntrl.makeDraggable(newClass);
                this.pane.getChildren().add(newClass.get_container().get_vbox());
            }
            
        }
        catch(Exception e)
        {
            //e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
