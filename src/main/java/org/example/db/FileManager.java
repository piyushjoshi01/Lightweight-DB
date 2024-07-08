package org.example.db;

import org.example.parser.SQLParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    /**
     *Storing and mapping column names and values
     * @param sqlParser
     * @return
     */
    public static Map<String, String> storeData(SQLParser sqlParser){


            List<String> columns = sqlParser.columnName;
            List<String> values = sqlParser.inputValues;

        System.out.println("Columns: " + columns);
        System.out.println("Values: " + values);

            Map<String, String> dataInTable = new HashMap<>();

        for (int i = 0; i < columns.size(); i++) {
            dataInTable.put(columns.get(i), values.get(i));
        }
        return dataInTable;
    }

}
