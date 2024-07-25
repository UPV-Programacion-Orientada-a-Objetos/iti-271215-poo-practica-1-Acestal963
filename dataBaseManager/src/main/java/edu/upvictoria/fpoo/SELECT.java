package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SELECT {

    private List<String[]> loadData(String path, String tableName) {
        List<String[]> data = new ArrayList<>();
        String pathtabla=path+"/"+tableName+".csv";
       if(hasTableData(path, tableName)){
           System.out.println(true);
            try (BufferedReader br = new BufferedReader(new FileReader(pathtabla))) {
                String line;
                while ((line = br.readLine()) != null) {
                    data.add(line.split("\t"));
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + pathtabla);
            } catch (IOException e) {
                e.printStackTrace();
            }
       }else{
           System.out.println("La tabla "+tableName+" no contiene datos");
       }
        return data;
    }

    public static List<String[]> readTableData(String path, String tableName) {
        List<String[]> data = new ArrayList<>();
        String pathtabla = path + "/" + tableName + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(pathtabla))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split("\t"));
            }
        } catch (FileNotFoundException e) {
           // System.out.println("File not found: " + pathtabla);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public boolean hasTableData(String path, String tableName) {
        List<String[]> data = readTableData(path, tableName);
        return data.size() > 1;
    }


    public void select(String path, String tabla, String columna){
        String ppath=path+"/"+tabla+".csv";
        String[]colum=columna.split(",");
        if(!hasTableData(path, tabla)){
            System.out.println("La tabla "+tabla+" no contiene datos");
            return;
        }else {
            try (BufferedReader br = new BufferedReader(new FileReader(ppath))) {
                String linea;
                boolean headerRead = false;
                int[] col = new int[colum.length];


                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split("\t");

                    if (!headerRead) {
                        for (int i = 0; i < datos.length; i++) {
                            for (int j = 0; j < colum.length; j++) {
                                if (datos[i].equals(colum[j].trim())) {
                                    col[j] = i;
                                }
                            }
                        }

                        headerRead = true;
                        continue;
                    }
                    for (int i = 0; i < col.length; i++) {
                        if (col[i] < datos.length) {
                            System.out.print(datos[col[i]] + "\t");
                        }
                    }
                    System.out.println();
                }
            } catch (IOException e) {
                System.out.println("La tabla no existe");
            }
        }
    }

    public void select(String path, String tableName) {
        List<String[]> data = loadData(path, tableName);
        for (String[] row : data) {
            for (String column : row) {
                System.out.print(column + "\t");
            }
            System.out.println();
        }
    }


    public void SELECT(String path, String tabla, String columns, String condition) {
        String path_table = path.endsWith("/") ? path + tabla + ".csv" : path + "/" + tabla + ".csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(path_table))) {
            String header = reader.readLine();
            if (header == null) {
                System.out.println("Tabla vacía o no existe");
                return;
            }
            String[] columnNames = header.split("\t");
            Set<String> selectedColumns = new HashSet<>(Arrays.asList(columns.split(",")));
            Map<String, Integer> columnIndices = new HashMap<>();
            for (int i = 0; i < columnNames.length; i++) {
                columnIndices.put(columnNames[i].trim(), i);
            }
            WHERE where = new WHERE(columnIndices);
            System.out.println("Resultados de la consulta:");
            for (String column : columnNames) {
                if (selectedColumns.contains(column.trim())) {
                    System.out.print(column.trim() + "\t");
                }
            }
            System.out.println();
            String line;
            while ((line = reader.readLine()) != null) {
                if (where.evaluateCondition(line, condition)) {
                    String[] fields = line.split("\t");
                    for (int i = 0; i < columnNames.length; i++) {
                        if (selectedColumns.contains(columnNames[i].trim())) {
                            System.out.print(fields[i] + "\t");
                        }
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

}