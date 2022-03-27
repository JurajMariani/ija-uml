package uml.core;


public class Core_Visibility extends java.lang.Object
{
    //  0 -> public
    //  1 -> protected
    //  2 -> private
    int visibility;

    
    public Core_Visibility()
    {
        this.visibility = 0;
    }

    public Core_Visibility(int visibility)
    {
        this.visibility = visibility;
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