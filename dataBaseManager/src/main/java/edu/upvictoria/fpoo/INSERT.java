package edu.upvictoria.fpoo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class INSERT {
    public void INSERT(String path, String columnas, String datos, String tabla) {
        String path_table = path.endsWith("/") ? path + tabla + ".csv" : path + "/" + tabla + ".csv";
        File file = new File(path_table);
        boolean isFileEmpty = !file.exists() || file.length() == 0; // Verifica si el archivo existe y si está vacío
        try (FileWriter writer = new FileWriter(path_table, true)) {
            if (!isFileEmpty) {
                writer.write("\n");
            }
            String[] dat = datos.trim().split(",");
            for (int i = 0; i < dat.length; i++) {
                writer.write(dat[i].trim());
                if (i < dat.length - 1) {
                    writer.write("\t");
                }
            }
            writer.close();
            System.out.println("Inserción correcta");
        } catch (IOException e) {
            System.out.println("No existe la tabla: " + tabla);
        }
        removeEmptyLines(path_table);
    }

    public void INSERT(String path, String datos, String tabla) {
        String path_table = path.endsWith("/") ? path + tabla + ".csv" : path + "/" + tabla + ".csv";
        File file = new File(path_table);
        boolean isFileEmpty = !file.exists() || file.length() == 0; // Verifica si el archivo existe y si está vacío
        try (FileWriter writer = new FileWriter(path_table, true)) {
            if (!isFileEmpty) {
                writer.write("\n");
            }
            String[] dat = datos.trim().split(",");
            for (int i = 0; i < dat.length; i++) {
                writer.write(dat[i].trim());
                if (i < dat.length - 1) {
                    writer.write("\t");
                }
            }
            writer.close();
            System.out.println("Inserción correcta");
        } catch (IOException e) {
            System.out.println("No existe la tabla: " + tabla);
        }
        removeEmptyLines(path_table);
    }

    private void removeEmptyLines(String path) {
        File file = new File(path);
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine || !line.trim().isEmpty()) {
                    lines.add(line);
                    isFirstLine = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
