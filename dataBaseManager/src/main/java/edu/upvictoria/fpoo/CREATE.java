package edu.upvictoria.fpoo;

import javax.swing.text.Document;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CREATE{

    /**
     *
     * @param path
     * @param tabla
     * @param columnas
     */

    public void crear_estruc_tabla(String path,String tabla,String columnas){
       boolean NO=true;
        if(!path.endsWith("/")){
            path=path+"/";
        }
        String tablas=path+tabla+"_estruc.csv";
        String [] columna_individual=columnas.trim().split(",");


        try{
            File archiv=new File(tablas);
            System.out.println(archiv.getAbsolutePath());
            if(archiv.createNewFile()){
                System.out.println("TABLE CREATED");
            }else{
                System.out.println("OBJECT ALREADY EXIST");
            }

          FileWriter writer =new FileWriter(archiv);
            writer.write(tabla + "\n");
            for(String col: columna_individual){
                col = col.trim();
                System.out.println(col);
                String[] datos = col.split("\\s+");

                if (datos.length >= 2 && validar_tipo_de_datos(datos[1].trim())) {
                    System.out.println(datos[1].trim());
                    writer.write(datos[0] + "\t");
                } else {
                    System.out.println("Tipo de dato no admitido o insuficientes datos");
                    NO = false;
                    break;
                }
            }
            writer.close();
        }catch(IOException e){
            //System.out.println("ERROR: TABLE NOT CREATED");
        }
        if(NO){
            crear_tabla(columnas,tabla,path);
        }
    }

    public boolean validar_tipo_de_datos(String dato){
        Pattern p= Pattern.compile("VARCHAR\\s*\\(\\d+\\)");
        Matcher m=p.matcher(dato);
        if(m.find()){
            return true;
        }
        if(dato.equals("INT")){
            return true;
        }
        if(dato.equals("NUMERIC") || dato.equals("NUMBER")){
            return true;
        }
        if(dato.equals("CHAR")){
            return true;
        }
        return false;
    }

    public void crear_tabla(String columnas, String table, String path) {
        path = path.endsWith("/") ? path : path + "/";
        String tablaPath = path + table + ".csv";
        try {
            File tablaCSV = new File(tablaPath);
            if (tablaCSV.createNewFile()) {
                System.out.println("Table file created");
            } else {
                System.out.println("Table file already exists");
            }

            try (FileWriter writer = new FileWriter(tablaCSV)) {
                String[] columna_individual = columnas.trim().split(",");
                for (int i = 0; i < columna_individual.length; i++) {
                    String[] dato = columna_individual[i].trim().split("\\s+");
                    writer.write(dato[0]);
                    if (i < columna_individual.length - 1) {
                        writer.write("\t");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
