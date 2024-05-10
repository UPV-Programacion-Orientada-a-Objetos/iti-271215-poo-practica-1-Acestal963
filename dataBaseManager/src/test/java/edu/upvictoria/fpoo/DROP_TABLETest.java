package edu.upvictoria.fpoo;

import junit.framework.TestCase;

import java.io.File;

public class DROP_TABLETest extends TestCase {

    public void testDrop() {
        String table1="src/test/java/edu/upvictoria/fpoo/gg";
        DROP_TABLE d=new DROP_TABLE();
        d.drop(table1,"tabla2","S");
        File f=new File("src/test/java/edu/upvictoria/fpoo/gg/tabla2.csv");
        assertTrue(!f.exists());
    }
}