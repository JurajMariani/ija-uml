package uml.seq;


import uml.core.Element;
import uml.core.Core_Class;
import uml.core.Core_ClassDiagram;
import seq.Seq_Message;
import seq.Seq_Class;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Seq_SequenceDiagram extends Element{

    protected List<Core_Class> available_classes;

    protected List<Seq_SequenceClass> classlist;
    protected List<Seq_Message> messagelist;

    public Seq_SequenceDiagram(Core_ClassDiagram cd)
    {
        super();

        this.available_classes = cd.get_classes();

        this.classlist = new ArrayList<Seq_SequenceClass>();
        this.messagelist = new ArrayList<Seq_Message>();
    }

    public boolean is_in_classlist(){
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

    public Seq_Class get_actor(String name)
    {
        for (Seq_Class actor : this.classlist)
        {
            if( actor.name.equals(name) )
                return actor;
        }

        return null;
    }

    //public Seq_Message
}