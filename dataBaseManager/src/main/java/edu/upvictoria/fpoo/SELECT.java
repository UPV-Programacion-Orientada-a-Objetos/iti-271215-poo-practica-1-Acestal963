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

    public void select(String path, String tableName, String columns, String condition) {
        List<String[]> data = loadData(path, tableName);
        if (data.isEmpty()) {
            System.out.println("No data found for table: " + tableName);
            return;
        }

        String[] colum = columns.split(",");
        int[] colIndices = new int[colum.length];
        String[] header = data.get(0);

        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < header.length; i++) {
            columnIndexMap.put(header[i].trim(), i);
        }

        for (int j = 0; j < colum.length; j++) {
            colum[j] = colum[j].trim();
            if (columnIndexMap.containsKey(colum[j])) {
                colIndices[j] = columnIndexMap.get(colum[j]);
            } else {
                System.out.println("Column not found: " + colum[j]);
                return;
            }
        }

        System.out.println("Resultados de la búsqueda:");
        for (int colIndex : colIndices) {
            System.out.print(header[colIndex] + "\t");
        }
        System.out.println();

        WHERE where = new WHERE(columnIndexMap);
        for (String[] row : data.subList(1, data.size())) {
            if (where.evaluateCondition(row, condition)) {
                for (int colIndex : colIndices) {
                    if (colIndex < row.length) {
                        System.out.print(row[colIndex] + "\t");
                    }
                }
                System.out.println();
            }
        }
    }


    public void selectAll(String path, String tableName, String condition) {
        List<String[]> data = loadData(path, tableName);
        if (data.isEmpty()) {
            System.out.println("No data found for table: " + tableName);
            return;
        }

        String[] header = data.get(0);
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < header.length; i++) {
            columnIndexMap.put(header[i].trim(), i);
        }

        WHERE where = new WHERE(columnIndexMap);
        System.out.println("Resultados de la búsqueda:");
        System.out.println(String.join("\t", header));

        for (String[] row : data.subList(1, data.size())) {
            if (where.evaluateCondition(row, condition)) {
                System.out.println(String.join("\t", row));
            }
        }
    }

}