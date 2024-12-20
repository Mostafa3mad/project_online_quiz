package com.mycompany.quiz_online1.template;

import com.mycompany.quiz_online1.singleton.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminFrame extends JFrame {
    private JTextArea userDataArea;
    private JTextField idField, usernameField, scoreField;
    private JButton updateButton, deleteButton, refreshButton;

    public AdminFrame() {
        setTitle("Admin - Manage Users");
        setSize(600, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Label & TextArea to Display User Data
        JLabel label = new JLabel("User Data:");
        label.setBounds(20, 20, 100, 25);
        add(label);

        userDataArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(userDataArea);
        scrollPane.setBounds(20, 50, 540, 200);
        add(scrollPane);

        // Labels and Input Fields
        JLabel idLabel = new JLabel("User ID:");
        idLabel.setBounds(20, 270, 80, 25);
        add(idLabel);
        idField = new JTextField();
        idField.setBounds(100, 270, 100, 25);
        add(idField);

        JLabel usernameLabel = new JLabel("New Username:");
        usernameLabel.setBounds(20, 310, 100, 25);
        add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setBounds(130, 310, 150, 25);
        add(usernameField);

        JLabel scoreLabel = new JLabel("New Score:");
        scoreLabel.setBounds(20, 350, 100, 25);
        add(scoreLabel);
        scoreField = new JTextField();
        scoreField.setBounds(130, 350, 150, 25);
        add(scoreField);

        // Buttons
        updateButton = new JButton("Update");
        updateButton.setBounds(300, 310, 100, 30);
        add(updateButton);

        deleteButton = new JButton("Delete User");
        deleteButton.setBounds(300, 350, 120, 30);
        add(deleteButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(450, 270, 100, 30);
        add(refreshButton);

        // Load Data Initially
        loadUserData();

        // Action Listeners
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUserData();
            }
        });

        setVisible(true);
    }

    // Method to Load User Data
    private void loadUserData() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            StringBuilder data = new StringBuilder();
            while (rs.next()) {
                data.append("ID: ").append(rs.getInt("id"))
                        .append(", Username: ").append(rs.getString("username"))
                        .append(", Score: ").append(rs.getInt("score_rate")).append("\n");
            }
            userDataArea.setText(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load data!");
        }
    }

    // Method to Update User Information
    private void updateUser() {
        String id = idField.getText();
        String newUsername = usernameField.getText();
        String newScore = scoreField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "User ID is required!");
            return;
        }

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Update Query
            String query = "UPDATE students SET username = ?, score_rate = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newUsername);
                stmt.setString(2, newScore);
                stmt.setInt(3, Integer.parseInt(id));

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "User updated successfully!");
                    loadUserData(); // Refresh Data
                } else {
                    JOptionPane.showMessageDialog(this, "User not found!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update user!");
        }
    }

    // Method to Delete User
    private void deleteUser() {
        String id = idField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "User ID is required to delete!");
            return;
        }

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Delete Query
            String query = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(id));

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully!");
                    loadUserData(); // Refresh Data
                } else {
                    JOptionPane.showMessageDialog(this, "User not found!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete user!");
        }
    }


}
