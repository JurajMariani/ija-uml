package uml.seq;



import uml.core.Element;

public class Seq_IDable extends Element
{
    protected static int instance;

    public Seq_IDable(String name)
    {
        super(name);
        instance++;
    }
}