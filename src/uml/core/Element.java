package uml.core;

/**
 * CLASS: ELEMENT
 * 
 * <p>Class element is the base for all other classes, it keeps track of the name of the object and it's position.
 * The position is there only for the io operations (load/store)
 *
 * @author Juraj Mariani
 */

public class Element extends java.lang.Object
{
    protected String name;
    protected double coordinate_x;
    protected double coordinate_y;

    /**
     * Element constructor
     *
     * <p>Creates a nameless element with default position [0,0]
     */
    public Element()
    {
        this.name = "";
        this.coordinate_x = 0;
        this.coordinate_y = 0;
    }

    /**
     * Element constructor, named variant
     *
     * <p>Creates a named element with default position [0,0]
     * @param (name) (Name of the element)
     */
    public Element(String name)
    {
        this.name = name;
    }

    /**
     * @return Name of the Element object
     */
    public String get_name()
    {
        return( this.name );
    }

    /**
     * Changes the name of the Element object
     *
     * @param new_name The new name of the Element
     */
    public void rename(String new_name)
    {
        this.name = new_name;
    }

    /**
     * Change the Element's coordinates to given values
     * <p> The attributes are used only in io (load/store) context
     *
     * @param new_x New X axis coordinate
     * @param new_y New Y axis coordinate
     */
    public void move(double new_x, double new_y)
    {
        this.coordinate_x = new_x;
        this.coordinate_y = new_y;
    }

    /**
     * @return Element's X and Y axis coordinates
     */
    public double[] get_position()
    {
        double[] ret = { this.coordinate_x, this.coordinate_y };
        return ret;
    }
}