package PartA_ConnectAndFetch;


import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc_exp"; // Database name jdbc_exp
        String username = "root";  // apna username
        String password = "Rivya@29";      // agar password hai to likh, warna empty chhodo

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connected to MySQL successfully!\n");

            // Create Statement
            Statement stmt = con.createStatement();

            // Execute SELECT query
            String query = "SELECT * FROM Employee";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("👩‍💼 Employee Table Data:");
            System.out.println("------------------------------------");
            System.out.println("EmpID\tName\tSalary");
            System.out.println("------------------------------------");

            // Loop through result
            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.println(id + "\t" + name + "\t" + salary);
            }

            // Close connection
            con.close();
        } 
        catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
