package uml.core;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import uml.core.Element;
import uml.core.Core_Attribute;
import uml.core.Core_Method;

public class Core_Class extends Element
{
    protected List<Core_Attribute> attribute_list;
    protected List<Core_Method> method_list;

    public Core_Class()
    {
        super();
        this.attribute_list = new ArrayList<Core_Attribute>();
        this.method_list = new ArrayList<Core_Method>();
    }

    public Core_Class(String name)
    {
        super(name);
        this.attribute_list = new ArrayList<Core_Attribute>();
        this.method_list = new ArrayList<Core_Method>();
    }

    public Core_Attribute add_attribute()
    {
        Core_Attribute attr = new Core_Attribute();
        this.attribute_list.add(attr);
        return( attr );
    }

    public void remove_attribute(Core_Attribute attr)
    {
        this.attribute_list.remove( attr );
    }

    public Core_Attribute get_attribute(String name)
    {
        for (Core_Attribute item : this.attribute_list)
        {
            if ( item.name == name )
                return item;    
        }

        return null;
    }

    public Core_Method add_method()
    {
        Core_Method method = new Core_Method();
        this.method_list.add(method);
        return(method);
    }

    public void remove_method(Core_Method meth)
    {
        this.method_list.remove( meth );
    }

    public Core_Method get_method(String name)
    {
        for (Core_Method item : this.method_list)
        {
            if ( item.name == name )
                return item;    
        }

        return null;
    }

    public List<Core_Attribute> get_attributes()
    {
        return Collections.unmodifiableList(this.attribute_list);
    }

    public List<Core_Method> get_methods()
    {
        return Collections.unmodifiableList(this.method_list);
    }
}