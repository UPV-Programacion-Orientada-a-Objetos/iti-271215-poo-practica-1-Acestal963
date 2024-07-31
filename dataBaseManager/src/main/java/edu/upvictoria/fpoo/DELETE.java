package edu.upvictoria.fpoo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DELETE {
    public void delete(String path, String tableName, String condition) {
        String pathTable = path.endsWith("/") ? path + tableName + ".csv" : path + "/" + tableName + ".csv";
        File file = new File(pathTable);

        Map<String, Integer> columnIndices = new HashMap<>();
        StringBuilder newContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                // Guardar encabezados y mapear índices de columnas
                String[] headers = line.split("\t");
                for (int i = 0; i < headers.length; i++) {
                    columnIndices.put(headers[i].trim(), i);
                }
                newContent.append(line).append("\n"); // Agregar encabezados al nuevo contenido
            }

            WHERE where = new WHERE(columnIndices);

            while ((line = reader.readLine()) != null) {
                String[] row = line.split("\t"); // Convertir la línea a un arreglo de cadenas
                if (!where.evaluateCondition(row, condition)) {
                    newContent.append(line).append("\n"); // Agregar al nuevo contenido si la condición no se cumple
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(newContent.toString()); // Escribir el nuevo contenido en el archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

