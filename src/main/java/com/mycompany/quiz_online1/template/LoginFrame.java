package com.mycompany.quiz_online1.template;

import com.mycompany.quiz_online1.factory.FrameFactory;
import com.mycompany.quiz_online1.singleton.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton studentRadio, teacherRadio, adminRadio;
    private ButtonGroup roleGroup;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Role Selection
        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setBounds(30, 20, 100, 25);
        add(roleLabel);

        studentRadio = new JRadioButton("Student");
        studentRadio.setBounds(140, 20, 100, 25);
        teacherRadio = new JRadioButton("Teacher");
        teacherRadio.setBounds(140, 50, 100, 25);
        adminRadio = new JRadioButton("Admin");
        adminRadio.setBounds(140, 80, 100, 25);

        roleGroup = new ButtonGroup();
        roleGroup.add(studentRadio);
        roleGroup.add(teacherRadio);
        roleGroup.add(adminRadio);

        add(studentRadio);
        add(teacherRadio);
        add(adminRadio);

        // Username and Password
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 120, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(140, 120, 200, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 160, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 160, 200, 25);
        add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(140, 200, 100, 30);
        add(loginButton);

        // Login Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = getSelectedRole();

                if (role == null) {
                    JOptionPane.showMessageDialog(null, "Please select a role!");
                    return;
                }

                if (authenticateUser(username, password, role)) {
                    JFrame frame = FrameFactory.getFrame(role, username);
                    frame.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
                }
            }
        });

        setVisible(true);
    }

    private String getSelectedRole() {
        if (studentRadio.isSelected()) return "Student";
        if (teacherRadio.isSelected()) return "Teacher";
        if (adminRadio.isSelected()) return "Admin";
        return null;
    }

    private boolean authenticateUser(String username, String password, String role) {
        String tableName;
        switch (role) {
            case "Student":
                tableName = "students";
                break;
            case "Teacher":
                tableName = "teachers";
                break;
            case "Admin":
                tableName = "admins";
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        String query = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database!");
        }
        return false;
    }


}
