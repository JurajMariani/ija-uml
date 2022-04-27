package uml.seq;


import uml.core.Element;
import uml.core.Core_Class;
import uml.core.Core_Method;
import uml.core.Core_ClassDiagram;
import uml.seq.Seq_Message;
import uml.seq.Seq_Class;
import uml.seq.Seq_IDable;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Seq_SequenceDiagram extends Seq_IDable{

    protected Core_ClassDiagram cd;
    protected List<Core_Class> available_classes;
    protected List<Seq_Class> classlist;
    protected List<Seq_Message> messagelist;

    public Seq_SequenceDiagram(String name, Core_ClassDiagram cd)
    {
        super(name);
        this.cd = cd;

        this.available_classes = cd.get_classes();

        this.classlist = new ArrayList<Seq_Class>();
        this.messagelist = new ArrayList<Seq_Message>();
        Core_Class system = new Core_Class("System");
        Core_Method meth = system.add_method();
        meth.rename("init");
        this.classlist.add(system);
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


    public Seq_Message get_message_by_id(int id)
    {
        for (Seq_Message mess : this.messagelist)
        {
            if( mess.get_instance() == id )
                return mess;
        }

        return null;
    }


    public void remove_message(Seq_Message mess)
    {
        this.classlist.remove(mess);
    }


    public void update_actor_activity(Seq_Message last_message)
    {
        for (Seq_Class s_class : this.classlist)
        {
            s_class.activity_check(last_message);
        }
    }

    public Seq_Message add_message(Seq_Class src, Seq_Class dst, String name, boolean ack)
    {
        Seq_Message mess = new Seq_Message(src, dst, ack);
        mess.rename(name);
        this.update_actor_activity(mess);
    }
}