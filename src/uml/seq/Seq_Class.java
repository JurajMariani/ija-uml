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
    protected boolean is_constructed;


    public Seq_Class(Core_Class ref, int inactivity_rows)
    {
        super(ref.get_name());

        /** This will, for now at least, stay as a shallow copy */
        this.reference_class = ref;
        this.messagelist = ref.get_methods();

        this.activity_list = new ArrayList<Boolean>();

        for(int i = 0; i < inactivity_rows; i++)
            this.activity_list.add(Boolean.FALSE);

        if(this.get_instance() == 1)
            this.activity_list.add(Boolean.TRUE);

        this.is_constructed = false;
    }

    public void update()
    {
        this.messagelist = this.reference_class.get_methods();
        this.rename(this.reference_class.get_name());
    }

    public Core_Class get_reference_class()
    {
        return this.reference_class;
    }

    public void activity_check(Seq_Message message)
    {
        System.out.println("CLASS: " + this.get_name());
        if ((message.get_caller() == this) || (message.get_receiver() == this))
        {
            this.activity_list.add(Boolean.TRUE);
        }
        else
        {
            if(this.get_name().equals("System"))
                this.activity_list.add(Boolean.TRUE);
            else
                this.activity_list.add(Boolean.FALSE);
        }
    }

    public void construct()
    {
        this.is_constructed = true;
    }

    public boolean is_constructed()
    {
        return this.is_constructed;
    }

    public Boolean get_last_activity()
    {
        return this.activity_list.get(this.activity_list.size() - 1);
    }

    public List<Boolean> get_activity()
    {
        return Collections.unmodifiableList(this.activity_list);
    }
    
}