package uml.seq;

import uml.core.Element;
import uml.core.Core_Method;
import uml.seq.Seq_Class;
import uml.seq.Seq_IDable;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Seq_Message extends Seq_IDable
{
    protected Seq_Class call_actor;
    protected Seq_Class receive_actor;
    protected Core_Method reference_method;

    protected boolean ack_message;
    protected boolean conts_message;
    protected List<Line> messageLine;
    protected Polygon messageEnd;
    protected Text messageText;


    public Seq_Message(Seq_Class s, Seq_Class e, boolean ack, boolean cons)
    {
        super("");
        this.call_actor = s;
        this.receive_actor = e;
        this.ack_message = ack;
        this.conts_message = cons;
        this.reference_method = null;
    }

    public void set_ref(Core_Method m)
    {
        this.reference_method = m;
    }

    public Core_Method get_ref()
    {
        return this.reference_method;
    }

    public Seq_Class get_caller()
    {
        return this.call_actor;
    }

    public Seq_Class get_receiver()
    {
        return this.receive_actor;
    }

    public void set_line(List<Line> l, Polygon p, Text t)
    {
        this.messageLine = l;
        this.messageEnd = p;
        this.messageText = t;
    }

    public List<Node> get_line()
    {
        List<Node> ret = new ArrayList<Node>();
        ret.addAll(this.messageLine);
        ret.add(this.messageEnd);
        ret.add(this.messageText);

        return Collections.unmodifiableList(ret);
    }

    public boolean is_constructor()
    {
        return this.conts_message;
    }

    public void update()
    {
        if(this.reference_method != null)
            this.rename(this.reference_method.get_str_method());
    }

    public boolean is_ack()
    {
        return this.ack_message;
    }
}