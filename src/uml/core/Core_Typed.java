package uml.core;


import uml.core.Element;

public class Core_Typed extends Element
{
    //  0 -> public
    //  1 -> protected
    //  2 -> private
    protected int visibility;
    protected String type;

    public Core_Typed()
    {
        super();
        this.type = "";
        this.visibility = 0;
    }

    public Core_Typed(String name, String type, int visibility)
    {
        super(name);
        this.type = type;
        this.visibility = visibility;
    }

    public String get_type()
    {
        return( this.type );
    }

    public void change_type(String new_type)
    {
        this.type = new_type;
    }

    public int get_visibility()
    {
        return( this.visibility );
    }

    public void change_visibility(int new_visibility)
    {
        this.visibility = new_visibility;
    }

    public String get_str_visibility()
    {
        switch( this.visibility )
        {
            case 0:
                return( "+" );

            case 1:
                return( "#" );

            case 2:
                return( "-" );

            default:
                return( "/" );
        }
    }
}