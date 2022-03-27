package uml.core;


import uml.core.Core_Visibility;
import uml.core.Core_Typed;

public class Core_Attribute extends Core_Typed
{
    String value;
    Core_Visibility visibility;

    public Core_Attribute()
    {
        super();
        value = "";
        visibility = new Core_Visibility();
    }

    public Core_Attribute(String name, Core_Typed type, String value, int visibility)
    {
        super(name, type);
        this.value = value;
        this.visibility = new Core_Visibility(visibility);
    }

    public void change_value(String new_value)
    {
        this.value = new_value;
    }

    public String get_value()
    {
        return( this.value );
    }
}