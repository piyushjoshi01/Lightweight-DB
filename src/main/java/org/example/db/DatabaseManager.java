package org.example.db;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseManager {

    private static final String BASE_DIRECTORY = "src/main/java/database/";
    public static String DBName;

    /**
     * This is to create a database
     * @param dbName
     */
    public static void createDatabase(String dbName) {
        Path baseDirPath = Paths.get(BASE_DIRECTORY);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(baseDirPath)) {
            boolean directoryExists = false;
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    DBName = path.getFileName().toString();
                    System.out.println("A database directory already exists: " + DBName + ". No new database created.");
                    directoryExists = true;
                    break;
                }
            }

            if (!directoryExists) {
                Path dbPath = Paths.get(BASE_DIRECTORY + dbName);
                Files.createDirectories(dbPath);
                DBName = dbName;
                System.out.println("Database Created: " + dbName);
            }
        } catch (IOException e) {
            System.err.println("Failed to check or create database directory.");
            e.printStackTrace();
        }
    }

    public static boolean databaseExists() {
        Path dbPath = Paths.get(BASE_DIRECTORY + DBName);
        return Files.exists(dbPath);
    }
}
