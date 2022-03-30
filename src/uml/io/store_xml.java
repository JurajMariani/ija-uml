package uml.io;


import uml.core.*;
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
import java.io.File;

public class store_xml extends java.lang.Object
{
    private Core_ClassDiagram c_diagram;
    private String path;
    private String filename;

    public store_xml(Core_ClassDiagram cd, String path, String filename)
    {
        this.c_diagram = cd;
        this.path = path;
        this.filename = filename;
        System.out.println("I was given: "+this.path + this.filename + ".\n");

        if (this.save() == 1)
            System.out.println("pixxi\n");
    }

    public int save()
    {
        try {
            DocumentBuilderFactory d_factory =
            DocumentBuilderFactory.newInstance();
            DocumentBuilder d_builder = d_factory.newDocumentBuilder();
            Document document = d_builder.newDocument();
            
            // root element <classdiagram>
            Element root_elem = document.createElement("classdiagram");
            document.appendChild(root_elem);

            // set the name attribute to <classdiagram>
            Attr attribute = document.createAttribute("name");
            attribute.setValue(this.c_diagram.get_name());
            root_elem.setAttributeNode(attribute);

            Element classes_item = document.createElement("classlist");
            root_elem.appendChild(classes_item);

            List<Core_Class> classes = this.c_diagram.get_classes();
            for (Core_Class item : classes)
            {
                // Create a <class> element 
                Element class_item = document.createElement("class");
                classes_item.appendChild(class_item);

                // add a name attribute to the <class> element
                attribute = document.createAttribute("name");
                attribute.setValue(item.get_name());
                class_item.setAttributeNode(attribute);

                List<Core_Attribute> attrs = item.get_attributes();
                for (Core_Attribute attr : attrs)
                {
                    // Create an attribute element
                    Element attribute_item = document.createElement("attribute");
                    class_item.appendChild(attribute_item);

                    // add a name ...
                    attribute = document.createAttribute("name");
                    attribute.setValue(attr.get_name());
                    attribute_item.setAttributeNode(attribute);

                    // type ...
                    attribute = document.createAttribute("type");
                    attribute.setValue(attr.get_type());
                    attribute_item.setAttributeNode(attribute);

                    // value ...
                    attribute = document.createAttribute("value");
                    attribute.setValue(attr.get_value());
                    attribute_item.setAttributeNode(attribute);

                    // modifier attributes
                    attribute = document.createAttribute("modif");
                    attribute.setValue(attr.get_str_visibility());
                    attribute_item.setAttributeNode(attribute);
                }

                List<Core_Method> meth = item.get_methods();
                for (Core_Method method_item : meth)
                {
                    // Create a method element
                    Element method = document.createElement("method");
                    class_item.appendChild(method);
                    
                    // add a name ...
                    attribute = document.createAttribute("name");
                    attribute.setValue(method_item.get_name());
                    method.setAttributeNode(attribute);

                    // type ...
                    attribute = document.createAttribute("type");
                    attribute.setValue(method_item.get_type());
                    method.setAttributeNode(attribute);

                    // and modifier
                    attribute = document.createAttribute("modif");
                    attribute.setValue(method_item.get_str_visibility());
                    method.setAttributeNode(attribute);

                    // Create a <paramlist> subelement
                    Element paramlist = document.createElement("paramlist");
                    method.appendChild(paramlist);

                    List<Core_Attribute> params = method_item.get_params();
                    for (Core_Attribute param : params)
                    {
                        Element param_item = document.createElement("param");
                        paramlist.appendChild(param_item);

                        attribute = document.createAttribute("name");
                        attribute.setValue(param.get_name());
                        param_item.setAttributeNode(attribute);

                        attribute = document.createAttribute("type");
                        attribute.setValue(param.get_value());
                        param_item.setAttributeNode(attribute);
                    }
                }
            }

            Element links_item = document.createElement("linklist");
            root_elem.appendChild(links_item);

            List<Core_Link> links = this.c_diagram.get_links();
            for (Core_Link link : links)
            {
                Element link_item = document.createElement("link");
                links_item.appendChild(link_item);
                
                attribute = document.createAttribute("name");
                attribute.setValue(link.get_name());
                link_item.setAttributeNode(attribute);

                attribute = document.createAttribute("type");
                attribute.setValue(link.get_type());
                link_item.setAttributeNode(attribute);

                Element object_item = document.createElement("objects");
                link_item.appendChild(object_item);

                uml.core.Element[] obj = link.get_objects();
                attribute = document.createAttribute("start");
                attribute.setValue(obj[0].get_name());
                object_item.setAttributeNode(attribute);

                attribute = document.createAttribute("end");
                attribute.setValue(obj[1].get_name());
                object_item.setAttributeNode(attribute);

                Element card = document.createElement("card");
                link_item.appendChild(card);

                String[] cardinality = link.get_card();
                attribute = document.createAttribute("start");
                attribute.setValue(cardinality[0]);
                card.setAttributeNode(attribute);

                attribute = document.createAttribute("end");
                attribute.setValue(cardinality[1]);
                card.setAttributeNode(attribute);
            }
   
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(this.path + this.filename+".xml"));
            transformer.transform(source, result);
            
         }
         catch (Exception e)
         {
            e.printStackTrace();
            return 1;
         }
         return 0;
    }
}