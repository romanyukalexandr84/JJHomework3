package org.example;

import java.sql.*;

public class WorkWithMySQLDB {
    private static void printTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet linesOfTable = statement.executeQuery("SELECT ID, name, type, birthdate, commands FROM cats");
            while (linesOfTable.next()) {
                int id = linesOfTable.getInt("ID");
                String name = linesOfTable.getString("name");
                String type = linesOfTable.getString("type");
                Date birthdate = linesOfTable.getDate("birthdate");
                String commands = linesOfTable.getString("commands");
                System.out.println("ID = " + id + ", name = " + name
                        + ", type = " + type + ", birthdate = " + birthdate + ", commands = " + commands);
            }
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HumanFriends", "admin", "admin")) {
            printTable(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}