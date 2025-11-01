package jdbc_exp.PartC_UpdateDelete;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc_exp";
        String username = "root";      // apna MySQL username
        String password = "Rivya@29";          // agar password hai to likh do

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connected to MySQL successfully!\n");

            // UPDATE record
            String updateQuery = "UPDATE Product SET Price = ? WHERE ProductName = ?";
            PreparedStatement psUpdate = con.prepareStatement(updateQuery);
            psUpdate.setDouble(1, 140.75);
            psUpdate.setString(2, "Notebook");
            int rowsUpdated = psUpdate.executeUpdate();
            System.out.println(rowsUpdated + " record(s) updated successfully!");

            // DELETE record
            String deleteQuery = "DELETE FROM Product WHERE ProductName = ?";
            PreparedStatement psDelete = con.prepareStatement(deleteQuery);
            psDelete.setString(1, "Pen");
            int rowsDeleted = psDelete.executeUpdate();
            System.out.println(rowsDeleted + " record(s) deleted successfully!");

            // DISPLAY remaining records
            System.out.println("\n📋 Remaining Records:");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Product");
            while (rs.next()) {
                System.out.println(
                    rs.getInt("ProductID") + " | " +
                    rs.getString("ProductName") + " | " +
                    rs.getDouble("Price") + " | " +
                    rs.getInt("Quantity")
                );
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
