package uml.core;


public class Element extends java.lang.Object
{
    protected String name;
    protected int coordinate_x;
    protected int coordinate_y;


    public Element()
    {
        this.name = "";
        this.coordinate_x = 0;
        this.coordinate_y = 0;
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

    public void move(int new_x, int new_y)
    {
        this.coordinate_x = new_x;
        this.coordinate_y = new_y;
    }

    public int[] get_position()
    {
        int[] ret = { this.coordinate_x, this.coordinate_y };
        return ret;
    }
}