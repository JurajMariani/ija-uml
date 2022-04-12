package uml.core;


import uml.core.Core_Typed;
import uml.core.Core_Attribute;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * CLASS: CORE METHOD
 * 
 * <p> Class Core_Method defines methods and operations over them
 *
 * @author Juraj Mariani
 */
public class Core_Method extends Core_Typed
{
    /**
     * List containing all the method parameters
     */
    protected List<Core_Attribute> params;

    /**
     * Bare constructor
     * <p> creates an empty method
     */
    public Core_Method()
    {
        super("", "void", 0);
        this.params = new ArrayList<Core_Attribute>();
    }

    /**
     * Full constructor
     * <p> creates a fully filled method head
     * 
     * @param name Method name
     * @param type Method return type
     * @param visibility method modifier
     */
    public Core_Method(String name, String type, int visibility)
    {
        super(name, type, visibility);
        this.params = new ArrayList<Core_Attribute>();
    }

    /**
     * Creates am empty parameter within a method
     * @return Reference to the new parameter
     */
    public Core_Attribute add_param()
    {
        Core_Attribute attr = new Core_Attribute();
        this.params.add( attr );
        return( attr );
    }

    /**
     * Removes the Core_Attribute object reference from the param list
     * @param param Reference to the deleted object
     */
    public void remove_param(Core_Attribute param)
    {
        this.params.remove( param );
    }

    /**
     * Looks up the name in param list
     * @param name Name of the wanted parameter
     * @return If found - reference to the Attribute, otherwise - null
     */
    public Core_Attribute get_param(String name)
    {
        for (Core_Attribute item : this.params)
        {
            if ( item.name == name )
                return item;    
        }
        
        return null;
    }

<<<<<<< HEAD
    public List<Core_Attribute> get_params()
    {
        return Collections.unmodifiableList(this.params);
    }

=======
    /**
     * Print Method in a special format:
     * 
     * [+#-]_name_(_param_name_{ = _param_value_}, ...): _type_
     * 
     * @return Method in string format
     */
>>>>>>> javadoc-documentation
    public String get_str_method()
    {
        StringBuilder str = new StringBuilder();
        str.append( this.get_str_visibility() );
        str.append( this.name );
        str.append("(");

        int i;
        for (i = 0; i < this.params.size() - 1; i++)
        {
            str.append(this.params.get(i).get_str_attribute(true));
            str.append(", ");
        }

        if ( this.params.size() > 0)
            str.append(this.params.get(i).get_str_attribute(true));
        str.append("): ");

        str.append(this.type);

        return( str.toString() );
    }


    /**
     * @return Unmodifiable parameter list
     */
    public List<Core_Attribute> get_params()
    {
        return Collections.unmodifiableList(this.params);
    }
}