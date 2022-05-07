package uml.core;


import uml.core.Element;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import uml.core.Core_Typed;

/**
 * CLASS: CORE LINK
 * 
 * <p> Class Core_Link implements bonds between elements
 *
 * @author Juraj Mariani
 */
public class Core_Link extends Core_Typed
{
    protected Element start_object;
    protected Element end_object;
    protected String start_card;
    protected String end_card;
    protected Node[] link;

    /**
     * @param s_o Start object, in generalisation this object inherites
     * @param e_o End object,  in generalisation this object is inherited
     */
    public Core_Link(Element s_o, Element e_o)
    {
        super();
        this.start_object = s_o;
        this.end_object = e_o;
        this.start_card = "";
        this.end_card = "";
    }

    /**
     * @param o New starting object
     */
    public void change_start(Element o)
    {
        this.start_object = o;
    }

    /**
     * @param o New end object
     */
    public void change_end(Element o)
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
     * Add link beteween two classes
     * @param newLink New link
     */
    public void add_link(Node[] newLink)
    {
        this.link = newLink;
    }

    /**
     * @return Array containing the start and end object references
     */
    public Element[] get_objects()
    {
        Element[] ret = { this.start_object, this.end_object };
        return ret;
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