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
        path=path+"/";
        String tablas=path+tabla+"_estruc.csv";
        String [] columna_individual=columnas.trim().split(",");

        try{
            File archiv=new File(tablas);
            if(archiv.createNewFile()){
                System.out.println("TABLE CREATED");
            }else{
                System.out.println("OBJECT ALREADY EXIST");
            }

            FileWriter writer=new FileWriter(archiv);
            writer.write(tabla);
            writer.write("\n");
            for(int i=0;i<columna_individual.length;i++){
                columna_individual[i]=columna_individual[i].trim();
                String[] datos=columna_individual[i].trim().split(" ");
                if(validar_tipo_de_datos(datos[1].trim())){
                    writer.write(datos[0]+"\t"+datos[2]+" "+datos[3]+"\t"+datos[1].trim());
                }else{
                    System.out.println("Tipo de dato no admitido");
                    NO=false;
                    return;
                }
                writer.write("\n");
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
        Pattern p= Pattern.compile("VARCHAR\\((\\d+)\\)");
        Matcher m=p.matcher(dato);
        if(m.find()){
            return true;
        }
        if(dato.equals("INT")){
            return true;
        }
        if(dato.equals("NUMERIC")){
            return true;
        }
        if(dato.equals("CHAR")){
            return true;
        }
        return false;
    }

    public void crear_tabla(String columna,String table,String path){
        path=path+"/";
        String tablaPath=path+"/"+table+".csv";
        try{
            File tablaCSV=new File(tablaPath);
            if(!tablaCSV.createNewFile()){
                //System.out.println("ERROR: TABLE NOT CREATED");
            }
            FileWriter Writer=new FileWriter(tablaCSV);

            String[] Columna=columna.trim().split(",");
            for(int i=0;i<Columna.length;i++){
                Columna[i]=Columna[i].trim();
                String[] dato=Columna[i].trim().split(" ");
                Writer.write(dato[0]+"\t");
            }
            Writer.close();

        }catch(IOException e){
            //System.out.println("ERROR: TABLE NOT CREATED");
        }
    }


}
