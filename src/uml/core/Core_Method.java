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
        params = new ArrayList<Core_Attribute>();
        returns = new ArrayList<Core_Attribute>();
        visibility = new Core_Visibility();
    }

    public Core_Method(String name, String type, String visibility)
    {
        super(name, type);
        params = new ArrayList<Core_Attribute>();
        returns = new ArrayList<Core_Attribute>();
        visibility = new Core_Visibility(visibility);
    }

    public Core_Attribute create_param()
    {
        Core_Attribute attr = new Core_Attribute();
        params.add( attr );
        return( attr );
    }

    public void remove_param(Core_Attribute param)
    {
        params.remove( param );
    }

    public Core_Attribute create_return()
    {
        Core_Attribute ret = new Core_Attribute();
        returns.add( ret );
        return( ret );
    }

    public void remove_return(Core_Attribute retutn)
    {
        returns.remove(retutn);
    }

    public Core_Attribute get_param(String name)
    {
        for (Core_Attribute item : params)
        {
            if ( item.name == name )
                return item;    
        }
    }

    public Core_Attribute get_return(String name)
    {
        for (Core_Attribute item : returns)
        {
            if ( item.name == name )
                return item;    
        }
    }
}