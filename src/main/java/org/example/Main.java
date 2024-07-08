package org.example;
import org.example.UserAuthentication.UserAuthenticator;
import org.example.db.TransactionManager;
import org.example.parser.SQLParser;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        SQLParser parser = new SQLParser();
        boolean inTransaction = false;
        List<String> transactionqueries = new ArrayList<>();
        String filepath = "src/main/java/org/example/UserAuthentication/registereduser.txt";
        String filepath1 = "src/main/java/org/example/UserAuthentication/LogFile.txt";

        try (Scanner sc = new Scanner(System.in)) {
            UserAuthenticator userAuthenticator = new UserAuthenticator();

            System.out.println("Register Enter 0 and to login enter 1");
            int choice = sc.nextInt();

            if (choice == 0) {
                userAuthenticator.registerUser(sc);
                choice = 1;
            }

            if (choice == 1) {
                boolean isLoggedIn = userAuthenticator.loginUser(sc);
                if (isLoggedIn) {
                    while (true) {
                        System.out.print("SQL> ");
                        String sql = sc.nextLine().trim();
                        if ("exit".equalsIgnoreCase(sql)) {
                            break;
                        }
                        else if ("Begin Transaction".equalsIgnoreCase(sql)) {
                            inTransaction = true;
                            transactionqueries.clear();

                        } else if ("Commit".equalsIgnoreCase(sql) && inTransaction) {
                            TransactionManager.startTransaction(transactionqueries);
                            inTransaction = false;
                        }
                        else if (inTransaction) {
                            transactionqueries.add(sql);
                        }


                        LocalDateTime now = LocalDateTime.now();
                        try (FileWriter fw = new FileWriter(filepath1, true)) {
                            fw.write("Name:" + UserAuthenticator.userName + "/nSQL Queries Entered:" + sql + "Date and Time" + now);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        parser.parse(sql);

                    }

                } else {
                    System.out.println("Invalid Login Credentials");
                }
            }
        }
    }
}










