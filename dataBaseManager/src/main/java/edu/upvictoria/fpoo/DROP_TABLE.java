package edu.upvictoria.fpoo;

import java.io.*;

public class DROP_TABLE {
    public void drop(String path,String tabla){
        String  ppath=path+"/"+tabla+".csv";
        String estructura=path+"/"+tabla+"_estruc.csv";
        try{
            File Table=new File(ppath);
            File estruc=new File(estructura);
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("¿Estás seguro de eliminar la tabla "+tabla+"? (S/n)");
            String dec=br.readLine();
            if(dec.toUpperCase().equals("S")){
                if(Table.delete()&&estruc.delete()){
                    System.out.println("Se ha eliminado la tabla "+tabla);
                }
            }else if(dec.toUpperCase().equals("N")){
                System.out.println("No se ha eliminó la tabla");
            }else{
                System.out.println("Accion no ejecutada");
                return;
            }
        }catch(IOException e){

        }
    }
}
