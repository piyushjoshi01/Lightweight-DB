package org.example.db;

import org.example.parser.SQLParser;
import java.util.List;

public class TransactionManager {

    /**
     *Storing all queries between beging transaction and commit
     * @param allQueries
     */
    public static void startTransaction(List<String> allQueries){


            SQLParser parser = new SQLParser();
            for(String i : allQueries){
                parser.parse(i);
            }


    }
}




