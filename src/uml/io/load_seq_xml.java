package uml.io;


import uml.core.*;
import uml.seq.*;

import java.util.ArrayList;
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

import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.geometry.HPos;
import javafx.collections.*;
 
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;



public class load_seq_xml  
{
    protected String path = "./data/";
    protected String filename;
    protected Seq_SequenceDiagram sd;
    protected GridPane gp;
    protected AnchorPane ap;
    protected int currDisplacement;
    protected int loadDisplacement;


    public load_seq_xml(String name, Seq_SequenceDiagram sd, GridPane g, AnchorPane a)
    {
        this.ap = a;
        this.gp = g;
        this.filename = name;
        this.sd = sd;
    }

    public int load()
    {
        try
        {
            //creating a constructor of file class and parsing an XML file
            File file = new File(this.path + this.filename);

            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();


            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            Element root = doc.getDocumentElement();

            this.sd.reset_master();
            this.loadDisplacement = Integer.parseInt(root.getAttribute("id"));
            
            String ref_name = root.getAttribute("ref");
            String name = root.getAttribute("name");

            if(!ref_name.equals(this.sd.get_reference().get_name()))
                return 1;

            Core_ClassDiagram cd = this.sd.get_reference();
            this.sd.rename(name);
            System.out.println("Displ " + this.loadDisplacement);
            System.out.println("IDable " + this.currDisplacement);
            /** Opravit displacement pri ID, lebo pri znovinacitani to robi bordeu */

            NodeList seqClasses = doc.getElementsByTagName("class");
            NodeList messages = doc.getElementsByTagName("message");

            Seq_Class newClass = null;
            Seq_Message newMessage = null;
            
            // nodeList is not iterable, so we are using for loop
            for (int i = this.loadDisplacement; i < (this.loadDisplacement + seqClasses.getLength() + messages.getLength()); i++)
            {
                System.out.println("------------------");
                Element e = this.next(i + 1, seqClasses, messages);

                if(e == null)
                    continue;
            
                System.out.println("E NODE NAME " + e.getNodeName());
                

                if(e.getNodeName().equals("class"))
                {
                    if(e.getAttribute("name").equals("System"))
                        continue;

                    newClass = this.sd.add_actor(cd.get_class(e.getAttribute("ref")), 0);
                    if(newClass == null)
                        return 1;
                    
                    newClass.rename(e.getAttribute("name"));
                    NodeList activityWrap = e.getChildNodes();
                    NodeList activity = activityWrap.item(0).getChildNodes();
                    List<Boolean> act = new ArrayList<Boolean>();
                    System.out.println("----------------------");
                    for(int j = 0; j < activity.getLength(); j++)
                    {
                        act.add(Boolean.parseBoolean(activity.item(j).getTextContent()));
                        System.out.println(activity.item(j).getTextContent());
                    }

                    newClass.set_activity(act);
                    if(e.getAttribute("constructed").equals("true"))
                        newClass.construct();
                }
                if(e.getNodeName().equals("message"))
                {
                    newMessage = this.sd.add_message_silent(this.sd.get_actor_by_id(this.get_correct_id(Integer.parseInt(e.getAttribute("caller")))), this.sd.get_actor_by_id(this.get_correct_id(Integer.parseInt(e.getAttribute("receiver")))), e.getAttribute("name"), null, Boolean.parseBoolean(e.getAttribute("ack")), Boolean.parseBoolean(e.getAttribute("constructor")));
                    String nameMess = e.getAttribute("name").substring(0, e.getAttribute("name").indexOf("("));
                    System.out.println(nameMess);
                    
                    if (!nameMess.equals("ok") && !nameMess.equals("init"))
                    {
                        Core_Method method = newMessage.get_receiver().get_reference_class().get_method(nameMess);
                        System.out.println("METHOD?");
                        if (method == null)
                            return 1;
                    }

                    NodeList vfx = e.getChildNodes();
                    vfx = vfx.item(0).getChildNodes();

                    List<Line> shapes = new ArrayList<Line>();
                    Polygon p = null;
                    Text t = null;

                    Element draw;
                    System.out.println(vfx.getLength());

                    System.out.println(vfx.item(0).getClass());

                    for (int j = 0; j < vfx.getLength(); j++)
                    {
                        draw = ((Element)vfx.item(j));
                        System.out.println("LINE? " + draw.getNodeName() + "\n\n\n");
                        if(draw.getNodeName().equals("Line"))
                        {
                            Line l = new Line();
                            l.setStartX(Double.parseDouble(draw.getAttribute("startX")));
                            l.setStartY(Double.parseDouble(draw.getAttribute("startY")));
                            l.setEndX(Double.parseDouble(draw.getAttribute("endX")));
                            l.setEndY(Double.parseDouble(draw.getAttribute("endY")));
                            if(draw.getAttribute("dashed").equals("true"))
                                l.getStrokeDashArray().addAll(6d);
                            else
                                l.getStrokeDashArray().clear();

                            shapes.add(l);
                            l.setStroke(Color.BLUE);
                        }

                        if(draw.getNodeName().equals("Polygon"))
                        {
                            p = new Polygon();
                            p.getPoints().addAll(new Double[]{Double.parseDouble(draw.getAttribute("x0")), Double.parseDouble(draw.getAttribute("y0")), Double.parseDouble(draw.getAttribute("x1")), Double.parseDouble(draw.getAttribute("y1")), Double.parseDouble(draw.getAttribute("x2")), Double.parseDouble(draw.getAttribute("y2"))});
                            p.setStroke(Color.BLUE);
                        }

                        if(draw.getNodeName().equals("Text"))
                        {
                            t = new Text();
                            t.setText(draw.getAttribute("text"));
                            t.setX(Double.parseDouble(draw.getAttribute("x")));
                            t.setY(Double.parseDouble(draw.getAttribute("y")));
                        }
                    }

                    newMessage.set_line(shapes, p, t);
                    this.ap.getChildren().addAll(newMessage.get_line());
                }
            }
        }
        catch (Exception e)   
        {
            e.printStackTrace();
            return 1;
        }

        this.generate_grid_layout();
        return 0;
    }


