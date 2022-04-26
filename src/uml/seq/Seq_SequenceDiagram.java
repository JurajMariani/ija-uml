package uml.seq;


import uml.core.Element;
import uml.core.Core_Class;
import uml.core.Core_Method;
import uml.core.Core_ClassDiagram;
import seq.Seq_Message;
import seq.Seq_Class;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Seq_SequenceDiagram extends Element{

    protected List<Core_Class> available_classes;

    protected List<Seq_SequenceClass> classlist;
    // moved to seq_class?
    //protected List<Seq_Message> messagelist;

    public Seq_SequenceDiagram(String name, Core_ClassDiagram cd)
    {
        super(name);

        this.available_classes = cd.get_classes();

        this.classlist = new ArrayList<Seq_SequenceClass>();
        Core_Class system = new Core_Class("System");
        Core_Method meth = system.add_method();
        meth.rename("init");
        this.classlist.add(system);
        //this.messagelist = new ArrayList<Seq_Message>();
    }

    public boolean is_in_classlist()
    {
        return true;
    };

    public Seq_Class add_actor(Core_Class avail)
    {
        Seq_Class sequence_actor = new Seq_Class(avail);
        if (sequence_actor == null)
        {
            //Actor already exists - notify user or not??
            return null;
        }
        else
        {
            return sequence_actor;
        }
    }

    public void remove_actor(Seq_Class actor)
    {
        this.classlist.remove(actor);
    }

    public Seq_Class get_actor_by_name(String name)
    {
        for (Seq_Class actor : this.classlist)
        {
            if( actor.name.equals(name) )
                return actor;
        }

        return null;
    }

    public Seq_Class get_actor_by_id(int id)
    {
        for (Seq_Class actor : this.classlist)
        {
            if( actor.get_instance() == id )
                return actor;
        }

        return null;
    }

    //public Seq_Message
}