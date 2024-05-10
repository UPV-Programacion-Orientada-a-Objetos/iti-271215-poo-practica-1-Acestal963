package edu.upvictoria.fpoo;

import junit.framework.TestCase;

import java.io.File;

public class CREATETest extends TestCase {

    public void testCrear_estruc_tabla() {
        CREATE c=new CREATE();
        c.crear_estruc_tabla("src/test/java/edu/upvictoria/fpoo/gg","juanito","ID INT NOT NULL");
        File ta=new File("src/test/java/edu/upvictoria/fpoo/gg/juanito.csv");
        assertTrue(ta.exists());
    }

    public void testValidar_tipo_de_datos_INT() {
        CREATE C=new CREATE();
        assertTrue(C.validar_tipo_de_datos("INT"));
    }
    public void testValidar_tipo_de_datos_VARCHAR() {
        CREATE C=new CREATE();
        assertTrue(C.validar_tipo_de_datos("VARCHAR(20)"));
    }
    public void testValidar_tipo_de_datos_NUMERIC() {
        CREATE C=new CREATE();
        assertTrue(C.validar_tipo_de_datos("NUMERIC"));
    }
    public void testValidar_tipo_de_datos_CHAR() {
        CREATE C=new CREATE();
        assertTrue(C.validar_tipo_de_datos("CHAR"));
    }

    public void testCrear_tabla() {
        CREATE C= new CREATE();
        C.crear_tabla("ID INT NOT NULL","mimi","src/test/java/edu/upvictoria/fpoo/gg");
        File to=new File("src/test/java/edu/upvictoria/fpoo/gg/mimi.csv");
        assertTrue(to.exists());
    }
}