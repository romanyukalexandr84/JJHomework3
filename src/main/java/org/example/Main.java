package org.example;

import java.sql.*;

public class Main {
    private static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE Student (
                      id int PRIMARY KEY,
                      firstName varchar(32),
                      secondName varchar(32),
                      age int);
                    """);
            System.out.println("Создана таблица Student");
        }
    }

    private static void fillTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int addedRecords = statement.executeUpdate("""
                    INSERT INTO Student(id, firstName, secondName, age) values
                    (1, 'Сергей', 'Иванов', 28),
                    (2, 'Алексей', 'Петров', 35),
                    (3, 'Игорь', 'Смирнов', 42),
                    (4, 'Петр', 'Ковалев', 19),
                    (5, 'Александр', 'Новиков', 57),
                    (6, 'Дмитрий', 'Степанов', 15)
                    """);
            System.out.println("Кол-во записей в таблице: " + addedRecords);
        }
    }

    private static void deleteRecord(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            System.out.println("Удалено записей: " + statement.executeUpdate("DELETE FROM Student WHERE id=3 OR id=4"));
        }
    }

    private static void changeRecord(Connection connection, int age, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE Student SET id = $1 WHERE age = $2")) {
            statement.setInt(1, id);
            statement.setInt(2, age);
            System.out.println("Обновлено записей: " + statement.executeUpdate());
        }
    }

    private static void workWithDB(Connection connection) throws SQLException {
        createTable(connection);
        fillTable(connection);
        deleteRecord(connection);
        changeRecord(connection, 57, 3);
        changeRecord(connection, 15, 4);
    }

    private static void printDB(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet linesOfTable = statement.executeQuery("SELECT id, firstName, secondName, age FROM Student");
            while (linesOfTable.next()) {
                int id = linesOfTable.getInt("id");
                String firstName = linesOfTable.getString("firstName");
                String secondName = linesOfTable.getString("secondName");
                int age = linesOfTable.getInt("age");
                System.out.println("id = " + id + ", firstName = " + firstName
                        + ", secondName = " + secondName + ", age = " + age);
            }
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            workWithDB(connection);
            printDB(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}