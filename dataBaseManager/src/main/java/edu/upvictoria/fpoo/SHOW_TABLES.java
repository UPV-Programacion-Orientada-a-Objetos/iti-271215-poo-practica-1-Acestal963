package edu.upvictoria.fpoo;
import java.io.File;

public class SHOW_TABLES {
    public void SHOW(String path){
        File base_Datos=new File(path);
        File[] tablas=base_Datos.listFiles();
        for(File tabla: tablas){
            if(!tabla.getName().contains("_")){
                System.out.println(tabla.getName());
            }
        }
    }
}