    private int get_correct_id(int id)
    {
        return (id - this.loadDisplacement + this.currDisplacement);
    }


    private Element next(int id, NodeList sClass, NodeList messL)
    {
        System.out.println("Next id: " + id);
        for(int i = 0; i < sClass.getLength() || i < messL.getLength(); i++)
        {
            Element el;

            if(i < sClass.getLength())
            {
                el = ((Element)sClass.item(i));
                if (Integer.parseInt(el.getAttribute("id")) == id)
                    return el;
            }
            if(i < messL.getLength())
            {
                el = ((Element)messL.item(i));
                if (Integer.parseInt(el.getAttribute("id")) == id)
                    return el;
            }
        }

        return null;
    }

    private ComboBox find_cbox()
    {
        for (javafx.scene.Node n : this.gp.getChildren())
        {
            if(n instanceof ComboBox)
                return ((ComboBox)n);    
        }

        return null;
    }

    private int find_max_activity()
    {
        int max = 0;
        for (Seq_Class c : this.sd.get_actors())
        {
            if(max < c.get_activity().size())
                max = c.get_activity().size();
        }

        return max;
    }


    private void generate_grid_layout()
    {
        int i;
        List<Seq_Class> clist = this.sd.get_actors();
        this.gp.getChildren().remove(this.find_cbox());

        List<Boolean> neu = new ArrayList<Boolean>();
        
        for(int g = 0; g < this.find_max_activity(); g++)
            neu.add(Boolean.TRUE);
        
        this.sd.get_actors().get(0).set_activity(neu);

        for(i = 0; i < clist.size(); i++)
        {
            if(i > 0)
            {
                this.gp.add(new Label(clist.get(i).get_name() + " ID:" + clist.get(i).get_instance()), i, 0);
            }

            List<Boolean> act = clist.get(i).get_activity();
            
            System.out.println(act);
            int j;

            for(j = 0; j < act.size(); j++)
            {
                if(i == 0)
                {
                    if(j > 0)
                    {
                        Rectangle rec = new Rectangle(15,40);
                        this.gp.add(rec,0, j+1);
                        this.gp.setHalignment(rec, HPos.CENTER);
                    }
                }
                else
                {
                    Rectangle rec;
                    System.out.println(act.get(j));
                    if(act.get(j))
                        rec = new Rectangle(15, 40);
                    else
                        rec = new Rectangle(2, 20);

                    this.gp.add(rec, i, j + 1);
                    this.gp.setHalignment(rec, HPos.CENTER);
                }
            }
        }


        this.gp.setHgap(100.0);
    }
}
