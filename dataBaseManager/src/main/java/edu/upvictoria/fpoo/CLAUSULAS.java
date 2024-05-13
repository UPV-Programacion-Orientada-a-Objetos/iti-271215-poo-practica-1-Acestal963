package edu.upvictoria.fpoo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLAUSULAS {
    String path$;

    /**
     *
     * @param clausula
     * @return
     */
    public Boolean clausula(String clausula){
       String [] sentencia=clausula.split(" ");
       try{
           if(sentencia[0].toUpperCase().equals("USE")){
               USE(sentencia[1]);
               return true;
           }else if(sentencia[0].toUpperCase().equals("SHOW")&&sentencia[1].toUpperCase().equals("TABLES")){
               SHOW_TABLES(path$);
               return true;
           }else if(sentencia[0].toUpperCase().toUpperCase().equals("CREATE")){
               CREATE(clausula);
               return true;
           }else if(sentencia[0].toUpperCase().equals("DROP")&&sentencia[1].toUpperCase().equals("TABLE")) {
               DROP_TABLE(clausula);
           }else if(sentencia[0].toUpperCase().equals("INSERT")){
               INSERT_INTO(clausula);
               return true;
           }else if(sentencia[0].toUpperCase().equals("SELECT")){
               SELECT(clausula);
               return true;
           }else{
               System.out.println("Comando desconocido");
           }
       }catch( ArrayIndexOutOfBoundsException e){
           System.out.println("objeto no encontrado");
       }
       return false;
    }

    /**
     *
     * @param path
     */
    public void USE(String path){
        System.out.println("Path de la base de datos: "+path);
        path$=path;
    }

    /**
     *
     * @param path
     */
    public void SHOW_TABLES(String path){
        SHOW_TABLES mostrar=new SHOW_TABLES();
        mostrar.SHOW(path$);
    }

    /**
     *
     * @param clausula
     */
    public void CREATE(String clausula){
        Pattern p=Pattern.compile("CREATE TABLE (\\w+) \\(((?:[^()]+|\\((?:[^()]+|\\([^()]*\\))*\\))+)\\);");
        Matcher m=p.matcher(clausula);
        if(m.find()){
            //7System.out.println("Nombre de la tabla: "+m.group(1)+"\nColumnas:\n");
            String[] col=m.group(2).trim().split(",");
            CREATE crear=new CREATE();
            crear.crear_estruc_tabla(path$,m.group(1),m.group(2).trim());
        }else if(!m.find()){
            System.out.println("Sintaxis Incorrecta");
        }
    }

    /**
     *
     * @param clausula
     */
    public void DROP_TABLE(String clausula){
        Pattern p=Pattern.compile("DROP TABLE (\\w+);");
        Matcher m=p.matcher(clausula);
        DROP_TABLE drop=new DROP_TABLE();
        if(m.find()){
          drop.drop(path$,m.group(1));
        }
    }

    /**
     *
     * @param clausula
     */
    public void INSERT_INTO(String clausula){
        Pattern p=Pattern.compile("INSERT INTO (\\w+) VALUES \\(([^)]+)\\);");
        Matcher m=p.matcher(clausula);

        Pattern p2=Pattern.compile("INSERT INTO (\\w+) \\(([^)]+)\\) VALUES \\(([^)]+)\\);");
        Matcher m2=p2.matcher(clausula);

        INSERT insert=new INSERT();
        if(m.find()){
            insert.INSERT(path$,m.group(2),m.group(1));
        }
        if(m2.find()){
            insert.INSERT(path$,m.group(2),m.group(3),m.group(1));
        }
    }

    public void SELECT(String clausula){
        boolean t=false;
        Pattern p2=Pattern.compile("SELECT \\* FROM (\\w+);");
        Matcher m2=p2.matcher(clausula);
        SELECT select=new SELECT();
        if(m2.find()){
            select.select(path$,m2.group(1));
            t=true;
        }

        Pattern p1=Pattern.compile("SELECT ([^*]+) FROM (\\w+);",Pattern.CASE_INSENSITIVE);
        Matcher m1=p1.matcher(clausula);
        if(m1.find()){
            select.select(path$,m1.group(2),m1.group(1));
            t=true;
        }

        Pattern p3=Pattern.compile("SELECT ([^*]+) FROM (\\w+) WHERE (.+);");
        Matcher m3=p3.matcher(clausula);
        if(m3.find()){
            select.select(path$,m3.group(2),m3.group(1),m3.group(3));
            //select.select(path$,m3.group(2),m3.group(1),m3.group(3));
            t=true;
        }
        if(!t){
            System.out.println("Sintaxis Incorrecta");
            return;
        }

    }
}
