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

/**
 * CLASS: SEQ MESSAGE
 * 
 * <p> Class Seq_Message defines Messages sent between Sequence Classes
 *
 * @author Juraj Mariani
 */
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


    /**
     * Constructor
     * @param s Caller Class
     * @param e Receiver Class
     * @param ack If Message is Acknowledgement (Return)
     * @param cons If Message is constructor
     */
    public Seq_Message(Seq_Class s, Seq_Class e, boolean ack, boolean cons)
    {
        super("");
        this.call_actor = s;
        this.receive_actor = e;
        this.ack_message = ack;
        this.conts_message = cons;
        this.reference_method = null;
    }

    /**
     * Sets the reference Core_Method this message is based on
     * @param m reference method
     */
    public void set_ref(Core_Method m)
    {
        this.reference_method = m;
    }

    /**
     * @return Core_Method reference method
     */
    public Core_Method get_ref()
    {
        return this.reference_method;
    }

    /**
     * @return The Message caller
     */
    public Seq_Class get_caller()
    {
        return this.call_actor;
    }

    /**
     * The Message receiver
     * @return
     */
    public Seq_Class get_receiver()
    {
        return this.receive_actor;
    }

    /**
     * Sets the JavaFX Nodes defining the Message Line(s), Polygon and Text
     * @param l Message lines
     * @param p Message Polygon
     * @param t Message Text
     */
    public void set_line(List<Line> l, Polygon p, Text t)
    {
        this.messageLine = l;
        this.messageEnd = p;
        this.messageText = t;
    }

    /**
     * @return JavaFX Node List of shapes
     */
    public List<Node> get_line()
    {
        List<Node> ret = new ArrayList<Node>();
        ret.addAll(this.messageLine);
        ret.add(this.messageEnd);
        ret.add(this.messageText);

        return Collections.unmodifiableList(ret);
    }

    /**
     * @return True if Message is an "init()" constructor Message
     */
    public boolean is_constructor()
    {
        return this.conts_message;
    }

    /**
     * After Class Diagram update, update Message name
     */
    public void update()
    {
        if(this.reference_method != null)
            this.rename(this.reference_method.get_str_method());
    }

    /**
     * @return True if Message is an acknowledge (Return) Message
     */
    public boolean is_ack()
    {
        return this.ack_message;
    }
}