package edu.upvictoria.fpoo;

import junit.framework.TestCase;

public class CLAUSULASTest extends TestCase {
    /**
     *
     */
    public void testClausula() {

        CLAUSULAS clausula1=new CLAUSULAS();
        assertTrue(clausula1.clausula("CREATE TABLE ejemplo1 (ID INT NOT NULL, NOMBRE VARCHAR(20) NOT NULL);)"));
    }

    public void testClausula_INSERT(){
        CLAUSULAS clausula1=new CLAUSULAS();
        assertTrue(clausula1.clausula("INSERT INTO ejemplo1 VALUES (1, ISRAEL);"));
    }
    public void testClausula_SELECT1(){
        CLAUSULAS clausula1=new CLAUSULAS();
        assertTrue(clausula1.clausula("SELECT * FROM ejemplo1;"));
    }
    public void testClausula_SELECT2(){
        CLAUSULAS clausula1=new CLAUSULAS();
        assertTrue(clausula1.clausula("SELECT nombre FROM ejemplo1;"));
    }
    public void testClausula_SELECT(){
        CLAUSULAS clausula1=new CLAUSULAS();
        assertTrue(clausula1.clausula("SELECT nombre FROM ejemplo1 WHERE nombre=israel;"));
    }
}