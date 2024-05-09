package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        boolean p=true;
        boolean m=true;
        CLAUSULAS clausula=new CLAUSULAS();
       do{
           BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
           String entrada=null;
           try{
               entrada=br.readLine();
           }catch(IOException e){
               System.out.println("Error al leer la entrada");
           }
           String[] en = entrada.split(" ");

                if(p&&!en[0].equals("USE")){
                    System.out.println("Inicialice la base de datos");
                    p=false;
                }else{
                    clausula.clausula(entrada);
                }

       }while(m);

    }
}
