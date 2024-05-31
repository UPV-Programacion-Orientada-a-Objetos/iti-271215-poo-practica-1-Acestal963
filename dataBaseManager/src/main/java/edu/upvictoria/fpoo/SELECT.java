package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SELECT {

    public void select(String path, String tabla, String columna){
        String ppath=path+"/"+tabla+".csv";
        String[]colum=columna.split(",");
        try{
            BufferedReader br=new BufferedReader(new FileReader(ppath));
            String linea;
            int j=0;
            int[] col=new int[colum.length];
            while((linea=br.readLine())!=null){
                String [] datos=linea.split("\t");
                for(int i=0;i<datos.length;i++) {
                    for(String s:colum){
                        if(datos[i].equals(s)){
                            col[j++]=i;
                        }
                    }
                }
                for(int a=0;a<j;a++){
                    System.out.print(datos[col[a]]+"\t");
                }
                System.out.print("\n");
                if(j==0){
                    System.out.println("Columnas inexistentes");
                    return;
                }
            }
        }catch(IOException e){
            System.out.println("La tabla no existe");
        }
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

    /*public void select(String path, String tabla, String columna, String condicion){
        String pppth=path+"/"+tabla+".csv";
        String[] condiciones=null;
        String operador_LO;//para un solo caso de operadores logicos

        for(int i=0;i<1;i++){
            if(condicion.toUpperCase().contains("AND")){
                operador_LO= "&&";
                condiciones=condicion.trim().split("AND");
            }else if(condicion.toUpperCase().contains("OR")){
                condiciones=condicion.trim().split("OR");
                operador_LO="||";
            }else{
                condiciones= new String[]{condicion};
                //System.out.println(condiciones[0]);
            }
        }
        int j=0;
        String cc=null;
        String vv=null;
        String comparador;
        for(int i=0;i<condiciones.length;i++){
            if(condiciones[i].contains("=")){
                String[] valores=condiciones[i].split("=");
                cc=valores[0];
                vv=valores[1];
                comparador="=";

            }else if(condiciones[i].contains("!=")||condiciones[i].contains("<>")){

            }
        }

    }

    public void WHERE(String path, String tabla, String columnas,String valores, String operadores_Comparacion){
        String patht=path+"/"+tabla+".csv";

    }

    public void WHERE(String path, String tabla, String[] columnas,String[] valores, String[] operadores_Comparacion,String [] operadores_logicos){

    }*/
    public void select (String path, String tabla,String columna, String condicion){

    }

}
