package uml.core;


import uml.core.Element;
import uml.core.Core_Typed;

public class Core_Link extends Core_Typed
{
    Element start_object;
    Element end_object;
    String start_card;
    String end_card;

    public Core_Link(Element s_o, Element e_o)
    {
        super();
        this.start_object = s_o;
        this.end_object = e_o;
        this.start_card = "";
        this.end_card = "";
    }

    public void change_start(Element o)
    {
        this.start_object = o;
    }

    public void change_end(Element o)
    {
        this.end_object = o;
    }

    public void change_start_card(String card)
    {
        this.start_card = card;
    }

    public void change_end_card(String card)
    {
        this.end_card = card;
    }
}