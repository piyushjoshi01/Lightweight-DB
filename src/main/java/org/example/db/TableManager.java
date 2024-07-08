package org.example.db;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class TableManager {

    private static final String BASE_DIRECTORY = "src/main/java/database/";

    /**
     *Creating a table
     * @param tableName
     *
     */
    public static void createTable(String tableName) {
        if (!DatabaseManager.databaseExists()) {
            System.err.println("Database does not exist: " + DatabaseManager.DBName);
            return;
        }

        Path tablePath = Paths.get(BASE_DIRECTORY + DatabaseManager.DBName + "/" + tableName + ".txt");
        try {
            if (!Files.exists(tablePath)) {
                Files.createFile(tablePath);
                System.out.println("Table Created: " + tableName);

            } else {
                System.out.println("Table already exists: " + tableName);
            }
        } catch (IOException e) {
            System.err.println("Failed to create table: " + tableName);
            e.printStackTrace();
        }
    }

    /**
     * Insert Values into the table
     * @param tableName
     * @param dataInTable
     */

    public static void insertValues(String tableName, Map<String,String> dataInTable) {
        Path tablePath = Paths.get(BASE_DIRECTORY + DatabaseManager.DBName + "/" + tableName + ".txt");


        try (FileWriter fileWriter = new FileWriter(tablePath.toString(), true);
             PrintWriter pw = new PrintWriter(fileWriter)) {

            for (Map.Entry<String, String> entry : dataInTable.entrySet()) {
                String line = entry.getKey() + "=" + entry.getValue();
                pw.println(line);
                System.out.println(line);
            }


//            pw.println(dataInTable);


            System.out.println(tablePath);
            System.out.println("Values inserted into table: " + tableName);
        } catch (IOException e) {
            System.err.println("Failed to insert values into table: " + tableName);
            e.printStackTrace();
        }
    }

    /**
     *This will run select query
     * @param dbName
     * @param tableName
     * @param whereClause
     */
    public static void selectFromTable(String dbName, String tableName, String whereClause) {

        String[] parts = whereClause.split("=");
        String filterColumn = parts[0].trim();
        String filterValue = parts[1].trim();

        Path tablePath = Paths.get(BASE_DIRECTORY + DatabaseManager.DBName + "/" + tableName + ".txt");
        try (BufferedReader br = new BufferedReader(new FileReader(tablePath.toString()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineParts = line.split("=");
                if (lineParts.length == 2) {
                    String currentColumn = lineParts[0].trim();
                    String currentValue = lineParts[1].trim();
                    if (currentColumn.equals(filterColumn) && currentValue.equals(filterValue)) {
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read from table: " + tableName);
            e.printStackTrace();
        }
    }
}
