package uml.testdir;


import java.util.List;

import uml.core.*;

/**
 * CLASS: CORE TEST
 * 
 * <p> Basic test suite for package uml.core
 * <p> Relies heavily on manual supervision
 *
 * @author Juraj Mariani
 */
public class Core_Test extends java.lang.Object
{

    public Core_Test()
    {

    }

    /**
     * Test Suite
     */
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


        System.out.println("\n\n");
        System.out.println("TEST 4:\n");
        /**
         * Test 4:
         * is a modification fo test 2 + added test for remove_*
         * 
         *  - Create a blank class diagram
         *     - Change the name to "Class Diagram 1"
         *     - Create a blank class
         *     - Rename the class "Class 1"
         *     -    - Add an attribute 'int x = 15'
         *     -    - Remove the attribute
         *     - Create a blank class
         *     - Rename the class "Class 2"
         *     - Create a link between the classes
         *     - Rename the link to 'works'
         *     - Remove the link 'works'
         *     - Remove the class 'Class 1'
         *     - Delete the Class Diagram 1
         *  - Close the diagram
         * 
         */

        cd = new Core_ClassDiagram();
        cd.rename("Class Diagram 1");
        calss = cd.add_class();
        calss.rename("Class 1");
        attr = calss.add_attribute();
        attr.rename("x");
        attr.change_type("int");
        attr.change_value("13");
        assert attr.get_name() == "x" : "The attribute name has been set to x but now it is not";
        assert attr.get_type() == "int" : "The attribute's type has been set to 'int' but now it is not";
        assert attr.get_value() == "13" : "The attribute's value has been set to '13' but now it is not";

        test.print_environment(cd);
        System.out.println("------------- 1 class 1 attribute ------------\n");

        Core_Attribute attr_2 = calss.get_attribute("x");
        assert attr == attr_2 : "The two attributes should point to the same object";
        calss.remove_attribute(attr);
        attr_2 = calss.get_attribute("x");
        assert attr_2 == null : "The attribute should not be present in the class";
        calsss = cd.add_class();
        calsss.rename("Class 2");
        link = cd.add_link(calss, calsss);
        link.rename("works");

        test.print_environment(cd);
        System.out.println("------------- 2 classes 0 attribs ------------\n");
        
        assert link.get_name() == "works" : "The link name should have been set to 'works'";
        Core_Link link2 = cd.get_link("works");
        assert link == link2 : "The links should contain a reference to the same object";
        cd.remove_link(link);
        assert cd.get_link("works") == null : "The link 'works' should have been deleted";
        
        assert cd.get_class("Class 1") == calss : "The two references should point to the same object";
        cd.remove_class(calss);
        assert cd.get_class("Class 1") == null : "The class should have been removed";

        test.print_environment(cd);
        System.out.println("------------- 1 class ------------\n");

        link = null;
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