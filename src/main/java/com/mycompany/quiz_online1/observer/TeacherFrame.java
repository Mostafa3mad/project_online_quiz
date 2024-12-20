package com.mycompany.quiz_online1.observer;

import com.mycompany.quiz_online1.command.AddQuestionCommand;
import com.mycompany.quiz_online1.command.Command;
import com.mycompany.quiz_online1.command.CommandInvoker;
import com.mycompany.quiz_online1.singleton.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TeacherFrame extends JFrame implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private JTextField questionField, correctAnswerField;
    private JTextField option1Field, option2Field, option3Field, option4Field;
    private JButton addQuestionButton, viewScoresButton, undoButton, viewQuestionsButton;
    private JTable scoresTable, questionsTable;
    private JScrollPane scrollPane, questionsScrollPane;

    private JComboBox<String> questionTypeComboBox;
    private JLabel option1Label, option2Label, option3Label, option4Label, correctAnswerLabel;
    private JRadioButton trueRadioButton, falseRadioButton;
    private ButtonGroup trueFalseGroup;

    private CommandInvoker commandInvoker;

    public TeacherFrame(String teacherName) {
        setTitle("Teacher Page - Welcome " + teacherName);
        setSize(1000, 700);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        commandInvoker = new CommandInvoker();

        JLabel welcomeLabel = new JLabel("Welcome, " + teacherName + "!");
        welcomeLabel.setBounds(30, 10, 300, 30);
        add(welcomeLabel);

        JLabel questionTypeLabel = new JLabel("Question Type:");
        questionTypeLabel.setBounds(30, 50, 100, 25);
        add(questionTypeLabel);

        questionTypeComboBox = new JComboBox<>(new String[]{"Multiple Choice", "True/False"});
        questionTypeComboBox.setBounds(140, 50, 200, 25);
        add(questionTypeComboBox);

        JLabel questionLabel = new JLabel("Question:");
        questionLabel.setBounds(30, 90, 100, 25);
        questionField = new JTextField();
        questionField.setBounds(140, 90, 300, 25);
        add(questionLabel);
        add(questionField);

        option1Label = new JLabel("Option 1:");
        option1Label.setBounds(30, 130, 100, 25);
        option1Field = new JTextField();
        option1Field.setBounds(140, 130, 300, 25);
        add(option1Label);
        add(option1Field);

        option2Label = new JLabel("Option 2:");
        option2Label.setBounds(30, 170, 100, 25);
        option2Field = new JTextField();
        option2Field.setBounds(140, 170, 300, 25);
        add(option2Label);
        add(option2Field);

        option3Label = new JLabel("Option 3:");
        option3Label.setBounds(30, 210, 100, 25);
        option3Field = new JTextField();
        option3Field.setBounds(140, 210, 300, 25);
        add(option3Label);
        add(option3Field);

        option4Label = new JLabel("Option 4:");
        option4Label.setBounds(30, 250, 100, 25);
        option4Field = new JTextField();
        option4Field.setBounds(140, 250, 300, 25);
        add(option4Label);
        add(option4Field);

        correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerLabel.setBounds(30, 290, 100, 25);
        correctAnswerField = new JTextField();
        correctAnswerField.setBounds(140, 290, 300, 25);
        add(correctAnswerLabel);
        add(correctAnswerField);

        trueRadioButton = new JRadioButton("True");
        falseRadioButton = new JRadioButton("False");
        trueFalseGroup = new ButtonGroup();
        trueFalseGroup.add(trueRadioButton);
        trueFalseGroup.add(falseRadioButton);

        trueRadioButton.setBounds(140, 130, 100, 25);
        falseRadioButton.setBounds(250, 130, 100, 25);

        addQuestionButton = new JButton("Add Question");
        addQuestionButton.setBounds(140, 330, 150, 30);
        add(addQuestionButton);

        undoButton = new JButton("Undo");
        undoButton.setBounds(310, 330, 150, 30);
        add(undoButton);

        viewScoresButton = new JButton("View Student Scores");
        viewScoresButton.setBounds(500, 50, 200, 30);
        add(viewScoresButton);

        viewQuestionsButton = new JButton("View Questions");
        viewQuestionsButton.setBounds(500, 100, 200, 30);
        add(viewQuestionsButton);

        scoresTable = new JTable();
        scrollPane = new JScrollPane(scoresTable);
        scrollPane.setBounds(450, 150, 500, 200);
        add(scrollPane);

        questionsTable = new JTable();
        questionsScrollPane = new JScrollPane(questionsTable);
        questionsScrollPane.setBounds(450, 400, 500, 200);
        add(questionsScrollPane);

        questionTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleQuestionTypeFields();
            }
        });

        addQuestionButton.addActionListener(e -> {
            Command addQuestionCommand = new AddQuestionCommand(this, questionField.getText(), correctAnswerField.getText());
            commandInvoker.executeCommand(addQuestionCommand);
        });

        undoButton.addActionListener(e -> {
            commandInvoker.undoLastCommand();
        });

        viewScoresButton.addActionListener(e -> loadStudentScores());

        viewQuestionsButton.addActionListener(e -> loadQuestions());

        toggleQuestionTypeFields();
        setVisible(true);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void addQuestionToDatabase(String questionText, String correctAnswer) {
        String questionType = (String) questionTypeComboBox.getSelectedItem();


        String query = "INSERT INTO questions (question_text, question_type, correct_answer, option1, option2, option3, option4) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, questionText);
            stmt.setString(2, questionType);
            stmt.setString(3, correctAnswer);
            stmt.setString(4, option1Field.getText());
            stmt.setString(5, option2Field.getText());
            stmt.setString(6, option3Field.getText());
            stmt.setString(7, option4Field.getText());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Question added successfully" );
            notifyObservers();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add question!");
        }
    }

    public void removeLastQuestion() {
        String query = "DELETE FROM questions ORDER BY id DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Last question removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No questions to remove!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to remove last question!");
        }
    }

    private void toggleQuestionTypeFields() {
        String questionType = (String) questionTypeComboBox.getSelectedItem();
        if (questionType.equals("True/False")) {
            option1Label.setVisible(false);
            option1Field.setVisible(false);
            option2Label.setVisible(false);
            option2Field.setVisible(false);
            option3Label.setVisible(false);
            option3Field.setVisible(false);
            option4Label.setVisible(false);
            option4Field.setVisible(false);
            correctAnswerLabel.setVisible(false);
            correctAnswerField.setVisible(false);

            trueRadioButton.setVisible(true);
            falseRadioButton.setVisible(true);
            add(trueRadioButton);
            add(falseRadioButton);
        } else {
            option1Label.setVisible(true);
            option1Field.setVisible(true);
            option2Label.setVisible(true);
            option2Field.setVisible(true);
            option3Label.setVisible(true);
            option3Field.setVisible(true);
            option4Label.setVisible(true);
            option4Field.setVisible(true);
            correctAnswerLabel.setVisible(true);
            correctAnswerField.setVisible(true);

            trueRadioButton.setVisible(false);
            falseRadioButton.setVisible(false);
        }
    }

    private void loadStudentScores() {
        String query = "SELECT id, username, score_rate FROM students";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Username");
            model.addColumn("Score");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("score_rate")
                });
            }
            scoresTable.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load student scores: " + ex.getMessage());
        }
    }

    private void loadQuestions() {
        String query = "SELECT question_text, question_type, correct_answer FROM questions";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Question");
            model.addColumn("Type");
            model.addColumn("Correct Answer");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("question_text"),
                        rs.getString("question_type"),
                        rs.getString("correct_answer")
                });
            }
            questionsTable.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load questions: " + ex.getMessage());
        }
    }
}
