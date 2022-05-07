package uml.seq;



import uml.core.Element;

public class Seq_IDable extends Element
{
    protected static int instance;
    protected int id;

    public Seq_IDable(String name)
    {
        super(name);
        this.id = instance;
        instance++;
    }

    public int get_instance()
    {
        return this.id;
    }

    public void reset_master()
    {
        instance = 2;
    }
}