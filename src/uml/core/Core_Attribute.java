package uml.core;


import uml.core.Core_Typed;

/**
 * CLASS: CORE ATTRIBUTE
 * 
 * <p> Class Core_Attribute defines all attributes and method parameters
 *
 * @author Juraj Mariani
 */
public class Core_Attribute extends Core_Typed
{
    /**
     * Attribute value is a default UML attribute value
     */
    protected String value;

    /**
     * Bare constructor
     * <p> creates an empty attribute
     */
    public Core_Attribute()
    {
        super();
        value = "";
    }

    /**
     * Full constructor
     * <p> creates a fully filled attribute
     * 
     * @param name Attribute name
     * @param type Attribute type
     * @param value Attribute default value
     * @param visibility Attribute visibility modifier
     */
    public Core_Attribute(String name, String type, String value, int visibility)
    {
        super(name, type, visibility);
        this.value = value;
    }

    /**
     * Changes the default value to new_value
     * @param new_value The new default value
     */
    public void change_value(String new_value)
    {
        this.value = new_value;
    }

    /**
     * @return Attribute's default value
     */
    public String get_value()
    {
        return( this.value );
    }

    /**
     * Print Attribute in a special format:
     * 
     * [+#-]_name_{ = _value_}: _type_
     * 
     * @param is_param An optional parameter for method parameters
     * @return Attribute in string format
     */
    public String get_str_attribute(boolean... is_param)
    {
        StringBuilder str = new StringBuilder();
        if (is_param.length == 0)
            str.append(this.get_str_visibility());
        str.append(this.get_name());
        if ( this.value != "" )
            str.append(" = " + this.value);

        str.append(": ");
        str.append(this.type);
        
        return ( str.toString() );
    }
}