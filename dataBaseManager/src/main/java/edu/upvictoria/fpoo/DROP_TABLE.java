package edu.upvictoria.fpoo;

import java.io.*;

public class DROP_TABLE {
    public void drop(String path,String tabla){
       drop(path,tabla, null);
    }
    public void drop(String path,String tabla,String dec){
        String ppath = path.endsWith("/") ? path + tabla + ".csv" : path + "/" + tabla + ".csv";
        String estructura = path.endsWith("/") ? path + tabla + "_estruc.csv" : path + "/" + tabla + "_estruc.csv";

        try {
            File table = new File(ppath);
            File estruc = new File(estructura);

            if (dec == null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("¿Estás seguro de eliminar la tabla " + tabla + "? (S/n)");
                dec = br.readLine().toUpperCase();
            } else {
                dec = dec.toUpperCase();
            }

            if (dec.equals("S")) {
                if (table.delete() && estruc.delete()) {
                    System.out.println("Se ha eliminado la tabla " + tabla);
                } else {
                    System.out.println("Error al eliminar la tabla " + tabla);
                }
            } else if (dec.equals("N")) {
                System.out.println("No se ha eliminado la tabla");
            } else {
                System.out.println("Acción no ejecutada");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
