package edu.upvictoria.fpoo;

import junit.framework.TestCase;

public class CLAUSULASTest extends TestCase {

    public void testClausula() {
        CLAUSULAS clausula1=new CLAUSULAS();
        assertTrue(clausula1.clausula("CREATE TABLE ejemplo1 (ID INT NOT NULL, NOMBRE VARCHAR(20) NOT NULL);)"));
    }
    public void testClausula_USE(){
        CLAUSULAS clausula1=new CLAUSULAS();

    }
}