package edu.upvictoria.fpoo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class INSERT {
    public void insertImplicit(String path, String datos, String tabla) {
        String pathTable = path.endsWith("/") ? path + tabla + ".csv" : path + "/" + tabla + ".csv";
        String pathStructure = path.endsWith("/") ? path + tabla + "_ESTRUCT.csv" : path + "/" + tabla + "_ESTRUCT.csv";
        File file = new File(pathTable);

        if (!file.exists() || file.length() == 0) {
            System.out.println("Error: No se pueden insertar datos sin encabezados en un archivo vacío.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             FileWriter writer = new FileWriter(pathTable, true)) {

            String header = br.readLine();
            if (header == null || header.isEmpty()) {
                System.out.println("Error: El archivo CSV no tiene encabezado.");
                return;
            }

            String[] headerColumns = header.split("\t");
            String[] dataColumns = parseDataColumns(datos, ',');

           // System.out.println("Encabezados del archivo CSV: " + String.join(",", headerColumns) + " (" + headerColumns.length + ")");
            //System.out.println("Datos a insertar: " + String.join("\t", dataColumns) + " (" + dataColumns.length + ")");

            if (headerColumns.length != dataColumns.length) {
                System.out.println("Error de inserción: el número de columnas no coincide.");
                return;
            }

            if (!validateDataTypes(pathStructure, dataColumns)) {
                System.out.println("Error de inserción: los tipos de datos no coinciden.");
                return;
            }

            if (file.length() > 0 && !fileEndsWithNewLine(file)) {
                writer.write("\n");
            }

            writer.write(String.join("\t", dataColumns) + "\n");
            System.out.println("Inserción correcta");

        } catch (IOException e) {
            System.out.println("Error al acceder al archivo: " + e.getMessage());
        }
    }

    public void insertExplicit(String path, String columnas, String datos, String tabla) {
        String pathTable = path.endsWith("/") ? path + tabla + ".csv" : path + "/" + tabla + ".csv";
        String pathStructure = path.endsWith("/") ? path + tabla + "_ESTRUCT.csv" : path + "/" + tabla + "_ESTRUCT.csv";
        File file = new File(pathTable);
        boolean isFileEmpty = !file.exists() || file.length() == 0;

        if (!file.exists()) {
            System.out.println("Error: La tabla especificada no existe.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             FileWriter writer = new FileWriter(pathTable, true)) {

            if (isFileEmpty) {
                writer.write(columnas.trim().replaceAll("\\s*,\\s*", "\t") + "\n");
                writer.write(String.join("\t", parseDataColumns(datos, ',')) + "\n");
            } else {
                String header = br.readLine();
                if (header == null || header.isEmpty()) {
                    System.out.println("Error: El archivo CSV no tiene encabezado.");
                    return;
                }

                if (!header.trim().replaceAll("\\s*,\\s*", "\t").equals(columnas.trim().replaceAll("\\s*,\\s*", "\t"))) {
                    System.out.println("Error: Las columnas proporcionadas no coinciden con las del archivo CSV.");
                    return;
                }

                String[] headerColumns = header.split("\t");
                String[] dataColumns = parseDataColumns(datos, ',');

                if (headerColumns.length != dataColumns.length) {
                    System.out.println("Error de inserción: el número de columnas no coincide.");
                    return;
                }

                if (!validateDataTypes(pathStructure, dataColumns)) {
                    System.out.println("Error de inserción: los tipos de datos no coinciden.");
                    return;
                }

                if (file.length() > 0 && !fileEndsWithNewLine(file)) {
                    writer.write("\n");
                }

                writer.write(String.join("\t", dataColumns) + "\n");
            }

            System.out.println("Inserción correcta");

        } catch (IOException e) {
            System.out.println("Error al acceder al archivo: " + e.getMessage());
        }
    }


    private boolean validateDataTypes(String pathStructure, String[] dataColumns) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathStructure))) {
            // Skip header
            String line = br.readLine();
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] struct = line.split(",");
                String dataType = struct[2].trim();
                if (!struct[0].trim().isEmpty()) {  // Check if column is not null
                    if (dataType.startsWith("VARCHAR")) {
                        int maxLength = Integer.parseInt(dataType.substring(dataType.indexOf('(') + 1, dataType.indexOf(')')));
                        if (dataColumns[index].length() > maxLength) {
                            System.out.println("Error de tipo de datos: el valor en la columna " + index + " excede la longitud máxima para VARCHAR.");
                            return false;
                        }
                    } else if (dataType.equalsIgnoreCase("INT")) {
                        try {
                            Integer.parseInt(dataColumns[index]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error de tipo de datos: el valor en la columna " + index + " no es un entero válido.");
                            return false;
                        }
                    } else if (dataType.equalsIgnoreCase("NUMERIC") || dataType.equalsIgnoreCase("NUMBER")) {
                        try {
                            Double.parseDouble(dataColumns[index]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error de tipo de datos: el valor en la columna " + index + " no es un número válido.");
                            return false;
                        }
                    } else if (dataType.equalsIgnoreCase("CHAR")) {
                        if (dataColumns[index].length() != 1) {
                            System.out.println("Error de tipo de datos: el valor en la columna " + index + " no es un carácter válido.");
                            return false;
                        }
                    }

                    index++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer la estructura de la tabla: " + e.getMessage());
            return false;
        }

        return true;
    }

    private String[] parseDataColumns(String datos, char delimiter) {
        List<String> columns = new ArrayList<>();
        StringBuilder currentColumn = new StringBuilder();
        boolean inQuotes = false;
        for (char c : datos.toCharArray()) {
            if (c == '\'') {
                inQuotes = !inQuotes;
            } else if (c == delimiter && !inQuotes) {
                columns.add(currentColumn.toString().trim());
                currentColumn.setLength(0);
            } else {
                currentColumn.append(c);
            }
        }
        columns.add(currentColumn.toString().trim());
        return columns.toArray(new String[0]);
    }

    private boolean fileEndsWithNewLine(File file) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            if (raf.length() == 0) return false;
            raf.seek(raf.length() - 1);
            int lastByte = raf.read();
            return lastByte == '\n';
        }
    }
}
