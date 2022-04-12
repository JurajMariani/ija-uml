package uml.core;


import uml.core.Element;

/**
 * CLASS: CORE TYPED
 * 
 * <p> Class Core_Typed is a base for all typed constructs - Attributes and Methods. It has a visibility and a type.
 *
 * @author Juraj Mariani
 */
public class Core_Typed extends Element
{
    /**
     * int visibility - can be public(+)       = 0,
     *                         protected(#)    = 1,
     *                      or private(-)      = 2,
     */
    protected int visibility;
    
    /**
     * String representation of the type
     */
    protected String type;

    /**
     * Bare constructor
     * <p> creates an empty typed element
     */
    public Core_Typed()
    {
        super();
        this.type = "";
        this.visibility = 0;
    }

    /**
     * Full constructor
     * <p> creates a fully filled typed element
     * 
     * @param name name of the typed element
     * @param type type of the element
     * @param visibility the visibility modifier of typed element
     */
    public Core_Typed(String name, String type, int visibility)
    {
        super(name);
        this.type = type;
        this.visibility = visibility;
    }

    /**
     * @return type of the element
     */
    public String get_type()
    {
        return( this.type );
    }

    /**
     * Changes the type of the element
     * 
     * @param new_type the new type of the element
     */
    public void change_type(String new_type)
    {
        this.type = new_type;
    }

    /**
     * @return Visibility as an integer
     */
    public int get_visibility()
    {
        return( this.visibility );
    }

    /**
     * @param new_visibility new visibility modifier
     */
    public void change_visibility(int new_visibility)
    {
        this.visibility = new_visibility;
    }

    /**
     * Outputs a character representing the modifier
     * 
     * @return visibility modifier as a character
     */
    public String get_str_visibility()
    {
        switch( this.visibility )
        {
            case 0:
                return( "+" );

            case 1:
                return( "#" );

            case 2:
                return( "-" );

            default:
                return( "/" );
        }
    }
}