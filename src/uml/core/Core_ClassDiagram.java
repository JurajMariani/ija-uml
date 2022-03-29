package uml.core;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import uml.core.Element;
import uml.core.Core_Class;
import uml.core.Core_Link;

public class Core_ClassDiagram extends Element
{
    protected List<Core_Class> class_list;
    protected List<Core_Link> link_list;

    public Core_ClassDiagram()
    {
        super();
        this.class_list = new ArrayList<Core_Class>();
        this.link_list = new ArrayList<Core_Link>();
    }

    public Core_ClassDiagram(String name)
    {
        super( name );
        this.class_list = new ArrayList<Core_Class>();
        this.link_list = new ArrayList<Core_Link>();
    }

    public Core_Class add_class()
    {
        Core_Class class_o = new Core_Class();
        this.class_list.add( class_o );
        return( class_o );
    }

    public void remove_class(Core_Class class_o)
    {
        this.class_list.remove( class_o );
    }

    public Core_Class get_class(String name)
    {
        for (Core_Class item : this.class_list)
        {
            if( item.name == name )
                return item;
        }

        return null;
    }

    public Core_Link add_link(Element start_object, Element end_object)
    {
        Core_Link link = new Core_Link(start_object, end_object);
        this.link_list.add( link );
        return( link );
    }

    public void remove_link(Core_Link link)
    {
        this.link_list.remove( link );
    }

    public Core_Link get_link(String name)
    {
        for (Core_Link item : this.link_list)
        {
            if( item.name == name )
                return item;
        }

        return null;
    }

    public List<Core_Class> get_classes()
    {
        return Collections.unmodifiableList(this.class_list);
    }

    public List<Core_Link> get_links()
    {
        return Collections.unmodifiableList(this.link_list);
    }
}