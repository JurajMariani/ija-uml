package uml.core;


import uml.core.Element;

public class Core_Typed extends Element
{
    String type;


    public Core_Typed()
    {
        super();
        this.type = "";
    }

    public Core_Typed(String name, String type)
    {
        super(name);
        this.type = type;
    }

    public String get_type()
    {
        return( this.type );
    }

    public void change_type(String new_type)
    {
        this.type = new_type;
    }
}