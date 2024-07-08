package org.example.parser;

import org.example.db.DatabaseManager;
import org.example.db.FileManager;
import org.example.db.TableManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLParser {

    public List<String> columnName = new ArrayList<>();
    public List<String> inputValues = new ArrayList<>();

    /**
     *This is parsing method which will parse all the queries inserted
     * @param sql
     */
    public void parse(String sql) {
        if (sql.startsWith("CREATE DATABASE")) {
            String dbName = sql.split("\\s+")[2];
            System.out.println(dbName);
            DatabaseManager.createDatabase(dbName);
        } else if (sql.startsWith("CREATE TABLE")) {
            Pattern pattern = Pattern.compile("CREATE TABLE (\\w+)\\s*\\((.*)\\)");
            Matcher matcher = pattern.matcher(sql);
            if (matcher.find()) {

                String tableName = matcher.group(1);
                String ColumnData = matcher.group(2);

                TableManager.createTable(tableName);



                System.out.println("Table Data: " + ColumnData);
            }

        } else if (sql.startsWith("INSERT INTO")) {
            Pattern pattern = Pattern.compile("INSERT INTO (\\w+)\\s*\\((.*)\\) VALUES \\((.*)\\)");
            Matcher matcher = pattern.matcher(sql);
            if (matcher.find()) {
                String dbName = "";
                String tableName = matcher.group(1);
                String columnData = matcher.group(2);
                String values = matcher.group(3);


                columnName.clear();
                inputValues.clear();


                Pattern pattern1 = Pattern.compile("\\b(\\w+)\\s+\\w+");
                Matcher matcher1 = pattern1.matcher(columnData);
                while (matcher1.find()) {
                    columnName.add(matcher1.group(1));
                }


                Pattern pattern2 = Pattern.compile("\"([^\"]+)\"");
                Matcher matcher2 = pattern2.matcher(values);
                while (matcher2.find()) {
                    inputValues.add(matcher2.group(1));
                }


                Map<String, String> dataInTable = FileManager.storeData(this);


                TableManager.insertValues(tableName, dataInTable);
            }
        }else if (sql.startsWith("SELECT")) {
            Pattern pattern = Pattern.compile("SELECT\\s+.*\\s+FROM\\s+(\\w+)(\\s+WHERE\\s+(.*))?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sql);
            if (matcher.find()) {
                String dbName = "";
                String tableName = matcher.group(1);


                String whereClause = null;
                if (matcher.groupCount() > 2) {
                    whereClause = matcher.group(3); //
                }


                System.out.println("Table Name: " + tableName);


                 TableManager.selectFromTable(dbName, tableName,whereClause);
            }
        }


    }
}
