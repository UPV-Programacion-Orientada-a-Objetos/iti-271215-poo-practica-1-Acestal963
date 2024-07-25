package edu.upvictoria.fpoo;
import java.io.File;

public class SHOW_TABLES {
    public void SHOW(String path){
        File base_Datos=new File(path);
        File[] tablas=base_Datos.listFiles();
        if(tablas!=null){
            for(File tabla: tablas){
                if(!tabla.getName().contains("_")&&tabla.getName()!=null){
                    System.out.println(tabla.getName());
                }
            }
        }else{
            System.out.println("No carnal, esta vacio\n");
        }
    }
}
