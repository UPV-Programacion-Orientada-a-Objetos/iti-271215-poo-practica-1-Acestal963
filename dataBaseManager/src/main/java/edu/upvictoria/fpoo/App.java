package edu.upvictoria.fpoo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**U
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        boolean p=true;
        boolean m=true;
        System.out.println("AL escribir las sentencias, escribala es una sola linea");
        CLAUSULAS clausula=new CLAUSULAS();

        do {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String entrada = null;
            try {
                entrada = br.readLine();
            } catch (IOException e) {
                System.out.println("Error al leer la entrada");
            }

            if (entrada != null) {
                String[] sentencias = entrada.split(";");

                for (String sentencia : sentencias) {
                    sentencia = sentencia.trim();
                    if (sentencia.isEmpty()) continue;

                    String[] en = sentencia.split(" ");

                    if (p && !en[0].toUpperCase().equals("USE")) {
                        System.out.println("Inicialice la base de datos");
                        p = false;
                    } else {
                        clausula.clausula(sentencia);
                    }

                    if (en[0].toUpperCase().equals("USE")) {
                        p = false;
                    }
                }
            }
        } while (m);

    }
}
