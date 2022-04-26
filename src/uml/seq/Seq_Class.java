package uml.seq;


import uml.core.Element;
import uml.core.Core_Class;

public class Seq_Class extends Element{
    
    protected Core_Class reference_class;
    protected static int instance;


    public Seq_Class(Core_Class ref)
    {
        super(ref.name);

        instance++;
    }
}