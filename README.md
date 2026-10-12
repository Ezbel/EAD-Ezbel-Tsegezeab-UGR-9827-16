Lab 1: Java Maven Starter Project

A simple, beginner-friendly Java project demonstrating how to connect to a MySQL database using JDBC and perform basic database operations (CRUD).

📌 What This Program Does

When you run Main.java, the program automatically performs the following six steps:

Creates Database & Table: Creates a database called StudentsDB and a table called students.
Inserts Students: Adds 11 students with their IDs, names, and grades.
Displays Data: Prints the first five students to the console.
Updates a Record: Changes student #1's first name to Michael.
Deletes a Record: Removes student #11.
Calculates Average: Calculates and displays the average grade of the remaining students.
📋 Requirements

Before running the project, make sure you have:

Java JDK 21 (or Java 17+)
MySQL Server installed and running on port 3306
IntelliJ IDEA or another Java IDE
Maven for dependency management
🚀 How to Run
Step 1: Configure Your MySQL Password

Open Main.java and update the database username and password to match your MySQL credentials.

Step 2: Start MySQL Server

Make sure your MySQL Server is running on your computer.

Step 3: Open the Project

Open the project in IntelliJ IDEA and allow Maven to download the required dependencies.

Step 4: Run the Program

Open Main.java and click the green Run button next to the main() method.

Step 5: View the Results

Check the IntelliJ console to see the database connection status, student records, update and delete results, and average grade.

🛠️ Technologies Used
Java
JDBC (Java Database Connectivity)
MySQL
Maven
IntelliJ IDEA
