package uml.core;


import uml.core.Core_Typed;

public class Core_Attribute extends Core_Typed
{
    protected String value;

    public Core_Attribute()
    {
        super();
        value = "";
    }

    public Core_Attribute(String name, String type, String value, int visibility)
    {
        super(name, type, visibility);
        this.value = value;
    }

    public void change_value(String new_value)
    {
        this.value = new_value;
    }

    public String get_value()
    {
        return( this.value );
    }

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