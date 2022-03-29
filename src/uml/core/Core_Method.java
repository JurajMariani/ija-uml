package uml.core;


import uml.core.Core_Typed;
import uml.core.Core_Attribute;
import java.util.List;
import java.util.ArrayList;

public class Core_Method extends Core_Typed
{
    protected List<Core_Attribute> params;
    protected List<Core_Attribute> returns;

    public Core_Method()
    {
        super("", "void", 0);
        this.params = new ArrayList<Core_Attribute>();
        this.returns = new ArrayList<Core_Attribute>();
    }

    public Core_Method(String name, String type, int visibility)
    {
        super(name, type, visibility);
        this.params = new ArrayList<Core_Attribute>();
        this.returns = new ArrayList<Core_Attribute>();
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

    public Core_Attribute get_param(String name)
    {
        for (Core_Attribute item : this.params)
        {
            if ( item.name == name )
                return item;    
        }
        
        return null;
    }

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
}