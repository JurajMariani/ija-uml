package uml.testdir;


import uml.core.*;
import uml.io.store_xml;

public class IO_Test extends java.lang.Object
{
    public static void main(String[] args)
    {
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

        
        Core_ClassDiagram cd = new Core_ClassDiagram("CD 1");
        Core_Class calss = cd.add_class();
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

        store_xml store = new store_xml(cd, "./", "aaa");

        attr = null;
        meth = null;
        calss = null;
        cd = null;
    }
}