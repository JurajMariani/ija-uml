package uml.seq;



import uml.core.Element;

/**
 * CLASS: SEQ IDABLE
 * 
 * <p> Class Seq_IDable assignes a unique ID to all Instances and Subclasses
 *
 * @author Juraj Mariani
 */
public class Seq_IDable extends Element
{
    protected static int instance;
    protected int id;

    /**
     * Constructor
     * @param name Element name
     */
    public Seq_IDable(String name)
    {
        super(name);
        this.id = instance;
        instance++;
    }

    /**
     * @return Object instance - ID
     */
    public int get_instance()
    {
        return this.id;
    }

    /**
     * Used to reset the ID counter to load Elements properly
     * <p> Used only during LOAD
     */
    public void reset_master()
    {
        instance = 2;
    }
}