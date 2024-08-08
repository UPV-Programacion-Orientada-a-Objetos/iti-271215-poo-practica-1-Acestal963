package edu.upvictoria.fpoo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        boolean p = false;
        boolean m = true;
        System.out.println("AL escribir las sentencias, escribala es una sola linea");
        CLAUSULAS clausula = new CLAUSULAS();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (m) {
            String entrada = null;
            try {
                entrada = br.readLine();
            } catch (IOException e) {
                System.out.println("Error al leer la entrada");
            }

            if (entrada != null) {
                sb.append(entrada).append("\n");
                if (entrada.trim().endsWith(";")) {
                    String sentenciaCompleta = sb.toString().trim();
                    sb.setLength(0); // Limpiar el StringBuilder

                    String[] sentencias = sentenciaCompleta.split(";");

                    for (String sentencia : sentencias) {
                        sentencia = sentencia.trim();
                        if (sentencia.isEmpty()) continue;

                        String[] en = sentencia.split(" ");

                        if (p && !en[0].toUpperCase().equals("USE")) {
                            System.out.println("Inicialice la base de datos");
                            p = true;
                        } else {
                            clausula.clausula(sentencia);
                        }

                        /*if (en[0].toUpperCase().equals("USE")) {
                            p = true;
                        }*/
                    }
                }
            }
        }

    }
}
