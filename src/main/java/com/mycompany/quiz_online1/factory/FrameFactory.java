package com.mycompany.quiz_online1.factory;

import com.mycompany.quiz_online1.template.AdminFrame;
import com.mycompany.quiz_online1.observer.QuizFrame;
import com.mycompany.quiz_online1.observer.TeacherFrame;

import javax.swing.*;

public class FrameFactory {
    public static JFrame getFrame(String role, String username) {
        switch (role) {
            case "Student":
                return new QuizFrame(username);
            case "Teacher":
                return new TeacherFrame(username);
            case "Admin":
                return new AdminFrame();
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
