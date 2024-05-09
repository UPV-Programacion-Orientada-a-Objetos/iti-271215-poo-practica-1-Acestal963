package edu.upvictoria.fpoo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLAUSULAS {
    String path$;

    public Boolean clausula(String clausula){
       String [] sentencia=clausula.split(" ");
       try{
           if(sentencia[0].equals("USE")){
               USE(sentencia[1]);
               return true;
           }else if(sentencia[0].equals("SHOW")&&sentencia[1].equals("TABLES")){
               SHOW_TABLES(path$);
               return true;
           }else if(sentencia[0].equals("CREATE")){
               CREATE(clausula);
               return true;
           }else if(sentencia[0].equals("DROP")) {
               DROP_TABLE(clausula);
           }else if(sentencia[0].equals("INSERT")&&sentencia[0].equals("INTO")){

           }else{
               System.out.println("Comando desconocido");
           }
       }catch( ArrayIndexOutOfBoundsException e){
           System.out.println("objeto no encontrado");
       }
       return false;
    }
    public void USE(String path){
        System.out.println("Path de la base de datos: "+path);
        path$=path;
    }

    public void SHOW_TABLES(String path){
        System.out.println("Mostrar tablas de la base de datos de: "+path$);
    }

    public void CREATE(String clausula){
        Pattern p=Pattern.compile("CREATE TABLE (\\w+) \\(((?:[^()]+|\\((?:[^()]+|\\([^()]*\\))*\\))+)\\);");
        Matcher m=p.matcher(clausula);
        if(m.find()){
            System.out.println("Nombre de la tabla: "+m.group(1)+"\nColumnas:\n");
            String[] col=m.group(2).trim().split(",");
            for(String columna:col){

                    System.out.println("datos: "+columna);

            }
            CREATE crear=new CREATE();
            crear.crear_estruc_tabla(path$,m.group(1),m.group(2).trim());
        }else if(!m.find()){
            System.out.println("ERROR: Sintaxis Incorrecta");
        }
    }

    public void DROP_TABLE(String clausula){
        Pattern p=Pattern.compile("DROP TABLE (\\w+);");
        Matcher m=p.matcher(clausula);
        if(m.find()){
            System.out.println("Tabla a eliminar: "+m.group(1));
        }
    }
}
