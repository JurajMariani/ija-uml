package uml.testdir;


import java.util.List;

import uml.core.*;

public class Core_Test extends java.lang.Object
{

    public Core_Test()
    {

    }

    public static void main(String[] argv)
    {

        Core_Test test = new Core_Test();

        System.out.println("TEST 1:\n");

        /**
         * Test 1:
         * 
         *  - Create a blank class diagram
         *     - check the attributes
         *  - Close the diagram
        */

        Object o = new Object();

        Core_ClassDiagram cd = new Core_ClassDiagram();
        assert cd.get_class("") == null : "Class is not null";
        assert cd.get_link("") == null : "Link is not null" ;
        assert cd.get_name() == "" : "Name is not empty" ;
        cd = null;

        System.out.println("\n\n");
        System.out.println("TEST 2:\n");

        /**
         * Test 2:
         * 
         *  - Create a blank class diagram
         *     - Change the name to "Class Diagram 1"
         *     - Create a blank class
         *     - Rename the class "Class 1"
         *     - Create a blank class
         *     - Rename the class "Class 2"
         *     - Create a link between the classes
         *     - Print the environment (should contain 2 empty classes)
         *  - Close the diagram
         * 
         */

        cd = new Core_ClassDiagram();
        cd.rename("Class Diagram 1");
        assert cd.get_name() == "Class Diagram 1" : "Name should be Class Diagram 1";
        Core_Class calss = cd.add_class();
        assert calss != null : "Class should not be null";
        int[] position = calss.get_position();
        assert ((position[0] == 0) && (position[1] == 0)) : "Position should be [0,0]";
        calss.rename("Class 1");
        assert calss.get_name() == "Class 1" : "Class name should be Class 1";
        Core_Class calsss = cd.add_class();
        assert calsss != null : "Class should not be null";
        calsss.rename("Class 2");
        assert calsss.get_name() == "Class 2" : "Class name should be Class 2";
        Core_Link link = cd.add_link(calss, calsss);
        assert link != null : "Link should not be null";

        test.print_environment(cd);

        link = null;
        calss = null;
        calsss = null;
        cd = null;


        System.out.println("\n\n");
        System.out.println("TEST 3:\n");

        /**
         * Test 3: 
         * 
         *  - Create a class diagram with 2 classes
         *     - Class 1
         *        - attr: "public int x = 5", "private String ff", "protected List<> list"
         *        - method: "private void git_gud(int[] arr)"
         *     - Class 2
         *        - attr: "public array ukk"
         *        - method: "public int x()"
         * 
         *  - Print the environment
         */

        cd = new Core_ClassDiagram("CD 1");
        calss = cd.add_class();
        calss.rename("Class 1");
         
        Core_Attribute attr = calss.add_attribute();
        attr.rename("x");
        attr.change_value("5");
        attr.change_type("int");
         
        attr = calss.add_attribute();
        attr.change_visibility(2);
        attr.change_type("String");
        attr.rename("ff");

        attr = calss.add_attribute();
        attr.change_visibility(1);
        attr.change_type("List<>");
        attr.rename("list");

        Core_Method meth = calss.add_method();
        meth.change_visibility(2);
        meth.rename("git_gud");
        attr = meth.add_param();
        attr.rename("arr");
        attr.change_type("int[]");

        calss = cd.add_class();
        calss.rename("Class 2");
        attr = calss.add_attribute();
        attr.change_type("array");
        attr.rename("ukk");
        meth = calss.add_method();
        meth.rename("x");
        meth.change_type("int");

        test.print_environment(cd);

        link = null;
        attr = null;
        meth = null;
        calss = null;
        calsss = null;
        cd = null;
    }

    public void print_environment(Core_ClassDiagram cd)
    {
        List<Core_Class> clst = cd.get_classes();
        List<Core_Attribute> attributel;
        List<Core_Method> methdsl;
        for (Core_Class cls : clst)
        {
            System.out.println(cls.get_name());
            System.out.println("----------------");

            attributel = cls.get_attributes();
            for (Core_Attribute attr : attributel)
                System.out.println(attr.get_str_attribute());
            
            System.out.println("----------------");
            methdsl = cls.get_methods();
            for (Core_Method meth : methdsl)
                System.out.println( meth.get_str_method() );

            System.out.println("================\n\n");
        }

    }
}