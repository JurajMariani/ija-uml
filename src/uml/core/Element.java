package uml.core;


public class Element extends java.lang.Object
{
    String name;


    public Element()
    {
        this.name = "";
    }

    public Element(String name)
    {
        this.name = name;
    }

    public String get_name()
    {
        return( this.name );
    }

    public void rename(String new_name)
    {
        this.name = new_name;
    }
}