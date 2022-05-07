package uml.seq;


import uml.core.Element;
import uml.core.Core_Class;
import uml.core.Core_Method;
import uml.core.Core_ClassDiagram;
import uml.core.Core_Link;
import uml.seq.Seq_Message;
import uml.seq.Seq_Class;
import uml.seq.Seq_IDable;

import uml.io.save_seq_xml;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Seq_SequenceDiagram extends Seq_IDable{

    protected Core_ClassDiagram cd;
    protected List<Core_Class> available_classes;
    protected List<Seq_Class> classlist;
    protected List<Seq_Message> messagelist;
    protected save_seq_xml saver;

    public Seq_SequenceDiagram(String name, Core_ClassDiagram cd)
    {
        super(name);
        this.cd = cd;

        this.available_classes = cd.get_classes();

        this.classlist = new ArrayList<Seq_Class>();
        this.messagelist = new ArrayList<Seq_Message>();
        Core_Class system = new Core_Class("System");
        Seq_Class ref = new Seq_Class(system, 0);
        ref.construct();
        this.classlist.add(ref);

        saver = new save_seq_xml(this, this.get_name());
    }

    public save_seq_xml get_saver()
    {
        return this.saver;   
    }

    public boolean is_in_classlist()
    {
        return true;
    }

    public Core_ClassDiagram get_reference()
    {
        return this.cd;
    }

    public Seq_Class add_actor(Core_Class avail, int inactive)
    {
        //this.update_actor_activity(this.dummy);
        Seq_Class sequence_actor = new Seq_Class(avail, inactive);
        this.classlist.add(sequence_actor);
            
        return sequence_actor;
    }

    public Core_Class get_actor_ref(String name)
    {
        return this.cd.get_class(name);
    }

    public void remove_actor(Seq_Class actor)
    {
        this.classlist.remove(actor);
    }

    public List<Core_Class> get_available()
    {
        return Collections.unmodifiableList(this.available_classes);
    }

    public Seq_Class get_actor_by_name(String name)
    {
        for (Seq_Class actor : this.classlist)
        {
            if( actor.get_name().equals(name) )
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


    public void attribute_update()
    {
        for (Seq_Class c : this.classlist)
        {
            c.update();    
        }

        for (Seq_Message m : this.messagelist)
        {
            m.update();       
        }
    }

    public void bond_update(Core_Link l)
    {

    }


    public List<Seq_Message> get_messages()
    {
        return Collections.unmodifiableList(this.messagelist);
    }


    public void remove_message(Seq_Message mess)
    {
        this.messagelist.remove(mess);
    }

    private void update_actor_activity(Seq_Message last_message)
    {
        for (Seq_Class s_class : this.classlist)
        {
            s_class.activity_check(last_message);
        }
    }

    public Seq_Message add_message(Seq_Class src, Seq_Class dst, String name, Core_Method m, boolean ack, boolean constructor)
    {
        Seq_Message mess = null;

        if(dst.is_constructed() || constructor)
        {
            if (constructor)
                dst.construct();
            mess = new Seq_Message(src, dst, ack, constructor);
            mess.rename(name);
            mess.set_ref(m);
            this.update_actor_activity(mess);
            this.messagelist.add(mess);
        }

        return mess;
    }


    public Seq_Message add_message_silent(Seq_Class src, Seq_Class dst, String name, Core_Method m, boolean ack, boolean constructor)
    {
        Seq_Message mess = null;

        if(dst.is_constructed() || constructor)
        {
            if (constructor)
                dst.construct();
            mess = new Seq_Message(src, dst, ack, constructor);
            mess.rename(name);
            mess.set_ref(m);
            this.messagelist.add(mess);
        }

        return mess;
    }

    public List<Seq_Class> get_actors()
    {
        return Collections.unmodifiableList(this.classlist);
    }

    public List<Seq_Class> get_actors_with_bond_to(int id)
    {
        Seq_Class cl = this.get_actor_by_id(id);
        List<Seq_Class> retList = new ArrayList<Seq_Class>();

        List<Core_Link> bonds = this.cd.get_links();

        for (Core_Link bond : bonds)
        {
            if(bond.get_objects().get(0).get_name().equals(cl.get_name()))
                if(this.get_actor_by_name(bond.get_objects().get(1).get_name()) != null)
                    retList.add(this.get_actor_by_name(bond.get_objects().get(1).get_name()));
        }

        return retList;
    }

    public List<Seq_Message> get_active_messages_with(int classId)
    {
        Seq_Class c = this.get_actor_by_id(classId);
        List<Seq_Message> ret = new ArrayList<Seq_Message>();

        for (Seq_Message m : this.messagelist)
        {
            if (m.get_caller() == c || m.get_receiver() == c)
                ret.add(m);    
        }

        return ret;
    }
}