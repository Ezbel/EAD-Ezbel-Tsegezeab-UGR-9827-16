package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ─────────────────────────────────────────────────────────────
            // Task 1: Connect to MySQL, Create Database and Table
            // ─────────────────────────────────────────────────────────────
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MySQL server successfully!");

            Statement statement = connection.createStatement();

            // Create the database if it doesn't exist
            statement.execute("CREATE DATABASE IF NOT EXISTS StudentsDB");
            System.out.println("Database 'StudentsDB' created (or already exists).");

            // Switch to the StudentsDB database
            statement.execute("USE StudentsDB");

            // Create the students table if it doesn't exist
            statement.execute(
                "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY, " +
                "firstname VARCHAR(255), " +
                "lastname VARCHAR(255), " +
                "grade INT" +
                ")"
            );
            System.out.println("Table 'students' created (or already exists).");

            // ─────────────────────────────────────────────────────────────
            // Task 2: Insert Data
            // ─────────────────────────────────────────────────────────────

            // Clear table first to avoid duplicate key errors on re-run
            statement.execute("DELETE FROM students");

            // Insert a single row as an example
            PreparedStatement insertStatement =
                connection.prepareStatement(
                    "INSERT INTO students (id, firstname, lastname, grade) VALUES (?, ?, ?, ?)"
                );

            insertStatement.setInt(1, 1);
            insertStatement.setString(2, "John");
            insertStatement.setString(3, "Doe");
            insertStatement.setInt(4, 90);
            insertStatement.executeUpdate();
            System.out.println("Single row inserted successfully.");

            // Insert ten more rows using a loop
            String[][] students = {
                {"Alice",   "Smith",    "85"},
                {"Bob",     "Johnson",  "78"},
                {"Carol",   "Williams", "92"},
                {"David",   "Brown",    "88"},
                {"Eva",     "Jones",    "76"},
                {"Frank",   "Garcia",   "95"},
                {"Grace",   "Martinez", "83"},
                {"Henry",   "Lee",      "70"},
                {"Isla",    "Harris",   "91"},
                {"James",   "Clark",    "87"}
            };

            for (int i = 0; i < students.length; i++) {
                insertStatement.setInt(1, i + 2);                          // id: 2..11
                insertStatement.setString(2, students[i][0]);              // firstname
                insertStatement.setString(3, students[i][1]);              // lastname
                insertStatement.setInt(4, Integer.parseInt(students[i][2])); // grade
                insertStatement.executeUpdate();
            }
            System.out.println("Ten additional rows inserted successfully.");

            // ─────────────────────────────────────────────────────────────
            // Task 3: Retrieve and Display 5 Rows
            // ─────────────────────────────────────────────────────────────
            System.out.println("\n--- First 5 Students ---");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students LIMIT 5");

            while (resultSet.next()) {
                int id            = resultSet.getInt("id");
                String firstname  = resultSet.getString("firstname");
                String lastname   = resultSet.getString("lastname");
                int grade         = resultSet.getInt("grade");

                System.out.println(id + " | " + firstname + " | " + lastname + " | " + grade);
            }

            // ─────────────────────────────────────────────────────────────
            // Task 4: Update firstname of a student by id
            // ─────────────────────────────────────────────────────────────
            PreparedStatement updateStatement =
                connection.prepareStatement("UPDATE students SET firstname = ? WHERE id = ?");

            updateStatement.setString(1, "Michael");
            updateStatement.setInt(2, 1);
            updateStatement.executeUpdate();
            System.out.println("\nStudent with id=1 firstname updated to 'Michael' successfully!");

            // ─────────────────────────────────────────────────────────────
            // Task 5: Delete a row by id
            // ─────────────────────────────────────────────────────────────
            PreparedStatement deleteStatement =
                connection.prepareStatement("DELETE FROM students WHERE id = ?");

            deleteStatement.setInt(1, 11);
            deleteStatement.executeUpdate();
            System.out.println("Student with id=11 deleted successfully!");

            // ─────────────────────────────────────────────────────────────
            // Task 6: Calculate and Display Average Grade
            // ─────────────────────────────────────────────────────────────
            ResultSet averageResult = statement.executeQuery("SELECT AVG(grade) AS average_grade FROM students");

            if (averageResult.next()) {
                double averageGrade = averageResult.getDouble("average_grade");
                System.out.println("\nAverage grade of all students: " + averageGrade);
            }

            // ─────────────────────────────────────────────────────────────
            // Close resources
            // ─────────────────────────────────────────────────────────────
            insertStatement.close();
            updateStatement.close();
            deleteStatement.close();
            resultSet.close();
            averageResult.close();
            statement.close();
            connection.close();

            System.out.println("\nAll tasks completed. Connection closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}