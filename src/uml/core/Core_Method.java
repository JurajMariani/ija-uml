package uml.core;


import uml.core.Core_Typed;
import uml.core.Core_Attribute;
import java.util.List;
import java.util.ArrayList;

public class Core_Method extends Core_Typed
{
    List<Core_Attribute> params;
    List<Core_Attribute> returns;
    Core_Visibility visibility;

    public Core_Method()
    {
        super();
        this.params = new ArrayList<Core_Attribute>();
        this.returns = new ArrayList<Core_Attribute>();
        this.visibility = new Core_Visibility();
    }

    public Core_Method(String name, String type, String visibility)
    {
        super(name, type);
        this.params = new ArrayList<Core_Attribute>();
        this.returns = new ArrayList<Core_Attribute>();
        this.visibility = new Core_Visibility(visibility);
    }

    public Core_Attribute add_param()
    {
        Core_Attribute attr = new Core_Attribute();
        this.params.add( attr );
        return( attr );
    }

    public void remove_param(Core_Attribute param)
    {
        this.params.remove( param );
    }

    public Core_Attribute add_return()
    {
        Core_Attribute ret = new Core_Attribute();
        this.returns.add( ret );
        return( ret );
    }

    public void remove_return(Core_Attribute retutn)
    {
        this.returns.remove(retutn);
    }

    public Core_Attribute get_param(String name)
    {
        for (Core_Attribute item : this.params)
        {
            if ( item.name == name )
                return item;    
        }
        
        return null;
    }

    public Core_Attribute get_return(String name)
    {
        for (Core_Attribute item : this.returns)
        {
            if ( item.name == name )
                return item;    
        }

        return null;
    }
}