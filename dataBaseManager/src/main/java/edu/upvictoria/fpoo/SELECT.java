package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SELECT {

    public void select(String path, String tabla, String columna){
        String ppath=path+"/"+tabla+".csv";
        try{
            BufferedReader br=new BufferedReader(new FileReader(ppath));
            String linea;
            int numer_col=0;
            while((linea=br.readLine())!=null){
                String [] datos=linea.split("\t");
                for(int i=0;i<datos.length;i++) {
                    if(datos[i].equals(columna)){
                        numer_col=i;
                    }
                }
                System.out.println(datos[numer_col]);
            }
        }catch(IOException e){
            System.out.println("La tabla no existe");
        }
    }

    public void select(String path, String tabla, String columna, String condicion){
        String[] col_condicional=condicion.split("=");
        System.out.println(col_condicional[1]+col_condicional[2]);
        String pppth=path+"/"+tabla+".csv";
    }

    public void select(String path, String tabla){
        String ppath=path+"/"+tabla+".csv";
        System.out.println(ppath);
        boolean bad= true;
        try{
            BufferedReader br=new BufferedReader(new FileReader(ppath));
            String linea;
            while((linea=br.readLine())!=null){
                String [] datos=linea.split("\t");
                for(int i=0;i<datos.length;i++) {
                    if(datos[i]!=null){
                        System.out.print(datos[i]+"\t");
                        bad=false;
                    }
                }
                System.out.print("\n");
            }
        }catch(IOException e){
          System.out.println("La tabla no existe");
        }
    }




}
