package uml.seq;


import uml.core.Core_Method;
import uml.core.Core_Class;
import uml.seq.Seq_IDable;
import uml.seq.Seq_Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Seq_Class extends Seq_IDable{
    
    protected Core_Class reference_class;
    protected List<Core_Method> messagelist;
    protected List<Boolean> activity_list;


    public Seq_Class(Core_Class ref)
    {
        super(ref.get_name());

        /** This will, for now at least, stay as a shallow copy */
        this.reference_class = ref;
        this.messagelist = ref.get_methods();

        this.activity_list = new ArrayList<Boolean>();
        this.activity_list.add(Boolean.TRUE);
    }

    public void update_sequence()
    {
        this.messagelist = this.reference_class.get_methods();
    }

    public void activity_check(Seq_Message message)
    {
        if ((message.get_caller() != this) || (message.get_receiver() != this))
        {
            this.activity_list.add(Boolean.TRUE);
        }
        else
        {
            this.activity_list.add(Boolean.FALSE);
        }
    }

    public List<Boolean> get_activity()
    {
        return Collections.unmodifiableList(this.activity_list);
    }
    
}