package edu.upvictoria.fpoo;

import java.io.*;

public class INSERT {


    public void INSERT(String path, String columnas, String datos, String tabla){
        String path_table=path+"/"+tabla+".csv";
        try{
            FileWriter writer=new FileWriter(path_table);
            writer.write("\n");
            String[] dat=datos.trim().split(",");
            for(int i=0;i<dat.length;i++){
                writer.write(dat[i]+"\t");
            }
            writer.close();
        }catch(IOException e){
          System.out.println("NO existe la tabla; "+tabla);
        }
    }

    public void INSERT(String path,String datos, String tabla){

    }
}
