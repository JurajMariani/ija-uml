package uml.core;


import uml.core.Element;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import uml.core.Core_Typed;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * CLASS: CORE LINK
 * 
 * <p> Class Core_Link implements bonds between elements
 *
 * @author Juraj Mariani
 */
public class Core_Link extends Core_Typed
{
    protected Core_Class start_object;
    protected Core_Class end_object;
    protected String start_card;
    protected String end_card;
    protected String l_type;
    protected Node[] link;

    /**
     * @param s_o Start object, in generalisation this object inherites
     * @param e_o End object,  in generalisation this object is inherited
     */
    public Core_Link(Core_Class s_o, Core_Class e_o)
    {
        super();
        this.start_object = s_o;
        this.end_object = e_o;
        this.start_card = "";
        this.end_card = "";
    }

    /**
     * Sets the bond type
     * If the new type is generalisation -> "gen", Start class inherits the end classes attributes and methods
     * 
     * @param new_type new bond type
     */
    public void set_type(String new_type)
    {
        this.l_type = new_type;

        if(this.l_type.equals("gen"))
        {
            List<Core_Attribute> params = this.end_object.get_attributes();
            List<Core_Method> methods = this.end_object.get_methods();
            Core_Method tmp_m;
            Core_Attribute tmp_a;

            for (Core_Method m : methods)
                this.start_object.add_method_object(m);

            for (Core_Attribute a : params)
                this.start_object.add_attribute_object(a);
        }
    }

    /**
     * @return Bond type
     */
    public String get_type()
    {
        return this.l_type;
    }

    /**
     * @param o New starting object
     */
    public void change_start(Core_Class o)
    {
        this.start_object = o;
    }

    /**
     * @param o New end object
     */
    public void change_end(Core_Class o)
    {
        this.end_object = o;
    }

    /**
     * Changes the cardinality of the start object
     * @param card New start object cardinality
     */
    public void change_start_card(String card)
    {
        this.start_card = card;
    }

    /**
     * Changes the cardinality of the end object
     * @param card New end object cardinality
     */
    public void change_end_card(String card)
    {
        this.end_card = card;
    }

    /**
     * Add line beteween two classes
     * @param newLink New link line
     */
    public void add_line(Node[] newLink)
    {
        this.link = newLink;
    }

    /**
     * @return Array containing the start and end object references
     */
    public List<Core_Class> get_objects()
    {
        List<Core_Class> ret = new ArrayList<Core_Class>();
        ret.add(this.start_object);
        ret.add(this.end_object);

        return Collections.unmodifiableList(ret);
    }

    /**
     * @return Array containing the start and end object cardinalities
     */
    public String[] get_card()
    {
        String[] ret = { this.start_card, this.end_card };
        return ret;
    }

    /**
     * 
     * @return Array containing all objects from link between classes
     */
    public Node[] get_link()
    {
        return this.link;
    }

}