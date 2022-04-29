package uml.seq;

import uml.core.Element;
import uml.seq.Seq_Class;
import uml.seq.Seq_IDable;


public class Seq_Message extends Seq_IDable
{
    protected Seq_Class call_actor;
    protected Seq_Class receive_actor;

    protected boolean ack_message;


    public Seq_Message(Seq_Class s, Seq_Class e, boolean ack)
    {
        super("");
        this.call_actor = s;
        this.receive_actor = e;
        this.ack_message = ack;
    }

    public Seq_Class get_caller()
    {
        return this.call_actor;
    }

    public Seq_Class get_receiver()
    {
        return this.receive_actor;
    }
}