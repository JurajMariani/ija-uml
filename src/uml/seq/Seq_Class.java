package uml.seq;


import uml.core.Element;
import uml.core.Core_Method;
import uml.core.Core_Class;
import java.util.List;

public class Seq_Class extends Element{
    
    protected Core_Class reference_class;
    protected static int instance;
    protected List<Core_Method> messagelist;


    public Seq_Class(Core_Class ref)
    {
        super(ref.name);

        /** This will, for now at least, stay as a shallow copy */
        this.reference_class = ref;
        this.messagelist = ref.get_methods();
        instance++;
    }

    public int get_instance()
    {
        return instance;
    }

    public void update_sequence()
    {
        this.messagelist = this.reference_class.get_methods();
    }

    
}