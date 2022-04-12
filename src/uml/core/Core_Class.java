package uml.core;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import uml.core.Element;
import uml.core.Core_Attribute;
import uml.core.Core_Method;

/**
 * CLASS: CORE CLASS
 * 
 * <p> Class Core_Class implements classes within the Class Diagram
 *
 * @author Juraj Mariani
 */
public class Core_Class extends Element
{
    protected List<Core_Attribute> attribute_list;
    protected List<Core_Method> method_list;

    /**
     * Bare constructor
     * <p> creates an empty class
     */
    public Core_Class()
    {
        super();
        this.attribute_list = new ArrayList<Core_Attribute>();
        this.method_list = new ArrayList<Core_Method>();
    }

    /**
     * Creates a class with a given name
     * @param name Name of the new class
     */
    public Core_Class(String name)
    {
        super(name);
        this.attribute_list = new ArrayList<Core_Attribute>();
        this.method_list = new ArrayList<Core_Method>();
    }

    /**
     * Creates a new blank attribute, appends the new attribute to the attribute list
     * @return Reference to the new attribute
     */
    public Core_Attribute add_attribute()
    {
        Core_Attribute attr = new Core_Attribute();
        this.attribute_list.add(attr);
        return( attr );
    }

    /**
     * Removes the reference to object 'attr' from the attribute list
     * @param attr reference to the to be removed attribute
     */
    public void remove_attribute(Core_Attribute attr)
    {
        this.attribute_list.remove( attr );
    }

    /**
     * @param name Name of the attribute
     * @return Reference to the found attribute / null
     */
    public Core_Attribute get_attribute(String name)
    {
        for (Core_Attribute item : this.attribute_list)
        {
            if ( item.name.equals(name) )
                return item;    
        }

        return null;
    }

    /**
     * Creates a new blank method, appends the new method to the method list
     * @return Reference to the new method
     */
    public Core_Method add_method()
    {
        Core_Method method = new Core_Method();
        this.method_list.add(method);
        return(method);
    }

    /**
     * Removes the reference to object 'meth' from the method list
     * @param meth reference to the to be removed method
     */
    public void remove_method(Core_Method meth)
    {
        this.method_list.remove( meth );
    }

    /**
     * @param name Name of the method
     * @return Reference to the found method / null
     */
    public Core_Method get_method(String name)
    {
        for (Core_Method item : this.method_list)
        {
            if ( item.name.equals(name) )
                return item;    
        }

        return null;
    }

    /**
     * @return Unmodifiable attribute list
     */
    public List<Core_Attribute> get_attributes()
    {
        return Collections.unmodifiableList(this.attribute_list);
    }

    /**
     * @return Unmodifiable method list
     */
    public List<Core_Method> get_methods()
    {
        return Collections.unmodifiableList(this.method_list);
    }
}