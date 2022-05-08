package uml.core;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import uml.core.Element;
import uml.core.Core_Class;
import uml.core.Core_Link;
import uml.seq.Seq_SequenceDiagram;

/**
 * CLASS: CORE CLASSDIAGRAM
 * 
 * <p> Class Core_ClassDiagram implements the Class Diagram
 *
 * @author Juraj Mariani
 */
public class Core_ClassDiagram extends Element
{
    protected List<Core_Class> class_list;
    protected List<Core_Link> link_list;
    protected List<Seq_SequenceDiagram> sds;

    /**
     * Bare constructor
     * <p> creates an empty class diagram with no name
     */
    public Core_ClassDiagram()
    {
        super();
        this.class_list = new ArrayList<Core_Class>();
        this.link_list = new ArrayList<Core_Link>();
        this.sds = new ArrayList<Seq_SequenceDiagram>();
    }

    /**
     * Creates a class diagram with a given name
     * @param name Name of the new class diagram
     */
    public Core_ClassDiagram(String name)
    {
        super( name );
        this.class_list = new ArrayList<Core_Class>();
        this.link_list = new ArrayList<Core_Link>();
    }


    /**
     * Checks, whether a class with a given name exists
     * @param name Checked name
     * @return true if exists, otherwise false
     */
    private boolean class_name_already_exists(String name)
    {
        for (Core_Class class_instance : this.class_list)
        {
            if(class_instance.get_name().equals(name))
                return true;    
        }

        return false;
    }

    /**
     * Creates a new blank class, appends the new class to the class list
     * @return Reference to the new class
     */
    public Core_Class add_class(String name)
    {
        if(! this.class_name_already_exists(name) )
        {
            Core_Class class_o = new Core_Class(name);
            this.class_list.add( class_o );
            return( class_o );
        }
        else
        {
            return null;
        }
    }

    /**
     * Removes the reference to object 'class_o' from the class list
     * @param class_o reference to the to be removed class
     */
    public void remove_class(Core_Class class_o)
    {
        this.class_list.remove( class_o );
    }

    /**
     * @param name Name of the class
     * @return Reference to the found class / null
     */
    public Core_Class get_class(String name)
    {
        for (Core_Class item : this.class_list)
        {
            if( item.name.equals(name) )
                return item;
        }

        return null;
    }

    /**
     * Creates a new bond between two objects
     * 
     * @param start_object 
     * @param end_object
     * @return Reference to the created bond
     */
    public Core_Link add_link(Core_Class start_object, Core_Class end_object)
    {
        Core_Link link = new Core_Link(start_object, end_object);
        this.link_list.add( link );
        return( link );
    }

    /**
     * Removes the reference to object 'link' from the link list
     * @param link reference to the to be removed link
     */
    public void remove_link(Core_Link link)
    {
        this.link_list.remove( link );
    }

    /**
     * @param name Name of the link
     * @return Reference to the found link / null
     */
    public Core_Link get_link(String name)
    {
        for (Core_Link item : this.link_list)
        {
            if( item.name.equals(name) )
                return item;
        }

        return null;
    }

    /**
     * @return Unmodifiable class list
     */
    public List<Core_Class> get_classes()
    {
        return Collections.unmodifiableList(this.class_list);
    }

    /**
     * @return Unmodifiable link list
     */
    public List<Core_Link> get_links()
    {
        return Collections.unmodifiableList(this.link_list);
    }

    /**
     * @return List containing all sequence diagrams
     */
    public List<Seq_SequenceDiagram> get_sequence_diagrams()
    {
        return Collections.unmodifiableList(this.sds);
    }

    /**
     * @param idx Seq. Diagram index
     * @return N-th Seq. Diagram
     */
    public Seq_SequenceDiagram get_seq_diagram(int idx)
    {
        if(idx < this.sds.size())
            return this.sds.get(idx);
        
        return null;
    }

    /**
     * Adds a Seq. Diagram to collection
     * @param sd
     */
    public void add_sd(Seq_SequenceDiagram sd)
    {
        this.sds.add(sd);
    }
}