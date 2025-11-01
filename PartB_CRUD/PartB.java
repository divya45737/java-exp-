package PartB_CRUD;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/jdbc_exp";
    static final String USER = "root";
    static final String PASSWORD = "Rivya@29"; // ← apna MySQL password daalo

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Connected to database!");

            conn.setAutoCommit(false); // Transaction handling

            while (true) {
                System.out.println("\n========= PRODUCT MENU =========");
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addProduct(conn, sc);
                        break;
                    case 2:
                        viewProducts(conn);
                        break;
                    case 3:
                        updateProduct(conn, sc);
                        break;
                    case 4:
                        deleteProduct(conn, sc);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        conn.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }

    // Create
    private static void addProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter product name: ");
            sc.nextLine();
            String name = sc.nextLine();
            System.out.print("Enter price: ");
            double price = sc.nextDouble();
            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();

            String query = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.executeUpdate();
            conn.commit();

            System.out.println("✅ Product added successfully!");
        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ex) {}
            System.out.println("❌ Error adding product: " + e.getMessage());
        }
    }

    // Read
    private static void viewProducts(Connection conn) {
        try {
            String query = "SELECT * FROM Product";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("\n--- Product List ---");
            while (rs.next()) {
                System.out.printf("%d | %s | %.2f | %d\n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading products: " + e.getMessage());
        }
    }

    // Update
    private static void updateProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter ProductID to update: ");
            int id = sc.nextInt();
            System.out.print("Enter new price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new quantity: ");
            int qty = sc.nextInt();

            String query = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, price);
            ps.setInt(2, qty);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product updated successfully!");
            } else {
                System.out.println("⚠️ Product not found!");
            }
        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ex) {}
            System.out.println("❌ Error updating product: " + e.getMessage());
        }
    }

    // Delete
    private static void deleteProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter ProductID to delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM Product WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product deleted successfully!");
            } else {
                System.out.println("⚠️ Product not found!");
            }
        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ex) {}
            System.out.println("❌ Error deleting product: " + e.getMessage());
        }
    }
}
